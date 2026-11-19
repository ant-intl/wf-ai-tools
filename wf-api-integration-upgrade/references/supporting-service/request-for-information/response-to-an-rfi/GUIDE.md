# Respond to an RFI

> 规格来源：[官方 response_to_an_rfi](https://docs.worldfirst.com/wfdocs/api-sdk/response_to_an_rfi)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/rfis/respond`

Submits the merchant's answers to the questions in a Request for Information (RFI). Each element in answers corresponds to one question returned by [Retrieve an RFI](https://docs.worldfirst.com/wfdocs/api-sdk/retrieve_an_rfi.md). Provide files for ATTACHMENT questions, text for TEXT questions, and textList for TEXTLIST questions, according to each question's answerType. Upload any files first via [Upload a File](https://docs.worldfirst.com/wfdocs/api-sdk/upload_a_file.md) and use the returned id as fileId. This is a stateful action. It requires the RFI to be in ACTION_REQUIRED status and advances it to ANSWERED. Repeated calls when the status is not ACTION_REQUIRED return RFI_INVALID_STATUS without side effects, so the operation is idempotent.

## Request Parameters

#### id (string, REQUIRED)

Unique identifier of the RFI to respond to.

#### answers (array, REQUIRED)

List of answers; each element corresponds to one question in the RFI's questions array. Provide 1–50 elements.

##### questionId (string, REQUIRED)

Identifier of the question being answered, matching questions\[\].id.

##### files (array)

List of uploaded file objects. Required when the corresponding question's answerType is ATTACHMENT. Provide up to 10 files, each up to 7 MB. Upload files first via [Upload a File](https://docs.worldfirst.com/wfdocs/api-sdk/upload_a_file.md) to obtain each fileId.

###### fileId (string, REQUIRED)

Unique file identifier. Use the id returned by [Upload a File](https://docs.worldfirst.com/wfdocs/api-sdk/upload_a_file.md).

###### fileName (string, REQUIRED)

Original file name including extension. Use the fileName returned by [Upload a File](https://docs.worldfirst.com/wfdocs/api-sdk/upload_a_file.md).

##### text (string)

Free-text answer content. Required when the corresponding question's answerType is TEXT.

##### textList (array)

Text-list answer content. Required when the corresponding question's answerType is TEXTLIST. Provide up to 20 elements.

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### id (string)

Unique identifier of the RFI.

#### status (string)

Status of the RFI after the operation. Fixed to ANSWERED on a successful response. See [RfiStatus](https://docs.worldfirst.com/wfdocs/api-sdk/request_for_information_overview.md#MZfNh).

#### createdAt (datetime)

Time the RFI was created. ISO 8601 UTC format (ending with Z).

#### updatedAt (datetime)

Time the RFI was updated by this operation. ISO 8601 UTC format (ending with Z).

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | success | N/A |
| RFI_NOT_FOUND | F | The requested RFI does not exist. Please verify the id and retry. | Check the `id` matches an existing RFI. |
| RFI_INVALID_STATUS | F | The RFI is not in ACTION_REQUIRED status and cannot be responded to. | Retrieve the RFI and confirm its `status` is `ACTION_REQUIRED` before responding. |
| AUTHORIZATION_NOT_EXIST | F | You do not have permission to operate this RFI. Please verify your account-id. | Check the `account-id` header is linked to the calling client. |
| PARAM_ILLEGAL | F | The request parameters are invalid. | Check each answer provides the field required by its question's `answerType` (`files` / `text` / `textList`). |
| PROCESS_FAIL | F | A general business failure occurred. | Retry later; if it persists, contact WorldFirst support. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `RfiService`；请求 `RespondToRfiRequest`，响应 `RespondToRfiResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
RfiService service = new RfiService(apiClient);
RespondToRfiRequest request = new RespondToRfiRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
RespondToRfiResponse response = service.respondToRfi(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "id": "20260818191210001360300000****1",
  "answers": [
    {
      "questionId": "20260818191210001370300000****1",
      "text": "Example Trading Ltd."
    },
    {
      "questionId": "20260818191210001370300000****2",
      "files": [
        {
          "fileId": "BIZ_ENTERPRISE_FILE_GENERAL_DOCUMENT_20260731191530000024978000****4",
          "fileName": "certificate_of_incorporation.jpg"
        }
      ]
    }
  ]
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
  "status": "ANSWERED",
  "createdAt": "2026-08-18T12:37:05Z",
  "updatedAt": "2026-08-18T12:44:36Z"
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
