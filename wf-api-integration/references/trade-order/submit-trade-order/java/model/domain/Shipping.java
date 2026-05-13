package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 物流信息，适用于 B2C 和 B2B 场景。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code shipping} 字段。
 * B2B 场景下根据 isUsedForExchange、isShipped、isDeclared、isNewBuyer
 * 等条件字段的组合，有不同的必填逻辑。
 *
 */
public class Shipping {

    // -------------------------------------------------------------------------
    // B2B 条件字段
    // -------------------------------------------------------------------------

    /** 是否已发货：Y 或 N（B2B + isUsedForExchange=Y 时必填） */
    private String isShipped;

    /** 是否已报关：Y 或 N（B2B + isUsedForExchange=Y + isShipped=Y 时必填） */
    private String isDeclared;

    /** 是否新买家：Y 或 N（B2B + isUsedForExchange=Y + isShipped=N 时必填） */
    private String isNewBuyer;

    // -------------------------------------------------------------------------
    // 运单信息
    // -------------------------------------------------------------------------

    /**
     * 运单信息列表。
     *
     * <p>B2C 场景必填；B2B 场景在 isUsedForExchange=Y + isShipped=Y + isDeclared=N 时必填。
     */
    private List<WayBillInfo> wayBillInfos;

    // -------------------------------------------------------------------------
    // B2C 收货地址
    // -------------------------------------------------------------------------

    /** 收货地址（B2C 必填） */
    private Address shippingAddress;

    // -------------------------------------------------------------------------
    // B2B 物流详情字段
    // -------------------------------------------------------------------------

    /**
     * 物流方式（B2B + isUsedForExchange=Y + isShipped=Y + isDeclared=N 时必填）。
     *
     * <p>可选值：RAILWAY / LAND_TRANSPORTATION / BY_SEA / AIR_CARGO /
     * EXPRESS / SPECIAL_LINE_TRANSPORTATION / OTHER
     */
    private String shippingMethod;

    /** 物流凭证附件列表（B2B + isUsedForExchange=Y + isShipped=Y + isDeclared=N 时必填） */
    private List<AttachmentInfo> shippingProofAttachmentList;

    /** 物流公司信息（B2B + isUsedForExchange=Y + isShipped=Y + isDeclared=N 时必填） */
    private LogisticsCompany logisticsCompany;

    /** 报关信息列表（B2B + isUsedForExchange=Y + isShipped=Y + isDeclared=Y 时必填） */
    private List<DeclarationInfo> declarationInfos;

    /** 预计发货日期，13 位时间戳（B2B + isUsedForExchange=Y + isShipped=N 时必填） */
    private String expectedShippingDate;

    /** 询盘聊天记录附件列表（B2B + isUsedForExchange=Y + isShipped=N + isNewBuyer=Y 时必填） */
    private List<AttachmentInfo> inquiryChatRecordAttachmentList;

    /** 物流聊天记录附件列表（B2B + isUsedForExchange=Y + isShipped=N + isNewBuyer=Y 时必填） */
    private List<AttachmentInfo> logisticsChatRecordAttachmentList;

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Getter method for property <tt>isShipped</tt>.
     *
     * @return property value of isShipped
     */
    public String getIsShipped() {
        return isShipped;
    }

    /**
     * Setter method for property <tt>isShipped</tt>.
     *
     * @param isShipped value to be assigned to property isShipped
     */
    public void setIsShipped(String isShipped) {
        this.isShipped = isShipped;
    }

    /**
     * Getter method for property <tt>isDeclared</tt>.
     *
     * @return property value of isDeclared
     */
    public String getIsDeclared() {
        return isDeclared;
    }

    /**
     * Setter method for property <tt>isDeclared</tt>.
     *
     * @param isDeclared value to be assigned to property isDeclared
     */
    public void setIsDeclared(String isDeclared) {
        this.isDeclared = isDeclared;
    }

    /**
     * Getter method for property <tt>isNewBuyer</tt>.
     *
     * @return property value of isNewBuyer
     */
    public String getIsNewBuyer() {
        return isNewBuyer;
    }

    /**
     * Setter method for property <tt>isNewBuyer</tt>.
     *
     * @param isNewBuyer value to be assigned to property isNewBuyer
     */
    public void setIsNewBuyer(String isNewBuyer) {
        this.isNewBuyer = isNewBuyer;
    }

    /**
     * Getter method for property <tt>wayBillInfos</tt>.
     *
     * @return property value of wayBillInfos
     */
    public List<WayBillInfo> getWayBillInfos() {
        return wayBillInfos;
    }

