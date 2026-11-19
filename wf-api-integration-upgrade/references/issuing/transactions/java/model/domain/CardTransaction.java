package {basePackage}.wf.model.domain;


/** CardTransaction 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CardTransaction {

    /** Transaction ID. Max 64 characters. */
    private String id;

    /** Creation time. ISO 8601 extended format. */
    private String createdAt;

    /** Current transaction status. - `WAITING_DEDUCT`: Awaiting deduction. - `PROCESSING`: Processing. - `WAITING_CLEARANCE`: Awaiting clearance. - `SUCCESS`: Transaction succeeded. - `FAILED`: Transaction failed. - `PARTIAL_CANCELLED`: Partial authorization cancelled. - `CANCELLED`: Authorization cancelled. - `PARTIAL_REFUNDED`: Partially refunded. - `REFUNDED`: Refunded. */
    private String status;

    /** Transaction time. ISO 8601 extended format. */
    private String transactedAt;

    /** Update time. Returned only in Webhook notification snapshots. ISO 8601 extended format. */
    private String updatedAt;

    /** Masked card number. Max 32 characters. */
    private String maskedCardNo;

    /** Card nickname. Max 128 characters. */
    private String cardNickName;

    /** Associated Card resource ID. Returned only for card payment and refund transactions (billType = CARD_PAYMENT or CARD_REFUND). Max 64 characters. */
    private String cardId;

    /** Merchant name. Max 256 characters. */
    private String merchantName;

    /** Merchant category code (MCC). Max 16 characters. */
    private String merchantCategoryCode;

    /** Merchant region. 2-letter ISO 3166 country code. */
    private String merchantRegion;

    /** Authorization code. Max 32 characters. */
    private String authCode;

    /** Transaction region. 2-letter ISO 3166 country code. */
    private String tradeRegion;

    /** Transaction amount with currency. See Data Types. */
    private Amount tradeAmount;

    /** Inflow amount credited to the card, for example, a refund. See Data Types. */
    private Amount inAmount;

    /** Outflow amount debited from the card. See Data Types. */
    private Amount outAmount;

    /** Exchange rate applied to the transaction. Max 32 characters. */
    private String exchangeRate;

    /** Bill type. - `CARD_PAYMENT`: Card payment. - `CARD_REFUND`: An unreferenced refund, a refund that is not linked to an original card payment transaction. */
    private String billType;

    /** Payer account number. Returned by query only. Max 64 characters. */
    private String payerAccountNo;

    /** Payer account type. - `NORMAL_BALANCE`: Standard balance. - `SAME_NAME_TOP_UP_BALANCE`: Same-name top-up balance. - `BUDGET_BALANCE`: Budget balance. */
    private String payerBalanceType;

    /** Payer budget account ID. Max 64 characters. */
    private String payerBudgetId;

    /** Transaction failure reason code. Returned only when status=FAILED. */
    private String failureCode;

    /** Transaction failure description. Returned only when status=FAILED. */
    private String failureMessage;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getTransactedAt() { return transactedAt; }

    public void setTransactedAt(String transactedAt) { this.transactedAt = transactedAt; }

    public String getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getMaskedCardNo() { return maskedCardNo; }

    public void setMaskedCardNo(String maskedCardNo) { this.maskedCardNo = maskedCardNo; }

    public String getCardNickName() { return cardNickName; }

    public void setCardNickName(String cardNickName) { this.cardNickName = cardNickName; }

    public String getCardId() { return cardId; }

    public void setCardId(String cardId) { this.cardId = cardId; }

    public String getMerchantName() { return merchantName; }

    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }

    public String getMerchantCategoryCode() { return merchantCategoryCode; }

    public void setMerchantCategoryCode(String merchantCategoryCode) { this.merchantCategoryCode = merchantCategoryCode; }

    public String getMerchantRegion() { return merchantRegion; }

    public void setMerchantRegion(String merchantRegion) { this.merchantRegion = merchantRegion; }

    public String getAuthCode() { return authCode; }

    public void setAuthCode(String authCode) { this.authCode = authCode; }

    public String getTradeRegion() { return tradeRegion; }

    public void setTradeRegion(String tradeRegion) { this.tradeRegion = tradeRegion; }

    public Amount getTradeAmount() { return tradeAmount; }

    public void setTradeAmount(Amount tradeAmount) { this.tradeAmount = tradeAmount; }

    public Amount getInAmount() { return inAmount; }

    public void setInAmount(Amount inAmount) { this.inAmount = inAmount; }

    public Amount getOutAmount() { return outAmount; }

    public void setOutAmount(Amount outAmount) { this.outAmount = outAmount; }

    public String getExchangeRate() { return exchangeRate; }

    public void setExchangeRate(String exchangeRate) { this.exchangeRate = exchangeRate; }

    public String getBillType() { return billType; }

    public void setBillType(String billType) { this.billType = billType; }

    public String getPayerAccountNo() { return payerAccountNo; }

    public void setPayerAccountNo(String payerAccountNo) { this.payerAccountNo = payerAccountNo; }

    public String getPayerBalanceType() { return payerBalanceType; }

    public void setPayerBalanceType(String payerBalanceType) { this.payerBalanceType = payerBalanceType; }

    public String getPayerBudgetId() { return payerBudgetId; }

    public void setPayerBudgetId(String payerBudgetId) { this.payerBudgetId = payerBudgetId; }

    public String getFailureCode() { return failureCode; }

    public void setFailureCode(String failureCode) { this.failureCode = failureCode; }

    public String getFailureMessage() { return failureMessage; }

    public void setFailureMessage(String failureMessage) { this.failureMessage = failureMessage; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CardTransaction[REDACTED]"; }
}
