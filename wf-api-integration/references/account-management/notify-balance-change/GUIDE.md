# notifyBalanceChange 接口接入指引

## 接口说明

当集成商的万里汇余额账户发生动账交易后，万里汇会调用本接口通知集成商关于所有账务变动的信息。

**注意：本接口为回调通知接口（WF → 集成商），集成商需实现 HTTP 端点接收通知并返回响应。**

## 官方文档

- [notifyBalanceChange 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/notify_balance_change)

## 请求地址

`POST {集成商提供的回调地址}`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601 |

## 请求参数（WF 发送给集成商）

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `notifySequence` | Integer | Yes | 通知时序，即万里汇向您发送通知的累计序号 |
| `balanceChangeLogs` | Array\<BalanceChangeLog\> | Yes | 账户余额变动记录列表 |

### BalanceChangeLog Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `balanceChangeTime` | String | Yes | 账户余额变动的时间，ISO 8601 格式 |
| `accountNo` | String | Yes | 万里汇定义的唯一账户 ID，最大 32 字符 |
| `balanceType` | String | No | 余额类型，取值见下方说明，默认 `NORMAL_BALANCE` |
| `accountingBizNo` | String | Yes | 万里汇账单动账流水单号 |
| `transactionAmount` | Amount | Yes | 动账金额（正数为入账，负数为出账） |
| `accountBalance` | Amount | Yes | 转账后的实时账户余额 |
| `transactionType` | String | Yes | 交易类型，取值见下方说明 |
| `transactionId` | String | Conditional | 万里汇定义的交易唯一 ID，最大 64 字符。当 transactionType 为 TRANSFER/TRANSFER_REFUND/WITHDRAWAL/WITHDRAWAL_REFUND 时必填 |
| `extTransactionId` | String | No | 集成商定义的交易唯一 ID，最大 256 字符 |
| `beneficiaryName` | String | No | 收款人姓名（脱敏），最大 128 字符 |
| `beneficiaryAccountNo` | String | No | 收款人万里汇账号（脱敏），最大 64 字符 |
| `remarks` | String | No | 转账附言，最大 512 字符 |

### balanceType 取值

| 值 | 说明 |
|----|------|
| `NORMAL_BALANCE` | 普通余额类型（即电商余额类型），默认值 |
| `SAME_NAME_TOP_UP_BALANCE` | 同名充值余额类型 |
| `BUDGET_BALANCE` | 预算账户余额类型 |

### transactionType 取值

| 值 | 说明 |
|----|------|
| `TRANSFER` | 转账 |
| `TRANSFER_REFUND` | 转账退款 |
| `WITHDRAWAL` | 提款 |
| `WITHDRAWAL_REFUND` | 提款退款 |
| `COLLECTION` | 收款 |
| `COLLECTION_REFUND` | 收款退款 |
| `CONVERSION` | 换汇 |
| `CONVERSION_DEAL` | 换汇交割 |
| `CHARGE` | 扣费 |
| `CHARGE_REFUND` | 扣费退款 |
| `DEDUCTION` | 扣款 |
| `FUND_COLLECTION` | 资金归集 |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | String | ISO-4217 货币代码 |
| `value` | Long | 以最小货币单位表示的整数金额 |

## 响应参数（集成商返回给 WF）

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `result` | Result | Yes | 代表接口调用结果 |

### Result Object

| Field | Type | Description |
|-------|------|-------------|
| `resultCode` | String | 结果码 |
| `resultStatus` | String | S/F/U |
| `resultMessage` | String | 结果描述 |

## result.resultCode（集成商响应）

| 结果码 | 值 | 结果码信息 | 行动建议 |
|--------|-----|-----------|---------|
| SUCCESS | S | Success | 确认收到通知 |
| UNKNOWN_EXCEPTION | U | API failed due to unknown reasons | 万里汇将重试 |
| SYSTEM_ERROR | F | System error | 请勿重试，联系万里汇技术支持 |

## 重试机制

如果集成商不向万里汇发送响应信息，万里汇会重新发送请求通知：

- 重试总数：7 次
- 两次重发请求之间的间隔：2分钟、10分钟、10分钟、1小时、2小时、6小时、15小时

## 处理流程

1. 从请求头提取 Signature 并调用 WfSigner.verifySignature() **验签**
2. 验签失败 → 返回 HTTP 400，不返回 SUCCESS
3. 解析 NotifyBalanceChangeRequest
4. 以 notifySequence 做**幂等判断**，已处理则直接返回 SUCCESS
5. 遍历 balanceChangeLogs **逐条处理**余额变动
6. 构建 NotifyBalanceChangeResponse（resultCode=SUCCESS）
7. 调用 WfSigner 对响应体签名，写入响应头 Signature
8. 返回响应

> 签名验证完成后立即返回 SUCCESS，业务逻辑异步处理，避免超时触发重试。

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
├── controller/NotifyBalanceChangeController.java  ← Spring @RestController
└── model/
    ├── domain/
    │   └── BalanceChangeLog.java
    ├── request/NotifyBalanceChangeRequest.java
    └── response/NotifyBalanceChangeResponse.java
```

### Golang 模板结构

```
golang/
├── controller/
│   └── notify_balance_change_controller.go           ← http.Handler 实现
└── model/
    ├── request/notify_balance_change_request.go
    └── response/notify_balance_change_response.go
```
