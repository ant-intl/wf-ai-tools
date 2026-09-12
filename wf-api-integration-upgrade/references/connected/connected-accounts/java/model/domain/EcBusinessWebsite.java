package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 电商网站信息对象。
 *
 * <p>描述关联账户的电商网站 URL 信息。
 */
public class EcBusinessWebsite {

    /** 电商网站 URL，最大1024字符 */
    private String url;

    public EcBusinessWebsite() {
    }

    /**
     * Getter method for property <tt>url</tt>.
     *
     * @return property value of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter method for property <tt>url</tt>.
     *
     * @param url value to be assigned to property url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
