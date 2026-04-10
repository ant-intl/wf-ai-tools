---
name: wf-api-integration
description: Generate Java or Golang integration code for WorldFirst (WF) APIs including transfer, payout, beneficiary management, balance inquiry, statement inquiry, and trade order management. Supports RSA256 signing, shared infrastructure reuse, and production-ready code generation with Alibaba coding standards.
---
# WF API Integration Skill

帮助用户对接万里汇(WorldFirst) API，支持 Java 和 Golang 两种语言，涵盖转账、代发、收款人管理、余额查询、账单查询、交易订单管理等模块。

## 模块索引

| 模块           | 模块目录                      | 模块说明                                   |
| -------------- | ----------------------------- | ------------------------------------------ |
| 万里汇转账     | `references/transfer/`        | 转账至WF账户                               |
| 全球分发       | `references/payout/`          | 代发到三方卡                               |
| 收款人管理     | `references/beneficiary/`     | 卡模版查询、绑定/删除/编辑/查询收款人          |
| 账户管理       | `references/balance-inquiry/` | 查询账户余额                               |
| 账单管理       | `references/statement-inquiry/` | 查询账户流水及详情                        |
| 交易信息管理   | `references/trade-order/`     | 上传交易订单（B2C 结汇 / B2B 订单关联）       |

## 快速决策树

```
用户咨询 WF API 对接
        |
        +-- 在 WF 账户之间划转资金？ --> 万里汇转账
        |
        +-- 付款到第三方银行卡（发工资、供应商付款）？ --> 全球分发（代发）
        |
        +-- 管理收款人银行卡（增删改查、卡模版）？ --> 收款人管理
        |
        +-- 查看账户有多少钱？ --> 余额查询
        |
        +-- 查看账户交易流水或对账？ --> 账单查询
        |
        +-- 上传交易订单（跨境结汇 / B2B 订单关联）？ --> 交易订单管理
```

## 场景关键词匹配

| 关键词 | 路由模块 |
| --- | --- |
| 转账、汇款、WF账户转账、内部转账、账户间转账、户到户、transfer、consultTransfer、createTransfer、inquiryTransfer、转账汇率、转账手续费、转账咨询、发起转账、转账状态、转账结果 | 万里汇转账 |
| 代发、payout、发工资、付款到银行卡、付款到第三方、全球分发、跨境代发、批量付款、代发汇率、代发报价、quoteId、consultPayout、createPayout、inquiryPayout、代发状态、代发结果 | 全球分发（代发） |
| 收款人、beneficiary、银行卡管理、绑卡、收款方、收款账户、卡模版、银行卡字段、inquiryTemplate、bindBeneficiary、editBeneficiary、removeBeneficiary、inquiryBeneficiaryList、添加收款人、删除收款人、修改收款人、收款人列表 | 收款人管理 |
| 余额、账户余额、balance、查余额、inquiryBalance、账户可用余额、币种余额 | 余额查询 |
| 账单、流水、交易记录、statement、对账、账户流水、inquiryStatementList、inquiryStatementDetail、流水列表、流水详情、账单详情 | 账单查询 |
| 交易订单、trade order、结汇、B2B订单、订单上传、PAY_INTO_CHINA、CREATE_B2B_ORDERS、submitTradeOrder、inquiryTradeOrder、notifyTradeOrder、订单回调、异步通知、webhook | 交易订单管理 |

## 澄清话术

当用户描述模糊时：

```
请确认您需要对接的万里汇（WorldFirst）业务模块：

1. 万里汇转账
   在 WF 账户之间划转资金，支持转账前汇率咨询、发起转账、查询转账结果
   适用：WF 账户间资金划转

2. 全球分发（代发）
   代发资金到第三方银行卡，支持跨币种汇率咨询、发起代发、查询代发结果
   适用：发工资、供应商付款、跨境分发等场景

3. 收款人管理
   管理代发目标银行卡，支持卡模版查询、绑定/编辑/删除/查询收款人
   适用：维护代发收款方的银行卡信息

4. 余额查询
   查询 WF 账户余额，支持按币种和余额类型过滤
   适用：实时查看账户可用余额

5. 账单查询
   查询账户交易流水列表及流水详情
   适用：对账、交易记录查询

6. 交易订单管理
   上传交易订单、查询处理结果、处理异步回调通知
   适用：跨境电商 B2C 结汇、B2B 订单关联

请告诉我您需要对接哪个模块？
```

## 使用流程

1. **确认模块**：确认用户需要对接的模块，读取对应模块的 `README.md` 了解接口列表
2. **确认接口**：确认用户需要对接的具体接口，读取对应接口的 `GUIDE.md` 了解接口规范
3. **加载公共代码**：读取 `references/common/` 下的公共代码模板
4. **加载接口代码**：读取对应接口目录下的代码模板
5. **生成代码**：根据用户项目结构生成代码，替换 `{basePackage}`（Java）或 `{moduleName}`（Golang）占位符

## Pre-Generation Questions (MUST ASK)

在生成代码前，**必须**通过 AskUserQuestion 工具询问用户以下所有问题。所有问题必须在开始生成任何代码之前全部收集完毕。

### 基础信息

1. **项目路径**：请问你的项目路径是什么？
2. **语言选择**：你需要生成 Java 还是 Golang 的代码？
3. **Base Package / Module Name**：
   - Java：请提供 base package（如 `com.example.project`）
   - Golang：请提供 Go module name（如 `github.com/example/project`）

### WfConfig 配置属性（用于生成 WfConfig 类，禁止使用占位符）

以下属性**必须**在生成 WfConfig 前向用户收集真实值，**严禁**在代码中生成 `YOUR_CLIENT_ID` 等占位符让用户自行替换。

