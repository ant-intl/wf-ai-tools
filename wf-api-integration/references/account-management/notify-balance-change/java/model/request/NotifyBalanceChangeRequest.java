package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.BalanceChangeLog;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst notifyBalanceChange 回调请求对象。
 *
 * <p>当集成商的万里汇余额账户发生动账交易后，万里汇会通过本请求通知集成商所有账务变动信息。
 * {@code notifySequence} 为通知时序，集成商可用于排序和去重。
 */
public class NotifyBalanceChangeRequest {

    /** 通知时序，即万里汇向集成商发送通知的累计序号 */
    private Integer notifySequence;

    /** 账户余额变动记录列表 */
    private List<BalanceChangeLog> balanceChangeLogs;

    /**
     * Getter method for property <tt>notifySequence</tt>.
     *
     * @return property value of notifySequence
     */
    public Integer getNotifySequence() {
        return notifySequence;
    }

    /**
     * Setter method for property <tt>notifySequence</tt>.
     *
     * @param notifySequence value to be assigned to property notifySequence
     */
    public void setNotifySequence(Integer notifySequence) {
        this.notifySequence = notifySequence;
    }

    /**
     * Getter method for property <tt>balanceChangeLogs</tt>.
     *
     * @return property value of balanceChangeLogs
     */
    public List<BalanceChangeLog> getBalanceChangeLogs() {
        return balanceChangeLogs;
    }

    /**
     * Setter method for property <tt>balanceChangeLogs</tt>.
     *
     * @param balanceChangeLogs value to be assigned to property balanceChangeLogs
     */
    public void setBalanceChangeLogs(List<BalanceChangeLog> balanceChangeLogs) {
        this.balanceChangeLogs = balanceChangeLogs;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
