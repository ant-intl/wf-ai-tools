package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.PayerBankAccount;
import {basePackage}.wf.model.domain.VostroBeneficiaryAccount;
import {basePackage}.wf.model.response.Result;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst notifyVostro 回调请求对象。
 *
 * <p>当集成商的万里汇账户发生充值后，万里汇会通过本请求通知集成商资金变化信息。
 * {@code fundingId} 为幂等字段，集成商可用于去重。
 */
public class NotifyVostroRequest {

    /**
     * 由万里汇定义、用于唯一标识某次垫付请求。
     * 幂等字段，最大 128 字符。
     */
    private String fundingId;

    /** 代表本次垫付是否成功 */
    private Result balanceResult;

    /** 付款人银行账户信息 */
    private PayerBankAccount payerBankAccount;

    /** 收款人万里汇 VA 账户信息 */
    private VostroBeneficiaryAccount beneficiaryAccount;

    /** 账户余额变动的金额 */
    private Amount balanceChangeAmount;

    /** 账户余额变动的时间，ISO 8601 格式 */
    private String balanceChangeTime;

    /** 垫付请求附加信息，最大 530 字符 */
    private String remitInfo;

    /**
     * Getter method for property <tt>fundingId</tt>.
     *
     * @return property value of fundingId
     */
    public String getFundingId() {
        return fundingId;
    }

    /**
     * Setter method for property <tt>fundingId</tt>.
     *
     * @param fundingId value to be assigned to property fundingId
     */
    public void setFundingId(String fundingId) {
        this.fundingId = fundingId;
    }

    /**
     * Getter method for property <tt>balanceResult</tt>.
     *
     * @return property value of balanceResult
     */
    public Result getBalanceResult() {
        return balanceResult;
    }

    /**
     * Setter method for property <tt>balanceResult</tt>.
     *
     * @param balanceResult value to be assigned to property balanceResult
     */
    public void setBalanceResult(Result balanceResult) {
        this.balanceResult = balanceResult;
    }

    /**
     * Getter method for property <tt>payerBankAccount</tt>.
     *
     * @return property value of payerBankAccount
     */
    public PayerBankAccount getPayerBankAccount() {
        return payerBankAccount;
    }

    /**
     * Setter method for property <tt>payerBankAccount</tt>.
     *
     * @param payerBankAccount value to be assigned to property payerBankAccount
     */
    public void setPayerBankAccount(PayerBankAccount payerBankAccount) {
        this.payerBankAccount = payerBankAccount;
    }

    /**
     * Getter method for property <tt>beneficiaryAccount</tt>.
     *
     * @return property value of beneficiaryAccount
     */
    public VostroBeneficiaryAccount getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    /**
     * Setter method for property <tt>beneficiaryAccount</tt>.
     *
     * @param beneficiaryAccount value to be assigned to property beneficiaryAccount
     */
    public void setBeneficiaryAccount(VostroBeneficiaryAccount beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    /**
     * Getter method for property <tt>balanceChangeAmount</tt>.
     *
     * @return property value of balanceChangeAmount
     */
    public Amount getBalanceChangeAmount() {
        return balanceChangeAmount;
    }

    /**
     * Setter method for property <tt>balanceChangeAmount</tt>.
     *
     * @param balanceChangeAmount value to be assigned to property balanceChangeAmount
     */
    public void setBalanceChangeAmount(Amount balanceChangeAmount) {
        this.balanceChangeAmount = balanceChangeAmount;
    }

    /**
     * Getter method for property <tt>balanceChangeTime</tt>.
     *
     * @return property value of balanceChangeTime
     */
    public String getBalanceChangeTime() {
        return balanceChangeTime;
    }

    /**
     * Setter method for property <tt>balanceChangeTime</tt>.
     *
     * @param balanceChangeTime value to be assigned to property balanceChangeTime
     */
    public void setBalanceChangeTime(String balanceChangeTime) {
        this.balanceChangeTime = balanceChangeTime;
    }

    /**
     * Getter method for property <tt>remitInfo</tt>.
     *
     * @return property value of remitInfo
     */
    public String getRemitInfo() {
        return remitInfo;
    }

    /**
     * Setter method for property <tt>remitInfo</tt>.
     *
     * @param remitInfo value to be assigned to property remitInfo
     */
    public void setRemitInfo(String remitInfo) {
        this.remitInfo = remitInfo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
