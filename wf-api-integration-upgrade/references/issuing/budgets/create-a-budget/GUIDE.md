# Create a Budget 接口接入指引

## 接口说明

创建预算账户，用于划拨并持有发卡消费资金。单个商户账户**最多持有 3 个预算账户**，超出返回 `BUDGET_ACCOUNT_NUMBER_OVER_LIMIT`；预算账户一经创建**不可关闭**。创建成功后 `status` 为 `ACTIVE`，返回的 `id` 用于后续查询、入金、出金。

## 官方文档

- [create_a_budget 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_budget)
- 枚举取值（`BudgetStatus`、`BalanceType`）见本模块 [README](../README.md)

## 请求地址

`POST /api/open/v1/issuing/budgets/create`

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
| `requestId` | string | Yes | 幂等键，最大 64 字符；每次创建请求需唯一（建议 UUID），重试须复用同一取值以免重复创建 |
| `name` | string | No | 预算账户名称，最大 50 字符；不传时由系统自动分配名称 |

### 请求示例

```json
{
  "requestId": "req_budget_20260808****",
  "name": "Marketing Cards Q3"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 预算账户唯一标识，最大 64 字符；用于后续 query / deposit / withdraw |
| `name` | string | 预算账户名称，回显请求值；请求未传时为系统自动分配的名称 |
| `status` | string | 预算账户当前状态：`ACTIVE`（已激活可用，非终态）、`FAILED`（创建失败，终态） |
| `createdAt` | datetime | 预算账户创建时间，ISO 8601 扩展格式（如 `2026-08-08T12:46:26Z`） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "BUDGET_20260401100101****",
  "name": "Marketing Cards Q3",
  "status": "ACTIVE",
  "createdAt": "2026-08-08T12:46:26Z"
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 创建成功 | 预算账户已创建并可用 |
| `BUDGET_ACCOUNT_NUMBER_OVER_LIMIT` | F | 预算账户数量超限 | 单个账户最多 3 个预算账户，改用 `list_budgets` 核对并复用已有账户 |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用正确的用户信息重试 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 | 重试前确认 `account-id` / `access-token` 授权关系有效 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败 | 联系万里汇支持确认合约状态后重试 |
| `REPEAT_REQ_INCONSISTENT` | F | 相同 `requestId` 参数不一致 | 该 `requestId` 已被不同参数的请求占用，保持参数完全一致或改用新的 `requestId` |
| `PARAM_ILLEGAL` | F | 参数非法 | 核对 `name` 长度（≤50）等字段是否符合本规范 |
| `PROCESS_FAIL` | F | 通用业务失败 | **不要重试**，联系万里汇支持 |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 使用相同 `requestId` 稍后重试；若问题持续联系万里汇支持 |

## 示例代码

参考 [references/issuing/budgets/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
budgets/java/
├── service/
│   └── BudgetService.java                             # 薄封装 Service，包含 createBudget 方法
└── model/
    ├── request/
    │   └── CreateBudgetRequest.java                   # 请求参数（requestId, name）
    └── response/
        └── CreateBudgetResponse.java                  # 响应结果（result, id, name, status, createdAt）
```

> `Result` 为通用对象，复用 `model/response` 包下的已有定义。

## 集成使用方式

BudgetService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 生成唯一 `requestId`，构造 `CreateBudgetRequest`（`name` 可选）
2. 调用 `BudgetService.createBudget(request)` 获取响应（内部已强制验签）
3. 检查 `result.resultStatus` 是否为 `S`，保存返回的 `id` 用于后续入金与查询
4. 若不确定是否创建成功，先用 `list_budgets` 核对已有账户数量与列表，再决定是否重试

### 业务代码示例

```java
CreateBudgetRequest request = new CreateBudgetRequest();
request.setRequestId("req_budget_20260808_001");
request.setName("Marketing Cards Q3");

CreateBudgetResponse response = budgetService.createBudget(request);

if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("预算账户 ID: " + response.getId());
    System.out.println("状态: " + response.getStatus());        // ACTIVE
    System.out.println("创建时间: " + response.getCreatedAt());
} else if ("BUDGET_ACCOUNT_NUMBER_OVER_LIMIT".equals(response.getResult().getResultCode())) {
    System.out.println("已有 3 个预算账户，请复用已有账户");
} else if ("PROCESS_FAIL".equals(response.getResult().getResultCode())) {
    System.out.println("业务失败，不要重试，联系万里汇支持");
} else {
    System.err.println("创建失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 幂等重试示例

```java
// 网络超时等场景下，使用同一 requestId 且参数完全一致重试，不会产生重复预算账户
String requestId = "req_budget_20260808_001";
request.setRequestId(requestId);

CreateBudgetResponse response = budgetService.createBudget(request);

if ("U".equals(response.getResult().getResultStatus())) {
    // 结果未知：先用相同 requestId + 相同参数重试，再用 list_budgets 确认
    response = budgetService.createBudget(request);
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BudgetService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BudgetService(config)` 创建实例。
