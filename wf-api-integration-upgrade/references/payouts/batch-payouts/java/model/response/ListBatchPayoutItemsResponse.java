package {basePackage}.wf.model.response;

import java.util.List;
import {basePackage}.wf.model.domain.BatchPayoutItem;

/** list_batch_payout_items response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class ListBatchPayoutItemsResponse {

    /** See Result. */
    private Result result;

    /** Batch items on the current page. Max 100 elements. Each item follows the Query a Payout schema, with itemId replacing id. The itemId is the payout identifier; pass it as id to Query a Payout. */
    private List<BatchPayoutItem> items;

    /** Cursor for the next page. Returned only when more results are available. Pass it back as cursor to fetch the next page. Max length: 256 characters. */
    private String nextCursor;

    /** Cursor for the previous page. Returned only when a previous page exists. Max length: 256 characters. */
    private String prevCursor;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public List<BatchPayoutItem> getItems() { return items; }

    public void setItems(List<BatchPayoutItem> items) { this.items = items; }

    public String getNextCursor() { return nextCursor; }

    public void setNextCursor(String nextCursor) { this.nextCursor = nextCursor; }

    public String getPrevCursor() { return prevCursor; }

    public void setPrevCursor(String prevCursor) { this.prevCursor = prevCursor; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "ListBatchPayoutItemsResponse[REDACTED]"; }
}
