package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.DownloadReport;

/** query_a_report response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class QueryReportResponse {

    /** See Result. */
    private Result result;

    /** Report task details. */
    private DownloadReport report;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public DownloadReport getReport() { return report; }

    public void setReport(DownloadReport report) { this.report = report; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "QueryReportResponse[REDACTED]"; }
}
