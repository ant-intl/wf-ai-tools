# Create a Settlement 接口接入指引

## 接口说明

发起 FX 结算以执行货币兑换。SPOT 结算需提供从 Quote API 获取的有效 `quoteId`；FORWARD 和 UNFUNDED_SPOT 结算需提供 `dealId` 并指定卖出或买入金额。`requestId` 实现幂等提交，防止重复结算。

## 官方文档

- [create_a_settlement 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_settlement)

## 请求地址

`POST /api/open/v1/fx/settlements/create`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-02T04:09:30+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|---------|-------------|
| `requestId` | string | Yes | 幂等 ID，用于防止重复提交，每次创建结算请求必须唯一 |
| `dealId` | string | Conditional | 交易 ID，FORWARD 和 UNFUNDED_SPOT 结算必填；SPOT 结算不得提供 |
| `quoteId` | string | Conditional | 报价 ID，SPOT 结算必填；FORWARD 和 UNFUNDED_SPOT 结算不得提供 |
| `sellAmount` | Amount | Conditional | 卖出金额，SPOT 结算不得提供；FORWARD/UNFUNDED_SPOT 结算时与 `buyAmount` 二必填 |
| `buyAmount` | Amount | Conditional | 买入金额，SPOT 结算不得提供；FORWARD/UNFUNDED_SPOT 结算时与 `sellAmount` 二必填 |
| `settlementDate` | string | No | 期望结算日期，YYYY-MM-DD 格式（UTC），适用于 FORWARD 和 UNFUNDED_SPOT |
| `beneficiary` | Beneficiary | Conditional | 收款人信息，涉及银行账户出金时提供 |
| `reference` | string | No | 业务参考号，用于对账追踪 |
| `memo` | string | No | 交易备注 |

### Beneficiary Object

| Field | Type | Required | Description |
|-------|------|---------|-------------|
| `beneficiaryId` | string | Conditional | 收款人唯一标识，当 `accountType` 为 BANK_ACCOUNT 时必填 |
| `accountType` | string | Yes | 收款人账户类型，支持 `BANK_ACCOUNT`（提供 beneficiary 时必填） |

### 请求示例（SPOT 结算）

```json
{
  "requestId": "SR20260402001",
  "quoteId": "QT202604021208001"
}
```

### 请求示例（FORWARD/UNFUNDED_SPOT 结算）

```json
{
  "requestId": "SR20260402002",
  "dealId": "DEAL20260402001",
  "sellAmount": {
    "currency": "USD",
    "value": 10000
  },
  "settlementDate": "2026-04-04"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 结算唯一标识符 |
| `sellAmount` | Amount | 卖出金额 |
| `buyAmount` | Amount | 买入金额 |
| `quote` | SettlementQuote | 报价信息 |
| `dealId` | string | 交易 ID，FORWARD 和 UNFUNDED_SPOT 结算返回 |
| `settlementDate` | string | 期望结算日期，YYYY-MM-DD 格式（UTC） |
| `beneficiary` | Beneficiary | 收款人信息（如涉及） |
| `reference` | string | 请求中提供的业务参考号 |
| `memo` | string | 请求中提供的交易备注 |
| `feeAmount` | Amount | 结算手续费 |
| `status` | string | 当前结算状态：PROCESSING、SUCCESS、FAILED |
| `createdAt` | datetime | 结算创建时间（ISO 8601 格式） |
| `settledAt` | datetime | 结算完成时间（ISO 8601 格式），仅结算成功后返回 |
| `failedAt` | datetime | 结算失败时间（ISO 8601 格式），仅 status 为 FAILED 时返回 |
| `failureCode` | string | 失败代码，仅 status 为 FAILED 时返回 |
| `failureMessage` | string | 失败描述信息，仅 status 为 FAILED 时返回 |

### SettlementQuote Object

| Field | Type | Description |
|-------|------|-------------|
| `quoteId` | string | 报价唯一标识符 |
| `currencyPair` | string | 货币对，ISO 4217 格式（如 USD/HKD） |
| `clientRate` | decimal | 客户端汇率，最多 8 位小数 |
| `effectiveAt` | datetime | 报价生效时间（ISO 8601 格式） |
| `expiresAt` | datetime | 报价过期时间（ISO 8601 格式） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "STL20260402001",
  "sellAmount": {
    "currency": "USD",
    "value": 10000
  },
  "buyAmount": {
    "currency": "HKD",
    "value": 77850
  },
  "quote": {
    "quoteId": "QT202604021208001",
    "currencyPair": "USD/HKD",
    "clientRate": "7.78500000",
    "effectiveAt": "2026-04-02T04:08:56Z",
    "expiresAt": "2026-04-02T04:09:56Z"
  },
  "status": "SUCCESS",
  "createdAt": "2026-04-02T04:08:58Z",
  "settledAt": "2026-04-02T04:08:58Z"
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 创建成功 | — |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 联系 WorldFirst 技术支持 |
| `PROCESS_FAIL` | F | 处理失败 | 短暂延迟后重试，若问题持续请联系支持 |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查所有必填参数是否提供且格式正确 |
| `USER_NOT_EXIST` | F | 用户不存在 | 验证 API 凭据和商户账户 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在 | 确保 API Key 具有 FX 结算权限 |
| `FX_SETTLEMENT_DATE_NOT_SUPPORTED` | F | 结算日期不支持 | 选择支持范围内的有效结算日期 |
| `FX_QUOTE_EXPIRED` | F | 报价已过期 | 获取新报价后重试结算请求 |
| `FX_SETTLEMENT_INCONSISTENT_WITH_DEAL_ORDER` | F | 结算详情与交易订单不一致 | 确认卖出/买入金额和币种与交易一致 |
| `FX_SETTLEMENT_METHOD_NOT_SUPPORTED` | F | 结算方式不支持 | 确认结算类型和参数 |
| `FX_SETTLEMENT_BENEFICIARY_NOT_EXISTED` | F | 收款人不存在 | 验证收款人 ID 和账户类型 |
| `FX_SETTLEMENT_AMOUNT_EXCEED_DEAL_AMOUNT` | F | 结算金额超出交易金额 | 减少结算金额至交易剩余额度内 |

## 示例代码

参考 [references/foreign-exchange/settlement/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
settlement/java/
├── service/
│   └── SettlementService.java                         # 薄封装 Service，包含 createSettlement 方法
└── model/
    ├── domain/
    │   ├── SettlementQuote.java                       # 结算关联的报价信息
    │   └── Beneficiary.java                           # 收款人信息
    ├── request/
    │   └── CreateSettlementRequest.java               # 请求参数（requestId, quoteId/dealId, amounts 等）
    └── response/
        └── CreateSettlementResponse.java              # 响应结果（result, id, quote, amounts, status 等）
```

