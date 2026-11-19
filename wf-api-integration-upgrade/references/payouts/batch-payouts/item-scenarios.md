# 批量代发项：场景字段

来源：[Create a Payout](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_payout)，核对日期 2026-11-18。以下仅保留可用于 addItems 的项字段；批次统一配置与 transferQuote 不属于项输入。各场景的必填/条件字段以本页为准；Java BatchPayoutBeneficiary/BankDetail/WalletDetail 是各场景字段并集，不代表所有字段同时必填。

## PAY_INTO_BANK_ACCOUNT

#### requestId (string, REQUIRED)

Idempotency key. Assign a unique value (e.g., UUID) per payout to safely retry without creating duplicates.

#### businessSceneCode (string)

Business scene code that identifies the payout type. See [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UqJiZ).

#### purposeCode (string, REQUIRED)

Payment purpose code. See [PurposeCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#XOHdL).

-   `GDS`: Payment for goods.
-   `GST`: General service trade.
-   `ACM`: Agency commission.
-   `COM`: Commission payment.
-   `TOA`: Transfer to own account, same-name withdrawal.
-   `SAL`: Salary/payroll.
-   `TXS`: Government-imposed charges including income tax, customs duties, and transfer payments.

#### sourceAmount (Amount conditional)

Source currency amount debited from the payer's account. currency is required on both sourceAmount and payoutAmount, but only one value may be specified. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### payoutAmount (Amount conditional)

Target currency amount (received by the beneficiary). currency is required on both sourceAmount and payoutAmount, but only one value may be specified. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### beneficiary (Beneficiary, REQUIRED)

Beneficiary (recipient) information. Provide bank details.

##### accountType (string, REQUIRED)

Beneficiary account type if beneficiaryId/referenceBeneficiaryId is not provided. Supported value: BANK_ACCOUNT.

##### entityType (string, REQUIRED)

Beneficiary entity type if beneficiaryId/referenceBeneficiaryId is not provided.

-   `COMPANY`: Corporate beneficiary.
-   `PERSONAL`: Individual beneficiary.

##### relationType (string, REQUIRED)

Relationship between payer and beneficiary if beneficiaryId/referenceBeneficiaryId is not provided.

-   `THIRD_PARTY`: Unrelated third party.
-   `SAME_NAME`: Same-name transfer (same account holder).
-   `RELATED_MERCHANT`: Affiliated merchant.

##### classificationCode (string conditional)

Relationship classification code when relationType **\=** THIRD_PARTY.

-   `0001`: Goods Trade
-   `0002`: Advertising/Marketing
-   `0003`: Logistics/Freight Forwarding/Warehousing.
-   `0004`: Financial & Tax Services
-   `0005`: Technology Services.

##### region (string, REQUIRED)

Beneficiary's country/region ISO 3166 two-letter code.

##### phone (string)

Beneficiary's phone number.

##### email (string)

Beneficiary's email address.

##### city (string)

Beneficiary's city.

##### address (string)

Beneficiary's street address.

##### referenceBeneficiaryId (string)

Your internal reference ID for this beneficiary. Max 64 characters.

##### bankDetails (BankDetail, REQUIRED)

Bank account details of the recipient.

###### accountHolderName (UserName)

Account holder's legal name. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

###### accountNumber (string)

Bank account number.

###### iban (string)

International Bank Account Number (IBAN). Used for SEPA and IBAN-based transfers.

###### bankBIC (string)

SWIFT/BIC code of the receiving bank. Required for cross-border wire transfers.

###### routingNumber (string)

Local clearing routing number (e.g., ABA routing number for ACH transfers).

###### bankName (string)

Receiving bank name.

###### bankLocalName (string)

Bank name in local language.

###### branchBankName (string)

Branch bank name.

###### bankBranchCode (string)

Bank branch code.

###### bankAccountLocalName (string)

Account holder's name in local language.

###### bankAddress (string)

Bank's address.

###### beneficiaryDepositType (string)

Beneficiary deposit category.

-   `CC`: Current account (demand deposit).
-   `CP`: Fixed deposit.

###### cardClientCode (string)

Beneficiary ID document type.

###### cardClientId (string)

Beneficiary ID document number.

###### beneficiaryTIN (string)

Beneficiary Tax Identification Number (TIN).

###### beneficiaryAccountNumberType (string)

Beneficiary account number type.

#### payer (Payer conditional)

Payer (sender) information. Defaults to the onboarded account details. Required when the On-Behalf-Of payment preference is specified.

##### balanceType (string)

Payer account type. Default: BALANCE.

-   `BALANCE`: Standard balance account.
-   `SUBLEDGER`: Sub-ledger account.

##### accountNumber (string)

Payer account number. Required when balanceType is SUBLEDGER.

##### region (string)

Payer's country/region. ISO 3166 two-letter code.

##### phone (string)

Payer's phone number.

##### email (string)

Payer's email address.

##### name (UserName)

Payer's legal name. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### address (Address)

Payer's mailing address. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### reference (string)

Bank transfer reference text. May appear on the beneficiary's bank statement, depending on the local clearing system and receiving bank.

#### transferMemo (string)

Internal transfer memo for your own records. Not visible to the beneficiary.

## PAY_INTO_DIGITAL_WALLET

#### requestId (string, REQUIRED)

Idempotency key. Assign a unique value (e.g., UUID) per payout to safely retry without creating duplicates.

#### businessSceneCode (string)

Business scene code that identifies the payout type. See [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UqJiZ).

#### purposeCode (string, REQUIRED)

Payment purpose code. See [PurposeCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#XOHdL).

-   `GDS`: Payment for goods.
-   `GST`: General service trade.
-   `ACM`: Agency commission.
-   `COM`: Commission payment.
-   `TOA`: Transfer to own account, same-name withdrawal.
-   `SAL`: Salary/payroll.
-   `TXS`: Government-imposed charges including income tax, customs duties, and transfer payments.

#### sourceAmount (Amount conditional)

Source currency amount debited from the payer's account. currency is required on both sourceAmount and payoutAmount, but only one value may be specified. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### payoutAmount (Amount conditional)

Target currency amount (received by the beneficiary). currency is required on both sourceAmount and payoutAmount, but only one value may be specified. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### beneficiary (Beneficiary, REQUIRED)

Beneficiary (recipient) information.

##### accountType (string, REQUIRED)

Beneficiary account type if beneficiaryId/referenceBeneficiaryId is not provided. Supported value: DIGITAL_WALLET.

##### entityType (string, REQUIRED)

Beneficiary entity type if beneficiaryId/referenceBeneficiaryId is not provided.

-   `COMPANY`: Corporate beneficiary.
-   `PERSONAL`: Individual beneficiary.

##### relationType (string, REQUIRED)

Relationship between payer and beneficiary if beneficiaryId/referenceBeneficiaryId is not provided.

-   `THIRD_PARTY`: Unrelated third party.
-   `SAME_NAME`: Same-name transfer (same account holder).
-   `RELATED_MERCHANT`: Affiliated merchant.

##### classificationCode (string conditional)

Relationship classification code when relationType **\=** THIRD_PARTY.

-   `0001`: Goods Trade
-   `0002`: Advertising/Marketing
-   `0003`: Logistics/Freight Forwarding/Warehousing.
-   `0004`: Financial & Tax Services
-   `0005`: Technology Services.

##### region (string, REQUIRED)

Beneficiary's country/region if beneficiaryId/referenceBeneficiaryId is not provided. ISO 3166 two-letter code.

##### phone (string)

Beneficiary's phone number.

##### email (string)

Beneficiary's email address.

##### city (string)

Beneficiary's city.

##### address (string)

Beneficiary's street address.

##### walletDetails (WalletDetail, REQUIRED)

E-wallet account details.

###### accountHolderName (UserName, REQUIRED)

Wallet holder's legal name. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

###### walletBrandName (string, REQUIRED)

Wallet brand. See [WalletBrandName](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#lZBBs).

###### accountNumber (string)

Wallet account number.

###### iban (string)

IBAN for the wallet account, if applicable.

#### payer (Payer conditional)

Payer (sender) information. Defaults to the onboarded account details. Required when the On-Behalf-Of payment preference is specified.

##### balanceType (string)

Payer account type. Default: BALANCE.

-   `BALANCE`: Standard balance account.
-   `SUBLEDGER`: Sub-ledger account.

##### accountNumber (string)

Payer account number. Required when balanceType is SUBLEDGER.

##### region (string)

Payer's country/region. ISO 3166 two-letter code.

##### phone (string)

Payer's phone number.

##### email (string)

Payer's email address.

##### name (UserName)

Payer's legal name. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### address (Address)

Payer's mailing address. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### reference (string)

Bank transfer reference text. May appear on the beneficiary's bank statement, depending on the local clearing system and receiving bank.

#### transferMemo (string)

Internal transfer memo for your own records. Not visible to the beneficiary.

## EXISTING_BENEFICIARY

#### requestId (string, REQUIRED)

Idempotency key. Assign a unique value (e.g., UUID) per payout to safely retry without creating duplicates.

#### businessSceneCode (string)

Business scene code that identifies the payout type. See [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UqJiZ).

#### purposeCode (string, REQUIRED)

Payment purpose code. See [PurposeCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#XOHdL).

-   `GDS`: Payment for goods.
-   `GST`: General service trade.
-   `ACM`: Agency commission.
-   `COM`: Commission payment.
-   `TOA`: Transfer to own account, same-name withdrawal.
-   `SAL`: Salary/payroll.
-   `TXS`: Government-imposed charges including income tax, customs duties, and transfer payments.

#### sourceAmount (Amount conditional)

Source currency amount debited from the payer's account. currency is required on both sourceAmount and payoutAmount, but only one value may be specified. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### payoutAmount (Amount conditional)

Target currency amount (received by the beneficiary). currency is required on both sourceAmount and payoutAmount, but only one value may be specified. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### beneficiary (Beneficiary, REQUIRED)

Beneficiary (recipient) information.

##### beneficiaryId (string, REQUIRED)

The ID of a pre-registered beneficiary generated by WorldFirst. When provided, all recipient details including bank/wallet properties are automatically populated from the existing beneficiary record.

#### payer (Payer conditional)

Payer (sender) information. Defaults to the onboarded account details. Required when the On-Behalf-Of payment preference is specified.

##### balanceType (string)

Payer account type. Default: BALANCE.

-   `BALANCE`: Standard balance account.
-   `SUBLEDGER`: Sub-ledger account.

##### accountNumber (string)

Payer account number. Required when balanceType is SUBLEDGER.

##### region (string)

Payer's country/region. ISO 3166 two-letter code.

##### phone (string)

Payer's phone number.

##### email (string)

Payer's email address.

##### name (UserName)

Payer's legal name. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### address (Address)

Payer's mailing address. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### reference (string)

Bank transfer reference text. May appear on the beneficiary's bank statement, depending on the local clearing system and receiving bank.

#### transferMemo (string)

Internal transfer memo for your own records. Not visible to the beneficiary.

## PAY_INTO_WORLDFIRST

#### requestId (string, REQUIRED)

Idempotency key. Assign a unique value (e.g., UUID) per payout to safely retry without creating duplicates.

#### businessSceneCode (string)

Business scene code that identifies the payout type. See [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UqJiZ).

#### purposeCode (string, REQUIRED)

Payment purpose code. See [PurposeCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#XOHdL).

-   `GDS`: Payment for goods.
-   `GST`: General service trade.
-   `ACM`: Agency commission.
-   `COM`: Commission payment.
-   `TOA`: Transfer to own account, same-name withdrawal.
-   `SAL`: Salary/payroll.
-   `TXS`: Government-imposed charges including income tax, customs duties, and transfer payments.

#### sourceAmount (Amount conditional)

Source currency amount debited from the payer's account. currency is required on both sourceAmount and payoutAmount, but only one value may be specified. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### payoutAmount (Amount conditional)

Target currency amount (received by the beneficiary). currency is required on both sourceAmount and payoutAmount, but only one value may be specified. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### beneficiary (Beneficiary, REQUIRED)

Beneficiary (recipient) information.

##### accountType (string, REQUIRED)

Beneficiary account type if beneficiaryId/referenceBeneficiaryId is not provided. Supported value: DIGITAL_WALLET.

##### entityType (string, REQUIRED)

Beneficiary entity type.

-   `COMPANY`: Corporate beneficiary.
-   `PERSONAL`: Individual beneficiary.

##### relationType (string, REQUIRED)

Relationship between payer and beneficiary.

-   `THIRD_PARTY`: Unrelated third party.
-   `SAME_NAME`: Same-name transfer (same account holder).
-   `RELATED_MERCHANT`: Affiliated merchant.

##### classificationCode (string conditional)

Relationship classification code when relationType **\=** THIRD_PARTY.

-   `0001`: Goods Trade
-   `0002`: Advertising/Marketing
-   `0003`: Logistics/Freight Forwarding/Warehousing.
-   `0004`: Financial & Tax Services
-   `0005`: Technology Services.

##### walletDetails (WalletDetail, REQUIRED)

WorldFirst account details.

###### walletBrandName (string, REQUIRED)

Wallet brand. Supported value: WORLDFIRST.

###### walletAccountType (string, REQUIRED)

Wallet account type.

-   `BALANCE`: Standard WorldFirst balance account.
-   `WORLDFIRST_RECEIVE_ACCOUNT`: WorldFirst receiving account (RA).

###### accountNumber (string, REQUIRED)

WorldFirst account number or RA number based on selected wallet account type.

#### reference (string)

Bank transfer reference text. May appear on the beneficiary's bank statement, depending on the local clearing system and receiving bank.

#### transferMemo (string)

Internal transfer memo for your own records. Not visible to the beneficiary.

#### additionalInfo (AdditionalInfo)

Supplementary fields for specific business scenarios. Maximum 10 key-value pairs, each key-value up to 255 characters. Contact WorldFirst support for applicable scenarios.

##### needMigrateMaterial (boolean)

Whether to migrate CNY settlement materials. Required only when businessSceneCode is MULTI_ACCOUNT_TRANSFER. Pass true to include settlement materials.

## PAY_INTO_CHINA

#### requestId (string, REQUIRED)

Idempotency key. Assign a unique value (e.g., UUID) per payout to safely retry without creating duplicates.

#### businessSceneCode (string)

Business scene code that identifies the payout type. See [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UqJiZ).

#### purposeCode (string, REQUIRED)

Payment purpose code. WorldFirst uses this code to classify the transaction for regulatory and compliance reporting. For payments into Mainland China, GDS is typically supported. Supported values:

-   `GDS`: Payment for goods (B2B trade and e-commerce).
-   `GST`: Payment for service (Available only in restricted scenarios, such as developer).

#### sourceAmount (Amount conditional)

Source currency amount debited from the payer's account. currency is required on both sourceAmount and payoutAmount, but only one value may be specified. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### payoutAmount (Amount conditional)

Target currency amount (received by the beneficiary). currency is required on both sourceAmount and payoutAmount, but only one value may be specified. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### beneficiary (Beneficiary, REQUIRED)

Beneficiary (recipient) information.

##### accountType (string, REQUIRED)

Beneficiary account type.

-   `BANK_ACCOUNT`: bank account.
-   `DIGITAL_WALLET`: digital wallet(for Alipay wallet only).

##### entityType (string, REQUIRED)

Beneficiary entity type if beneficiaryId/referenceBeneficiaryId is not provided.

-   `COMPANY`: Corporate beneficiary.
-   `PERSONAL`: Individual beneficiary.

##### relationType (string, REQUIRED)

Relationship between payer and beneficiary if beneficiaryId/referenceBeneficiaryId is not provided.

-   `THIRD_PARTY`: Unrelated third party.
-   `SAME_NAME`: Same-name transfer (same account holder).
-   `RELATED_MERCHANT`: Affiliated merchant.

##### classificationCode (string conditional)

Relationship classification code when relationType **\=** THIRD_PARTY.

-   `0001`: Goods Trade
-   `0002`: Advertising/Marketing
-   `0003`: Logistics/Freight Forwarding/Warehousing.
-   `0004`: Financial & Tax Services
-   `0005`: Technology Services.

##### region (string)

Beneficiary's country/region if beneficiaryId/referenceBeneficiaryId is not provided(e.g., CN).

##### phone (string)

Beneficiary's phone number.

##### email (string)

Beneficiary's email address.

##### city (string)

Beneficiary's city.

##### address (string)

Beneficiary's street address.

##### bankDetails (BankDetail conditional)

Bank account details of the recipient. Omit ifwalletDetails is provided.

###### bankAccountLocalName (string, REQUIRED)

Beneficiary's account name in local language. Max 128 characters.

###### certificateNo (string conditional)

ID number (for PERSONAL) or business registration number (for COMPANY). Required only for beneficiary information declaration.

###### accountNumber (string, REQUIRED)

Beneficiary's bank account number.

###### bankLocalName (string, REQUIRED)

Bank name in local language. Max 128 characters.

###### routingNumber (string conditional)

Bank code. Required for business bank accounts.

###### branchBankName (string)

Branch name. Max 128 characters.

###### bankBranchCode (string)

Branch clearing code.

###### bankAddress (string)

Bank address. Max 1024 characters.

###### bankCity (string)

Bank city.

###### bankState (string)

Bank state or province.

###### bankPostalCode (string)

Bank postal code.

##### walletDetails (WalletDetail conditional)

E-wallet account details. Omit ifbankDetails is provided.

###### accountHolderName (UserName, REQUIRED)

Alipay wallet account holder's legal full name. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

###### walletBrandName (string, REQUIRED)

Wallet brand. (e.g., ALIPAY).

###### accountNumber (string, REQUIRED)

Alipay wallet account number.

###### certificateNo (string conditional)

Identification number of the ALIPAY account holder. Required only for beneficiary information declaration.

#### payer (Payer)

Payer (sender) information. Defaults to the onboarded account details.

##### balanceType (string)

Payer account type. Default: BALANCE.

-   `BALANCE`: Standard balance account.
-   `SUBLEDGER`: Sub-ledger account.

##### accountNumber (string)

Payer account number. Required when balanceType is SUBLEDGER.

#### quotaConsumptionPreference (object)

Specifies how the CNY quota is consumed for this payout, including whose quota is drawn down and which type of trade documents supports the consumption. By default, this object inherits its values from your account profile, and overriding them is supported only in specific approved scenarios.

##### party (string)

Entity whose quota is consumed. Defaults to the entity associated with your account profile. Contact WorldFirst for special requirements.

-   `PAYER`: consumes CNY quota accumulated by payer.
-   `BENEFICIARY`: consumes CNY quota accumulated by the beneficiary.

##### tradeOrderType (string)

Type of trade materials supporting the quota usage.

-   `B2C`: Backed by B2C trade orders.
-   `B2B`: Backed by B2B trade orders.

#### reference (string)

Bank transfer reference text. May appear on the beneficiary's bank statement, depending on the local clearing system and receiving bank.

#### transferMemo (string)

Internal transfer memo for your own records. Not visible to the beneficiary.
## 批次通用选项

以下字段只在创建批次时传，不放入 items[]。来自官方 Create a Payout 的同名选项定义：

| 字段 | 取值与约束 |
|---|---|
| fvtPreference | NEED_FVT：请求全额到账，由付款方承担中间行费用 |
| oboPreference | NEED_OBO：支持 OBO 的渠道传递 payer 信息；NO_OBO：不应用 OBO |
| paymentType | LOCAL：本地清算；CROSS：跨境电汇；与 paymentNetwork 成对提供 |

## 场景枚举



### PaymentNetwork

|  |  |
| --- | --- |
| **Value** | **Description** |
| `ACH` | US Automated Clearing House. |
| `SEPA` | Single Euro Payments Area (standard). |
| `SEPA_INSTANT` | SEPA Instant Credit Transfer. |
| `FASTER_PAYMENTS` | UK Faster Payments. |
| `CHAPS` | UK CHAPS (same-day high-value). |
| `BACS` | UK BACS. |
| `FPS` | Hong Kong Faster Payment System. |
| `CHATS` | Hong Kong CHATS. |
| `RTGS` | Real-Time Gross Settlement. |
| `SWIFT` | Cross-border SWIFT wire transfer. |
| `FEDWIRE` | US Fedwire. |
| `TARGET2` | Euro TARGET2 RTGS. |
| `NPP` | Australia New Payments Platform. |
| `BECS` | Bulk Electronic Clearing System in Australia and New Zealand. |
| `FAST` | Singapore FAST. |
| `PROMPTPAY` | Thailand PromptPay. |
| `DUITNOW` | Malaysia DuitNow. |
| `GIRO` | Singapore/Malaysia GIRO. |
| `BI_FAST` | Indonesia BI-FAST. |
| `SKN` | Indonesia SKNBI. |
| `RENTAS` | Malaysia RENTAS RTGS. |
| `INSTAPAY` | Philippines InstaPay. |
| `PESONET` | Philippines PESONet. |
| `PHILPASS` | Philippines PhilPaSS. |
| `MPES` | Singapore MAS Electronic Payment System (MEPS+). |
| `BAHTNET` | Thailand BAHTNET real-time gross settlement system. |
| `ELIXIR` | Poland ELIXIR. |
| `ELIXIR_INSTANT` | Poland ELIXIR Instant. |
| `SORBNET` | Poland SORBNET. |
| `TBFX` | Payment route for IDR payouts in Indonesia. |
| `SEPI` | Mexico interbank electronic payment system (SPEI). |
| `ETF` | Canadian electronic funds transfer (EFT). |

### BusinessSceneCode

|  |  |
| --- | --- |
| **Value** | **Description** |
| `THIRD_PARTY_PAYOUT` | Payout to third-party bank account/digital wallet. |
| `SAME_NAME_PAYOUT` | Withdrawal to a same-name bank account/digital wallet. |
| `MULTI_ACCOUNT_TRANSFER` | Transfer between parent and sub-accounts. |

### PurposeCode

|  |  |
| --- | --- |
| **Value** | **Description** |
| `GDS` | Payment for goods. |
| `GST` | General Service Trade |
| `ACM` | Agency commission. |
| `COM` | Commission payment. |
| `TOA` | Transfer to own account, same-name withdrawal. |
| `SAL` | Salary/payroll. |
| `TXS` | Government-imposed charges including income tax, customs duties, and transfer payments. |

### FeeItemType

|  |  |
| --- | --- |
| **Value** | **Description** |
| `REMIT_SERVICE_FEE` | Basic remittance service fee. |
| `PAYOUT_SERVICE_FEE` | Basic payout service fee. |
| `OBO_SERVICE_FEE` | On-Behalf-Of (OBO) service fee. |
| `FVT_SERVICE_FEE` | Full Value Transfer (FVT) service fee. |

### WalletBrandName

|  |  |
| --- | --- |
| **Value** | **Description** |
| `WORLDFIRST` | WorldFirst wallet. |
| `ALIPAY` | Alipay. |
| `OVO` | OVO (Indonesia). |
| `GOPAY` | GoPay (Indonesia). |
| `DANA` | DANA (Indonesia). |
| `LINKAJA` | LinkAja (Indonesia). |
| `SHOPEEPAY` | ShopeePay (Indonesia). |
| `GCASH` | GCash (Philippines). |
| `VNPT` | VNPT Pay (Vietnam). |
| `MOMO` | MoMo (Vietnam). |
| `ZALOPAY` | ZaloPay (Vietnam). |
| `VNPAY` | VNPay (Vietnam). |
| `JAZZCASH` | JazzCash (Pakistan). |
| `EASYPAISA` | Easypaisa (Pakistan). |
| `BKASH` | bKash (Bangladesh). |
| `TNGD` | Touch 'n Go Digital (Malaysia). |
| `ZAMTEL_MOBILE_MONEY` | Zamtel Mobile Money (Zambia). |

### WalletAccountType

|  |  |
| --- | --- |
| **Value** | **Description** |
| `BALANCE` | Standard balance account. |
| `SUBLEDGER` | Sub-ledger account. |
| `WORLDFIRST_RECEIVE_ACCOUNT` | WorldFirst receiving account (RA). |
| `ALIPAY_ACCOUNT_LOGON_ID` | Alipay account login ID (e.g., email address). |
| `ALIPAY_ACCOUNT_NUMBER` | Alipay account number (e.g., 2088xxxxx). |

### AccountType

|  |  |
| --- | --- |
| **Value** | **Description** |
| `BANK_ACCOUNT` | Bank account |
| `DIGITAL_WALLET` | Digital wallet |

### BalanceType

|  |  |
| --- | --- |
| **Value** | **Description** |
| `BALANCE` | Balance account |
| `SUBLEDGER` | Sub-ledger account |
