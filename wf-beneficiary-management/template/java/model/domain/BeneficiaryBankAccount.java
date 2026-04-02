package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import lombok.Data;

/**
 * Bank account details for binding a beneficiary.
 * Field requirements are determined by the card template returned by inquiryBeneficiaryTemplate.
 */
@Data
public class BeneficiaryBankAccount {

    /**
     * Account name (English)
     */
    private String bankAccountName;

    /**
     * Bank account number / card number
     */
    private String bankAccountNo;

    /**
     * Bank name (English)
     */
    private String bankName;

    /**
     * Bank BIC/SWIFT code
     */
    private String bankBIC;

    /**
     * IBAN
     */
    private String bankAccountIBAN;

    /**
     * Routing number
     */
    private String routingNumber;

    /**
     * Beneficiary address
     */
    private String beneficiaryAddress;

    /**
     * Beneficiary country code
     */
    private String beneficiaryCountryCode;

    /**
     * Beneficiary phone
     */
    private String beneficiaryPhone;

    /**
     * Bank branch code
     */
    private String bankBranchCode;

    /**
     * Bank local name
     */
    private String bankLocalName;

    /**
     * Account name (local language)
     */
    private String bankAccountLocalName;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
