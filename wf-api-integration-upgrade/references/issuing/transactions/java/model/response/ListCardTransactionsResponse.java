package {basePackage}.wf.model.response;

import java.util.List;
import {basePackage}.wf.model.domain.CardTransaction;

/** list_card_transactions response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class ListCardTransactionsResponse {

    /** See Result. */
    private Result result;

    /** Card transaction list. Returned only when resultStatus=S. */
    private List<CardTransaction> items;

    /** Cursor for the next page. An empty value indicates there is no next page. Max 256 characters. */
    private String nextCursor;

    /** Cursor for the previous page. An empty value indicates there is no previous page. Max 256 characters. */
    private String prevCursor;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public List<CardTransaction> getItems() { return items; }

    public void setItems(List<CardTransaction> items) { this.items = items; }

    public String getNextCursor() { return nextCursor; }

    public void setNextCursor(String nextCursor) { this.nextCursor = nextCursor; }

    public String getPrevCursor() { return prevCursor; }

    public void setPrevCursor(String prevCursor) { this.prevCursor = prevCursor; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "ListCardTransactionsResponse[REDACTED]"; }
}
