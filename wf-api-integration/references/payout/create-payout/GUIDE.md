# createPayout 接口接入指引

## 接口说明

调用此接口进行代发到第三方银行卡或电子钱包（支付宝账户）。支持卡详情模式、卡 token 模式、支付宝账户详情模式、钱包账户模式和关联支付宝钱包模式。

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

| Field | Type | Required | Description                                                                                       |
|-------|------|----------|---------------------------------------------------------------------------------------------------|
| `paymentMethodType` | String | **Yes** | `BANK_ACCOUNT_DETAIL`（卡详情）、`BENEFICIARY_TOKEN`（token）、`ALIPAY_CN_DETAIL`（支付宝账户详情）、`WALLET_ACCOUNT_DETAIL`（钱包账户）或 `REFERENCE_ALIPAY_CN`（关联支付宝钱包） |
| `paymentMethodMetaData` | String | Conditional | 卡详情模式必填（BankAccountPaymentMethodDetail JSON）；支付宝账户详情模式；钱包账户模式必填（WalletAccountDetail JSON） |
| `paymentMethodId` | String | Conditional | token 模式传 beneficiaryToken；关联支付宝钱包模式传 referenceCustomerId；钱包账户模式传 walletAccountId         |

### PaymentMethodMetaData Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `bankAccountName` | String | Conditional | 账户名称（英文） |
| `bankAccountNo` | String | **Yes** | 银行账号/卡号 |
| `bankName` | String | Conditional | 银行名称 |
| `bankBIC` | String | Conditional | SWIFT 代码 |
| `bankCountryCode` | String | Conditional | 国家代码（ISO-3166） |
| `beneficiaryType` | String | No | 收款方类型 |

### WalletAccountDetail Object

钱包账户模式下 `paymentMethodMetaData` 的字段（JSON 字符串）：

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `walletFullName` | String | No | 钱包账户持有人姓名 |
| `walletAccountNo` | String | No | 钱包账号 |
| `walletBrandName` | String | No | 钱包品牌名称（如 GCASH） |
| `walletCountryCode` | String | No | 钱包国家代码（ISO-3166，2 位字母，如 PH） |

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

## 跨币种代发流程

跨币种代发需要先调用 consultPayout 获取汇率报价（quoteId），再传入 createPayout：

1. **调用 consultPayout** 获取 quoteId（参见 `../consult-payout/GUIDE.md`）
2. **从响应中提取 quoteId**：`response.transferToDetail.transferQuote.quoteId`
3. **调用 createPayout** 时传入 `transferToDetail.transferQuote.quoteId = quoteId`
4. **若返回 PROCESSING**，调用 inquiryPayout 轮询最终状态

> 注意：quoteId 有过期时间（quoteExpiryTime），过期后需重新调用 consultPayout 获取新报价。

## 收款方式

createPayout 支持五种互斥的收款方式（paymentMethodType）：

### 1. 卡详情模式（BANK_ACCOUNT_DETAIL）

直接传递银行卡详情信息，适用于未绑定收款人的场景。

- `paymentMethodType` = `BANK_ACCOUNT_DETAIL`
- `paymentMethodMetaData` = `BankAccountPaymentMethodDetail` 对象（必填 `bankAccountNo`）

### 2. 卡 token 模式（BENEFICIARY_TOKEN）

使用已绑定收款人的 token，适用于已通过 bindBeneficiary 绑定收款人的场景。

- `paymentMethodType` = `BENEFICIARY_TOKEN`
- `paymentMethodId` = `beneficiaryToken`（通过 bindBeneficiary 获取）

### 3. 支付宝账户详情模式（ALIPAY_CN_DETAIL）

代发到支付宝账户，`paymentMethodMetaData` 传空对象即可。

- `paymentMethodType` = `ALIPAY_CN_DETAIL`
- `paymentMethodMetaData` = `BankAccountPaymentMethodDetail` 对象（必填 `bankAccountNo`）

### 4. 关联支付宝钱包模式（REFERENCE_ALIPAY_CN）

代发到关联的支付宝钱包，通过 `paymentMethodId` 传递 `referenceCustomerId`。

- `paymentMethodType` = `REFERENCE_ALIPAY_CN`
- `paymentMethodId` = `referenceCustomerId`

### 5. 钱包账户模式（WALLET_ACCOUNT_DETAIL）

代发到钱包账户，通过 `paymentMethodMetaData` 传递钱包账户详情信息。

- `paymentMethodType` = `WALLET_ACCOUNT_DETAIL`
- `paymentMethodMetaData` = `WalletAccountDetail` 对象 JSON（包含 `walletFullName`、`walletAccountNo`、`walletBrandName`、`walletCountryCode`）

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
├── client/
│   ├── PayoutClient.java           ← 统一客户端（consultPayout + createPayout + inquiryPayout）
│   └── PayoutClientTest.java
└── model/
    ├── domain/
    │   ├── Amount.java
    │   ├── TransferFromDetail.java
    │   ├── TransferToDetail.java
    │   ├── TransferToMethod.java    ← paymentMethodMetaData 类型为 String（JSON 序列化）
    │   ├── PaymentMethodMetaData.java ← 银行账户详情（BANK_ACCOUNT_DETAIL 模式）
    │   ├── TransferQuote.java       ← 汇率报价信息（跨币种代发）
    │   ├── BankAccountDetail.java
    │   ├── BeneficiaryInfo.java
    │   └── WalletAccountDetail.java ← 钱包账户详情（WALLET_ACCOUNT_DETAIL 模式）
    ├── request/
    │   └── CreatePayoutRequest.java
    └── response/
        └── CreatePayoutResponse.java
```

## 测试方法

| 方法 | 说明 |
|------|------|
| `testConsultPayoutCrossCurrency` | 跨币种咨询（USD -> CNY），获取 quoteId |
| `testCrossCurrencyPayoutFlow` | 跨币种代发完整流程：consultPayout -> createPayout |
| `testCreatePayoutCardDetail` | 卡详情模式（同币种） |
| `testCreatePayoutTokenMode` | token 模式 |
| `testCreatePayoutFromAmount` | 指定付款方金额 |
| `testCreatePayoutAlipayDetail` | 支付宝账户详情模式（ALIPAY_CN_DETAIL） |
| `testCreatePayoutReferenceAlipay` | 关联支付宝钱包模式（REFERENCE_ALIPAY_CN） |
| `testCreatePayoutWalletAccount` | 钱包账户模式（WALLET_ACCOUNT_DETAIL） |

### 默认测试卡信息

```
bankAccountName = "vaL2LTest"
bankAccountNo = "100100004623"
bankName = "STARK bankName"
bankBIC = "CITIHKHX"
bankCountryCode = "HK"
beneficiaryType = "THIRD_PARTY_PERSONAL_BANK_ACCOUNT"
```

