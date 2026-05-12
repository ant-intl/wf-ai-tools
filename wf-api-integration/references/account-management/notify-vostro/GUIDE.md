# notifyVostro 接口接入指引

## 接口说明

当集成商的万里汇账户发生充值后，万里汇会调用本接口通知集成商关于资金变化的信息。

**注意：本接口为回调通知接口（WF → 集成商），集成商需实现 HTTP 端点接收通知并返回响应。**

## 官方文档

- [notifyVostro 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/notify_vostro)

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
| `fundingId` | String | Yes | 由万里汇定义、用于唯一标识某次垫付请求。幂等字段，最大 128 字符 |
| `balanceResult` | Result | Yes | 代表本次垫付是否成功 |
| `payerBankAccount` | PayerBankAccount | Yes | 付款人银行账户信息 |
| `beneficiaryAccount` | BeneficiaryBankAccount | Yes | 收款人万里汇 VA 账户信息 |
| `balanceChangeAmount` | Amount | Yes | 账户余额变动的金额 |
| `balanceChangeTime` | String | No | 账户余额变动的时间，ISO 8601 格式 |
| `remitInfo` | String | No | 垫付请求附加信息，最大 530 字符 |

### PayerBankAccount Object

| Field | Type | Description |
|-------|------|-------------|
| `payerBankAccountNo` | String | 付款人银行账号 |
| `payerBankName` | String | 付款人银行名称 |

### BeneficiaryBankAccount Object

| Field | Type | Description |
|-------|------|-------------|
| `beneficiaryBankAccountNo` | String | 收款人万里汇 VA 账号 |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | String | ISO-4217 货币代码 |
| `value` | Long | 以最小货币单位表示的整数金额 |

### Result Object (balanceResult)

| Field | Type | Description |
|-------|------|-------------|
| `resultCode` | String | 结果码 |
| `resultStatus` | String | S/F/U |
| `resultMessage` | String | 结果描述 |

## 响应参数（集成商返回给 WF）

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `result` | Result | Yes | 代表接口调用结果 |

## balanceResult.resultCode

| 结果码 | 值 | 结果码信息 | 行动建议 |
|--------|-----|-----------|---------|
| SUCCESS | S | Success | 垫付成功 |
| REFUND | S | Refund Success | 退款成功 |

## result.resultCode（集成商响应）

| 结果码 | 值 | 结果码信息 | 行动建议 |
|--------|-----|-----------|---------|
| SUCCESS | S | Success | 确认收到通知 |
| UNKNOWN_EXCEPTION | U | API failed due to an unknown reason | 万里汇将重试 |

## 重试机制

如果集成商不向万里汇发送响应信息，万里汇会重新发送请求通知：

- 重试总数：7 次
- 两次重发请求之间的间隔：2分钟、10分钟、10分钟、1小时、2小时、6小时、15小时

## 处理流程

1. 从请求头提取 Signature 并调用 WfSigner.verifySignature() **验签**
2. 验签失败 → 返回 HTTP 400，不返回 SUCCESS
3. 解析 NotifyVostroRequest
4. 以 fundingId 做**幂等判断**，已处理则直接返回 SUCCESS
5. 根据 balanceResult 处理**垫付成功/退款成功**业务逻辑
6. 构建 NotifyVostroResponse（resultCode=SUCCESS）
7. 调用 WfSigner 对响应体签名，写入响应头 Signature
8. 返回响应

> 签名验证完成后立即返回 SUCCESS，业务逻辑异步处理，避免超时触发重试。

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
├── controller/NotifyVostroController.java    ← Spring @RestController
└── model/
    ├── domain/
    │   ├── PayerBankAccount.java
    │   └── VostroBeneficiaryAccount.java
    ├── request/NotifyVostroRequest.java
    └── response/NotifyVostroResponse.java
```

### Golang 模板结构

```
golang/
├── controller/
│   └── notify_vostro_controller.go           ← http.Handler 实现
└── model/
    ├── request/notify_vostro_request.go
    └── response/notify_vostro_response.go
```
