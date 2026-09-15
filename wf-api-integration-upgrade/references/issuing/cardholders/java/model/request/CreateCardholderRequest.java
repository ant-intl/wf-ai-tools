package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.Identification;
import {basePackage}.wf.model.domain.PersonInfo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_cardholder 请求对象。
 *
 * <p>注册持卡人并提交身份材料进入 KYC 审核。
 */
public class CreateCardholderRequest {

    /**
     * 幂等键，最大 64 字符。
     * <p>每次创建请求必须唯一（建议 UUID），保证网络重试不会重复创建持卡人。
     */
    private String requestId;

    /**
     * 持卡人类型（CardholderType 枚举）。
     * <p>取值决定所上传身份材料文件的解析方式。
     */
    private String type;

    /**
     * 持卡人个人详情，用于 KYC 审核。
     */
    private PersonInfo personInfo;

    /**
     * 持卡人国籍，ISO 3166-1 alpha-3 三字母国家代码（如 CHN）。
     */
    private String nationality;

    /**
     * 提交 KYC 审核的身份证明材料。
     */
    private Identification identification;

    public CreateCardholderRequest() {
    }

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
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     *
     * @param type value to be assigned to property type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>personInfo</tt>.
     *
     * @return property value of personInfo
     */
    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    /**
     * Setter method for property <tt>personInfo</tt>.
     *
     * @param personInfo value to be assigned to property personInfo
     */
    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    /**
     * Getter method for property <tt>nationality</tt>.
     *
     * @return property value of nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Setter method for property <tt>nationality</tt>.
     *
     * @param nationality value to be assigned to property nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Getter method for property <tt>identification</tt>.
     *
     * @return property value of identification
     */
    public Identification getIdentification() {
        return identification;
    }

    /**
     * Setter method for property <tt>identification</tt>.
     *
     * @param identification value to be assigned to property identification
     */
    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
