# Query a Budget 接口接入指引

## 接口说明

按预算账户 ID 查询其详情与**多币种余额**。`balanceAmounts` 每个币种至多一条记录；`status` 为 `FAILED` 时余额列表为空。常用于入金/出金前后核对预算账户可用资金。

## 官方文档

- [query_a_budget 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_budget)
- 枚举取值（`BudgetStatus`）见本模块 [README](../README.md)

## 请求地址

`POST /api/open/v1/issuing/budgets/query`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-08-08T08:00:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | 预算账户唯一标识，最大 64 字符；取自 `create_a_budget` 返回的 `id` |

### 请求示例

```json
{
  "id": "BUDGET_20260401100101****"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 预算账户唯一标识，最大 64 字符 |
| `name` | string | 预算账户名称 |
| `status` | string | 预算账户当前状态：`ACTIVE`（已激活可用，非终态）、`FAILED`（创建失败，终态） |
| `createdAt` | datetime | 预算账户创建时间，ISO 8601 扩展格式 |
| `balanceAmounts` | array[Amount] | 多币种余额列表，每个币种至多一条；`status` 为 `FAILED` 时返回空列表 |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | ISO 4217 三字母货币代码；离岸人民币使用 `CNH` |
| `value` | long | 以**最小货币单位**表示的整数金额（如 100.50 USD → `10050`；200 JPY → `200`） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "BUDGET_20260808100101****",
  "name": "Marketing Cards Q3",
  "status": "ACTIVE",
  "createdAt": "2026-08-08T10:56:52Z",
  "balanceAmounts": [
    { "currency": "USD", "value": 100 }
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | - |
| `BUDGET_NOT_FOUND` | F | 预算账户不存在 | 核对 `id` 是否正确，可用 `list_budgets` 获取有效 ID |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用正确的用户信息重试 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 | 重试前确认 `account-id` / `access-token` 授权关系有效 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败 | 联系万里汇支持确认合约状态后重试 |
| `PARAM_ILLEGAL` | F | 参数非法 | 核对 `id` 是否符合本规范 |
| `PROCESS_FAIL` | F | 通用业务失败 | **不要重试**，联系万里汇支持 |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 稍后重试；若问题持续联系万里汇支持 |

## 示例代码

参考 [references/issuing/budgets/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
budgets/java/
├── service/
│   └── BudgetService.java                             # 薄封装 Service，包含 queryBudget 方法
└── model/
    ├── request/
    │   └── QueryBudgetRequest.java                    # 请求参数（id）
    └── response/
        └── QueryBudgetResponse.java                   # 响应结果（result, id, name, status, createdAt, balanceAmounts）
```

> `Result` 为通用对象，复用 `model/response` 包下的已有定义；`Amount` 复用 `model/domain` 下的通用金额对象。

## 集成使用方式

BudgetService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryBudgetRequest`，设置 `id`
2. 调用 `BudgetService.queryBudget(request)` 获取响应（内部已强制验签）
3. 检查 `result.resultStatus` 是否为 `S`，读取 `status` 与 `balanceAmounts`

### 业务代码示例

```java
QueryBudgetRequest request = new QueryBudgetRequest();
request.setId("BUDGET_20260401100101****");

QueryBudgetResponse response = budgetService.queryBudget(request);

if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("状态: " + response.getStatus());
    for (Amount amt : response.getBalanceAmounts()) {
        // value 为最小货币单位，展示时需按币种小数位换算
        System.out.println("余额: " + amt.getCurrency() + " " + amt.getValue());
    }
} else if ("BUDGET_NOT_FOUND".equals(response.getResult().getResultCode())) {
    System.out.println("预算账户不存在，请用 list_budgets 核对 id");
} else {
    System.err.println("查询失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BudgetService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BudgetService(config)` 创建实例。
