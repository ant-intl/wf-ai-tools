/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.client;

import java.util.List;

import com.alibaba.fastjson.JSON;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.exception.WfErrorCode;
import {basePackage}.wf.exception.WfException;
import {basePackage}.wf.model.domain.TradeOrder;
import {basePackage}.wf.model.request.InquiryTradeOrderRequest;
import {basePackage}.wf.model.request.SubmitTradeOrderRequest;
import {basePackage}.wf.model.response.InquiryTradeOrderResponse;
import {basePackage}.wf.model.response.Result;
import {basePackage}.wf.model.response.SubmitTradeOrderResponse;
import {basePackage}.wf.signer.WfSigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WorldFirst 交易订单管理客户端，统一封装 submitTradeOrder 和 inquiryTradeOrder 两个接口。
 *
 * <p>交易订单申报流程：
 * <ol>
 *   <li>调用 {@link #submitTradeOrder} 提交交易订单（B2C 或 B2B）</li>
 *   <li>若 PAY_INTO_CHINA 场景返回 PROCESSING，调用 {@link #inquiryTradeOrder} 轮询最终结果</li>
 *   <li>或通过 notifyUrl 接收 WF 回调通知（NotifyTradeOrderController）</li>
 * </ol>
 *
 * @author Qoder
 * @version TradeOrderManagementClient.java, v 0.1 2026-04-03
 */
public class TradeOrderManagementClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeOrderManagementClient.class);

    private static final String PATH_SUBMIT_TRADE_ORDER = "/amsin/api/v1/business/account/submitTradeOrder";
    private static final String PATH_INQUIRY_TRADE_ORDER = "/amsin/api/v1/business/account/inquiryTradeOrder";

    /** PAY_INTO_CHINA 场景码 — B2C 跨境电商收款入境 */
    private static final String SCENE_PAY_INTO_CHINA = "PAY_INTO_CHINA";

    /** CREATE_B2B_ORDERS 场景码 — B2B 外贸订单创建 */
    private static final String SCENE_CREATE_B2B_ORDERS = "CREATE_B2B_ORDERS";

    /** PAY_INTO_CHINA（B2C）场景最大订单数 */
    private static final int MAX_B2C_ORDERS = 100;

    /** CREATE_B2B_ORDERS（B2B）场景最大订单数 */
    private static final int MAX_B2B_ORDERS = 10;

    /** requestId 最大长度 */
    private static final int MAX_REQUEST_ID_LENGTH = 64;

    private final WfConfig config;
    private WfHttpClientUtil httpClientUtil;
    private WfSigner signer;

    public TradeOrderManagementClient(WfConfig config) {
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
    // submitTradeOrder
    // =========================================================================

    /**
     * 提交交易订单。
     *
     * <p>根据 sceneCode 支持两种场景：
     * <ul>
     *   <li>PAY_INTO_CHINA — B2C 跨境电商，最多 100 笔订单，platform 必填</li>
     *   <li>CREATE_B2B_ORDERS — B2B 外贸，最多 10 笔订单</li>
     * </ul>
     *
     * @param request 提交请求，包含 tradeOrders 列表和场景信息
     * @return 提交响应，PAY_INTO_CHINA 返回 tradeOrderResult，CREATE_B2B_ORDERS 返回 acceptOrderId
     * @throws WfException 参数校验失败或调用失败时抛出异常
     */
    public SubmitTradeOrderResponse submitTradeOrder(SubmitTradeOrderRequest request) {
        validateSubmitTradeOrderRequest(request);
        String requestBody = JSON.toJSONString(request);
        String url = config.getBaseUrl() + PATH_SUBMIT_TRADE_ORDER;
        LOGGER.info("TradeOrderManagementClient submitTradeOrder, requestId={}, sceneCode={}",
            request.getRequestId(), request.getSceneCode());
        String responseBody = httpClientUtil.sendPostRequest(url, PATH_SUBMIT_TRADE_ORDER, requestBody);
        return parseSubmitTradeOrderResponse(responseBody);
    }

    // =========================================================================
    // inquiryTradeOrder
    // =========================================================================

    /**
     * 查询交易订单处理结果（仅 PAY_INTO_CHINA 场景）。
     *
     * <p>当 submitTradeOrder 返回 PROCESSING 时，调用此接口轮询批次处理状态。
     * 当 batchStatus 为 FINISHED 时，可从 tradeOrderResults 获取各订单最终结果。
     *
     * @param request 查询请求，需传入与 submitTradeOrder 相同的 requestId
     * @return 查询响应，包含 batchStatus 和 tradeOrderResults
     * @throws WfException 参数校验失败或调用失败时抛出异常
     */
    public InquiryTradeOrderResponse inquiryTradeOrder(InquiryTradeOrderRequest request) {
        validateInquiryTradeOrderRequest(request);
        String requestBody = JSON.toJSONString(request);
        String url = config.getBaseUrl() + PATH_INQUIRY_TRADE_ORDER;
        LOGGER.info("TradeOrderManagementClient inquiryTradeOrder, requestId={}, sceneCode={}",
            request.getRequestId(), request.getSceneCode());
        String responseBody = httpClientUtil.sendPostRequest(url, PATH_INQUIRY_TRADE_ORDER, requestBody);
        return parseInquiryTradeOrderResponse(responseBody);
    }

    // =========================================================================
    // Validation
    // =========================================================================

    private void validateSubmitTradeOrderRequest(SubmitTradeOrderRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "SubmitTradeOrderRequest must not be null");
        }

        String requestId = request.getRequestId();
        if (requestId == null || requestId.trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "requestId must not be blank");
        }
        if (requestId.length() > MAX_REQUEST_ID_LENGTH) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "requestId length must not exceed " + MAX_REQUEST_ID_LENGTH);
        }

        String sceneCode = request.getSceneCode();
        if (sceneCode == null || sceneCode.trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "sceneCode must not be blank");
        }
        if (!SCENE_PAY_INTO_CHINA.equals(sceneCode) && !SCENE_CREATE_B2B_ORDERS.equals(sceneCode)) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "unsupported sceneCode: " + sceneCode
                    + ", expected PAY_INTO_CHINA or CREATE_B2B_ORDERS");
        }

        List<TradeOrder> tradeOrders = request.getTradeOrders();
        if (tradeOrders == null || tradeOrders.isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "tradeOrders must not be null or empty");
        }

        if (SCENE_PAY_INTO_CHINA.equals(sceneCode)) {
            if (tradeOrders.size() > MAX_B2C_ORDERS) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "PAY_INTO_CHINA tradeOrders size must not exceed " + MAX_B2C_ORDERS
                        + ", actual: " + tradeOrders.size());
            }
            if (request.getPlatform() == null || request.getPlatform().trim().isEmpty()) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "platform is required when sceneCode is PAY_INTO_CHINA");
            }
        } else if (SCENE_CREATE_B2B_ORDERS.equals(sceneCode)) {
            if (tradeOrders.size() > MAX_B2B_ORDERS) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "CREATE_B2B_ORDERS tradeOrders size must not exceed " + MAX_B2B_ORDERS
                        + ", actual: " + tradeOrders.size());
            }
        }
    }

    private void validateInquiryTradeOrderRequest(InquiryTradeOrderRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "InquiryTradeOrderRequest must not be null");
        }

        String requestId = request.getRequestId();
        if (requestId == null || requestId.trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "requestId must not be blank");
        }
        if (requestId.length() > MAX_REQUEST_ID_LENGTH) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "requestId length must not exceed " + MAX_REQUEST_ID_LENGTH);
        }

        String sceneCode = request.getSceneCode();
        if (sceneCode == null || sceneCode.trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "sceneCode must not be blank");
        }
        if (!SCENE_PAY_INTO_CHINA.equals(sceneCode)) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "inquiryTradeOrder only supports sceneCode PAY_INTO_CHINA, actual: " + sceneCode);
        }
    }

    // =========================================================================
    // Response parsing
    // =========================================================================

    private SubmitTradeOrderResponse parseSubmitTradeOrderResponse(String responseBody) {
        SubmitTradeOrderResponse response;
        try {
            response = JSON.parseObject(responseBody, SubmitTradeOrderResponse.class);
        } catch (Exception e) {
            LOGGER.error("TradeOrderManagementClient failed to parse submitTradeOrder response, body={}",
                responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse submitTradeOrder response: " + e.getMessage(), e);
        }
        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "submitTradeOrder response result is null");
        }
        if ("S".equals(result.getResultStatus())) {
            LOGGER.info("TradeOrderManagementClient submitTradeOrder success, requestId={}, resultCode={}",
                response.getRequestId(), result.getResultCode());
            return response;
        }
        WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
        LOGGER.warn("TradeOrderManagementClient submitTradeOrder failed, resultStatus={}, resultCode={}, resultMessage={}",
            result.getResultStatus(), result.getResultCode(), result.getResultMessage());
        throw new WfException(errorCode,
            "submitTradeOrder failed: " + result.getResultCode() + " - " + result.getResultMessage());
    }

    private InquiryTradeOrderResponse parseInquiryTradeOrderResponse(String responseBody) {
        InquiryTradeOrderResponse response;
        try {
            response = JSON.parseObject(responseBody, InquiryTradeOrderResponse.class);
        } catch (Exception e) {
            LOGGER.error("TradeOrderManagementClient failed to parse inquiryTradeOrder response, body={}",
                responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse inquiryTradeOrder response: " + e.getMessage(), e);
        }
        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "inquiryTradeOrder response result is null");
        }
        if ("S".equals(result.getResultStatus())) {
            LOGGER.info("TradeOrderManagementClient inquiryTradeOrder success, requestId={}, batchStatus={}",
                response.getRequestId(), response.getBatchStatus());
            return response;
        }
        WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
        LOGGER.warn("TradeOrderManagementClient inquiryTradeOrder failed, resultStatus={}, resultCode={}, resultMessage={}",
            result.getResultStatus(), result.getResultCode(), result.getResultMessage());
        throw new WfException(errorCode,
            "inquiryTradeOrder failed: " + result.getResultCode() + " - " + result.getResultMessage());
    }
}
