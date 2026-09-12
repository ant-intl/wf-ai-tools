package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_budgets 请求对象。
 *
 * <p>用于列出当前账户下全部预算账户。本接口无请求参数，响应不含余额与分页游标，
 * 最多返回 3 条记录。
 * <p>本对象无业务字段，序列化后为空对象 {@code {}}；保留用于后续过滤条件扩展。
 */
public class ListBudgetsRequest {

    public ListBudgetsRequest() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
