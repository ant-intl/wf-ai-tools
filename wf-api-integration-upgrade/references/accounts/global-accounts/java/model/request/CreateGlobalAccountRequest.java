package {basePackage}.wf.model.request;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import {basePackage}.wf.model.domain.RequiredFeature;

/**
 * WorldFirst create_a_global_account 请求对象。
 *
 * <p>用于开通全球收款账户，指定银行地区和所需收款能力。
 */
public class CreateGlobalAccountRequest {

    /**
     * 幂等键，唯一请求标识。
     * <p>每次操作使用唯一值（如 UUID），重复的 requestId 会被拒绝。
     */
    private String requestId;

    /**
     * 银行所在地区（ISO 3166），如 US、GB、HK。
     * <p>详见 WF 官方文档 Data Types。
     */
    private String bankRegion;

    /**
     * 申请的收款能力列表，每个元素指定一种币种 + 支付方式组合。
     * <p>最多 10 个元素。
     */
    private List<RequiredFeature> requiredFeatures;

    /**
     * 用户自定义账户标签，用于内部标识，不显示在银行对账单上。
     * <p>不提供时默认使用商户名称。
     */
    private String nickName;

    /**
     * Getter method for property <tt>requestId</tt>.
     *
     * @return property value of requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Setter method for property <tt>requestId</tt>.
     *
     * @param requestId value to be assigned to property requestId
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Getter method for property <tt>bankRegion</tt>.
     *
     * @return property value of bankRegion
     */
    public String getBankRegion() {
        return bankRegion;
    }

    /**
     * Setter method for property <tt>bankRegion</tt>.
     *
     * @param bankRegion value to be assigned to property bankRegion
     */
    public void setBankRegion(String bankRegion) {
        this.bankRegion = bankRegion;
    }

    /**
     * Getter method for property <tt>requiredFeatures</tt>.
     *
     * @return property value of requiredFeatures
     */
    public List<RequiredFeature> getRequiredFeatures() {
        return requiredFeatures;
    }

    /**
     * Setter method for property <tt>requiredFeatures</tt>.
     *
     * @param requiredFeatures value to be assigned to property requiredFeatures
     */
    public void setRequiredFeatures(List<RequiredFeature> requiredFeatures) {
        this.requiredFeatures = requiredFeatures;
    }

    /**
     * Getter method for property <tt>nickName</tt>.
     *
     * @return property value of nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Setter method for property <tt>nickName</tt>.
     *
     * @param nickName value to be assigned to property nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
