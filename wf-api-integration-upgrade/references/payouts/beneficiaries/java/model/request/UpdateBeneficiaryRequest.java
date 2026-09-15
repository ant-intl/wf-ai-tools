package {basePackage}.wf.model.request;

import java.util.List;

import {basePackage}.wf.model.domain.BankDetail;
import {basePackage}.wf.model.domain.WalletDetail;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst update_a_beneficiary 请求对象。
 *
 * <p>更新已激活（ACTIVE）收款人的信息。仅需传入要修改的字段，省略的字段保持不变。
 */
public class UpdateBeneficiaryRequest {

    /** 收款人 ID（必填） */
    private String id;

    /** 新的显示名称 */
    private String nickname;

    /** 支持的支付方式列表：LOCAL、CROSS */
    private List<String> paymentTypeList;

    /** 新的联系电话 */
    private String phone;

    /** 新的联系邮箱（最多 64 字符） */
    private String email;

    /** 新的街道地址（最多 256 字符） */
    private String address;

    /** 新的城市 */
    private String city;

    /** 更新的银行账户详情（仅需修改银行信息时传入） */
    private BankDetail bankDetails;

    /** 更新的钱包账户详情（仅需修改钱包信息时传入） */
    private WalletDetail walletDetails;

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
     * Getter method for property <tt>nickname</tt>.
     *
     * @return property value of nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter method for property <tt>nickname</tt>.
     *
     * @param nickname value to be assigned to property nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter method for property <tt>paymentTypeList</tt>.
     *
     * @return property value of paymentTypeList
     */
    public List<String> getPaymentTypeList() {
        return paymentTypeList;
    }

    /**
     * Setter method for property <tt>paymentTypeList</tt>.
     *
     * @param paymentTypeList value to be assigned to property paymentTypeList
     */
    public void setPaymentTypeList(List<String> paymentTypeList) {
        this.paymentTypeList = paymentTypeList;
    }

    /**
     * Getter method for property <tt>phone</tt>.
     *
     * @return property value of phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter method for property <tt>phone</tt>.
     *
     * @param phone value to be assigned to property phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter method for property <tt>email</tt>.
     *
     * @return property value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for property <tt>email</tt>.
     *
     * @param email value to be assigned to property email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for property <tt>address</tt>.
     *
     * @return property value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for property <tt>address</tt>.
     *
     * @param address value to be assigned to property address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for property <tt>city</tt>.
     *
     * @return property value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter method for property <tt>city</tt>.
     *
     * @param city value to be assigned to property city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter method for property <tt>bankDetails</tt>.
     *
     * @return property value of bankDetails
     */
    public BankDetail getBankDetails() {
        return bankDetails;
    }

    /**
     * Setter method for property <tt>bankDetails</tt>.
     *
     * @param bankDetails value to be assigned to property bankDetails
     */
    public void setBankDetails(BankDetail bankDetails) {
        this.bankDetails = bankDetails;
    }

    /**
     * Getter method for property <tt>walletDetails</tt>.
     *
     * @return property value of walletDetails
     */
    public WalletDetail getWalletDetails() {
        return walletDetails;
    }

    /**
     * Setter method for property <tt>walletDetails</tt>.
     *
     * @param walletDetails value to be assigned to property walletDetails
     */
    public void setWalletDetails(WalletDetail walletDetails) {
        this.walletDetails = walletDetails;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
