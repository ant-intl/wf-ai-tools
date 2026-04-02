/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.client;

import com.alibaba.fastjson.JSON;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.exception.WfErrorCode;
import {basePackage}.wf.exception.WfException;
import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.PaymentMethodMetaData;
import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;
import {basePackage}.wf.model.domain.TransferToMethod;
import {basePackage}.wf.model.request.ConsultPayoutRequest;
import {basePackage}.wf.model.request.CreatePayoutRequest;
import {basePackage}.wf.model.request.InquiryPayoutRequest;
import {basePackage}.wf.model.response.ConsultPayoutResponse;
import {basePackage}.wf.model.response.CreatePayoutResponse;
import {basePackage}.wf.model.response.InquiryPayoutResponse;
import {basePackage}.wf.model.response.Result;
import {basePackage}.wf.signer.WfSigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WorldFirst 代发客户端，统一封装 consultPayout、createPayout 和 inquiryPayout 三个接口。
 *
 * <p>跨币种代发流程：
 * <ol>
 *   <li>调用 {@link #consultPayout} 获取汇率报价（quoteId）</li>
 *   <li>将 quoteId 传入 {@link #createPayout} 的 transferToDetail.transferQuote.quoteId</li>
 *   <li>若返回 PROCESSING，调用 {@link #inquiryPayout} 轮询最终结果</li>
 * </ol>
 *
 * @author Qoder
 * @version PayoutClient.java, v 0.1 2026-03-27
 */
public class PayoutClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayoutClient.class);

    private static final String PATH_CONSULT_PAYOUT = "/amsin/api/v1/business/fund/consultPayout";
    private static final String PATH_CREATE_PAYOUT = "/amsin/api/v1/business/fund/createPayout";
    private static final String PATH_INQUIRY_PAYOUT = "/amsin/api/v1/business/fund/inquiryPayout";

    private static final String TRANSFER_METHOD_BANK = "BANK_ACCOUNT_DETAIL";
    private static final String TRANSFER_METHOD_TOKEN = "BENEFICIARY_TOKEN";
    private static final String CNY = "CNY";
    private static final int MAX_TRANSFER_REQUEST_ID_LENGTH = 64;

    private final WfConfig config;
    private WfHttpClientUtil httpClientUtil;
    private WfSigner signer;

    public PayoutClient(WfConfig config) {
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
    // consultPayout
    // =========================================================================

    /**
     * 咨询跨币种代发汇率报价。
     *
     * <p>在发起跨币种 createPayout 之前，调用此接口获取汇率报价（quoteId），
     * 然后将 quoteId 传入 createPayout 请求的 transferToDetail.transferQuote.quoteId 字段。
     *
     * @param request 咨询请求，包含 transferFromDetail 和 transferToDetail
     * @return 咨询响应，包含汇率报价信息（通过 getQuoteId() 获取 quoteId）
     * @throws WfException 调用失败时抛出异常
     */
    public ConsultPayoutResponse consultPayout(ConsultPayoutRequest request) {
        validateConsultPayoutRequest(request);
        String requestBody = JSON.toJSONString(request);
        String url = config.getBaseUrl() + PATH_CONSULT_PAYOUT;
        LOGGER.info("PayoutClient consultPayout, fromCurrency={}, toCurrency={}",
            request.getTransferFromDetail().getTransferFromAmount().getCurrency(),
            request.getTransferToDetail().getTransferToAmount().getCurrency());
        String responseBody = httpClientUtil.sendPostRequest(url, PATH_CONSULT_PAYOUT, requestBody);
        return parseConsultPayoutResponse(responseBody);
    }

    // =========================================================================
    // createPayout
    // =========================================================================

    public CreatePayoutResponse createPayout(CreatePayoutRequest request) {
        validateCreatePayoutRequest(request);
        String requestBody = JSON.toJSONString(request);
        String url = config.getBaseUrl() + PATH_CREATE_PAYOUT;
        LOGGER.info("PayoutClient createPayout, transferRequestId={}", request.getTransferRequestId());
        String responseBody = httpClientUtil.sendPostRequest(url, PATH_CREATE_PAYOUT, requestBody);
        return parseCreatePayoutResponse(responseBody);
    }

    // =========================================================================
    // inquiryPayout
    // =========================================================================

    public InquiryPayoutResponse inquiryPayout(InquiryPayoutRequest request) {
        validateInquiryPayoutRequest(request);
        String requestBody = JSON.toJSONString(request);
        String url = config.getBaseUrl() + PATH_INQUIRY_PAYOUT;
        LOGGER.info("PayoutClient inquiryPayout, transferId={}, transferRequestId={}",
            request.getTransferId(), request.getTransferRequestId());
        String responseBody = httpClientUtil.sendPostRequest(url, PATH_INQUIRY_PAYOUT, requestBody);
        return parseInquiryPayoutResponse(responseBody);
    }

    // =========================================================================
    // Validation
    // =========================================================================

    private void validateConsultPayoutRequest(ConsultPayoutRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "ConsultPayoutRequest must not be null");
        }

        TransferFromDetail fromDetail = request.getTransferFromDetail();
        if (fromDetail == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "transferFromDetail must not be null");
        }
        Amount fromAmount = fromDetail.getTransferFromAmount();
        if (fromAmount == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferFromDetail.transferFromAmount must not be null");
        }
        if (fromAmount.getCurrency() == null || fromAmount.getCurrency().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferFromDetail.transferFromAmount.currency must not be blank");
        }

        TransferToDetail toDetail = request.getTransferToDetail();
        if (toDetail == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "transferToDetail must not be null");
        }
        Amount toAmount = toDetail.getTransferToAmount();
        if (toAmount == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferToDetail.transferToAmount must not be null");
        }
        if (toAmount.getCurrency() == null || toAmount.getCurrency().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferToDetail.transferToAmount.currency must not be blank");
        }

        // CNY requires businessSceneCode
        if (CNY.equals(toAmount.getCurrency())) {
            if (request.getBusinessSceneCode() == null
                || request.getBusinessSceneCode().trim().isEmpty()) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "businessSceneCode is required when transferToAmount.currency is CNY");
            }
        }
    }

    private void validateCreatePayoutRequest(CreatePayoutRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "CreatePayoutRequest must not be null");
        }
        String reqId = request.getTransferRequestId();
        if (reqId == null || reqId.trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "transferRequestId must not be blank");
        }
        if (reqId.length() > MAX_TRANSFER_REQUEST_ID_LENGTH) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferRequestId length must not exceed " + MAX_TRANSFER_REQUEST_ID_LENGTH);
        }

        TransferFromDetail fromDetail = request.getTransferFromDetail();
        if (fromDetail == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "transferFromDetail must not be null");
        }
        Amount fromAmount = fromDetail.getTransferFromAmount();
        if (fromAmount == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferFromDetail.transferFromAmount must not be null");
        }
        if (fromAmount.getCurrency() == null || fromAmount.getCurrency().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferFromDetail.transferFromAmount.currency must not be blank");
        }

        TransferToDetail toDetail = request.getTransferToDetail();
        if (toDetail == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "transferToDetail must not be null");
        }
        Amount toAmount = toDetail.getTransferToAmount();
        if (toAmount == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferToDetail.transferToAmount must not be null");
        }
        if (toAmount.getCurrency() == null || toAmount.getCurrency().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferToDetail.transferToAmount.currency must not be blank");
        }

        boolean hasFromValue = fromAmount.getValue() != null;
        boolean hasToValue = toAmount.getValue() != null;
        if (hasFromValue && hasToValue) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferFromAmount.value and transferToAmount.value are mutually exclusive");
        }
        if (!hasFromValue && !hasToValue) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "either transferFromAmount.value or transferToAmount.value must be specified");
        }
        if (hasFromValue && fromAmount.getValue() <= 0) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferFromAmount.value must be positive when specified");
        }
        if (hasToValue && toAmount.getValue() <= 0) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferToAmount.value must be positive when specified");
        }

        TransferToMethod toMethod = toDetail.getTransferToMethod();
        if (toMethod == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferToDetail.transferToMethod must not be null");
        }
        String paymentMethodType = toMethod.getPaymentMethodType();
        if (paymentMethodType == null || paymentMethodType.trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "transferToDetail.transferToMethod.paymentMethodType must not be blank");
        }

        String paymentMethodId = toMethod.getPaymentMethodId();
        PaymentMethodMetaData metaData = toMethod.getPaymentMethodMetaData();

        if (TRANSFER_METHOD_TOKEN.equals(paymentMethodType)) {
            if (paymentMethodId == null || paymentMethodId.trim().isEmpty()) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "paymentMethodId (beneficiaryToken) is required when paymentMethodType=BENEFICIARY_TOKEN");
            }
            if (metaData != null) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "paymentMethodMetaData must not be specified when paymentMethodType=BENEFICIARY_TOKEN");
            }
        } else if (TRANSFER_METHOD_BANK.equals(paymentMethodType)) {
            if (metaData == null || metaData.getBankAccountNo() == null
                || metaData.getBankAccountNo().trim().isEmpty()) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "paymentMethodMetaData.bankAccountNo is required when paymentMethodType=BANK_ACCOUNT_DETAIL");
            }
            if (paymentMethodId != null && !paymentMethodId.trim().isEmpty()) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "paymentMethodId must not be specified when paymentMethodType=BANK_ACCOUNT_DETAIL");
            }
        } else {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "unsupported paymentMethodType: " + paymentMethodType
                    + ", expected BANK_ACCOUNT_DETAIL or BENEFICIARY_TOKEN");
        }

        if (CNY.equals(toAmount.getCurrency())) {
            if (request.getBusinessSceneCode() == null
                || request.getBusinessSceneCode().trim().isEmpty()) {
                throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                    "businessSceneCode is required when transferToAmount.currency is CNY");
            }
        }

        if (toDetail.getPurposeCode() == null || toDetail.getPurposeCode().trim().isEmpty()) {
            toDetail.setPurposeCode("GDS");
        }
    }

    private void validateInquiryPayoutRequest(InquiryPayoutRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "InquiryPayoutRequest must not be null");
        }
        boolean hasTransferId = request.getTransferId() != null
            && !request.getTransferId().trim().isEmpty();
        boolean hasRequestId = request.getTransferRequestId() != null
            && !request.getTransferRequestId().trim().isEmpty();
        if (!hasTransferId && !hasRequestId) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL,
                "either transferId or transferRequestId must be provided");
        }
    }

    // =========================================================================
    // Response parsing
    // =========================================================================

    private ConsultPayoutResponse parseConsultPayoutResponse(String responseBody) {
        ConsultPayoutResponse response;
        try {
            response = JSON.parseObject(responseBody, ConsultPayoutResponse.class);
        } catch (Exception e) {
            LOGGER.error("PayoutClient failed to parse consultPayout response, body={}", responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse consultPayout response: " + e.getMessage(), e);
        }
        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT, "consultPayout response result is null");
        }
        if ("S".equals(result.getResultStatus())) {
            LOGGER.info("PayoutClient consultPayout success, quoteId={}, chargeMode={}",
                response.getQuoteId(), response.getChargeMode());
            return response;
        }
        WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
        LOGGER.warn("PayoutClient consultPayout failed, resultStatus={}, resultCode={}, resultMessage={}",
            result.getResultStatus(), result.getResultCode(), result.getResultMessage());
        throw new WfException(errorCode,
            "consultPayout failed: " + result.getResultCode() + " - " + result.getResultMessage());
    }

    private CreatePayoutResponse parseCreatePayoutResponse(String responseBody) {
        CreatePayoutResponse response;
        try {
            response = JSON.parseObject(responseBody, CreatePayoutResponse.class);
        } catch (Exception e) {
            LOGGER.error("PayoutClient failed to parse createPayout response, body={}", responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse createPayout response: " + e.getMessage(), e);
        }
        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT, "createPayout response result is null");
        }
        if ("S".equals(result.getResultStatus())) {
            LOGGER.info("PayoutClient createPayout accepted, transferId={}, resultCode={}",
                response.getTransferId(), result.getResultCode());
            return response;
        }
        WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
        LOGGER.warn("PayoutClient createPayout failed, resultStatus={}, resultCode={}, resultMessage={}",
            result.getResultStatus(), result.getResultCode(), result.getResultMessage());
        throw new WfException(errorCode,
            "createPayout failed: " + result.getResultCode() + " - " + result.getResultMessage());
    }

    private InquiryPayoutResponse parseInquiryPayoutResponse(String responseBody) {
        InquiryPayoutResponse response;
        try {
            response = JSON.parseObject(responseBody, InquiryPayoutResponse.class);
        } catch (Exception e) {
            LOGGER.error("PayoutClient failed to parse inquiryPayout response, body={}", responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse inquiryPayout response: " + e.getMessage(), e);
        }
        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT, "inquiryPayout response result is null");
        }
        if ("S".equals(result.getResultStatus())) {
            LOGGER.info("PayoutClient inquiryPayout success, transferId={}, transferResult={}",
                response.getTransferId(), response.getTransferResult());
            return response;
        }
        WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
        LOGGER.warn("PayoutClient inquiryPayout failed, resultStatus={}, resultCode={}, resultMessage={}",
            result.getResultStatus(), result.getResultCode(), result.getResultMessage());
        throw new WfException(errorCode,
            "inquiryPayout failed: " + result.getResultCode() + " - " + result.getResultMessage());
    }
}
