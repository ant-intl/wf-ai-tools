# inquiryBeneficiaryTemplate 接口接入指引

## 接口说明

查询指定国家/币种/账户类型的卡模版信息，确定绑定收款人时需要传递哪些字段。

## 请求地址

`POST /amsin/api/v1/business/account/inquiryBeneficiaryTemplate`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `countryCode` | String | Conditional | ISO-3166 2 位字母，最大 2 字符 |
| `currency` | String | Conditional | ISO-4217 3 位字母 |
| `beneficiaryType` | String | Conditional | 账户类型 |

### beneficiaryType 值

| Value | Description |
|-------|-------------|
| `THIRD_PARTY_PERSONAL_BANK_ACCOUNT` | 第三方个人银行账户 |
| `THIRD_PARTY_COMPANY_BANK_ACCOUNT` | 第三方企业银行账户 |
| `PERSONAL_BANK_ACCOUNT` | 个人银行账户（同名） |
| `COMPANY_BANK_ACCOUNT` | 企业银行账户（同名） |

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API result |
| `responseId` | String | 唯一响应 ID |
| `cardTemplateData` | List\<CardTemplateField\> | 标准卡模版字段列表 |
| `localCardTemplateData` | List\<CardTemplateField\> | 本地清算网络模版 |
| `crossBorderCardTemplateData` | List\<CardTemplateField\> | 跨境清算网络模版 |

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

注意：BeneficiaryManagementClient 统一包含 5 个接口方法，代码位于 `java/client/BeneficiaryManagementClient.java`。

### Java 模板结构

```
java/
├── client/
│   ├── BeneficiaryManagementClient.java    ← 统一客户端（5 个方法）
│   └── BeneficiaryManagementClientTest.java
└── model/
    ├── domain/CardTemplateField.java
    ├── request/InquiryBeneficiaryTemplateRequest.java
    └── response/InquiryBeneficiaryTemplateResponse.java
```

