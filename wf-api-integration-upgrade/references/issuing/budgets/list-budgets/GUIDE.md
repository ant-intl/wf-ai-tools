# List Budgets 接口接入指引

## 接口说明

列出当前账户下全部预算账户，含状态与创建时间。**本接口无请求参数、不分页**（响应无游标字段），最多返回 3 条记录（与「单账户最多 3 个预算账户」的上限一致）；无数据时返回空列表。响应中的 `items` 不含余额字段，需要余额请用 `query_a_budget`。

## 官方文档

- [list_budgets 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/list_budgets)
- 枚举取值（`BudgetStatus`）见本模块 [README](../README.md)

## 请求地址

`POST /api/open/v1/issuing/budgets/list`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-08-08T08:00:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

无。请求体为 `{}`。

### 请求示例

```json
{}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `items` | array[BudgetRecord] | 预算账户列表；无数据时返回空列表 |

### BudgetRecord Object

| Field | Type | Description |
|-------|------|-------------|
| `id` | string | 预算账户唯一标识，最大 64 字符 |
| `name` | string | 预算账户名称 |
| `status` | string | 预算账户当前状态：`ACTIVE`（已激活可用，非终态）、`FAILED`（创建失败，终态） |
| `createdAt` | datetime | 预算账户创建时间，ISO 8601 扩展格式 |

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
      "id": "BUDGET_20260401100101****",
      "name": "budget_name_test",
      "status": "ACTIVE",
      "createdAt": "2026-07-21T12:46:26Z"
    }
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | - |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用正确的用户信息重试 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 | 重试前确认 `account-id` / `access-token` 授权关系有效 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败 | 联系万里汇支持确认合约状态后重试 |
| `PROCESS_FAIL` | F | 通用业务失败 | **不要重试**，联系万里汇支持 |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 稍后重试；若问题持续联系万里汇支持 |

## 示例代码

参考 [references/issuing/budgets/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
budgets/java/
├── service/
│   └── BudgetService.java                             # 薄封装 Service，包含 listBudgets 方法
└── model/
    ├── domain/
    │   └── BudgetRecord.java                          # 预算账户记录（id, name, status, createdAt）
    ├── request/
    │   └── ListBudgetsRequest.java                    # 无业务字段，序列化为 {}
    └── response/
        └── ListBudgetsResponse.java                   # 响应结果（result, items）
```

> `ListBudgetsRequest` 无业务字段，保留是为了与其他接口保持统一的调用形态（均传入 Request 对象），并便于后续新增过滤条件时不破坏调用方代码。

## 集成使用方式

BudgetService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造空的 `ListBudgetsRequest`（无需设置任何字段）
2. 调用 `BudgetService.listBudgets(request)` 获取响应（内部已强制验签）
3. 检查 `result.resultStatus` 是否为 `S`，遍历 `items`

### 业务代码示例

```java
ListBudgetsResponse response = budgetService.listBudgets(new ListBudgetsRequest());

if ("S".equals(response.getResult().getResultStatus())) {
    for (BudgetRecord budget : response.getItems()) {
        System.out.println("预算账户: " + budget.getId()
            + " | " + budget.getName()
            + " | " + budget.getStatus());
    }
    // 本接口不分页，items 即全部结果
} else {
    System.err.println("查询失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 典型用法：创建前先核对数量

```java
// create_a_budget 前先用列表接口确认是否已达 3 个上限，避免直接撞 BUDGET_ACCOUNT_NUMBER_OVER_LIMIT
ListBudgetsResponse list = budgetService.listBudgets(new ListBudgetsRequest());
if ("S".equals(list.getResult().getResultStatus()) && list.getItems().size() >= 3) {
    System.out.println("预算账户已达上限，复用: " + list.getItems().get(0).getId());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BudgetService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BudgetService(config)` 创建实例。