## 集成使用方式

SettlementService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 根据结算类型准备参数：SPOT 使用 `quoteId`，FORWARD/UNFUNDED_SPOT 使用 `dealId` + 金额
2. 构造 `CreateSettlementRequest` 设置 `requestId`（幂等 ID）及对应参数
3. 调用 `SettlementService.createSettlement(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
4. 检查 `result.resultStatus` 是否为 `S`，获取结算 ID 和状态
5. 根据状态处理后续逻辑：PROCESSING 需轮询，SUCCESS 完成，FAILED 查看失败原因

### 业务代码示例

```java
// === SPOT 结算 ===
CreateSettlementRequest spotRequest = new CreateSettlementRequest();
spotRequest.setRequestId("SR20260402001");
spotRequest.setQuoteId("QT202604021208001");

CreateSettlementResponse spotResponse = settlementService.createSettlement(spotRequest);

if ("S".equals(spotResponse.getResult().getResultStatus())) {
    System.out.println("结算 ID: " + spotResponse.getId());
    System.out.println("状态: " + spotResponse.getStatus());
    System.out.println("卖出: " + spotResponse.getSellAmount().getValue() + " " + spotResponse.getSellAmount().getCurrency());
    System.out.println("买入: " + spotResponse.getBuyAmount().getValue() + " " + spotResponse.getBuyAmount().getCurrency());
}

// === FORWARD/UNFUNDED_SPOT 结算 ===
CreateSettlementRequest dealRequest = new CreateSettlementRequest();
dealRequest.setRequestId("SR20260402002");
dealRequest.setDealId("DEAL20260402001");

Amount sellAmount = new Amount();
sellAmount.setCurrency("USD");
sellAmount.setValue(new BigDecimal("10000"));
dealRequest.setSellAmount(sellAmount);
dealRequest.setSettlementDate("2026-04-04");

CreateSettlementResponse dealResponse = settlementService.createSettlement(dealRequest);

if ("S".equals(dealResponse.getResult().getResultStatus())) {
    System.out.println("结算 ID: " + dealResponse.getId());
    System.out.println("关联交易: " + dealResponse.getDealId());
    System.out.println("结算日期: " + dealResponse.getSettlementDate());
}
```

### 幂等性保障

```java
// requestId 用于防止重复提交，相同 requestId 的重复请求返回相同结果
String requestId = UUID.randomUUID().toString();

CreateSettlementRequest request = new CreateSettlementRequest();
request.setRequestId(requestId);
request.setQuoteId("QT202604021208001");

// 首次调用
CreateSettlementResponse response1 = settlementService.createSettlement(request);

// 网络超时后重试（相同 requestId），返回相同结果
CreateSettlementResponse response2 = settlementService.createSettlement(request);
// response2.getId() == response1.getId()
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 SettlementService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new SettlementService(config)` 创建实例。
