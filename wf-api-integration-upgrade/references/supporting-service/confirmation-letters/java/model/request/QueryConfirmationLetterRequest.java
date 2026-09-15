package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_a_confirmation_letter 请求对象。
 *
 * <p>查询确认函下载任务的状态和结果。
 */
public class QueryConfirmationLetterRequest {

    /**
     * 下载任务的唯一标识符。
     * <p>由 Create a Confirmation Letter 接口返回。
     * <p>最大 64 字符。
     */
    private String id;

    public QueryConfirmationLetterRequest() {
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
