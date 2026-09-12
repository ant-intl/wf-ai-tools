# 余额（Balance）模块

## 官方文档

- [WorldFirst 开发者文档 - Account](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/account)
- [query_balance](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/query_balance) | [list_balance_history](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/list_balance_history)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 查询余额 | `query-balance/GUIDE.md` | 查询 WF 账户余额，支持按币种和余额类型过滤 | `POST /api/open/v1/balances/query` |
| 查询余额变动历史 | `list-balance-history/GUIDE.md` | 查询余额变动历史记录，支持游标分页和按币种、时间范围过滤 | `POST /api/open/v1/balances/listHistory` |

## BalanceService 说明

`BalanceService` 是余额模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `QueryBalanceResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `queryBalance(QueryBalanceRequest)` | `QueryBalanceRequest` | `QueryBalanceResponse` | 查询账户余额，可按币种和余额类型过滤 |
| `listBalanceHistory(ListBalanceHistoryRequest)` | `ListBalanceHistoryRequest` | `ListBalanceHistoryResponse` | 查询余额变动历史，支持游标分页 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `BalanceService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `BalanceService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

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
BalanceService balanceService = new BalanceService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 查询余额
QueryBalanceRequest queryRequest = new QueryBalanceRequest();
queryRequest.setCurrencies(Arrays.asList("USD"));

QueryBalanceResponse queryResponse = balanceService.queryBalance(queryRequest);

// 查询余额变动历史
ListBalanceHistoryRequest historyRequest = new ListBalanceHistoryRequest();
historyRequest.setLimit(20);
historyRequest.setCurrency("USD");

ListBalanceHistoryResponse historyResponse = balanceService.listBalanceHistory(historyRequest);
```

### 4. 检查业务结果

```java
if ("S".equals(queryResponse.getResult().getResultStatus())) {
    if (queryResponse.getBalances() != null) {
        for (BalanceItem item : queryResponse.getBalances()) {
            System.out.println(item.getCurrency() + " available: " + item.getAvailableAmount().getValue());
        }
    }
}
```

## 注意事项

### query_balance
- `currencies` 为空则返回所有币种余额，最多支持 50 个币种
- `balanceTypes` 支持 `NORMAL_BALANCE`（默认）、`SAME_NAME_TOP_UP_BALANCE`、`BUDGET_BALANCE`
- 当 `balanceTypes` 包含 `BUDGET_BALANCE` 时，`budgetAccountId` 必填
- 余额 `value` 为最小货币单位的整数（如 USD 100.00 → value = 10000）

### list_balance_history
- 使用**游标分页**（cursor-based pagination），非传统页码分页
- `limit` 取值范围 1-100，默认 20
- 首次请求不传 `cursor`，后续请求传入上一次响应返回的 `nextCursor`
- 当 `nextCursor` 为空时表示已到最后一页
- `fromTransactAt` / `toTransactAt` 类型为 `String`，使用 ISO 8601 格式（如 `2024-01-01T00:00:00Z`）
- 时间范围跨度有限制，具体上限参见 WF 官方文档
- `changeAmount.value` 正数为入账，负数为出账

## 文件结构

```
balance/
├── README.md
├── java/
│   ├── service/
│   │   └── BalanceService.java              # 余额服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── BalanceItem.java             # 余额项（query_balance 响应，引用 common.Amount）
│       │   └── BalanceHistoryRecord.java    # 变动记录（list_balance_history 响应，引用 common.Amount）
│       # 说明：Amount 定义在 common 模块，不再重复
│       ├── request/
│       │   ├── QueryBalanceRequest.java     # 查询余额请求
│       │   └── ListBalanceHistoryRequest.java # 查询余额变动历史请求
│       └── response/
│           ├── QueryBalanceResponse.java    # 查询余额响应
│           └── ListBalanceHistoryResponse.java # 查询余额变动历史响应
├── query-balance/
│   └── GUIDE.md                            # query_balance 接口接入指引
└── list-balance-history/
    └── GUIDE.md                            # list_balance_history 接口接入指引
```
