# submitTradeOrder 接口接入指引

## 接口说明

上传交易订单到万里汇。支持两种业务场景：PAY_INTO_CHINA（B2C 结汇）和 CREATE_B2B_ORDERS（B2B 订单关联）。

## 官方文档

- [submitTradeOrder 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/submit_trade_order)

## 请求地址

`POST /amsin/api/v1/business/account/submitTradeOrder`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<base64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601, e.g. `2019-04-04T12:08:56+08:00` |
| `Connected-AccountId` | No | 万里汇账户唯一标识 |

## 请求参数

### Root Level

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `requestId` | String | **Yes** | 幂等键，max 64 chars |
| `sceneCode` | String | **Yes** | `PAY_INTO_CHINA` 或 `CREATE_B2B_ORDERS` |
| `quotaAccumulationMethod` | String | **Yes** | `USER_ID` / `RECEIVING_ACCOUNT` / `VIRTUAL_ACCOUNT` / `BENEFICIARY` / `TRANSFER_ID` / `COLLECTION_ID` |
| `quotaAccumulationId` | String | **Yes** | 与上述方式对应的 ID |
| `tradeOrders` | List\<TradeOrder\> | **Yes** | B2C 最多 100 笔，B2B 最多 10 笔 |
| `notifyUrl` | String | No | 异步回调地址，max 256 chars |
| `platform` | String | B2C required | sceneCode=`PAY_INTO_CHINA` 时必填 |
| `extendInfo` | String | No | B2B 额外信息，max 2048 chars |

### TradeOrder 对象

完整嵌套字段见 [field-reference.md](../field-reference.md)。

**两个场景共有字段**：referenceOrderNo, paymentTime, transAmount, tradeAmount, tradeType, goods（tradeType=GOODS），shipping（tradeType=GOODS）

**PAY_INTO_CHINA 额外必填**：orderTime, orderType, merchant, seller, buyer

**CREATE_B2B_ORDERS 额外必填**：tradeTerms, isUsedForExchange, bizContractInfo, logisticsMode（tradeType=GOODS）

## 响应参数

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U), `resultCode`, `resultMessage` |
| `requestId` | String | PAY_INTO_CHINA | 回显请求号 |
| `tradeOrderResult` | List\<TradeOrderResult\> | PAY_INTO_CHINA | 各笔订单受理结果 |
| `acceptOrderId` | String | CREATE_B2B_ORDERS | 万里汇受理单号，max 128 chars |

## 错误码

### 可重试 (resultStatus=U) — 最多 7 次，间隔: 5/10/20/40/80/160/320 min

| Code | Handling |
|------|----------|
| `UNKNOWN_EXCEPTION` | 使用原 requestId 直接重试 |

### 不可重试 (resultStatus=F)

| Code | Handling |
|------|----------|
| `PARAM_ILLEGAL` | 修改参数后更换 requestId 重试 |
| `REPEAT_REQ_INCONSISTENT` | 更换 requestId 重试 |
| `PROCESS_FAIL` / `INVALID_CLIENT` | 人工介入，勿盲目重试 |

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
├── client/
│   ├── TradeOrderManagementClient.java    ← 统一客户端（submitTradeOrder + inquiryTradeOrder）
│   └── TradeOrderManagementClientTest.java
└── model/
    ├── domain/
    │   ├── TradeOrder.java                ← 含所有 B2C/B2B 子对象内部类
    │   ├── Merchant.java
    │   ├── Store.java
    │   ├── Customer.java
    │   ├── Buyer.java
    │   ├── BuyerName.java
    │   ├── Goods.java
    │   ├── Shipping.java
    │   ├── BizContractInfo.java
    │   ├── AttachmentInfo.java
    │   ├── WayBillInfo.java
    │   ├── LogisticsCompany.java
    │   ├── DeclarationInfo.java
    │   └── TradeOrderResult.java
    ├── request/SubmitTradeOrderRequest.java
    └── response/SubmitTradeOrderResponse.java
```

### Golang 模板结构

```
golang/
├── client/
│   ├── trade_order_management_client.go
│   └── trade_order_management_integration_test.go
└── model/
    ├── domain/trade_order.go
    ├── request/submit_trade_order_request.go
    └── response/submit_trade_order_response.go
```

## 测试方法

| 方法 | 说明 |
|------|------|
| `testSubmitTradeOrderB2C` | PAY_INTO_CHINA 场景，GOODS 类型，含 merchant/seller/buyer/goods/shipping |
| `testSubmitTradeOrderB2B` | CREATE_B2B_ORDERS 场景，含 bizContractInfo/isUsedForExchange |
| `testInquiryTradeOrder` | 使用 submitTradeOrder 的 requestId 查询批次状态 |
