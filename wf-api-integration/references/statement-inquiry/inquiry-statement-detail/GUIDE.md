# inquiryStatementDetail 接口接入指引

## 接口说明

查询指定账单流水的详细信息。需先调用 `inquiryStatementList` 获取 `accountingBizNo`，再以此为入参调用本接口。

## 请求地址

`POST /amsin/api/v1/business/account/inquiryStatementDetail`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<base64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601 |

## 请求参数

### 必填字段

| Field | Type | Description |
|-------|------|-------------|
| `accountingBizNo` | String | 账单流水唯一 ID，通过 inquiryStatementList 获取 |

## 响应参数

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U) |
| `responseId` | String | S | 响应唯一 ID，最大 32 位 |
| `transactionId` | String | S | 交易 ID（TRANSFER/WITHDRAWAL/CONVERSION 等类型必返回） |
| `extTransactionId` | String | S | 调用方传入的外部交易 ID，最大 256 位 |
| `transactionStatus` | String | S | 交易状态：INIT/PROCESSING/PENDING/SUCCESS/FAIL/REFUNDED |
| `transactionTime` | DateTime | S | 余额变动时间，ISO 8601 格式 |
| `transactionType` | String | S | 交易类型，如 TRANSFER、COLLECTION 等 |
| `transactionAmount` | Amount | S | 本次余额变动金额 |
| `originalTransactionAmount` | Amount | S | 原始提交的交易金额 |
| `feeAmount` | Amount | S | 手续费金额（TRANSFER/WITHDRAWAL/CONVERSION 等类型条件必返回） |
| `feeItemType` | String | S | 手续费类型：OBO_SERVICE_FEE / REMIT_SERVICE_FEE |
| `netAmount` | Amount | S | 扣除手续费后的净额（TRANSFER/CHARGE/PAYMENT/CASH_BACK 必返回） |
| `receiveAmount` | Amount | S | 换汇后实际到账金额（TRANSFER/CHARGE/PAYMENT/CASH_BACK 必返回） |
| `accountBalance` | Amount | S | 交易后账户实时余额 |
| `fundMoveDetail` | FundMoveDetail | S | 资金流动详情（付款方/收款方信息） |
| `foreignExchangeQuote` | Quote | S | 汇率报价信息（TRANSFER/WITHDRAWAL/CONVERSION 等类型可选） |
| `refundForeignExchangeQuote` | Quote | S | 退款汇率报价（TRANSFER_REFUND/WITHDRAWAL_REFUND/CHARGE_REFUND 可选） |
| `balanceType` | String | S | 余额类型：NORMAL_BALANCE / SAME_NAME_TOP_UP_BALANCE / BUDGET_BALANCE |
| `accountingBizNo` | String | S | 账单流水唯一 ID |
| `failReason` | Result | S | 失败原因（transactionStatus=FAIL 时返回） |
| `combinedTransactionList` | List\<RelatedStatement\> | S | 关联交易列表 |
| `operatorInfo` | OperatorInfo | S | 操作员信息（仅通过 WF 门户操作时返回） |
| `goodsName` | String | S | 商品名称 |
| `goodsAmount` | Amount | S | 商品金额 |
| `originalFeeAmount` | Amount | S | 优惠前手续费金额 |
| `discountFeeAmount` | Amount | S | 手续费优惠金额 |

### RelatedStatement 字段

| Field | Type | Description |
|-------|------|-------------|
| `transactionId` | String | 关联交易 ID |
| `accountingBizNo` | String | 关联账单流水 ID |
| `transactionType` | String | 关联交易类型 |

## 错误码

### 不可重试 (resultStatus=F)

`PARAM_ILLEGAL`, `PROCESS_FAIL`, `INVALID_SIGNATURE`, `USER_NOT_EXIST`, `CURRENCY_NOT_SUPPORT`, `CONTRACT_CHECK_FAIL`, `AUTHORIZATION_NOT_EXIST`, `ACCESS_TOKEN_EXPIRED`

### 可重试 (resultStatus=U)

`UNKNOWN_EXCEPTION`, `REQUEST_TRAFFIC_EXCEED_LIMIT`

### failReason.resultCode

`CURRENCY_NOT_SUPPORT`, `CARD_INFO_NOT_MATCH`, `ORDER_IS_REVERSED`, `ORDER_IS_CLOSED`, `AMOUNT_EXCEED_LIMIT`, `RISK_REJECT`, `BALANCE_NOT_ENOUGH`

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
└── model/
    ├── domain/
    │   └── RelatedStatement.java
    ├── request/InquiryStatementDetailRequest.java
    └── response/InquiryStatementDetailResponse.java
```

> 注意：`StatementClient` 位于 `inquiry-statement-list/` 模块下，
> 同时包含 `inquiryStatementList` 和 `inquiryStatementDetail` 两个方法。
> `Amount`、`FundMoveDetail`、`ForeignExchangeQuote`、`OperatorInfo` 等 domain 类与 `inquiry-statement-list` 共用，无需重复生成。

### Golang 模板结构

```
golang/
└── model/
    ├── request/inquiry_statement_detail_request.go
    └── response/inquiry_statement_detail_response.go
```

> 注意：`StatementClient` 位于 `inquiry-statement-list/golang/client/` 下，
> 同时包含 `InquiryStatementList` 和 `InquiryStatementDetail` 两个方法。

## StatementClient.inquiryStatementDetail 关键行为

1. **参数校验**：`accountingBizNo` 必填，不能为空
2. **构建请求体**：仅包含 `accountingBizNo` 字段
3. **结果处理**：`S` → 返回；`F`/`U` → 抛出 `WfException`
