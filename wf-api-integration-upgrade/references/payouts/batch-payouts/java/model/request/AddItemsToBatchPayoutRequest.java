package {basePackage}.wf.model.request;

import java.util.List;
import {basePackage}.wf.model.domain.BatchPayoutItemInput;

/** add_items_to_a_batch_payout request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class AddItemsToBatchPayoutRequest {

    /** Required. Unique identifier of the target batch, returned by Create a Batch Payout. */
    private String id;

    /** Required. Payout items to append to the batch. Provide 1 to 10 items per request. Build each item from the request of the matching payout scenario in Create a Payout. Items in the same batch can use different payout scenarios. Note: fvtPreference, oboPreference, paymentType, and paymentNetwork are defined in Create a Batch Payout and applied to every item in the batch. transferQuote is not applicable; call Calculate a Batch Payout to preview amounts, fees, and FX rates later. */
    private List<BatchPayoutItemInput> items;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public List<BatchPayoutItemInput> getItems() { return items; }

    public void setItems(List<BatchPayoutItemInput> items) { this.items = items; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "AddItemsToBatchPayoutRequest[REDACTED]"; }
}
