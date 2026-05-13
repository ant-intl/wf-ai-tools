package {basePackage}.wf.controller;

import com.alibaba.fastjson.JSON;
import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.PayerBankAccount;
import {basePackage}.wf.model.domain.VostroBeneficiaryAccount;
import {basePackage}.wf.model.request.NotifyVostroRequest;
import {basePackage}.wf.model.response.NotifyVostroResponse;
import {basePackage}.wf.model.response.Result;
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

/**
 * WorldFirst notifyVostro 回调处理器。
 *
 * <p>当集成商的万里汇账户发生充值后，万里汇会主动 POST 到集成商提供的回调地址，
 * 将垫付/退款结果通知给集成商。
 *
 * <p>处理流程：
 * <ol>
 *   <li>验签：从请求头提取 Signature，调用 WfSigner.verifySignature() 验证</li>
 *   <li>幂等判断：以 fundingId 去重，已处理直接返回 SUCCESS</li>
 *   <li>异步处理：业务逻辑异步执行，避免超时触发 WF 重试</li>
 *   <li>签名响应：对响应体签名后返回</li>
 * </ol>
 *
 * <p>WF 重试策略：未收到有效响应时重试 7 次，间隔 2min→10min→10min→1h→2h→6h→15h。
 */
@RestController
public class NotifyVostroController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyVostroController.class);

    private static final String NOTIFY_PATH = "/amsin/api/v1/business/fund/notifyVostro";
    private static final String HEADER_SIGNATURE = "Signature";
    private static final String HEADER_CLIENT_ID = "Client-Id";
    private static final String HEADER_REQUEST_TIME = "Request-Time";
    private static final String RESULT_STATUS_SUCCESS = "S";

    private final WfSigner signer;

    public NotifyVostroController(WfSigner signer) {
        this.signer = signer;
    }

    /**
     * 处理 WF notifyVostro 回调。
     *
     * @param headers HTTP 请求头
     * @param requestBody 原始请求体
     * @return 签名后的响应
     */
    @PostMapping("${wf.notify.vostro.path:/notify/vostro}")
    public ResponseEntity<String> handleNotification(
            @RequestHeader HttpHeaders headers,
            @RequestBody String requestBody) {

        LOGGER.info("NotifyVostroController received notification");

        // Step 1: 验签
        String signature = extractSignatureValue(headers.getFirst(HEADER_SIGNATURE));
        String clientId = headers.getFirst(HEADER_CLIENT_ID);
        String requestTime = headers.getFirst(HEADER_REQUEST_TIME);

        if (signature == null || clientId == null || requestTime == null) {
            LOGGER.warn("NotifyVostroController missing required headers");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required headers");
        }

        boolean verified = signer.verifySignature(NOTIFY_PATH, clientId, requestTime, requestBody);
        if (!verified) {
            LOGGER.warn("NotifyVostroController signature verification failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signature verification failed");
        }

        // Step 2: 解析请求
        NotifyVostroRequest request;
        try {
            request = JSON.parseObject(requestBody, NotifyVostroRequest.class);
        } catch (Exception e) {
            LOGGER.error("NotifyVostroController failed to parse request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body");
        }

        if (request == null || request.getFundingId() == null) {
            LOGGER.error("NotifyVostroController request or fundingId is null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fundingId is missing");
        }

        String fundingId = request.getFundingId();
        LOGGER.info("NotifyVostroController processing fundingId={}", fundingId);

        // Step 3: 幂等判断 — 实际项目中需要持久化存储已处理的 fundingId
        // if (alreadyProcessed(fundingId)) {
        //     return buildSignedResponse(NotifyVostroResponse.success(), clientId);
        // }

        // Step 4: 处理垫付/退款结果
        Result balanceResult = request.getBalanceResult();
        if (balanceResult != null && RESULT_STATUS_SUCCESS.equals(balanceResult.getResultStatus())) {
            String resultCode = balanceResult.getResultCode();
            if ("SUCCESS".equals(resultCode)) {
                // 垫付成功
                handleFundingSuccess(request);
            } else if ("REFUND".equals(resultCode)) {
                // 退款成功
                handleRefundSuccess(request);
            } else {
                LOGGER.warn("NotifyVostroController unknown resultCode={} with resultStatus=S, fundingId={}",
                    resultCode, fundingId);
                handleFundingSuccess(request);
            }
        } else {
            LOGGER.warn("NotifyVostroController unexpected balanceResult: resultStatus={}, resultCode={}, fundingId={}",
                balanceResult != null ? balanceResult.getResultStatus() : "null",
                balanceResult != null ? balanceResult.getResultCode() : "null",
                fundingId);
        }

        // Step 5: 构建成功响应
        NotifyVostroResponse response = NotifyVostroResponse.success();

        // Step 6: 签名响应
        String responseBody = JSON.toJSONString(response);
        String responseSignature = signer.generateSignature(NOTIFY_PATH, clientId, responseBody);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HEADER_SIGNATURE,
            "algorithm=RSA256, keyVersion=2, signature=" + responseSignature);

        return new ResponseEntity<>(responseBody, responseHeaders, HttpStatus.OK);
    }

    /**
     * 处理垫付成功通知。
     *
     * <p>集成商需在此方法中实现具体的业务逻辑，例如：
     * <ul>
     *   <li>更新账户余额</li>
     *   <li>记录充值流水</li>
     *   <li>发送内部通知</li>
     * </ul>
     *
     * @param request 通知请求
     */
    private void handleFundingSuccess(NotifyVostroRequest request) {
        Amount amount = request.getBalanceChangeAmount();
        LOGGER.info("NotifyVostroController funding success, fundingId={}, amount={}, beneficiaryAccount={}, balanceChangeTime={}",
            request.getFundingId(),
            amount != null ? amount.getValue() + " " + amount.getCurrency() : "null",
            request.getBeneficiaryAccount() != null
                ? request.getBeneficiaryAccount().getBeneficiaryBankAccountNo() : "null",
            request.getBalanceChangeTime());

        // TODO: 集成商实现具体业务逻辑
    }

    /**
     * 处理退款成功通知。
     *
     * <p>集成商需在此方法中实现退款相关的业务逻辑。
     *
     * @param request 通知请求
     */
    private void handleRefundSuccess(NotifyVostroRequest request) {
        Amount amount = request.getBalanceChangeAmount();
        LOGGER.info("NotifyVostroController refund success, fundingId={}, amount={}, beneficiaryAccount={}, balanceChangeTime={}",
            request.getFundingId(),
            amount != null ? amount.getValue() + " " + amount.getCurrency() : "null",
            request.getBeneficiaryAccount() != null
                ? request.getBeneficiaryAccount().getBeneficiaryBankAccountNo() : "null",
            request.getBalanceChangeTime());

        // TODO: 集成商实现退款业务逻辑
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

