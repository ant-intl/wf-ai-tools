# 外汇结算（Settlement）模块

## 官方文档

- [WorldFirst 开发者文档 - Settlement Overview](https://docs.worldfirst.com/wfdocs/api-sdk/settlement_overview)
- [create_a_settlement](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_settlement) | [query_a_settlement](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_settlement) | [list_settlements](https://docs.worldfirst.com/wfdocs/api-sdk/list_settlements)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 创建结算 | `create-settlement/GUIDE.md` | 发起 FX 结算，SPOT 使用 quoteId，FORWARD/UNFUNDED_SPOT 使用 dealId | `POST /api/open/v1/fx/settlements/create` |
| 查询结算 | `query-settlement/GUIDE.md` | 通过结算 ID 检索完整结算详情，跟踪结算状态 | `POST /api/open/v1/fx/settlements/query` |
| 查询结算列表 | `list-settlements/GUIDE.md` | 分页查询结算记录，支持按交易 ID、币种、状态、创建时间范围过滤 | `POST /api/open/v1/fx/settlements/list` |

## SettlementService 说明

`SettlementService` 是外汇结算模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `CreateSettlementResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `createSettlement(CreateSettlementRequest)` | `CreateSettlementRequest` | `CreateSettlementResponse` | 发起 FX 结算 |
| `querySettlement(QuerySettlementRequest)` | `QuerySettlementRequest` | `QuerySettlementResponse` | 查询结算详情 |
| `listSettlements(ListSettlementsRequest)` | `ListSettlementsRequest` | `ListSettlementsResponse` | 分页查询结算记录列表（游标分页） |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `SettlementService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `SettlementService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

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
SettlementService settlementService = new SettlementService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 创建结算（SPOT 场景）
CreateSettlementRequest createRequest = new CreateSettlementRequest();
createRequest.setRequestId("SR20260402001");
createRequest.setQuoteId("QT202604021208001");

CreateSettlementResponse createResponse = settlementService.createSettlement(createRequest);

// 查询结算
QuerySettlementRequest queryRequest = new QuerySettlementRequest();
queryRequest.setId("STL20260402001");

QuerySettlementResponse queryResponse = settlementService.querySettlement(queryRequest);

// 查询结算列表
ListSettlementsRequest listRequest = new ListSettlementsRequest();
listRequest.setLimit(10);
listRequest.setStatus("PROCESSING");
listRequest.setDealId("DEAL20260402002");

ListSettlementsResponse listResponse = settlementService.listSettlements(listRequest);
```

### 4. 检查业务结果

```java
if ("S".equals(createResponse.getResult().getResultStatus())) {
    System.out.println("结算 ID: " + createResponse.getId());
    System.out.println("状态: " + createResponse.getStatus());
    System.out.println("卖出: " + createResponse.getSellAmount().getValue() + " " + createResponse.getSellAmount().getCurrency());
    System.out.println("买入: " + createResponse.getBuyAmount().getValue() + " " + createResponse.getBuyAmount().getCurrency());
}

if ("S".equals(queryResponse.getResult().getResultStatus())) {
    System.out.println("结算状态: " + queryResponse.getStatus());
    System.out.println("创建时间: " + queryResponse.getCreatedAt());
    if (queryResponse.getSettledAt() != null) {
        System.out.println("结算完成时间: " + queryResponse.getSettledAt());
    }
}

if ("S".equals(listResponse.getResult().getResultStatus())) {
    for (SettlementRecord settlement : listResponse.getItems()) {
        System.out.println("结算 ID: " + settlement.getId() + "，状态: " + settlement.getStatus());
        System.out.println("关联交易: " + settlement.getDealId() + "，期望结算日: " + settlement.getSettlementDate());
    }
    // nextCursor 为 null 表示已到最后一页
    System.out.println("下一页游标: " + listResponse.getNextCursor());
}
```

## 注意事项

### create_a_settlement
- `requestId` 必填，幂等 ID 用于防止重复提交，每次创建结算请求必须唯一
- SPOT 结算：提供 `quoteId`，**不得**提供 `dealId`、`sellAmount`、`buyAmount`
- FORWARD / UNFUNDED_SPOT 结算：提供 `dealId`，并指定 `sellAmount` 或 `buyAmount`（二选一），**不得**提供 `quoteId`
- FORWARD 和 UNFUNDED_SPOT 可通过 `settlementDate` 指定期望结算日期（YYYY-MM-DD，UTC）
- 涉及银行账户出金时需提供 `beneficiary`，其中 `beneficiaryId` 和 `accountType`（BANK_ACCOUNT）必填
- `reference` 和 `memo` 为可选字段，分别用于业务对账和交易备注

### query_a_settlement
- `id` 必填，必须是当前账户创建的有效结算
- 响应字段根据结算状态有条件返回：
  - `dealId`：仅 FORWARD 和 UNFUNDED_SPOT 结算返回
  - `beneficiary`：仅涉及银行账户出金时返回
  - `feeAmount`：嵌套在 `memo` 的 `validValues` 中，表示结算手续费
  - `settledAt`：仅结算成功后返回
  - `failedAt`/`failureCode`/`failureMessage`：仅状态为 FAILED 时返回

### list_settlements
- 所有请求参数均可选：`limit` 不传时默认 20，取值范围 1-100
- 采用游标分页：首次不传 `cursor`，后续传入上次响应的 `nextCursor`；`nextCursor` 为 `null` 表示已到最后一页
- `fromCreatedAt`/`toCreatedAt` 最大查询跨度 30 天，超出需拆分时间窗口分批拉取
- 可按 `dealId` 反查某笔远期/无资金即期交易关联的全部结算，用于交割核对
- 响应字段有条件返回：`dealId`/`settlementDate` 仅 FORWARD/UNFUNDED_SPOT；`beneficiary` 仅银行账户出金；`feeAmount` 仅 FORWARD/UNFUNDED_SPOT 且银行账户出金；`settledAt` 仅 SUCCESS；`failedAt`/`failureCode`/`failureMessage` 仅 FAILED
- 仅需查单笔完整详情时，优先用 `query_settlement`；批量对账与状态跟踪用本接口

## 枚举类型说明

### SettlementStatus（结算状态）
- `PROCESSING`: 结算正在处理中
- `SUCCESS`: 结算已成功完成
- `FAILED`: 结算已失败

### SettlementAccountType（收款人账户类型）
- `BANK_ACCOUNT`: 银行账户，目前仅支持该类型

### 关联的 DealType（交易类型）
- `SPOT`: 即期交易，自动结算（通过 quoteId 创建）
- `UNFUNDED_SPOT`: 无资金即期交易，需显式结算（通过 dealId 创建）
- `FORWARD`: 远期交易，需显式结算（通过 dealId 创建）

## 文件结构

```
settlement/
├── README.md
├── java/
│   ├── service/
│   │   └── SettlementService.java                     # 外汇结算服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── SettlementQuote.java                   # 结算关联的报价信息
│       │   ├── Beneficiary.java                       # 收款人信息
│       │   └── SettlementRecord.java                  # 结算记录（用于 list 响应的 items）
│       ├── request/
│       │   ├── CreateSettlementRequest.java           # 创建结算请求
│       │   ├── QuerySettlementRequest.java            # 查询结算请求（id）
│       │   └── ListSettlementsRequest.java            # 查询结算列表请求（limit, cursor, dealId, 币种, status, 时间范围）
│       └── response/
│           ├── CreateSettlementResponse.java          # 创建结算响应
│           ├── QuerySettlementResponse.java           # 查询结算响应
│           └── ListSettlementsResponse.java           # 查询结算列表响应
├── create-settlement/
│   └── GUIDE.md                                       # create_a_settlement 接口接入指引
├── query-settlement/
│   └── GUIDE.md                                       # query_a_settlement 接口接入指引
└── list-settlements/
    └── GUIDE.md                                       # list_settlements 接口接入指引
```

> **复用 domain 对象**：`Amount`、`Result` 等通用对象与其他模块共享，引用 `{basePackage}.wf.model.domain` 包下的已有定义。`SettlementQuote`、`Beneficiary`、`SettlementRecord` 为本模块独有的 domain 对象。
