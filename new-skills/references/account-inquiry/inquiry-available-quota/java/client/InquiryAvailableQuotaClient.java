/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.client.account;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.InquiryAvailableQuotaRequest;
import {basePackage}.wf.model.response.InquiryAvailableQuotaResponse;
import {basePackage}.wf.model.response.Result;
import {basePackage}.wf.signer.WfSigner;
import {basePackage}.wf.util.WfHttpClientUtil;

/**
 * WorldFirst inquiryAvailableQuota 接口客户端
 *
 * <p>用于查询可申报的结汇额度
 *
 * @author Qoder
 * @version InquiryAvailableQuotaClient.java, v 0.1 2026-04-08
 */
public class InquiryAvailableQuotaClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(InquiryAvailableQuotaClient.class);

    private static final String API_PATH = "/amsin/api/v1/business/account/inquiryAvailableQuota";

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
     * 允许外部注入 {@link WfHttpClientUtil}，适用于测试场景。
     *
     * @param httpClientUtil HTTP 客户端工具
     */
    public void setHttpClientUtil(WfHttpClientUtil httpClientUtil) {
        this.httpClientUtil = httpClientUtil;
        if (httpClientUtil != null) {
            this.signer = httpClientUtil.getSigner();
        }
    }

    /**
     * 查询可申报的结汇额度
     *
     * <p>支持四种累计方式：
     * <ul>
     *   <li>USER_ID - 按用户ID累计</li>
     *   <li>RECEIVING_ACCOUNT - 按收款账户累计</li>
     *   <li>VIRTUAL_ACCOUNT - 按虚拟账户累计</li>
     *   <li>BENEFICIARY - 按收款人累计（需传tradeType）</li>
     * </ul>
     *
     * @param request 查询请求
     * @return 可申报的结汇额度响应
     * @throws WfException 调用失败时抛出
     */
    public InquiryAvailableQuotaResponse inquiryAvailableQuota(InquiryAvailableQuotaRequest request) {
        validate(request);

        String requestBody = buildRequestBody(request);
        String url = config.getBaseUrl() + API_PATH;

        LOGGER.info("InquiryAvailableQuotaClient invoking inquiryAvailableQuota, url=" + url);

        String responseBody = httpClientUtil.sendPostRequest(url, API_PATH, requestBody);

        return parseResponse(responseBody);
    }

    /**
     * 参数校验
     *
     * @param request 请求对象
     * @throws WfException 校验失败时抛出
     */
    private void validate(InquiryAvailableQuotaRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "InquiryAvailableQuotaRequest must not be null");
        }
        if (request.getQuotaAccumulationMethod() == null || request.getQuotaAccumulationMethod().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "quotaAccumulationMethod must not be blank");
        }
        if (request.getQuotaAccumulationId() == null || request.getQuotaAccumulationId().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "quotaAccumulationId must not be blank");
        }
        if (request.getCurrency() == null || request.getCurrency().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "currency must not be blank");
        }
        // BENEFICIARY 方式时必须传 tradeType
        if ("BENEFICIARY".equals(request.getQuotaAccumulationMethod())) {
            if (request.getTradeType() == null || request.getTradeType().trim().isEmpty()) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "tradeType is required when quotaAccumulationMethod is BENEFICIARY");
            }
        }
    }

    /**
     * 构建请求体 JSON 字符串
     *
     * @param request 请求对象
     * @return JSON 字符串
     */
    private String buildRequestBody(InquiryAvailableQuotaRequest request) {
        JSONObject body = new JSONObject();
        body.put("quotaAccumulationMethod", request.getQuotaAccumulationMethod());
        body.put("quotaAccumulationId", request.getQuotaAccumulationId());
        body.put("currency", request.getCurrency());
        if (request.getTradeType() != null && !request.getTradeType().trim().isEmpty()) {
            body.put("tradeType", request.getTradeType());
        }
        return body.toJSONString();
    }

    /**
     * 解析响应体
     *
     * @param responseBody 响应体 JSON 字符串
     * @return 解析后的响应对象
     * @throws WfException 响应格式非法或业务失败时抛出
     */
    private InquiryAvailableQuotaResponse parseResponse(String responseBody) {
        InquiryAvailableQuotaResponse response;
        try {
            response = JSON.parseObject(responseBody, InquiryAvailableQuotaResponse.class);
        } catch (Exception e) {
            LOGGER.error("InquiryAvailableQuotaClient failed to parse response, body=" + responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse inquiryAvailableQuota response: " + e.getMessage(), e);
        }

        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "inquiryAvailableQuota response result is null");
        }

        String resultStatus = result.getResultStatus();
        if (RESULT_STATUS_SUCCESS.equals(resultStatus)) {
            LOGGER.info("InquiryAvailableQuotaClient inquiryAvailableQuota success, availableQuota="
                + response.getAvailableQuota());
            return response;
        } else if (RESULT_STATUS_FAIL.equals(resultStatus)) {
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryAvailableQuotaClient inquiryAvailableQuota failed, resultCode="
                + result.getResultCode() + ", resultMessage=" + result.getResultMessage());
            throw new WfException(errorCode, result.getResultMessage());
        } else {
            // resultStatus=U 或未知状态，交由调用方重试
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryAvailableQuotaClient inquiryAvailableQuota unknown/retryable status, resultStatus="
                + resultStatus + ", resultCode=" + result.getResultCode());
            throw new WfException(errorCode, result.getResultMessage());
        }
    }

    public void setConfig(WfConfig config) {
        this.config = config;
    }

    public WfConfig getConfig() {
        return config;
    }
}
