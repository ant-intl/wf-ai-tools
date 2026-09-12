package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.PersonInfo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_a_cardholder 响应对象。
 *
 * <p>包含接口调用结果及持卡人完整详情；创建后可轮询 status 字段获知 KYC 审核结果。
 */
public class QueryCardholderResponse {

    /** 接口调用结果 */
    private Result result;

    /** 持卡人唯一标识，最大 64 字符 */
    private String id;

    /** 持卡人类型 */
    private String type;

    /** 持卡人当前状态，创建后可轮询此字段获知 KYC 审核结果 */
    private String status;

    /** 持卡人个人信息 */
    private PersonInfo personInfo;

    /** 持卡人国籍，ISO 3166-1 alpha-3 三字母国家代码 */
    private String nationality;

    /** 持卡人创建时间（ISO 8601 格式） */
    private String createdAt;

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
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
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
     * Getter method for property <tt>createdAt</tt>.
     *
     * @return property value of createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter method for property <tt>createdAt</tt>.
     *
     * @param createdAt value to be assigned to property createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
