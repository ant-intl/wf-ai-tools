/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.client;

import com.alibaba.fastjson.JSON;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.BindBeneficiaryRequest;
import {basePackage}.wf.model.request.EditBeneficiaryRequest;
import {basePackage}.wf.model.request.InquiryBeneficiaryListRequest;
import {basePackage}.wf.model.request.InquiryBeneficiaryTemplateRequest;
import {basePackage}.wf.model.request.RemoveBeneficiaryRequest;
import {basePackage}.wf.model.response.BindBeneficiaryResponse;
import {basePackage}.wf.model.response.EditBeneficiaryResponse;
import {basePackage}.wf.model.response.InquiryBeneficiaryListResponse;
import {basePackage}.wf.model.response.InquiryBeneficiaryTemplateResponse;
import {basePackage}.wf.model.response.RemoveBeneficiaryResponse;
import {basePackage}.wf.model.response.Result;
import {basePackage}.wf.signer.WfSigner;
import {basePackage}.wf.util.WfHttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WorldFirst 收款人管理统一客户端。
 *
 * <p>包含 5 个接口方法：
 * <ul>
 *   <li>{@link #inquiryBeneficiaryTemplate} — 查询卡模版</li>
 *   <li>{@link #bindBeneficiary} — 绑定收款人</li>
 *   <li>{@link #removeBeneficiary} — 删除收款人</li>
 *   <li>{@link #editBeneficiary} — 编辑收款人昵称</li>
 *   <li>{@link #inquiryBeneficiaryList} — 查询收款人列表</li>
 * </ul>
 *
 * @author Qoder
 * @version BeneficiaryManagementClient.java, v 0.1 2026-03-26
 */
public class BeneficiaryManagementClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeneficiaryManagementClient.class);

    // API paths
    private static final String PATH_INQUIRY_TEMPLATE = "/amsin/api/v1/business/account/inquiryBeneficiaryTemplate";
    private static final String PATH_BIND = "/amsin/api/v1/business/account/bindBeneficiary";
    private static final String PATH_REMOVE = "/amsin/api/v1/business/account/removeBeneficiary";
    private static final String PATH_EDIT = "/amsin/api/v1/business/account/editBeneficiary";
    private static final String PATH_LIST = "/amsin/api/v1/business/account/inquiryBeneficiaryList";

    private static final int MAX_COUNTRY_CODE_LENGTH = 2;
    private static final int MAX_REQUEST_ID_LENGTH = 64;
    private static final int MAX_PAGE_SIZE = 50;

    private final WfConfig config;
    private WfHttpClientUtil httpClientUtil;
    private WfSigner signer;

    public BeneficiaryManagementClient(WfConfig config) {
        this.config = config;
    }

    public void init() {
        this.httpClientUtil = new WfHttpClientUtil(config);
        this.signer = httpClientUtil.getSigner();
    }

    /**
     * 允许外部注入 {@link WfHttpClientUtil}。
     * 适用于测试场景需要 mock HTTP 客户端的情况。
     *
     * @param httpClientUtil HTTP 客户端工具
     */
    public void setHttpClientUtil(WfHttpClientUtil httpClientUtil) {
        this.httpClientUtil = httpClientUtil;
        if (httpClientUtil != null) {
            this.signer = httpClientUtil.getSigner();
        }
    }

    // -------------------------------------------------------------------------
    // 1. 查询卡模版
    // -------------------------------------------------------------------------

    /**
     * 查询卡模版信息。
     *
     * @param request 查询请求
     * @return 卡模版响应
     * @throws WfException 参数校验失败或业务失败时抛出
     */
    public InquiryBeneficiaryTemplateResponse inquiryBeneficiaryTemplate(InquiryBeneficiaryTemplateRequest request) {
        validateTemplateRequest(request);
        return executeRequest(request, PATH_INQUIRY_TEMPLATE, InquiryBeneficiaryTemplateResponse.class);
    }

    // -------------------------------------------------------------------------
    // 2. 绑定收款人
    // -------------------------------------------------------------------------

    /**
     * 绑定收款人。
     *
     * @param request 绑定请求
     * @return 绑定响应（含 beneficiaryToken）
     * @throws WfException 参数校验失败或业务失败时抛出
     */
    public BindBeneficiaryResponse bindBeneficiary(BindBeneficiaryRequest request) {
        validateBindRequest(request);
        return executeRequest(request, PATH_BIND, BindBeneficiaryResponse.class);
    }

    // -------------------------------------------------------------------------
    // 3. 删除收款人
    // -------------------------------------------------------------------------

    /**
     * 删除收款人。
     *
     * @param request 删除请求
     * @return 删除响应
     * @throws WfException 参数校验失败或业务失败时抛出
     */
    public RemoveBeneficiaryResponse removeBeneficiary(RemoveBeneficiaryRequest request) {
        validateRemoveRequest(request);
        return executeRequest(request, PATH_REMOVE, RemoveBeneficiaryResponse.class);
    }

    // -------------------------------------------------------------------------
    // 4. 编辑收款人
    // -------------------------------------------------------------------------

    /**
     * 编辑收款人昵称。
     *
     * @param request 编辑请求
     * @return 编辑响应
     * @throws WfException 参数校验失败或业务失败时抛出
     */
    public EditBeneficiaryResponse editBeneficiary(EditBeneficiaryRequest request) {
        validateEditRequest(request);
        return executeRequest(request, PATH_EDIT, EditBeneficiaryResponse.class);
    }

    // -------------------------------------------------------------------------
    // 5. 查询收款人列表
    // -------------------------------------------------------------------------

    /**
     * 分页查询收款人列表。
     *
     * @param request 查询请求
     * @return 收款人列表响应
     * @throws WfException 参数校验失败或业务失败时抛出
     */
    public InquiryBeneficiaryListResponse inquiryBeneficiaryList(InquiryBeneficiaryListRequest request) {
        validateListRequest(request);
        return executeRequest(request, PATH_LIST, InquiryBeneficiaryListResponse.class);
    }

    // -------------------------------------------------------------------------
    // Validation methods
    // -------------------------------------------------------------------------

    private void validateTemplateRequest(InquiryBeneficiaryTemplateRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "Request must not be null");
        }
        String countryCode = request.getCountryCode();
        if (countryCode != null && countryCode.length() > MAX_COUNTRY_CODE_LENGTH) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "countryCode max length is " + MAX_COUNTRY_CODE_LENGTH);
        }
    }

    private void validateBindRequest(BindBeneficiaryRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "Request must not be null");
        }
        validateRequestId(request.getBindBeneficiaryRequestId(), "bindBeneficiaryRequestId");
        if (request.getBeneficiaryType() == null || request.getBeneficiaryType().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "beneficiaryType is required");
        }
    }

    private void validateRemoveRequest(RemoveBeneficiaryRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "Request must not be null");
        }
        validateRequestId(request.getRemoveBeneficiaryRequestId(), "removeBeneficiaryRequestId");
        if (request.getBeneficiaryToken() == null || request.getBeneficiaryToken().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "beneficiaryToken is required");
        }
    }

    private void validateEditRequest(EditBeneficiaryRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "Request must not be null");
        }
        if (request.getBeneficiaryToken() == null || request.getBeneficiaryToken().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "beneficiaryToken is required");
        }
        if (request.getBeneficiaryNick() == null || request.getBeneficiaryNick().trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "beneficiaryNick is required");
        }
    }

    private void validateListRequest(InquiryBeneficiaryListRequest request) {
        if (request == null) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "Request must not be null");
        }
        if (request.getPageSize() == null || request.getPageSize() <= 0) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "pageSize is required and must be positive");
        }
        if (request.getPageSize() > MAX_PAGE_SIZE) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "pageSize max is " + MAX_PAGE_SIZE);
        }
        if (request.getPageNumber() == null || request.getPageNumber() <= 0) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, "pageNumber is required and must be positive");
        }
    }

    private void validateRequestId(String requestId, String fieldName) {
        if (requestId == null || requestId.trim().isEmpty()) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, fieldName + " is required");
        }
        if (requestId.length() > MAX_REQUEST_ID_LENGTH) {
            throw new WfException(WfErrorCode.PARAM_ILLEGAL, fieldName + " max length is " + MAX_REQUEST_ID_LENGTH);
        }
    }

    // -------------------------------------------------------------------------
    // Execute request
    // -------------------------------------------------------------------------

    private <T> T executeRequest(Object request, String apiPath, Class<T> responseClass) {
        String requestBody = JSON.toJSONString(request);
        String url = config.getBaseUrl() + apiPath;

        LOGGER.info("BeneficiaryManagementClient calling {}, url={}", apiPath, url);

        String responseBody = httpClientUtil.sendPostRequest(url, apiPath, requestBody);

        return parseResponse(responseBody, responseClass);
    }

    private <T> T parseResponse(String responseBody, Class<T> responseClass) {
        T response;
        try {
            response = JSON.parseObject(responseBody, responseClass);
        } catch (Exception e) {
            LOGGER.error("Failed to parse response: {}", responseBody, e);
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT,
                "Failed to parse response: " + e.getMessage(), e);
        }

        // 获取 result 字段
        Result result = null;
        try {
            java.lang.reflect.Method getResult = responseClass.getMethod("getResult");
            result = (Result) getResult.invoke(response);
        } catch (Exception e) {
            LOGGER.warn("Response class has no getResult method");
        }

        if (result == null) {
            throw new WfException(WfErrorCode.INVALID_RESPONSE_FORMAT, "Response result is null");
        }

        String resultStatus = result.getResultStatus();
        if ("S".equals(resultStatus)) {
            LOGGER.info("BeneficiaryManagementClient success, resultCode={}", result.getResultCode());
            return response;
        }

        WfErrorCode errorCode = WfErrorCode.fromCode(result.getResultCode());
        LOGGER.error("BeneficiaryManagementClient failed, resultStatus={}, resultCode={}, message={}",
            resultStatus, result.getResultCode(), result.getResultMessage());
        throw new WfException(errorCode,
            "API call failed: " + result.getResultCode() + " - " + result.getResultMessage());
    }
}
