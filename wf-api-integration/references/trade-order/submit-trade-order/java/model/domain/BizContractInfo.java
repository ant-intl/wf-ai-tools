package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * B2B 贸易合同信息。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code bizContractInfo} 字段。
 * tradeType=GOODS 时 buyerEnName/tradeCountry/deliverCountry/contractList 为必填。
 *
 */
public class BizContractInfo {

    /** 买家英文名称，最大 100 字符（tradeType=GOODS 时必填） */
    private String buyerEnName;

    /** 贸易国家代码（ISO-3166，2 位字母），不支持 BY/RU（tradeType=GOODS 时必填） */
    private String tradeCountry;

    /** 交付国家代码（ISO-3166，2 位字母），不支持 BY/RU（tradeType=GOODS 时必填） */
    private String deliverCountry;

    /** 合同附件列表：形式发票、商业发票或合同（tradeType=GOODS 时必填） */
    private List<AttachmentInfo> contractList;

    /** 其他附件列表（B2B 必填） */
    private List<AttachmentInfo> otherAttachmentList;

    /** 附件描述，最大 200 字符（B2B 必填） */
    private String attachmentDesc;

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Getter method for property <tt>buyerEnName</tt>.
     *
     * @return property value of buyerEnName
     */
    public String getBuyerEnName() {
        return buyerEnName;
    }

    /**
     * Setter method for property <tt>buyerEnName</tt>.
     *
     * @param buyerEnName value to be assigned to property buyerEnName
     */
    public void setBuyerEnName(String buyerEnName) {
        this.buyerEnName = buyerEnName;
    }

    /**
     * Getter method for property <tt>tradeCountry</tt>.
     *
     * @return property value of tradeCountry
     */
    public String getTradeCountry() {
        return tradeCountry;
    }

    /**
     * Setter method for property <tt>tradeCountry</tt>.
     *
     * @param tradeCountry value to be assigned to property tradeCountry
     */
    public void setTradeCountry(String tradeCountry) {
        this.tradeCountry = tradeCountry;
    }

    /**
     * Getter method for property <tt>deliverCountry</tt>.
     *
     * @return property value of deliverCountry
     */
    public String getDeliverCountry() {
        return deliverCountry;
    }

    /**
     * Setter method for property <tt>deliverCountry</tt>.
     *
     * @param deliverCountry value to be assigned to property deliverCountry
     */
    public void setDeliverCountry(String deliverCountry) {
        this.deliverCountry = deliverCountry;
    }

    /**
     * Getter method for property <tt>contractList</tt>.
     *
     * @return property value of contractList
     */
    public List<AttachmentInfo> getContractList() {
        return contractList;
    }

    /**
     * Setter method for property <tt>contractList</tt>.
     *
     * @param contractList value to be assigned to property contractList
     */
    public void setContractList(List<AttachmentInfo> contractList) {
        this.contractList = contractList;
    }

    /**
     * Getter method for property <tt>otherAttachmentList</tt>.
     *
     * @return property value of otherAttachmentList
     */
    public List<AttachmentInfo> getOtherAttachmentList() {
        return otherAttachmentList;
    }

    /**
     * Setter method for property <tt>otherAttachmentList</tt>.
     *
     * @param otherAttachmentList value to be assigned to property otherAttachmentList
     */
    public void setOtherAttachmentList(List<AttachmentInfo> otherAttachmentList) {
        this.otherAttachmentList = otherAttachmentList;
    }

    /**
     * Getter method for property <tt>attachmentDesc</tt>.
     *
     * @return property value of attachmentDesc
     */
    public String getAttachmentDesc() {
        return attachmentDesc;
    }

    /**
     * Setter method for property <tt>attachmentDesc</tt>.
     *
     * @param attachmentDesc value to be assigned to property attachmentDesc
     */
    public void setAttachmentDesc(String attachmentDesc) {
        this.attachmentDesc = attachmentDesc;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
