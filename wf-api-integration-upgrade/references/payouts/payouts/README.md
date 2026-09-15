# 代发（Payouts）模块

## 官方文档

- [WorldFirst 开发者文档 - Payouts](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 咨询代发 | `consult-a-payout/GUIDE.md` | 预校验代发并获取手续费和汇率估算，不实际移动资金 | `POST /api/open/v1/payouts/consult` |
| 创建代发 | `create-a-payout/GUIDE.md` | 发起代发到收款方银行账户或电子钱包 | `POST /api/open/v1/payouts/create` |
| 查询代发 | `query-a-payout/GUIDE.md` | 根据代发单 ID 查询当前状态和详情 | `POST /api/open/v1/payouts/query` |

## PayoutService 说明

`PayoutService` 是代发模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（`PayoutResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `consultPayout(ConsultPayoutRequest)` | `ConsultPayoutRequest` | `PayoutResponse` | 咨询代发，获取费用和汇率估算 |
| `createPayout(CreatePayoutRequest)` | `CreatePayoutRequest` | `PayoutResponse` | 创建代发，发起资金转移 |
| `queryPayout(QueryPayoutRequest)` | `QueryPayoutRequest` | `PayoutResponse` | 查询代发状态和详情 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `PayoutService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `PayoutService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

## 对接流程

### 同币种代发

1. 调用 `createPayout` 发起代发（无需 consult）
2. 响应 `status=PROCESSING` 时，调用 `queryPayout` 轮询最终状态
3. 最终状态为 `SUCCESS`（成功）或 `FAIL`（失败）

### 跨币种代发

1. 调用 `consultPayout` 获取汇率报价（`quoteId`）
2. 将 `quoteId` 传入 `createPayout` 的 `transferQuote.quoteId` 锁定汇率
3. 响应 `status=PROCESSING` 时，调用 `queryPayout` 轮询最终状态

### 轮询策略

- 最多 7 次，指数退避：5/10/20/40/80/160/320 分钟
- 最终状态：`SUCCESS`（成功）、`FAIL`（失败）、`RETURN`（退回）

## 注意事项

- `requestId` 为幂等键，每次创建代发必须唯一（建议使用 UUID）
- `sourceAmount.value` 与 `payoutAmount.value` 不能同时指定，二选一
- `sourceAmount.currency` 和 `payoutAmount.currency` 都必须指定
- 跨币种代发必须先调用 `consultPayout` 获取 `quoteId`，`quoteId` 有过期时间
- `purposeCode` 为必填字段
- 收款方信息提供方式三选一：`beneficiaryId`（已注册收款人）、`bankDetails`（直接传卡详情）、`walletDetails`（钱包账户）
- 资金类接口结果不可假定：必须通过 `queryPayout` 或异步通知确认最终状态

## 枚举参考

### PayoutStatus

| 值 | 说明 |
|----|------|
| `PROCESSING` | 代发处理中 |
| `SUCCESS` | 代发成功 |
| `FAIL` | 代发失败，检查 `failureCode` 和 `failureMessage` |
| `RETURN` | 资金退回，检查 `returnedAmount` 和 `returnedAt` |

### BusinessSceneCode

| 值 | 说明 |
|----|------|
| `PAYOUT` | 代理代发 |
| `THIRD_PARTY_TRANSFER` | 转账到第三方银行账户 |
| `THIRD_PARTY_PAYOUT` | 代发到第三方卡 |
| `SAME_NAME_TRANSFER` | 同名银行账户提现 |
| `SAME_NAME_PAYOUT` | 同名卡提现 |
| `BENEFICIARY_TOKEN_TRANSFER` | 已注册收款人转账 |
| `PURCHASE_TRANSFER` | 供应商采购代发 |

### PurposeCode

| 值 | 说明 |
|----|------|
| `GDS` | 货物付款 |
| `TXS` | 税务付款 |
| `ACM` | 代理佣金 |
| `GST` | 服务付款 |
| `COM` | 佣金付款 |
| `TOA` | 同名提现 |
| `SAL` | 工资薪酬 |

### PaymentNetwork

支持 ACH、SEPA、SEPA_INSTANT、FASTER_PAYMENTS、CHAPS、BACS、FPS、CHATS、SWIFT、FEDWIRE、TARGET2、NPP、BECS、FAST 等多种清算网络，完整列表见 [官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview)。

## 文件结构

```
payouts/
├── README.md
├── java/
│   ├── service/
│   │   └── PayoutService.java                 # 代发服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── Payout.java                    # 代发基础对象
│       │   ├── Beneficiary.java         # 代发收款方信息（含 bankDetails/walletDetails）
│       │   ├── Payer.java              # 代发付款方信息（name 引用 common.UserName，address 引用 common.Address）
│       │   ├── BankDetail.java               # 收款方银行账户详情
│       │   ├── WalletDetail.java             # 收款方电子钱包账户详情
│       │   ├── AdditionalInfo.java           # 附加业务信息
│       │   ├── Quote.java                    # 汇率报价
│       │   ├── FeeItem.java                  # 手续费明细项（feeAmount 引用 common.Amount）
│       │   └── Promotion.java                # 促销信息
│       # 说明：Amount / Address / UserName 定义在 common 模块，不再重复
│       ├── request/
│       │   ├── ConsultPayoutRequest.java     # 咨询代发请求
│       │   ├── CreatePayoutRequest.java      # 创建代发请求
│       │   └── QueryPayoutRequest.java       # 查询代发请求
│       └── response/
│           └── PayoutResponse.java           # 代发响应（consult/create/query 共用）
├── consult-a-payout/
│   └── GUIDE.md                              # consult_a_payout 接口接入指引
├── create-a-payout/
│   └── GUIDE.md                              # create_a_payout 接口接入指引
└── query-a-payout/
    └── GUIDE.md                              # query_a_payout 接口接入指引
```
