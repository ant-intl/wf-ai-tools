# 交易信息管理模块 (Trade Order Management)

## 官方文档

- [WorldFirst 开发者文档 - Trade Order](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/trade_order)
- [submitTradeOrder](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/submit_trade_order) | [inquiryTradeOrder](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_trade_order) | [notifyTradeOrder](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/notify_trade_order)

## Pre-Generation Question (MUST ASK)

在生成代码前，**必须**询问用户：

**请问需要支持哪些业务场景？**

| 场景 | 说明 | 需要生成的接口 |
|------|------|---------------|
| PAY_INTO_CHINA（B2C 结汇） | 上传 B2C 交易订单用于结汇 | 全部三个接口：submitTradeOrder、inquiryTradeOrder、notifyTradeOrder |
| CREATE_B2B_ORDERS（B2B 订单关联） | 上传 B2B 订单关联资金 | 仅 submitTradeOrder |
| 两者都需要 | 生成完整套件 | 全部三个接口，inquiryTradeOrder 和 notifyTradeOrder 标注仅适用于 PAY_INTO_CHINA |

## 接口列表

| 接口 | 目录 | 说明 |
|------|------|------|
| 提交交易订单 | `submit-trade-order/` | 调用 submitTradeOrder 接口，上传交易订单（B2C / B2B） |
| 查询订单结果 | `inquiry-trade-order/` | 调用 inquiryTradeOrder 接口，查询上传结果（仅 PAY_INTO_CHINA） |
| 订单回调通知 | `notify-trade-order/` | 处理 WF 异步回调通知（仅 PAY_INTO_CHINA） |

## 对接流程

### PAY_INTO_CHINA（B2C 结汇）

1. 调用 submitTradeOrder 上传交易订单（sceneCode=`PAY_INTO_CHINA`）
2. 若 resultStatus=U（UNKNOWN_EXCEPTION），使用原 requestId 重试
3. 若需主动查询结果，调用 inquiryTradeOrder（使用相同 requestId）
4. 或通过 notifyUrl 接收 WF 异步回调通知

### CREATE_B2B_ORDERS（B2B 订单关联）

1. 调用 submitTradeOrder 上传订单（sceneCode=`CREATE_B2B_ORDERS`）
2. 响应中获取 acceptOrderId

## 字段参考

完整的嵌套对象字段定义见 [field-reference.md](field-reference.md)。

## 注意事项

- sceneCode 决定必填字段集合：PAY_INTO_CHINA 需传 merchant/seller/buyer；CREATE_B2B_ORDERS 需传 bizContractInfo/isUsedForExchange
- tradeOrders 上限：B2C 最多 100 笔，B2B 最多 10 笔，超出需分批提交
- inquiryTradeOrder 必须使用与 submitTradeOrder 相同的 requestId
- notifyTradeOrder 必须先验签，验签失败不能返回 SUCCESS
- notifyTradeOrder 以 requestId 做幂等判断，防止重复处理
- tradeCountry / deliverCountry 不能为 BY（白俄罗斯）或 RU（俄罗斯）
