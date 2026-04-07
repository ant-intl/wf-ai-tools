/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.controller;

import com.alibaba.fastjson.JSON;
import {basePackage}.wf.model.request.NotifyTradeOrderRequest;
import {basePackage}.wf.model.response.NotifyTradeOrderResponse;
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
 * WorldFirst notifyTradeOrder 回调处理器（仅 PAY_INTO_CHINA 场景）。
 *
 * <p>WF 在交易订单处理完成后，主动 POST 到集成商提供的 notifyUrl。
 *
 * <p>处理流程：
 * <ol>
 *   <li>验签：从请求头提取 Signature，调用 WfSigner.verifySignature() 验证</li>
 *   <li>幂等判断：以 requestId 去重，已处理直接返回 SUCCESS</li>
 *   <li>异步处理：业务逻辑异步执行，避免超时触发 WF 重试</li>
 *   <li>签名响应：对响应体签名后返回</li>
 * </ol>
 *
 * <p>WF 重试策略：未收到有效响应时重试 7 次，间隔 2min→10min→10min→1h→2h→6h→15h。
 *
 * @author Qoder
 * @version NotifyTradeOrderController.java, v 0.1 2026-04-03
 */
@RestController
public class NotifyTradeOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyTradeOrderController.class);

    private static final String NOTIFY_PATH = "/amsin/api/v1/business/account/notifyTradeOrder";
    private static final String HEADER_SIGNATURE = "Signature";
    private static final String HEADER_CLIENT_ID = "Client-Id";
    private static final String HEADER_REQUEST_TIME = "Request-Time";

    private final WfSigner signer;

    public NotifyTradeOrderController(WfSigner signer) {
        this.signer = signer;
    }

    /**
     * 处理 WF notifyTradeOrder 回调。
     *
     * @param headers HTTP 请求头
     * @param requestBody 原始请求体
     * @return 签名后的响应
     */
    @PostMapping("${wf.notify.trade-order.path:/notify/trade-order}")
    public ResponseEntity<String> handleNotification(
            @RequestHeader HttpHeaders headers,
            @RequestBody String requestBody) {

        LOGGER.info("NotifyTradeOrderController received notification");

        // Step 1: 验签
        String signature = extractSignatureValue(headers.getFirst(HEADER_SIGNATURE));
        String clientId = headers.getFirst(HEADER_CLIENT_ID);
        String requestTime = headers.getFirst(HEADER_REQUEST_TIME);

        if (signature == null || clientId == null || requestTime == null) {
            LOGGER.warn("NotifyTradeOrderController missing required headers");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required headers");
        }

        String signContent = "POST " + NOTIFY_PATH + "\n" + clientId + "." + requestTime + "." + requestBody;
        boolean verified = signer.verifySignature(NOTIFY_PATH, clientId, requestTime, requestBody);
        if (!verified) {
            LOGGER.warn("NotifyTradeOrderController signature verification failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signature verification failed");
        }

        // Step 2: 解析请求
        NotifyTradeOrderRequest request;
        try {
            request = JSON.parseObject(requestBody, NotifyTradeOrderRequest.class);
        } catch (Exception e) {
            LOGGER.error("NotifyTradeOrderController failed to parse request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body");
        }

        String requestId = request.getRequestId();
        LOGGER.info("NotifyTradeOrderController processing requestId={}", requestId);

        // Step 3: 幂等判断 — 实际项目中需要持久化存储已处理的 requestId
        // if (alreadyProcessed(requestId)) {
        //     return buildSignedResponse(buildSuccessResponse());
        // }

        // Step 4: 异步处理业务逻辑 — 避免阻塞导致 WF 超时重试
        // asyncProcessTradeOrderResults(request.getTradeOrderResults());

        // Step 5: 构建成功响应
        NotifyTradeOrderResponse response = new NotifyTradeOrderResponse();
        Result result = new Result();
        result.setResultCode("SUCCESS");
        result.setResultStatus("S");
        result.setResultMessage("success");
        response.setResult(result);

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
