package {basePackage}.wf.client;

import com.alibaba.fastjson.JSON;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.exception.WfErrorCode;
import {basePackage}.wf.exception.WfException;
import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;
import {basePackage}.wf.model.request.ConsultTransferRequest;
import {basePackage}.wf.model.response.ConsultTransferResponse;
import {basePackage}.wf.model.response.Result;
import {basePackage}.wf.signer.WfSigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WorldFirst 转账咨询客户端，封装 consultTransfer 接口。
 *
 * <p>在调用 createTransfer 进行转账之前，可先调用此接口获取转账相关信息，
 * 如跨币种汇率、手续费等。
 *
 */
public class TransferClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferClient.class);

    private static final String PATH_CONSULT_TRANSFER = "/amsin/api/v1/business/fund/consultTransfer";

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
    // consultTransfer
    // =========================================================================

    /**
     * 调用 WF consultTransfer 接口，获取转账咨询信息。
     *
     * <p>此接口为同步接口，返回转账相关的汇率、手续费等信息。
     * 适用于跨币种转账前的汇率查询场景。
     *
     * @param request 转账咨询请求
     * @return 转账咨询响应（含汇率、手续费等）
     * @throws WfException 当 resultStatus 为 F 或 U 时抛出
     */
    public ConsultTransferResponse consultTransfer(ConsultTransferRequest request) {
        validateConsultTransferRequest(request);
        String requestBody = JSON.toJSONString(request);
        String url = config.getBaseUrl() + PATH_CONSULT_TRANSFER;
        LOGGER.info("TransferClient consultTransfer, fromCurrency={}, toCurrency={}",
            request.getTransferFromDetail().getTransferFromAmount().getCurrency(),
            request.getTransferToDetail().getTransferToAmount().getCurrency());
        String responseBody = httpClientUtil.sendPostRequest(url, PATH_CONSULT_TRANSFER, requestBody);
        return parseConsultTransferResponse(responseBody);
    }

    // =========================================================================
    // Validation
    // =========================================================================

    private void validateConsultTransferRequest(ConsultTransferRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "ConsultTransferRequest must not be null");
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
    }

    // =========================================================================
    // Response parsing
    // =========================================================================

    private ConsultTransferResponse parseConsultTransferResponse(String responseBody) {
        ConsultTransferResponse response;
        try {
            response = JSON.parseObject(responseBody, ConsultTransferResponse.class);
        } catch (Exception e) {
            LOGGER.error("TransferClient failed to parse consultTransfer response, body={}", responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse consultTransfer response: " + e.getMessage(), e);
        }
        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT, "consultTransfer response result is null");
        }
        if ("S".equals(result.getResultStatus())) {
            LOGGER.info("TransferClient consultTransfer success, resultCode={}", result.getResultCode());
            return response;
        }
        WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
        LOGGER.warn("TransferClient consultTransfer failed, resultStatus={}, resultCode={}, resultMessage={}",
            result.getResultStatus(), result.getResultCode(), result.getResultMessage());
        throw new WfException(errorCode,
            "consultTransfer failed: " + result.getResultCode() + " - " + result.getResultMessage());
    }
}
