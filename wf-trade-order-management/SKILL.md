---
name: wf-trade-order-management
description: "[user] Generate Java integration code for WorldFirst (WF) trade order management APIs: submitTradeOrder (upload trade orders), inquiryTradeOrder (query upload results, PAY_INTO_CHINA only), and notifyTradeOrder (receive async callback, PAY_INTO_CHINA only). All methods share a single TradeOrderManagementClient class. Use when implementing WF trade order submission, querying trade order upload status, handling WF trade order callbacks, or preparing for WF settlement (结汇)."
---

# WF Trade Order Management APIs Integration

Generate production-ready Java code for integrating with WorldFirst trade order management APIs.
All outbound interfaces share a single `TradeOrderManagementClient` class; the inbound callback is handled by a dedicated `NotifyTradeOrderController`.

## Prerequisites

- Java 8 (禁止使用 Java 9+ 专有语法)
- WF Client ID and RSA key pair
- Shared infrastructure: WfConfig, WfSigner, WfHttpClientUtil (use respective sub-skills if not present)

## Sub-skills

- **WfConfig.java** — invoke `wf-config` skill
- **WfSigner.java** — invoke `wf-rsa256-signer` skill
- **WfHttpClientUtil.java** — invoke `wf-http-client` skill

## Pre-Generation Questions (MUST ASK BEFORE GENERATING CODE)

**Question**: 请问需要支持哪些业务场景？

| Option | Description |
|--------|-------------|
| **PAY_INTO_CHINA（B2C 结汇）** | 生成全套三个接口：submitTradeOrder、inquiryTradeOrder、notifyTradeOrder 回调处理器 |
| **CREATE_B2B_ORDERS（B2B 订单关联）** | 仅生成 submitTradeOrder |
| **两者都需要** | 生成完整套件，inquiryTradeOrder 和 notifyTradeOrder 标注仅适用于 PAY_INTO_CHINA |

---

## API 1: submitTradeOrder

**Endpoint**: `POST /amsin/api/v1/business/account/submitTradeOrder`

### Request Headers

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<base64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601, e.g. `2019-04-04T12:08:56+08:00` |
| `Connected-AccountId` | No | 万里汇账户唯一标识 |

### Request Parameters

#### Root Level

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

#### TradeOrder Object

完整嵌套字段见 [field-reference.md](field-reference.md)，以下为各场景必填概览：

**两个场景共有字段**

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `referenceOrderNo` | String | **Yes** | 订单号，max 64；B2B: >3 chars，不能有连续4个重复字符 |
| `paymentTime` | String | **Yes** | ISO8601，不能晚于 API 调用时间 |
| `transAmount` | Amount | **Yes** | B2C: 买家支付金额；B2B: 期望关联金额 |
| `tradeAmount` | Amount | **Yes** | B2C: 卖家收到金额；B2B: 本批订单总金额 |
| `tradeType` | String | **Yes** | `GOODS` 或 `SERVICE` |
| `goods` | List\<Goods\> | tradeType=GOODS | 商品信息 |
| `shipping` | Shipping | tradeType=GOODS | 物流信息 |

**PAY_INTO_CHINA 额外必填**

| Field | Type | Description |
|-------|------|-------------|
| `orderTime` | String | 交易时间，ISO8601 |
| `orderType` | String | `LOAN`（支付单）或 `REFUND`（退款单）|
| `merchant` | Merchant | 商户信息（含 `store.storeShopUrl`） |
| `seller` | Customer | 卖家信息 |
| `buyer` | Buyer | 买家信息（referenceBuyerId/buyerName/buyerEmail 三选一） |

**CREATE_B2B_ORDERS 额外必填**

| Field | Type | Description |
|-------|------|-------------|
| `tradeTerms` | String | 成交方式 |
| `isUsedForExchange` | String | `Y` 或 `N` |
| `bizContractInfo` | BizContractInfo | 合同信息 |
| `logisticsMode` | String | tradeType=GOODS 时必填：`DROPSHIPPING` 或 `REGULAR_MODE` |

### submitTradeOrder Response Fields

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U), `resultCode`, `resultMessage` |
| `requestId` | String | PAY_INTO_CHINA | 回显请求号 |
| `tradeOrderResult` | List\<TradeOrderResult\> | PAY_INTO_CHINA | 各笔订单受理结果 |
| `acceptOrderId` | String | CREATE_B2B_ORDERS | 万里汇受理单号，max 128 chars |

### submitTradeOrder Error Codes

#### Retryable (resultStatus=U) — max 7 retries, intervals: 5/10/20/40/80/160/320 min

