package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 按币种拆分的额度对象。
 *
 * <p>描述单个币种下的可用结算额度。
 */
public class QuotaByCurrency {

    /** 额度金额，最小货币单位（如 USD 的分） */
    private Long value;

    /** 币种代码，ISO 4217 三字母代码 */
    private String currency;

    public QuotaByCurrency() {
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
