/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.client.statement;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.InquiryStatementDetailRequest;
import {basePackage}.wf.model.response.InquiryStatementDetailResponse;
import {basePackage}.wf.model.response.Result;
import {basePackage}.wf.signer.WfSigner;
import {basePackage}.wf.util.WfHttpClientUtil;

/**
 * WorldFirst inquiryStatementDetail 接口客户端
 *
 * <p>查询指定账单流水的详细信息。需先调用 inquiryStatementList 获取 accountingBizNo，
 * 再以此为入参调用本接口。
 *
 * @author Qoder
 * @version InquiryStatementDetailClient.java, v 0.1 2026-04-08
 */
public class InquiryStatementDetailClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(InquiryStatementDetailClient.class);

    private static final String API_PATH = "/amsin/api/v1/business/account/inquiryStatementDetail";

    private static final String RESULT_STATUS_SUCCESS = "S";
    private static final String RESULT_STATUS_FAIL = "F";

    private WfConfig config;
    private WfHttpClientUtil httpClientUtil;
    private WfSigner signer;

    /**
     * 初始化方法，在 Spring Bean 创建后调用
     */
    public void init() {
        this.httpClientUtil = new WfHttpClientUtil(config);
        this.signer = httpClientUtil.getSigner();
    }

    /**
     * 查询账单流水详情
     *
     * @param request 查询请求，accountingBizNo 必填
     * @return 账单流水详情响应
     * @throws WfException 参数校验失败或调用失败时抛出
     */
    public InquiryStatementDetailResponse inquiryStatementDetail(InquiryStatementDetailRequest request) {
        validate(request);

        String requestBody = buildRequestBody(request);
        String url = config.getBaseUrl() + API_PATH;

        LOGGER.info("InquiryStatementDetailClient invoking inquiryStatementDetail, url=" + url
            + ", accountingBizNo=" + request.getAccountingBizNo());

        String responseBody = httpClientUtil.sendPostRequest(url, API_PATH, requestBody);

        return parseResponse(responseBody);
    }

    /**
     * 参数校验
     *
     * @param request 请求对象
     * @throws WfException 校验失败时抛出
     */
    private void validate(InquiryStatementDetailRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "InquiryStatementDetailRequest must not be null");
        }
        if (request.getAccountingBizNo() == null || request.getAccountingBizNo().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "accountingBizNo is required");
        }
    }

    /**
     * 构建请求体 JSON 字符串
     *
     * @param request 请求对象
     * @return JSON 字符串
     */
    private String buildRequestBody(InquiryStatementDetailRequest request) {
        JSONObject body = new JSONObject();
        body.put("accountingBizNo", request.getAccountingBizNo());
        return body.toJSONString();
    }

    /**
     * 解析响应体
     *
     * @param responseBody 响应体 JSON 字符串
     * @return 解析后的响应对象
     * @throws WfException 响应格式非法或业务失败时抛出
     */
    private InquiryStatementDetailResponse parseResponse(String responseBody) {
        InquiryStatementDetailResponse response;
        try {
            response = JSON.parseObject(responseBody, InquiryStatementDetailResponse.class);
        } catch (Exception e) {
            LOGGER.error("InquiryStatementDetailClient failed to parse response, body=" + responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse inquiryStatementDetail response: " + e.getMessage(), e);
        }

        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "inquiryStatementDetail response result is null");
        }

        String resultStatus = result.getResultStatus();
        if (RESULT_STATUS_SUCCESS.equals(resultStatus)) {
            LOGGER.info("InquiryStatementDetailClient inquiryStatementDetail success, responseId="
                + response.getResponseId()
                + ", transactionId=" + response.getTransactionId()
                + ", transactionStatus=" + response.getTransactionStatus());
            return response;
        } else if (RESULT_STATUS_FAIL.equals(resultStatus)) {
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryStatementDetailClient inquiryStatementDetail failed, resultCode="
                + result.getResultCode() + ", resultMessage=" + result.getResultMessage());
            throw new WfException(errorCode, result.getResultMessage());
        } else {
            // resultStatus=U 或未知状态，交由调用方重试
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryStatementDetailClient inquiryStatementDetail unknown/retryable status, resultStatus="
                + resultStatus + ", resultCode=" + result.getResultCode());
            throw new WfException(errorCode, result.getResultMessage());
        }
    }

    /**
     * 测试专用：注入 WfHttpClientUtil（跳过 init()，同时同步 signer）。
     *
     * @param httpClientUtil 外部构造的 WfHttpClientUtil 实例
     */
    public void setHttpClientUtil(WfHttpClientUtil httpClientUtil) {
        this.httpClientUtil = httpClientUtil;
        if (httpClientUtil != null) {
            this.signer = httpClientUtil.getSigner();
        }
    }

    /**
     * Setter method for property <tt>config</tt>.
     *
     * @param config value to be assigned to property config
     */
    public void setConfig(WfConfig config) {
        this.config = config;
    }

    /**
     * Getter method for property <tt>config</tt>.
     *
     * @return property value of config
     */
    public WfConfig getConfig() {
        return config;
    }
}
