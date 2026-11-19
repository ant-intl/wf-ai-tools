package {basePackage}.wf.model.response;

import java.util.List;
import {basePackage}.wf.model.domain.BatchPayoutCalculationSummary;
import {basePackage}.wf.model.domain.BatchPayoutItem;

/** add_items_to_a_batch_payout response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class AddItemsToBatchPayoutResponse {

    /** API call result. See Result. */
    private Result result;

    /** Unique identifier of the batch payout. */
    private String id;

    /** Current batch lifecycle status after the operation. See BatchPayoutStatus. */
    private String status;

    /** Time the batch was created, in ISO 8601 extended format. */
    private String createdAt;

    /** Time the batch was most recently updated, in ISO 8601 extended format. */
    private String updatedAt;

    /** Idempotency key supplied when the batch was created. */
    private String requestId;

    /** Human-readable batch name, if provided at creation. */
    private String batchName;

    /** Internal memo attached to the batch, if provided at creation. */
    private String transferMemo;

    /** Business scene code set on the batch, if provided at creation. See BusinessSceneCode. */
    private String businessSceneCode;

    /** Payment clearing network set on the batch, if provided at creation. See PaymentNetwork. */
    private String paymentNetwork;

    /** Payment channel type set on the batch, if provided at creation. See PaymentType. */
    private String paymentType;

    /** Full value transfer (FVT) preference set on the batch, if provided at creation. See FvtPreference. */
    private String fvtPreference;

    /** On-behalf-of (OBO) routing preference set on the batch, if provided at creation. See OboPreference. */
    private String oboPreference;

    /** Total number of payout items currently in the batch, from 0 to 100. */
    private Integer totalItemCount;

    /** Number of payout items in the batch that have passed validation, from 0 to 100. */
    private Integer validItemCount;

    /** Latest reference pricing summary. Returned once calculate (or submit) has completed pricing. All totals and rates are indicative — final values are determined at payout processing. */
    private BatchPayoutCalculationSummary calculationSummary;

    /** Results for the items submitted in this request. */
    private List<BatchPayoutItem> items;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getBatchName() { return batchName; }

    public void setBatchName(String batchName) { this.batchName = batchName; }

    public String getTransferMemo() { return transferMemo; }

    public void setTransferMemo(String transferMemo) { this.transferMemo = transferMemo; }

    public String getBusinessSceneCode() { return businessSceneCode; }

    public void setBusinessSceneCode(String businessSceneCode) { this.businessSceneCode = businessSceneCode; }

    public String getPaymentNetwork() { return paymentNetwork; }

    public void setPaymentNetwork(String paymentNetwork) { this.paymentNetwork = paymentNetwork; }

    public String getPaymentType() { return paymentType; }

    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getFvtPreference() { return fvtPreference; }

    public void setFvtPreference(String fvtPreference) { this.fvtPreference = fvtPreference; }

    public String getOboPreference() { return oboPreference; }

    public void setOboPreference(String oboPreference) { this.oboPreference = oboPreference; }

    public Integer getTotalItemCount() { return totalItemCount; }

    public void setTotalItemCount(Integer totalItemCount) { this.totalItemCount = totalItemCount; }

    public Integer getValidItemCount() { return validItemCount; }

    public void setValidItemCount(Integer validItemCount) { this.validItemCount = validItemCount; }

    public BatchPayoutCalculationSummary getCalculationSummary() { return calculationSummary; }

    public void setCalculationSummary(BatchPayoutCalculationSummary calculationSummary) { this.calculationSummary = calculationSummary; }

    public List<BatchPayoutItem> getItems() { return items; }

    public void setItems(List<BatchPayoutItem> items) { this.items = items; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "AddItemsToBatchPayoutResponse[REDACTED]"; }
}