4. **WF Client ID**：请提供您的万里汇 Client ID
5. **WF User ID**：请提供您的万里汇登录 User ID
6. **API Base URL**：请提供 API 网关地址（默认 `https://open-sitprod-sg.alipay.com`）
7. **私钥文件路径**：请提供 RSA 私钥文件路径（PKCS#8 格式），如 `/home/admin/keys/private_key.pem`
8. **公钥文件路径**：请提供万里汇 RSA 公钥文件路径，如 `/home/admin/keys/wf_public_key.pem`

> **重要**：用户提供的值直接填入生成的 WfConfig 代码中。如果用户表示暂时不确定某个值，应使用 Spring XML property placeholder（如 `${wf.clientId}`）或 Go 环境变量读取（如 `os.Getenv("WF_CLIENT_ID")`）而非硬编码占位符字符串。

## 公共依赖

所有模块共用以下组件，位于 `references/common/`：

### Java (`references/common/java/`)


| 组件             | 路径                               | 说明                                          |
| ---------------- | ---------------------------------- | --------------------------------------------- |
| WfConfig         | `config/WfConfig.java`             | 配置管理（clientId、baseUrl、密钥路径、超时） |
| WfSigner         | `signer/WfSigner.java`             | RSA256 签名/验签                              |
| WfHttpClientUtil | `util/WfHttpClientUtil.java`       | HTTP 客户端封装（签名注入、响应验签）         |
| Result           | `model/response/Result.java`       | 统一响应结果对象                              |
| WfErrorCode      | `model/exception/WfErrorCode.java` | 错误码枚举                                    |
| WfException      | `model/exception/WfException.java` | 业务异常类                                    |

### Golang (`references/common/golang/`)


| 组件         | 路径                              | 说明                                   |
| ------------ | --------------------------------- | -------------------------------------- |
| WfConfig     | `config/config.go`                | 配置管理                               |
| WfSigner     | `signer/signer.go`                | RSA256 签名/验签（Signer 接口 + 实现） |
| WfHttpClient | `util/wf_http_client.go`          | HTTP 客户端封装                        |
| Result       | `model/response/result.go`        | 统一响应结果                           |
| WfErrorCode  | `model/exception/error_code.go`   | 错误码定义                             |
| WfException  | `model/exception/wf_exception.go` | 异常定义                               |

## 公共代码生成规则

- **WfConfig**：使用 Pre-Generation Questions 中收集到的用户真实值（第 4-8 项）填充字段默认值，**禁止使用占位符**
- **Result.java / result.go**：共享，仅首次生成，已存在则复用
- **WfErrorCode**：共享，新接口的错误码追加到已有文件，不重复生成
- **WfException**：共享，已存在则复用

## 代码规范

### Java

- Java 8（禁止使用 Java 9+ 专有语法）
- CamelCase 类名，lowerCamelCase 变量名，UPPER_SNAKE_CASE 常量名
- 同行左花括号，4 空格缩进，每行最多 120 字符
- 所有 public 方法必须有 Javadoc，使用 SLF4J 日志

### Golang

- 标准库优先，按包分文件
- 依赖注入模式（构造函数注入）
- `{moduleName}` 占位符替换为用户实际 go.mod 中的 module path

## 签名算法

所有 WF API 使用 RSA256 签名，签名内容格式：

```
POST {apiPath}\n{clientId}.{requestTime}.{requestBody}
```

详见 `references/common/` 下的签名工具代码。

## 测试代码生成

生成测试代码前，**必须**询问签名模式：


| 模式      | 说明                                                                              |
| --------- | --------------------------------------------------------------------------------- |
| Mock 签名 | Mock WfSigner 固定返回`"TESTING_SIGNATURE"`，跳过真实签名，适用于快速验证请求格式 |
| 真实签名  | 使用用户提供的私钥/公钥文件路径，可完整跑通接口                                   |

## 安全红线

> ⛔ 以下规则为万里汇 API 对接的**安全红线**，违反可能导致资金损失或安全事故，必须严格遵守。

+   **私钥禁止硬编码**：RSA 私钥必须通过文件路径或密钥管理服务加载，严禁将私钥内容硬编码在源代码中。
+   **私钥禁止记日志**：私钥内容不得出现在任何日志输出中，包括 debug 级别日志。
+   **私钥禁止传公共仓库**：私钥文件不得上传到 GitHub、GitLab 等公共代码仓库，必须加入 `.gitignore`。
+   **clientId / secretKey 禁止明文存储**：clientId 和密钥配置必须通过环境变量、配置中心或加密文件管理，禁止明文写入代码或配置文件提交到版本库。
+   **响应必须验签**：收到 WF API 响应后必须使用 WF 公钥验签，确认响应来自万里汇，防止中间人篡改。
+   **异步通知必须验签**：收到 notifyTradeOrder 等异步回调通知后，必须先验签再处理业务逻辑，防止伪造通知。
+   **幂等性保障**：转账（createTransfer）和代发（createPayout）等资金类接口必须使用唯一的 transferRequestId / payoutRequestId，防止因重试导致重复扣款。
+   **HTTPS 强制**：所有 API 请求必须通过 HTTPS 发送，禁止使用 HTTP 明文传输。
+   **转账/代发结果不可假定**：发起转账或代发后，必须通过查询接口（inquiryTransfer / inquiryPayout）或异步通知确认最终状态，禁止仅凭请求响应的 status 判定最终结果。
+   **生产密钥与测试密钥隔离**：生产环境和测试环境必须使用不同的 clientId 和密钥对，严禁混用。
