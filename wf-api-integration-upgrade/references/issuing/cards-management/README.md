# 卡片管理（Cards Management）模块

官方规格：[概览](https://docs.worldfirst.com/wfdocs/api-sdk/cards_management_overview)；核对日期：2026-11-18。

## 接口列表

| 接口 | GUIDE 路径 | Endpoint |
|---|---|---|
| `create_a_card` | [create-a-card/GUIDE.md](create-a-card/GUIDE.md) | `POST /api/open/v1/issuing/cards/create` |
| `query_a_card` | [query-a-card/GUIDE.md](query-a-card/GUIDE.md) | `POST /api/open/v1/issuing/cards/query` |
| `list_cards` | [list-cards/GUIDE.md](list-cards/GUIDE.md) | `POST /api/open/v1/issuing/cards/list` |
| `query_card_sensitive_info` | [query-card-sensitive-info/GUIDE.md](query-card-sensitive-info/GUIDE.md) | `POST /api/open/v1/issuing/cards/querySensitiveInfo` |
| `update_a_card` | [update-a-card/GUIDE.md](update-a-card/GUIDE.md) | `POST /api/open/v1/issuing/cards/update` |

## 集成流程与边界

- 创建接口当前仅支持 `cardBrand=MASTERCARD`、`mediaType=VIRTUAL`；概览的 VISA/PHYSICAL 是资源枚举，不代表创建接口支持。cardholderId 可选，budgetId 只能创建时设置。
- email/mobilePhone/billingAddress 必填。创建默认异步；PENDING 不是已激活，应查询至 ACTIVE/FAILED。enableSyncActivation/threeDsFrictionless 仅属于创建请求。
- 更新必须带 requestId 与 id，只传要改的字段；transactionLimits/authorizationControl 是整体替换。仅可请求 ACTIVE/FROZEN/CANCELLED；取消不可逆。
- 限额按币种分组（最多 5 组，当前 USD），每组最多 4 周期；rangeAmount 为 Long 最小货币单位，至少 1。输入模型不包含只读 usedAmount/remainAmount。
- MCC 允许/禁止列表互斥；allowedAuthTimes 至少 1；创建的激活时间须在未来，取消时间晚于激活时间。
- 普通查询返回掩码卡号，敏感查询才返回完整 PAN/CVV/有效期。敏感响应及任何 JSON 序列化结果不得记录日志或写入异常；toString 固定脱敏，适配 Lombok 时必须保留该行为。不得将响应中的示例掩码当作真实返回契约。

## Java 参考实现

入口 [java/service/CardsManagementService.java](java/service/CardsManagementService.java)，提供 `WfClientConfig` 和 `WfApiClient` 两种构造器。所有接口通过公共 WfApiClient 加签/验签；不在 Service 内校验参数或自动重试。

| 方法 | 请求 / 响应类型 |
|---|---|
| `createCard` | `CreateCardRequest` / `CreateCardResponse` |
| `queryCard` | `QueryCardRequest` / `QueryCardResponse` |
| `listCards` | `ListCardsRequest` / `ListCardsResponse` |
| `queryCardSensitiveInfo` | `QueryCardSensitiveInfoRequest` / `QueryCardSensitiveInfoResponse` |
| `updateCard` | `UpdateCardRequest` / `UpdateCardResponse` |

每个方法均有 `(request)` 与 `(request, WfRequestContext context)` 两种重载。复用 common 的 WfRequestContext、WfApiClient、配置、Result、Amount、Address、UserName；上下文只作用于单次调用。RSA 仍受官网支持，本次不改为 API Key。GUIDE 保留完整字段与 JSON 示例，不需要在生成时临时补查接口规格。

- 模型使用 Java 8 POJO、显式 getter/setter，日期和状态保留 String，decimal 用 BigDecimal，扩展 metadata 用 Map。
- 模型的 toString 不输出金融/个人资料；适配 Lombok 后也必须保留脱敏。
- 只追加新错误码；同一 resultCode 在不同接口的重试规则可能不同，以当前 GUIDE 为准，不只依赖全局 retryable 标志。

## 枚举参考


### CardStatus

|  |  |
| --- | --- |
| **Value** | **Description** |
| `PENDING` | Card application submitted, awaiting review or production. |
| `ACTIVE` | Card is activated and ready for transactions. |
| `FROZEN` | Card is frozen; transactions are paused and can be resumed. |
| `FAILED` | Card creation failed. Terminal state. |
| `CANCELLED` | Card cancelled. Terminal state. |

### MediaType

|  |  |
| --- | --- |
| **Value** | **Description** |
| `VIRTUAL` | Virtual card. |
| `PHYSICAL` | Physical card. |

### CardBrand

|  |  |
| --- | --- |
| **Value** | **Description** |
| `VISA` | Visa network. |
| `MASTERCARD` | Mastercard network. |

### TransactionInterval

|  |  |
| --- | --- |
| **Value** | **Description** |
| `PER_TRANSACTION` | Per-transaction limit. |
| `DAILY` | Daily limit. |
| `MONTHLY` | Monthly limit. |
| `ALL_TIME` | Lifetime limit for the card. |
