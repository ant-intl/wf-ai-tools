# Query a Payout 接口接入指引

## 接口说明

根据代发单 ID 查询当前状态和完整详情。用于轮询代发完成状态或检查最终结果。

## 官方文档

- [query_a_payout 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_payout)

## 请求地址

`POST /api/open/v1/payouts/query`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601 |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | 代发单 ID（由 create_a_payout 返回） |

### 请求示例

```json
{
  "id": "20260401xxxxx"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果 |
| `id` | string | 代发单 ID |
| `status` | string | 代发状态：`PROCESSING`、`SUCCESS`、`FAIL`、`RETURN` |
| `failureCode` | string | 失败原因码（`status=FAIL` 时返回） |
| `failureMessage` | string | 失败原因描述（`status=FAIL` 时返回） |
| `returnedAmount` | Amount | 退回金额（`status=RETURN` 时返回） |
| `returnedAt` | datetime | 退回时间（`status=RETURN` 时返回） |
| `paymentType` | string | 支付通道类型 |
| `paymentNetwork` | string | 清算网络 |
| `sourceAmount` | Amount | 源币种金额 |
| `payoutAmount` | Amount | 目标币种金额 |
| `feeAmount` | Amount | 总手续费 |
| `transferQuote` | Quote | 汇率报价 |
| `purposeCode` | string | 付款用途码 |
| `reference` | string | 银行转账附言 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "20260401xxxxx",
  "status": "SUCCESS",
  "businessSceneCode": "THIRD_PARTY_PAYOUT",
  "paymentType": "LOCAL",
  "paymentNetwork": "ACH",
  "sourceAmount": { "currency": "HKD", "value": 78900 },
  "payoutAmount": { "currency": "USD", "value": 10000 },
  "feeAmount": { "currency": "USD", "value": 100 },
  "purposeCode": "GDS"
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 成功 |
| `PARAM_ILLEGAL` | F | 参数非法，检查 id |
| `FUND_ORDER_NOT_EXIST` | F | 代发单不存在 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败 |
| `FEATURE_NOT_AVAILABLE` | F | 功能不可用 |

## 示例代码

参考 [references/payouts/payouts/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
payouts/java/
├── service/
│   └── PayoutService.java                 # 薄封装 Service，包含 queryPayout 方法
└── model/
    ├── domain/
    │   ├── Payout.java                    # 代发基础对象
    │   ├── Beneficiary.java         # 代发收款方信息（含 bankDetails/walletDetails）
    │   ├── Payer.java              # 代发付款方信息（引用 common.UserName / common.Address）
    │   ├── BankDetail.java               # 收款方银行账户详情
    │   ├── WalletDetail.java             # 收款方电子钱包账户详情
    │   ├── AdditionalInfo.java           # 附加业务信息
    │   ├── Quote.java                    # 汇率报价
    │   ├── FeeItem.java                  # 手续费明细项（引用 common.Amount）
    │   └── Promotion.java                # 促销信息
    # 说明：Amount / Address / UserName 定义在 common 模块
    ├── request/
    │   └── QueryPayoutRequest.java       # 查询代发请求
    └── response/
        └── PayoutResponse.java           # 代发响应
```

## 集成使用方式

PayoutService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryPayoutRequest` 设置代发单 ID
2. 调用 `PayoutService.queryPayout(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，根据 `status` 字段判断代发最终状态

### 业务代码示例

```java
// 构造请求
QueryPayoutRequest request = new QueryPayoutRequest();
request.setId("20260401xxxxx");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
PayoutResponse response = payoutService.queryPayout(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    String status = response.getStatus();
    if ("SUCCESS".equals(status)) {
        System.out.println("代发成功");
    } else if ("FAIL".equals(status)) {
        System.err.println("代发失败: " + response.getFailureCode());
    } else if ("RETURN".equals(status)) {
        System.out.println("资金退回: " + response.getReturnedAmount().getValue());
    } else {
        System.out.println("处理中，继续轮询");
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 PayoutService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new PayoutService(config)` 创建实例。
