# inquiryPayout 接口接入指引

## 接口说明

查询代发单的处理状态。当 createPayout 返回 `PROCESSING` 时，必须调用此接口轮询最终结果。

## 官方文档

- [inquiryPayout 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_payout)

## 请求地址

`POST /amsin/api/v1/business/fund/inquiryPayout`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferId` | String | Conditional | WF 生成的代发单 ID。与 `transferRequestId` 二选一 |
| `transferRequestId` | String | Conditional | 集成商定义的请求 ID。与 `transferId` 二选一 |

## 响应参数

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | **API 调用级别**结果 |
| `transferResult` | Object | result.S | **代发单级别**结果 |
| `transferRequestId` | String | result.S | 请求 ID |
| `transferId` | String | result.S | 转账 ID |
| `transferFinishTime` | String | result.S | 完成时间 |
| `chargeMode` | String | result.S | 计费模式 |

### 两层结果区分

- `result.resultStatus=S` → 本次查询 API 调用成功，继续查看 `transferResult`
- `transferResult.resultCode=SUCCESS` → 代发单最终成功
- `transferResult.resultCode=PROCESSING` → 代发仍在处理中，继续轮询
- `transferResult.resultStatus=F` → 代发失败

### TransferResult Object

| Field | Type | Description |
|-------|------|-------------|
| `resultStatus` | String | `S` — 成功或处理中；`F` — 失败；`U` — 可重试 |
| `resultCode` | String | `SUCCESS` / `PROCESSING` / 失败码 |
| `resultMessage` | String | 结果描述 |

## 轮询策略

最多 7 次，指数退避：5/10/20/40/80/160/320 分钟。

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

注意：inquiryPayout 的方法集成在 PayoutClient 中（与 consultPayout、createPayout 共用一个客户端类），PayoutClient 代码位于 `../create-payout/java/client/PayoutClient.java`。

### Java 模板结构

```
java/
└── model/
    ├── request/InquiryPayoutRequest.java
    └── response/
        ├── InquiryPayoutResponse.java
        └── TransferResult.java        ← 代发单级别结果
```

## 测试方法

| 方法 | 说明 |
|------|------|
| `testInquiryPayoutByRequestId` | 按 transferRequestId 查询 |
| `testInquiryPayoutByTransferId` | 按 transferId 查询 |

