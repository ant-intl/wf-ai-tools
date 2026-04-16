# 账户管理模块 (Account Inquiry)

## 接口列表

| 接口 | 目录 | 说明 |
|------|------|------|
| 查询账户信息 | `inquiry-account/` | 查询 WF 账户信息，包括账户类型、账号、激活状态、币种、银行账户等 |
| 查询余额 | `inquiry-balance/` | 查询 WF 账户余额，支持按币种和余额类型过滤 |
| 查询结汇额度 | `inquiry-available-quota/` | 查询可申报的结汇额度，支持四种累计方式 |
| 查询子账号信息 | `inquiry-subuser/` | 查询万里汇主账号及子账号信息，支持分页 |

## 注意事项

### inquiryAccount
- `accountType` 为必填字段，支持 `RECEIVE_ACCOUNT`、`VIRTUAL_ACCOUNT`、`ALIPAY_WALLET`、`ALIPAY_SHADOW_WALLET`、`ALIPAY_ORIGIN_WALLET`
- `RECEIVE_ACCOUNT` / `ALIPAY_WALLET` 时 `referenceCustomerId` 必填
- `VIRTUAL_ACCOUNT` 时 `accessToken` 必填
- `ALIPAY_SHADOW_WALLET` 时 `accountId` 必填

### inquiryBalance
- `currencyList` 为空则返回所有币种余额
- `balanceTypes` 支持 `NORMAL_BALANCE`（默认）、`SAME_NAME_TOP_UP_BALANCE`、`BUDGET_BALANCE`
- 当 `balanceTypes` 包含 `BUDGET_BALANCE` 时，`budgetAccountId` 必填
- 余额 `value` 为最小货币单位的整数（如 USD 100.00 → value = 10000）

### inquiryAvailableQuota
- 支持四种累计方式：`USER_ID`、`RECEIVING_ACCOUNT`、`VIRTUAL_ACCOUNT`、`BENEFICIARY`
- `BENEFICIARY` 方式时 `tradeType` 必填（`GOODS` 或 `SERVICE`）

### inquirySubuser
- `pageSize` 和 `pageNumber` 均为必填，`pageNumber` 从 1 开始
- 仅**主账号**可调用，子账号调用将返回 `USER_ACCOUNT_NOT_PRIMARY`
- `primaryUserInformation` 返回主账号信息，`userInformations` 返回当前页子账号列表
- 子账号使用 `userNickName` 字段表示昵称，主账号使用 `userName` 字段

