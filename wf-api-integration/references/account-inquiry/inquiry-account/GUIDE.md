# inquiryAccount 接口接入指引

## 接口说明

查询 WF 账户信息，包括账户类型、账号、激活状态、币种、银行账户信息等。

## 请求地址

`POST /amsin/api/v1/business/account/inquiryAccount`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601 |
| `Connected-AccountId` | Conditional | 平台客户操作商户账户时必填 |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `accountType` | String | **Yes** | 查询账号类型：`RECEIVE_ACCOUNT`（收款账户）、`VIRTUAL_ACCOUNT`（虚拟账户）、`ALIPAY_WALLET`（支付宝钱包）、`ALIPAY_SHADOW_WALLET`（关联公司支付宝钱包）、`ALIPAY_ORIGIN_WALLET`（企业支付宝钱包） |
| `referenceCustomerId` | String | Conditional | 集成商分配给注册用户的唯一用户ID。`accountType` 为 `RECEIVE_ACCOUNT` 或 `ALIPAY_WALLET` 时必填。最大长度：64 |
| `accountId` | String | Conditional | 万里汇账户唯一标识。`accountType` 为 `ALIPAY_SHADOW_WALLET` 时必填。最大长度：64 |
| `accessToken` | String | Conditional | OAuth 访问令牌。`accountType` 为 `VIRTUAL_ACCOUNT` 时必填。最大长度：64 |

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | `resultStatus` (S/F/U) |
| `responseId` | String | 唯一响应 ID，最大 32 位 |
| `accountId` | String | 万里汇账户唯一标识（`VIRTUAL_ACCOUNT`/`RECEIVE_ACCOUNT`/`ALIPAY_SHADOW_WALLET` 时返回） |
| `accountInfos` | List\<AccountInfo\> | 账号信息列表（`VIRTUAL_ACCOUNT`/`RECEIVE_ACCOUNT`/`ALIPAY_SHADOW_WALLET` 时返回） |
| `customer` | Customer | 客户信息 |
| `alipayCustomer` | AlipayCustomer | 企业支付宝用户信息（`ALIPAY_ORIGIN_WALLET` 时返回） |
| `affiliatedCustomer` | AlipayCustomer | 关联公司信息（`ALIPAY_SHADOW_WALLET` 时返回） |

### AccountInfo Object

| Field | Type | Description |
|-------|------|-------------|
| `accountNo` | String | 账户号码（`VIRTUAL_ACCOUNT`/`RECEIVE_ACCOUNT` 时返回） |
| `currencyList` | List\<String\> | 账户币种列表（ISO-4217） |
| `accountType` | String | 账号类型 |
| `accountStatus` | String | 账户状态：`ACTIVE`（已激活）、`ABNORMAL`（异常） |
| `bankAccountList` | List\<BankAccount\> | 银行账户信息（`VIRTUAL_ACCOUNT` 时返回） |

### BankAccount Object

| Field | Type | Description |
|-------|------|-------------|
| `bankAccountNo` | String | 银行账号 |
| `holderName` | UserName | 账号户主姓名 |
| `holderAccountType` | String | 户主账户类型：`INDIVIDUAL`/`COMPANY` |
| `holderAddress` | Address | 户主地址 |
| `currencyList` | List\<String\> | VA 对应币种列表 |
| `bankName` | String | 银行名称 |
| `routingNumber` | String | 汇款路径代码（USD+US 时必填） |
| `bankAddress` | Address | 银行地址 |
| `bankRegion` | String | 银行所在国家/地区（ISO-3166 二字母） |
| `bankBIC` | String | 银行 BIC 代码（8-11 位） |
| `bankAccountIBAN` | String | IBAN（EUR+EU 或 GBP+GB 时必填） |
| `bankAccountBSB` | String | BSB 号码（AUD+AU 或 NZD+NZ 时必填） |
| `accountCreationDate` | String | 账号创建时间 |
| `bankCode` | String | 银行代码 |
| `branchCode` | String | 银行分行代码 |
| `bankAccountType` | String | 银行账户类型：`checking`/`saving`（JPY/CAD 时必填） |
| `collectionArea` | String | 收款地区：`GLOBAL`/`LOCAL`（AUD/NZD 时必填） |
| `wireRoutingNumber` | String | Wire 汇款路线号码（USD+US 时必填） |
| `sortCode` | String | Sort Code（GBP+GB 或 EUR+GB 时必填） |

### Customer Object

