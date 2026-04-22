/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.controller;

import com.alibaba.fastjson.JSON;
import {basePackage}.wf.model.domain.TransferResult;
import {basePackage}.wf.model.request.NotifyPayoutRequest;
import {basePackage}.wf.model.response.NotifyPayoutResponse;
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
 * WorldFirst notifyPayout 回调处理器。
 *
 * <p>万里汇在转账完成后，主动 POST 到集成商提供的回调地址（transferNotifyUrl），
 * 将转账结果通知给集成商。
 *
 * <p>处理流程：
 * <ol>
 *   <li>验签：从请求头提取 Signature，调用 WfSigner.verifySignature() 验证</li>
 *   <li>幂等判断：以 transferRequestId 去重，已处理直接返回 SUCCESS</li>
 *   <li>异步处理：业务逻辑异步执行，避免超时触发 WF 重试</li>
 *   <li>签名响应：对响应体签名后返回</li>
 * </ol>
 *
 * <p>WF 重试策略：未收到有效响应时重试 7 次，间隔 2min→10min→10min→1h→2h→6h→15h。
 *
 * @author Qoder
 * @version NotifyPayoutController.java, v 0.1 2026-04-22
 */
@RestController
public class NotifyPayoutController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyPayoutController.class);

    private static final String NOTIFY_PATH = "/amsin/api/v1/business/fund/notifyPayout";
    private static final String HEADER_SIGNATURE = "Signature";
    private static final String HEADER_CLIENT_ID = "Client-Id";
    private static final String HEADER_REQUEST_TIME = "Request-Time";

    private final WfSigner signer;

    public NotifyPayoutController(WfSigner signer) {
        this.signer = signer;
    }

    /**
     * 处理 WF notifyPayout 回调。
     *
     * @param headers HTTP 请求头
     * @param requestBody 原始请求体
     * @return 签名后的响应
     */
    @PostMapping("${wf.notify.payout.path:/notify/payout}")
    public ResponseEntity<String> handleNotification(
            @RequestHeader HttpHeaders headers,
            @RequestBody String requestBody) {

        LOGGER.info("NotifyPayoutController received notification");

        // Step 1: 验签
        String signature = extractSignatureValue(headers.getFirst(HEADER_SIGNATURE));
        String clientId = headers.getFirst(HEADER_CLIENT_ID);
        String requestTime = headers.getFirst(HEADER_REQUEST_TIME);

        if (signature == null || clientId == null || requestTime == null) {
            LOGGER.warn("NotifyPayoutController missing required headers");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required headers");
        }

        boolean verified = signer.verifySignature(NOTIFY_PATH, clientId, requestTime, requestBody);
        if (!verified) {
            LOGGER.warn("NotifyPayoutController signature verification failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signature verification failed");
        }

        // Step 2: 解析请求
        NotifyPayoutRequest request;
        try {
            request = JSON.parseObject(requestBody, NotifyPayoutRequest.class);
        } catch (Exception e) {
            LOGGER.error("NotifyPayoutController failed to parse request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body");
        }

        String transferRequestId = request.getTransferRequestId();
        String transferId = request.getTransferId();
        LOGGER.info("NotifyPayoutController processing transferRequestId={}, transferId={}",
            transferRequestId, transferId);

        // Step 3: 幂等判断 — 实际项目中需要持久化存储已处理的 transferRequestId
        // if (alreadyProcessed(transferRequestId)) {
        //     return buildSignedResponse(buildSuccessResponse());
        // }

        // Step 4: 处理转账结果
        TransferResult transferResult = request.getTransferResult();
        if (transferResult != null && transferResult.isSuccess()) {
            LOGGER.info("NotifyPayoutController transfer succeeded:"
                + " transferRequestId={}, transferId={}, chargeMode={}, finishTime={}",
                transferRequestId,
                transferId,
                request.getChargeMode(),
                request.getTransferFinishTime());

            // TODO: 集成商根据转账成功结果实现具体业务逻辑
            // 例如：更新本地订单状态为已完成、记录到账金额等
            // asyncProcessTransferSuccess(request);
        } else {
            LOGGER.warn("NotifyPayoutController transfer failed:"
                + " transferRequestId={}, transferId={}, resultCode={}, resultMessage={}",
                transferRequestId,
                transferId,
                transferResult != null ? transferResult.getResultCode() : "null",
                transferResult != null ? transferResult.getResultMessage() : "null");

            // TODO: 处理转账失败的情况
            // 例如：更新本地订单状态为失败、通知用户等
            // asyncProcessTransferFailure(request);
        }

        // Step 5: 构建成功响应
        NotifyPayoutResponse response = NotifyPayoutResponse.success();

        // Step 6: 签名响应
        String responseBody = JSON.toJSONString(response);
        String responseSignature = signer.generateSignature(NOTIFY_PATH, clientId, responseBody);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HEADER_SIGNATURE,
            "algorithm=RSA256, keyVersion=2, signature=" + responseSignature);

        return new ResponseEntity<>(responseBody, responseHeaders, HttpStatus.OK);
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
