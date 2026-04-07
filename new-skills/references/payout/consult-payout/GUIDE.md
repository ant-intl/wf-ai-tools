# consultPayout 接口接入指引

## 接口说明

在发起跨币种 createPayout 之前，调用此接口获取汇率报价（quoteId）。获取到 quoteId 后，将其传入 createPayout 请求的 `transferToDetail.transferQuote.quoteId` 字段。

## 请求地址

`POST /amsin/api/v1/business/fund/consultPayout`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<base64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601, e.g. `2019-04-04T12:08:56+08:00` |
| `Connected-AccountId` | Conditional | 平台客户操作商户账户时必填 |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferFromDetail` | Object | **Yes** | 付款方详情，必须指定 `transferFromAmount.currency` |
| `transferToDetail` | Object | **Yes** | 收款方详情（含 `transferToAmount`） |
| `businessSceneCode` | String | Conditional | 收款币种为 CNY 时必填：`THIRD_PARTY_PAYOUT` / `SAME_NAME_PAYOUT` |

### 接口约束

- `transferFromAmount.value` 和 `transferToAmount.value` 不能同时指定（二选一）

## 响应参数

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U), `resultCode`, `resultMessage` |
| `chargeMode` | String | S | 计费模式：`INNER_DEDUCT` / `OUTER_DEDUCT` |
| `transferFromDetail` | TransferFromDetail | S | 计算后的付款方金额 |
| `transferToDetail` | TransferToDetail | S | 计算后的收款方金额（含 `transferQuote`，其中包含 `quoteId`） |
| `availableQuota` | Amount | S (CNY) | 剩余可结汇额度（收款币种为 CNY 时返回） |

## 跨币种代发流程

1. **调用 consultPayout** 获取 quoteId
2. **从响应中提取 quoteId**：`response.transferToDetail.transferQuote.quoteId`
3. **调用 createPayout** 时传入 `transferToDetail.transferQuote.quoteId = quoteId`
4. **若返回 PROCESSING**，调用 inquiryPayout 轮询最终状态

## 错误码

### 不可重试 (resultStatus=F)

`PARAM_ILLEGAL`, `PROCESS_FAIL`, `INVALID_API`, `INVALID_CLIENT`, `INVALID_SIGNATURE`, `METHOD_NOT_SUPPORTED`, `UN_SUPPORT_BUSINESS`, `USER_NO_PERMISSION`, `CURRENCY_NOT_SUPPORT`, `USER_NOT_EXIST`, `USER_ACCOUNT_ABNORMAL`, `USER_STATUS_ABNORMAL`, `CONTRACT_NOT_EXIST`, `CONTRACT_CHECK_FAIL`, `CARD_INFO_NOT_MATCH`

### 可重试 (resultStatus=U) — 最多 7 次，指数退避

`UNKNOWN_EXCEPTION`, `REQUEST_TRAFFIC_EXCEED_LIMIT`, `FEE_EXCEPTION`

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

注意：consultPayout 的方法集成在 PayoutClient 中（与 createPayout、inquiryPayout 共用一个客户端类），PayoutClient 代码位于 `../create-payout/java/client/PayoutClient.java`。

### Java 模板结构

```
java/
└── model/
    ├── request/ConsultPayoutRequest.java
    └── response/ConsultPayoutResponse.java
```

### Golang 模板结构

```
golang/
└── model/
    ├── request/consult_payout_request.go
    └── response/consult_payout_response.go
```

## 测试方法

| 方法 | 说明 |
|------|------|
| `testConsultPayoutCrossCurrency` | 跨币种咨询（USD -> CNY），获取 quoteId |
| `testCrossCurrencyPayoutFlow` | 跨币种代发完整流程：consultPayout -> createPayout |

