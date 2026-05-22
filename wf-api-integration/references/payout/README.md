# 全球分发模块 (Payout)

## 官方文档

- [WorldFirst 开发者文档 - Payout](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/payout)
- [consultPayout](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/consult_payout) | [createPayout](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/create_payout) | [inquiryPayout](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_payout) | [notifyPayout](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/notify_payout)

## 接口列表


| 接口         | 目录              | 说明                                                             |
| ------------ | ----------------- | ---------------------------------------------------------------- |
| 咨询代发汇率 | `consult-payout/` | 调用 consultPayout 接口,咨询费用、校验卡模版、获取跨币种汇率报价 |
| 创建代发     | `create-payout/`  | 调用 createPayout 接口，代发到银行卡或电子钱包                   |
| 查询代发结果 | `inquiry-payout/` | 调用 inquiryPayout 接口，查询代发单状态                          |
| 代发结果通知 | `notify-payout/`  | 接收 notifyPayout 回调，万里汇主动通知转账结果                   |

## 对接流程

### 同币种代发

1. 调用 createPayout 发起代发
2. 响应 `resultCode=PROCESSING` 时，调用 inquiryPayout 轮询最终状态
3. 也可通过 `transferNotifyUrl` 接收异步通知

### 跨币种代发

1. 调用 consultPayout 获取汇率报价（quoteId）
2. 将 quoteId 传入 createPayout 的 `transferToDetail.transferQuote.quoteId`
3. 响应 `resultCode=PROCESSING` 时，调用 inquiryPayout 轮询最终状态

## 字段参考

完整的嵌套对象字段定义见 [field-reference.md](field-reference.md)。

## 注意事项

- 支持五种支付方式（互斥）：
  - 卡详情模式（BANK_ACCOUNT_DETAIL）— 直接传银行卡详情
  - 卡 token 模式（BENEFICIARY_TOKEN）— 使用已绑定收款人的 token
  - 支付宝账户详情模式（ALIPAY_CN_DETAIL）— 代发到支付宝账户
  - 关联支付宝钱包模式（REFERENCE_ALIPAY_CN）— 代发到关联的支付宝钱包
  - 钱包账户模式（WALLET_ACCOUNT_DETAIL）— 代发到钱包账户
- `transferFromAmount.value` 与 `transferToAmount.value` 不能同时指定，二选一
- 收款币种为 CNY 时，`businessSceneCode` 必填
- 跨币种代发必须先调用 consultPayout 获取 quoteId，quoteId 有过期时间（quoteExpiryTime）
- inquiryPayout 有两层结果：`result`（API 调用级别）和 `transferResult`（代发单级别）
- 轮询策略：最多 7 次，指数退避（5/10/20/40/80/160/320 分钟）
