# Query a Deposit 接口接入指引

## 接口说明

根据存款 ID 查询单笔存款的完整详情，包括来源金额、入账金额、手续费、汇率信息、付款方支付方式等。

## 官方文档

- [query_a_deposit 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_deposit)

## 请求地址

`POST /api/open/v1/deposits/query`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-05-22T10:15:30+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | 存款唯一标识，使用 List Deposits 返回的 ID 或存款 webhook 通知中的 ID |

### 请求示例

```json
{
  "id": "dep_2026051901HJK7N"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 存款唯一标识 |
| `accountId` | string | 收款商户的 WorldFirst 账户 ID |
| `type` | string | 存款业务类型（DepositType 枚举） |
| `status` | string | 存款生命周期状态（DepositStatus 枚举） |
| `sourceAmount` | Amount | 付款方发送的原始金额（货币转换和手续费扣除前） |
| `amount` | Amount | 实际入账金额（货币转换和手续费扣除后），status 为 SUCCESS/PARTIAL_REFUNDED/REFUNDED 时返回 |
| `refundedAmount` | Amount | 累计退款金额，status 为 PARTIAL_REFUNDED/REFUNDED 时返回 |
| `feeAmount` | Amount | 手续费金额，币种与 sourceAmount.currency 一致，status 为 SUCCESS/PARTIAL_REFUNDED/REFUNDED 时返回 |
| `quote` | Quote | 跨币种汇率详情，仅跨币种转换完成后返回，同币种存款不返回 |
| `initiatingPaymentMethod` | InitiatingPaymentMethod | 付款方支付方式详情，当付款方信息可识别时返回 |
| `receiveMethod` | ReceiveMethod | 收款方式，表示资金如何接收 |
| `reference` | string | 付款方转账备注/附言，当付款方提供了备注时返回 |
| `createdAt` | datetime | 存款记录创建时间（ISO 8601 格式） |
| `succeededAt` | datetime | 资金入账时间（ISO 8601 格式），status 为 SUCCESS/PARTIAL_REFUNDED 时返回 |
| `refundedAllAt` | datetime | 全额退款时间（ISO 8601 格式），status 为 REFUNDED 时返回 |

### Quote Object

| Field | Type | Description |
|-------|------|-------------|
| `currencyPair` | string | 汇率货币对，由两个 ISO 4217 三字母代码以斜杠分隔（如 EUR/USD） |
| `clientRate` | string | 应用于此存款的汇率 |

### InitiatingPaymentMethod Object

| Field | Type | Description |
|-------|------|-------------|
| `paymentAccountType` | string | 付款方账户类型：BANK_ACCOUNT 或 DIGITAL_WALLET |
| `bankDetails` | BankDetail | 银行账户详情，当 paymentAccountType 为 BANK_ACCOUNT 时返回 |
| `walletDetails` | WalletDetail | 数字钱包详情，当 paymentAccountType 为 DIGITAL_WALLET 时返回 |

### BankDetail Object

| Field | Type | Description |
|-------|------|-------------|
| `accountHolderName` | UserName | 账户持有人姓名 |
| `accountNumber` | string | 银行账号（脱敏） |
| `bankBIC` | string | SWIFT/BIC 代码 |
| `bankName` | string | 银行名称 |

### WalletDetail Object

| Field | Type | Description |
|-------|------|-------------|
| `accountHolderName` | UserName | 账户持有人姓名 |
| `walletBrandName` | string | 数字钱包品牌（WalletBrandName 枚举） |
| `accountNumber` | string | 钱包账号（脱敏） |

### UserName Object

| Field | Type | Description |
|-------|------|-------------|
| `firstName` | string | 名（given name） |
| `middleName` | string | 中间名 |
| `lastName` | string | 姓（family name） |
| `fullName` | string | 全名 |

### ReceiveMethod Object

| Field | Type | Description |
|-------|------|-------------|
| `type` | string | 收款账户类型：VA（Global Account）或 RA（Receiving Account） |
| `accountNumber` | string | 收款账号（脱敏） |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | 币种代码（ISO-4217） |
| `value` | integer | 金额值，最小货币单位（如 USD 100.00 → value = 10000） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultStatus": "S",
    "resultMessage": "Query succeeded."
  },
  "id": "dep_2026051901HJK7N",
  "accountId": "acct_50045123",
  "type": "THIRD_PARTY",
  "status": "SUCCESS",
  "sourceAmount": { "value": 98000, "currency": "USD" },
  "amount": { "value": 96000, "currency": "USD" },
  "feeAmount": { "value": 2000, "currency": "USD" },
  "initiatingPaymentMethod": {
    "paymentAccountType": "BANK_ACCOUNT",
    "bankDetails": {
      "accountHolderName": { "fullName": "Wei Zhang", "firstName": "Wei", "lastName": "Zhang" },
      "accountNumber": "6225********1234",
      "bankBIC": "BKCHCNBJ"
    }
  },
  "receiveMethod": {
    "type": "VA",
    "accountNumber": "8400********5678"
  },
  "reference": "Invoice INV-2026-001",
  "createdAt": "2026-05-22T10:15:30+08:00",
  "succeededAt": "2026-05-22T14:20:00+08:00"
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 查询成功 |
| `DEPOSIT_NOT_FOUND` | F | 存款不存在或无权访问，不可重试 |
| `INVALID_PARAMETER` | F | 参数非法，不可重试 |

## 示例代码

参考 [references/receiving/deposits/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
deposits/java/
├── service/
│   └── DepositService.java                          # 薄封装 Service，包含 queryDeposit 方法
└── model/
    ├── domain/
    │   ├── Quote.java                               # 汇率信息（currencyPair, clientRate）
    │   ├── BankDetail.java                          # 银行账户详情
    │   ├── WalletDetail.java                        # 数字钱包详情
    │   ├── InitiatingPaymentMethod.java             # 付款方支付方式
    │   ├── ReceiveMethod.java                       # 收款方式
    │   └── DepositRecord.java                       # 存款记录（用于 list 响应）
    ├── request/
    │   └── QueryDepositRequest.java                 # 请求参数（id）
    └── response/
        └── QueryDepositResponse.java                # 响应结果（result + 完整存款详情）
```

