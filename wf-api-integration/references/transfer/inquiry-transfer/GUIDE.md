# inquiryTransfer 接口接入指引

## 接口说明

集成商可调用此接口查询转账结果。

注意：

- 集成商需设定 timeout 机制中断查询，以处理转账后无明确结果的情况；
- 若调用 createTransfer 接口后 2 小时没有接收到万里汇通知，且调用 inquiryTransfer 接口后 `transferResult.resultCode` 返回 `UNKNOWN`，联系万里汇技术支持。

## 请求地址

`POST /amsin/api/v1/business/fund/inquiryTransfer`

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
| `transferRequestId` | String | **Yes** | 集成商定义的唯一转账识别 ID（幂等键），最大 64 字符 |

## 响应参数

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U), `resultCode`, `resultMessage` |
| `transferRequestId` | String | S | 集成商定义的转账请求 ID 回传，最大 64 字符 |
| `transferId` | String | S | WF 生成的转账 ID，最大 64 字符 |
| `businessSceneCode` | String | S | 转账业务类型码。`MULTI_ACCOUNT_TRANSFER`（主/子账号余额互转）或 `ATOMIC_TRANSFER`（户到户转账） |
| `transferResult` | Result | S | 转账结果，包含 `resultStatus`/`resultCode`/`resultMessage` |
| `transferFinishTime` | DateTime | S | 转账结束时间，ISO 8601 格式。仅在重复请求时返回 |
| `transferFromDetail` | TransferFromDetail | S | 支付方转账详情 |
| `transferToDetail` | TransferToDetail | S | 收款方转账详情 |

## 双层结果码说明

响应包含两个结果码：

- `result.resultStatus`：API 调用结果
- `transferResult.resultStatus`：转账结果

### result.resultStatus 取值

| Status | 描述 |
|--------|------|
| `S` | API 请求成功 |
| `F` | API 请求失败 |
| `U` | 结果未知，需重试。策略：最多 7 次，间隔 5/10/20/40/80/160/320 分钟 |

### transferResult.resultCode 业务结果码

| Code | Status | 描述 | 行动建议 |
|------|--------|------|----------|
| `SUCCESS` | S | 转账成功 | — |
| `PROCESSING` | S | 处理中 | 稍后调用该接口查询最终结果 |
| `PROCESS_FAIL` | F | 业务失败 | 联系万里汇技术支持 |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 重试 |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用正确的用户信息重试 |
| `USER_ACCOUNT_ABNORMAL` | F | 账户状态异常 | 使用其他用户信息重试 |
| `AMOUNT_EXCEED_LIMIT` | F | 金额超限 | 确认金额正确后重试 |
| `RISK_REJECT` | F | 风控拒绝 | 通知用户需通过风控审核 |
| `ORDER_IS_CLOSED` | F | 订单已关闭 | 换单重试 |
| `ORDER_IS_REVERSED` | F | 订单已冲正 | 换单重试 |
| `BALANCE_NOT_ENOUGH` | F | 余额不足 | 确认余额充足后重试 |
| `ORDER_NOT_FOUND` | U | 订单未找到 | 使用正确的订单信息重试 |

## 错误码

### 不可重试 (resultStatus=F)

| Code | Handling |
|------|----------|
| `PARAM_ILLEGAL` | 检查请求参数 |
| `PROCESS_FAIL` | 业务失败，不重试 |
| `INVALID_SIGNATURE` | 签名无效 |
| `INVALID_API` | 确认调用正确的 API |
| `INVALID_CLIENT` | Client ID 不存在或无效 |
| `METHOD_NOT_SUPPORTED` | 确认 HTTP 方法为 POST |

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
│   ├── TransferClient.java         ← 转账查询客户端
│   └── TransferClientTest.java     ← 集成测试
└── model/
    ├── request/
    │   └── InquiryTransferRequest.java
    └── response/
        └── InquiryTransferResponse.java
```

### Golang 模板结构

```
golang/
├── client/
│   ├── transfer_client.go
│   └── transfer_integration_test.go
└── model/
    ├── request/inquiry_transfer_request.go
    └── response/inquiry_transfer_response.go
```

> **注意**：inquiryTransfer 复用 `create-transfer` 中已有的 domain 对象（Amount、TransferFromDetail、TransferToDetail 等），无需重复创建。

## TransferClient 关键行为

1. **参数校验**：transferRequestId 不为空且 ≤ 64 字符
2. **调用**：`httpClientUtil.sendPostRequest(url, PATH_INQUIRY_TRANSFER, body)`
3. **结果处理**：`S` → 返回；`F`/`U` → 抛出 `WfException`

## 测试方法

| 方法 | 说明 |
|------|------|
| `testInquiryTransfer` | 查询户到户转账结果 |
| `testInquiryTransferMultiAccount` | 查询主/子账号余额互转结果 |
