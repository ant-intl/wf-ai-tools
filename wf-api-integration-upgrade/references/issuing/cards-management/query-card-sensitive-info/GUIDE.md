# Query Card Sensitive Info

> 规格来源：[官方 query_card_sensitive_info](https://docs.worldfirst.com/wfdocs/api-sdk/query_card_sensitive_info)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/issuing/cards/querySensitiveInfo`

Retrieve a card's sensitive information (full card number, CVV, expiry date). Handle the response in a PCI-compliant manner.

## Request Parameters

#### id (string, REQUIRED)

Unique card identifier. Max 64 characters.

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### id (string)

Unique card identifier. Max 64 characters.

#### accountId (string)

User account ID. Max 64 characters.

#### cardNumber (string)

Full card number. Max 19 characters.

#### cvv (string)

CVV security code. Max 4 characters.

#### expiryDate (string)

Card expiry date. Format MM/YY.

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | Success. | N/A |
| PARAM_ILLEGAL | F | Illegal parameters exist. | Verify request fields against this reference. |
| PARTNER_INFO_NOT_EXIST | F | Business partner info does not exist. | Contact WorldFirst support to verify partner onboarding. |
| PARTNER_STATUS_INVALID | F | Business partner status is invalid. | Contact WorldFirst support to confirm partner status. |
| AUTHORIZATION_NOT_EXIST | F | The authorization does not exist. | Verify the `account-id` / `access-token` authorization. |
| ASSET_NOT_EXIT | F | Asset does not exist. | Verify the `id` is correct. |
| RISK_REJECT | F | Request rejected due to risk concerns. | Contact WorldFirst support to review the risk decision. |
| ASSET_CARD_DECRYPT_ERROR | F | Card number decryption error. | Retry later; contact WorldFirst support if it persists. |
| USER_NOT_EXIST | F | The user does not exist. | Retry with correct user information. |
| USER_NO_PERMISSION | F | User does not have permission. | Retry with a user that has permission for this card. |
| CARD_NOT_FOUND | F | Card not found. | Verify the `id` is correct. |
| CARD_STATUS_INVALID | F | Card status is invalid. | Sensitive info is only available for eligible card statuses. |
| PROCESS_FAIL | F | A general business failure occurred. Do not retry. | Do not retry. Contact WorldFirst support. |
| UNKNOWN_EXCEPTION | U | An unknown exception occurred. | Server error. Retry later; contact WorldFirst support if it persists. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `CardsManagementService`；请求 `QueryCardSensitiveInfoRequest`，响应 `QueryCardSensitiveInfoResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
CardsManagementService service = new CardsManagementService(apiClient);
QueryCardSensitiveInfoRequest request = new QueryCardSensitiveInfoRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
QueryCardSensitiveInfoResponse response = service.queryCardSensitiveInfo(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "id": "2026062229027200120603300003203"
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
  "id": "2026062229027200120603300003203",
  "accountId": "2120120018****",
  "cardNumber": "5262********9419",
  "cvv": "***",
  "expiryDate": "06/31"
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
