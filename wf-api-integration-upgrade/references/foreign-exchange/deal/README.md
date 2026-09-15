# 外汇交易（Deal）模块

## 官方文档

- [WorldFirst 开发者文档 - Deal Overview](https://docs.worldfirst.com/wfdocs/api-sdk/deal_overview)
- [create_a_deal](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_deal) | [query_a_deal](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_deal) | [cancel_a_deal](https://docs.worldfirst.com/wfdocs/api-sdk/cancel_a_deal) | [list_deals](https://docs.worldfirst.com/wfdocs/api-sdk/list_deals) | [list_margin_charge_records](https://docs.worldfirst.com/wfdocs/api-sdk/list_margin_charge_records)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 创建交易 | `create-deal/GUIDE.md` | 使用有效报价执行 FX 交易，支持 SPOT/UNFUNDED_SPOT/FORWARD | `POST /api/open/v1/fx/deals/create` |
| 查询交易 | `query-deal/GUIDE.md` | 通过交易 ID 检索完整交易详情，跟踪结算状态 | `POST /api/open/v1/fx/deals/query` |
| 取消交易 | `cancel-deal/GUIDE.md` | 取消尚未结算的交易，仅 PROCESSING 状态可取消 | `POST /api/open/v1/fx/deals/cancel` |
| 查询交易列表 | `list-deals/GUIDE.md` | 分页查询交易记录，支持按卖出/买入币种、状态、创建时间范围过滤 | `POST /api/open/v1/fx/deals/list` |
| 查询保证金追缴记录 | `list-margin-charge-records/GUIDE.md` | 分页查询远期交易的保证金追缴记录，支持按交易 ID、场景、状态、创建时间范围过滤 | `POST /api/open/v1/fx/deals/listMarginChargeRecords` |

## DealService 说明

`DealService` 是外汇交易模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `CreateDealResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `createDeal(CreateDealRequest)` | `CreateDealRequest` | `CreateDealResponse` | 使用有效报价创建 FX 交易 |
| `queryDeal(QueryDealRequest)` | `QueryDealRequest` | `QueryDealResponse` | 查询交易详情，跟踪结算进度 |
| `cancelDeal(CancelDealRequest)` | `CancelDealRequest` | `CancelDealResponse` | 取消尚未结算的交易 |
| `listDeals(ListDealsRequest)` | `ListDealsRequest` | `ListDealsResponse` | 分页查询交易记录列表（游标分页） |
| `listMarginChargeRecords(ListMarginChargeRecordsRequest)` | `ListMarginChargeRecordsRequest` | `ListMarginChargeRecordsResponse` | 分页查询保证金追缴记录（游标分页） |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `DealService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `DealService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

## 对接流程

### 1. 配置

```java
WfClientConfig config = WfClientConfig.builder()
    .clientId("YOUR_CLIENT_ID")
    .privateKeyFromPath("/path/to/private_key.pem")
    .publicKeyFromPath("/path/to/wf_public_key.pem")
    .baseUrl("https://YOUR_BASE_URL")
    .build();
```

### 2. 创建 Service

```java
DealService dealService = new DealService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 创建交易
CreateDealRequest createDealRequest = new CreateDealRequest();
createDealRequest.setRequestId("REQ20260402001");
createDealRequest.setQuoteId("QT202604021208001");

CreateDealResponse createDealResponse = dealService.createDeal(createDealRequest);

// 查询交易
QueryDealRequest queryDealRequest = new QueryDealRequest();
queryDealRequest.setId("DEAL20260402001");

QueryDealResponse queryDealResponse = dealService.queryDeal(queryDealRequest);

// 取消交易
CancelDealRequest cancelDealRequest = new CancelDealRequest();
cancelDealRequest.setId("DEAL20260402001");

CancelDealResponse cancelDealResponse = dealService.cancelDeal(cancelDealRequest);

// 查询交易列表
ListDealsRequest listDealsRequest = new ListDealsRequest();
listDealsRequest.setLimit(20);
listDealsRequest.setSellCurrency("USD");
listDealsRequest.setBuyCurrency("HKD");
listDealsRequest.setStatus("SUCCESS");

ListDealsResponse listDealsResponse = dealService.listDeals(listDealsRequest);

// 查询保证金追缴记录列表
ListMarginChargeRecordsRequest listMarginRequest = new ListMarginChargeRecordsRequest();
listMarginRequest.setLimit(10);
listMarginRequest.setDealId("DEAL20260402001");
listMarginRequest.setScene("INITIAL_MARGIN");

ListMarginChargeRecordsResponse listMarginResponse = dealService.listMarginChargeRecords(listMarginRequest);
```

### 4. 检查业务结果

```java
if ("S".equals(createDealResponse.getResult().getResultStatus())) {
    System.out.println("交易 ID: " + createDealResponse.getId());
    System.out.println("交易类型: " + createDealResponse.getDealType());
    System.out.println("状态: " + createDealResponse.getStatus());
    System.out.println("卖出: " + createDealResponse.getSellAmount().getValue() + " " + createDealResponse.getSellAmount().getCurrency());
    System.out.println("买入: " + createDealResponse.getBuyAmount().getValue() + " " + createDealResponse.getBuyAmount().getCurrency());
}

if ("S".equals(queryDealResponse.getResult().getResultStatus())) {
    System.out.println("交易状态: " + queryDealResponse.getStatus());
    System.out.println("创建时间: " + queryDealResponse.getCreatedAt());
    if (queryDealResponse.getSettledAt() != null) {
        System.out.println("结算时间: " + queryDealResponse.getSettledAt());
    }
}

if ("S".equals(cancelDealResponse.getResult().getResultStatus())) {
    System.out.println("取消状态: " + cancelDealResponse.getStatus());
    System.out.println("取消时间: " + cancelDealResponse.getCancelledAt());
    if (cancelDealResponse.getCancelFeeAmount() != null) {
        System.out.println("取消费用: " + cancelDealResponse.getCancelFeeAmount().getValue() + " " + cancelDealResponse.getCancelFeeAmount().getCurrency());
    }
}

if ("S".equals(listDealsResponse.getResult().getResultStatus())) {
    for (DealRecord deal : listDealsResponse.getItems()) {
        System.out.println("交易 ID: " + deal.getId() + "，类型: " + deal.getDealType() + "，状态: " + deal.getStatus());
        System.out.println("卖出: " + deal.getSellAmount().getValue() + " " + deal.getSellAmount().getCurrency());
        System.out.println("买入: " + deal.getBuyAmount().getValue() + " " + deal.getBuyAmount().getCurrency());
    }
    // nextCursor 为 null 表示已到最后一页
    System.out.println("下一页游标: " + listDealsResponse.getNextCursor());
}

if ("S".equals(listMarginResponse.getResult().getResultStatus())) {
    for (MarginChargeRecord record : listMarginResponse.getItems()) {
        System.out.println("保证金记录: " + record.getId() + "，场景: " + record.getScene()
            + "，动作: " + record.getAction() + "，状态: " + record.getStatus());
        System.out.println("金额: " + record.getAmount().getValue() + " " + record.getAmount().getCurrency());
    }
    // nextCursor 为 null 表示已到最后一页
    System.out.println("下一页游标: " + listMarginResponse.getNextCursor());
}
```

## 注意事项

### create_a_deal
- `requestId` 必填，幂等 ID 用于防止重复提交，每次创建请求必须唯一
- `quoteId` 必填，从 Quote API 获取，报价必须在创建交易时有效且未过期
- 交易类型由报价决定：SPOT 自动结算，UNFUNDED_SPOT 和 FORWARD 需通过 Settlement API 显式结算
- FORWARD 交易需要保证金管理，创建时会冻结初始保证金
- 远期交易需要有效的 LEI（Legal Entity Identifier）注册

### query_a_deal
- `id` 必填，必须是当前账户创建的有效交易
- 响应字段根据交易类型和状态有条件返回：
  - `creditSupport`：仅 FORWARD 交易返回
  - `settlementPeriod`：仅 UNFUNDED_SPOT 和 FORWARD 交易返回
  - `unSettleBuyAmount`/`unSettleSellAmount`：仅 UNFUNDED_SPOT/FORWARD 且状态为 PROCESSING/CANCELED 时返回
  - `cancelFeeAmount`：仅状态为 CANCELED 时返回
  - `settledAt`：仅结算后返回
  - `failedAt`/`failureCode`/`failureMessage`：仅状态为 FAILED 时返回

### cancel_a_deal
- `id` 必填，交易必须处于 PROCESSING 状态且属于当前账户
- 取消成功后状态变为 CANCELED，`cancelledAt` 填充取消时间戳
- 根据交易类型可能收取取消费用（`cancelFeeAmount`）
- UNFUNDED_SPOT 和 FORWARD 取消后会填充 `unSettleBuyAmount` 和 `unSettleSellAmount`
- 已结算（SUCCESS）或已失败（FAILED）的交易不可取消

### list_deals
- 所有请求参数均可选：`limit` 不传时默认 20，取值范围 1-100
- 采用游标分页：首次不传 `cursor`，后续传入上次响应的 `nextCursor`；`nextCursor` 为 `null` 表示已到最后一页
- `fromCreatedAt`/`toCreatedAt` 最大查询跨度 31 天，超出需拆分时间窗口分批拉取
- `items` 中每笔交易的结构与 `query_deal` 响应一致，可复用 `DealRecord` 理解
- 响应字段按交易类型和状态有条件返回：
  - `creditSupport`：仅 FORWARD 交易返回
  - `settlementPeriod`：仅 UNFUNDED_SPOT 和 FORWARD 交易返回
  - `unSettleBuyAmount`/`unSettleSellAmount`：仅 UNFUNDED_SPOT/FORWARD 且状态为 PROCESSING/CANCELLED 时返回
  - `cancelFeeAmount`：仅状态为 CANCELLED 时返回
  - `settledAt`：仅结算后返回
  - `failedAt`/`failureCode`/`failureMessage`：仅状态为 FAILED 时返回
- 仅需查单笔完整详情时，优先用 `query_deal`；批量对账与状态跟踪用本接口

### list_margin_charge_records
- 所有请求参数均可选：`limit` 不传时默认 20，取值范围 1-100
- 采用游标分页：首次不传 `cursor`，后续传入上次响应的 `nextCursor`；`nextCursor` 为 `null` 表示已到最后一页
- `fromCreatedAt`/`toCreatedAt` 最大查询跨度 31 天，超出需拆分时间窗口分批拉取
- 仅远期（FORWARD）交易存在保证金追缴记录，SPOT 交易无保证金
- 响应字段按状态有条件返回：
  - `cancelReason`/`cancelledAt`：仅 `status` 为 CANCELLED 时返回
  - `succeededAt`：仅 `status` 为 SUCCESS 时返回
  - `closedOutAt`：仅 `status` 为 CLOSED_OUT 时返回
  - `closeOutAt`：触发强平时返回
  - `failedAt`：`status` 为 FAILED 时返回
  - `dueAt`：保证金缴纳截止时间，适用时返回（`PENDING`/`OVERDUE` 中间态用于催缴判断）

## 枚举类型说明

### DealType（交易类型）

| 取值 | 说明 |
|------|------|
| `SPOT` | 标准即期交易，自动结算 |
| `UNFUNDED_SPOT` | 无资金即期交易，需要通过 Settlement API 显式结算 |
| `FORWARD` | 远期交易，需要保证金与结算周期 |

### DealStatus（交易状态）

| 取值 | 说明 |
|------|------|
| `PROCESSING` | 交易正在处理中，等待结算 |
| `SUCCESS` | 交易已成功完成并结算 |
| `CANCELLED` | 交易已被用户或系统取消 |
| `FAILED` | 交易在处理过程中失败 |

### DealMode（结算模式）

| 取值 | 说明 |
|------|------|
| `FIXED` | 固定结算日期 |
| `FLEXIBLE` | 灵活结算日期 |
| `WINDOWED` | 窗口结算周期（需要 `startDate`） |

### MarginScene（保证金场景）

| 取值 | 说明 |
|------|------|
| `INITIAL_MARGIN` | 创建交易时要求的初始保证金 |
| `ADDITION_MARGIN` | 因市场波动而追加的保证金 |
| `RELEASE_MARGIN` | 结算或取消后释放的保证金 |

### MarginAction（保证金动作）

| 取值 | 说明 |
|------|------|
| `FREEZE` | 冻结（锁定）保证金资金 |
| `UNFREEZE` | 解冻（释放）保证金资金 |

### MarginChargeStatus（保证金追缴状态）

| 取值 | 说明 |
|------|------|
| `PENDING` | 保证金追缴待处理 |
| `OVERDUE` | 保证金追缴已逾期，当前处于宽限期内 |
| `CLOSED_OUT` | 因违约已强制平仓 |
| `SUCCESS` | 保证金追缴或释放成功完成 |
| `FAILED` | 保证金追缴或释放失败 |
| `CANCELLED` | 保证金追缴已取消 |


### MarginCancelReason（保证金取消原因）

| 取值 | 说明 |
|------|------|
| `DEALER_CANCELLED` | 由交易台主动取消 |
| `RELEASE` | 交易结算后保证金释放 |
| `MARGIN_EXEMPT` | 保证金豁免 |

## 文件结构

```
deal/
├── README.md
├── java/
│   ├── service/
│   │   └── DealService.java                           # 外汇交易服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── DealQuote.java                         # 交易关联的报价信息
│       │   ├── DealRecord.java                         # 交易记录（用于 list 响应的 items）
│       │   └── MarginChargeRecord.java                # 保证金追缴记录（用于 list 响应的 items）
│       ├── request/
│       │   ├── CreateDealRequest.java                 # 创建交易请求（requestId, quoteId）
│       │   ├── QueryDealRequest.java                  # 查询交易请求（id）
│       │   ├── CancelDealRequest.java                 # 取消交易请求（id）
│       │   ├── ListDealsRequest.java                   # 查询交易列表请求（limit, cursor, sellCurrency, buyCurrency, status, 时间范围）
│       │   └── ListMarginChargeRecordsRequest.java    # 查询保证金追缴记录列表请求（limit, cursor, dealId, scene, status, 时间范围）
│       └── response/
│           ├── CreateDealResponse.java                # 创建交易响应
│           ├── QueryDealResponse.java                 # 查询交易响应
│           ├── CancelDealResponse.java                # 取消交易响应
│           ├── ListDealsResponse.java                  # 查询交易列表响应
│           └── ListMarginChargeRecordsResponse.java   # 查询保证金追缴记录列表响应
├── create-deal/
│   └── GUIDE.md                                       # create_a_deal 接口接入指引
├── query-deal/
│   └── GUIDE.md                                       # query_a_deal 接口接入指引
├── cancel-deal/
│   └── GUIDE.md                                       # cancel_a_deal 接口接入指引
├── list-deals/
│   └── GUIDE.md                                       # list_deals 接口接入指引
└── list-margin-charge-records/
    └── GUIDE.md                                       # list_margin_charge_records 接口接入指引
```

> **复用 domain 对象**：`Amount`、`CreditSupport`、`SettlementPeriod`、`Result` 等通用对象与 Quote 模块共享，引用 `{basePackage}.wf.model.domain` 包下的已有定义。
