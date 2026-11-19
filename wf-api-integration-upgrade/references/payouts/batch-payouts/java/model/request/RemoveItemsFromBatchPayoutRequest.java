package {basePackage}.wf.model.request;

import java.util.List;

/** remove_items_from_a_batch_payout request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class RemoveItemsFromBatchPayoutRequest {

    /** Required. Unique identifier of the target batch payout. */
    private String id;

    /** Required. IDs of the batch items to remove. Provide item IDs returned by Add Items to a Batch Payout or List Batch Payout Items. Max 100 elements. */
    private List<String> itemIds;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public List<String> getItemIds() { return itemIds; }

    public void setItemIds(List<String> itemIds) { this.itemIds = itemIds; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "RemoveItemsFromBatchPayoutRequest[REDACTED]"; }
}
