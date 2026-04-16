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
import {basePackage}.wf.model.request.InquiryAccountRequest;
import {basePackage}.wf.model.request.InquiryBalanceRequest;
import {basePackage}.wf.model.request.InquiryAvailableQuotaRequest;
import {basePackage}.wf.model.response.InquiryAccountResponse;
import {basePackage}.wf.model.response.InquiryBalanceResponse;
import {basePackage}.wf.model.response.InquiryAvailableQuotaResponse;
import {basePackage}.wf.model.response.Result;
import {basePackage}.wf.signer.WfSigner;
import {basePackage}.wf.util.WfHttpClientUtil;

import java.util.Arrays;
import java.util.List;

/**
 * WorldFirst 账户信息查询统一客户端。
 *
 * <p>包含 3 个接口方法：
 * <ul>
 *   <li>{@link #inquiryAccount} — 查询账户信息</li>
 *   <li>{@link #inquiryBalance} — 查询账户余额</li>
 *   <li>{@link #inquiryAvailableQuota} — 查询可申报结汇额度</li>
 * </ul>
 *
 * @author Qoder
 * @version InquiryAccountInfoClient.java, v 0.1 2026-04-14
 */
public class InquiryAccountInfoClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(InquiryAccountInfoClient.class);

    // API paths
    private static final String PATH_INQUIRY_ACCOUNT = "/amsin/api/v1/business/account/inquiryAccount";
    private static final String PATH_BALANCE         = "/amsin/api/v1/business/account/inquiryBalance";
    private static final String PATH_QUOTA           = "/amsin/api/v1/business/account/inquiryAvailableQuota";

    private static final String RESULT_STATUS_SUCCESS = "S";
    private static final String RESULT_STATUS_FAIL = "F";

    /** inquiryAccount 接口 accountType 有效值 */
    private static final List<String> VALID_ACCOUNT_TYPES = Arrays.asList(
        "RECEIVE_ACCOUNT", "VIRTUAL_ACCOUNT", "ALIPAY_WALLET",
        "ALIPAY_SHADOW_WALLET", "ALIPAY_ORIGIN_WALLET"
    );

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

    // ==================== inquiryAccount ====================

    /**
     * 查询 WF 账户信息
     *
     * <p>支持 5 种 accountType：
     * <ul>
     *   <li>RECEIVE_ACCOUNT — 万里汇收款账户（需传 referenceCustomerId）</li>
     *   <li>VIRTUAL_ACCOUNT — 万里汇虚拟账户（需传 accessToken）</li>
     *   <li>ALIPAY_WALLET — 支付宝钱包（需传 referenceCustomerId）</li>
     *   <li>ALIPAY_SHADOW_WALLET — 关联公司支付宝钱包（需传 accountId）</li>
     *   <li>ALIPAY_ORIGIN_WALLET — 企业支付宝钱包</li>
     * </ul>
     *
     * @param request 查询请求
     * @return 账户信息响应
     * @throws WfException 调用失败时抛出
     */
    public InquiryAccountResponse inquiryAccount(InquiryAccountRequest request) {
        validateAccountRequest(request);

        String requestBody = buildAccountRequestBody(request);
        String url = config.getBaseUrl() + PATH_INQUIRY_ACCOUNT;

        LOGGER.info("InquiryAccountInfoClient invoking inquiryAccount, url=" + url);

        String responseBody = httpClientUtil.sendPostRequest(url, PATH_INQUIRY_ACCOUNT, requestBody);

        return parseAccountResponse(responseBody);
    }

    /**
     * 校验账户查询请求参数
     *
     * @param request 请求对象
     * @throws WfException 校验失败时抛出
     */
    private void validateAccountRequest(InquiryAccountRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "InquiryAccountRequest must not be null");
        }

        String accountType = request.getAccountType();
        if (accountType == null || accountType.trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "accountType must not be blank");
        }
        if (!VALID_ACCOUNT_TYPES.contains(accountType)) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "accountType must be one of: " + VALID_ACCOUNT_TYPES);
        }

        // RECEIVE_ACCOUNT / ALIPAY_WALLET 时 referenceCustomerId 必填
        if ("RECEIVE_ACCOUNT".equals(accountType) || "ALIPAY_WALLET".equals(accountType)) {
            if (isBlank(request.getReferenceCustomerId())) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "referenceCustomerId is required when accountType is " + accountType);
            }
        }

        // VIRTUAL_ACCOUNT 时 accessToken 必填
        if ("VIRTUAL_ACCOUNT".equals(accountType)) {
            if (isBlank(request.getAccessToken())) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "accessToken is required when accountType is VIRTUAL_ACCOUNT");
            }
        }

        // ALIPAY_SHADOW_WALLET 时 accountId 必填
        if ("ALIPAY_SHADOW_WALLET".equals(accountType)) {
            if (isBlank(request.getAccountId())) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "accountId is required when accountType is ALIPAY_SHADOW_WALLET");
            }
        }
    }

    /**
     * 构建账户查询请求体 JSON 字符串，仅包含非空字段
     *
     * @param request 请求对象
     * @return JSON 字符串
     */
    private String buildAccountRequestBody(InquiryAccountRequest request) {
        JSONObject body = new JSONObject();
        body.put("accountType", request.getAccountType());

        if (!isBlank(request.getReferenceCustomerId())) {
            body.put("referenceCustomerId", request.getReferenceCustomerId());
        }
        if (!isBlank(request.getAccountId())) {
            body.put("accountId", request.getAccountId());
        }
        if (!isBlank(request.getAccessToken())) {
            body.put("accessToken", request.getAccessToken());
        }
        return body.toJSONString();
    }

    /**
     * 解析账户查询响应体
     *
     * @param responseBody 响应体 JSON 字符串
     * @return 解析后的响应对象
     * @throws WfException 响应格式非法或业务失败时抛出
     */
    private InquiryAccountResponse parseAccountResponse(String responseBody) {
        InquiryAccountResponse response;
        try {
            response = JSON.parseObject(responseBody, InquiryAccountResponse.class);
        } catch (Exception e) {
            LOGGER.error("InquiryAccountInfoClient failed to parse inquiryAccount response, body=" + responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse inquiryAccount response: " + e.getMessage(), e);
        }

        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "inquiryAccount response result is null");
        }

        String resultStatus = result.getResultStatus();
        if (RESULT_STATUS_SUCCESS.equals(resultStatus)) {
            LOGGER.info("InquiryAccountInfoClient inquiryAccount success, responseId=" + response.getResponseId());
            return response;
        } else if (RESULT_STATUS_FAIL.equals(resultStatus)) {
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryAccountInfoClient inquiryAccount failed, resultCode=" + result.getResultCode()
                + ", resultMessage=" + result.getResultMessage());
            throw new WfException(errorCode, result.getResultMessage());
        } else {
            // resultStatus=U 或未知状态，交由调用方重试
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryAccountInfoClient inquiryAccount unknown/retryable status, resultStatus="
                + resultStatus + ", resultCode=" + result.getResultCode());
            throw new WfException(errorCode, result.getResultMessage());
        }
    }

    // ==================== inquiryBalance ====================

    /**
     * 查询 WF 账户余额
     *
     * @param request 查询请求，所有字段均为可选
     * @return 账户余额响应
     * @throws WfException 调用失败时抛出
     */
    public InquiryBalanceResponse inquiryBalance(InquiryBalanceRequest request) {
        validateBalanceRequest(request);

        String requestBody = buildBalanceRequestBody(request);
        String url = config.getBaseUrl() + PATH_BALANCE;

        LOGGER.info("InquiryAccountInfoClient invoking inquiryBalance, url=" + url);

        String responseBody = httpClientUtil.sendPostRequest(url, PATH_BALANCE, requestBody);

        return parseBalanceResponse(responseBody);
    }

    /**
     * 校验余额查询请求参数
     *
     * @param request 请求对象
     * @throws WfException 校验失败时抛出
     */
    private void validateBalanceRequest(InquiryBalanceRequest request) {
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
     * 构建余额查询请求体 JSON 字符串，仅包含非空字段
     *
     * @param request 请求对象
     * @return JSON 字符串
     */
    private String buildBalanceRequestBody(InquiryBalanceRequest request) {
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
     * 解析余额查询响应体
     *
     * @param responseBody 响应体 JSON 字符串
     * @return 解析后的响应对象
     * @throws WfException 响应格式非法或业务失败时抛出
     */
    private InquiryBalanceResponse parseBalanceResponse(String responseBody) {
        InquiryBalanceResponse response;
        try {
            response = JSON.parseObject(responseBody, InquiryBalanceResponse.class);
        } catch (Exception e) {
            LOGGER.error("InquiryAccountInfoClient failed to parse inquiryBalance response, body=" + responseBody, e);
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
            LOGGER.info("InquiryAccountInfoClient inquiryBalance success, responseId=" + response.getResponseId());
            return response;
        } else if (RESULT_STATUS_FAIL.equals(resultStatus)) {
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryAccountInfoClient inquiryBalance failed, resultCode=" + result.getResultCode()
                + ", resultMessage=" + result.getResultMessage());
            throw new WfException(errorCode, result.getResultMessage());
        } else {
            // resultStatus=U 或未知状态，交由调用方重试
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryAccountInfoClient inquiryBalance unknown/retryable status, resultStatus="
                + resultStatus + ", resultCode=" + result.getResultCode());
            throw new WfException(errorCode, result.getResultMessage());
        }
    }

    // ==================== inquiryAvailableQuota ====================

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
        validateQuotaRequest(request);

        String requestBody = buildQuotaRequestBody(request);
        String url = config.getBaseUrl() + PATH_QUOTA;

        LOGGER.info("InquiryAccountInfoClient invoking inquiryAvailableQuota, url=" + url);

        String responseBody = httpClientUtil.sendPostRequest(url, PATH_QUOTA, requestBody);

        return parseQuotaResponse(responseBody);
    }

    /**
     * 校验额度查询请求参数
     *
     * @param request 请求对象
     * @throws WfException 校验失败时抛出
     */
    private void validateQuotaRequest(InquiryAvailableQuotaRequest request) {
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
     * 构建额度查询请求体 JSON 字符串
     *
     * @param request 请求对象
     * @return JSON 字符串
     */
    private String buildQuotaRequestBody(InquiryAvailableQuotaRequest request) {
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
     * 解析额度查询响应体
     *
     * @param responseBody 响应体 JSON 字符串
     * @return 解析后的响应对象
     * @throws WfException 响应格式非法或业务失败时抛出
     */
    private InquiryAvailableQuotaResponse parseQuotaResponse(String responseBody) {
        InquiryAvailableQuotaResponse response;
        try {
            response = JSON.parseObject(responseBody, InquiryAvailableQuotaResponse.class);
        } catch (Exception e) {
            LOGGER.error("InquiryAccountInfoClient failed to parse inquiryAvailableQuota response, body=" + responseBody, e);
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
            LOGGER.info("InquiryAccountInfoClient inquiryAvailableQuota success, availableQuota="
                + response.getAvailableQuota());
            return response;
        } else if (RESULT_STATUS_FAIL.equals(resultStatus)) {
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryAccountInfoClient inquiryAvailableQuota failed, resultCode="
                + result.getResultCode() + ", resultMessage=" + result.getResultMessage());
            throw new WfException(errorCode, result.getResultMessage());
        } else {
            // resultStatus=U 或未知状态，交由调用方重试
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryAccountInfoClient inquiryAvailableQuota unknown/retryable status, resultStatus="
                + resultStatus + ", resultCode=" + result.getResultCode());
            throw new WfException(errorCode, result.getResultMessage());
        }
    }

    // ==================== 工具方法 ====================

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    // ==================== Getter/Setter ====================

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
