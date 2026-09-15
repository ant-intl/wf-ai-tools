# Query Trading Calendar 接口接入指引

## 接口说明

查询指定货币对和交易类型在给定日期范围内的交易日历。返回可创建交易或可完成结算的可用日期列表，具体取决于请求的日历类型。

## 官方文档

- [query_trading_calendar 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_trading_calendar)

## 请求地址

`POST /api/open/v1/fx/references/queryCalendar`

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
| `dealType` | string | Yes | 交易类型：`SPOT`、`UNFUNDED_SPOT`、`FORWARD` |
| `startDate` | string | Yes | 查询起始日期，格式 `YYYY-MM-DD`（UTC） |
| `endDate` | string | Yes | 查询结束日期，格式 `YYYY-MM-DD`（UTC） |
| `buyCurrency` | string | Yes | 买入货币，ISO 4217 三字母代码（如 `USD`） |
| `sellCurrency` | string | Yes | 卖出货币，ISO 4217 三字母代码（如 `HKD`） |
| `calendarType` | string | No | 日历类型：`DEAL_CALENDAR`（默认）或 `SETTLEMENT_CALENDAR` |

### 请求示例

```json
{
  "dealType": "FORWARD",
  "startDate": "2026-04-02",
  "endDate": "2026-04-30",
  "buyCurrency": "USD",
  "sellCurrency": "HKD"
}
```

## 响应参数

| Field | Type          | Description |
|-------|---------------|-------------|
| `result` | Result        | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `availableDates` | array[string] | 可用日期列表，`YYYY-MM-DD` 格式字符串 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "availableDates": [
    "2026-04-03", "2026-04-06", "2026-04-07", "2026-04-08", "2026-04-09",
    "2026-04-10", "2026-04-13", "2026-04-14", "2026-04-15", "2026-04-16"
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 验证所有必填参数已提供且格式正确，`startDate`/`endDate` 为 YYYY-MM-DD，货币代码为有效 ISO 4217 |
| `USER_NOT_EXIST` | F | 用户不存在 | 验证商户账户有效且已完成 KYC |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在 | 检查 `Client-Id` 头及授权关系 |
| `FX_DEAL_DATE_RANGE_NOT_SUPPORTED` | F | 日期范围不支持 | 调整日期范围至该交易类型允许的交易窗口内 |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 联系 WorldFirst 技术支持 |

## 示例代码

参考 [references/foreign-exchange/reference/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
reference/java/
├── service/
│   └── ReferenceService.java                          # 薄封装 Service，包含 queryCalendar 方法
└── model/
    ├── request/
    │   └── QueryCalendarRequest.java                  # 请求参数（dealType, startDate, endDate, buyCurrency, sellCurrency, calendarType）
    └── response/
        └── QueryCalendarResponse.java                 # 响应结果（result, availableDates）
```

## 集成使用方式

ReferenceService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryCalendarRequest` 设置必填字段（`dealType`、`startDate`、`endDate`、`buyCurrency`、`sellCurrency`）及可选 `calendarType`
2. 调用 `ReferenceService.queryCalendar(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理 `availableDates` 列表

### 业务代码示例

```java
// 构造请求
QueryCalendarRequest request = new QueryCalendarRequest();
request.setDealType("FORWARD");
request.setStartDate("2026-04-02");
request.setEndDate("2026-04-30");
request.setBuyCurrency("USD");
request.setSellCurrency("HKD");
// 可选：指定日历类型，默认为 DEAL_CALENDAR
// request.setCalendarType("SETTLEMENT_CALENDAR");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryCalendarResponse response = referenceService.queryCalendar(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    List<String> availableDates = response.getAvailableDates();
    System.out.println("可用日期数量: " + availableDates.size());
    for (String date : availableDates) {
        System.out.println("  " + date);
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

### 创建交易前预检可用日期

```java
// 在创建 FORWARD 交易前，先查询可用交易日
QueryCalendarRequest calendarRequest = new QueryCalendarRequest();
calendarRequest.setDealType("FORWARD");
calendarRequest.setStartDate("2026-04-02");
calendarRequest.setEndDate("2026-04-30");
calendarRequest.setBuyCurrency("USD");
calendarRequest.setSellCurrency("HKD");
calendarRequest.setCalendarType("DEAL_CALENDAR");

QueryCalendarResponse calendarResponse = referenceService.queryCalendar(calendarRequest);

if ("S".equals(calendarResponse.getResult().getResultStatus())) {
    List<String> dates = calendarResponse.getAvailableDates();
    if (dates.isEmpty()) {
        System.out.println("该日期范围内无可用交易日");
    } else {
        // 选择第一个可用日期创建交易
        String targetDate = dates.get(0);
        System.out.println("选择交易日: " + targetDate);
        // ... 继续创建交易逻辑
    }
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 ReferenceService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new ReferenceService(config)` 创建实例。
