package {basePackage}.wf.model.response;

import java.util.List;
import {basePackage}.wf.model.domain.RfiSummary;

/** list_rfis response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class ListRfisResponse {

    /** See Result. */
    private Result result;

    /** Cursor token for the next page. Pass it as cursor to fetch the following page. null when there is no next page. */
    private String nextCursor;

    /** Cursor token for the previous page. Pass it as cursor to fetch the preceding page. null when the current page is the first page. */
    private String prevCursor;

    /** List of RFI summaries, ordered by creation time. Each summary omits the questions array; retrieve full question details with Retrieve an RFI. */
    private List<RfiSummary> items;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public String getNextCursor() { return nextCursor; }

    public void setNextCursor(String nextCursor) { this.nextCursor = nextCursor; }

    public String getPrevCursor() { return prevCursor; }

    public void setPrevCursor(String prevCursor) { this.prevCursor = prevCursor; }

    public List<RfiSummary> getItems() { return items; }

    public void setItems(List<RfiSummary> items) { this.items = items; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "ListRfisResponse[REDACTED]"; }
}
