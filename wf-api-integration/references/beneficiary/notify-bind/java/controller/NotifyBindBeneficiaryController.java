package {basePackage}.wf.controller;

import com.alibaba.fastjson.JSON;
import {basePackage}.wf.model.domain.NotifyBeneficiary;
import {basePackage}.wf.model.request.NotifyBindBeneficiaryRequest;
import {basePackage}.wf.model.response.NotifyBindBeneficiaryResponse;
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
 * WorldFirst notifyBindBeneficiary 回调处理器。
 *
 * <p>收款人绑定成功后，万里汇会主动 POST 到集成商提供的回调地址，
 * 将收款人绑定结果通知给集成商。
 *
 * <p>处理流程：
 * <ol>
 *   <li>验签：从请求头提取 Signature，调用 WfSigner.verifySignature() 验证</li>
 *   <li>幂等判断：以 bindBeneficiaryRequestId 去重，已处理直接返回 SUCCESS</li>
 *   <li>异步处理：业务逻辑异步执行，避免超时触发 WF 重试</li>
 *   <li>签名响应：对响应体签名后返回</li>
 * </ol>
 *
 * <p>WF 重试策略：未收到有效响应时重试 7 次，间隔 2min→10min→10min→1h→2h→6h→15h。
 */
@RestController
public class NotifyBindBeneficiaryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyBindBeneficiaryController.class);

    private static final String NOTIFY_PATH = "/amsin/api/v1/business/account/notifyBindBeneficiary";
    private static final String HEADER_SIGNATURE = "Signature";
    private static final String HEADER_CLIENT_ID = "Client-Id";
    private static final String HEADER_REQUEST_TIME = "Request-Time";

    private final WfSigner signer;

    public NotifyBindBeneficiaryController(WfSigner signer) {
        this.signer = signer;
    }

    /**
     * 处理 WF notifyBindBeneficiary 回调。
     *
     * @param headers HTTP 请求头
     * @param requestBody 原始请求体
     * @return 签名后的响应
     */
    @PostMapping("${wf.notify.bind-beneficiary.path:/notify/bind-beneficiary}")
    public ResponseEntity<String> handleNotification(
            @RequestHeader HttpHeaders headers,
            @RequestBody String requestBody) {

        LOGGER.info("NotifyBindBeneficiaryController received notification");

        // Step 1: 验签
        String signature = extractSignatureValue(headers.getFirst(HEADER_SIGNATURE));
        String clientId = headers.getFirst(HEADER_CLIENT_ID);
        String requestTime = headers.getFirst(HEADER_REQUEST_TIME);

        if (signature == null || clientId == null || requestTime == null) {
            LOGGER.warn("NotifyBindBeneficiaryController missing required headers");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required headers");
        }

        boolean verified = signer.verifySignature(NOTIFY_PATH, clientId, requestTime, requestBody);
        if (!verified) {
            LOGGER.warn("NotifyBindBeneficiaryController signature verification failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signature verification failed");
        }

        // Step 2: 解析请求
        NotifyBindBeneficiaryRequest request;
        try {
            request = JSON.parseObject(requestBody, NotifyBindBeneficiaryRequest.class);
        } catch (Exception e) {
            LOGGER.error("NotifyBindBeneficiaryController failed to parse request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body");
        }

        String bindBeneficiaryRequestId = request.getBindBeneficiaryRequestId();
        LOGGER.info("NotifyBindBeneficiaryController processing bindBeneficiaryRequestId={}", bindBeneficiaryRequestId);

        // Step 3: 幂等判断 — 实际项目中需要持久化存储已处理的 bindBeneficiaryRequestId
        // if (alreadyProcessed(bindBeneficiaryRequestId)) {
        //     return buildSignedResponse(buildSuccessResponse());
        // }

        // Step 4: 处理绑定结果
        Result bindResult = request.getResult();
        if (bindResult != null && "S".equals(bindResult.getResultStatus())) {
            NotifyBeneficiary beneficiary = request.getBeneficiary();
            if (beneficiary != null) {
                LOGGER.info("NotifyBindBeneficiaryController beneficiary bound successfully:"
                    + " token={}, status={}, type={}, referenceBeneficiaryId={}",
                    beneficiary.getBeneficiaryToken(),
                    beneficiary.getStatus(),
                    beneficiary.getBeneficiaryType(),
                    beneficiary.getReferenceBeneficiaryId());

                // TODO: 集成商根据绑定结果实现具体业务逻辑
                // 例如：更新本地收款人状态、存储 beneficiaryToken 等
                // asyncProcessBeneficiaryBinding(beneficiary);
            }
        } else {
            LOGGER.warn("NotifyBindBeneficiaryController beneficiary binding failed:"
                + " resultCode={}, resultMessage={}",
                bindResult != null ? bindResult.getResultCode() : "null",
                bindResult != null ? bindResult.getResultMessage() : "null");

            // TODO: 处理绑定失败的情况
        }

        // Step 5: 构建成功响应
        NotifyBindBeneficiaryResponse response = NotifyBindBeneficiaryResponse.success();

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
