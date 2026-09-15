<p align="center">
  <img alt="WF API 集成技能" src="resources/logos/wf-api-skill-logo.svg" width="80" height="80">
</p>

<p align="center">
  <a href="https://github.com/trending">
    <img src="https://img.shields.io/badge/WorldFirst-API%20%E9%9B%86%E6%88%90-blue?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJ3aGl0ZSI+PHBhdGggZD0iTTEyIDJMMyA3djEwbDkgNSA5LTVWN2wtOS01eiIvPjwvc3ZnPg=="/>
  </a>
</p>

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![技能数量](https://img.shields.io/badge/%E6%8A%80%E8%83%BD-2%20%E4%B8%AA-green)](#%E8%AF%A5%E9%80%89%E5%93%AA%E4%B8%AA%E6%8A%80%E8%83%BD)
[![接口数量](https://img.shields.io/badge/%E6%8E%A5%E5%8F%A3-85%20%E4%B8%AA-orange)](#%E8%AF%A5%E9%80%89%E5%93%AA%E4%B8%AA%E6%8A%80%E8%83%BD)

🌐 **语言 / Language:** [English](README.md) | [中文](README_zh.md)

# WorldFirst API 集成技能

把万里汇（WorldFirst，下称 WF）API 的集成知识——接口规格、RSA256 加验签、请求响应模型、生产级代码模板——打包成 AI 编码助手可读的技能，用自然语言描述需求即可生成可用的集成代码。

本仓库包含**两个技能**，按你账号所对接的 API 版本选择。

## 该选哪个技能

| | **WF API Integration** | **WF API Integration Upgrade** |
|---|---|---|
| 目录 | [`wf-api-integration/`](wf-api-integration/) | [`wf-api-integration-upgrade/`](wf-api-integration-upgrade/) |
| 接口前缀 | `/api/v1/business/*` | `/api/open/v1/*`（Enhanced API） |
| 支持语言 | Java、Golang（Python 仅 common 与账单） | Java |
| 模块 / 接口 | 6 / 26 | 18 / 59 |
| 额外覆盖 | — | 外汇交易、发卡、OAuth 授权、文件、确认函 |
| 技能入口 | [SKILL.md](wf-api-integration/SKILL.md) | [SKILL.md](wf-api-integration-upgrade/SKILL.md) |

> 不确定自己走哪个前缀？查一下 WF 开户材料里的 endpoint 与凭证；两个技能的 `SKILL.md` 都列明了各自的 base URL。

---

## WF API Integration

面向 `/api/v1/business/*` 的资金与账户能力。Java、Golang 覆盖全部模块；Python 仅覆盖 `common` 与账单管理。

| 业务域 | 模块 | 接口数 | 能力 |
|---|---|---|---|
| 资金划转 | [转账](wf-api-integration/references/transfer/README.md) | 4 | 汇率手续费咨询、WF 账户间划转、结果查询、结果通知 |
| 付款 | [全球分发](wf-api-integration/references/payout/README.md) | 4 | 代发咨询、付款到银行卡/电子钱包、结果查询、结果通知 |
| 收款人 | [收款人管理](wf-api-integration/references/beneficiary/README.md) | 6 | 卡模板查询、绑定/删除/编辑、列表查询、绑定结果通知 |
| 账户 | [账户管理](wf-api-integration/references/account-management/README.md) | 7 | 账户信息、余额、结汇额度、子账号、店铺，及充值与余额变动通知 |
| 账单与报告 | [账单管理](wf-api-integration/references/statement-management/README.md) | 2 | 交易流水列表与详情（Java、Golang、Python） |
| 贸易单证 | [交易信息管理](wf-api-integration/references/trade-order/README.md) | 3 | 上传交易订单（B2C 结汇 / B2B 关联）、结果查询、异步通知 |

接口级参数、示例与错误码见各模块下 `<接口>/GUIDE.md`；模块级流程、枚举与注意事项见模块 `README.md`。

---

## WF API Integration Upgrade

面向新版 Enhanced API（`/api/open/v1/*`），仅 Java。每个模块提供薄封装 `*Service`、请求响应模型与逐接口指引。

| 业务域 | 模块 | 接口数 | 能力 |
|---|---|---|---|
| 授权 | [oauth2](wf-api-integration-upgrade/references/authorization/oauth2/README.md) | 3 | 发起授权、换取访问令牌、撤销令牌 |
| 账户 | [balance](wf-api-integration-upgrade/references/accounts/balance/README.md) | 2 | 查询账户余额、余额变动历史 |
| 账户 | [global-accounts](wf-api-integration-upgrade/references/accounts/global-accounts/README.md) | 5 | 开通、查询、更新、关闭全球收款账户，及列表分页 |
| 关联账户 | [connected-accounts](wf-api-integration-upgrade/references/connected/connected-accounts/README.md) | 3 | 创建关联账户（二级商户入驻）、查询详情、列表查询 |
| 收款 | [deposits](wf-api-integration-upgrade/references/receiving/deposits/README.md) | 2 | 存款记录列表与单笔详情 |
| 账单与报告 | [statement-report](wf-api-integration-upgrade/references/reporting/statement-report/README.md) | 2 | 对账单列表与单笔详情 |
| 收款人 | [beneficiaries](wf-api-integration-upgrade/references/payouts/beneficiaries/README.md) | 7 | 卡模板查询、创建/查询/列表/更新/删除收款人、信息校验 |
| 付款 | [payouts](wf-api-integration-upgrade/references/payouts/payouts/README.md) | 3 | 代发咨询、发起代发、结果查询 |
| 外汇 | [quote](wf-api-integration-upgrade/references/foreign-exchange/quote/README.md) | 3 | 实时汇率、历史汇率、创建绑定报价 |
| 外汇 | [deal](wf-api-integration-upgrade/references/foreign-exchange/deal/README.md) | 5 | 创建/查询/取消交易、交易列表、保证金追缴记录 |
| 外汇 | [settlement](wf-api-integration-upgrade/references/foreign-exchange/settlement/README.md) | 3 | 创建结算、查询结算、结算列表 |
| 外汇 | [reference](wf-api-integration-upgrade/references/foreign-exchange/reference/README.md) | 2 | 交易日历、支持的币种对查询 |
| 发卡 | [cardholders](wf-api-integration-upgrade/references/issuing/cardholders/README.md) | 4 | 持卡人注册与 KYC 审核、查询、列表、删除 |
| 发卡 | [budgets](wf-api-integration-upgrade/references/issuing/budgets/README.md) | 6 | 预算账户创建/查询/列表、入金、出金、每日余额 |
| 发卡 | [otp-management](wf-api-integration-upgrade/references/issuing/otp-management/README.md) | 2 | 查询、更新 3DS OTP 是否通过 API 投递 |
| 支撑服务 | [file](wf-api-integration-upgrade/references/supporting-service/file/README.md) | 2 | base64 上传文件、获取临时下载链接 |
| 支撑服务 | [confirmation-letters](wf-api-integration-upgrade/references/supporting-service/confirmation-letters/README.md) | 2 | 创建确认函下载任务、查询任务状态与链接 |
| 贸易单证 | [tradeOrders](wf-api-integration-upgrade/references/supporting-service/tradeOrders/README.md) | 3 | 提交贸易订单批次、查询处理状态、查询可用结算额度 |

`SKILL.md` 中的[模块与接口索引](wf-api-integration-upgrade/SKILL.md#模块与接口索引)是权威路由表；[快速决策树](wf-api-integration-upgrade/SKILL.md#快速决策树)把业务问题映射到模块，模块 `README.md` 的接口列表再把模块映射到具体接口目录。

---

## 快速开始

1. **定位模块** — 用一句话描述业务目标（如"给供应商银行卡付款"），让技能的决策树选出模块，或直接从上面的模块表进入。
2. **读接口指引** — `<模块>/<接口>/GUIDE.md` 给出 endpoint、请求响应字段、枚举、错误码与示例。接口目录名以模块 README 的接口列表为准，不要按接口名直译拼路径。
3. **提供环境信息** — 目标工程路径、语言、基础包名（生成时替换 `{basePackage}` 占位符），以及 clientId、base URL、RSA 公私钥文件路径。密钥一律通过路径或环境变量注入，不写入代码。
4. **生成后验收** — 技能会执行生成后检查（模型字段一致性、编译、包名与 import）。上生产前请自行复核业务逻辑。

## 仓库结构

```
.
├── wf-api-integration/                 # 技能一 — /api/v1/business/*
│   ├── SKILL.md                        # 路由、流程、安全规则
│   └── references/
│       ├── common/                     # 配置、加验签、HTTP 客户端、模型（Java/Golang/Python）
│       └── <模块>/<接口>/              # GUIDE.md + java/ golang/ python/
└── wf-api-integration-upgrade/         # 技能二 — /api/open/v1/*
    ├── SKILL.md                        # 决策树、关键词路由、模块索引
    └── references/
        ├── common/java/                # WfClientConfig、WfSignatureUtil、WfApiClient、Result、WfErrorCode、WfException、Amount/Address/UserName
        └── <业务域>/<模块>/            # GUIDE.md + java/{service,model/{domain,request,response}}
```

## 共同底座

两个技能使用同一套 RSA256 方案 —— `SHA256withRSA`、PKCS#8 私钥、X.509 WF 公钥、签名头 `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>`。所有响应与异步通知都必须验签，Upgrade 技能已在 HTTP 客户端内强制验签，验签失败直接抛异常。

适用于任何生成代码的安全底线：

- **密钥不入代码** — 通过文件路径、环境变量或密钥管理服务加载，`.pem` 文件不得提交版本库。
- **写操作幂等** — 资金类操作携带唯一请求 ID；用同一 ID 且参数完全一致重试必须安全。
- **结果不可假定** — 最终状态以查询接口或异步通知为准；结果未知时先查证，禁止换新 ID 重复发起。
- **环境隔离** — 沙箱与生产使用不同 clientId 与密钥对；仅走 HTTPS；密钥与完整响应体不得出现在日志中。

## 注意事项

- 生成代码是起点而非终点：业务逻辑、金额与币种精度、状态流转需自行复核。
- 若技能文档与 WF 线上文档不一致，以线上文档为准，并反馈以便修正技能内容。
- 新增模块或接口放在所属技能的 `references/` 下，记得同步更新该技能 `SKILL.md` 的决策树、关键词表与模块索引。

## 许可

[MIT](LICENSE)
