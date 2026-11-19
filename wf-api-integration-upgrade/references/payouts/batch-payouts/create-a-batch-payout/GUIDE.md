# Create a Batch Payout

> 规格来源：[官方 create_a_batch_payout](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_batch_payout)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/batchPayouts/create`

Create an empty batch payout container. The batch is created in INIT status with no items — use [Add Items to a Batch Payout](https://docs.worldfirst.com/wfdocs/api-sdk/add_items_to_a_batch_payout.md) to populate it, then call calculate for an indicative total amount, fee and FX preview (for reference only, not the final settlement amount) and submit to dispatch. Retry with the same requestId and the same request payload to obtain the same batch idempotently. Reusing the requestId with changed key fields returns REPEAT_REQ_INCONSISTENT.

## Request Parameters

#### requestId (string, REQUIRED)

Idempotency key. Assign a unique value (e.g., UUID) per batch to safely retry without creating duplicates. Max length: 64 characters.

#### batchName (string)

Human-readable name for the batch (e.g., Payroll 2026-08). Not visible to beneficiaries. Max length: 128 characters.

#### businessSceneCode (string)

Business scene code that applies to every item in the batch. Individual items may override this value when added. See [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UqJiZ).

#### fvtPreference (string)

The full value transfer preference for the batch. If specified, it applies to all items in the batch. Set NEED_FVT to request guaranteed full-amount delivery (payer covers intermediary fees). See [FvtPreference](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

#### oboPreference (string)

The On-Behalf-Of routing preference for the batch. If specified, it applies to all items in the batch. See [OboPreference](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

#### paymentType (string)

The payment channel type for the batch. If specified, it applies to all items in the batch. Required if paymentNetwork is specified. See [PaymentType](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

#### paymentNetwork (string)

The payment clearing network for the batch. If specified, it applies to all items in the batch. Required ifpaymentType is specified. See [PaymentNetwork](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UFKUW).

#### transferMemo (string)

Internal memo attached to the batch for your own records. Not visible to beneficiaries. Max length: 256 characters.

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### id (string)

Unique identifier of the created batch payout. Use this value for every subsequent addItems/calculate/submit/query call on the batch.

#### status (string)

Current batch status. Newly created batches always return INIT. See [BatchPayoutStatus](https://docs.worldfirst.com/wfdocs/api-sdk/batch_payouts_overview.md#VrCNk).

#### requestId (string)

Echoes the requestId submitted in the request.

#### batchName (string)

Echoes the batchName submitted in the request, when provided.

#### businessSceneCode (string)

Echoes the businessSceneCode submitted in the request, when provided. See [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UqJiZ).

#### fvtPreference (string)

Echoes the fvtPreference submitted in the request, when provided. See [FvtPreference](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

#### oboPreference (string)

Echoes the oboPreference submitted in the request, when provided. See [OboPreference](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

#### paymentNetwork (string)

Echoes the paymentNetwork submitted in the request, when provided. See [PaymentNetwork](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UFKUW).

#### paymentType (string)

Echoes the paymentType submitted in the request, when provided. See [PaymentType](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

#### transferMemo (string)

Echoes the transferMemo submitted in the request, when provided.

#### totalItemCount (integer)

Total number of items currently in the batch. Zero at creation time; increases as items are added.

#### validItemCount (integer)

Number of items that passed validation. Zero at creation time; updated as items are added and after calculate.

#### createdAt (datetime)

Batch creation time. ISO 8601 extended format.

#### updatedAt (datetime)

Last update time. ISO 8601 extended format.

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | Success | N/A |
| PARAM_ILLEGAL | F | Illegal parameters exist. | Verify required fields, string length limits, and enum values. |
| AUTHORIZATION_NOT_EXIST | F | The authorization does not exist. | Confirm the OAuth authorization is still active for the target account. |
| USER_NOT_EXIST | F | The user does not exist. | Verify the user identified by the request, then retry with correct user information. |
| USER_STATUS_ABNORMAL | F | The status of the user is abnormal. | Contact WorldFirst technical support to check the user status. |
| USER_NO_PERMISSION | F | User does not have permission. | Confirm the user has permission to manage batch payouts, or retry with a user that has the required permissions. |
| CONTRACT_NOT_EXIST | F | The contract does not exist. | Ensure the required payout contract is in place, then retry. |
| REPEAT_REQ_INCONSISTENT | F | Repeated requests are inconsistent. | Reuse the original payload for the same `requestId`, or assign a new `requestId`. |
| PROCESS_FAIL | F | A general business failure occurred. Do not retry. | Do not retry. Contact WorldFirst technical support with your `requestId`. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `BatchPayoutService`；请求 `CreateBatchPayoutRequest`，响应 `CreateBatchPayoutResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
BatchPayoutService service = new BatchPayoutService(apiClient);
CreateBatchPayoutRequest request = new CreateBatchPayoutRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
CreateBatchPayoutResponse response = service.createBatchPayout(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "requestId": "req_batch_20260808_0001",
  "batchName": "Payroll 2026-08",
  "transferMemo": "August payroll run"
}
```

## 响应 JSON 示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "bp_20260808****",
  "status": "INIT",
  "requestId": "req_batch_20260808_0001",
  "batchName": "Payroll 2026-08",
  "transferMemo": "August payroll run",
  "totalItemCount": 0,
  "validItemCount": 0,
  "createdAt": "2026-08-08T10:00:00+08:00",
  "updatedAt": "2026-08-08T10:00:00+08:00"
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
