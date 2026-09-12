# List Margin Charge Records 接口接入指引

## 接口说明

分页查询远期交易（FORWARD）的保证金追缴记录，支持按交易 ID、保证金场景、状态和创建时间范围过滤，用于跟踪保证金的冻结、追加与释放。采用游标分页。

## 官方文档

- [list_margin_charge_records 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/list_margin_charge_records)
- 枚举取值（`MarginScene`、`MarginAction`、`MarginChargeStatus`、`MarginCancelReason`）见 [Deal Overview](https://docs.worldfirst.com/wfdocs/api-sdk/deal_overview)

## 请求地址

`POST /api/open/v1/fx/deals/listMarginChargeRecords`

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
| `dealId` | string | No | 按交易 ID 过滤，仅返回指定交易关联的保证金记录 |
| `scene` | string | No | 按保证金场景过滤（MarginScene 枚举，如 `INITIAL_MARGIN`） |
| `status` | string | No | 按保证金追缴状态过滤（MarginChargeStatus 枚举，如 `SUCCESS`） |
| `fromCreatedAt` | datetime | No | 创建时间范围起始（ISO 8601 格式）；最大查询跨度 31 天 |
| `toCreatedAt` | datetime | No | 创建时间范围结束（ISO 8601 格式） |

### 游标分页说明

- **首次请求**：不传 `cursor`，API 返回 `nextCursor`
- **后续请求**：将上次响应的 `nextCursor` 作为 `cursor` 传入
- **最后一页**：`nextCursor` 为 `null`，表示无更多数据
- **时间范围**：`fromCreatedAt` 与 `toCreatedAt` 的最大跨度为 31 天，超范围查询需缩小时间窗口分批拉取

### 请求示例

```json
{
  "limit": 10,
  "dealId": "DEAL20260402001",
  "scene": "INITIAL_MARGIN"
}
```

> 带游标的后续请求示例：

```json
{
  "limit": 10,
  "cursor": "eyJjcmVhdGVkQXQiOiIyMDI2LTA0LTAyVDA0OjEwOjAwIn0=",
  "dealId": "DEAL20260402001",
  "scene": "INITIAL_MARGIN"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `items` | array[MarginChargeRecord] | 保证金追缴记录列表 |
| `nextCursor` | string | 下一页游标，为空表示无更多数据 |
| `prevCursor` | string | 上一页游标 |

### MarginChargeRecord Object

| Field | Type | Description |
|-------|------|-------------|
| `id` | string | 保证金追缴记录唯一标识 |
| `dealId` | string | 关联交易 ID |
| `scene` | string | 保证金场景（MarginScene 枚举，如 `INITIAL_MARGIN`） |
| `action` | string | 保证金动作类型（MarginAction 枚举，如 `FREEZE`） |
| `status` | string | 保证金追缴状态（MarginChargeStatus 枚举） |
| `cancelReason` | string | 取消原因（MarginCancelReason 枚举），`status` 为 CANCELLED 时返回 |
| `amount` | Amount | 保证金金额 |
| `dueAt` | datetime | 保证金缴纳截止时间，适用时返回 |
| `closeOutAt` | datetime | 强制平仓触发时间，触发强平时返回 |
| `succeededAt` | datetime | 完成时间，`status` 为 SUCCESS 时返回 |
| `failedAt` | datetime | 追缴失败时间，追缴失败时返回 |
| `cancelledAt` | datetime | 取消时间，`status` 为 CANCELLED 时返回 |
| `closedOutAt` | datetime | 强制平仓完成时间，`status` 为 CLOSED_OUT 时返回 |
| `createdAt` | datetime | 记录创建时间（ISO 8601 格式） |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | 币种代码（ISO-4217） |
| `value` | integer | 金额值，最小货币单位（如 USD 25.00 → value = 2500） |

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
      "id": "MARGIN20260402001",
      "dealId": "DEAL20260402001",
      "scene": "INITIAL_MARGIN",
      "action": "FREEZE",
      "status": "SUCCESS",
      "amount": { "currency": "USD", "value": 2500 },
      "succeededAt": "2026-04-02T04:30:00Z",
      "createdAt": "2026-04-02T04:10:00Z"
    }
  ]
}
```

## 错误码

官方契约当前仅列出 `SUCCESS`，其余结果码遵循 WF 通用错误码规范（`resultStatus` 为 `F`/`U`）。
下表列出客户端 `WfErrorCode` 已定义、本接口可能命中的通用码（非契约逐条枚举，仅供排查参考）：

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查 `limit` 是否在 1-100 区间、时间参数是否为合法 ISO 8601 格式且跨度不超过 31 天 |
| `INVALID_CURSOR` | F | 游标无效或已过期 | 去掉 `cursor` 从第一页重新查询 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在 | 确保 API Key 具有 FX 交易权限 |
| `USER_NOT_EXIST` | F | 用户不存在 | 验证 API 凭据和商户账户 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，可重试 | 携带交易 ID 与时间重试，若问题持续联系技术支持 |

## 示例代码

参考 [references/foreign-exchange/deal/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
deal/java/
├── service/
│   └── DealService.java                               # 薄封装 Service，包含 listMarginChargeRecords 方法
└── model/
    ├── domain/
    │   ├── DealQuote.java                             # 交易关联的报价信息
    │   └── MarginChargeRecord.java                    # 保证金追缴记录（用于 list 响应的 items）
    ├── request/
    │   └── ListMarginChargeRecordsRequest.java        # 请求参数（limit, cursor, dealId, scene, status, fromCreatedAt, toCreatedAt）
    └── response/
        └── ListMarginChargeRecordsResponse.java       # 响应结果（result, items, nextCursor, prevCursor）
```

> `Amount`、`Result` 为通用对象，复用 `model/domain`、`model/response` 包下的已有定义。

## 集成使用方式

DealService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `ListMarginChargeRecordsRequest` 设置查询条件（首次请求不传 `cursor`）
2. 调用 `DealService.listMarginChargeRecords(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理保证金记录
4. 若 `nextCursor` 不为 `null`，将其作为 `cursor` 传入下一次请求，重复步骤 1-3

### 业务代码示例

```java
// 构造请求
ListMarginChargeRecordsRequest request = new ListMarginChargeRecordsRequest();
request.setLimit(10);
request.setDealId("DEAL20260402001");
request.setScene("INITIAL_MARGIN");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
ListMarginChargeRecordsResponse response = dealService.listMarginChargeRecords(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    for (MarginChargeRecord record : response.getItems()) {
        System.out.println("记录 ID: " + record.getId());
        System.out.println("关联交易: " + record.getDealId());
        System.out.println("场景/动作: " + record.getScene() + " / " + record.getAction());
        System.out.println("状态: " + record.getStatus());
        System.out.println("金额: " + record.getAmount().getValue()
            + " " + record.getAmount().getCurrency());
        System.out.println("创建时间: " + record.getCreatedAt());
    }

    // 游标分页：继续查询下一页
    if (response.getNextCursor() != null) {
        request.setCursor(response.getNextCursor());
        // 再次调用 dealService.listMarginChargeRecords(request) 获取下一页数据
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 游标分页完整遍历示例

```java
List<MarginChargeRecord> allRecords = new ArrayList<>();
String cursor = null;

do {
    ListMarginChargeRecordsRequest request = new ListMarginChargeRecordsRequest();
    request.setLimit(100);
    request.setDealId("DEAL20260402001");
    request.setFromCreatedAt("2026-03-01T00:00:00+08:00");
    request.setToCreatedAt("2026-03-31T23:59:59+08:00");
    if (cursor != null) {
        request.setCursor(cursor);
    }

    // 调用 Service（内部已强制验签，验签失败抛 WfException）
    ListMarginChargeRecordsResponse response = dealService.listMarginChargeRecords(request);
    if (!"S".equals(response.getResult().getResultStatus())) {
        throw new RuntimeException("查询失败: " + response.getResult().getResultMessage());
    }

    allRecords.addAll(response.getItems());
    cursor = response.getNextCursor();
} while (cursor != null);

System.out.println("共查询到 " + allRecords.size() + " 条保证金追缴记录");
```

### 保证金应缴/强平巡检示例

```java
// 按状态筛选未完成的追缴，识别临近截止或已被强平的记录
ListMarginChargeRecordsRequest request = new ListMarginChargeRecordsRequest();
request.setLimit(100);
request.setFromCreatedAt("2026-03-01T00:00:00+08:00");
request.setToCreatedAt("2026-03-31T23:59:59+08:00");

ListMarginChargeRecordsResponse response = dealService.listMarginChargeRecords(request);

if ("S".equals(response.getResult().getResultStatus())) {
    for (MarginChargeRecord record : response.getItems()) {
        if ("SUCCESS".equals(record.getStatus())) {
            // 追缴完成，仅做留痕
            continue;
        }
        if ("CANCELLED".equals(record.getStatus())) {
            System.out.println("追缴已取消: " + record.getId()
                + "，原因: " + record.getCancelReason()
                + "，取消时间: " + record.getCancelledAt());
            continue;
        }
        if ("CLOSED_OUT".equals(record.getStatus())) {
            System.out.println("已强制平仓: " + record.getDealId()
                + "，触发时间: " + record.getCloseOutAt()
                + "，完成时间: " + record.getClosedOutAt());
            continue;
        }
        // 待处理记录：按 dueAt 提醒补缴保证金
        System.out.println("待处理追缴: " + record.getId()
            + "，场景: " + record.getScene()
            + "，应缴截止: " + record.getDueAt());
    }
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 DealService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new DealService(config)` 创建实例。
