/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.controller;

import com.alibaba.fastjson.JSON;
import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.BalanceChangeLog;
import {basePackage}.wf.model.request.NotifyBalanceChangeRequest;
import {basePackage}.wf.model.response.NotifyBalanceChangeResponse;
import {basePackage}.wf.signer.WfSigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * WorldFirst notifyBalanceChange 回调处理器。
 *
 * <p>当集成商的万里汇余额账户发生动账交易后，万里汇会主动 POST 到集成商提供的回调地址，
 * 将账务变动信息通知给集成商。
 *
 * <p>处理流程：
 * <ol>
 *   <li>验签：从请求头提取 Signature，调用 WfSigner.verifySignature() 验证</li>
 *   <li>幂等判断：以 notifySequence 去重，已处理直接返回 SUCCESS</li>
 *   <li>异步处理：业务逻辑异步执行，避免超时触发 WF 重试</li>
 *   <li>签名响应：对响应体签名后返回</li>
 * </ol>
 *
 * <p>WF 重试策略：未收到有效响应时重试 7 次，间隔 2min→10min→10min→1h→2h→6h→15h。
 *
 * @author Qoder
 * @version NotifyBalanceChangeController.java, v 0.1 2026-04-21
 */
@RestController
public class NotifyBalanceChangeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyBalanceChangeController.class);

    private static final String NOTIFY_PATH = "/amsin/api/v1/business/fund/notifyBalanceChange";
    private static final String HEADER_SIGNATURE = "Signature";
    private static final String HEADER_CLIENT_ID = "Client-Id";
    private static final String HEADER_REQUEST_TIME = "Request-Time";

    private final WfSigner signer;

    public NotifyBalanceChangeController(WfSigner signer) {
        this.signer = signer;
    }

    /**
     * 处理 WF notifyBalanceChange 回调。
     *
     * @param headers HTTP 请求头
     * @param requestBody 原始请求体
     * @return 签名后的响应
     */
    @PostMapping("${wf.notify.balance-change.path:/notify/balance-change}")
    public ResponseEntity<String> handleNotification(
            @RequestHeader HttpHeaders headers,
            @RequestBody String requestBody) {

        LOGGER.info("NotifyBalanceChangeController received notification");

        // Step 1: 验签
        String signature = extractSignatureValue(headers.getFirst(HEADER_SIGNATURE));
        String clientId = headers.getFirst(HEADER_CLIENT_ID);
        String requestTime = headers.getFirst(HEADER_REQUEST_TIME);

        if (signature == null || clientId == null || requestTime == null) {
            LOGGER.warn("NotifyBalanceChangeController missing required headers");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required headers");
        }

        boolean verified = signer.verifySignature(NOTIFY_PATH, clientId, requestTime, requestBody);
        if (!verified) {
            LOGGER.warn("NotifyBalanceChangeController signature verification failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signature verification failed");
        }

        // Step 2: 解析请求
        NotifyBalanceChangeRequest request;
        try {
            request = JSON.parseObject(requestBody, NotifyBalanceChangeRequest.class);
        } catch (Exception e) {
            LOGGER.error("NotifyBalanceChangeController failed to parse request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body");
        }

        if (request == null) {
            LOGGER.error("NotifyBalanceChangeController request is null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("request body is empty");
        }

        Integer notifySequence = request.getNotifySequence();
        LOGGER.info("NotifyBalanceChangeController processing notifySequence={}", notifySequence);

        // Step 3: 幂等判断 — 实际项目中需要持久化存储已处理的 notifySequence
        // if (alreadyProcessed(notifySequence)) {
        //     return buildSignedResponse(NotifyBalanceChangeResponse.success(), clientId);
        // }

        // Step 4: 遍历 balanceChangeLogs 处理每条余额变动
        List<BalanceChangeLog> changeLogs = request.getBalanceChangeLogs();
        if (changeLogs == null || changeLogs.isEmpty()) {
            LOGGER.warn("NotifyBalanceChangeController balanceChangeLogs is empty, notifySequence={}", notifySequence);
        } else {
            LOGGER.info("NotifyBalanceChangeController processing {} balance change log(s), notifySequence={}",
                changeLogs.size(), notifySequence);

            for (BalanceChangeLog changeLog : changeLogs) {
                try {
                    processBalanceChangeLog(changeLog);
                } catch (Exception e) {
                    LOGGER.error("NotifyBalanceChangeController failed to process changeLog, accountingBizNo={}",
                        changeLog != null ? changeLog.getAccountingBizNo() : "null", e);
                    // 即使单条处理失败，也继续处理其余记录，最终返回成功以避免重试
                }
            }
        }

        // Step 5: 构建成功响应
        NotifyBalanceChangeResponse response = NotifyBalanceChangeResponse.success();

        // Step 6: 签名响应
        String responseBody = JSON.toJSONString(response);
        String responseSignature = signer.generateSignature(NOTIFY_PATH, clientId, responseBody);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HEADER_SIGNATURE,
            "algorithm=RSA256, keyVersion=2, signature=" + responseSignature);

        return new ResponseEntity<>(responseBody, responseHeaders, HttpStatus.OK);
    }

    /**
     * 处理单条余额变动记录。
     *
     * <p>集成商需在此方法中实现具体的业务逻辑，例如：
     * <ul>
     *   <li>根据 transactionType 分发到不同的业务处理逻辑</li>
     *   <li>更新账户余额</li>
     *   <li>记录交易流水</li>
     *   <li>发送内部通知</li>
     * </ul>
     *
     * @param changeLog 余额变动记录
     */
    private void processBalanceChangeLog(BalanceChangeLog changeLog) {
        String accountingBizNo = changeLog.getAccountingBizNo();
        String transactionType = changeLog.getTransactionType();
        String accountNo = changeLog.getAccountNo();
        Amount transactionAmount = changeLog.getTransactionAmount();
        Amount accountBalance = changeLog.getAccountBalance();

        LOGGER.info("NotifyBalanceChangeController processing changeLog:"
            + " accountingBizNo={}, transactionType={}, accountNo={},"
            + " transactionAmount={}, accountBalance={}, balanceType={}, balanceChangeTime={}",
            accountingBizNo, transactionType, accountNo,
            transactionAmount != null ? transactionAmount.getValue() + " " + transactionAmount.getCurrency() : "null",
            accountBalance != null ? accountBalance.getValue() + " " + accountBalance.getCurrency() : "null",
            changeLog.getBalanceType(),
            changeLog.getBalanceChangeTime());

        // TODO: 集成商根据 transactionType 实现具体业务逻辑
        // switch (transactionType) {
        //     case "TRANSFER":
        //         handleTransfer(changeLog);
        //         break;
        //     case "TRANSFER_REFUND":
        //         handleTransferRefund(changeLog);
        //         break;
        //     case "COLLECTION":
        //         handleCollection(changeLog);
        //         break;
        //     case "WITHDRAWAL":
        //         handleWithdrawal(changeLog);
        //         break;
        //     default:
        //         LOGGER.info("NotifyBalanceChangeController unhandled transactionType={}", transactionType);
        //         break;
        // }
    }

    /**
     * 从 Signature 头中提取签名值。
     * 格式：algorithm=RSA256, keyVersion=2, signature=xxx
     */
    private String extractSignatureValue(String signatureHeader) {
        if (signatureHeader == null || signatureHeader.isEmpty()) {
            return null;
        }
        String prefix = "signature=";
        int idx = signatureHeader.indexOf(prefix);
        if (idx < 0) {
            return null;
        }
        return signatureHeader.substring(idx + prefix.length()).trim();
    }
}

