# 存款（Deposits）模块

## 官方文档

- [WorldFirst 开发者文档 - Deposits Overview](https://docs.worldfirst.com/wfdocs/api-sdk/deposits_overview)
- [query_a_deposit](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_deposit) | [list_deposits](https://docs.worldfirst.com/wfdocs/api-sdk/list_deposits)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 查询存款详情 | `query-deposit/GUIDE.md` | 根据 ID 查询单笔存款的完整详情 | `POST /api/open/v1/deposits/query` |
| 查询存款列表 | `list-deposits/GUIDE.md` | 分页查询存款列表，支持按状态、类型、币种、时间范围过滤 | `POST /api/open/v1/deposits/list` |

## DepositService 说明

`DepositService` 是存款模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `QueryDepositResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `queryDeposit(QueryDepositRequest)` | `QueryDepositRequest` | `QueryDepositResponse` | 查询单笔存款详情 |
| `listDeposits(ListDepositsRequest)` | `ListDepositsRequest` | `ListDepositsResponse` | 分页查询存款列表，支持游标分页 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `DepositService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `DepositService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

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
DepositService depositService = new DepositService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 查询存款详情
QueryDepositRequest queryRequest = new QueryDepositRequest();
queryRequest.setId("dep_2026051901HJK7N");

QueryDepositResponse queryResponse = depositService.queryDeposit(queryRequest);

// 查询存款列表
ListDepositsRequest listRequest = new ListDepositsRequest();
listRequest.setLimit(20);
listRequest.setStatus("SUCCESS");
listRequest.setCurrency("USD");
listRequest.setFromCreatedAt("2026-05-01T00:00:00+08:00");
listRequest.setToCreatedAt("2026-05-22T23:59:59+08:00");

ListDepositsResponse listResponse = depositService.listDeposits(listRequest);
```

### 4. 检查业务结果

```java
if ("S".equals(queryResponse.getResult().getResultStatus())) {
    System.out.println("存款 ID: " + queryResponse.getId());
    System.out.println("状态: " + queryResponse.getStatus());
    System.out.println("入账金额: " + queryResponse.getAmount().getValue());
    
    if (queryResponse.getInitiatingPaymentMethod() != null) {
        System.out.println("付款方类型: " + queryResponse.getInitiatingPaymentMethod().getPaymentAccountType());
    }
}
```

## 注意事项

### query_deposit
- `id` 必填，使用 List Deposits 返回的 ID 或存款 webhook 通知中的 ID
- 响应中的 `amount`、`feeAmount`、`quote` 等字段仅在特定状态下返回（如 SUCCESS、PARTIAL_REFUNDED、REFUNDED）
- `initiatingPaymentMethod` 仅在付款方信息可识别时返回
- `bankDetails` 和 `walletDetails` 根据 `paymentAccountType` 互斥返回

### list_deposits
- 使用**游标分页**（cursor-based pagination），非传统页码分页
- `limit` 取值范围 1-100，必填
- 首次请求不传 `cursor`，后续请求传入上一次响应返回的 `nextCursor`
- 当 `nextCursor` 为空时表示已到最后一页
- `fromCreatedAt` / `toCreatedAt` 必须成对使用，类型为 `String`，使用 ISO 8601 格式
- 游标有效期为 7 天，过期后需重新从第一页开始查询
- `items` 列表中的存款记录按 `createdAt` 降序排列

## 枚举类型说明

### DepositType（存款业务类型）
- `THIRD_PARTY`: B2B 第三方贸易付款
- `MARKETPLACE`: 电商平台结算（如 Amazon、Shopify、eBay）
- `PSP`: 支付服务商结算（如 Stripe、PayPal）
- `SAME_NAME_TOPUP`: 同名银行账户充值
- `CARD_SAME_NAME_TOPUP`: 同名银行卡充值
- `DEVELOPER`: 开发者收入（如 App Store、Google Play）
- `FREELANCER`: 自由职业者收入（如 Upwork、Fiverr）
- `TAX_REFUND`: 退税
- `UNKNOWN`: 未知来源

### DepositStatus（存款生命周期状态）
- `PROCESSING`: 处理中（等待清算、hold release 或风控审核）
- `WAITING_CLAIM`: 等待商户认领存款类型或补充收款信息
- `UNDER_CLAIM`: 商户已提交认领申请，审核中
- `SUCCESS`: 资金已入账，未发生退款
- `PARTIAL_REFUNDED`: 资金已入账，但发生部分退款
- `REFUNDED`: 全额退款（终态）
- `CANCELLED`: 保留终态：入账前取消（当前未使用）

### ReceiveMethodType（收款账户类型）
- `VA`: Global Account（全球账户）
- `RA`: Receiving Account（收款账户）

### AccountType（付款方账户类型）
- `BANK_ACCOUNT`: 银行账户转账
- `DIGITAL_WALLET`: 数字钱包转账

### WalletBrandName（数字钱包品牌）
- `OVO`、`GOPAY`、`DANA`、`LINKAJA`、`SHOPEEPAY`、`GCASH`、`VNPT`、`MOMO`、`ZALOPAY`、`VNPAY`、`JAZZCASH`、`EASYPAISA`、`BKASH`、`TNGD`、`WORLDFIRST`、`ALIPAY`

## 文件结构

```
deposits/
├── README.md
├── java/
│   ├── service/
│   │   └── DepositService.java                    # 存款服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── Quote.java                         # 汇率信息对象
│       │   ├── BankDetail.java                    # 银行账户详情
│       │   ├── WalletDetail.java                  # 数字钱包详情
│       │   ├── InitiatingPaymentMethod.java       # 付款方支付方式
│       │   ├── ReceiveMethod.java                 # 收款方式
│       │   └── DepositRecord.java                 # 存款记录（用于 list 响应）
│       ├── request/
│       │   ├── QueryDepositRequest.java           # 查询存款请求
│       │   └── ListDepositsRequest.java           # 查询存款列表请求
│       └── response/
│           ├── QueryDepositResponse.java          # 查询存款响应
│           └── ListDepositsResponse.java          # 查询存款列表响应
├── query-deposit/
│   └── GUIDE.md                                   # query_deposit 接口接入指引
└── list-deposits/
    └── GUIDE.md                                   # list_deposits 接口接入指引
```
