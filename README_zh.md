<p align="center">
  <img alt="WF API Integration Skill" src="resources/logos/wf-api-skill-logo.svg" width="80" height="80">
</p>

<p align="center">
  <a href="https://github.com/trending">
    <img src="https://img.shields.io/badge/WorldFirst-API%20Integration-blue?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJ3aGl0ZSI+PHBhdGggZD0iTTEyIDJMMyA3djEwbDkgNSA5LTVWN2wtOS01eiIvPjwvc3ZnPg=="/>
  </a>
</p>

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Version](https://img.shields.io/badge/version-1.0.0-brightgreen)](CHANGELOG.md)
[![Languages](https://img.shields.io/badge/Languages-Java%20|%20Golang%20|%20Python-orange)](wf-api-integration/references/)

🌐 **Language / 语言:** [English](README.md) | [中文](README_zh.md)

# WF API 集成技能

一个用于将万里汇（WorldFirst）API 与 LLM 或 Agent 框架集成的技能。支持 Java、Golang 和 Python 代码生成，覆盖转账、全球分发、收款人管理、账户管理、账单管理和交易信息管理——内置 RSA256 签名和生产级模板。

**[快速开始](#快速开始)** | **[支持的模块](#支持的模块)** | **[浏览参考代码](wf-api-integration/references/)**

---

## 目录

- [解决什么问题](#解决什么问题)
- [支持的模块](#支持的模块)
- [快速开始](#快速开始)
- [目录结构](#目录结构)
- [模块详情](#模块详情)
- [公共基础设施](#公共基础设施)
- [签名算法](#签名算法)
- [重要注意事项](#重要注意事项)

---

## 解决什么问题

万里汇提供了一套完整的跨境支付、资金转账和账户管理 API。然而，集成这些 API 涉及复杂的步骤，包括 RSA256 签名、请求/响应模型构建、错误处理和多语言样板代码。

本技能将 WF API 集成知识打包为 AI 可读取并生成代码的标准化模板。开发者可以在 Vibe Coding 模式下集成 WF API——只需用自然语言描述需求，技能即可帮助 AI：

- **选择正确的 API** —— 根据业务场景匹配最合适的接口
- **生成生产级代码** —— 包含正确的签名、错误处理和编码规范
- **避免常见陷阱** —— 如硬编码密钥、缺少签名验证、幂等性问题等

## 支持的模块

| 模块 | 描述 | 支持语言 |
|------|------|----------|
| **转账（Transfer）** | 万里汇账户间资金转账（咨询 / 创建 / 查询 / 通知） | Java, Golang |
| **全球分发（Payout）** | 向银行卡或电子钱包付款（咨询 / 创建 / 查询 / 通知） | Java, Golang |
| **收款人管理（Beneficiary）** | 收款人管理（模板查询 / 绑定 / 解绑 / 编辑 / 列表查询 / 绑定通知） | Java, Golang |
| **账户管理（Account）** | 查询账户信息、余额、额度、子账户、店铺；接收入账/余额变动通知 | Java, Golang |
| **账单管理（Statement）** | 查询交易账单列表和详情 | Java, Golang, Python |
| **交易信息管理（Trade Order）** | 提交交易订单（B2C 结算 / B2B 关联），查询结果，处理异步通知 | Java, Golang |

> **注意**：Python 目前仅支持公共模块（`common/`）和账单管理（`statement-management/`）。其他模块暂无 Python 模板。

## 快速开始

### 1. 选择模块

确定你需要哪个 WF API 模块：

- **转账（Transfer）** —— 万里汇账户间资金转移
- **全球分发（Payout）** —— 向第三方银行账户或电子钱包付款（发工资、供应商付款等）
- **收款人管理（Beneficiary）** —— 管理收款人银行卡信息
- **账户管理（Account）** —— 查询账户余额、信息、额度、子账户、店铺，接收通知
- **账单管理（Statement）** —— 查看交易记录和对账
- **交易信息管理（Trade Order）** —— 上传交易订单用于 B2C 结算或 B2B 关联

### 2. 选择接口

每个模块包含特定的接口。完整列表请参见下方[模块详情](#模块详情)。

### 3. 提供必要信息

代码生成前，你需要提供：

- **项目路径** —— 代码生成的目标位置
- **编程语言** —— Java、Golang 或 Python
- **基础包名 / 模块名** —— 例如 `com.example.project`（Java）、`github.com/example/project`（Go）、`wf_integration`（Python）
- **WF 凭证** —— Client ID、User ID、API 基础 URL、RSA 密钥路径

### 4. 生成并验证

技能根据你的配置生成代码，然后执行生成后检查清单以确保正确性。

## 目录结构

```
wf-api-integration/
├── SKILL.md                          # 技能定义和指令
├── README.md                         # 本文件
└── references/                       # 所有参考代码和指南
    ├── common/                       # 公共基础设施（签名、配置、HTTP 客户端、模型）
    │   ├── golang/
    │   ├── java/
    │   ├── python/
    │   └── README.md
    ├── transfer/                     # 转账模块
    │   ├── consult-transfer/
    │   ├── create-transfer/
    │   ├── inquiry-transfer/
    │   ├── notify-transfer/
    │   └── README.md
    ├── payout/                       # 全球分发模块
    │   ├── consult-payout/
    │   ├── create-payout/
    │   ├── inquiry-payout/
    │   ├── notify-payout/
    │   ├── field-reference.md
    │   └── README.md
    ├── beneficiary/                  # 收款人管理
    │   ├── inquiry-template/
    │   ├── bind/
    │   ├── remove/
    │   ├── edit/
    │   ├── inquiry-list/
    │   ├── notify-bind/
    │   └── README.md
    ├── account-management/              # 账户管理
    │   ├── inquiry-account/
    │   ├── inquiry-balance/
    │   ├── inquiry-available-quota/
    │   ├── inquiry-subuser/
    │   ├── inquiry-store/
    │   ├── notify-vostro/
    │   ├── notify-balance-change/
    │   └── README.md
    ├── statement-management/            # 账单管理
    │   ├── inquiry-statement-list/
    │   ├── inquiry-statement-detail/
    │   └── README.md
    └── trade-order/                  # 交易信息管理
        ├── submit-trade-order/
        ├── inquiry-trade-order/
        ├── notify-trade-order/
        ├── field-reference.md
        └── README.md
```

每个接口目录包含：
- `GUIDE.md` —— API 规范和集成指南
- `java/` —— Java 代码模板
- `golang/` —— Golang 代码模板
- `python/` —— Python 代码模板（部分接口提供）

## 模块详情

### 转账（Transfer）

| 接口 | 描述 |
|------|------|
| consult-transfer | 转账前获取汇率和手续费 |
| create-transfer | 在万里汇账户间转账 |
| inquiry-transfer | 查询转账结果（轮询 PROCESSING 状态） |
| notify-transfer | 接收异步转账结果通知（WF → 接入方） |

### 全球分发（Payout）

| 接口 | 描述 |
|------|------|
| consult-payout | 咨询手续费、验证卡模板、获取汇率报价 |
| create-payout | 向银行卡或电子钱包付款 |
| inquiry-payout | 查询付款状态 |
| notify-payout | 接收异步付款结果通知（WF → 接入方） |

### 收款人管理（Beneficiary Management）

| 接口 | 描述 |
|------|------|
| inquiry-template | 根据国家/币种查询卡模板字段要求 |
| bind | 将收款人绑定到万里汇账户 |
| remove | 解绑已绑定的收款人 |
| edit | 更新收款人昵称 |
| inquiry-list | 分页查询已绑定的收款人列表 |
| notify-bind | 接收收款人绑定结果通知（WF → 接入方） |

### 账户管理（Account Management）

| 接口 | 描述 |
|------|------|
| inquiry-account | 查询万里汇账户信息（类型、账号、激活状态、币种） |
| inquiry-balance | 查询账户余额，支持按币种和余额类型筛选 |
| inquiry-available-quota | 查询可用结汇额度（4 种累计方式） |
| inquiry-subuser | 分页查询主子账户信息 |
| inquiry-store | 分页查询店铺信息及关联账户 |
| notify-vostro | 接收入账/预付款通知（WF → 接入方） |
| notify-balance-change | 接收余额变动通知（WF → 接入方） |

### 账单管理（Statement Management）

| 接口 | 描述 |
|------|------|
| inquiry-statement-list | 分页查询交易账单列表 |
| inquiry-statement-detail | 查询指定账单记录的详细信息 |

### 交易信息管理（Trade Order）

| 接口 | 描述 |
|------|------|
| submit-trade-order | 上传交易订单（B2C 结算 / B2B 关联） |
| inquiry-trade-order | 查询上传结果（仅 PAY_INTO_CHINA） |
| notify-trade-order | 处理异步通知（仅 PAY_INTO_CHINA） |

## 公共基础设施

所有模块共享以下位于 `references/common/` 的组件：

| 组件 | Java | Golang | Python | 描述 |
|------|------|--------|--------|------|
| WfConfig | `config/WfConfig.java` | `config/config.go` | `config/wf_config.py` | 配置管理（clientId、baseUrl、密钥路径、超时时间） |
| WfSigner | `signer/WfSigner.java` | `signer/signer.go` | `signer/wf_signer.py` | RSA256 签名与验签 |
| HttpClient | `util/WfHttpClientUtil.java` | `util/wf_http_client.go` | `util/wf_http_client.py` | 带签名注入和响应验签的 HTTP 客户端 |
| Result | `model/response/Result.java` | `model/response/result.go` | `model/response/result.py` | 统一响应对象 |
| ErrorCode | `model/exception/WfErrorCode.java` | `model/exception/error_code.go` | `model/exception/wf_error_code.py` | 错误码定义 |
| Exception | `model/exception/WfException.java` | `model/exception/wf_exception.go` | `model/exception/wf_exception.py` | 业务异常类 |

## 签名算法

所有 WF API 使用 RSA256 签名。签名内容格式：

```
POST {apiPath}\n{clientId}.{requestTime}.{requestBody}
```

各语言的签名工具代码请参见 `references/common/`。

## 重要注意事项

1. **明确业务场景** —— 向 AI 描述需求时，请指定具体的业务场景（如"万里汇账户间转账"或"向银行卡付款"），避免歧义。
2. **务必审查生成的代码** —— 部署到生产环境前，请自行验证逻辑正确性。
3. **禁止硬编码密钥** —— RSA 私钥、clientId 和 secretKey 必须通过文件路径、环境变量或密钥管理服务加载，绝不能存储在源代码中。
4. **始终验证签名** —— 对每个 WF API 响应和异步通知都要验证签名，防止中间人攻击。
5. **确保幂等性** —— 资金类操作使用唯一的 `transferRequestId` / `payoutRequestId`，防止重试导致重复扣款。
6. **仅使用 HTTPS** —— 所有 API 请求必须使用 HTTPS，禁止使用 HTTP。
7. **不要假设最终结果** —— 发起转账或付款后，必须通过查询接口或异步通知确认最终状态，不能仅依赖请求响应状态。
8. **区分生产和测试密钥** —— 生产环境和测试环境必须使用不同的 clientId 和密钥对。

