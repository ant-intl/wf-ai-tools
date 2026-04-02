# 账单查询模块 (Statement Inquiry)

## 接口列表

| 接口 | 目录 | 说明 |
|------|------|------|
| 查询账单流水 | `inquiry-statement-list/` | 分页查询 WF 账户交易流水 |

## 注意事项

- `pageSize` 固定为 10，不允许调用方修改
- `pageNumber` 范围 1-50
- 当 `fuzzyName` 为空时，`startTime` 与 `endTime` 的间隔不超过 100 天
- 时间格式：ISO 8601 with timezone，如 `2024-01-01T00:00:00+08:00`

