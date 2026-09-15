# 对账单（Statement Report）模块

## 官方文档

- [WorldFirst 开发者文档 - Statement Report Overview](https://docs.worldfirst.com/wfdocs/api-sdk/statement_report_overview)
- [list_statements](https://docs.worldfirst.com/wfdocs/api-sdk/list_statements) | [query_a_statement](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_statement)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 查询对账单详情 | `query-a-statement/GUIDE.md` | 根据 ID 查询单笔对账单的完整详情 | `POST /api/open/v1/statements/query` |
| 查询对账单列表 | `list-statements/GUIDE.md` | 分页查询对账单列表，支持按交易类型、币种、余额类型、时间范围过滤 | `POST /api/open/v1/statements/list` |

## StatementService 说明

`StatementService` 是对账单模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `QueryStatementResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `queryStatement(QueryStatementRequest)` | `QueryStatementRequest` | `QueryStatementResponse` | 查询单笔对账单详情 |
| `listStatements(ListStatementsRequest)` | `ListStatementsRequest` | `ListStatementsResponse` | 分页查询对账单列表，支持游标分页 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `StatementService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `StatementService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

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
StatementService statementService = new StatementService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 查询对账单详情
QueryStatementRequest queryRequest = new QueryStatementRequest();
queryRequest.setId("STM202604180000****");

QueryStatementResponse queryResponse = statementService.queryStatement(queryRequest);

// 查询对账单列表
ListStatementsRequest listRequest = new ListStatementsRequest();
listRequest.setLimit(20);
listRequest.setFromTransactAt("2026-04-01T00:00:00+08:00");
listRequest.setToTransactAt("2026-04-23T23:59:59+08:00");
listRequest.setTransactionTypes(Arrays.asList("TRANSFER", "COLLECTION"));
listRequest.setCurrencies(Arrays.asList("USD", "GBP"));
listRequest.setBalanceTypes(Arrays.asList("NORMAL_BALANCE"));

ListStatementsResponse listResponse = statementService.listStatements(listRequest);
```

### 4. 检查业务结果

```java
if ("S".equals(queryResponse.getResult().getResultStatus())) {
    System.out.println("对账单 ID: " + queryResponse.getId());
    System.out.println("交易类型: " + queryResponse.getTransactionType());
    System.out.println("状态: " + queryResponse.getStatus());
    System.out.println("交易金额: " + queryResponse.getTransactionAmount().getValue());

    if (queryResponse.getExchangeRate() != null) {
        System.out.println("卖出币种: " + queryResponse.getExchangeRate().getSellCurrency());
        System.out.println("买入币种: " + queryResponse.getExchangeRate().getBuyCurrency());
        System.out.println("汇率: " + queryResponse.getExchangeRate().getRate());
    }
}
```

## 注意事项

### query_a_statement
- `id` 必填，使用 List Statements 返回的 ID
- `failureCode` 和 `failureMessage` 仅在 `status` 为 `FAIL` 时返回
- `feeItemType` 仅在 `transactionType` 为 `CHARGE` 时返回
- `exchangeRate` 仅在涉及币种转换的交易中返回
- `goodsInfo` 仅在涉及海关申报的交易中返回
- `combinedTransactions` 返回关联交易列表（如手续费扣款等）
- `fundFlowDetail` 中的付款方和收款人信息已脱敏

### list_statements
- 使用**游标分页**（cursor-based pagination），非传统页码分页
- `limit` 取值范围 1-100，必填
- 首次请求不传 `cursor`，后续请求传入上一次响应返回的 `nextCursor`
- 当 `nextCursor` 为空时表示已到最后一页
- `fromTransactAt` / `toTransactAt` 必须成对使用，最大时间跨度 100 天
- `transactionTypes`、`currencies`、`balanceTypes` 为数组类型，支持多值过滤（OR 逻辑）
- 当 `balanceTypes` 包含 `BUDGET_BALANCE` 时，必须传入 `budgetAccountIds`
- `keyword` 支持模糊匹配对方名称和备注

## 枚举类型说明

### TransactionType（交易类型）
- `TRANSFER`: 转账
- `TRANSFER_REFUND`: 转账退款
- `WITHDRAWAL`: 提现
- `WITHDRAWAL_REFUND`: 提现退款
- `CONVERSION`: 币种转换
- `CONVERSION_DEAL`: 币种转换（锁定汇率）
- `CHARGE`: 服务费
- `CHARGE_REFUND`: 服务费退款
- `DEDUCTION`: 扣款
- `FUND_COLLECTION`: 资金归集
- `COLLECTION`: 收款
- `COLLECTION_REFUND`: 收款退款
- `PAYMENT`: 付款
- `CASH_BACK`: 返现
- `TOP_UP`: 充值

### StatementStatus（对账单状态）
- `INIT`: 已创建
- `PROCESSING`: 处理中
- `PENDING`: 待处理
- `SUCCESS`: 成功
- `FAIL`: 失败
- `REFUNDED`: 已退款

### BalanceType（余额类型）
- `NORMAL_BALANCE`: 标准电商余额（默认值，当 `balanceTypes` 省略时使用）
- `SAME_NAME_TOP_UP_BALANCE`: 同名充值余额
- `BUDGET_BALANCE`: 预算账户余额，需配合 `budgetAccountIds` 使用

### FeeItemType（费用项类型）
- `OBO_SERVICE_FEE`: On-Behalf-Of 服务费
- `REMIT_SERVICE_FEE`: 汇款基础服务费

### FailureCode（失败原因码）
- `CURRENCY_NOT_SUPPORT`: 币种不支持
- `CARD_INFO_NOT_MATCH`: 银行卡信息不匹配
- `ORDER_IS_REVERSED`: 订单已冲正
- `ORDER_IS_CLOSED`: 订单已关闭
- `AMOUNT_EXCEED_LIMIT`: 金额超限
- `RISK_REJECT`: 风控拒绝
- `BALANCE_NOT_ENOUGH`: 余额不足（渠道侧）

### StatementAccountType（账户类型）
- `WORLDFIRST`: WorldFirst 账户
- `BANK_CARD`: 银行卡
- `VIRTUAL_ACCOUNT`: WorldFirst 虚拟账户（VA）
- `ALIPAY_CN`: 支付宝中国钱包
- `MINI_ACCOUNT`: 小程序账户
- `INST_ACCOUNT`: 机构账户
- `INNER_ACCOUNT`: 内部账户
- `OVO`: OVO 钱包（仅收款方）

## 文件结构

```
statement-report/
├── README.md
├── java/
│   ├── service/
│   │   └── StatementService.java                      # 对账单服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── ExchangeRate.java                      # 汇率信息对象
│       │   ├── FundFlowDetail.java                    # 资金流详情
│       │   ├── PayerInfo.java                         # 付款方信息
│       │   ├── BeneficiaryInfo.java                   # 收款方信息
│       │   ├── GoodsInfo.java                         # 商品信息
│       │   ├── CombinedTransaction.java               # 关联交易
│       │   └── StatementRecord.java                   # 对账单记录（用于 list 响应）
│       ├── request/
│       │   ├── QueryStatementRequest.java             # 查询对账单请求
│       │   └── ListStatementsRequest.java             # 查询对账单列表请求
│       └── response/
│           ├── QueryStatementResponse.java            # 查询对账单响应
│           └── ListStatementsResponse.java            # 查询对账单列表响应
├── query-a-statement/
│   └── GUIDE.md                                       # query_a_statement 接口接入指引
└── list-statements/
    └── GUIDE.md                                       # list_statements 接口接入指引
```
