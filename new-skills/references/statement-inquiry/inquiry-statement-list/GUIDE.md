# inquiryStatementList 接口接入指引

## 接口说明

分页查询 WF 账户交易流水。

## 请求地址

`POST /amsin/api/v1/business/account/inquiryStatementList`

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
| `startTime` | DateTime | ISO 8601。fuzzyName 为空时，时间跨度 ≤ 100 天 |
| `endTime` | DateTime | ISO 8601 |
| `pageSize` | Integer | **固定值 10** |
| `pageNumber` | Integer | 1-50 |

### 可选字段

| Field | Type | Description |
|-------|------|-------------|
| `transactionTypeList` | List\<String\> | `TRANSFER`, `COLLECTION` 等 |
| `currencyList` | List\<String\> | ISO-4217 |
| `balanceTypes` | List\<String\> | `NORMAL_BALANCE` 等 |
| `fuzzyName` | String | 模糊关键字，设置后 100 天限制取消 |

## 响应参数

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U) |
| `responseId` | String | S | 响应 ID |
| `statementList` | List | S | 流水记录列表 |
| `totalCount` | Integer | S | 总记录数 |
| `totalPageNumber` | Integer | S | 总页数 |
| `currentPageNumber` | Integer | S | 当前页码 |

### StatementRecord 主要字段

| Field | Type | Description |
|-------|------|-------------|
| `transactionId` | String | 交易 ID |
| `transactionTime` | String | ISO 8601 |
| `transactionType` | String | TRANSFER, CHARGE, COLLECTION 等 |
| `transactionStatus` | String | SUCCESS, PROCESSING, FAIL, REFUNDED |
| `transactionAmount` | Amount | 本次交易变动金额（正=收入，负=支出） |
| `foreignExchangeQuote` | Object | 汇率信息 |
| `fundMoveDetail` | Object | 资金流动详情（付款方/收款方信息） |

## 错误码

### 不可重试 (resultStatus=F)

`PARAM_ILLEGAL`, `PROCESS_FAIL`, `INVALID_SIGNATURE`, `USER_NOT_EXIST`, `CURRENCY_NOT_SUPPORT`

### 可重试 (resultStatus=U)

`UNKNOWN_EXCEPTION`, `REQUEST_TRAFFIC_EXCEED_LIMIT`

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
├── client/
│   ├── InquiryStatementClient.java
│   └── InquiryStatementClientTest.java
└── model/
    ├── domain/
    │   ├── StatementRecord.java
    │   ├── FundMoveDetail.java
    │   ├── ForeignExchangeQuote.java
    │   └── OperatorInfo.java
    ├── request/InquiryStatementRequest.java
    └── response/InquiryStatementResponse.java
```

### Golang 模板结构

```
golang/
├── client/
│   ├── inquiry_statement_client.go
│   └── inquiry_statement_integration_test.go
└── model/
    ├── request/inquiry_statement_request.go
    └── response/inquiry_statement_response.go
```

## InquiryStatementClient 关键行为

1. **参数校验**：startTime、endTime 必填；pageNumber 范围 1-50；fuzzyName 为空时校验时间跨度 ≤ 100 天
2. **pageSize 固定**：始终覆盖为 10
3. **构建请求体**：仅包含非空字段
4. **结果处理**：`S` → 返回；`F`/`U` → 抛出 `WfException`

