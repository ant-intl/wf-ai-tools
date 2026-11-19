package {basePackage}.wf.model.response;


/** create_a_batch_payout response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CreateBatchPayoutResponse {

    /** See Result. */
    private Result result;

    /** Unique identifier of the created batch payout. Use this value for every subsequent addItems/calculate/submit/query call on the batch. */
    private String id;

    /** Current batch status. Newly created batches always return INIT. See BatchPayoutStatus. */
    private String status;

    /** Echoes the requestId submitted in the request. */
    private String requestId;

    /** Echoes the batchName submitted in the request, when provided. */
    private String batchName;

    /** Echoes the businessSceneCode submitted in the request, when provided. See BusinessSceneCode. */
    private String businessSceneCode;

    /** Echoes the fvtPreference submitted in the request, when provided. See FvtPreference. */
    private String fvtPreference;

    /** Echoes the oboPreference submitted in the request, when provided. See OboPreference. */
    private String oboPreference;

    /** Echoes the paymentNetwork submitted in the request, when provided. See PaymentNetwork. */
    private String paymentNetwork;

    /** Echoes the paymentType submitted in the request, when provided. See PaymentType. */
    private String paymentType;

    /** Echoes the transferMemo submitted in the request, when provided. */
    private String transferMemo;

    /** Total number of items currently in the batch. Zero at creation time; increases as items are added. */
    private Integer totalItemCount;

    /** Number of items that passed validation. Zero at creation time; updated as items are added and after calculate. */
    private Integer validItemCount;

    /** Batch creation time. ISO 8601 extended format. */
    private String createdAt;

    /** Last update time. ISO 8601 extended format. */
    private String updatedAt;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getBatchName() { return batchName; }

    public void setBatchName(String batchName) { this.batchName = batchName; }

    public String getBusinessSceneCode() { return businessSceneCode; }

    public void setBusinessSceneCode(String businessSceneCode) { this.businessSceneCode = businessSceneCode; }

    public String getFvtPreference() { return fvtPreference; }

    public void setFvtPreference(String fvtPreference) { this.fvtPreference = fvtPreference; }

    public String getOboPreference() { return oboPreference; }

    public void setOboPreference(String oboPreference) { this.oboPreference = oboPreference; }

    public String getPaymentNetwork() { return paymentNetwork; }

    public void setPaymentNetwork(String paymentNetwork) { this.paymentNetwork = paymentNetwork; }

    public String getPaymentType() { return paymentType; }

    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getTransferMemo() { return transferMemo; }

    public void setTransferMemo(String transferMemo) { this.transferMemo = transferMemo; }

    public Integer getTotalItemCount() { return totalItemCount; }

    public void setTotalItemCount(Integer totalItemCount) { this.totalItemCount = totalItemCount; }

    public Integer getValidItemCount() { return validItemCount; }

    public void setValidItemCount(Integer validItemCount) { this.validItemCount = validItemCount; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CreateBatchPayoutResponse[REDACTED]"; }
}
