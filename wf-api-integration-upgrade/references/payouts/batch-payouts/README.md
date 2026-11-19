# 批量代发（Batch Payouts）模块

官方规格：[概览](https://docs.worldfirst.com/wfdocs/api-sdk/batch_payouts_overview)；核对日期：2026-11-18。

## 接口列表

| 接口 | GUIDE 路径 | Endpoint |
|---|---|---|
| `create_a_batch_payout` | [create-a-batch-payout/GUIDE.md](create-a-batch-payout/GUIDE.md) | `POST /api/open/v1/batchPayouts/create` |
| `add_items_to_a_batch_payout` | [add-items-to-a-batch-payout/GUIDE.md](add-items-to-a-batch-payout/GUIDE.md) | `POST /api/open/v1/batchPayouts/addItems` |
| `remove_items_from_a_batch_payout` | [remove-items-from-a-batch-payout/GUIDE.md](remove-items-from-a-batch-payout/GUIDE.md) | `POST /api/open/v1/batchPayouts/removeItems` |
| `list_batch_payout_items` | [list-batch-payout-items/GUIDE.md](list-batch-payout-items/GUIDE.md) | `POST /api/open/v1/batchPayouts/listItems` |
| `calculate_a_batch_payout` | [calculate-a-batch-payout/GUIDE.md](calculate-a-batch-payout/GUIDE.md) | `POST /api/open/v1/batchPayouts/calculate` |
| `submit_a_batch_payout` | [submit-a-batch-payout/GUIDE.md](submit-a-batch-payout/GUIDE.md) | `POST /api/open/v1/batchPayouts/submit` |
| `query_a_batch_payout` | [query-a-batch-payout/GUIDE.md](query-a-batch-payout/GUIDE.md) | `POST /api/open/v1/batchPayouts/query` |
| `delete_a_batch_payout` | [delete-a-batch-payout/GUIDE.md](delete-a-batch-payout/GUIDE.md) | `POST /api/open/v1/batchPayouts/delete` |
| `cancel_a_batch_payout` | [cancel-a-batch-payout/GUIDE.md](cancel-a-batch-payout/GUIDE.md) | `POST /api/open/v1/batchPayouts/cancel` |

## 集成流程与边界

- 使用原生 `batchPayouts` 9 个接口，不能用循环单笔代发伪装批量 API。
- 创建空批次（INIT）→ 每次添加 1–10 项（每批最多 100 项）→ 计算 → 全部项通过校验且至少 1 项 → 提交 → 查询批次和逐项结果。添加返回成功也可能包含 INVALID 项。
- `COMPLETED` 仅表示所有项终态，可能部分成功；逐项区分 SUCCESS / FAILED / RETURNED / CANCELLED / REJECTED。不要套用单笔代发的 FAIL / RETURN 枚举。
- 计算金额、费用及汇率仅供预览，不锁定最终执行价格。金额复用 `Amount`（Long 最小货币单位），`clientRate` 使用 BigDecimal。
- 创建批次及添加项分别保留自己的 requestId；重试保持原 ID 与原参数。提交结果未知先查询原批次，禁止换新 ID 重复付款。Service 不自动重试。
- INIT 可增删项、计算及删除草稿；仅 INIT / PENDING_APPROVAL 可取消。PROCESSING 后不能取消；审批在 WF 门户完成，本模块没有审批 API。
- `fvtPreference/oboPreference/paymentType/paymentNetwork` 在批次上设置；paymentType/paymentNetwork 成对提供。项级不能传 transferQuote；businessSceneCode 可由单项覆盖。
- 项结构及各场景必填条件见 [item-scenarios.md](item-scenarios.md)。本模块使用 BatchPayout 前缀领域类，避免覆盖旧单笔代发中字段不同的模型；仅复用 common 的 Amount/Address/UserName/Result。
- List 返回的 itemId 可作为单笔 Query a Payout 的 id。费用子项字段为 feeItemAmount，不是 feeAmount。

## Java 参考实现

入口 [java/service/BatchPayoutService.java](java/service/BatchPayoutService.java)，提供 `WfClientConfig` 和 `WfApiClient` 两种构造器。所有接口通过公共 WfApiClient 加签/验签；不在 Service 内校验参数或自动重试。

| 方法 | 请求 / 响应类型 |
|---|---|
| `createBatchPayout` | `CreateBatchPayoutRequest` / `CreateBatchPayoutResponse` |
| `addItemsToBatchPayout` | `AddItemsToBatchPayoutRequest` / `AddItemsToBatchPayoutResponse` |
| `removeItemsFromBatchPayout` | `RemoveItemsFromBatchPayoutRequest` / `RemoveItemsFromBatchPayoutResponse` |
| `listBatchPayoutItems` | `ListBatchPayoutItemsRequest` / `ListBatchPayoutItemsResponse` |
| `calculateBatchPayout` | `CalculateBatchPayoutRequest` / `CalculateBatchPayoutResponse` |
| `submitBatchPayout` | `SubmitBatchPayoutRequest` / `SubmitBatchPayoutResponse` |
| `queryBatchPayout` | `QueryBatchPayoutRequest` / `QueryBatchPayoutResponse` |
| `deleteBatchPayout` | `DeleteBatchPayoutRequest` / `DeleteBatchPayoutResponse` |
| `cancelBatchPayout` | `CancelBatchPayoutRequest` / `CancelBatchPayoutResponse` |

每个方法均有 `(request)` 与 `(request, WfRequestContext context)` 两种重载。复用 common 的 WfRequestContext、WfApiClient、配置、Result、Amount、Address、UserName；上下文只作用于单次调用。RSA 仍受官网支持，本次不改为 API Key。GUIDE 保留完整字段与 JSON 示例，不需要在生成时临时补查接口规格。

- 模型使用 Java 8 POJO、显式 getter/setter，日期和状态保留 String，decimal 用 BigDecimal，扩展 metadata 用 Map。
- 模型的 toString 不输出金融/个人资料；适配 Lombok 后也必须保留脱敏。
- 只追加新错误码；同一 resultCode 在不同接口的重试规则可能不同，以当前 GUIDE 为准，不只依赖全局 retryable 标志。

## 枚举参考


### BatchPayoutStatus

|  |  |
| --- | --- |
| **Value** | **Description** |
| `INIT` | Draft. Items can be added, removed, or re-priced; the batch itself can be deleted. |
| `PENDING_APPROVAL` | Submitted and awaiting an approver in the WF business/enterprise portal. |
| `PROCESSING` | Accepted for processing. Rates are locked, funds are held per item, and payouts are being dispatched. |
| `COMPLETED` | All items reached a terminal state (partial success is possible). |
| `REJECTED` | Batch was rejected by an approver. |
| `CANCELLED` | Batch was withdrawn or automatically cancelled after approval timed out. |

### BatchPayoutItemStatus

|  |  |
| --- | --- |
| **Value** | **Description** |
| `INIT` | Item is queued in the draft batch, awaiting submission or approval. |
| `INVALID` | Item failed pre-submission validation. Correct or remove the item before submitting the batch; every remaining item must be valid. |
| `PENDING_APPROVAL` | Item was submitted and its batch is awaiting approval. |
| `PROCESSING` | A payout order was created. Pass the item's `itemId` as `id` to Query a Payout for its latest status. |
| `CANCELLED` | Item was cancelled along with its batch when the batch was withdrawn, or removed while the batch was `INIT`. |
| `REJECTED` | Item was rejected along with its batch. |
| `SUCCESS` | Payout succeeded. |
| `FAILED` | Execution failed after submission; see `failureCode` / `failureMessage`. |
| `RETURNED` | Payout was dispatched but subsequently returned. |
