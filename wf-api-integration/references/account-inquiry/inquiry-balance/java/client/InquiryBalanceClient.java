/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.client.balance;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.InquiryBalanceRequest;
import {basePackage}.wf.model.response.InquiryBalanceResponse;
import {basePackage}.wf.model.response.Result;
import {basePackage}.wf.signer.WfSigner;
import {basePackage}.wf.util.WfHttpClientUtil;

import java.util.List;

/**
 * WorldFirst inquiryBalance 接口客户端
 *
 * @author Qoder
 * @version InquiryBalanceClient.java, v 0.1 2026-03-24
 */
public class InquiryBalanceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(InquiryBalanceClient.class);

    private static final String API_PATH = "/amsin/api/v1/business/account/inquiryBalance";

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
     * 查询 WF 账户余额
     *
     * @param request 查询请求，所有字段均为可选
     * @return 账户余额响应
     * @throws WfException 调用失败时抛出
     */
    public InquiryBalanceResponse inquiryBalance(InquiryBalanceRequest request) {
        validate(request);

        String requestBody = buildRequestBody(request);
        String url = config.getBaseUrl() + API_PATH;

        LOGGER.info("InquiryBalanceClient invoking inquiryBalance, url=" + url);

        String responseBody = httpClientUtil.sendPostRequest(url, API_PATH, requestBody);

        return parseResponse(responseBody);
    }

    /**
     * 参数校验
     *
     * @param request 请求对象
     * @throws WfException 校验失败时抛出
     */
    private void validate(InquiryBalanceRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "InquiryBalanceRequest must not be null");
        }
        List<String> balanceTypes = request.getBalanceTypes();
        if (balanceTypes != null && balanceTypes.contains("BUDGET_BALANCE")) {
            if (request.getBudgetAccountId() == null || request.getBudgetAccountId().trim().isEmpty()) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "budgetAccountId is required when balanceTypes contains BUDGET_BALANCE");
            }
        }
    }

    /**
     * 构建请求体 JSON 字符串，仅包含非空字段
     *
     * @param request 请求对象
     * @return JSON 字符串
     */
    private String buildRequestBody(InquiryBalanceRequest request) {
        JSONObject body = new JSONObject();
        if (request.getCurrencyList() != null && !request.getCurrencyList().isEmpty()) {
            body.put("currencyList", request.getCurrencyList());
        }
        if (request.getBalanceTypes() != null && !request.getBalanceTypes().isEmpty()) {
            body.put("balanceTypes", request.getBalanceTypes());
        }
        if (request.getBudgetAccountId() != null && !request.getBudgetAccountId().trim().isEmpty()) {
            body.put("budgetAccountId", request.getBudgetAccountId());
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
    private InquiryBalanceResponse parseResponse(String responseBody) {
        InquiryBalanceResponse response;
        try {
            response = JSON.parseObject(responseBody, InquiryBalanceResponse.class);
        } catch (Exception e) {
            LOGGER.error("InquiryBalanceClient failed to parse response, body=" + responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse inquiryBalance response: " + e.getMessage(), e);
        }

        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "inquiryBalance response result is null");
        }

        String resultStatus = result.getResultStatus();
        if (RESULT_STATUS_SUCCESS.equals(resultStatus)) {
            LOGGER.info("InquiryBalanceClient inquiryBalance success, responseId=" + response.getResponseId());
            return response;
        } else if (RESULT_STATUS_FAIL.equals(resultStatus)) {
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryBalanceClient inquiryBalance failed, resultCode=" + result.getResultCode()
                + ", resultMessage=" + result.getResultMessage());
            throw new WfException(errorCode, result.getResultMessage());
        } else {
            // resultStatus=U 或未知状态，交由调用方重试
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryBalanceClient inquiryBalance unknown/retryable status, resultStatus="
                + resultStatus + ", resultCode=" + result.getResultCode());
            throw new WfException(errorCode, result.getResultMessage());
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