| Field | Type | Description |
|-------|------|-------------|
| `accountId` | String | 万里汇账户标识 |
| `referenceCustomerId` | String | 集成商用户 ID |
| `customerCompanyName` | String | 注册公司名 |
| `certificateList` | List\<Certificate\> | 营业执照信息 |
| `legalEntityType` | String | 法律实体类型：`INDIVIDUAL`/`COMPANY` |
| `logonId` | String | 登录账号 |
| `verificationLevel` | String | 身份认证等级：`NOT_ALLOW_COLLECTION`/`ALLOW_COLLECTION` |

### Certificate Object

| Field | Type | Description |
|-------|------|-------------|
| `certificateNo` | String | 营业执照编号 |
| `certificateType` | String | 证书类型：`ENTERPRISE_REGISTRATION` |

### AlipayCustomer Object

| Field | Type | Description |
|-------|------|-------------|
| `alipayNo` | String | 支付宝账号 |
| `companyName` | String | 企业名称 |
| `region` | String | 所在国家/地区（ISO-3166 二字母） |

### UserName Object

| Field | Type | Description |
|-------|------|-------------|
| `firstName` | String | 名字 |
| `middleName` | String | 中间名 |
| `lastName` | String | 姓氏 |
| `fullName` | String | 全名 |

### Address Object

| Field | Type | Description |
|-------|------|-------------|
| `region` | String | 国家/地区（ISO-3166 二字母） |
| `state` | String | 省/州 |
| `city` | String | 城市 |
| `address1` | String | 地址第一行 |
| `address2` | String | 地址第二行 |
| `zipCode` | String | 邮编 |

## 错误码

### 系统结果码（不可重试 resultStatus=F）

`PARAM_ILLEGAL`, `OAUTH_FAIL`, `INVALID_API`, `INVALID_CLIENT`, `INVALID_SIGNATURE`, `METHOD_NOT_SUPPORTED`, `KEY_NOT_FOUND`, `MEDIA_TYPE_NOT_ACCEPTABLE`

### 业务结果码（不可重试 resultStatus=F）

`ACCESS_DENIED`, `ACCOUNT_NOT_EXIST`, `ACCESS_TOKEN_EXPIRED`, `AUTHORIZATION_NOT_EXIST`

### 可重试（resultStatus=U）

`UNKNOWN_EXCEPTION`, `REQUEST_TRAFFIC_EXCEED_LIMIT`

## 请求示例

```json
{
  "accountType": "RECEIVE_ACCOUNT",
  "referenceCustomerId": "YOUR_CUSTOMER_ID"
}
```

## 响应示例

```json
{
  "accountInfos": [
    {
      "accountNo": "20000001234567",
      "accountStatus": "ACTIVE",
      "accountType": "RECEIVE_ACCOUNT",
      "currencyList": ["EUR"]
    },
    {
      "accountNo": "20000001234568",
      "accountStatus": "ACTIVE",
      "accountType": "RECEIVE_ACCOUNT",
      "currencyList": ["USD"]
    }
  ],
  "customer": {
    "certificateList": [
      {
        "certificateNo": "91110000MA0XXXXXX",
        "certificateType": "ENTERPRISE_REGISTRATION"
      }
    ],
    "customerCompanyName": "Example Company Ltd.",
    "accountId": "20000001234567"
  },
  "accountId": "20000001234567",
  "responseId": "a1b2c3d4e5f6",
  "result": {
    "resultStatus": "S",
    "resultCode": "SUCCESS",
    "resultMessage": "success"
  }
}
```

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
└── model/
    ├── domain/
    │   ├── AccountInfo.java
    │   ├── BankAccount.java
    │   ├── Customer.java
    │   ├── Certificate.java
    │   ├── AlipayCustomer.java
    │   ├── UserName.java
    │   └── Address.java
    ├── request/InquiryAccountRequest.java
    └── response/InquiryAccountResponse.java
```

### Golang 模板结构

```
golang/
└── model/
    ├── domain/
    │   ├── account_info.go
    │   ├── bank_account.go
    │   ├── customer.go
    │   └── alipay_customer.go
    ├── request/inquiry_account_request.go
    └── response/inquiry_account_response.go
```

## 注意事项

1. **accountType 条件必填规则**：
   - `RECEIVE_ACCOUNT` / `ALIPAY_WALLET` → `referenceCustomerId` 必填
   - `VIRTUAL_ACCOUNT` → `accessToken` 必填
   - `ALIPAY_SHADOW_WALLET` → `accountId` 必填
2. **重试策略**: 遇到 `U` 状态时建议实现指数退避重试机制，最多重试 7 次
