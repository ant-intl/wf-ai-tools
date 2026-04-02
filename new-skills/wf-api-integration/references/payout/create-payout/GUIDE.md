# createPayout 接口接入指引

## 接口说明

调用此接口进行代发到第三方银行卡。支持明文卡模式和卡 token 模式。

## 请求地址

`POST /amsin/api/v1/business/fund/createPayout`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<base64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601, e.g. `2019-04-04T12:08:56+08:00` |
| `Connected-AccountId` | Conditional | 平台客户操作商户账户时必填 |

## 请求参数

### Root Level

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferRequestId` | String | **Yes** | 幂等键，最大 64 字符 |
| `transferFromDetail` | Object | **Yes** | 必须指定 `transferFromAmount.currency` |
| `transferToDetail` | Object | **Yes** | 收款方详情 |
| `businessSceneCode` | String | Conditional | 收款币种为 CNY 时必填：`THIRD_PARTY_PAYOUT` / `SAME_NAME_PAYOUT` |

### 接口约束

- `transferFromAmount.value` 和 `transferToAmount.value` 不能同时指定（二选一）
- 当指定收款方金额时，`transferFromAmount` 只传 `currency`
- 当指定付款方金额时，`transferToAmount` 只传 `currency`

### TransferToMethod Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `paymentMethodType` | String | **Yes** | `BANK_ACCOUNT_DETAIL`（明文卡）或 `BENEFICIARY_TOKEN`（token） |
| `paymentMethodMetaData` | Object | Conditional | 明文卡模式必填 |
| `paymentMethodId` | String | Conditional | token 模式必填（传 beneficiaryToken） |

### PaymentMethodMetaData Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `bankAccountName` | String | Conditional | 账户名称（英文） |
| `bankAccountNo` | String | **Yes** | 银行账号/卡号 |
| `bankName` | String | Conditional | 银行名称 |
| `bankBIC` | String | Conditional | SWIFT 代码 |
| `bankCountryCode` | String | Conditional | 国家代码（ISO-3166） |
| `beneficiaryType` | String | No | 收款方类型 |

## 响应参数

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U) |
| `transferRequestId` | String | S | 请求 ID 回传 |
| `transferId` | String | S | WF 生成的转账 ID |
| `chargeMode` | String | S | `INNER_DEDUCT` / `OUTER_DEDUCT` |

## PROCESSING 状态

`resultCode=PROCESSING` 时必须调用 inquiryPayout 轮询。

## 错误码

### 不可重试 (resultStatus=F)

`PARAM_ILLEGAL`, `PROCESS_FAIL`, `INVALID_SIGNATURE`, `CARD_INFO_NOT_MATCH`, `BALANCE_NOT_ENOUGH`, `AMOUNT_EXCEED_LIMIT`, `AVAILABLE_QUOTA_NOT_ENOUGH`, `QUOTE_EXPIRED`, `REPEAT_REQ_INCONSISTENT`

### 可重试 (resultStatus=U)

`UNKNOWN_EXCEPTION`, `REQUEST_TRAFFIC_EXCEED_LIMIT`, `FEE_EXCEPTION`

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
├── client/
│   ├── PayoutClient.java           ← 统一客户端（createPayout + inquiryPayout）
│   └── PayoutClientTest.java
└── model/
    ├── domain/
    │   ├── Amount.java, TransferFromDetail.java, TransferToDetail.java
    │   ├── TransferToMethod.java, PaymentMethodMetaData.java
    │   ├── BankAccountDetail.java, BeneficiaryInfo.java
    ├── request/CreatePayoutRequest.java
    └── response/CreatePayoutResponse.java
```

## 测试方法

| 方法 | 说明 |
|------|------|
| `testCreatePayoutPlaintextCard` | 明文卡模式 |
| `testCreatePayoutTokenMode` | token 模式 |
| `testCreatePayoutFromAmount` | 指定付款方金额 |

### 默认测试卡信息

```
bankAccountName = "vaL2LTest"
bankAccountNo = "100100004623"
bankName = "STARK bankName"
bankBIC = "CITIHKHX"
bankCountryCode = "HK"
beneficiaryType = "THIRD_PARTY_PERSONAL_BANK_ACCOUNT"
```

