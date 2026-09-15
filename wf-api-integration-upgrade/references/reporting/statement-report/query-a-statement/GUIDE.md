# Query a Statement 接口接入指引

## 接口说明

根据对账单 ID 查询单笔对账单的完整详情，包括交易金额、手续费、汇率信息、资金流详情、商品信息及关联交易等。

## 官方文档

- [query_a_statement 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_statement)

## 请求地址

`POST /api/open/v1/statements/query`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-18T10:15:30+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | 对账单唯一标识，使用 List Statements 返回的 ID |

### 请求示例

```json
{
  "id": "STM202604180000****"
}
```

## 响应参数

| Field | Type | Description                                                 |
|-------|------|-------------------------------------------------------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 对账单唯一标识                                                     |
| `transactionId` | string | WorldFirst 定义的交易唯一 ID                                       |
| `externalTransactionId` | string | 集成方定义的交易唯一 ID                                               |
| `transactionType` | string | 交易类型（TransactionType 枚举）                                    |
| `status` | string | 对账单状态（StatementStatus 枚举）                                   |
| `failureCode` | string | 失败原因码，仅当 `status` 为 `FAIL` 时返回                              |
| `failureMessage` | string | 失败原因描述，仅当 `status` 为 `FAIL` 时返回                             |
| `transactionAmount` | Amount | 交易金额，正数为入账，负数为出账                                            |
| `originalTransactionAmount` | Amount | 手续费扣除前的原始交易金额                                               |
| `feeAmount` | Amount | WorldFirst 收取的手续费                                           |
| `netAmount` | Amount | 手续费扣除后的净金额                                                  |
| `discountFeeAmount` | Amount | 服务费折扣金额                                                     |
| `originalFeeAmount` | Amount | 折扣前的原始服务费                                                   |
| `platformFeeAmount` | Amount | 第三方平台服务费                                                    |
| `receiveAmount` | Amount | 币种转换后的到账金额                                                  |
| `balanceAmount` | Amount | 交易后的账户余额                                                    |
| `balanceType` | string | 余额类型（BalanceType 枚举）                                        |
| `feeItemType` | string | 费用项类型，仅当 `transactionType` 为 `CHARGE` 时返回                   |
| `exchangeRate` | ExchangeRate | 汇率信息，仅涉及币种转换时返回                                             |
| `fundFlowDetail` | FundFlowDetail | 资金流详情，包含付款方和收款方信息                                           |
| `goodsInfo` | GoodsInfo | 商品信息，仅涉及海关申报的交易返回                                           |
| `combinedTransactions` | array[CombinedTransaction] | 关联交易列表                                                      |
| `reference` | string | 渠道参考信息，传递给收款方                                               |
| `description` | string | 用户提供的交易描述                                                   |
| `transactedAt` | datetime | 交易时间（ISO 8601 格式）                                           |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | 币种代码（ISO-4217） |
| `value` | integer | 金额值，最小货币单位（如 USD 100.00 → value = 10000） |

### ExchangeRate Object

| Field | Type | Description |
|-------|------|-------------|
| `sellCurrency` | string | 卖出币种 |
| `buyCurrency` | string | 买入币种 |
| `rate` | string | 汇率值 |

### FundFlowDetail Object

| Field | Type | Description |
|-------|------|-------------|
| `payerInfo` | PayerInfo | 付款方信息       |
| `beneficiaryInfo` | BeneficiaryInfo | 收款方信息       |
| `paymentExplanation` | string | 付款方提供的补充备注  |
| `paymentSubject` | string | 付款主体        |
| `paymentVoucherNo` | string | 付款凭证号       |

### PayerInfo Object

| Field | Type | Description |
|-------|------|-------------|
| `payerUserId` | string | 付款方 WorldFirst 唯一 ID |
| `name` | string | 付款方名称（脱敏） |
| `accountNo` | string | 付款方账号（脱敏） |
| `accountType` | string | 付款方账户类型 |
| `bankName` | string | 付款方银行名称 |

### BeneficiaryInfo Object

