# Retrieve an RFI

> 规格来源：[官方 retrieve_an_rfi](https://docs.worldfirst.com/wfdocs/api-sdk/retrieve_an_rfi)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/rfis/query`

Retrieves a single Request for Information (RFI) by its ID, including the full questions array with the detailed information the merchant must provide. This is the only endpoint that returns question details; [List RFIs](https://docs.worldfirst.com/wfdocs/api-sdk/list_rfis.md) and webhook notifications return summaries without questions. To retrieve an RFI for a connected account, a platform passes the target account in the account-id request header. WorldFirst validates that the account-id is linked to the caller; otherwise it returns AUTHORIZATION_NOT_EXIST. See [Authentication](https://docs.worldfirst.com/wfdocs/api-sdk/authentication.md).

## Request Parameters

#### id (string, REQUIRED)

Unique identifier of the RFI to retrieve. This is the only accepted field in the request body.

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### id (string)

Unique identifier of the RFI.

#### accountId (string)

ID of the account the RFI belongs to.

#### rfiType (string)

Type of the RFI, indicating the business scenario it originates from. See [RfiType](https://docs.worldfirst.com/wfdocs/api-sdk/request_for_information_overview.md#i0VUp).

#### status (string)

Current status of the RFI. See [RfiStatus](https://docs.worldfirst.com/wfdocs/api-sdk/request_for_information_overview.md#MZfNh).

#### instruction (string)

Human-readable, top-level instruction describing what the merchant needs to provide. Returned only when the RFI has a top-level instruction.

#### expiresAt (datetime)

Deadline for answering the RFI; the RFI is automatically marked EXPIRED once passed. ISO 8601 UTC format (ending with Z). Returned only when the RFI has a deadline.

#### questions (array)

List of questions (the requested information) the merchant must answer. Returned only when the RFI has question details.

##### id (string)

Unique identifier of the question. Echo it back in answers\[\].questionId when responding.

##### auditKey (string)

Machine anchor that locates the corresponding required form field (for example, basicInformation.companyName.value). A free-form string, not an enum; its value varies by business scenario.

##### description (string)

Returned only when the review provides a specific description.

##### answerType (string)

Type of answer expected, which determines whether to provide files, text, or textList when responding.

-   `ATTACHMENT`: File-upload question; provide files.
-   `TEXT`: Free-text question; provide text.
-   `TEXTLIST`: Text-list question; provide textList.

##### required (boolean)

Whether an answer to this question is mandatory.

#### createdAt (datetime)

Time the RFI was created. ISO 8601 UTC format (ending with Z).

#### updatedAt (datetime)

Time the RFI was last updated. ISO 8601 UTC format (ending with Z).

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | success | N/A |
| RFI_NOT_FOUND | F | The requested RFI does not exist. Please verify the id and retry. | Check the `id` matches an existing RFI. |
| AUTHORIZATION_NOT_EXIST | F | You do not have permission to operate this RFI. Please verify your account-id. | Check the `account-id` header is linked to the calling client. |
| PARAM_ILLEGAL | F | The request parameters are invalid. | Check `id` is present and well-formed. |
| PROCESS_FAIL | F | A general business failure occurred. | Retry later; if it persists, contact WorldFirst support. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `RfiService`；请求 `RetrieveRfiRequest`，响应 `RetrieveRfiResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
RfiService service = new RfiService(apiClient);
RetrieveRfiRequest request = new RetrieveRfiRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
RetrieveRfiResponse response = service.retrieveRfi(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "id": "20260818191210001360300000****1"
}
```

## 响应 JSON 示例

```json
{
  "result": {
    "resultStatus": "S",
    "resultCode": "SUCCESS",
    "resultMessage": "success"
  },
  "id": "20260818191210001360300000****1",
  "accountId": "21201203489****3",
  "rfiType": "KYC_ONGOING_CDD_RFI",
  "status": "CLOSED",
  "instruction": "Please re-confirm your company name and upload the certificate of incorporation.",
  "questions": [
    {
      "id": "20260818191210001370300000****1",
      "auditKey": "basicInformation.companyName.value",
      "description": "The document has expired",
      "answerType": "TEXT",
      "required": true
    },
    {
      "id": "20260818191210001370300000****2",
      "auditKey": "basicInformation.companyCertificateOfIncorporation",
      "description": "The document is mismatched",
      "answerType": "ATTACHMENT",
      "required": true
    }
  ],
  "createdAt": "2026-08-18T12:37:05Z",
  "updatedAt": "2026-08-19T02:53:31Z"
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
