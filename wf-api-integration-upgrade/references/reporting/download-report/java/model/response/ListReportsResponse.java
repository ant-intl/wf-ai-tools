package {basePackage}.wf.model.response;

import java.util.List;
import {basePackage}.wf.model.domain.DownloadReportSummary;

/** list_reports response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class ListReportsResponse {

    /** See Result. */
    private Result result;

    /** List of report tasks matching the query. In list responses, download links are not included; call Query a Report to obtain them. */
    private List<DownloadReportSummary> items;

    /** Cursor for the next page. Returned when more records are available; pass it as cursor to fetch the next page. Absent on the last page. */
    private String nextCursor;

    /** Cursor for the previous page. Returned when a previous page exists; pass it as cursor to fetch the previous page. */
    private String prevCursor;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public List<DownloadReportSummary> getItems() { return items; }

    public void setItems(List<DownloadReportSummary> items) { this.items = items; }

    public String getNextCursor() { return nextCursor; }

    public void setNextCursor(String nextCursor) { this.nextCursor = nextCursor; }

    public String getPrevCursor() { return prevCursor; }

    public void setPrevCursor(String prevCursor) { this.prevCursor = prevCursor; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "ListReportsResponse[REDACTED]"; }
}
