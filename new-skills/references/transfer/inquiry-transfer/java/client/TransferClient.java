/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.client;

import com.alibaba.fastjson.JSON;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.exception.WfErrorCode;
import {basePackage}.wf.exception.WfException;
import {basePackage}.wf.model.request.InquiryTransferRequest;
import {basePackage}.wf.model.response.InquiryTransferResponse;
import {basePackage}.wf.model.response.Result;
import {basePackage}.wf.signer.WfSigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WorldFirst 转账查询客户端，封装 inquiryTransfer 接口。
 *
 * <p>用于查询转账结果。当 createTransfer 返回 PROCESSING 时，
 * 需调用此接口轮询最终状态。
 *
 * @author Qoder
 * @version TransferClient.java, v 0.1 2026-04-07
 */
public class TransferClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferClient.class);

    private static final String PATH_INQUIRY_TRANSFER = "/amsin/api/v1/business/fund/inquiryTransfer";

    private static final int MAX_TRANSFER_REQUEST_ID_LENGTH = 64;

    private final WfConfig config;
    private WfHttpClientUtil httpClientUtil;
    private WfSigner signer;

    public TransferClient(WfConfig config) {
        this.config = config;
    }

    public void init() {
        this.httpClientUtil = new WfHttpClientUtil(config);
        this.signer = httpClientUtil.getSigner();
    }

    /**
     * 测试专用：注入 WfHttpClientUtil（跳过 init()，同时同步 signer）。
     */
    public void setHttpClientUtil(WfHttpClientUtil httpClientUtil) {
        this.httpClientUtil = httpClientUtil;
        if (httpClientUtil != null) {
            this.signer = httpClientUtil.getSigner();
        }
    }

    // =========================================================================
    // inquiryTransfer
    // =========================================================================

    /**
     * 调用 WF inquiryTransfer 接口查询转账结果。
     *
     * <p>响应包含两层结果码：
     * <ul>
     *   <li>{@code result} — API 调用结果</li>
     *   <li>{@code transferResult} — 转账业务结果</li>
     * </ul>
     *
     * <p>当 {@code transferResult.resultCode=PROCESSING} 时，转账仍在处理中，需继续轮询。
     *
     * @param request 查询请求
     * @return 查询响应
     * @throws WfException 当 result.resultStatus 为 F 或 U 时抛出
     */
    public InquiryTransferResponse inquiryTransfer(InquiryTransferRequest request) {
        validateInquiryTransferRequest(request);
        String requestBody = JSON.toJSONString(request);
        String url = config.getBaseUrl() + PATH_INQUIRY_TRANSFER;
        LOGGER.info("TransferClient inquiryTransfer, transferRequestId={}", request.getTransferRequestId());
        String responseBody = httpClientUtil.sendPostRequest(url, PATH_INQUIRY_TRANSFER, requestBody);
        return parseInquiryTransferResponse(responseBody);
    }

    // =========================================================================
    // Validation
    // =========================================================================

    private void validateInquiryTransferRequest(InquiryTransferRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "InquiryTransferRequest must not be null");
        }
        String reqId = request.getTransferRequestId();
        if (reqId == null || reqId.trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "transferRequestId must not be blank");
        }
        if (reqId.length() > MAX_TRANSFER_REQUEST_ID_LENGTH) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferRequestId length must not exceed " + MAX_TRANSFER_REQUEST_ID_LENGTH);
        }
    }

    // =========================================================================
    // Response parsing
    // =========================================================================

    private InquiryTransferResponse parseInquiryTransferResponse(String responseBody) {
        InquiryTransferResponse response;
        try {
            response = JSON.parseObject(responseBody, InquiryTransferResponse.class);
        } catch (Exception e) {
            LOGGER.error("TransferClient failed to parse inquiryTransfer response, body={}", responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse inquiryTransfer response: " + e.getMessage(), e);
        }
        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT, "inquiryTransfer response result is null");
        }
        if ("S".equals(result.getResultStatus())) {
            LOGGER.info("TransferClient inquiryTransfer succeeded, transferId={}, transferResultCode={}",
                response.getTransferId(),
                response.getTransferResult() != null ? response.getTransferResult().getResultCode() : "N/A");
            return response;
        }
        WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
        LOGGER.warn("TransferClient inquiryTransfer failed, resultStatus={}, resultCode={}, resultMessage={}",
            result.getResultStatus(), result.getResultCode(), result.getResultMessage());
        throw new WfException(errorCode,
            "inquiryTransfer failed: " + result.getResultCode() + " - " + result.getResultMessage());
    }
}
