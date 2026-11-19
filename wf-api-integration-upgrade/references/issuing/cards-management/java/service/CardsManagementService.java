package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;
import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.client.WfRequestContext;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.request.CreateCardRequest;
import {basePackage}.wf.model.response.CreateCardResponse;
import {basePackage}.wf.model.request.QueryCardRequest;
import {basePackage}.wf.model.response.QueryCardResponse;
import {basePackage}.wf.model.request.ListCardsRequest;
import {basePackage}.wf.model.response.ListCardsResponse;
import {basePackage}.wf.model.request.QueryCardSensitiveInfoRequest;
import {basePackage}.wf.model.response.QueryCardSensitiveInfoResponse;
import {basePackage}.wf.model.request.UpdateCardRequest;
import {basePackage}.wf.model.response.UpdateCardResponse;

/** 卡片管理 API 薄封装。单次请求；业务状态和重试由调用方按 GUIDE 处理。 */
public class CardsManagementService {
    private final WfApiClient apiClient;

    public CardsManagementService(WfClientConfig config) { this(new WfApiClient(config)); }

    public CardsManagementService(WfApiClient apiClient) { this.apiClient = apiClient; }

    /** POST /api/open/v1/issuing/cards/create；响应已由公共客户端验签。 */
    public CreateCardResponse createCard(CreateCardRequest request) {
        return apiClient.post("/api/open/v1/issuing/cards/create", JSON.toJSONString(request), CreateCardResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public CreateCardResponse createCard(CreateCardRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/issuing/cards/create", JSON.toJSONString(request), CreateCardResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/issuing/cards/query；响应已由公共客户端验签。 */
    public QueryCardResponse queryCard(QueryCardRequest request) {
        return apiClient.post("/api/open/v1/issuing/cards/query", JSON.toJSONString(request), QueryCardResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public QueryCardResponse queryCard(QueryCardRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/issuing/cards/query", JSON.toJSONString(request), QueryCardResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/issuing/cards/list；响应已由公共客户端验签。 */
    public ListCardsResponse listCards(ListCardsRequest request) {
        return apiClient.post("/api/open/v1/issuing/cards/list", JSON.toJSONString(request), ListCardsResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public ListCardsResponse listCards(ListCardsRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/issuing/cards/list", JSON.toJSONString(request), ListCardsResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/issuing/cards/querySensitiveInfo；响应已由公共客户端验签。 */
    public QueryCardSensitiveInfoResponse queryCardSensitiveInfo(QueryCardSensitiveInfoRequest request) {
        return apiClient.post("/api/open/v1/issuing/cards/querySensitiveInfo", JSON.toJSONString(request), QueryCardSensitiveInfoResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public QueryCardSensitiveInfoResponse queryCardSensitiveInfo(QueryCardSensitiveInfoRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/issuing/cards/querySensitiveInfo", JSON.toJSONString(request), QueryCardSensitiveInfoResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/issuing/cards/update；响应已由公共客户端验签。 */
    public UpdateCardResponse updateCard(UpdateCardRequest request) {
        return apiClient.post("/api/open/v1/issuing/cards/update", JSON.toJSONString(request), UpdateCardResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public UpdateCardResponse updateCard(UpdateCardRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/issuing/cards/update", JSON.toJSONString(request), UpdateCardResponse.class, context).getServiceResponse();
    }
}
