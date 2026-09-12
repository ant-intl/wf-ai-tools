package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.CreateBeneficiaryRequest;
import {basePackage}.wf.model.request.DeleteBeneficiaryRequest;
import {basePackage}.wf.model.request.ListBeneficiariesRequest;
import {basePackage}.wf.model.request.QueryBeneficiaryRequest;
import {basePackage}.wf.model.request.QueryBeneficiaryTemplateRequest;
import {basePackage}.wf.model.request.UpdateBeneficiaryRequest;
import {basePackage}.wf.model.request.ValidateBeneficiaryRequest;
import {basePackage}.wf.model.response.BeneficiaryResponse;
import {basePackage}.wf.model.response.ListBeneficiariesResponse;
import {basePackage}.wf.model.response.QueryBeneficiaryTemplateResponse;
import {basePackage}.wf.model.response.ValidateBeneficiaryResponse;

/**
 * WorldFirst 收款人（Beneficiaries）服务。
 *
 * <p>内部使用 {@link WfApiClient} 处理 HTTP 请求与加验签，采用薄封装模式：
 * 不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。
 * <p><b>安全强制</b>：{@link WfApiClient} 在返回响应前会执行验签，
 * 验签失败时抛出 {@link WfException}（错误码 {@link WfErrorCode#INVALID_SIGNATURE}）。
 *
 * <pre>{@code
 * // 1. 创建配置
 * WfClientConfig config = WfClientConfig.builder()
 *     .clientId("YOUR_CLIENT_ID")
 *     .privateKeyFromPath("/path/to/private_key.pem")
 *     .publicKeyFromPath("/path/to/wf_public_key.pem")
 *     .baseUrl("https://YOUR_BASE_URL")
 *     .build();
 *
 * // 2. 创建服务
 * BeneficiaryService beneficiaryService = new BeneficiaryService(config);
 *
 * // 3. 创建收款人（内部已强制验签，验签失败抛 WfException）
 * CreateBeneficiaryRequest request = new CreateBeneficiaryRequest();
 * request.setAccountType("BANK_ACCOUNT");
 * request.setRegion("US");
 * // ... 设置其他参数
 *
 * BeneficiaryResponse response = beneficiaryService.createBeneficiary(request);
 * }</pre>
 */
public class BeneficiaryService {

    private static final String QUERY_TEMPLATE_ENDPOINT = "/api/open/v1/beneficiaries/queryTemplate";
    private static final String CREATE_ENDPOINT = "/api/open/v1/beneficiaries/create";
    private static final String QUERY_ENDPOINT = "/api/open/v1/beneficiaries/query";
    private static final String LIST_ENDPOINT = "/api/open/v1/beneficiaries/list";
    private static final String UPDATE_ENDPOINT = "/api/open/v1/beneficiaries/update";
    private static final String DELETE_ENDPOINT = "/api/open/v1/beneficiaries/delete";
    private static final String VALIDATE_ENDPOINT = "/api/open/v1/beneficiaries/validate";

    private final WfApiClient apiClient;

    public BeneficiaryService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    public BeneficiaryService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 查询收款人字段模板。
     * <p>对应 WF API: POST /api/open/v1/beneficiaries/queryTemplate
     */
    public QueryBeneficiaryTemplateResponse queryBeneficiaryTemplate(QueryBeneficiaryTemplateRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_TEMPLATE_ENDPOINT, body, QueryBeneficiaryTemplateResponse.class).getServiceResponse();
    }

    /**
     * 创建收款人。
     * <p>对应 WF API: POST /api/open/v1/beneficiaries/create
     */
    public BeneficiaryResponse createBeneficiary(CreateBeneficiaryRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CREATE_ENDPOINT, body, BeneficiaryResponse.class).getServiceResponse();
    }

    /**
     * 查询收款人详情。
     * <p>对应 WF API: POST /api/open/v1/beneficiaries/query
     */
    public BeneficiaryResponse queryBeneficiary(QueryBeneficiaryRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_ENDPOINT, body, BeneficiaryResponse.class).getServiceResponse();
    }

    /**
     * 列表查询收款人。
     * <p>对应 WF API: POST /api/open/v1/beneficiaries/list
     */
    public ListBeneficiariesResponse listBeneficiaries(ListBeneficiariesRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(LIST_ENDPOINT, body, ListBeneficiariesResponse.class).getServiceResponse();
    }

    /**
     * 更新收款人。
     * <p>对应 WF API: POST /api/open/v1/beneficiaries/update
     */
    public BeneficiaryResponse updateBeneficiary(UpdateBeneficiaryRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(UPDATE_ENDPOINT, body, BeneficiaryResponse.class).getServiceResponse();
    }

    /**
     * 删除收款人。
     * <p>对应 WF API: POST /api/open/v1/beneficiaries/delete
     */
    public BeneficiaryResponse deleteBeneficiary(DeleteBeneficiaryRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(DELETE_ENDPOINT, body, BeneficiaryResponse.class).getServiceResponse();
    }

    /**
     * 校验收款人信息（不实际创建）。
     * <p>对应 WF API: POST /api/open/v1/beneficiaries/validate
     */
    public ValidateBeneficiaryResponse validateBeneficiary(ValidateBeneficiaryRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(VALIDATE_ENDPOINT, body, ValidateBeneficiaryResponse.class).getServiceResponse();
    }
}
