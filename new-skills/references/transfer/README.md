# 转账模块 (Transfer)

## 接口列表

| 接口 | 目录 | 说明 |
|------|------|------|
| 户到户转账 | `create-transfer/` | 调用 createTransfer 接口，在万里汇账户之间转账 |

## 对接流程

1. 调用 createTransfer 发起转账
2. 响应 `resultCode=PROCESSING` 时，需调用 inquiryTransfer 轮询最终状态
3. 转账结果也会通过 notifyTransfer 异步通知

## 注意事项

- 转账为异步接口，`PROCESSING` 状态必须轮询
- `transferRequestId` 是幂等键，相同 ID + 不同 body → `REPEAT_REQ_INCONSISTENT`
- `businessSceneCode` 在主/子账号余额互转时必填 `MULTI_ACCOUNT_TRANSFER`
- Amount 的 `value` 为 Long，最小货币单位（2 位小数币种 × 100，0 位小数币种 × 1）

