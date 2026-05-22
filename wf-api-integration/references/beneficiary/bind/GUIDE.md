# bindBeneficiary 接口接入指引

## 接口说明

绑定收款人到当前 WF 账户，获取 `beneficiaryToken` 用于后续代发。

## 官方文档

- [bindBeneficiary 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/bind_beneficiary)

## 请求地址

`POST /amsin/api/v1/business/account/bindBeneficiary`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `bindBeneficiaryRequestId` | String | **Yes** | 幂等请求 ID，最大 64 字符 |
| `beneficiaryType` | String | **Yes** | 账户类型 |
| `beneficiaryBankAccount` | Object | Conditional | 银行账户信息，按卡模版字段填写 |
| `beneficiaryAlipayAccount` | Object | Conditional | 支付宝账户信息 |
| `thirdPartyIdentity` | Object | Conditional | 三方身份信息（CN/CNY 三方场景必填） |
| `countryCode` | String | Conditional | ISO-3166 2 位字母 |
| `currency` | String | Conditional | ISO-4217 3 位字母 |
| `beneficiaryNick` | String | Conditional | 收款人昵称，最大 70 字符 |

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API result |
| `beneficiary` | Beneficiary | 绑定的收款人信息（含 `beneficiaryToken`） |

## 错误码

| Code | Description |
|------|-------------|
| `CARD_TEMPLATE_NOT_EXIST` | 卡模版不存在 |
| `BENEFICIARY_ALREADY_EXISTED` | 收款人已存在 |
| `EXCEED_MAX_COUNT_LIMIT` | 超过最大收款人数量 |
| `RISK_REJECT` | 风控拒绝 |

## 示例代码

参考同目录下 `java/` 中的模板代码。

### Java 模板结构

```
java/
└── model/
    ├── domain/
    │   ├── Beneficiary.java
    │   ├── BeneficiaryBankAccount.java
    │   ├── BeneficiaryAlipayAccount.java
    │   ├── ThirdPartyIdentity.java
    │   └── Address.java
    ├── request/BindBeneficiaryRequest.java
    └── response/BindBeneficiaryResponse.java
```

### 默认测试卡信息

```
bankAccountName = "vaL2LTest"
bankAccountNo = "100100004623"
bankName = "STARK bankName"
bankBIC = "CITIHKHX"
bankCountryCode = "HK"
beneficiaryType = "THIRD_PARTY_PERSONAL_BANK_ACCOUNT"
```

