package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 商业档案对象。
 *
 * <p>描述商户的主要业务活动和经营地址信息。
 */
public class BusinessProfile {

    /** 经营地址 */
    private Address businessAddress;

    /** 注册商业名称，最大256字符 */
    private String businessName;

    /** 英文商业名称，最大256字符 */
    private String englishBusinessName;

    /** 行业分类代码，最大32字符 */
    private String industryType;

    /** MCC 代码，最大8字符 */
    private String merchantCategoryCode;

    /** 品牌名称，最大128字符 */
    private String brand;

    /** Logo URL，最大512字符 */
    private String logo;

    /** 经营国家/地区列表，ISO 3166-1 alpha-2 */
    private java.util.List<String> operatingRegions;

    /** 商品或服务描述列表 */
    private java.util.List<String> goodsOrServices;

    /** 网站 URL 列表，最多10个 */
    private java.util.List<String> websites;

    /** 实体店铺列表，最多10个 */
    private java.util.List<Store> stores;

    /** 资金来源列表，最多10个 */
    private java.util.List<String> fundsSources;

    /** 资金去向列表，最多10个 */
    private java.util.List<String> fundsDestinations;

    /** 美国电商业务分类 */
    private String usEcBusinessType;

    /** 是否拥有运营中的电商网站 */
    private Boolean webSiteReady;

    /** 电商网站列表 */
    private java.util.List<EcBusinessWebsite> ecBusinessWebsites;

    public BusinessProfile() {
    }

    /**
     * Getter method for property <tt>businessAddress</tt>.
     *
     * @return property value of businessAddress
     */
    public Address getBusinessAddress() {
        return businessAddress;
    }

    /**
     * Setter method for property <tt>businessAddress</tt>.
     *
     * @param businessAddress value to be assigned to property businessAddress
     */
    public void setBusinessAddress(Address businessAddress) {
        this.businessAddress = businessAddress;
    }

    /**
     * Getter method for property <tt>businessName</tt>.
     *
     * @return property value of businessName
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * Setter method for property <tt>businessName</tt>.
     *
     * @param businessName value to be assigned to property businessName
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * Getter method for property <tt>englishBusinessName</tt>.
     *
     * @return property value of englishBusinessName
     */
    public String getEnglishBusinessName() {
        return englishBusinessName;
    }

    /**
     * Setter method for property <tt>englishBusinessName</tt>.
     *
     * @param englishBusinessName value to be assigned to property englishBusinessName
     */
    public void setEnglishBusinessName(String englishBusinessName) {
        this.englishBusinessName = englishBusinessName;
    }

    /**
     * Getter method for property <tt>industryType</tt>.
     *
     * @return property value of industryType
     */
    public String getIndustryType() {
        return industryType;
    }