| Field | Type | Description |
|-------|------|-------------|
| `name` | string | 收款方名称（脱敏） |
| `accountNo` | string | 收款方账号（脱敏） |
| `accountType` | string | 收款方账户类型 |
| `bankRegion` | string | 收款方账户所在地区 |
| `bankName` | string | 收款方银行名称 |
| `storeName` | string | 收款方店铺名称 |
| `marketplaceName` | string | 集成方平台注册名称 |
| `receiveAccount` | string | 收款方 RA 或 VA 账号 |

### GoodsInfo Object

| Field | Type | Description |
|-------|------|-------------|
| `goodsName` | string | 申报商品名称      |
| `goodsAmount` | Amount | 申报商品金额      |

### CombinedTransaction Object

| Field               | Type | Description |
|---------------------|------|-------------|
| `transactionAmount` | Amount | 关联交易金额      |
| `transactedAt`      | datetime | 关联交易时间      |
| `transactionId`     | string | 关联交易唯一 ID   |
| `transactionType`   | string | 关联交易类型      |
| `status`            | string | 关联交易状态      |

### 响应示例

```json
{
  "result": {
    "resultStatus": "S",
    "resultCode": "SUCCESS",
    "resultMessage": "success"
  },
  "id": "STM202604180000****",
  "transactionId": "TXN202604180000****",
  "externalTransactionId": "EXT-ORD-2026-****",
  "transactionType": "TRANSFER",
  "status": "SUCCESS",
  "transactionAmount": { "value": -250000, "currency": "GBP" },
  "originalTransactionAmount": { "value": 260000, "currency": "GBP" },
  "feeAmount": { "value": 10000, "currency": "GBP" },
  "netAmount": { "value": 250000, "currency": "GBP" },
  "discountFeeAmount": { "value": 500, "currency": "GBP" },
  "originalFeeAmount": { "value": 10500, "currency": "GBP" },
  "receiveAmount": { "value": 2125000, "currency": "CNY" },
  "balanceAmount": { "value": 1820030, "currency": "GBP" },
  "balanceType": "NORMAL_BALANCE",
  "exchangeRate": { "sellCurrency": "GBP", "buyCurrency": "CNY", "rate": "8.5000" },
  "fundFlowDetail": {
    "payerInfo": {
      "payerUserId": "****",
      "name": "Acme Corp ****",
      "accountNo": "****5678",
      "accountType": "BANK_ACCOUNT",
      "bankName": "Barclays Bank"
    },
    "beneficiaryInfo": {
      "name": "Global Trade Inc.",
      "accountNo": "****1234",
      "accountType": "BANK_ACCOUNT",
      "bankRegion": "CN",
      "bankName": "Bank of China",
      "storeName": "**** Store",
      "marketplaceName": "**** Marketplace",
      "receiveAccount": "****5678"
    },
    "paymentExplanation": "Goods payment",
    "paymentSubject": "Goods Payment",
    "paymentVoucherNo": "VCH-2026-****"
  },
  "combinedTransactions": [
    {
      "transactionAmount": { "value": -10000, "currency": "GBP" },
      "transactedAt": "2026-04-18T09:15:01+08:00",
      "transactionId": "TXN202604180001****",
      "transactionType": "CHARGE",
      "status": "SUCCESS"
    }
  ],
  "reference": "Payment for PO-****",
  "description": "Cross-border trade payment",
  "transactedAt": "2026-04-18T09:15:00+08:00"
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 验证 `id` 是否为 List Statements 返回的有效对账单 ID |
| `PROCESS_FAIL` | F | 业务处理失败，不可重试 | 验证商户是否有对应权限 |
| `USER_NOT_EXIST` | F | 用户不存在 | 验证商户账户是否有效且已激活 |
| `SYSTEM_ERROR` | F | 系统错误，不可重试 | 稍后重试 |
| `SERVICE_NOT_ALLOWED` | F | 服务不允许 | 联系 WorldFirst 支持 |
| `CURRENCY_NOT_SUPPORT` | F | 币种不支持 | 检查交易币种是否受支持 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败 | 验证商户合约是否包含对账单查询权限 |
| `ACCESS_TOKEN_EXPIRED` | F | 访问令牌过期 | 刷新 OAuth 访问令牌后重试 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在 | 验证 `account-id` 是否属于当前平台商户 |

## 示例代码

参考 [references/reporting/statement-report/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
statement-report/java/
├── service/
│   └── StatementService.java                          # 薄封装 Service，包含 queryStatement 方法
└── model/
    ├── domain/
    │   ├── ExchangeRate.java                          # 汇率信息（sellCurrency, buyCurrency, rate）
    │   ├── FundFlowDetail.java                        # 资金流详情（payerInfo, beneficiaryInfo, paymentExplanation, paymentSubject, paymentVoucherNo）
    │   ├── PayerInfo.java                             # 付款方信息（payerUserId, name, accountNo, accountType, bankName）
    │   ├── BeneficiaryInfo.java                       # 收款方信息（name, accountNo, accountType, bankRegion, bankName, storeName, marketplaceName, receiveAccount）
    │   ├── GoodsInfo.java                             # 商品信息（goodsName, goodsAmount）
    │   ├── CombinedTransaction.java                   # 关联交易（transactionAmount, transactedAt, transactionId, transactionType）
    │   └── StatementRecord.java                       # 对账单记录（用于 list 响应）
    ├── request/
    │   └── QueryStatementRequest.java                 # 请求参数（id）
    └── response/
        └── QueryStatementResponse.java                # 响应结果（result + 完整对账单详情）
```

