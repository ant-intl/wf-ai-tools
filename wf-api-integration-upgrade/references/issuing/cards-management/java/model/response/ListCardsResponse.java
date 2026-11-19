package {basePackage}.wf.model.response;

import java.util.List;
import {basePackage}.wf.model.domain.CardSummary;

/** list_cards response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class ListCardsResponse {

    /** See Result. */
    private Result result;

    /** Card summary array. Maximum 100 entries. Each element includes only summary fields; fetch full transaction limits and authorization controls via Query a Card. */
    private List<CardSummary> items;

    /** Cursor for the next page. null indicates the last page. */
    private String nextCursor;

    /** Cursor for the previous page. null indicates the first page. */
    private String prevCursor;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public List<CardSummary> getItems() { return items; }

    public void setItems(List<CardSummary> items) { this.items = items; }

    public String getNextCursor() { return nextCursor; }

    public void setNextCursor(String nextCursor) { this.nextCursor = nextCursor; }

    public String getPrevCursor() { return prevCursor; }

    public void setPrevCursor(String prevCursor) { this.prevCursor = prevCursor; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "ListCardsResponse[REDACTED]"; }
}
