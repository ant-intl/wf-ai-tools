package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;
import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.client.WfRequestContext;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.request.CreateReportRequest;
import {basePackage}.wf.model.response.CreateReportResponse;
import {basePackage}.wf.model.request.QueryReportRequest;
import {basePackage}.wf.model.response.QueryReportResponse;
import {basePackage}.wf.model.request.ListReportsRequest;
import {basePackage}.wf.model.response.ListReportsResponse;

/** 报表下载 API 薄封装。单次请求；业务状态和重试由调用方按 GUIDE 处理。 */
public class DownloadReportService {
    private final WfApiClient apiClient;

    public DownloadReportService(WfClientConfig config) { this(new WfApiClient(config)); }

    public DownloadReportService(WfApiClient apiClient) { this.apiClient = apiClient; }

    /** POST /api/open/v1/reports/create；响应已由公共客户端验签。 */
    public CreateReportResponse createReport(CreateReportRequest request) {
        return apiClient.post("/api/open/v1/reports/create", JSON.toJSONString(request), CreateReportResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public CreateReportResponse createReport(CreateReportRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/reports/create", JSON.toJSONString(request), CreateReportResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/reports/query；响应已由公共客户端验签。 */
    public QueryReportResponse queryReport(QueryReportRequest request) {
        return apiClient.post("/api/open/v1/reports/query", JSON.toJSONString(request), QueryReportResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public QueryReportResponse queryReport(QueryReportRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/reports/query", JSON.toJSONString(request), QueryReportResponse.class, context).getServiceResponse();
    }

    /** POST /api/open/v1/reports/list；响应已由公共客户端验签。 */
    public ListReportsResponse listReports(ListReportsRequest request) {
        return apiClient.post("/api/open/v1/reports/list", JSON.toJSONString(request), ListReportsResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public ListReportsResponse listReports(ListReportsRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/reports/list", JSON.toJSONString(request), ListReportsResponse.class, context).getServiceResponse();
    }
}