    /**
     * Setter method for property <tt>wayBillInfos</tt>.
     *
     * @param wayBillInfos value to be assigned to property wayBillInfos
     */
    public void setWayBillInfos(List<WayBillInfo> wayBillInfos) {
        this.wayBillInfos = wayBillInfos;
    }

    /**
     * Getter method for property <tt>shippingAddress</tt>.
     *
     * @return property value of shippingAddress
     */
    public Address getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Setter method for property <tt>shippingAddress</tt>.
     *
     * @param shippingAddress value to be assigned to property shippingAddress
     */
    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * Getter method for property <tt>shippingMethod</tt>.
     *
     * @return property value of shippingMethod
     */
    public String getShippingMethod() {
        return shippingMethod;
    }

    /**
     * Setter method for property <tt>shippingMethod</tt>.
     *
     * @param shippingMethod value to be assigned to property shippingMethod
     */
    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    /**
     * Getter method for property <tt>shippingProofAttachmentList</tt>.
     *
     * @return property value of shippingProofAttachmentList
     */
    public List<AttachmentInfo> getShippingProofAttachmentList() {
        return shippingProofAttachmentList;
    }

    /**
     * Setter method for property <tt>shippingProofAttachmentList</tt>.
     *
     * @param shippingProofAttachmentList value to be assigned to property shippingProofAttachmentList
     */
    public void setShippingProofAttachmentList(List<AttachmentInfo> shippingProofAttachmentList) {
        this.shippingProofAttachmentList = shippingProofAttachmentList;
    }

    /**
     * Getter method for property <tt>logisticsCompany</tt>.
     *
     * @return property value of logisticsCompany
     */
    public LogisticsCompany getLogisticsCompany() {
        return logisticsCompany;
    }

    /**
     * Setter method for property <tt>logisticsCompany</tt>.
     *
     * @param logisticsCompany value to be assigned to property logisticsCompany
     */
    public void setLogisticsCompany(LogisticsCompany logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    /**
     * Getter method for property <tt>declarationInfos</tt>.
     *
     * @return property value of declarationInfos
     */
    public List<DeclarationInfo> getDeclarationInfos() {
        return declarationInfos;
    }

    /**
     * Setter method for property <tt>declarationInfos</tt>.
     *
     * @param declarationInfos value to be assigned to property declarationInfos
     */
    public void setDeclarationInfos(List<DeclarationInfo> declarationInfos) {
        this.declarationInfos = declarationInfos;
    }

    /**
     * Getter method for property <tt>expectedShippingDate</tt>.
     *
     * @return property value of expectedShippingDate
     */
    public String getExpectedShippingDate() {
        return expectedShippingDate;
    }

    /**
     * Setter method for property <tt>expectedShippingDate</tt>.
     *
     * @param expectedShippingDate value to be assigned to property expectedShippingDate
     */
    public void setExpectedShippingDate(String expectedShippingDate) {
        this.expectedShippingDate = expectedShippingDate;
    }

    /**
     * Getter method for property <tt>inquiryChatRecordAttachmentList</tt>.
     *
     * @return property value of inquiryChatRecordAttachmentList
     */
    public List<AttachmentInfo> getInquiryChatRecordAttachmentList() {
        return inquiryChatRecordAttachmentList;
    }

    /**
     * Setter method for property <tt>inquiryChatRecordAttachmentList</tt>.
     *
     * @param inquiryChatRecordAttachmentList value to be assigned to property inquiryChatRecordAttachmentList
     */
    public void setInquiryChatRecordAttachmentList(List<AttachmentInfo> inquiryChatRecordAttachmentList) {
        this.inquiryChatRecordAttachmentList = inquiryChatRecordAttachmentList;
    }

    /**
     * Getter method for property <tt>logisticsChatRecordAttachmentList</tt>.
     *
     * @return property value of logisticsChatRecordAttachmentList
     */
    public List<AttachmentInfo> getLogisticsChatRecordAttachmentList() {
        return logisticsChatRecordAttachmentList;
    }

    /**
     * Setter method for property <tt>logisticsChatRecordAttachmentList</tt>.
     *
     * @param logisticsChatRecordAttachmentList value to be assigned to property logisticsChatRecordAttachmentList
     */
    public void setLogisticsChatRecordAttachmentList(List<AttachmentInfo> logisticsChatRecordAttachmentList) {
        this.logisticsChatRecordAttachmentList = logisticsChatRecordAttachmentList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
