# Delete a Batch Payout

> 规格来源：[官方 delete_a_batch_payout](https://docs.worldfirst.com/wfdocs/api-sdk/delete_a_batch_payout)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/batchPayouts/delete`

Permanently deletes a batch payout in INIT status along with all its items. Use this endpoint to discard a draft batch that has not yet been submitted. To withdraw a batch that has already been submitted, call [Cancel a Batch Payout](https://docs.worldfirst.com/wfdocs/api-sdk/cancel_a_batch_payout.md) instead.

## Request Parameters

#### id (string, REQUIRED)

Unique identifier of the batch payout to delete. Note: the batch must be in INIT status.

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | Success | N/A |
| PARAM_ILLEGAL | F | Illegal parameters exist. | Verify `id` is present and no longer than 64 characters. |
| BATCH_NOT_EXIST | F | The batch payout does not exist. | Confirm `id` matches a batch returned by \[Create a Batch Payout\](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_batch_payout); a previously deleted batch cannot be deleted again. |
| BATCH_PAYOUT_STATUS_NOT_SUPPORTED | F | Only a batch payout in INIT status can be deleted. To withdraw a submitted batch payout, cancel it instead. | Check the batch `status` via \[Query a Batch Payout\](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_batch_payout); use \[Cancel a Batch Payout\](https://docs.worldfirst.com/wfdocs/api-sdk/cancel_a_batch_payout) once the batch has been submitted. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `BatchPayoutService`；请求 `DeleteBatchPayoutRequest`，响应 `DeleteBatchPayoutResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
BatchPayoutService service = new BatchPayoutService(apiClient);
DeleteBatchPayoutRequest request = new DeleteBatchPayoutRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
DeleteBatchPayoutResponse response = service.deleteBatchPayout(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "id": "20260808BATCH00001"
}
```

## 响应 JSON 示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultStatus": "S",
    "resultMessage": "success"
  }
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