| Code | Handling |
|------|----------|
| `UNKNOWN_EXCEPTION` | 使用原 requestId 直接重试 |

#### Non-retryable (resultStatus=F)

| Code | Handling |
|------|----------|
| `PARAM_ILLEGAL` | 修改参数后更换 requestId 重试 |
| `REPEAT_REQ_INCONSISTENT` | 更换 requestId 重试 |
| `PROCESS_FAIL` / `INVALID_CLIENT` | 人工介入，勿盲目重试 |

---

## API 2: inquiryTradeOrder（PAY_INTO_CHINA only）

**Endpoint**: `POST /amsin/api/v1/business/account/inquiryTradeOrder`

### Request Parameters

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `requestId` | String | **Yes** | 与 submitTradeOrder 相同的 requestId，max 64 chars |
| `sceneCode` | String | **Yes** | 固定值：`PAY_INTO_CHINA` |
| `quotaAccumulationMethod` | String | **Yes** | `USER_ID` / `RECEIVING_ACCOUNT` / `VIRTUAL_ACCOUNT` / `BENEFICIARY` |
| `quotaAccumulationId` | String | **Yes** | 与上述方式对应的 ID |
| `tradeType` | String | **Yes** | `GOODS` 或 `SERVICE` |

### inquiryTradeOrder Response Fields

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U), `resultCode`, `resultMessage` |
| `requestId` | String | Always | 回显请求号 |
| `batchStatus` | String | Always | `PROCESSING`（处理中）或 `FINISHED`（处理完成）|
| `tradeOrderResults` | List\<TradeOrderResult\> | batchStatus=FINISHED | 各笔订单结果 |

TradeOrderResult fields: `referenceOrderNo` (String, REQUIRED, 交易订单编号, max 64 chars), `orderStatus` (String, REQUIRED, 交易订单状态: REJECT/AVAILABLE/ACCEPT/PARTIAL_DECLARED), `orderType` (String, CONDITIONAL, 订单类型: LOAN/REFUND, PAY_INTO_CHINA时必填), `statusMessage` (String, REQUIRED, 交易订单状态详情), `transAmount` (Amount, 交易金额), `tradeAmount` (Amount, 贸易金额), `remainAmount` (Amount, 通过上传本笔交易单积累的可用结汇额度)。

### Polling Strategy

```
1. 使用相同 requestId 调用 inquiryTradeOrder
2. batchStatus = PROCESSING → 等待后重试
3. batchStatus = FINISHED → 处理 tradeOrderResults
```

建议轮询间隔与 submitTradeOrder 重试间隔对齐（5/10/20 min）。

### inquiryTradeOrder Error Codes

#### API Level (result.resultCode)

| Code | Handling |
|------|----------|
| `UNKNOWN_EXCEPTION` | Retry (7x, 5/10/20/40/80/160/320 min) |
| `PARAM_ILLEGAL` | 修改参数，更换 requestId |
| `ORDER_NOT_EXIST` | 确认 requestId 与已提交的订单匹配 |
| `USER_NOT_EXIST` / `ACCOUNT_NOT_EXIST` | 检查 quotaAccumulationId |
| `INVALID_CLIENT` / `CONTRACT_CHECK_FAIL` | 检查配置，勿盲目重试 |

#### Order Level (tradeOrderResult.errorCode)

| Code | Handling |
|------|----------|
| `PARAM_ILLEGAL` | 检查提交时的订单数据 |
| `TRADE_ORDER_SUBMITTED` | 该订单已提交，停止重试 |
| `RISK_REJECT` | 联系 WF 支持 |
| `PROCESS_FAIL` | 联系 WF 支持，勿重试 |

---

## API 3: notifyTradeOrder Callback（PAY_INTO_CHINA only）

WF 在处理完成后主动 POST 到集成商的 `notifyUrl`。

### Inbound Request（WF → 集成商）

收到通知后**必须先验签**，再处理业务逻辑。

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `requestId` | String | **Yes** | 与 submitTradeOrder 相同的 requestId |
| `tradeOrderResults` | List\<TradeOrderResult\> | **Yes** | Fields: `referenceOrderNo`、`orderStatus`、`orderType`、`statusMessage`、`transAmount`、`tradeAmount`、`remainAmount` |

### Outbound Response（集成商 → WF）

返回前**必须签名**。

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `result.resultCode` | String | **Yes** | `SUCCESS` / `UNKNOWN_EXCEPTION` / `PROCESS_FAIL` |
| `result.resultMessage` | String | **Yes** | 结果描述 |

### WF Retry Policy

未收到有效响应时，WF 重试 7 次，间隔：2 min → 10 min → 10 min → 1h → 2h → 6h → 15h。

