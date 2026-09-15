# Submit Trade Orders 接口接入指引

## 接口说明

提交一批 B2B 贸易订单关联来款，或直接上传 B2C 贸易订单信息以累积 CNY 跨境结算额度。每笔订单独立校验。

> **重要提示** ：此接口仅限已获批准的商户使用。请联系我们进行审批，我们将为您量身定制解决方案。
## 官方文档

- [submit_trade_orders 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/submit_trade_orders)

## 请求地址

`POST /api/open/v1/tradeOrders/submit`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-29T10:05:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type  | Required | Description |
|-------|-------|----------|-------------|
| `batchRequestId` | string | Yes | 批次幂等键，每次请求使用唯一值（如 UUID），重试时使用相同值不产生重复提交 |
| `sceneCode` | string | Yes | 业务场景码（SceneCode 枚举），如 `PAY_INTO_CHINA` |
| `subSceneCode` | string | No | 子场景码，用于自定义路由 |
| `quotaAccumulationMethod` | string | Yes | 额度累积方式（QuotaAccumulationMethod 枚举） |
| `quotaAccumulationId` | string | Yes | 与 quotaAccumulationMethod 配对的累积标识 |
| `platform` | string | Yes | 贸易来源平台（如 `AE`） |
| `tradeOrders` | array | Yes | 贸易订单详情列表，最多 100 条，至少 1 条 |

### TradeOrder Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `referenceOrderNo` | string | Yes | 商户分配的订单号，用于在 query 响应中关联结果 |
| `orderedAt` | datetime | Yes | 订单下单时间（ISO 8601 扩展格式） |
| `paidAt` | datetime | Yes | 支付完成时间（ISO 8601 扩展格式） |
| `orderDirection` | string | Yes | 资金方向：`CREDIT`（收款）或 `DEBIT`（退款） |
| `tradeCategory` | string | Yes | 贸易类别：`GOODS`（货物贸易）或 `SERVICE`（服务贸易） |
| `transAmount` | Amount | Yes | 实际交易收款金额 |
| `tradeAmount` | Amount | Yes | 申报结算金额，可能因手续费或折扣与 transAmount 不同 |
| `merchant` | Merchant | Yes | 商户信息 |
| `seller` | Seller | Yes | 卖家（收款方）信息 |
| `buyer` | Buyer | Yes | 买家（付款方）信息 |
| `goods` | array | Yes | 商品列表，最多 200 条，至少 1 条 |
| `shipping` | Shipping | Conditional | 物流发货详情，tradeCategory 为 `GOODS` 时必填 |
| `subPlatform` | string | No | 子平台来源标识 |
| `officialBizNo` | string | No | 官方业务参考号（如保险单号） |
| `logisticsMode` | string | Conditional | 物流模式。tradeCategory 为 `GOODS` 时必填。支持值：`DROPSHIPPING`（供应商直发）、`REGULAR_MODE`（标准物流） |
| `usedForExchange` | boolean | Yes | 是否用于换汇（CNY 结算额度累积），设为 `true` 表示用于额度累积 |

### Merchant Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `merchantId` | string | Yes | 第三方平台分配的商户标识（非 WorldFirst 账户 ID） |
| `merchantMCC` | string | Yes | 商户类别码（如 `5734`） |
| `merchantName` | string | Yes | 商户展示名称 |
| `store` | Store | Yes | 商户店铺信息 |

### Store Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `storeShopUrl` | string | Yes | 店铺 URL，最大长度 256 字符 |
| `referenceStoreId` | string | No | 外部店铺 ID，最大长度 64 字符 |
| `storeName` | string | No | 店铺名称，最大长度 128 字符 |
| `storeMCC` | string | No | 店铺商品类别码 |
| `storeDisplayName` | string | No | 店铺在平台上的展示名称，最大长度 128 字符 |
| `storeAddress` | Address | No | 店铺地址 |

### Seller Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `accountId` | string | Yes | WorldFirst 账户 ID |
| `referenceAccountId` | string | No | 第三方系统中的外部账户标识 |
| `customerType` | string | No | 注册公司类型：`PERSONAL` 或 `ENTERPRISE` |
| `customerCompanyName` | string | No | 注册法人实体名称（ENTERPRISE 时） |
| `customerName` | UserNameInfo | No | 注册个人法人姓名（PERSONAL 时） |
| `customerEmail` | string | No | 联系邮箱地址 |
| `nationality` | string | No | 卖家国籍，ISO 3166 两字母代码 |

### UserNameInfo Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `firstName` | string | Yes | 名字（Given name or first name） |
| `lastName` | string | Yes | 姓氏（Surname or last name） |
| `middleName` | string | No | 中间名（Middle name） |
| `nickName` | string | No | 昵称（Nickname） |
| `companyName` | string | No | 公司名称（Company name） |
| `fullName` | string | No | 全名（Full name） |

