# 单据支付模块 (Payout)

## 接口列表

| 接口 | 目录 | 说明 |
|------|------|------|
| 创建代发 | `create-payout/` | 调用 createPayout 接口，代发到第三方银行卡 |
| 查询代发结果 | `inquiry-payout/` | 调用 inquiryPayout 接口，查询代发单状态 |

## 对接流程

建议按 创建代发 → 查询代发结果 的顺序依次对接。

1. 调用 createPayout 发起代发
2. 响应 `resultCode=PROCESSING` 时，调用 inquiryPayout 轮询最终状态
3. 也可通过 `transferNotifyUrl` 接收异步通知

## 注意事项

- 支持两种收款模式（互斥）：明文卡模式（BANK_ACCOUNT_DETAIL）和卡 token 模式（BENEFICIARY_TOKEN）
- `transferFromAmount.value` 与 `transferToAmount.value` 不能同时指定，二选一
- 收款币种为 CNY 时，`businessSceneCode` 必填
- inquiryPayout 有两层结果：`result`（API 调用级别）和 `transferResult`（代发单级别）
- 轮询策略：最多 7 次，指数退避（5/10/20/40/80/160/320 分钟）

