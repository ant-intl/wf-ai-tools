# 收款人管理模块 (Beneficiary Management)

## 官方文档

- [WorldFirst 开发者文档 - Beneficiary](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/beneficiary)
- [inquiryTemplate](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_template) | [bindBeneficiary](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/bind_beneficiary) | [removeBeneficiary](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/remove_beneficiary) | [editBeneficiary](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/edit_beneficiary) | [inquiryBeneficiaryList](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_beneficiary_list) | [notifyBindBeneficiary](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/notify_bind_beneficiary)

## Pre-Generation Question (MUST ASK)

在生成代码前，**必须**询问用户：

**请问您的 Payout 集成场景是使用卡详情模式还是卡 token 模式？**

| 模式 | 说明 | 需要集成的接口 |
|------|------|---------------|
| 卡详情模式 | 每次代发时直接传递银行卡详情信息 | 仅 `inquiry-template/`（查询卡模版） |
| 卡 token 模式 | 先绑定收款人获取 token，后续代发用 token | 全部 6 个接口 |

## 接口列表

| 接口 | 目录 | 说明 |
|------|------|------|
| 查询卡模版 | `inquiry-template/` | 查询指定国家/币种/账户类型的卡模版字段要求 |
| 绑定收款人 | `bind/` | 绑定收款人到 WF 账户，获取 beneficiaryToken |
| 删除收款人 | `remove/` | 删除已绑定的收款人 |
| 编辑收款人 | `edit/` | 修改收款人昵称 |
| 查询收款人列表 | `inquiry-list/` | 分页查询已绑定的收款人 |
| 绑定收款人通知 | `notify-bind/` | 回调通知：WF 通知集成商收款人绑定结果 |

## 对接流程

### 卡详情模式
仅需集成 `inquiry-template/` 接口，用于查询卡模版字段要求。

### 卡 token 模式
建议按以下顺序对接：
1. 查询卡模版 → 了解需要传哪些字段
2. 绑定收款人 → 获取 beneficiaryToken
3. 接收绑定通知 → 确认收款人绑定结果（回调）
4. 查询收款人列表 → 管理已绑定收款人
5. 编辑/删除收款人 → 按需操作

