# consultTransfer 接口接入指引

## 接口说明

在调用 createTransfer API 进行转账之前，集成商可调用此接口获取转账相关信息（如跨币种汇率、手续费等）。

此接口为同步接口，调用后直接返回咨询结果。

## 请求地址

`POST /amsin/api/v1/business/fund/consultTransfer`

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
| `transferFromDetail` | TransferFromDetail | **Yes** | 支付方提供的转账详细信息 |
| `transferToDetail` | TransferToDetail | **Yes** | 收款方接受的转账详细信息 |

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
| `value` | **Long** | Conditional | 最小货币单位的整数。2 位小数币种 x 100，0 位小数币种 x 1 |

## 响应参数

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U), `resultCode`, `resultMessage` |
| `transferFromDetail` | TransferFromDetail | S | 支付方转账详情（含计算后的金额、手续费等） |
| `transferToDetail` | TransferToDetail | S | 收款方转账详情（含汇率、实际到账金额等） |

### 响应 TransferFromDetail Object

| Field | Type | Description |
|-------|------|-------------|
| `transferFromMethod` | PaymentMethod | 转账付款方式 |
| `transferFromAmount` | Amount | 支付方需要支付的金额（计算手续费之前） |
| `actualTransferFromAmount` | Amount | 实际转账金额（计算手续费之后） |
| `feeAmount` | Amount | 付款方支付的转账手续费 |

### 响应 TransferToDetail Object

| Field | Type | Description |
|-------|------|-------------|
| `transferToMethod` | PaymentMethod | 收款方式 |
| `transferToAmount` | Amount | 转账收款金额（计算费用前） |
| `actualTransferToAmount` | Amount | 实际到账金额（计算手续费之后） |
| `feeAmount` | Amount | 转账手续费 |
| `transferQuote` | Quote | 转账汇率信息 |

## 错误码

### 不可重试 (resultStatus=F)

| Code | Handling |
|------|----------|
| `PARAM_ILLEGAL` | 检查请求参数 |
| `PROCESS_FAIL` | 业务失败，不重试 |
| `INVALID_API` | 确认调用正确的 API |
| `INVALID_CLIENT` | Client ID 不存在或无效 |
| `INVALID_SIGNATURE` | 签名无效 |
| `METHOD_NOT_SUPPORTED` | 确认 HTTP 方法为 POST |
| `UN_SUPPORT_BUSINESS` | 不支持的业务类型 |
| `USER_NO_PERMISSION` | 用户无权限 |
| `CURRENCY_NOT_SUPPORT` | 币种不支持 |
| `USER_NOT_EXIST` | 用户不存在 |
| `USER_ACCOUNT_ABNORMAL` | 用户账户状态异常 |
| `USER_STATUS_ABNORMAL` | 用户状态异常 |
| `CONTRACT_CHECK_FAIL` | 合约校验失败 |

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
│   ├── TransferClient.java         ← 转账客户端（新增 consultTransfer 方法）
│   └── TransferClientTest.java     ← 集成测试
└── model/
    ├── request/
    │   └── ConsultTransferRequest.java
    └── response/
        └── ConsultTransferResponse.java
```

### Golang 模板结构

```
golang/
├── client/
│   ├── transfer_client.go
│   └── transfer_integration_test.go
└── model/
    ├── request/
    │   └── consult_transfer_request.go
    └── response/
        └── consult_transfer_response.go
```

> **注意**：consultTransfer 复用 `create-transfer` 中已有的 domain 对象（Amount、TransferFromDetail、TransferToDetail、Quote、PaymentMethod），无需重复创建。

## TransferClient 关键行为

1. **参数校验**：transferFromAmount.currency 不为空；transferToAmount.currency 不为空；value 至少指定一个
2. **调用**：`httpClientUtil.sendPostRequest(url, PATH_CONSULT_TRANSFER, body)`
3. **结果处理**：`S` → 返回；`F`/`U` → 抛出 `WfException`

## 测试方法

| 方法 | 说明 |
|------|------|
| `testConsultTransfer` | 同币种转账咨询（如 USD → USD） |
| `testConsultTransferCrossCurrency` | 跨币种转账咨询（如 USD → GBP），验证返回汇率信息 |
