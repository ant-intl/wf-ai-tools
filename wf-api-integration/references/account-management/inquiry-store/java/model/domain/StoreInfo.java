package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 店铺信息
 *
 * <p>用于 inquiryStore 接口，描述店铺及其关联账号信息。
 */
public class StoreInfo {

    /** 店铺名称 */
    private String storeName;

    /** 平台名称 */
    private String marketplaceName;

    /** 店铺授权状态：AUTHORIZED（已授权）、NEVER_AUTHORIZED（未授权） */
    private String authorizedStatus;

    /** 店铺账号信息 */
    private List<AccountInfo> accountInformation;

    /**
     * Getter method for property <tt>storeName</tt>.
     *
     * @return property value of storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * Setter method for property <tt>storeName</tt>.
     *
     * @param storeName value to be assigned to property storeName
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * Getter method for property <tt>marketplaceName</tt>.
     *
     * @return property value of marketplaceName
     */
    public String getMarketplaceName() {
        return marketplaceName;
    }

    /**
     * Setter method for property <tt>marketplaceName</tt>.
     *
     * @param marketplaceName value to be assigned to property marketplaceName
     */
    public void setMarketplaceName(String marketplaceName) {
        this.marketplaceName = marketplaceName;
    }

    /**
     * Getter method for property <tt>authorizedStatus</tt>.
     *
     * @return property value of authorizedStatus
     */
    public String getAuthorizedStatus() {
        return authorizedStatus;
    }

    /**
     * Setter method for property <tt>authorizedStatus</tt>.
     *
     * @param authorizedStatus value to be assigned to property authorizedStatus
     */
    public void setAuthorizedStatus(String authorizedStatus) {
        this.authorizedStatus = authorizedStatus;
    }

    /**
     * Getter method for property <tt>accountInformation</tt>.
     *
     * @return property value of accountInformation
     */
    public List<AccountInfo> getAccountInformation() {
        return accountInformation;
    }

    /**
     * Setter method for property <tt>accountInformation</tt>.
     *
     * @param accountInformation value to be assigned to property accountInformation
     */
    public void setAccountInformation(List<AccountInfo> accountInformation) {
        this.accountInformation = accountInformation;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