### Buyer Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `referenceBuyerId` | string | Yes | 第三方平台中的外部买家用户 ID |
| `buyerEmail` | string | No | 买家邮箱地址 |
| `buyerPhone` | string | No | 买家电话号码 |
| `buyerRegion` | string | No | 买家国家/地区，ISO 3166 两字母代码 |
| `buyerName` | UserNameInfo | No | 买家姓名 |

### Goods Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `goodsName` | string | Yes      | 商品名称（如 `"Electronics"`） |
| `referenceGoodsId` | string | No       | 外部商品 ID |
| `goodsCnName` | string | Yes      | 商品中文名称 |
| `goodsQuantity` | integer | Yes      | 订购数量，最小 1 |
| `storeUrl` | string | Yes      | 商品或店铺 URL |
| `goodsCategory` | string | No       | 商品类别（如 `ELECTRONICS`） |
| `departureCity` | string | No       | 出发城市（OTA 场景） |
| `arrivalCity` | string | No       | 到达城市（OTA 场景） |
| `pnrNo` | string | No       | 机票 PNR 号（OTA 场景） |

### Shipping Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `wayBillInfos` | array | No | 运单详情列表，最多 50 条 |

### WayBillInfo Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `logisticsCompanyName` | string | No | 物流公司名称，最大长度 128 字符 |
| `shippingOrderReferenceNo` | string | No | 出站物流追踪号，最大长度 64 字符 |
| `shippingAddress` | Address | No | 物流配送物理地址 |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | 币种代码（ISO-4217） |
| `value` | integer | 金额值，最小货币单位（如 USD 100.00 → value = 10000） |

### 请求示例

```json
{
  "batchRequestId": "req_20260429001",
  "sceneCode": "PAY_INTO_CHINA",
  "quotaAccumulationMethod": "USER_ID",
  "quotaAccumulationId": "2208****",
  "platform": "AE",
  "tradeOrders": [
    {
      "referenceOrderNo": "ORD_20260429001",
      "orderedAt": "2026-04-29T09:00:00+08:00",
      "paidAt": "2026-04-29T09:30:00+08:00",
      "transAmount": { "currency": "USD", "value": 10000 },
      "tradeAmount": { "currency": "USD", "value": 9500 },
      "tradeCategory": "GOODS",
      "orderDirection": "CREDIT",
      "merchant": {
        "merchantName": "Test Merchant",
        "merchantMCC": "5734",
        "store": { "storeShopUrl": "https://store.example.com" }
      },
      "seller": { "accountId": "ACC_****" },
      "buyer": { "referenceBuyerId": "BUYER_****" },
      "goods": [
        {
          "referenceGoodsId": "GOODS_****",
          "goodsName": "Electronics",
          "goodsCnName": "电子产品",
          "goodsQuantity": 10,
          "storeUrl": "https://store.example.com/goods/1"
        }
      ],
      "shipping": {
        "wayBillInfos": [
          {
            "logisticsCompanyName": "DHL",
            "shippingAddress": { "region": "US", "city": "New York" }
          }
        ]
      }
    }
  ]
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 系统生成的批次标识，用于后续查询处理状态 |
| `batchRequestId` | string | 请求中提供的幂等键回显 |
| `status` | string | 批次状态，同步阶段始终为 `ACCEPTED` |
| `tradeOrders` | array | 每笔订单的处理结果 |
| `createdAt` | datetime | 批次创建时间（ISO 8601 扩展格式） |

### TradeOrderResult Object

| Field | Type | Description |
|-------|------|-------------|
| `referenceOrderNo` | string | 商户分配的订单号回显 |
| `orderStatus` | string | 单笔订单处理状态（OrderStatus 枚举） |
| `statusMessage` | string | 订单状态描述 |
| `failureCode` | string | 订单级错误码，orderStatus 为 REJECTED 时返回 |
| `failureMessage` | string | 订单级错误描述，orderStatus 为 REJECTED 时返回 |
| `tradeAmount` | Amount | 申报结算金额回显 |
| `transAmount` | Amount | 实际交易金额回显 |
| `orderDirection` | string | 资金方向回显 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "unique_****",
  "batchRequestId": "req_20260429001",
  "status": "ACCEPTED",
  "createdAt": "2026-04-29T10:05:00+08:00",
  "tradeOrders": [
    {
      "referenceOrderNo": "ORD_20260429001",
      "orderStatus": "ACCEPTED",
      "statusMessage": "Accepted, pending process.",
      "tradeAmount": { "currency": "USD", "value": 9500 },
      "transAmount": { "currency": "USD", "value": 10000 },
      "orderDirection": "CREDIT"
    }
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 提交成功 |
| `PARAM_ILLEGAL` | F | 必填字段缺失或无效，不可重试 |
| `ACCOUNT_NOT_EXIST` | F | 账户不存在，不可重试 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败，不可重试 |
| `PROCESS_FAIL` | F | 业务处理失败，不可重试 |
| `UNKNOWN_EXCEPTION` | F | 未知异常，可使用相同 batchRequestId 重试 |

## 示例代码

参考 [references/supporting-service/tradeOrders/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
tradeOrders/java/
├── service/
│   └── TradeOrderService.java                       # 薄封装 Service，包含 submitTradeOrders 方法
└── model/
    ├── domain/
    │   ├── Store.java                               # 商户店铺信息
    │   ├── Merchant.java                            # 商户信息
    │   ├── Seller.java                              # 卖家（收款方）信息
    │   ├── Buyer.java                               # 买家（付款方）信息
    │   ├── Goods.java                               # 商品信息
    │   ├── WayBillInfo.java                         # 物流运单信息
    │   ├── Shipping.java                            # 物流发货详情
    │   └── TradeOrderResult.java                    # 贸易订单处理结果
    ├── request/
    │   └── SubmitTradeOrdersRequest.java            # 请求参数（含内部类 TradeOrder）
    └── response/
        └── SubmitTradeOrdersResponse.java           # 响应结果（result + 批次信息 + 订单结果列表）
```