## 集成使用方式

StatementService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryStatementRequest` 设置对账单 ID
2. 调用 `StatementService.queryStatement(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理对账单详情

### 业务代码示例

```java
// 构造请求
QueryStatementRequest request = new QueryStatementRequest();
request.setId("STM202604180000****");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryStatementResponse response = statementService.queryStatement(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("对账单 ID: " + response.getId());
    System.out.println("交易 ID: " + response.getTransactionId());
    System.out.println("交易类型: " + response.getTransactionType());
    System.out.println("状态: " + response.getStatus());
    System.out.println("交易金额: " + response.getTransactionAmount().getValue() + " " + response.getTransactionAmount().getCurrency());

    // 处理汇率信息（跨币种场景）
    if (response.getExchangeRate() != null) {
        System.out.println("卖出币种: " + response.getExchangeRate().getSellCurrency());
        System.out.println("买入币种: " + response.getExchangeRate().getBuyCurrency());
        System.out.println("汇率: " + response.getExchangeRate().getRate());
    }

    // 处理资金流详情
    if (response.getFundFlowDetail() != null) {
        FundFlowDetail fundFlow = response.getFundFlowDetail();
        if (fundFlow.getPayerInfo() != null) {
            System.out.println("付款方: " + fundFlow.getPayerInfo().getName());
            System.out.println("付款方银行: " + fundFlow.getPayerInfo().getBankName());
        }
        if (fundFlow.getBeneficiaryInfo() != null) {
            System.out.println("收款方: " + fundFlow.getBeneficiaryInfo().getName());
            System.out.println("收款方银行: " + fundFlow.getBeneficiaryInfo().getBankName());
        }
    }

    // 处理关联交易
    if (response.getCombinedTransactions() != null) {
        for (CombinedTransaction combined : response.getCombinedTransactions()) {
            System.out.println("关联交易 ID: " + combined.getTransactionId());
            System.out.println("关联交易类型: " + combined.getTransactionType());
            System.out.println("关联交易金额: " + combined.getTransactionAmount().getValue());
            System.out.println("关联交易状态: " + combined.getStatus());
        }
    }

    // 处理失败信息
    if ("FAIL".equals(response.getStatus())) {
        System.out.println("失败原因码: " + response.getFailureCode());
        System.out.println("失败描述: " + response.getFailureMessage());
    }
} else {
    // 处理错误
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 StatementService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new StatementService(config)` 创建实例。
