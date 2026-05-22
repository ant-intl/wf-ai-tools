# notifyBindBeneficiary 回调接入指引

## 接口说明

收款人绑定成功后，WF 主动调用此接口将收款人绑定结果通知给集成商。

这是一个**入站回调**（WF → 集成商），与其他出站 API 不同，需要：
1. 接收 WF 的 HTTP 请求并**验签**
2. 处理业务逻辑
3. 构建响应并**签名**后返回

在收到通知后，集成商需正确发送响应。若不向 WF 发送响应信息，WF 会重新发送最多 **7 次**请求通知。

## 官方文档

- [notifyBindBeneficiary 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/notify_bind_beneficiary)

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
| `bindBeneficiaryRequestId` | String | **Yes** | 集成商定义的幂等请求 ID，最大 64 字符 |
| `result` | Result | **Yes** | 收款人绑定结果 |
| `beneficiary` | Beneficiary | **Yes** | 收款人信息（仅 result.resultStatus=S 时有值） |

### Beneficiary

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `beneficiaryToken` | String | **Yes** | Base64 加密的收款人银行账户信息 |
| `bindBeneficiaryRequestId` | String | **Yes** | 集成商定义的幂等请求 ID |
| `beneficiaryBankAccount` | Object | No | 收款人银行账户信息 |
| `countryCode` | String | No | 收款行所在国家/地区，ISO-3166 2 位字母 |
| `currency` | String | No | 币种，ISO-4217 3 位字母 |
| `beneficiaryType` | String | No | 账户类型 |
| `beneficiaryNick` | String | No | 收款人昵称 |
| `status` | String | **Yes** | 收款人是否可用：`SUCCESS` / `FAIL` |
| `referenceBeneficiaryId` | String | No | 集成商定义的收款人唯一 ID，最大 64 字符 |

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
3. 解析 NotifyBindBeneficiaryRequest
4. 以 bindBeneficiaryRequestId 做**幂等判断**，已处理则直接返回 SUCCESS
5. **异步处理** beneficiary 业务逻辑
6. 构建 NotifyBindBeneficiaryResponse（resultCode=SUCCESS）
7. 调用 WfSigner 对响应体签名，写入响应头 Signature
8. 返回响应

> 签名验证完成后立即返回 SUCCESS，业务逻辑异步处理，避免超时触发重试。

## WF 重试策略

未收到有效响应时，WF 重试 7 次，间隔：2 min → 10 min → 10 min → 1h → 2h → 6h → 15h。

## 错误码

| Code | resultStatus | Description |
|------|-------------|-------------|
| `SUCCESS` | S | 处理成功 |
| `PROCESS_FAIL` | F | 业务失败，WF 不再重试 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，WF 将重试 |

## 示例

### 请求体示例（WF → 集成商）

```json
{
  "bindBeneficiaryRequestId": "20103419388723400*****",
  "beneficiary": {
    "beneficiaryToken": "ALIPAYLfIqcPI58IL7yqfV+pPEC8V/akm2jIxdo5DLB1/3URdErkz/N8G++iOGYZBU+JCTCAo/b1OjVTvtl1*****",
    "bindBeneficiaryRequestId": "Test_17403***92",
    "status": "PROCESSING"
  },
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
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
├── controller/NotifyBindBeneficiaryController.java  ← Spring @RestController
└── model/
    ├── domain/NotifyBeneficiary.java                ← 回调中的收款人信息
    ├── request/NotifyBindBeneficiaryRequest.java     ← 入站请求
    └── response/NotifyBindBeneficiaryResponse.java   ← 出站响应
```

### Golang 模板结构

```
golang/
├── controller/
│   └── notify_bind_beneficiary_controller.go  ← http.Handler 实现
└── model/
    ├── request/notify_bind_beneficiary_request.go
    └── response/notify_bind_beneficiary_response.go
```
