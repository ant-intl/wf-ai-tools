# 补充资料 RFI（Request for Information）模块

官方规格：[概览](https://docs.worldfirst.com/wfdocs/api-sdk/request_for_information_overview)；核对日期：2026-11-18。

## 接口列表

| 接口 | GUIDE 路径 | Endpoint |
|---|---|---|
| `retrieve_an_rfi` | [retrieve-an-rfi/GUIDE.md](retrieve-an-rfi/GUIDE.md) | `POST /api/open/v1/rfis/query` |
| `response_to_an_rfi` | [response-to-an-rfi/GUIDE.md](response-to-an-rfi/GUIDE.md) | `POST /api/open/v1/rfis/respond` |
| `list_rfis` | [list-rfis/GUIDE.md](list-rfis/GUIDE.md) | `POST /api/open/v1/rfis/list` |

## 集成流程与边界

- 列表仅返回摘要；必须 retrieve 获得 questions，按 questions[].id 填 answers[].questionId，不用 auditKey 替代 ID。auditKey 为开放字符串。
- 只在 ACTION_REQUIRED 状态答复；成功进入 ANSWERED 等待审核，不等于 CLOSED。审核后可重新进入 ACTION_REQUIRED；仅有 expiresAt 才会过期。
- answers 为 1–50 项；按 answerType 提供 files/text/textList；附件最多 10 个且每个最多 7 MB，文本列表最多 20 项。
- 附件先用现有 File 模块上传，把返回 id/fileName 映射为 fileId/fileName。只回答服务端请求的问题，并完成 required=true 的项。
- query 请求体只有 id；respond 无 requestId。重复答复非 ACTION_REQUIRED 的 RFI 返回 RFI_INVALID_STATUS，无副作用。超时后先 retrieve 确认状态，再决定是否补交，不能伪造成功。
- 代理账户使用请求级 WfRequestContext.forAccount(...) 设置 account-id 头；list 请求体 accountId 只是过滤条件，不能替代授权头。OAuth 场景可用 forAccessToken(...)；两种头互斥，禁止在共享客户端中切换全局账户状态。
- 错误处理按具体 GUIDE；RFI 的 PROCESS_FAIL 文档允许稍后重试，不将其他模块同码的策略机械套用。

## Java 参考实现

入口 [java/service/RfiService.java](java/service/RfiService.java)，提供 `WfClientConfig` 和 `WfApiClient` 两种构造器。所有接口通过公共 WfApiClient 加签/验签；不在 Service 内校验参数或自动重试。

| 方法 | 请求 / 响应类型 |
|---|---|
| `retrieveRfi` | `RetrieveRfiRequest` / `RetrieveRfiResponse` |
| `respondToRfi` | `RespondToRfiRequest` / `RespondToRfiResponse` |
| `listRfis` | `ListRfisRequest` / `ListRfisResponse` |

每个方法均有 `(request)` 与 `(request, WfRequestContext context)` 两种重载。复用 common 的 WfRequestContext、WfApiClient、配置、Result、Amount、Address、UserName；上下文只作用于单次调用。RSA 仍受官网支持，本次不改为 API Key。GUIDE 保留完整字段与 JSON 示例，不需要在生成时临时补查接口规格。

- 模型使用 Java 8 POJO、显式 getter/setter，日期和状态保留 String，decimal 用 BigDecimal，扩展 metadata 用 Map。
- 模型的 toString 不输出金融/个人资料；适配 Lombok 后也必须保留脱敏。
- 只追加新错误码；同一 resultCode 在不同接口的重试规则可能不同，以当前 GUIDE 为准，不只依赖全局 retryable 标志。

## 枚举参考


### RfiStatus

|  |  |
| --- | --- |
| Value | Description |
| `ACTION_REQUIRED` | Awaiting the merchant's answer. Set when the RFI is created or reopened after a rejected answer. |
| `ANSWERED` | The merchant has answered; awaiting review by the requesting party. |
| `CLOSED` | Terminal state. The requesting party approved the answer or closed the RFI. |
| `EXPIRED` | Terminal state. Reached only when an RFI with an `expiresAt` deadline passes its deadline. |

### RfiType

|  |  |
| --- | --- |
| Value | Description |
| `KYC_RFI` | Information request raised during the KYC process. |
| `KYC_ONGOING_CDD_RFI` | Information request raised during ongoing customer due diligence (Ongoing CDD). |

### AnswerType

|  |  |
| --- | --- |
| Value | Description |
| `ATTACHMENT` | File-upload question. The answer must provide the `files` field. |
| `TEXT` | Free-text question. The answer must provide the `text` field. |
| `TEXTLIST` | Text-list question. The answer must provide the `textList` field. |