> 签名验证完成后立即返回 `SUCCESS`，业务逻辑异步处理，避免超时触发重试。

### Idempotency

以 `requestId` 作为幂等键，记录已处理的通知，跳过重复投递。

---

## Package Structure

```
{basePackage}.wf
├── model/
│   ├── request/
│   │   ├── SubmitTradeOrderRequest.java
│   │   ├── InquiryTradeOrderRequest.java      ← PAY_INTO_CHINA only
│   │   └── NotifyTradeOrderRequest.java        ← PAY_INTO_CHINA only（inbound）
│   ├── response/
│   │   ├── SubmitTradeOrderResponse.java
│   │   ├── InquiryTradeOrderResponse.java      ← PAY_INTO_CHINA only
│   │   ├── NotifyTradeOrderResponse.java        ← PAY_INTO_CHINA only（outbound）
│   │   └── Result.java                         ← 共享，已存在则复用
│   └── domain/
│       ├── TradeOrder.java                     ← 含所有 B2C/B2B 子对象
│       ├── Amount.java                         ← 共享，已存在则复用
│       ├── Merchant.java
│       ├── Customer.java
│       ├── Buyer.java
│       ├── Goods.java
│       ├── Shipping.java
│       ├── BizContractInfo.java
│       ├── AttachmentInfo.java
│       ├── WayBillInfo.java
│       ├── LogisticsCompany.java
│       ├── DeclarationInfo.java
│       ├── Address.java                        ← 共享，已存在则复用
│       └── TradeOrderResult.java
├── client/
│   └── TradeOrderManagementClient.java         ← submitTradeOrder + inquiryTradeOrder
├── controller/
│   └── NotifyTradeOrderController.java         ← PAY_INTO_CHINA only，inbound callback
├── config/   WfConfig.java                     ← 共享，复用
├── signer/   WfSigner.java                     ← 共享，复用
└── util/     WfHttpClientUtil.java             ← 共享，复用
```

---

## TradeOrderManagementClient

```java
public class TradeOrderManagementClient {
    private final WfConfig config;
    private WfHttpClientUtil httpClientUtil;

    public TradeOrderManagementClient(WfConfig config) { ... }

    public void init() {
        this.httpClientUtil = new WfHttpClientUtil(config);
    }

    public void setHttpClientUtil(WfHttpClientUtil httpClientUtil) {
        this.httpClientUtil = httpClientUtil;
    }

    // Both scenes
    public SubmitTradeOrderResponse submitTradeOrder(SubmitTradeOrderRequest request) { ... }

    // PAY_INTO_CHINA only
    public InquiryTradeOrderResponse inquiryTradeOrder(InquiryTradeOrderRequest request) { ... }
}
```

### Key behaviors

#### submitTradeOrder()

1. **Validate**: `requestId` 非空，max 64；`sceneCode` 非空；`tradeOrders` 非空且不超过上限（B2C 100 / B2B 10）；`platform` 在 sceneCode=`PAY_INTO_CHINA` 时必填
2. **Call** `httpClientUtil.sendPostRequest(url, PATH_SUBMIT_TRADE_ORDER, body)`
3. **Handle resultStatus**: `S` → return；`F`/`U` → throw `WfException`

#### inquiryTradeOrder()

1. **Validate**: `requestId` 非空；`sceneCode` 固定 `PAY_INTO_CHINA`；`tradeType` 非空
2. **Call** `httpClientUtil.sendPostRequest(url, PATH_INQUIRY_TRADE_ORDER, body)`
3. **Handle resultStatus**: `S` → return（调用方自行判断 `batchStatus`）；`F`/`U` → throw `WfException`

## NotifyTradeOrderController

```java
@RestController
public class NotifyTradeOrderController {

    // 1. 从请求头提取 Signature 并调用 WfSigner.verifySignature() 验签
    //    验签失败 → 返回 HTTP 400，不返回 SUCCESS
    // 2. 解析 NotifyTradeOrderRequest
    // 3. 以 requestId 做幂等判断，已处理则直接返回 SUCCESS
    // 4. 异步处理 tradeOrderResults 业务逻辑
    // 5. 构建 NotifyTradeOrderResponse（resultCode=SUCCESS）
    // 6. 调用 WfSigner 对响应体签名，写入响应头 Signature
    // 7. 返回响应

    @PostMapping("/your-notify-path")
    public ResponseEntity<NotifyTradeOrderResponse> handle(
        @RequestHeader HttpHeaders headers,
        @RequestBody NotifyTradeOrderRequest request) { ... }
}
```

---

## Test Class

