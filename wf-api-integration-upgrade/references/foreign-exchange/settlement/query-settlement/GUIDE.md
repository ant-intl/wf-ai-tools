# Query a Settlement 接口接入指引

## 接口说明

通过结算 ID 检索特定结算的完整详情。响应包含所有可用的结算信息，包括金额、报价详情、状态和时间戳。用于检查结算当前状态或在创建后检索完整详情。

## 官方文档

- [query_a_settlement 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_settlement)

## 请求地址

`POST /api/open/v1/fx/settlements/query`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-02T04:09:30+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | 要查询的结算 ID，必须是当前账户创建的有效结算 |

### 请求示例

```json
{
  "id": "STL20260402001"
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
| `reference` | string | 业务参考号 |
| `memo` | string | 交易备注 |
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
| `SUCCESS` | S | 查询成功 | — |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 联系 WorldFirst 技术支持 |
| `PARAM_ILLEGAL` | F | 参数非法 | 验证结算 ID 是否提供且格式正确 |
| `USER_NOT_EXIST` | F | 用户不存在 | 验证 API 凭据和商户账户 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在 | 确保 API Key 具有 FX 结算权限 |
| `FUND_ORDER_NOT_EXIST` | F | 结算不存在 | 验证结算 ID 是否正确且属于当前账户 |

## 示例代码

参考 [references/foreign-exchange/settlement/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
settlement/java/
├── service/
│   └── SettlementService.java                         # 薄封装 Service，包含 querySettlement 方法
└── model/
    ├── domain/
    │   ├── SettlementQuote.java                       # 结算关联的报价信息
    │   └── Beneficiary.java                           # 收款人信息
    ├── request/
    │   └── QuerySettlementRequest.java                # 请求参数（id）
    └── response/
        └── QuerySettlementResponse.java               # 响应结果（result, id, quote, amounts, status 等）
```

## 集成使用方式

SettlementService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QuerySettlementRequest` 设置结算 `id`
2. 调用 `SettlementService.querySettlement(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理结算详情

### 业务代码示例

```java
// 构造请求
QuerySettlementRequest request = new QuerySettlementRequest();
request.setId("STL20260402001");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QuerySettlementResponse response = settlementService.querySettlement(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("结算 ID: " + response.getId());
    System.out.println("结算状态: " + response.getStatus());
    System.out.println("创建时间: " + response.getCreatedAt());

    // 报价信息
    SettlementQuote quote = response.getQuote();
    System.out.println("货币对: " + quote.getCurrencyPair());
    System.out.println("汇率: " + quote.getClientRate());

    // 金额信息
    System.out.println("卖出: " + response.getSellAmount().getValue() + " " + response.getSellAmount().getCurrency());
    System.out.println("买入: " + response.getBuyAmount().getValue() + " " + response.getBuyAmount().getCurrency());

    // 根据状态处理不同逻辑
    switch (response.getStatus()) {
        case "PROCESSING":
            System.out.println("结算处理中，等待完成");
            System.out.println("期望结算日期: " + response.getSettlementDate());
            break;
        case "SUCCESS":
            System.out.println("结算已完成: " + response.getSettledAt());
            if (response.getFeeAmount() != null) {
                System.out.println("手续费: " + response.getFeeAmount().getValue() + " " + response.getFeeAmount().getCurrency());
            }
            break;
        case "FAILED":
            System.out.println("结算失败: " + response.getFailureCode());
            System.out.println("失败原因: " + response.getFailureMessage());
            System.out.println("失败时间: " + response.getFailedAt());
            break;
    }

    // 查看关联交易（FORWARD/UNFUNDED_SPOT）
    if (response.getDealId() != null) {
        System.out.println("关联交易 ID: " + response.getDealId());
    }

    // 查看收款人信息
    if (response.getBeneficiary() != null) {
        Beneficiary beneficiary = response.getBeneficiary();
        System.out.println("收款人 ID: " + beneficiary.getBeneficiaryId());
        System.out.println("账户类型: " + beneficiary.getAccountType());
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

### 结算状态轮询示例

```java
// 创建结算后轮询状态直到完成
String settlementId = createResponse.getId();
QuerySettlementRequest queryRequest = new QuerySettlementRequest();
queryRequest.setId(settlementId);

int maxRetries = 60;
int intervalSeconds = 10;

for (int i = 0; i < maxRetries; i++) {
    QuerySettlementResponse queryResponse = settlementService.querySettlement(queryRequest);

    if ("S".equals(queryResponse.getResult().getResultStatus())) {
        String status = queryResponse.getStatus();
        System.out.println("当前状态: " + status);

        if ("SUCCESS".equals(status)) {
            System.out.println("结算已完成: " + queryResponse.getSettledAt());
            break;
        } else if ("FAILED".equals(status)) {
            System.out.println("结算失败: " + queryResponse.getFailureCode());
            System.out.println("失败原因: " + queryResponse.getFailureMessage());
            break;
        }
        // PROCESSING 状态继续轮询
    }

    Thread.sleep(intervalSeconds * 1000L);
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 SettlementService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new SettlementService(config)` 创建实例。
