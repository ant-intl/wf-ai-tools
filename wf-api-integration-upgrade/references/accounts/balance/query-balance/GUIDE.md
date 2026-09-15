# Query Balance 接口接入指引

## 接口说明

查询商户账户中每种币种的当前余额。支持按币种和余额类型过滤，包括普通余额（NORMAL_BALANCE）、同名充值余额（SAME_NAME_TOP_UP_BALANCE）、预算余额（BUDGET_BALANCE）。

## 官方文档

- [query_balance 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_balance)

## 请求地址

`POST /api/open/v1/balances/query`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2024-01-01T12:08:56+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `currencies` | array[string] | No | ISO 4217 货币代码列表，最多 50 个；为空则返回所有币种 |
| `balanceTypes` | array[string] | No | 余额类型，默认 `NORMAL_BALANCE`；可选值：`NORMAL_BALANCE`、`SAME_NAME_TOP_UP_BALANCE`、`BUDGET_BALANCE` |
| `budgetAccountId` | string | Conditional | 预算账户 ID；当 `balanceTypes` 包含 `BUDGET_BALANCE` 时**必填** |

### 余额类型说明

| 值 | 说明 |
|----|------|
| `NORMAL_BALANCE` | 普通余额（默认） |
| `SAME_NAME_TOP_UP_BALANCE` | 同名充值余额 |
| `BUDGET_BALANCE` | 预算余额（需配合 budgetAccountId） |

### 请求示例

```json
{
  "currencies": ["USD", "CNY"],
  "balanceTypes": ["NORMAL_BALANCE", "BUDGET_BALANCE"],
  "budgetAccountId": "budget_account_001"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `balances` | array[BalanceItem] | 余额列表 |

### BalanceItem Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | ISO 4217 币种代码 |
| `balanceType` | string | 余额类型（`NORMAL_BALANCE` / `SAME_NAME_TOP_UP_BALANCE` / `BUDGET_BALANCE`） |
| `availableAmount` | Amount | 可用余额 |
| `frozenAmount` | Amount | 冻结余额 |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | 币种代码 |
| `value` | integer | 金额值，最小货币单位（如 USD 100.00 → value = 10000） |

### 响应示例

```json
{
  "result": {
    "resultStatus": "S",
    "resultCode": "SUCCESS",
    "resultMessage": "success"
  },
  "balances": [
    {
      "currency": "USD",
      "balanceType": "NORMAL_BALANCE",
      "availableAmount": {
        "currency": "USD",
        "value": 1000000
      },
      "frozenAmount": {
        "currency": "USD",
        "value": 50000
      }
    },
    {
      "currency": "CNY",
      "balanceType": "NORMAL_BALANCE",
      "availableAmount": {
        "currency": "CNY",
        "value": 5000000
      },
      "frozenAmount": {
        "currency": "CNY",
        "value": 0
      }
    }
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 调用成功 |
| `PARAM_ILLEGAL` | F | 参数非法，不可重试 |
| `ACCOUNT_NOT_EXIST` | F | 账户不存在，不可重试 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败，不可重试 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在，不可重试 |
| `USER_NOT_EXIST` | F | 用户不存在，不可重试 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，可重试 |

## 示例代码

参考 [references/accounts/balance/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
balance/java/
├── service/
│   └── BalanceService.java                    # 薄封装 Service，包含 queryBalance 方法
└── model/
    ├── domain/
    │   └── BalanceItem.java                   # 余额项（currency, balanceType, availableAmount, frozenAmount）
    # 说明：Amount 定义在 common 模块
    ├── request/
    │   └── QueryBalanceRequest.java           # 请求参数（currencies, balanceTypes, budgetAccountId）
    └── response/
        └── QueryBalanceResponse.java          # 响应结果（result, balances）
```

## 集成使用方式

BalanceService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryBalanceRequest` 设置查询条件
2. 调用 `BalanceService.queryBalance(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理余额数据

### 业务代码示例

```java
// 构造请求
QueryBalanceRequest request = new QueryBalanceRequest();
request.setCurrencies(Arrays.asList("USD", "CNY"));
request.setBalanceTypes(Arrays.asList("NORMAL_BALANCE"));

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryBalanceResponse response = balanceService.queryBalance(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    // 处理余额数据
    for (BalanceItem item : response.getBalances()) {
        System.out.println(item.getCurrency() + " 可用余额: " + item.getAvailableAmount().getValue());
    }
} else {
    // 处理错误
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BalanceService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BalanceService(config)` 创建实例。
