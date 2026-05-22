package {basePackage}.wf.controller;

import com.alibaba.fastjson.JSON;
import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;
import {basePackage}.wf.model.domain.TransferOrderAddition;
import {basePackage}.wf.model.request.NotifyTransferRequest;
import {basePackage}.wf.model.response.NotifyTransferResponse;
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
 * WorldFirst notifyTransfer 回调处理器。
 *
 * <p>转账完成后，万里汇会主动 POST 到集成商提供的回调地址（通过 createTransfer 时
 * 的 transferToDetail.transferNotifyUrl 设置），将转账结果通知给集成商。
 *
 * <p>处理流程：
 * <ol>
 *   <li>验签：从请求头提取 Signature，调用 WfSigner.verifySignature() 验证</li>
 *   <li>幂等判断：以 transferRequestId 去重，已处理直接返回 SUCCESS</li>
 *   <li>结果判断：根据 transferResult 判断转账成功/失败</li>
 *   <li>异步处理：业务逻辑异步执行，避免超时触发 WF 重试</li>
 *   <li>签名响应：对响应体签名后返回</li>
 * </ol>
 *
 * <p>WF 重试策略：未收到有效响应时重试 7 次，间隔 2min→10min→10min→1h→2h→6h→15h。
 *
 */
@RestController
public class NotifyTransferController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyTransferController.class);

    private static final String NOTIFY_PATH = "/amsin/api/v1/business/fund/notifyTransfer";
    private static final String HEADER_SIGNATURE = "Signature";
    private static final String HEADER_CLIENT_ID = "Client-Id";
    private static final String HEADER_REQUEST_TIME = "Request-Time";

    private final WfSigner signer;

    public NotifyTransferController(WfSigner signer) {
        this.signer = signer;
    }

    /**
     * 处理 WF notifyTransfer 回调。
     *
     * @param headers HTTP 请求头
     * @param requestBody 原始请求体
     * @return 签名后的响应
     */
    @PostMapping("${wf.notify.transfer.path:/notify/transfer}")
    public ResponseEntity<String> handleNotification(
            @RequestHeader HttpHeaders headers,
            @RequestBody String requestBody) {

        LOGGER.info("NotifyTransferController received notification");

        // Step 1: 验签
        String signature = extractSignatureValue(headers.getFirst(HEADER_SIGNATURE));
        String clientId = headers.getFirst(HEADER_CLIENT_ID);
        String requestTime = headers.getFirst(HEADER_REQUEST_TIME);

        if (signature == null || clientId == null || requestTime == null) {
            LOGGER.warn("NotifyTransferController missing required headers");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required headers");
        }

        boolean verified = signer.verifySignature(NOTIFY_PATH, clientId, requestTime, requestBody);
        if (!verified) {
            LOGGER.warn("NotifyTransferController signature verification failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signature verification failed");
        }

        // Step 2: 解析请求
        NotifyTransferRequest request;
        try {
            request = JSON.parseObject(requestBody, NotifyTransferRequest.class);
        } catch (Exception e) {
            LOGGER.error("NotifyTransferController failed to parse request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body");
        }

        String transferRequestId = request.getTransferRequestId();
        String transferId = request.getTransferId();
        LOGGER.info("NotifyTransferController processing transferRequestId={}, transferId={}",
            transferRequestId, transferId);

        // Step 3: 幂等判断 — 实际项目中需要持久化存储已处理的 transferRequestId
        // if (alreadyProcessed(transferRequestId)) {
        //     return buildSignedResponse(buildSuccessResponse());
        // }

        // Step 4: 处理转账结果
        Result transferResult = request.getTransferResult();
        if (transferResult != null && "S".equals(transferResult.getResultStatus())
                && "SUCCESS".equals(transferResult.getResultCode())) {
            // 转账成功
            LOGGER.info("NotifyTransferController transfer succeeded:"
                + " transferRequestId={}, transferId={}, transferFinishTime={}",
                transferRequestId, transferId, request.getTransferFinishTime());

            // 记录转账详情
            TransferFromDetail fromDetail = request.getTransferFromDetail();
            TransferToDetail toDetail = request.getTransferToDetail();
            if (fromDetail != null && fromDetail.getTransferFromAmount() != null) {
                LOGGER.info("NotifyTransferController transferFrom: currency={}, value={}",
                    fromDetail.getTransferFromAmount().getCurrency(),
                    fromDetail.getTransferFromAmount().getValue());
            }
            if (toDetail != null && toDetail.getTransferToAmount() != null) {
                LOGGER.info("NotifyTransferController transferTo: currency={}, value={}, purposeCode={}",
                    toDetail.getTransferToAmount().getCurrency(),
                    toDetail.getTransferToAmount().getValue(),
                    toDetail.getPurposeCode());
            }

            TransferOrderAddition addition = request.getTransferOrderAddition();
            if (addition != null) {
                LOGGER.info("NotifyTransferController transferOrderAddition: referenceOrderId={}",
                    addition.getReferenceOrderId());
            }

            // TODO: 集成商根据转账结果实现具体业务逻辑
            // 例如：更新本地转账订单状态、记录转账完成时间等
            // asyncProcessTransferSuccess(request);

        } else {
            // 转账失败
            LOGGER.warn("NotifyTransferController transfer failed:"
                + " transferRequestId={}, transferId={}, resultCode={}, resultMessage={}",
                transferRequestId, transferId,
                transferResult != null ? transferResult.getResultCode() : "null",
                transferResult != null ? transferResult.getResultMessage() : "null");

            // TODO: 处理转账失败的情况
            // asyncProcessTransferFailure(request);
        }

        // Step 5: 构建成功响应
        NotifyTransferResponse response = NotifyTransferResponse.success();

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
