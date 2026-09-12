package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst delete_a_cardholder 请求对象。
 *
 * <p>永久删除名下已无关联卡片的持卡人。
 */
public class DeleteCardholderRequest {

    /**
     * 待删除的持卡人唯一标识，最大 64 字符。
     */
    private String id;

    public DeleteCardholderRequest() {
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
