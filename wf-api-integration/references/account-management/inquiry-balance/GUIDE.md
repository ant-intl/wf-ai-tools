# inquiryBalance 接口接入指引

## 接口说明

查询 WF 账户余额，支持按币种和余额类型过滤。

## 官方文档

- [inquiryBalance 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_balance)

## 请求地址

`POST /amsin/api/v1/business/account/inquiryBalance`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601 |
| `Connected-AccountId` | Conditional | 平台客户操作商户账户时必填 |

## 请求参数

| Field | Type | Description |
|-------|------|-------------|
| `currencyList` | List\<String\> | ISO-4217 货币代码，为空则返回所有币种 |
| `balanceTypes` | List\<String\> | `NORMAL_BALANCE`（默认）、`SAME_NAME_TOP_UP_BALANCE`、`BUDGET_BALANCE` |
| `budgetAccountId` | String | 当 `balanceTypes` 包含 `BUDGET_BALANCE` 时**必填** |

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Object | `resultStatus` (S/F/U) |
| `responseId` | String | 唯一响应 ID |
| `accountBalances` | List | 账户余额列表 |

### AccountBalance Object

| Field | Type | Description |
|-------|------|-------------|
| `accountNo` | String | 账户号 |
| `currency` | String | 币种 |
| `balanceType` | String | 余额类型 |
| `totalBalance` | Object | `{"currency": "USD", "value": 999450809995}` — value 为最小货币单位 |
| `availableBalance` | Object | 同上 |
| `frozenBalance` | Object | 同上 |

## 错误码

### 不可重试 (resultStatus=F)

`PARAM_ILLEGAL`, `INVALID_SIGNATURE`, `USER_NOT_EXIST`, `ACCOUNT_NOT_EXIST`, `CURRENCY_NOT_SUPPORT`, `CONTRACT_CHECK_FAIL`

### 可重试 (resultStatus=U)

`UNKNOWN_EXCEPTION`, `REQUEST_TRAFFIC_EXCEED_LIMIT`

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
├── client/
│   ├── InquiryAccountInfoClient.java          # 统一客户端，包含 balance + quota 两个方法
│   └── InquiryAccountInfoClientTest.java
└── model/
    ├── domain/AccountBalance.java
    ├── request/InquiryBalanceRequest.java
    └── response/InquiryBalanceResponse.java
```

> 注意：`InquiryAccountInfoClient` 同时包含 `inquiryBalance` 和 `inquiryAvailableQuota` 两个方法，
> `inquiry-available-quota` 模块下仅有 model 类，无需单独的 Client。

### Golang 模板结构

```
golang/
├── client/
│   ├── inquiry_account_info_client.go          # 统一客户端，包含两个方法
│   └── inquiry_account_info_client_integration_test.go
└── model/
    ├── request/inquiry_balance_request.go
    └── response/inquiry_balance_response.go
```

