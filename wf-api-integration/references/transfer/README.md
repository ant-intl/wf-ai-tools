# 万里汇转账模块 (Transfer)

## 接口列表

| 接口 | 目录 | 说明 |
|------|------|------|
| 咨询转账 | `consult-transfer/` | 调用 consultTransfer 接口，在转账前获取汇率、手续费等信息 |
| 户到户转账 | `create-transfer/` | 调用 createTransfer 接口，在万里汇账户之间转账 |
| 查询转账结果 | `inquiry-transfer/` | 调用 inquiryTransfer 接口，查询转账结果 |
| 转账结果通知 | `notify-transfer/` | 接收 notifyTransfer 回调，处理万里汇推送的转账结果通知 |

## 对接流程

1. （可选）调用 consultTransfer 咨询转账信息（跨币种场景建议先咨询汇率）
2. 调用 createTransfer 发起转账
3. 响应 `resultCode=PROCESSING` 时，需调用 inquiryTransfer 轮询最终状态
4. 转账结果也会通过 notifyTransfer 异步通知，集成商需实现回调接口接收通知

## 注意事项

- 转账为异步接口，`PROCESSING` 状态必须轮询
- `transferRequestId` 是幂等键，相同 ID + 不同 body → `REPEAT_REQ_INCONSISTENT`
- `businessSceneCode` 在主/子账号余额互转时必填 `MULTI_ACCOUNT_TRANSFER`
- Amount 的 `value` 为 Long，最小货币单位（2 位小数币种 × 100，0 位小数币种 × 1）

