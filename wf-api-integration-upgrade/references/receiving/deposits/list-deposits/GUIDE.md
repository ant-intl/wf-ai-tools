# List Deposits 接口接入指引

## 接口说明

分页查询存款记录列表，支持按状态、类型、币种和创建时间范围过滤。采用游标分页，适用于对账和审计场景。

## 官方文档

- [list_deposits 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/list_deposits)

## 请求地址

`POST /api/open/v1/deposits/list`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-05-22T10:15:30+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `limit` | integer | Yes | 每页记录数，取值范围 1-100 |
| `cursor` | string | No | 分页游标；首次请求省略，后续请求传入上次响应的 `nextCursor` 或 `prevCursor`；游标有效期 7 天 |
| `status` | string | No | 按生命周期状态过滤（DepositStatus 枚举） |
| `type` | string | No | 按业务类型过滤（DepositType 枚举） |
| `currency` | string | No | 按币种过滤（ISO-4217 三字母代码） |
| `fromCreatedAt` | datetime | No | 创建时间范围起始（ISO 8601 格式，包含），必须与 `toCreatedAt` 配合使用 |
| `toCreatedAt` | datetime | No | 创建时间范围结束（ISO 8601 格式，包含），必须与 `fromCreatedAt` 配合使用 |

### 游标分页说明

- **首次请求**：不传 `cursor`，API 返回 `nextCursor`
- **后续请求**：将上次响应的 `nextCursor` 作为 `cursor` 传入
- **最后一页**：`nextCursor` 为 `null`，表示无更多数据
- **游标有效期**：7 天，过期后需重新从第一页开始查询
- **排序**：返回记录按 `createdAt` 降序排列

### 请求示例

```json
{
  "limit": 20,
  "status": "SUCCESS",
  "fromCreatedAt": "2026-05-01T00:00:00+08:00",
  "toCreatedAt": "2026-05-22T23:59:59+08:00",
  "currency": "USD"
}
```

> 带游标的后续请求示例：

```json
{
  "limit": 20,
  "cursor": "eyJjcmVhdGVkQXQiOiIyMDI2LTA1LTIyVDA5OjAwOjAwIn0=",
  "status": "SUCCESS",
  "fromCreatedAt": "2026-05-01T00:00:00+08:00",
  "toCreatedAt": "2026-05-22T23:59:59+08:00",
  "currency": "USD"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `items` | array[DepositRecord] | 存款记录列表，按 `createdAt` 降序 |
| `nextCursor` | string | 下一页游标，为空表示无更多数据 |
| `prevCursor` | string | 上一页游标，第一页为 `null` |

### DepositRecord Object

| Field | Type | Description |
|-------|------|-------------|
| `id` | string | 存款唯一标识 |
| `accountId` | string | 收款商户的 WorldFirst 账户 ID |
| `type` | string | 存款业务类型（DepositType 枚举） |
| `status` | string | 存款生命周期状态（DepositStatus 枚举） |
| `sourceAmount` | Amount | 付款方发送的原始金额 |
| `amount` | Amount | 实际入账金额，status 为 SUCCESS/PARTIAL_REFUNDED/REFUNDED 时返回 |
| `refundedAmount` | Amount | 累计退款金额，status 为 PARTIAL_REFUNDED/REFUNDED 时返回 |
| `feeAmount` | Amount | 手续费金额，status 为 SUCCESS/PARTIAL_REFUNDED/REFUNDED 时返回 |
| `quote` | Quote | 跨币种汇率详情，仅跨币种转换完成后返回 |
| `initiatingPaymentMethod` | InitiatingPaymentMethod | 付款方支付方式详情，当付款方信息可识别时返回 |
| `receiveMethod` | ReceiveMethod | 收款方式 |
| `reference` | string | 付款方转账备注，当付款方提供了备注时返回 |
| `createdAt` | datetime | 存款记录创建时间（ISO 8601 格式） |
| `succeededAt` | datetime | 资金入账时间，status 为 SUCCESS/PARTIAL_REFUNDED 时返回 |
| `refundedAllAt` | datetime | 全额退款时间，status 为 REFUNDED 时返回 |

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
    "resultStatus": "S",
    "resultMessage": "Query succeeded."
  },
  "items": [
    {
      "id": "dep_2026051901HJK7N",
      "accountId": "acct_50045123",
      "type": "THIRD_PARTY",
      "status": "SUCCESS",
      "sourceAmount": { "value": 98000, "currency": "USD" },
      "amount": { "value": 96000, "currency": "USD" },
      "feeAmount": { "value": 2000, "currency": "USD" },
      "initiatingPaymentMethod": {
        "paymentAccountType": "BANK_ACCOUNT",
        "bankDetails": {
          "accountHolderName": { "fullName": "Wei Zhang", "firstName": "Wei", "lastName": "Zhang" }
        }
      },
      "receiveMethod": {
        "type": "VA",
        "accountNumber": "8400********5678"
      },
      "createdAt": "2026-05-22T10:15:30+08:00",
      "succeededAt": "2026-05-22T14:20:00+08:00"
    }
  ],
  "nextCursor": "eyJjcmVhdGVkQXQiOiIyMDI2LTA1LTIyVDA5OjAwOjAwIn0="
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 查询成功 |
| `INVALID_PARAMETER` | F | 参数非法，不可重试 |
| `INVALID_CURSOR` | F | 游标无效或已过期，不可重试 |

## 示例代码

参考 [references/receiving/deposits/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
deposits/java/
├── service/
│   └── DepositService.java                          # 薄封装 Service，包含 listDeposits 方法
└── model/
    ├── domain/
    │   ├── Quote.java                               # 汇率信息（currencyPair, clientRate）
    │   ├── BankDetail.java                          # 银行账户详情
    │   ├── WalletDetail.java                        # 数字钱包详情
    │   ├── InitiatingPaymentMethod.java             # 付款方支付方式
    │   ├── ReceiveMethod.java                       # 收款方式
    │   └── DepositRecord.java                       # 存款记录（用于 list 响应的 items）
    ├── request/
    │   └── ListDepositsRequest.java                 # 请求参数（limit, cursor, status, type, currency, fromCreatedAt, toCreatedAt）
    └── response/
        └── ListDepositsResponse.java                # 响应结果（result, items, nextCursor, prevCursor）
```

