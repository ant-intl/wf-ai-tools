# Validate a Beneficiary 接口接入指引

## 接口说明

预校验收款人信息而不实际创建。用于在调用 create_a_beneficiary 前验证字段格式和约束是否符合要求，提前发现字段级错误。

## 官方文档

- [validate_a_beneficiary 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/validate_a_beneficiary)

## 请求地址

`POST /api/open/v1/beneficiaries/validate`

## 请求参数

与 create_a_beneficiary 请求参数相同。

### 请求示例

```json
{
  "region": "US",
  "currency": "USD",
  "accountType": "BANK_ACCOUNT",
  "entityType": "PERSONAL",
  "relationType": "THIRD_PARTY",
  "bankDetails": {
    "beneficiaryRegion": "US",
    "accountNumber": "50001121",
    "routingNumber": "021000021"
  }
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果，见 [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result)。result 为 SUCCESS 且 validationErrors 为空数组时表示校验通过 |
| `validationErrors` | array | 字段级校验错误列表（全部通过时为空数组，最多 50 条） |

#### validationErrors 子参数

| Field | Type | Description |
|-------|------|-------------|
| `field` | string | 校验失败的字段路径（点号分隔，如 `bankDetails.sortCode`）。最多 256 字符 |
| `type` | string | 错误类型：`FIELD_OPTION_NOT_MATCH`（值不在允许选项中）、`FIELD_PATTERN_NOT_MATCH`（值不匹配正则） |
| `desc` | string | 纯文本错误描述。最多 512 字符 |
| `restrictionRegex` | string | 需匹配的正则表达式（`type` 为 `FIELD_PATTERN_NOT_MATCH` 时返回） |
| `restrictionOptions` | array | 允许的选项值列表（`type` 为 `FIELD_OPTION_NOT_MATCH` 时返回） |

#### restrictionOptions 子参数

| Field | Type | Description |
|-------|------|-------------|
| `optionName` | string | 选项显示名称 |
| `optionValue` | string | 选项提交值 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "validationErrors": []
}
```

### 校验失败响应示例

```json
{
  "result": {
    "resultCode": "PARAM_ILLEGAL",
    "resultMessage": "Illegal parameters exist.",
    "resultStatus": "F"
  },
  "validationErrors": [
    {
      "field": "bankDetails.sortCode",
      "type": "FIELD_PATTERN_NOT_MATCH",
      "desc": "sortCode format is invalid",
      "restrictionRegex": "^[0-9]{6}$"
    }
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 | 排查建议 |
|------------|--------------|------|------------|
| `SUCCESS` | S | 成功 | — |
| `PARAM_ILLEGAL` | F | 参数不合法 | 查看 `validationErrors` 中的字段级错误详情，修正后重试 |
| `PROCESS_FAIL` | F | 业务处理失败 | 不可重试，联系 WorldFirst 技术支持 |
| `UN_SUPPORT_BUSINESS` | F | 不支持的业务 | 确认参数值在支持范围内后重试 |
| `USER_NO_PERMISSION` | F | 用户无权限 | 使用有权限的用户账号重试 |
| `INVALID_CLIENT` | F | 客户端无效 | 确认 Client ID 存在且有效 |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用有效的用户凭证重试 |

## 示例代码

参考 [references/payouts/beneficiaries/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
beneficiaries/java/
├── service/
│   └── BeneficiaryService.java              # 薄封装 Service，包含 validateBeneficiary 方法
└── model/
    ├── domain/
    │   ├── Beneficiary.java                 # 收款人对象
    │   ├── BankDetail.java                  # 银行账户详情
    │   └── WalletDetail.java                # 钱包账户详情
    ├── request/
    │   └── ValidateBeneficiaryRequest.java  # 校验请求
    └── response/
        └── ValidateBeneficiaryResponse.java  # 校验响应（含 validationErrors）
```

## 集成使用方式

BeneficiaryService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `ValidateBeneficiaryRequest` 设置与 create_a_beneficiary 相同的参数
2. 调用 `BeneficiaryService.validateBeneficiary(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，并查看 `validationErrors` 是否为空数组，确认校验通过后再调用 create_a_beneficiary

### 业务代码示例

```java
// 构造请求
ValidateBeneficiaryRequest request = new ValidateBeneficiaryRequest();
request.setAccountType("BANK_ACCOUNT");
request.setEntityType("PERSONAL");
request.setRelationType("THIRD_PARTY");
request.setRegion("US");
request.setCurrency("USD");
// ... 设置其他字段

// 调用 Service（内部已强制验签，验签失败抛 WfException）
ValidateBeneficiaryResponse response = beneficiaryService.validateBeneficiary(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    if (response.getValidationErrors() == null || response.getValidationErrors().isEmpty()) {
        System.out.println("校验通过，可以创建收款人");
    } else {
        for (ValidationError error : response.getValidationErrors()) {
            System.err.println("字段 " + error.getField() + ": " + error.getDesc());
        }
    }
} else {
    System.err.println("校验失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BeneficiaryService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BeneficiaryService(config)` 创建实例。
