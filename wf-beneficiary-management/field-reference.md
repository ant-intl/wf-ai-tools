# Beneficiary Management Field Reference

## CardTemplateField Object

| Field | Type | Description |
|-------|------|-------------|
| `fieldName` | String | 字段名称 |
| `fieldType` | String | 字段类型 |
| `required` | Boolean | 是否必填 |
| `maxLength` | Integer | 最大长度 |
| `description` | String | 字段描述 |

---

## BeneficiaryBankAccount Object

字段与 `PaymentMethodMetaData` 相同，按 `inquiryBeneficiaryTemplate` 返回的 `cardTemplateData` 填写：

| Field | Type | Description |
|-------|------|-------------|
| `bankAccountName` | String | 账户名称（英文） |
| `bankAccountNo` | String | 银行账号/卡号 |
| `bankName` | String | 银行名称（英文） |
| `bankBIC` | String | 银行 BIC/SWIFT |
| `bankAccountIBAN` | String | IBAN |
| `routingNumber` | String | 路由号码 |
| `beneficiaryAddress` | String | 受益人地址 |
| `beneficiaryCountryCode` | String | 受益人国家代码 |
| `beneficiaryPhone` | String | 受益人电话 |
| `bankBranchCode` | String | 银行分支代码 |
| `bankLocalName` | String | 银行本地名称 |
| `bankAccountLocalName` | String | 账户名称（本地文字） |

---

## BeneficiaryAlipayAccount Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `alipayAccountName` | String | **Yes** | 支付宝账户名称 |
| `alipayAccountId` | String | **Yes** | 支付宝账户ID |

---

## ThirdPartyIdentity Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `certificateNo` | String | **Yes** | 证件号（个人身份证/企业营业执照） |
| `address` | Address | No | 地址信息 |
| `phoneNumber` | String | No | 电话号码 |
| `email` | String | No | 邮箱地址 |

---

## Address Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `region` | String | **Yes** | 国家/地区代码 (ISO-3166) |
| `state` | String | No | 省/州, max 8 chars |
| `city` | String | No | 城市, max 32 chars |
| `address1` | String | No | 地址行1, max 128 chars |
| `address2` | String | No | 地址行2, max 128 chars |
| `zipCode` | String | No | 邮编, max 32 chars |

---

## Beneficiary Object (响应通用)

| Field | Type | Description |
|-------|------|-------------|
| `beneficiaryToken` | String | 收款人令牌 (Base64加密) |
| `beneficiaryNick` | String | 收款人昵称 |
| `beneficiaryType` | String | 账户类型 |
| `status` | String | 状态 |
| `referenceBeneficiaryId` | String | 集成商自定义ID |

---

## beneficiaryType Values

| Value | Description |
|-------|-------------|
| `THIRD_PARTY_PERSONAL_BANK_ACCOUNT` | 第三方个人银行账户 |
| `THIRD_PARTY_COMPANY_BANK_ACCOUNT` | 第三方企业银行账户 |
| `PERSONAL_BANK_ACCOUNT` | 个人银行账户（同名） |
| `COMPANY_BANK_ACCOUNT` | 企业银行账户（同名） |
| `RELATED_MERCHANT_COMPANY_BANK_ACCOUNT` | 关联商户企业银行账户 |
| `RELATED_MERCHANT_ALIPAY_COMPANY_ACCOUNT` | 关联商户支付宝企业账户 |