## 集成使用方式

DepositService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `ListDepositsRequest` 设置查询条件（首次请求不传 `cursor`）
2. 调用 `DepositService.listDeposits(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理存款记录
4. 若 `nextCursor` 不为 `null`，将其作为 `cursor` 传入下一次请求，重复步骤 1-3

### 业务代码示例

```java
// 构造请求
ListDepositsRequest request = new ListDepositsRequest();
request.setLimit(20);
request.setStatus("SUCCESS");
request.setCurrency("USD");
request.setFromCreatedAt("2026-05-01T00:00:00+08:00");
request.setToCreatedAt("2026-05-22T23:59:59+08:00");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
ListDepositsResponse response = depositService.listDeposits(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    // 处理存款记录
    for (DepositRecord record : response.getItems()) {
        System.out.println("存款 ID: " + record.getId());
        System.out.println("状态: " + record.getStatus());
        System.out.println("入账金额: " + record.getAmount().getValue() + " " + record.getAmount().getCurrency());
        System.out.println("创建时间: " + record.getCreatedAt());
    }

    // 游标分页：继续查询下一页
    if (response.getNextCursor() != null) {
        request.setCursor(response.getNextCursor());
        // 再次调用 depositService.listDeposits(request) 获取下一页数据
    }
} else {
    // 处理错误
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

### 游标分页完整遍历示例

```java
List<DepositRecord> allDeposits = new ArrayList<>();
String cursor = null;

do {
    ListDepositsRequest request = new ListDepositsRequest();
    request.setLimit(100);
    request.setStatus("SUCCESS");
    request.setCurrency("USD");
    request.setFromCreatedAt("2026-05-01T00:00:00+08:00");
    request.setToCreatedAt("2026-05-22T23:59:59+08:00");
    if (cursor != null) {
        request.setCursor(cursor);
    }

    // 调用 Service（内部已强制验签，验签失败抛 WfException）
    ListDepositsResponse response = depositService.listDeposits(request);
    if (!"S".equals(response.getResult().getResultStatus())) {
        throw new RuntimeException("查询失败: " + response.getResult().getResultMessage());
    }

    allDeposits.addAll(response.getItems());
    cursor = response.getNextCursor();
} while (cursor != null);

System.out.println("共查询到 " + allDeposits.size() + " 条存款记录");
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 DepositService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new DepositService(config)` 创建实例。