### Pre-Generation Question: Signature Mode (MUST ASK)

**Question**: 生成测试类时，签名逻辑使用哪种模式？

| Option | Description |
|--------|-------------|
| **Mock 签名（跳过验签）** | Mock WfSigner 的 `generateSignature` 方法固定返回 `"TESTING_SIGNATURE"`。WF 会返回 `INVALID_SIGNATURE`，适用于快速验证请求格式。 |
| **真实签名（使用密钥文件）** | 使用 WfConfig 中配置的真实私钥和公钥路径，可完整跑通接口。 |

#### If user selects Mock 签名:

```java
WfSigner mockSigner = Mockito.mock(WfSigner.class);
Mockito.when(mockSigner.generateSignature(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
    .thenReturn("TESTING_SIGNATURE");
Mockito.when(mockSigner.verifySignature(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
    .thenReturn(true);

WfHttpClientUtil httpClientUtil = new WfHttpClientUtil(mockConfig, mockSigner);
client = new TradeOrderManagementClient(mockConfig);
client.setHttpClientUtil(httpClientUtil);
```

#### If user selects 真实签名:

```java
WfConfig config = new WfConfig();
client = new TradeOrderManagementClient(config);
client.init();
```

### 测试方法

| 方法 | 说明 |
|------|------|
| `testSubmitTradeOrderB2C` | PAY_INTO_CHINA 场景，GOODS 类型，含 merchant/seller/buyer/goods/shipping |
| `testSubmitTradeOrderB2B` | CREATE_B2B_ORDERS 场景，含 bizContractInfo/isUsedForExchange |
| `testInquiryTradeOrder` | 使用 submitTradeOrder 的 requestId 查询批次状态 |

---

## WfErrorCode Additions

追加以下错误码到 `WfErrorCode.java`（检查重复后追加）：

```java
// -------------------------------------------------------------------------
// submitTradeOrder / inquiryTradeOrder 接口错误码
// -------------------------------------------------------------------------
ORDER_NOT_EXIST("ORDER_NOT_EXIST", "Trade order does not exist"),
TRADE_ORDER_SUBMITTED("TRADE_ORDER_SUBMITTED", "Trade order has been submitted before"),
RISK_REJECT("RISK_REJECT", "Trade order rejected for risk control reasons"),
```

---

## Checklist

### submitTradeOrder
- [ ] Generate domain models: TradeOrder, Amount（复用）, Merchant, Customer, Buyer, Goods, Shipping, BizContractInfo, AttachmentInfo, WayBillInfo, LogisticsCompany, DeclarationInfo, Address（复用）
- [ ] Generate SubmitTradeOrderRequest (`wf.model.request`)
- [ ] Generate SubmitTradeOrderResponse + TradeOrderResult (`wf.model.response` / `wf.model.domain`)
- [ ] **Reuse** Result.java from `{basePackage}.wf.model.response`
- [ ] **Reuse** WfException / WfErrorCode（追加错误码，不重复生成）
- [ ] Generate TradeOrderManagementClient with `submitTradeOrder()` method

### inquiryTradeOrder（PAY_INTO_CHINA only）
- [ ] Generate InquiryTradeOrderRequest (`wf.model.request`)
- [ ] Generate InquiryTradeOrderResponse (`wf.model.response`)
- [ ] **Add** `inquiryTradeOrder()` method to TradeOrderManagementClient

### notifyTradeOrder（PAY_INTO_CHINA only）
- [ ] Generate NotifyTradeOrderRequest / NotifyTradeOrderResponse
- [ ] Generate NotifyTradeOrderController with signature verification + async processing + signed response

### Test
- [ ] Generate TradeOrderManagementClientTest with 3 test methods（询问签名模式后生成）

---

## Common Issues

1. **sceneCode 决定字段集合**: PAY_INTO_CHINA 需传 merchant/seller/buyer；CREATE_B2B_ORDERS 需传 bizContractInfo/isUsedForExchange
2. **tradeOrders 上限**: B2C 最多 100 笔，B2B 最多 10 笔，超出需分批提交
3. **inquiryTradeOrder 使用相同 requestId**: 查询时必须与提交时的 requestId 完全一致
4. **notifyTradeOrder 必须先验签**: 验签失败不能返回 SUCCESS，直接拒绝
5. **notifyTradeOrder 幂等**: 同一 requestId 可能多次投递，需记录已处理的请求
6. **tradeOrderResult 无 errorCode**: 表示该笔订单受理成功
7. **不支持国家**: tradeCountry / deliverCountry 不能为 `BY`（白俄罗斯）或 `RU`（俄罗斯）
