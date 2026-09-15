# 发卡预算账户（Budgets）模块

预算账户（Budget Account）用于划拨并管控卡片消费资金。本模块覆盖预算账户的创建、详情与多币种余额查询、账户列表、入金（充值）、出金（提现）以及每日余额与资金流水查询。

> 单个商户账户**最多持有 3 个预算账户**；预算账户一经创建**不可关闭**。仅 `ACTIVE` 状态的预算账户可入金/出金。入金出金为资金变动操作，必须携带唯一 `requestId`。

## 官方文档

- [create_a_budget](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_budget) | [query_a_budget](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_budget) | [list_budgets](https://docs.worldfirst.com/wfdocs/api-sdk/list_budgets)
- [deposit_to_a_budget](https://docs.worldfirst.com/wfdocs/api-sdk/deposit_to_a_budget) | [withdraw_from_a_budget](https://docs.worldfirst.com/wfdocs/api-sdk/withdraw_from_a_budget) | [list_daily_balances](https://docs.worldfirst.com/wfdocs/api-sdk/list_daily_balances)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 创建预算账户 | `create-a-budget/GUIDE.md` | 创建预算账户以划拨并持有发卡消费资金 | `POST /api/open/v1/issuing/budgets/create` |
| 查询预算账户 | `query-a-budget/GUIDE.md` | 按 ID 查询预算账户详情与多币种余额 | `POST /api/open/v1/issuing/budgets/query` |
| 查询预算账户列表 | `list-budgets/GUIDE.md` | 列出当前账户下全部预算账户（无参数、不分页） | `POST /api/open/v1/issuing/budgets/list` |
| 预算账户入金 | `deposit-to-a-budget/GUIDE.md` | 从源余额账户向预算账户划拨资金 | `POST /api/open/v1/issuing/budgets/deposit` |
| 预算账户出金 | `withdraw-from-a-budget/GUIDE.md` | 将预算账户资金退回余额账户 | `POST /api/open/v1/issuing/budgets/withdraw` |
| 查询每日余额 | `list-daily-balances/GUIDE.md` | 按日期区间查询日终余额与当日资金流入/流出（游标分页） | `POST /api/open/v1/issuing/budgets/listDailyBalance` |

## BudgetService 说明

`BudgetService` 是发卡预算账户模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `CreateBudgetResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `createBudget(CreateBudgetRequest)` | `CreateBudgetRequest` | `CreateBudgetResponse` | 创建预算账户（幂等键 `requestId`） |
| `queryBudget(QueryBudgetRequest)` | `QueryBudgetRequest` | `QueryBudgetResponse` | 查询详情与多币种余额 |
| `listBudgets(ListBudgetsRequest)` | `ListBudgetsRequest` | `ListBudgetsResponse` | 列出全部预算账户（无参数、不分页） |
| `depositToBudget(DepositToBudgetRequest)` | `DepositToBudgetRequest` | `DepositToBudgetResponse` | 入金：余额账户 → 预算账户（资金变动） |
| `withdrawFromBudget(WithdrawFromBudgetRequest)` | `WithdrawFromBudgetRequest` | `WithdrawFromBudgetResponse` | 出金：预算账户 → 余额账户（资金变动） |
| `listDailyBalances(ListDailyBalancesRequest)` | `ListDailyBalancesRequest` | `ListDailyBalancesResponse` | 查询每日余额与资金流水（游标分页） |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `BudgetService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `BudgetService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

## 对接流程

### 1. 配置

```java
WfClientConfig config = WfClientConfig.builder()
    .clientId("YOUR_CLIENT_ID")
    .privateKeyFromPath("/path/to/private_key.pem")
    .publicKeyFromPath("/path/to/wf_public_key.pem")
    .baseUrl("https://YOUR_BASE_URL")
    .build();
```

### 2. 创建 Service

```java
BudgetService budgetService = new BudgetService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 创建预算账户
CreateBudgetRequest createRequest = new CreateBudgetRequest();
createRequest.setRequestId("req_budget_20260808_001");
createRequest.setName("Marketing Cards Q3");

CreateBudgetResponse createResponse = budgetService.createBudget(createRequest);
String budgetId = createResponse.getId();

// 查询详情与多币种余额
QueryBudgetRequest queryRequest = new QueryBudgetRequest();
queryRequest.setId(budgetId);

QueryBudgetResponse queryResponse = budgetService.queryBudget(queryRequest);

// 列出全部预算账户（无请求参数）
ListBudgetsResponse listResponse = budgetService.listBudgets(new ListBudgetsRequest());

// 入金：从标准余额账户划 10.02 USD 到预算账户
DepositToBudgetRequest depositRequest = new DepositToBudgetRequest();
depositRequest.setRequestId("req_deposit_20260808_001");
depositRequest.setId(budgetId);
depositRequest.setBalanceType("NORMAL_BALANCE");
depositRequest.setAmount(new Amount("USD", 1002L));

DepositToBudgetResponse depositResponse = budgetService.depositToBudget(depositRequest);

// 出金：将 200.00 USD 从预算账户退回余额账户
WithdrawFromBudgetRequest withdrawRequest = new WithdrawFromBudgetRequest();
withdrawRequest.setRequestId("req_withdraw_20260808_001");
withdrawRequest.setId(budgetId);
withdrawRequest.setAmount(new Amount("USD", 20000L));

WithdrawFromBudgetResponse withdrawResponse = budgetService.withdrawFromBudget(withdrawRequest);

// 查询每日余额（区间跨度不超过 31 天）
ListDailyBalancesRequest dailyRequest = new ListDailyBalancesRequest();
dailyRequest.setStartDate("2026-06-01");
dailyRequest.setEndDate("2026-06-07");
dailyRequest.setCurrencies(Arrays.asList("USD", "EUR"));
dailyRequest.setLimit(20);

ListDailyBalancesResponse dailyResponse = budgetService.listDailyBalances(dailyRequest);
```

### 4. 检查业务结果

```java
if ("S".equals(createResponse.getResult().getResultStatus())) {
    System.out.println("预算账户 ID: " + createResponse.getId());
    System.out.println("状态: " + createResponse.getStatus());          // 创建后为 ACTIVE
}

if ("S".equals(queryResponse.getResult().getResultStatus())) {
    for (Amount amt : queryResponse.getBalanceAmounts()) {             // FAILED 时为空列表
        System.out.println("余额: " + amt.getCurrency() + " " + amt.getValue());
    }
}

if ("S".equals(listResponse.getResult().getResultStatus())) {
    for (BudgetRecord budget : listResponse.getItems()) {              // 最多 3 条，不分页
        System.out.println("预算账户: " + budget.getId() + " " + budget.getStatus());
    }
}

if ("S".equals(depositResponse.getResult().getResultStatus())) {
    System.out.println("入金操作状态: " + depositResponse.getStatus());
}

if ("S".equals(withdrawResponse.getResult().getResultStatus())) {
    System.out.println("出金操作状态: " + withdrawResponse.getStatus());
}

if ("S".equals(dailyResponse.getResult().getResultStatus())) {
    for (DailyBalanceRecord rec : dailyResponse.getDailyBalances()) {
        System.out.println(rec.getDate() + " EOD=" + rec.getEndOfDayBalance().getValue());
    }
    // nextCursor 缺失表示已到最后一页
    System.out.println("下一页游标: " + dailyResponse.getNextCursor());
}
```

## 注意事项

### create_a_budget
- `requestId` 必填，为幂等键（最大 64 字符），每次创建请求必须唯一；网络重试须复用同一取值，避免重复创建
- 单个商户账户最多 3 个预算账户，超出返回 `BUDGET_ACCOUNT_NUMBER_OVER_LIMIT`；建议创建前先用 `list_budgets` 核对
- `name` 可选（最大 50 字符），不传时由系统自动分配
- 预算账户一经创建**不可关闭**，无关闭/删除接口
- 创建成功后 `status` 为 `ACTIVE`（区别于持卡人需等待 KYC 审核）

### query_a_budget
- `id` 必填，为 `create_a_budget` 返回的预算账户标识
- `balanceAmounts` 每币种至多一条；`status` 为 `FAILED` 时返回空列表
- 金额 `value` 为**最小货币单位**整数，展示前需按币种小数位换算

### list_budgets
- **无请求参数**，请求体为 `{}`；`ListBudgetsRequest` 无业务字段，仅为统一调用形态与后续扩展保留
- **不分页**：响应仅 `result` 与 `items`，无 `nextCursor`/`prevCursor`/`totalCount`
- `items` 每项字段与 `query_a_budget` 响应一致但**不含余额**，需要余额须再调 `query_a_budget`

### deposit_to_a_budget
- `requestId`、`id`、`balanceType`、`amount` 均为必填；`amount.value` 须大于 0
- `balanceType` 为入金**来源**余额账户类型：`NORMAL_BALANCE`（普通余额，即电商余额，默认）、`SAME_NAME_TOP_UP_BALANCE`（同名充值余额）
- 仅 `ACTIVE` 预算账户可入金，否则返回 `BUDGET_ACCOUNT_STATUS_INACTIVE`；来源账户资金不足返回 `BALANCE_NOT_ENOUGH`
- 资金变动操作：`resultStatus=U` 时先用同一 `requestId` + 完全相同参数重试，仍不确定则用 `query_a_budget` 核对余额，**禁止换新 `requestId` 重发**（会重复扣款）

### withdraw_from_a_budget
- `requestId`、`id`、`amount` 均为必填；`amount.value` 须大于 0，币种须与被出金预算账户一致
- 仅 `ACTIVE` 预算账户可出金；预算账户可用余额不足返回 `BALANCE_NOT_ENOUGH`
- 响应不回显 `balanceType`（入金独有），出金资金一律退回余额账户
- 资金变动操作：幂等与结果未知处置规则同 `deposit_to_a_budget`

### list_daily_balances
- `startDate`、`endDate` 必填，格式 `yyyy-MM-dd`（客户本地时区），**区间跨度不超过 31 天**，超出返回 `PARAM_ILLEGAL`
- `budgetId` 可选（不传返回全部预算账户）；`currencies` 可选（ISO 4217 三字母，最多 10 项）
- `limit` 取值范围 1-100，不传时默认 20
- 响应列表字段名为 **`dailyBalances`**（非 `items`）；分页终止以 **`nextCursor` 缺失**为准
- 每条记录的 `endOfDayBalance`/`dailyFundInAmount`/`dailyFundOutAmount` 均为 `Amount` 对象

## 枚举类型说明

### BudgetStatus（预算账户状态）

| 取值 | 说明 |
|------|------|
| `ACTIVE` | 账户已激活，可正常使用；非终态 |
| `FAILED` | 账户创建失败；终态 |

### BalanceType（余额账户类型）

用于 `deposit_to_a_budget` 指定入金来源余额账户类型（请求与响应均回显）。

| 取值 | 说明 |
|------|------|
| `NORMAL_BALANCE` | 普通余额账户（电商余额），默认类型 |
| `SAME_NAME_TOP_UP_BALANCE` | 同名充值余额账户，由同名银行账户转入资金 |

## 模块专用错误码

以下错误码由本模块接口引入，已作「发卡预算账户（Budgets）模块错误码」分节追加到共享枚举
`{basePackage}.wf.model.exception.WfErrorCode`（`USER_NOT_EXIST`、`AUTHORIZATION_NOT_EXIST`、`CONTRACT_CHECK_FAIL`、`REPEAT_REQ_INCONSISTENT`、`PARAM_ILLEGAL`、`PROCESS_FAIL`、`UNKNOWN_EXCEPTION`、`BALANCE_NOT_ENOUGH`、`USER_NO_PERMISSION`、`SYSTEM_ERROR` 等为通用/跨模块码，已在该枚举既有分节中定义，不重复追加）：

| resultCode | resultStatus | 出现接口 | 说明 |
|------------|--------------|----------|------|
| `BUDGET_ACCOUNT_NUMBER_OVER_LIMIT` | F | create | 预算账户数量超过 3 个上限，复用已有账户 |
| `BUDGET_NOT_FOUND` | F | query / deposit / withdraw | 预算账户不存在，`id` 有误 |
| `BUDGET_ACCOUNT_STATUS_INACTIVE` | F | deposit / withdraw | 预算账户非 `ACTIVE` 状态，不可出入金 |

## 文件结构

```
budgets/
├── README.md
├── java/
│   ├── service/
│   │   └── BudgetService.java                         # 发卡预算账户服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── BudgetRecord.java                      # 预算账户记录（用于 list 响应的 items）
│       │   └── DailyBalanceRecord.java                # 每日余额记录（用于 dailyBalances 列表）
│       ├── request/
│       │   ├── CreateBudgetRequest.java               # 创建预算账户请求（requestId, name）
│       │   ├── QueryBudgetRequest.java                # 查询预算账户请求（id）
│       │   ├── ListBudgetsRequest.java                # 查询预算账户列表请求（无业务字段）
│       │   ├── DepositToBudgetRequest.java            # 预算账户入金请求（requestId, id, balanceType, amount）
│       │   ├── WithdrawFromBudgetRequest.java         # 预算账户出金请求（requestId, id, amount）
│       │   └── ListDailyBalancesRequest.java          # 查询每日余额请求（startDate, endDate, budgetId, currencies, cursor, limit）
│       └── response/
│           ├── CreateBudgetResponse.java              # 创建预算账户响应
│           ├── QueryBudgetResponse.java               # 查询预算账户响应（含 balanceAmounts）
│           ├── ListBudgetsResponse.java               # 查询预算账户列表响应（result, items）
│           ├── DepositToBudgetResponse.java           # 预算账户入金响应
│           ├── WithdrawFromBudgetResponse.java        # 预算账户出金响应
│           └── ListDailyBalancesResponse.java         # 查询每日余额响应（result, dailyBalances, nextCursor, prevCursor）
├── create-a-budget/
│   └── GUIDE.md                                       # create_a_budget 接口接入指引
├── query-a-budget/
│   └── GUIDE.md                                       # query_a_budget 接口接入指引
├── list-budgets/
│   └── GUIDE.md                                       # list_budgets 接口接入指引
├── deposit-to-a-budget/
│   └── GUIDE.md                                       # deposit_to_a_budget 接口接入指引
├── withdraw-from-a-budget/
│   └── GUIDE.md                                       # withdraw_from_a_budget 接口接入指引
└── list-daily-balances/
    └── GUIDE.md                                       # list_daily_balances 接口接入指引
```

> **复用 domain 对象**：`Amount`、`Result` 等通用对象与其他模块共享，引用 `{basePackage}.wf.model.domain`、`{basePackage}.wf.model.response` 包下的已有定义。`BudgetRecord`、`DailyBalanceRecord` 为本模块独有的 domain 对象。
>
> **依赖的其他模块**：无强制前置依赖。预算账户资金供发卡（`issuing/cardholders` 及卡片模块）消费使用，日常余额核对亦可与对账单模块（`reporting/statement-report`）交叉验证。