## 集成使用方式

TradeOrderService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `SubmitTradeOrdersRequest` 设置批次信息和订单列表
2. 调用 `TradeOrderService.submitTradeOrders(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，获取批次 ID
4. 使用批次 ID 调用 query 接口查询最终处理结果

### 业务代码示例

```java
// 构造商品信息
Goods goods = new Goods();
goods.setReferenceGoodsId("GOODS_****");
goods.setGoodsName("Electronics");
goods.setGoodsCnName("电子产品");
goods.setGoodsQuantity(10);
goods.setStoreUrl("https://store.example.com/goods/1");

// 构造店铺信息
Store store = new Store();
store.setStoreShopUrl("https://store.example.com");

// 构造商户信息
Merchant merchant = new Merchant();
merchant.setMerchantName("Test Merchant");
merchant.setMerchantMCC("5734");
merchant.setStore(store);

// 构造卖家信息
Seller seller = new Seller();
seller.setAccountId("ACC_****");

// 构造买家信息
Buyer buyer = new Buyer();
buyer.setReferenceBuyerId("BUYER_****");

// 构造单笔订单
SubmitTradeOrdersRequest.TradeOrder order = new SubmitTradeOrdersRequest.TradeOrder();
order.setReferenceOrderNo("ORD_20260429001");
order.setOrderedAt("2026-04-29T09:00:00+08:00");
order.setPaidAt("2026-04-29T09:30:00+08:00");
order.setOrderDirection("CREDIT");
order.setTradeCategory("GOODS");

Amount transAmount = new Amount();
transAmount.setCurrency("USD");
transAmount.setValue(10000L);
order.setTransAmount(transAmount);

Amount tradeAmount = new Amount();
tradeAmount.setCurrency("USD");
tradeAmount.setValue(9500L);
order.setTradeAmount(tradeAmount);

order.setMerchant(merchant);
order.setSeller(seller);
order.setBuyer(buyer);
order.setGoods(Collections.singletonList(goods));

// 构造请求
SubmitTradeOrdersRequest request = new SubmitTradeOrdersRequest();
request.setBatchRequestId("req_20260429001");
request.setSceneCode("PAY_INTO_CHINA");
request.setQuotaAccumulationMethod("USER_ID");
request.setQuotaAccumulationId("2208****");
request.setPlatform("AE");
request.setTradeOrders(Collections.singletonList(order));

// 调用 Service（内部已强制验签，验签失败抛 WfException）
SubmitTradeOrdersResponse response = tradeOrderService.submitTradeOrders(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("批次 ID: " + response.getId());
    System.out.println("批次状态: " + response.getStatus());
    for (TradeOrderResult result : response.getTradeOrders()) {
        System.out.println("订单号: " + result.getReferenceOrderNo()
            + ", 状态: " + result.getOrderStatus());
    }
    // 使用批次 ID 查询最终处理结果
    // QueryTradeOrdersRequest queryRequest = new QueryTradeOrdersRequest();
    // queryRequest.setId(response.getId());
    // QueryTradeOrdersResponse queryResponse = tradeOrderService.queryTradeOrders(queryRequest);
} else {
    System.err.println("提交失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 TradeOrderService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new TradeOrderService(config)` 创建实例。
