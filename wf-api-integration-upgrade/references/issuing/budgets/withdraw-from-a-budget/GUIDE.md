# Withdraw from a Budget 接口接入指引

## 接口说明

将预算账户资金退回余额账户（出金）。仅 `ACTIVE` 状态的预算账户可出金，否则返回 `BUDGET_ACCOUNT_STATUS_INACTIVE`；预算账户可用余额不足时返回 `BALANCE_NOT_ENOUGH`。出金不指定目标账户类型，资金退回余额账户。

> **资金变动接口**：`requestId` 必填且在 `partnerId` 内唯一，重试必须复用同一取值以免重复出金；结果不确定（`U`）时稍后用原 `requestId` 和完全相同的业务参数重试，最多重试 3 次（不含首次请求）；仍未知则保留未知结果并联系 WorldFirst 技术支持。

## 官方文档

- [withdraw_from_a_budget 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/withdraw_from_a_budget)
- 枚举取值（`BudgetStatus`）见本模块 [README](../README.md)

## 请求地址

`POST /api/open/v1/issuing/budgets/withdraw`

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
| `requestId` | string | Yes | 幂等键，最大 64 字符，需在 `partnerId` 内唯一；重试须复用同一取值以免重复出金 |
| `id` | string | Yes | 预算账户唯一标识，最大 64 字符；取自 `create_a_budget` 返回的 `id` |
| `amount` | Amount | Yes | 出金金额，`value` 须大于 0 |

### Amount Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `currency` | string | Yes | ISO 4217 三字母货币代码；离岸人民币使用 `CNH`。须与被出金预算账户的币种一致 |
| `value` | long | Yes | 以**最小货币单位**表示的整数金额，须大于 0（如 200.00 USD → `20000`） |

### 请求示例

```json
{
  "requestId": "req_withdraw_20260808****",
  "id": "BUDGET_20260401100101****",
  "amount": {
    "currency": "USD",
    "value": 20000
  }
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 预算账户唯一标识，最大 64 字符，回显请求值 |
| `amount` | Amount | 出金金额，回显请求值 |
| `status` | string | 出金操作状态，如 `SUCCESS` |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "BUDGET_20260401100101****",
  "amount": {
    "currency": "USD",
    "value": 20000
  },
  "status": "SUCCESS"
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 出金受理成功 | 核对返回的 `status` 与预算账户余额 |
| `BUDGET_NOT_FOUND` | F | 预算账户不存在 | 核对 `id` 是否正确 |
| `BUDGET_ACCOUNT_STATUS_INACTIVE` | F | 预算账户非 ACTIVE 状态 | 仅 `ACTIVE` 预算账户可出金，先用 `query_a_budget` 确认状态 |
| `BALANCE_NOT_ENOUGH` | F | 余额不足 | 确认预算账户可用余额充足（含未被卡片消费占用的部分） |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用正确的用户信息重试 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 | 重试前确认 `account-id` / `access-token` 授权关系有效 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败 | 联系万里汇支持确认合约状态后重试 |
| `REPEAT_REQ_INCONSISTENT` | F | 相同 `requestId` 参数不一致 | 保持参数完全一致，或改用新的 `requestId` |
| `PARAM_ILLEGAL` | F | 参数非法 | 核对 `amount.value`（>0、最小货币单位）与币种 |
| `PROCESS_FAIL` | F | 通用业务失败 | **不要重试**，联系万里汇支持 |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 稍后用原 `requestId` + 相同参数重试，最多重试 3 次；仍未知则携带 `requestId` 和时间戳联系 WorldFirst 技术支持，不换新幂等键 |

## 示例代码

参考 [references/issuing/budgets/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
budgets/java/
├── service/
│   └── BudgetService.java                             # 薄封装 Service，包含 withdrawFromBudget 方法
└── model/
    ├── request/
    │   └── WithdrawFromBudgetRequest.java             # 请求参数（requestId, id, amount）
    └── response/
        └── WithdrawFromBudgetResponse.java            # 响应结果（result, id, amount, status）
```

> `Amount` 复用 `model/domain` 下的通用金额对象；`Result` 复用 `model/response` 下的已有定义。

## 集成使用方式

BudgetService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 通过 `query_a_budget` 确认预算账户为 `ACTIVE`，且对应币种余额充足
2. 生成唯一 `requestId`，构造 `WithdrawFromBudgetRequest`（`amount.value` 用最小货币单位）
3. 调用 `BudgetService.withdrawFromBudget(request)` 获取响应（内部已强制验签）
4. 检查 `result.resultStatus`：`S` 表示成功；`F` 按错误码语义处理（`PROCESS_FAIL` 不重试）；`resultStatus=U` 表示结果未知，不能判定成功或失败；稍后用原 `requestId` + 完全相同的业务参数重试，最多重试 3 次（不含首次请求），仍未知则保留未知结果并携带 `requestId` 和时间戳联系 WorldFirst 技术支持。**禁止为重试同一笔操作生成新 `requestId`**。

### 业务代码示例

```java
Amount amount = new Amount("USD", 20000L);           // 200.00 USD，最小货币单位

WithdrawFromBudgetRequest request = new WithdrawFromBudgetRequest();
request.setRequestId("req_withdraw_20260808_001");
request.setId("BUDGET_20260401100101****");
request.setAmount(amount);

WithdrawFromBudgetResponse response = budgetService.withdrawFromBudget(request);

if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("出金状态: " + response.getStatus());
} else if ("BALANCE_NOT_ENOUGH".equals(response.getResult().getResultCode())) {
    System.out.println("预算账户可用余额不足，先查询确认实际余额");
} else if ("BUDGET_ACCOUNT_STATUS_INACTIVE".equals(response.getResult().getResultCode())) {
    System.out.println("预算账户非 ACTIVE，不可出金");
} else {
    System.err.println("出金失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 结果未知时的处置

依据：[官方 Result 通用错误规则](https://docs.worldfirst.com/wfdocs/api-sdk/result)及本接口错误表。
`BudgetService` 仅封装单次调用；调用方对 `UNKNOWN_EXCEPTION / U` 安排最多 3 次延迟重试，复用原 requestId 和业务参数。
下面使用阻塞等待演示调用顺序，1 秒仅为示例间隔，并非官网规定；应用可使用调度器实现。

```java
// request 已包含本笔操作的唯一 requestId；后续重试不得修改它或其他业务参数。
String requestId = request.getRequestId();
String firstAttemptAt = java.time.OffsetDateTime.now().toString();
WithdrawFromBudgetResponse response = budgetService.withdrawFromBudget(request);
for (int retry = 0; retry < 3
        && "U".equals(response.getResult().getResultStatus())
        && "UNKNOWN_EXCEPTION".equals(response.getResult().getResultCode()); retry++) {
    try {
        Thread.sleep(1000L); // 示例间隔，可改为应用调度器的延迟策略
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        break; // 中断后停止重试，保留未知结果
    }
    response = budgetService.withdrawFromBudget(request);
}
if ("U".equals(response.getResult().getResultStatus())) {
    System.err.println("结果仍未知，请联系 WorldFirst 技术支持。requestId="
        + requestId + ", timestamp=" + firstAttemptAt);
}
// S/F 按本接口结果处理；PROCESS_FAIL 不重试，U 不得当作成功或确定失败。
```

集成建议：查询预算余额仅用于辅助核对，不能仅凭余额变化确认本笔入金/出金成功，也不应将其设为重试前置条件。

> **Spring Boot 项目**：通过 `@Autowired` 注入 BudgetService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BudgetService(config)` 创建实例。
