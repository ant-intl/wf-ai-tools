# 余额查询模块 (Balance Inquiry)

## 接口列表

| 接口 | 目录 | 说明 |
|------|------|------|
| 查询余额 | `inquiry-balance/` | 查询 WF 账户余额，支持按币种和余额类型过滤 |

## 注意事项

- `currencyList` 为空则返回所有币种余额
- `balanceTypes` 支持 `NORMAL_BALANCE`（默认）、`SAME_NAME_TOP_UP_BALANCE`、`BUDGET_BALANCE`
- 当 `balanceTypes` 包含 `BUDGET_BALANCE` 时，`budgetAccountId` 必填
- 余额 `value` 为最小货币单位的整数（如 USD 100.00 → value = 10000）

