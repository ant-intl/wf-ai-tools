# 账单查询模块 (Statement Inquiry)

## 接口列表

| 接口 | 目录 | 说明 |
|------|------|------|
| 查询账单流水 | `inquiry-statement-list/` | 分页查询 WF 账户交易流水 |
| 查询账单详情 | `inquiry-statement-detail/` | 查询指定账单流水的详细信息 |

## 典型调用流程

1. 调用 `inquiryStatementList` 获取账单流水列表，取得 `accountingBizNo`
2. 以 `accountingBizNo` 为入参调用 `inquiryStatementDetail` 获取单笔流水详情

## 注意事项

- `pageSize` 固定为 10，不允许调用方修改
- `pageNumber` 范围 1-50
- 当 `fuzzyName` 为空时，`startTime` 与 `endTime` 的间隔不超过 100 天
- 时间格式：ISO 8601 with timezone，如 `2024-01-01T00:00:00+08:00`
- `inquiryStatementDetail` 的 `accountingBizNo` 必须通过 `inquiryStatementList` 获取

