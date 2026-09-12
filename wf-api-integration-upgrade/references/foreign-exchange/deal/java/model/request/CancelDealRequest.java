package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst cancel_a_deal 请求对象。
 *
 * <p>取消尚未结算的交易。仅处于 PROCESSING 状态的交易可被取消。
 */
public class CancelDealRequest {

    /**
     * 要取消的交易 ID。
     * <p>交易必须处于 PROCESSING 状态且属于当前账户。
     */
    private String id;

    public CancelDealRequest() {
    }

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