    /**
     * Setter method for property <tt>industryType</tt>.
     *
     * @param industryType value to be assigned to property industryType
     */
    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    /**
     * Getter method for property <tt>merchantCategoryCode</tt>.
     *
     * @return property value of merchantCategoryCode
     */
    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    /**
     * Setter method for property <tt>merchantCategoryCode</tt>.
     *
     * @param merchantCategoryCode value to be assigned to property merchantCategoryCode
     */
    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }

    /**
     * Getter method for property <tt>brand</tt>.
     *
     * @return property value of brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Setter method for property <tt>brand</tt>.
     *
     * @param brand value to be assigned to property brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Getter method for property <tt>logo</tt>.
     *
     * @return property value of logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * Setter method for property <tt>logo</tt>.
     *
     * @param logo value to be assigned to property logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * Getter method for property <tt>operatingRegions</tt>.
     *
     * @return property value of operatingRegions
     */
    public java.util.List<String> getOperatingRegions() {
        return operatingRegions;
    }

    /**
     * Setter method for property <tt>operatingRegions</tt>.
     *
     * @param operatingRegions value to be assigned to property operatingRegions
     */
    public void setOperatingRegions(java.util.List<String> operatingRegions) {
        this.operatingRegions = operatingRegions;
    }

    /**
     * Getter method for property <tt>goodsOrServices</tt>.
     *
     * @return property value of goodsOrServices
     */
    public java.util.List<String> getGoodsOrServices() {
        return goodsOrServices;
    }

    /**
     * Setter method for property <tt>goodsOrServices</tt>.
     *
     * @param goodsOrServices value to be assigned to property goodsOrServices
     */
    public void setGoodsOrServices(java.util.List<String> goodsOrServices) {
        this.goodsOrServices = goodsOrServices;
    }

    /**
     * Getter method for property <tt>websites</tt>.
     *
     * @return property value of websites
     */
    public java.util.List<String> getWebsites() {
        return websites;
    }

    /**
     * Setter method for property <tt>websites</tt>.
     *
     * @param websites value to be assigned to property websites
     */
    public void setWebsites(java.util.List<String> websites) {
        this.websites = websites;
    }

    /**
     * Getter method for property <tt>stores</tt>.
     *
     * @return property value of stores
     */
    public java.util.List<Store> getStores() {
        return stores;
    }

    /**
     * Setter method for property <tt>stores</tt>.
     *
     * @param stores value to be assigned to property stores
     */
    public void setStores(java.util.List<Store> stores) {
        this.stores = stores;
    }

    /**
     * Getter method for property <tt>fundsSources</tt>.
     *
     * @return property value of fundsSources
     */
    public java.util.List<String> getFundsSources() {
        return fundsSources;
    }

    /**
     * Setter method for property <tt>fundsSources</tt>.
     *
     * @param fundsSources value to be assigned to property fundsSources
     */
    public void setFundsSources(java.util.List<String> fundsSources) {
        this.fundsSources = fundsSources;
    }

    /**
     * Getter method for property <tt>fundsDestinations</tt>.
     *
     * @return property value of fundsDestinations
     */
    public java.util.List<String> getFundsDestinations() {
        return fundsDestinations;
    }

    /**
     * Setter method for property <tt>fundsDestinations</tt>.
     *
     * @param fundsDestinations value to be assigned to property fundsDestinations
     */
    public void setFundsDestinations(java.util.List<String> fundsDestinations) {
        this.fundsDestinations = fundsDestinations;
    }

    /**
     * Getter method for property <tt>usEcBusinessType</tt>.
     *
     * @return property value of usEcBusinessType
     */
    public String getUsEcBusinessType() {
        return usEcBusinessType;
    }

    /**
     * Setter method for property <tt>usEcBusinessType</tt>.
     *
     * @param usEcBusinessType value to be assigned to property usEcBusinessType
     */
    public void setUsEcBusinessType(String usEcBusinessType) {
        this.usEcBusinessType = usEcBusinessType;
    }

    /**
     * Getter method for property <tt>webSiteReady</tt>.
     *
     * @return property value of webSiteReady
     */
    public Boolean getWebSiteReady() {
        return webSiteReady;
    }

    /**
     * Setter method for property <tt>webSiteReady</tt>.
     *
     * @param webSiteReady value to be assigned to property webSiteReady
     */
    public void setWebSiteReady(Boolean webSiteReady) {
        this.webSiteReady = webSiteReady;
    }

    /**
     * Getter method for property <tt>ecBusinessWebsites</tt>.
     *
     * @return property value of ecBusinessWebsites
     */
    public java.util.List<EcBusinessWebsite> getEcBusinessWebsites() {
        return ecBusinessWebsites;
    }

    /**
     * Setter method for property <tt>ecBusinessWebsites</tt>.
     *
     * @param ecBusinessWebsites value to be assigned to property ecBusinessWebsites
     */
    public void setEcBusinessWebsites(java.util.List<EcBusinessWebsite> ecBusinessWebsites) {
        this.ecBusinessWebsites = ecBusinessWebsites;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
