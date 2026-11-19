package {basePackage}.wf.model.domain;

import java.util.List;
import java.math.BigDecimal;

/** BatchPayoutCurrencyPair 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class BatchPayoutCurrencyPair {

    /** Currency pair in {sourceCurrency}/{payoutCurrency} format, composed of two ISO 4217 three-letter currency codes (for example, USD/EUR). See Data Types. */
    private String currencyPair;

    /** Indicative exchange rate applied to items in this currency pair for this reference preview. The final rate is determined at payout processing. Up to 6 decimal places. */
    private BigDecimal clientRate;

    /** Total amount debited from the payer across all items in this currency pair. See Data Types. */
    private Amount sourceAmount;

    /** Total amount received by beneficiaries across all items in this currency pair. See Data Types. */
    private Amount payoutAmount;

    /** Actual service fee rate applied, after any promotional discount. Returned when a service fee is charged. */
    private String feeRate;

    /** Payout service fee for this currency pair, after any promotional discount. Returned when a service fee is charged. See Data Types. */
    private Amount feeAmount;

    /** Itemized breakdown of the service fee, with one entry per fee item when multiple fee items apply. Returned when a service fee is charged. Up to 10 entries. */
    private List<BatchPayoutFeeItem> feeItemList;

    /** Service fee rate before promotional discounts. Returned when a promotion applies. Compare with feeRate to see the discount. */
    private String originalFeeRate;

    /** Service fee before promotional discounts. Returned when a promotion applies and a service fee is charged. Compare with feeAmount to see the discount. See Data Types. */
    private Amount originalFeeAmount;

    /** Promotions applied to this currency pair. Returned when a promotion applies. Up to 10 entries. */
    private List<BatchPayoutPromotion> promotionList;

    public String getCurrencyPair() { return currencyPair; }

    public void setCurrencyPair(String currencyPair) { this.currencyPair = currencyPair; }

    public BigDecimal getClientRate() { return clientRate; }

    public void setClientRate(BigDecimal clientRate) { this.clientRate = clientRate; }

    public Amount getSourceAmount() { return sourceAmount; }

    public void setSourceAmount(Amount sourceAmount) { this.sourceAmount = sourceAmount; }

    public Amount getPayoutAmount() { return payoutAmount; }

    public void setPayoutAmount(Amount payoutAmount) { this.payoutAmount = payoutAmount; }

    public String getFeeRate() { return feeRate; }

    public void setFeeRate(String feeRate) { this.feeRate = feeRate; }

    public Amount getFeeAmount() { return feeAmount; }

    public void setFeeAmount(Amount feeAmount) { this.feeAmount = feeAmount; }

    public List<BatchPayoutFeeItem> getFeeItemList() { return feeItemList; }

    public void setFeeItemList(List<BatchPayoutFeeItem> feeItemList) { this.feeItemList = feeItemList; }

    public String getOriginalFeeRate() { return originalFeeRate; }

    public void setOriginalFeeRate(String originalFeeRate) { this.originalFeeRate = originalFeeRate; }

    public Amount getOriginalFeeAmount() { return originalFeeAmount; }

    public void setOriginalFeeAmount(Amount originalFeeAmount) { this.originalFeeAmount = originalFeeAmount; }

    public List<BatchPayoutPromotion> getPromotionList() { return promotionList; }

    public void setPromotionList(List<BatchPayoutPromotion> promotionList) { this.promotionList = promotionList; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "BatchPayoutCurrencyPair[REDACTED]"; }
}
