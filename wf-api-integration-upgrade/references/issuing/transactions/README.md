# 卡交易记录（Transactions）模块

官方规格：[概览](https://docs.worldfirst.com/wfdocs/api-sdk/transactions_overview)；核对日期：2026-11-18。

## 接口列表

| 接口 | GUIDE 路径 | Endpoint |
|---|---|---|
| `list_card_transactions` | [list-card-transactions/GUIDE.md](list-card-transactions/GUIDE.md) | `POST /api/open/v1/issuing/transactions/list` |

## 集成流程与边界

- 本模块是卡交易（支付/退款）查询，不是外汇 Deal 或 Statement Report。
- limit/fromCreatedAt/toCreatedAt 必填，limit 为 1–100（推荐 10），时间范围不超过 100 天；时间字符串保留 ISO 8601 时区。
- statuses 最多 9 个，budgetAccountIds 最多 50 个；cardNo 可为末四位或完整 16 位，后者不得出现在日志。
- 分页保持查询条件不变，使用服务端 nextCursor；为空停止。tradeAmount/inAmount/outAmount 均使用最小货币单位，不使用浮点数；exchangeRate 官方类型为 string。
- CARD_REFUND 表示未关联原交易的退款；部分撤销/部分退款需保留原状态，不能只归类为成功或失败。updatedAt 仅在 Webhook 快照返回，本接口不要要求它非空。

## Java 参考实现

入口 [java/service/CardTransactionService.java](java/service/CardTransactionService.java)，提供 `WfClientConfig` 和 `WfApiClient` 两种构造器。所有接口通过公共 WfApiClient 加签/验签；不在 Service 内校验参数或自动重试。

| 方法 | 请求 / 响应类型 |
|---|---|
| `listCardTransactions` | `ListCardTransactionsRequest` / `ListCardTransactionsResponse` |

每个方法均有 `(request)` 与 `(request, WfRequestContext context)` 两种重载。复用 common 的 WfRequestContext、WfApiClient、配置、Result、Amount、Address、UserName；上下文只作用于单次调用。RSA 仍受官网支持，本次不改为 API Key。GUIDE 保留完整字段与 JSON 示例，不需要在生成时临时补查接口规格。

- 模型使用 Java 8 POJO、显式 getter/setter，日期和状态保留 String，decimal 用 BigDecimal，扩展 metadata 用 Map。
- 模型的 toString 不输出金融/个人资料；适配 Lombok 后也必须保留脱敏。
- 只追加新错误码；同一 resultCode 在不同接口的重试规则可能不同，以当前 GUIDE 为准，不只依赖全局 retryable 标志。

## 枚举参考


### TransactionStatus

|  |  |
| --- | --- |
| **Value** | **Description** |
| `WAITING_DEDUCT` | Awaiting deduction. |
| `PROCESSING` | Processing. |
| `WAITING_CLEARANCE` | Awaiting clearance. |
| `SUCCESS` | Transaction succeeded. |
| `FAILED` | Transaction failed. |
| `PARTIAL_CANCELLED` | Partial authorization cancelled. |
| `CANCELLED` | Authorization cancelled. |
| `PARTIAL_REFUNDED` | Partially refunded. |
| `REFUNDED` | Refunded. |

### BillType

|  |  |
| --- | --- |
| **Value** | **Description** |
| `CARD_PAYMENT` | Card payment. |
| `CARD_REFUND` | Card refund. |

### PayerBalanceType

|  |  |
| --- | --- |
| **Value** | **Description** |
| `NORMAL_BALANCE` | Standard balance. |
| `SAME_NAME_TOP_UP_BALANCE` | Same-name top-up balance. |
| `BUDGET_BALANCE` | Budget balance. |
