package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;
import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.client.WfRequestContext;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.request.RetrieveRfiRequest;
import {basePackage}.wf.model.response.RetrieveRfiResponse;
import {basePackage}.wf.model.request.RespondToRfiRequest;
import {basePackage}.wf.model.response.RespondToRfiResponse;
import {basePackage}.wf.model.request.ListRfisRequest;
import {basePackage}.wf.model.response.ListRfisResponse;

/** 补充资料 RFI API 薄封装。单次请求；业务状态和重试由调用方按 GUIDE 处理。 */
public class RfiService {
    private final WfApiClient apiClient;

    public RfiService(WfClientConfig config) { this(new WfApiClient(config)); }

    public RfiService(WfApiClient apiClient) { this.apiClient = apiClient; }

    /** POST /api/open/v1/rfis/query；响应已由公共客户端验签。 */
    public RetrieveRfiResponse retrieveRfi(RetrieveRfiRequest request) {
        return apiClient.post("/api/open/v1/rfis/query", JSON.toJSONString(request), RetrieveRfiResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public RetrieveRfiResponse retrieveRfi(RetrieveRfiRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/rfis/query", JSON.toJSONString(request), RetrieveRfiResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/rfis/respond；响应已由公共客户端验签。 */
    public RespondToRfiResponse respondToRfi(RespondToRfiRequest request) {
        return apiClient.post("/api/open/v1/rfis/respond", JSON.toJSONString(request), RespondToRfiResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public RespondToRfiResponse respondToRfi(RespondToRfiRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/rfis/respond", JSON.toJSONString(request), RespondToRfiResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/rfis/list；响应已由公共客户端验签。 */
    public ListRfisResponse listRfis(ListRfisRequest request) {
        return apiClient.post("/api/open/v1/rfis/list", JSON.toJSONString(request), ListRfisResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public ListRfisResponse listRfis(ListRfisRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/rfis/list", JSON.toJSONString(request), ListRfisResponse.class, context).getServiceResponse();
    }
}
