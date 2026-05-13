package {basePackage}.wf.client;

import com.alibaba.fastjson.JSON;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.exception.WfErrorCode;
import {basePackage}.wf.exception.WfException;
import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;
import {basePackage}.wf.model.request.CreateTransferRequest;
import {basePackage}.wf.model.response.CreateTransferResponse;
import {basePackage}.wf.model.response.Result;
import {basePackage}.wf.signer.WfSigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WorldFirst 转账客户端，封装 createTransfer 接口。
 *
 * <p>用于万里汇户到户转账，资金在万里汇账户之间流通。
 *
 */
public class TransferClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferClient.class);

    private static final String PATH_CREATE_TRANSFER = "/amsin/api/v1/business/fund/createTransfer";

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
    // createTransfer
    // =========================================================================

    /**
     * 调用 WF createTransfer 接口进行户到户转账。
     *
     * <p>转账步骤为异步。调用本接口后，万里汇仅返回此接口的调用结果。
     * 当 {@code result.resultCode=PROCESSING} 时，需调用 inquiryTransfer 查询最终结果。
     *
     * @param request 转账请求
     * @return 转账响应
     * @throws WfException 当 resultStatus 为 F 或 U 时抛出
     */
    public CreateTransferResponse createTransfer(CreateTransferRequest request) {
        validateCreateTransferRequest(request);
        String requestBody = JSON.toJSONString(request);
        String url = config.getBaseUrl() + PATH_CREATE_TRANSFER;
        LOGGER.info("TransferClient createTransfer, transferRequestId={}", request.getTransferRequestId());
        String responseBody = httpClientUtil.sendPostRequest(url, PATH_CREATE_TRANSFER, requestBody);
        return parseCreateTransferResponse(responseBody);
    }

    // =========================================================================
    // Validation
    // =========================================================================

    private void validateCreateTransferRequest(CreateTransferRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "CreateTransferRequest must not be null");
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

    private CreateTransferResponse parseCreateTransferResponse(String responseBody) {
        CreateTransferResponse response;
        try {
            response = JSON.parseObject(responseBody, CreateTransferResponse.class);
        } catch (Exception e) {
            LOGGER.error("TransferClient failed to parse createTransfer response, body={}", responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse createTransfer response: " + e.getMessage(), e);
        }
        Result result = response.getResult();
        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT, "createTransfer response result is null");
        }
        if ("S".equals(result.getResultStatus())) {
            LOGGER.info("TransferClient createTransfer accepted, transferId={}, resultCode={}",
                response.getTransferId(), result.getResultCode());
            return response;
        }
        WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
        LOGGER.warn("TransferClient createTransfer failed, resultStatus={}, resultCode={}, resultMessage={}",
            result.getResultStatus(), result.getResultCode(), result.getResultMessage());
        throw new WfException(errorCode,
            "createTransfer failed: " + result.getResultCode() + " - " + result.getResultMessage());
    }
}