## 集成使用方式

DepositService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryDepositRequest` 设置存款 ID
2. 调用 `DepositService.queryDeposit(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理存款详情

### 业务代码示例

```java
// 构造请求
QueryDepositRequest request = new QueryDepositRequest();
request.setId("dep_2026051901HJK7N");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryDepositResponse response = depositService.queryDeposit(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("存款 ID: " + response.getId());
    System.out.println("状态: " + response.getStatus());
    System.out.println("入账金额: " + response.getAmount().getValue() + " " + response.getAmount().getCurrency());
    
    // 处理付款方信息
    if (response.getInitiatingPaymentMethod() != null) {
        String paymentType = response.getInitiatingPaymentMethod().getPaymentAccountType();
        System.out.println("付款方类型: " + paymentType);
        
        if ("BANK_ACCOUNT".equals(paymentType) && response.getInitiatingPaymentMethod().getBankDetails() != null) {
            BankDetail bank = response.getInitiatingPaymentMethod().getBankDetails();
            System.out.println("付款方银行: " + bank.getBankName());
            System.out.println("账户持有人: " + bank.getAccountHolderName().getFullName());
        }
    }
    
    // 处理汇率信息（跨币种场景）
    if (response.getQuote() != null) {
        System.out.println("汇率货币对: " + response.getQuote().getCurrencyPair());
        System.out.println("客户端汇率: " + response.getQuote().getClientRate());
    }
} else {
    // 处理错误
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 DepositService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new DepositService(config)` 创建实例。
