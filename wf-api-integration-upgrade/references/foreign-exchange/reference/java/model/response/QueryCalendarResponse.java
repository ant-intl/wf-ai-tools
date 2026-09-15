package {basePackage}.wf.model.response;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_trading_calendar 响应对象。
 *
 * <p>包含接口调用结果及可用日期列表。
 */
public class QueryCalendarResponse {

    /** 接口调用结果 */
    private Result result;

    /** 可用日期列表，YYYY-MM-DD 格式 */
    private List<String> availableDates;

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

    /**
     * Getter method for property <tt>availableDates</tt>.
     *
     * @return property value of availableDates
     */
    public List<String> getAvailableDates() {
        return availableDates;
    }

    /**
     * Setter method for property <tt>availableDates</tt>.
     *
     * @param availableDates value to be assigned to property availableDates
     */
    public void setAvailableDates(List<String> availableDates) {
        this.availableDates = availableDates;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
