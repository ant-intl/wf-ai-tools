package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;
import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.client.WfRequestContext;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.request.CreateBatchPayoutRequest;
import {basePackage}.wf.model.response.CreateBatchPayoutResponse;
import {basePackage}.wf.model.request.AddItemsToBatchPayoutRequest;
import {basePackage}.wf.model.response.AddItemsToBatchPayoutResponse;
import {basePackage}.wf.model.request.RemoveItemsFromBatchPayoutRequest;
import {basePackage}.wf.model.response.RemoveItemsFromBatchPayoutResponse;
import {basePackage}.wf.model.request.ListBatchPayoutItemsRequest;
import {basePackage}.wf.model.response.ListBatchPayoutItemsResponse;
import {basePackage}.wf.model.request.CalculateBatchPayoutRequest;
import {basePackage}.wf.model.response.CalculateBatchPayoutResponse;
import {basePackage}.wf.model.request.SubmitBatchPayoutRequest;
import {basePackage}.wf.model.response.SubmitBatchPayoutResponse;
import {basePackage}.wf.model.request.QueryBatchPayoutRequest;
import {basePackage}.wf.model.response.QueryBatchPayoutResponse;
import {basePackage}.wf.model.request.DeleteBatchPayoutRequest;
import {basePackage}.wf.model.response.DeleteBatchPayoutResponse;
import {basePackage}.wf.model.request.CancelBatchPayoutRequest;
import {basePackage}.wf.model.response.CancelBatchPayoutResponse;

/** 批量代发 API 薄封装。单次请求；业务状态和重试由调用方按 GUIDE 处理。 */
public class BatchPayoutService {
    private final WfApiClient apiClient;

    public BatchPayoutService(WfClientConfig config) { this(new WfApiClient(config)); }

    public BatchPayoutService(WfApiClient apiClient) { this.apiClient = apiClient; }

    /** POST /api/open/v1/batchPayouts/create；响应已由公共客户端验签。 */
    public CreateBatchPayoutResponse createBatchPayout(CreateBatchPayoutRequest request) {
        return apiClient.post("/api/open/v1/batchPayouts/create", JSON.toJSONString(request), CreateBatchPayoutResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public CreateBatchPayoutResponse createBatchPayout(CreateBatchPayoutRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/batchPayouts/create", JSON.toJSONString(request), CreateBatchPayoutResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/batchPayouts/addItems；响应已由公共客户端验签。 */
    public AddItemsToBatchPayoutResponse addItemsToBatchPayout(AddItemsToBatchPayoutRequest request) {
        return apiClient.post("/api/open/v1/batchPayouts/addItems", JSON.toJSONString(request), AddItemsToBatchPayoutResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public AddItemsToBatchPayoutResponse addItemsToBatchPayout(AddItemsToBatchPayoutRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/batchPayouts/addItems", JSON.toJSONString(request), AddItemsToBatchPayoutResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/batchPayouts/removeItems；响应已由公共客户端验签。 */
    public RemoveItemsFromBatchPayoutResponse removeItemsFromBatchPayout(RemoveItemsFromBatchPayoutRequest request) {
        return apiClient.post("/api/open/v1/batchPayouts/removeItems", JSON.toJSONString(request), RemoveItemsFromBatchPayoutResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public RemoveItemsFromBatchPayoutResponse removeItemsFromBatchPayout(RemoveItemsFromBatchPayoutRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/batchPayouts/removeItems", JSON.toJSONString(request), RemoveItemsFromBatchPayoutResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/batchPayouts/listItems；响应已由公共客户端验签。 */
    public ListBatchPayoutItemsResponse listBatchPayoutItems(ListBatchPayoutItemsRequest request) {
        return apiClient.post("/api/open/v1/batchPayouts/listItems", JSON.toJSONString(request), ListBatchPayoutItemsResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public ListBatchPayoutItemsResponse listBatchPayoutItems(ListBatchPayoutItemsRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/batchPayouts/listItems", JSON.toJSONString(request), ListBatchPayoutItemsResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/batchPayouts/calculate；响应已由公共客户端验签。 */
    public CalculateBatchPayoutResponse calculateBatchPayout(CalculateBatchPayoutRequest request) {
        return apiClient.post("/api/open/v1/batchPayouts/calculate", JSON.toJSONString(request), CalculateBatchPayoutResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public CalculateBatchPayoutResponse calculateBatchPayout(CalculateBatchPayoutRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/batchPayouts/calculate", JSON.toJSONString(request), CalculateBatchPayoutResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/batchPayouts/submit；响应已由公共客户端验签。 */
    public SubmitBatchPayoutResponse submitBatchPayout(SubmitBatchPayoutRequest request) {
        return apiClient.post("/api/open/v1/batchPayouts/submit", JSON.toJSONString(request), SubmitBatchPayoutResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public SubmitBatchPayoutResponse submitBatchPayout(SubmitBatchPayoutRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/batchPayouts/submit", JSON.toJSONString(request), SubmitBatchPayoutResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/batchPayouts/query；响应已由公共客户端验签。 */
    public QueryBatchPayoutResponse queryBatchPayout(QueryBatchPayoutRequest request) {
        return apiClient.post("/api/open/v1/batchPayouts/query", JSON.toJSONString(request), QueryBatchPayoutResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public QueryBatchPayoutResponse queryBatchPayout(QueryBatchPayoutRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/batchPayouts/query", JSON.toJSONString(request), QueryBatchPayoutResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/batchPayouts/delete；响应已由公共客户端验签。 */
    public DeleteBatchPayoutResponse deleteBatchPayout(DeleteBatchPayoutRequest request) {
        return apiClient.post("/api/open/v1/batchPayouts/delete", JSON.toJSONString(request), DeleteBatchPayoutResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public DeleteBatchPayoutResponse deleteBatchPayout(DeleteBatchPayoutRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/batchPayouts/delete", JSON.toJSONString(request), DeleteBatchPayoutResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/batchPayouts/cancel；响应已由公共客户端验签。 */
    public CancelBatchPayoutResponse cancelBatchPayout(CancelBatchPayoutRequest request) {
        return apiClient.post("/api/open/v1/batchPayouts/cancel", JSON.toJSONString(request), CancelBatchPayoutResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public CancelBatchPayoutResponse cancelBatchPayout(CancelBatchPayoutRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/batchPayouts/cancel", JSON.toJSONString(request), CancelBatchPayoutResponse.class, context).getServiceResponse();
    }
}
