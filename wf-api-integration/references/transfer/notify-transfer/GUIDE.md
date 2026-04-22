# notifyTransfer 回调接入指引

## 接口说明

转账完成后，万里汇主动调用此接口将转账结果通知给集成商。

集成商可在调用 createTransfer 接口时通过 `transferToDetail.transferNotifyUrl` 设置接收通知的地址。

这是一个**入站回调**（WF → 集成商），与其他出站 API 不同，需要：
1. 接收 WF 的 HTTP 请求并**验签**
2. 处理业务逻辑
3. 构建响应并**签名**后返回

在收到通知后，集成商需正确发送响应。若不向 WF 发送响应信息，WF 会重新发送最多 **7 次**请求通知。

## 入站请求（WF → 集成商）

### 请求头

| Header | Description |
|--------|-------------|
| `Client-Id` | WF client identifier |
| `Signature` | `algorithm=RSA256, keyVersion=2, signature=*****`，集成商需验签 |
| `Content-Type` | `application/json; charset=UTF-8` |
| `Request-Time` | ISO 8601，如 `2019-04-04T12:08:56+08:00` |

### 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferResult` | Result | **Yes** | 转账结果，包含 `resultStatus`/`resultCode`/`resultMessage` |
| `transferRequestId` | String | **Yes** | 集成商定义的唯一转账请求 ID（幂等键），最大 64 字符 |
| `transferId` | String | **Yes** | 万里汇生成的转账 ID，最大 64 字符 |
| `transferFinishTime` | String | No | 转账完成时间，ISO 8601 格式 |
| `transferFromDetail` | TransferFromDetail | No | 支付方转账详情 |
| `transferToDetail` | TransferToDetail | No | 收款方转账详情 |
| `transferOrderAddition` | TransferOrderAddition | No | 转账订单附加信息 |

### TransferFromDetail

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferFromAmount` | Amount | No | 支付方金额 |

### TransferToDetail

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `purposeCode` | String | No | 转账目的代码（GDS/TXS/ACM/GST/COM） |
| `transferToAmount` | Amount | No | 收款方金额 |
| `feeAmount` | Amount | No | 手续费 |
| `transferToMethod` | PaymentMethod | No | 收款方式 |
| `transferQuote` | Quote | No | 转账汇率 |

### TransferOrderAddition

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `referenceOrderId` | String | No | 集成商定义的关联订单 ID |

### Amount

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `currency` | String | **Yes** | ISO-4217 货币代码 |
| `value` | Long | **Yes** | 最小货币单位的整数金额 |

### Result

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `resultCode` | String | **Yes** | 结果码 |
| `resultStatus` | String | **Yes** | `S`/`F`/`U` |
| `resultMessage` | String | No | 结果描述 |

## 出站响应（集成商 → WF）

返回前**必须签名**。

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `result.resultCode` | String | **Yes** | `SUCCESS` / `UNKNOWN_EXCEPTION` / `PROCESS_FAIL` |
| `result.resultStatus` | String | **Yes** | `S` / `F` / `U` |
| `result.resultMessage` | String | No | 结果描述 |

## 处理流程

1. 从请求头提取 Signature 并调用 WfSigner.verifySignature() **验签**
2. 验签失败 → 返回 HTTP 400，不返回 SUCCESS
3. 解析 NotifyTransferRequest
4. 以 transferRequestId 做**幂等判断**，已处理则直接返回 SUCCESS
5. 根据 transferResult 判断转账结果：
   - `transferResult.resultStatus=S` 且 `transferResult.resultCode=SUCCESS` → 转账成功
   - `transferResult.resultStatus=F` → 转账失败
6. **异步处理**转账结果业务逻辑
7. 构建 NotifyTransferResponse（resultCode=SUCCESS）
8. 调用 WfSigner 对响应体签名，写入响应头 Signature
9. 返回响应

> 签名验证完成后立即返回 SUCCESS，业务逻辑异步处理，避免超时触发重试。

## WF 重试策略

未收到有效响应时，WF 重试 7 次，间隔：2 min → 10 min → 10 min → 1h → 2h → 6h → 15h。

## 错误码

### result.resultCode（响应结果码）

| Code | resultStatus | Description |
|------|-------------|-------------|
| `SUCCESS` | S | 处理成功 |
| `PROCESS_FAIL` | F | 业务失败，WF 不再重试 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，WF 将重试 |

### transferResult.resultCode（转账业务结果码）

| Code | resultStatus | Description | 行动建议 |
|------|-------------|-------------|----------|
| `SUCCESS` | S | 转账成功 | — |
| `PROCESS_FAIL` | F | 业务失败 | 联系万里汇技术支持 |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 重试 |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用正确的用户信息重试 |
| `USER_ACCOUNT_ABNORMAL` | F | 账户状态异常 | 使用其他用户信息重试 |
| `AMOUNT_EXCEED_LIMIT` | F | 金额超限 | 确认金额正确后重试 |
| `RISK_REJECT` | F | 风控拒绝 | 通知用户需通过风控审核 |
| `ORDER_IS_CLOSED` | F | 订单已关闭 | 换单重试 |
| `ORDER_IS_REVERSED` | F | 订单已冲正 | 换单重试 |
| `BALANCE_NOT_ENOUGH` | F | 余额不足 | 确认余额充足后重试 |

## 示例

### 请求体示例（WF → 集成商）

```json
{
  "transferResult": {
    "resultCode": "SUCCESS",
    "resultMessage": "success.",
    "resultStatus": "S"
  },
  "transferRequestId": "*****",
  "transferId": "*****",
  "transferFinishTime": "2022-05-11T06:27:25Z",
  "transferFromDetail": {
    "transferFromAmount": {
      "currency": "USD",
      "value": 1000
    }
  },
  "transferToDetail": {
    "purposeCode": "GDS",
    "transferToAmount": {
      "currency": "EUR",
      "value": 938
    },
    "feeAmount": {
      "currency": "USD",
      "value": 10
    },
    "transferToMethod": {
      "customerId": "*****",
      "paymentMethodType": "BALANCE"
    },
    "transferQuote": {
      "quoteId": "*****"
    }
  },
  "transferOrderAddition": {
    "referenceOrderId": "*****"
  }
}
```

### 响应体示例（集成商 → WF）

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success.",
    "resultStatus": "S"
  }
}
```

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
├── controller/NotifyTransferController.java  ← Spring @RestController
└── model/
    ├── request/NotifyTransferRequest.java     ← 入站请求
    └── response/NotifyTransferResponse.java   ← 出站响应
```

### Golang 模板结构

```
golang/
├── controller/
│   └── notify_transfer_controller.go  ← http.Handler 实现
└── model/
    ├── request/notify_transfer_request.go
    └── response/notify_transfer_response.go
```
