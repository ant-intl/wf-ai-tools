# createTransfer 接口接入指引

## 接口说明

集成商可调用此接口进行资金的转账。资金会转入到另一个万里汇账户，即资金在万里汇账户之间流通。

转账步骤为异步。调用本接口后，万里汇仅返回此接口的调用结果，而在 notifyTransfer 接口中返回转账结果。集成商也可以自行调用 inquiryTransfer 接口查询转账结果。

## 官方文档

- [createTransfer 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/create_transfer)

## 请求地址

`POST /amsin/api/v1/business/fund/createTransfer`

## 认证方式

RSA256 Signature — 参见 `references/common/` 下的签名工具代码。

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<base64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601, e.g. `2019-04-04T12:08:56+08:00` |

## 请求参数

### Root Level

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferRequestId` | String | **Yes** | 集成商定义的唯一转账 ID（幂等键），最大 64 字符 |
| `businessSceneCode` | String | Conditional | 转账业务类型。值：`MULTI_ACCOUNT_TRANSFER`（主/子账号余额互转）。主/子账号间转账时必填 |
| `transferFromDetail` | TransferFromDetail | **Yes** | 支付方转账详情 |
| `transferToDetail` | TransferToDetail | **Yes** | 收款方转账详情 |

### TransferFromDetail Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferFromAmount` | Amount | **Yes** | 支付方金额，必须指定 `currency` |

### TransferToDetail Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferToAmount` | Amount | **Yes** | 收款方金额，必须指定 `currency` |

### Amount Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `currency` | String | **Yes** | ISO-4217 货币代码，如 `USD` |
| `value` | **Long** | Conditional | 最小货币单位的整数。2 位小数币种 × 100，0 位小数币种 × 1 |

## 响应参数

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U), `resultCode`, `resultMessage` |
| `transferRequestId` | String | S | 集成商定义的请求 ID 回传 |
| `transferId` | String | S | WF 生成的转账 ID |
| `businessSceneCode` | String | S | 转账业务类型 |
| `transferFromDetail` | TransferFromDetail | S | 支付方转账详情 |
| `transferToDetail` | TransferToDetail | S | 收款方转账详情 |

## PROCESSING 状态处理

当 `result.resultStatus=S` 且 `result.resultCode=PROCESSING` 时，转账仍在处理中。
调用方**必须**使用 inquiryTransfer 轮询最终状态。

## 错误码

### 不可重试 (resultStatus=F)

| Code | Handling |
|------|----------|
| `PARAM_ILLEGAL` | 检查请求参数 |
| `PROCESS_FAIL` | 业务失败，不重试 |
| `INVALID_SIGNATURE` | 签名无效 |
| `UN_SUPPORT_BUSINESS` | 不支持的业务类型 |
| `BALANCE_NOT_ENOUGH` | 余额不足 |
| `REPEAT_REQ_INCONSISTENT` | 幂等冲突 |
| `QUOTE_EXPIRED` | 报价过期 |

### 可重试 (resultStatus=U) — 最多 7 次，指数退避：5/10/20/40/80/160/320 分钟

| Code | Handling |
|------|----------|
| `UNKNOWN_EXCEPTION` | 重试 |
| `REQUEST_TRAFFIC_EXCEED_LIMIT` | 重试 |

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
├── client/
│   ├── TransferClient.java         ← 转账客户端
│   └── TransferClientTest.java     ← 集成测试
└── model/
    ├── domain/
    │   ├── Amount.java
    │   ├── TransferFromDetail.java
    │   └── TransferToDetail.java
    ├── request/
    │   └── CreateTransferRequest.java
    └── response/
        └── CreateTransferResponse.java
```

### Golang 模板结构

```
golang/
├── client/
│   ├── transfer_client.go
│   └── transfer_integration_test.go
└── model/
    ├── domain/transfer.go
    ├── request/create_transfer_request.go
    └── response/create_transfer_response.go
```

## TransferClient 关键行为

1. **参数校验**：transferRequestId 不为空且 ≤ 64 字符；transferFromAmount.currency 不为空；transferToAmount.currency 不为空；value 至少指定一个
2. **调用**：`httpClientUtil.sendPostRequest(url, PATH_CREATE_TRANSFER, body)`
3. **结果处理**：`S` → 返回；`F`/`U` → 抛出 `WfException`

## 测试方法

| 方法 | 说明 |
|------|------|
| `testCreateTransfer` | 户到户转账，指定 transferToAmount |
| `testCreateTransferMultiAccount` | 主/子账号余额互转，设置 businessSceneCode=MULTI_ACCOUNT_TRANSFER |

