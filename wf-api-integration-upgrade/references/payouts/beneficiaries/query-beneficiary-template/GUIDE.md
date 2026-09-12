# Query a Beneficiary Template 接口接入指引

## 接口说明

查询指定地区、币种和账户类型的收款人字段模板，了解创建收款人时需要提供哪些字段及其校验规则。

## 官方文档

- [query_beneficiary_template 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_beneficiary_template)

## 请求地址

`POST /api/open/v1/beneficiaries/queryTemplate`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `region` | string | Yes | 地区（ISO 3166 两位字母代码，如 `US`、`GB`） |
| `currency` | string | Yes | 币种（ISO 4217 三字母代码，如 `USD`、`EUR`） |
| `accountType` | string | No | 账户类型，查询数字钱包模板时必填：`BANK_ACCOUNT`、`DIGITAL_WALLET` |
| `entityType` | string | No | 实体类型，须与 `accountType` 和 `relationType` 同时提供或同时省略：`COMPANY`、`PERSONAL` |
| `relationType` | string | No | 关系类型，须与 `accountType` 和 `entityType` 同时提供或同时省略：`SAME_NAME`、`THIRD_PARTY`、`RELATED_MERCHANT` |
| `paymentType` | string | No | 支付方式，省略时返回合并所有支付类型的模板：`LOCAL`、`CROSS` |
| `walletBrandName` | string | No | 钱包品牌名称，`accountType` 为 `DIGITAL_WALLET` 时必填，见 [WalletBrandName](https://docs.worldfirst.com/wfdocs/api-sdk/beneficiaries_overview) |

### 请求示例

```json
{
  "region": "US",
  "currency": "USD",
  "paymentType": "LOCAL"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果，见 [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result) |
| `templates` | array[BeneficiaryTemplate] | 字段模板列表，每次最多返回 5 个模板 |

### BeneficiaryTemplate 字段

| Field | Type | Description |
|-------|------|-------------|
| `fields` | array[TemplateField] | 字段列表，每个模板最多 20 个字段 |
| `walletBrandName` | string | 钱包品牌名称，`accountType` 为 `DIGITAL_WALLET` 时返回，见 [WalletBrandName](https://docs.worldfirst.com/wfdocs/api-sdk/beneficiaries_overview) |

### TemplateField 字段

| Field | Type | Description |
|-------|------|-------------|
| `fieldName` | string | 字段名称 |
| `fieldDescription` | string | 字段描述 |
| `required` | string | 是否必填：`Y`=必填，`N`=选填 |
| `restrictionType` | string | 限制类型：`PATTERN_RESTRICTION`（正则）、`OPTIONS_RESTRICTION`（枚举） |
| `restrictionMsg` | string | 限制提示信息 |
| `restrictionRegex` | string | 正则表达式（`restrictionType` 为 `PATTERN_RESTRICTION` 时返回） |
| `restrictionOptions` | array[TemplateOption] | 可选项列表（`restrictionType` 为 `OPTIONS_RESTRICTION` 时返回） |

### TemplateOption 字段

| Field | Type | Description |
|-------|------|-------------|
| `optionName` | string | 选项名称 |
| `optionValue` | string | 选项值 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "templates": [
    {
      "fields": [
        {
          "fieldName": "bankAccountName",
          "fieldDescription": "Beneficiary account name",
          "required": "Y",
          "restrictionType": "PATTERN_RESTRICTION",
          "restrictionMsg": "Only English letters, numbers, and special characters are supported. Max 70 characters.",
          "restrictionRegex": "^[A-Za-z0-9/() .,-?:'+&@]{0,70}$"
        },
        {
          "fieldName": "accountType",
          "fieldDescription": "Account Type",
          "required": "Y",
          "restrictionType": "OPTIONS_RESTRICTION",
          "restrictionMsg": "Please fill out this required field",
          "restrictionOptions": [
            { "optionName": "BANK_ACCOUNT", "optionValue": "BANK_ACCOUNT" },
            { "optionName": "DIGITAL_WALLET", "optionValue": "DIGITAL_WALLET" }
          ]
        }
      ]
    }
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 | 排查建议 |
|------------|--------------|------|----------|
| `SUCCESS` | S | 成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 确认 `region` 为 ISO 3166 两位代码、`currency` 为 ISO 4217 代码；查询钱包模板时确认 `accountType` 为 `DIGITAL_WALLET` 且提供 `walletBrandName` |
| `UN_SUPPORT_BUSINESS` | F | 不支持的业务 | 确认参数值在支持范围内后重试 |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用有效的用户凭证重试 |

## 示例代码

参考 [references/payouts/beneficiaries/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
beneficiaries/java/
├── service/
│   └── BeneficiaryService.java              # 薄封装 Service，包含 queryBeneficiaryTemplate 方法
└── model/
    ├── domain/
    │   ├── Beneficiary.java                 # 收款人对象
    │   ├── BankDetail.java                  # 银行账户详情
    │   ├── WalletDetail.java                # 钱包账户详情
    │   ├── BeneficiaryTemplate.java          # 字段模板（包含 fields 列表）
    │   ├── TemplateField.java               # 模板字段（含校验规则）
    │   └── TemplateOption.java              # 字段选项（枚举值）
    ├── request/
    │   └── QueryBeneficiaryTemplateRequest.java # 模板查询请求
    └── response/
        └── QueryBeneficiaryTemplateResponse.java # 模板响应结果
```

## 集成使用方式

BeneficiaryService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryBeneficiaryTemplateRequest` 设置 region、currency，按需设置 accountType、entityType、relationType、paymentType、walletBrandName
2. 调用 `BeneficiaryService.queryBeneficiaryTemplate(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，根据返回的字段模板准备创建收款人所需的参数

### 业务代码示例

```java
// 构造请求
QueryBeneficiaryTemplateRequest request = new QueryBeneficiaryTemplateRequest();
request.setRegion("US");
request.setCurrency("USD");
request.setPaymentType("LOCAL");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryBeneficiaryTemplateResponse response = beneficiaryService.queryBeneficiaryTemplate(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    // 遍历模板列表，了解创建收款人时需要提供哪些字段
    for (BeneficiaryTemplate template : response.getTemplates()) {
        for (TemplateField field : template.getFields()) {
            System.out.println(field.getFieldName() + " - required: " + field.getRequired());
        }
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BeneficiaryService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BeneficiaryService(config)` 创建实例。
