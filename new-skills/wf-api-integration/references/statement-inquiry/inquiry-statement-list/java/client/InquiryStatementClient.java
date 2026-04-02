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
import {basePackage}.wf.model.request.InquiryStatementRequest;
import {basePackage}.wf.model.response.InquiryStatementResponse;
import {basePackage}.wf.model.response.Result;
import {basePackage}.wf.signer.WfSigner;
import {basePackage}.wf.util.WfHttpClientUtil;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * WorldFirst inquiryStatementList 接口客户端
 *
 * <p>pageSize 固定为 10，pageNumber 范围 1-50。
 * 当 fuzzyName 为空时，startTime 与 endTime 的间隔不超过 100 天。
 *
 * @author Qoder
 * @version InquiryStatementClient.java, v 0.1 2026-03-24
 */
public class InquiryStatementClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(InquiryStatementClient.class);

    private static final String API_PATH = "/amsin/api/v1/business/account/inquiryStatementList";

    /** pageSize 固定值 */
    private static final int FIXED_PAGE_SIZE = 10;

    /** pageNumber 最大值 */
    private static final int MAX_PAGE_NUMBER = 50;

    /** 时间范围最大天数（fuzzyName 为空时生效） */
    private static final int MAX_TIME_RANGE_DAYS = 100;

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
     * 查询 WF 账户流水列表
     *
     * @param request 查询请求，startTime、endTime、pageNumber 必填
     * @return 账单流水响应
     * @throws WfException 参数校验失败或调用失败时抛出
     */
    public InquiryStatementResponse inquiryStatementList(InquiryStatementRequest request) {
        validate(request);

        // 强制覆盖 pageSize 为固定值 10
        request.setPageSize(FIXED_PAGE_SIZE);

        // 设置 pageNumber 默认值
        if (request.getPageNumber() == null) {
            request.setPageNumber(1);
        }

        String requestBody = buildRequestBody(request);
        String url = config.getBaseUrl() + API_PATH;

        LOGGER.info("InquiryStatementClient invoking inquiryStatementList, url=" + url
            + ", pageNumber=" + request.getPageNumber());

        String responseBody = httpClientUtil.sendPostRequest(url, API_PATH, requestBody);

        return parseResponse(responseBody);
    }

    /**
     * 参数校验
     *
     * @param request 请求对象
     * @throws WfException 校验失败时抛出
     */
    private void validate(InquiryStatementRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "InquiryStatementRequest must not be null");
        }
        if (request.getStartTime() == null || request.getStartTime().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "startTime is required");
        }
        if (request.getEndTime() == null || request.getEndTime().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "endTime is required");
        }

        // pageNumber 校验
        Integer pageNumber = request.getPageNumber();
        if (pageNumber != null && (pageNumber < 1 || pageNumber > MAX_PAGE_NUMBER)) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "pageNumber must be between 1 and " + MAX_PAGE_NUMBER + ", actual: " + pageNumber);
        }

        // 当 fuzzyName 为空时，校验时间范围不超过 100 天
        boolean hasFuzzyName = request.getFuzzyName() != null && !request.getFuzzyName().trim().isEmpty();
        if (!hasFuzzyName) {
            validateTimeRange(request.getStartTime(), request.getEndTime());
        }
    }

    /**
     * 校验时间范围不超过 MAX_TIME_RANGE_DAYS 天
     *
     * @param startTime 起始时间字符串
     * @param endTime   结束时间字符串
     * @throws WfException 时间格式非法或超范围时抛出
     */
    private void validateTimeRange(String startTime, String endTime) {
        try {
            ZonedDateTime start = parseDateTime(startTime);
            ZonedDateTime end = parseDateTime(endTime);
            long days = Duration.between(start, end).toDays();
            if (days > MAX_TIME_RANGE_DAYS || days < 0) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "Time range must not exceed " + MAX_TIME_RANGE_DAYS + " days when fuzzyName is not set. "
                        + "Actual days: " + days + ". Set fuzzyName to bypass this limit.");
            }
        } catch (WfException e) {
            throw e;
        } catch (Exception e) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "Invalid time format, expected ISO 8601, e.g. 2024-01-01T00:00:00+08:00. Error: " + e.getMessage());
        }
    }

    /**
     * 解析时间字符串，支持多种 ISO 8601 格式
     *
     * @param timeStr 时间字符串
     * @return ZonedDateTime
     */
    private ZonedDateTime parseDateTime(String timeStr) {
        // 尝试标准 ISO 8601 带时区格式
        try {
            return ZonedDateTime.parse(timeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeParseException e) {
            // 兼容无时区或其他格式，按 Asia/Shanghai 处理
            try {
                return ZonedDateTime.parse(timeStr.replace(" ", "T") + "+08:00",
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            } catch (DateTimeParseException ex) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "Cannot parse time: " + timeStr + ". Please use ISO 8601 format.");
            }
        }
    }

    /**
     * 构建请求体 JSON 字符串，仅包含非空字段
     *
     * @param request 请求对象
     * @return JSON 字符串
     */
    private String buildRequestBody(InquiryStatementRequest request) {
        JSONObject body = new JSONObject();
        body.put("startTime", request.getStartTime());
        body.put("endTime", request.getEndTime());
        body.put("pageSize", FIXED_PAGE_SIZE);
        body.put("pageNumber", request.getPageNumber());

        if (request.getTransactionTypeList() != null && !request.getTransactionTypeList().isEmpty()) {
            body.put("transactionTypeList", request.getTransactionTypeList());
        }
        if (request.getCurrencyList() != null && !request.getCurrencyList().isEmpty()) {
            body.put("currencyList", request.getCurrencyList());
        }
        if (request.getBalanceTypes() != null && !request.getBalanceTypes().isEmpty()) {
            body.put("balanceTypes", request.getBalanceTypes());
        }
        if (request.getBudgetAccountIds() != null && !request.getBudgetAccountIds().isEmpty()) {
            body.put("budgetAccountIds", request.getBudgetAccountIds());
        }
        if (request.getFuzzyName() != null && !request.getFuzzyName().trim().isEmpty()) {
            body.put("fuzzyName", request.getFuzzyName());
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
    private InquiryStatementResponse parseResponse(String responseBody) {
        InquiryStatementResponse response;
        try {
            response = JSON.parseObject(responseBody, InquiryStatementResponse.class);
        } catch (Exception e) {
            LOGGER.error("InquiryStatementClient failed to parse response, body=" + responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse inquiryStatementList response: " + e.getMessage(), e);
        }

        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "inquiryStatementList response result is null");
        }

        String resultStatus = result.getResultStatus();
        if (RESULT_STATUS_SUCCESS.equals(resultStatus)) {
            LOGGER.info("InquiryStatementClient inquiryStatementList success, responseId=" + response.getResponseId()
                + ", totalCount=" + response.getTotalCount());
            return response;
        } else if (RESULT_STATUS_FAIL.equals(resultStatus)) {
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryStatementClient inquiryStatementList failed, resultCode=" + result.getResultCode()
                + ", resultMessage=" + result.getResultMessage());
            throw new WfException(errorCode, result.getResultMessage());
        } else {
            // resultStatus=U 或未知状态，交由调用方重试
            WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
            LOGGER.warn("InquiryStatementClient inquiryStatementList unknown/retryable status, resultStatus="
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
