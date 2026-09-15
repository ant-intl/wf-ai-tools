package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst delete_a_cardholder 响应对象。
 *
 * <p>删除为永久操作；{@code resultStatus=S} 即表示持卡人已删除成功，响应不含其他业务字段。
 */
public class DeleteCardholderResponse {

    /** 接口调用结果，resultStatus 为 S 表示删除成功 */
    private Result result;

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
