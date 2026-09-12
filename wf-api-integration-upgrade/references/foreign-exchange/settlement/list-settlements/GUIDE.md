# List Settlements 接口接入指引

## 接口说明

分页查询 FX 结算记录列表，支持按关联交易 ID、卖出币种、买入币种、结算状态和创建时间范围过滤。采用游标分页，适用于结算对账和批量跟踪结算状态等场景。

## 官方文档

- [list_settlements 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/list_settlements)
- 枚举取值（`SettlementStatus`、`SettlementAccountType`）见 [Settlement Overview](https://docs.worldfirst.com/wfdocs/api-sdk/settlement_overview)

## 请求地址

`POST /api/open/v1/fx/settlements/list`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-02T04:10:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `limit` | integer | No | 每页记录数，取值范围 1-100；不传时默认 20 |
| `cursor` | string | No | 分页游标；首次请求省略，后续请求传入上次响应的 `nextCursor` 或 `prevCursor` |
| `dealId` | string | No | 按关联交易 ID 过滤 |
| `sellCurrency` | string | No | 按卖出币种过滤（ISO 4217 三字母代码） |
| `buyCurrency` | string | No | 按买入币种过滤（ISO 4217 三字母代码） |
| `status` | string | No | 按结算状态过滤（SettlementStatus 枚举） |
| `fromCreatedAt` | datetime | No | 创建时间范围起始（ISO 8601 格式）；最大查询跨度 30 天 |
| `toCreatedAt` | datetime | No | 创建时间范围结束（ISO 8601 格式） |

### 游标分页说明

- **首次请求**：不传 `cursor`，API 返回 `nextCursor`
- **后续请求**：将上次响应的 `nextCursor` 作为 `cursor` 传入
- **最后一页**：`nextCursor` 为 `null`，表示无更多数据
- **时间范围**：`fromCreatedAt` 与 `toCreatedAt` 的最大跨度为 30 天，超范围查询需缩小时间窗口分批拉取

### 请求示例

```json
{
  "limit": 10,
  "status": "PROCESSING",
  "sellCurrency": "USD",
  "fromCreatedAt": "2026-04-01T00:00:00Z",
  "toCreatedAt": "2026-04-30T23:59:59Z"
}
```

> 带游标的后续请求示例：

```json
{
  "limit": 10,
  "cursor": "eyJpZCI6IlNUTDIwMjYwNDAyMDAzIn0=",
  "status": "PROCESSING",
  "sellCurrency": "USD",
  "fromCreatedAt": "2026-04-01T00:00:00Z",
  "toCreatedAt": "2026-04-30T23:59:59Z"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `items` | array[SettlementRecord] | 结算记录列表 |
| `nextCursor` | string | 下一页游标，有更多结果时返回 |
| `prevCursor` | string | 上一页游标 |

### SettlementRecord Object

| Field | Type | Description |
|-------|------|-------------|
| `id` | string | 结算唯一标识符 |
| `sellAmount` | Amount | 卖出金额 |
| `buyAmount` | Amount | 买入金额 |
| `quote` | SettlementQuote | 报价信息 |
| `dealId` | string | 关联交易 ID，仅 FORWARD/UNFUNDED_SPOT 结算返回 |
| `settlementDate` | string | 期望结算日期（YYYY-MM-DD），仅 FORWARD/UNFUNDED_SPOT 结算返回 |
| `beneficiary` | Beneficiary | 收款人信息，仅结算目标为银行账户时返回 |
| `reference` | string | 业务参考号，作为银行账单描述传递给收款行 |
| `memo` | string | 交易备注 |
| `feeAmount` | Amount | 手续费金额，FORWARD/UNFUNDED_SPOT 且银行账户出金时返回 |
| `status` | string | 结算状态（SettlementStatus 枚举）：PROCESSING、SUCCESS、FAILED |
| `createdAt` | datetime | 结算创建时间（ISO 8601 格式） |
| `settledAt` | datetime | 结算完成时间，`status` 为 SUCCESS 时返回 |
| `failedAt` | datetime | 结算失败时间，`status` 为 FAILED 时返回 |
| `failureCode` | string | 失败结果码，`status` 为 FAILED 时返回 |
| `failureMessage` | string | 失败原因描述，`status` 为 FAILED 时返回 |

### SettlementQuote Object

| Field | Type | Description |
|-------|------|-------------|
| `quoteId` | string | 本笔结算使用的报价 ID |
| `currencyPair` | string | 货币对，格式为 `sell/buy`（如 `USD/HKD`） |
| `clientRate` | decimal | 成交汇率，8 位小数 |
| `effectiveAt` | datetime | 报价生效时间（ISO 8601 格式） |
| `expiresAt` | datetime | 报价过期时间（ISO 8601 格式） |

### Beneficiary Object

| Field | Type | Description |
|-------|------|-------------|
| `beneficiaryId` | string | 收款人 ID |
| `accountType` | string | 收款人账户类型（SettlementAccountType 枚举），当前仅支持 `BANK_ACCOUNT` |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | 币种代码（ISO-4217） |
| `value` | integer | 金额值，最小货币单位（如 USD 100.00 → value = 10000） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "items": [
    {
      "id": "STL20260402003",
      "sellAmount": { "currency": "USD", "value": 25000 },
      "buyAmount": { "currency": "JPY", "value": 3731250 },
      "quote": {
        "quoteId": "QT202604021208003",
        "currencyPair": "USD/JPY",
        "clientRate": "149.25000000",
        "effectiveAt": "2026-04-02T04:10:00Z",
        "expiresAt": "2026-04-02T04:11:00Z"
      },
      "dealId": "DEAL20260402002",
      "settlementDate": "2026-04-03",
      "beneficiary": {
        "beneficiaryId": "BEN_TOKEN_001",
        "accountType": "BANK_ACCOUNT"
      },
      "reference": "FX Forward Settlement",
      "memo": "FX Forward Settlement",
      "feeAmount": { "currency": "USD", "value": 25 },
      "status": "PROCESSING",
      "createdAt": "2026-04-02T04:10:10Z"
    }
  ],
  "nextCursor": "eyJpZCI6IlNUTDIwMjYwNDAyMDAzIn0="
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查 `limit` 是否在 1-100 区间，`fromCreatedAt`/`toCreatedAt` 是否为合法 ISO 8601 格式且跨度不超过 30 天 |
| `INVALID_CURSOR` | F | 游标无效或已过期 | 去掉 `cursor` 从第一页重新查询 |
| `USER_NOT_EXIST` | F | 用户不存在 | 验证 API 凭据和商户账户 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在 | 确保 API Key 具有 FX 结算查询权限 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，可重试 | 稍后重试，若问题持续联系技术支持 |

## 示例代码

参考 [references/foreign-exchange/settlement/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
settlement/java/
├── service/
│   └── SettlementService.java                         # 薄封装 Service，包含 listSettlements 方法
└── model/
    ├── domain/
    │   ├── SettlementQuote.java                       # 结算关联的报价信息
    │   ├── Beneficiary.java                           # 收款人信息
    │   └── SettlementRecord.java                      # 结算记录（用于 list 响应的 items）
    ├── request/
    │   └── ListSettlementsRequest.java                # 请求参数（limit, cursor, dealId, sellCurrency, buyCurrency, status, fromCreatedAt, toCreatedAt）
    └── response/
        └── ListSettlementsResponse.java               # 响应结果（result, items, nextCursor, prevCursor）
```

> `Amount`、`Result` 为通用对象，复用 `model/domain`、`model/response` 包下的已有定义。

## 集成使用方式

SettlementService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `ListSettlementsRequest` 设置查询条件（首次请求不传 `cursor`）
2. 调用 `SettlementService.listSettlements(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理结算记录
4. 若 `nextCursor` 不为 `null`，将其作为 `cursor` 传入下一次请求，重复步骤 1-3

### 业务代码示例

```java
// 构造请求
ListSettlementsRequest request = new ListSettlementsRequest();
request.setLimit(10);
request.setStatus("PROCESSING");
request.setSellCurrency("USD");
request.setFromCreatedAt("2026-04-01T00:00:00Z");
request.setToCreatedAt("2026-04-30T23:59:59Z");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
ListSettlementsResponse response = settlementService.listSettlements(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    for (SettlementRecord settlement : response.getItems()) {
        System.out.println("结算 ID: " + settlement.getId());
        System.out.println("状态: " + settlement.getStatus());
        System.out.println("关联交易: " + settlement.getDealId());
        System.out.println("期望结算日: " + settlement.getSettlementDate());
        System.out.println("卖出: " + settlement.getSellAmount().getValue() + " " + settlement.getSellAmount().getCurrency());
        System.out.println("买入: " + settlement.getBuyAmount().getValue() + " " + settlement.getBuyAmount().getCurrency());

        if (settlement.getBeneficiary() != null) {
            System.out.println("收款人: " + settlement.getBeneficiary().getBeneficiaryId());
        }
        if (settlement.getFeeAmount() != null) {
            System.out.println("手续费: " + settlement.getFeeAmount().getValue()
                + " " + settlement.getFeeAmount().getCurrency());
        }
    }

    // 游标分页：继续查询下一页
    if (response.getNextCursor() != null) {
        request.setCursor(response.getNextCursor());
        // 再次调用 settlementService.listSettlements(request) 获取下一页数据
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 游标分页完整遍历示例

```java
List<SettlementRecord> allSettlements = new ArrayList<>();
String cursor = null;

do {
    ListSettlementsRequest request = new ListSettlementsRequest();
    request.setLimit(100);
    request.setFromCreatedAt("2026-04-01T00:00:00Z");
    request.setToCreatedAt("2026-04-30T23:59:59Z");
    if (cursor != null) {
        request.setCursor(cursor);
    }

    // 调用 Service（内部已强制验签，验签失败抛 WfException）
    ListSettlementsResponse response = settlementService.listSettlements(request);
    if (!"S".equals(response.getResult().getResultStatus())) {
        throw new RuntimeException("查询失败: " + response.getResult().getResultMessage());
    }

    allSettlements.addAll(response.getItems());
    cursor = response.getNextCursor();
} while (cursor != null);

System.out.println("共查询到 " + allSettlements.size() + " 笔结算");
```

### 按交易 ID 核对结算结果示例

```java
// 查询某笔远期交易关联的全部结算，确认是否已交割完成
ListSettlementsRequest request = new ListSettlementsRequest();
request.setLimit(50);
request.setDealId("DEAL20260402002");

ListSettlementsResponse response = settlementService.listSettlements(request);

if ("S".equals(response.getResult().getResultStatus())) {
    for (SettlementRecord settlement : response.getItems()) {
        switch (settlement.getStatus()) {
            case "PROCESSING":
                System.out.println("结算处理中: " + settlement.getId());
                break;
            case "SUCCESS":
                System.out.println("结算完成: " + settlement.getId()
                    + "，时间: " + settlement.getSettledAt());
                break;
            default:
                // FAILED
                System.out.println("结算失败: " + settlement.getId()
                    + "，原因码: " + settlement.getFailureCode()
                    + "，描述: " + settlement.getFailureMessage());
                break;
        }
    }
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 SettlementService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new SettlementService(config)` 创建实例。
