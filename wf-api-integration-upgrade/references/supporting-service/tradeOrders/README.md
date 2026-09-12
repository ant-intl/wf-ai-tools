# 贸易订单（Trade Orders）模块

## 官方文档

- [WorldFirst 开发者文档 - Trade Orders Overview](https://docs.worldfirst.com/wfdocs/api-sdk/trade_orders_overview)
- [submit_trade_orders](https://docs.worldfirst.com/wfdocs/api-sdk/submit_trade_orders) | [query_trade_orders](https://docs.worldfirst.com/wfdocs/api-sdk/query_trade_orders) | [query_available_settlement_quota](https://docs.worldfirst.com/wfdocs/api-sdk/query_available_settlement_quota)

> **重要提示** ：此接口仅限已获批准的商户使用。请联系我们进行审批，我们将为您量身定制解决方案。

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 提交贸易订单 | `submit-trade-orders/GUIDE.md` | 提交一批 B2B/B2C 贸易订单，用于关联来款或累积 CNY 跨境结算额度 | `POST /api/open/v1/tradeOrders/submit` |
| 查询贸易订单 | `query-trade-orders/GUIDE.md` | 根据批次 ID 查询已提交贸易订单的处理状态和结果 | `POST /api/open/v1/tradeOrders/query` |
| 查询可用结算额度 | `query-quota/GUIDE.md` | 查询指定币种和累积方式下的剩余 CNY 结算额度 | `POST /api/open/v1/tradeOrders/queryQuota` |

## TradeOrderService 说明

`TradeOrderService` 是贸易订单模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `SubmitTradeOrdersResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `submitTradeOrders(SubmitTradeOrdersRequest)` | `SubmitTradeOrdersRequest` | `SubmitTradeOrdersResponse` | 提交一批贸易订单 |
| `queryTradeOrders(QueryTradeOrdersRequest)` | `QueryTradeOrdersRequest` | `QueryTradeOrdersResponse` | 查询批次处理状态 |
| `queryQuota(QueryQuotaRequest)` | `QueryQuotaRequest` | `QueryQuotaResponse` | 查询可用结算额度 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `TradeOrderService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `TradeOrderService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

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
TradeOrderService tradeOrderService = new TradeOrderService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 提交贸易订单
SubmitTradeOrdersRequest submitRequest = new SubmitTradeOrdersRequest();
submitRequest.setBatchRequestId("req_20260429001");
submitRequest.setSceneCode("PAY_INTO_CHINA");
submitRequest.setQuotaAccumulationMethod("USER_ID");
submitRequest.setQuotaAccumulationId("2208****");
submitRequest.setPlatform("AE");
// ... 设置 tradeOrders 列表
SubmitTradeOrdersResponse submitResponse = tradeOrderService.submitTradeOrders(submitRequest);

// 查询批次处理状态
QueryTradeOrdersRequest queryRequest = new QueryTradeOrdersRequest();
queryRequest.setId(submitResponse.getId());
QueryTradeOrdersResponse queryResponse = tradeOrderService.queryTradeOrders(queryRequest);

// 查询可用结算额度
QueryQuotaRequest quotaRequest = new QueryQuotaRequest();
quotaRequest.setCurrency("USD");
quotaRequest.setQuotaAccumulationMethod("BENEFICIARY_ID");
quotaRequest.setQuotaAccumulationId("RA_****");
quotaRequest.setTradeCategory("GOODS");
QueryQuotaResponse quotaResponse = tradeOrderService.queryQuota(quotaRequest);
```

### 4. 检查业务结果

```java
if ("S".equals(submitResponse.getResult().getResultStatus())) {
    System.out.println("批次 ID: " + submitResponse.getId());
    System.out.println("批次状态: " + submitResponse.getStatus());
    for (TradeOrderResult order : submitResponse.getTradeOrders()) {
        System.out.println("订单号: " + order.getReferenceOrderNo() + ", 状态: " + order.getOrderStatus());
    }
}
```

## 注意事项

### submit_trade_orders
- `batchRequestId` 为幂等键，每次请求必须使用唯一值（如 UUID），重试时使用相同值不会产生重复提交
- `tradeOrders` 列表最多 100 条，至少 1 条
- 每条订单的 `goods` 列表最多 200 条，至少 1 条
- `shipping` 在 `tradeCategory` 为 `GOODS` 时必填
- 同步阶段返回 `status = ACCEPTED`，最终结果需通过 query 接口查询

### query_trade_orders
- `id` 使用 submit 接口返回的批次 ID
- 当 `status` 为 `COMPLETED` 时，`tradeOrders` 中包含每条订单的最终处理结果
- `remainingAmount` 仅在 query 响应中返回，表示该订单剩余可申报额度

### query_available_settlement_quota
- `quotaAccumulationMethod` 在传入 `USER_ID` 时可不传，查询所有累积实体的额度
- `tradeCategory` 仅在查询受益人级别额度时必填
- `availableQuotas` 最多返回 100 条

## 枚举类型说明

### SceneCode（业务场景码）
- `PAY_INTO_CHINA`: B2C CNY 结算入境中国，用于累积入境 CNY 结算额度
- `B2B_FUNDS_ASSOCIATION`: B2B 资金关联，将 B2B 资金订单或贷记通知与贸易订单关联

### QuotaAccumulationMethod（额度累积方式）
- `USER_ID`: WorldFirst 账户 ID
- `GLOBAL_ACCOUNT`: WorldFirst 全球账户号（虚拟账户）
- `RECEIVING_ACCOUNT`: WorldFirst 收款账户号
- `BENEFICIARY_ID`: 受益人唯一标识（创建受益人时分配的外部 ID）
- `BATCH_NO`: 外部批次号
- `DEPOSIT_ID`: B2B 存款 / 收款 ID

### BatchStatus（批次状态）
- `ACCEPTED`: 批次已接收并存储，submit（同步）和 query 均返回
- `PROCESSING`: 批次中所有贸易订单正在处理
- `COMPLETED`: 批次中所有贸易订单已处理完成，query 和 webhook 返回

### TradeCategory（贸易类别）
- `GOODS`: 货物贸易
- `SERVICE`: 服务贸易

### OrderDirection（资金方向）
- `CREDIT`: 收款（入站）
- `DEBIT`: 出站退款

### OrderStatus（订单状态）
- `ACCEPTED`: 贸易订单已接受结算申报，submit 和 query 均返回
- `TRADE_AMOUNT_OVER_LIMIT`: 贸易金额超限，订单被静默过滤，仅 submit 返回
- `AVAILABLE`: 订单可用于结算，额度累积成功，仅 query 返回
- `REJECTED`: 订单被拒绝（金额超限或校验失败），仅 query 返回
- `PARTIAL_DECLARED`: 结算额度部分消耗，仅 query 返回
- `DECLARED`: 结算额度全部消耗，仅 query 返回

### LogisticsMode（物流模式）
- `DROPSHIPPING`: 代发货，卖家不备货，供应商直接发货给买家
- `REGULAR_MODE`: 常规物流模式

## 文件结构

```
tradeOrders/
├── README.md
├── java/
│   ├── service/
│   │   └── TradeOrderService.java                     # 贸易订单服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── Store.java                             # 商户店铺信息
│       │   ├── Merchant.java                          # 商户信息
│       │   ├── Seller.java                            # 卖家（收款方）信息
│       │   ├── Buyer.java                             # 买家（付款方）信息
│       │   ├── Goods.java                             # 商品信息
│       │   ├── WayBillInfo.java                       # 物流运单信息
│       │   ├── Shipping.java                          # 物流发货详情
│       │   ├── TradeOrderResult.java                  # 贸易订单处理结果
│       │   ├── AvailableQuotaEntry.java               # 可用额度条目
│       │   └── QuotaByCurrency.java                   # 按币种拆分的额度
│       ├── request/
│       │   ├── SubmitTradeOrdersRequest.java          # 提交贸易订单请求
│       │   ├── QueryTradeOrdersRequest.java           # 查询贸易订单请求
│       │   └── QueryQuotaRequest.java                 # 查询可用额度请求
│       └── response/
│           ├── SubmitTradeOrdersResponse.java         # 提交贸易订单响应
│           ├── QueryTradeOrdersResponse.java          # 查询贸易订单响应
│           └── QueryQuotaResponse.java                # 查询可用额度响应
├── submit-trade-orders/
│   └── GUIDE.md                                       # submit_trade_orders 接口接入指引
├── query-trade-orders/
│   └── GUIDE.md                                       # query_trade_orders 接口接入指引
└── query-quota/
    └── GUIDE.md                                       # query_available_settlement_quota 接口接入指引
```
