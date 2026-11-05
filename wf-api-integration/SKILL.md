---
name: wf-api-integration
description: Generate Java, Golang, Python, or PHP integration code for WorldFirst (WF) APIs, and adapt the same API contracts to other technology stacks through project-aware, language-agnostic generation. Covers transfer, payout, beneficiary, account, statement, and trade order APIs with RSA256 signing, shared infrastructure reuse, security checks, and build verification.
---
# WF API Integration Skill

帮助用户对接万里汇(WorldFirst) API，原生支持 Java、Golang、Python 和 PHP，并可通过语言无关适配层生成其他技术栈的集成代码，涵盖转账、代发、收款人管理、账户管理、账单查询、交易订单管理等模块。

> **语言支持说明**：Java、Golang 和 PHP 覆盖所有模块；Python 目前仅支持公共模块和账单管理。PHP 要求 8.1+。其他语言使用 [语言无关适配流程](references/language-agnostic/README.md)，以客户项目结构、GUIDE 契约和现有实现为依据，必须经过目标技术栈验证。

## 模块索引


| 模块         | 模块目录                                                        | 模块说明                                                     | 官方文档                                                                                             | 语言支持           |
| ----------- |----------------------------------------------------------------|-----------------------------------------------------------|----------------------------------------------------------------------------------------------------| ------------------ |
| 万里汇转账   | [references/transfer/](references/transfer/)                   | 在 WF 账户之间划转资金（咨询/发起/查询/结果通知）                | [📄 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_solution/transfer_funds)           | Java、Golang、PHP  |
| 全球分发     | [references/payout/](references/payout/)                       | 代发到第三方银行卡或电子钱包（咨询/发起/查询/结果通知）            | [📄 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_solution/create_payout)             | Java、Golang、PHP  |
| 收款人管理   | [references/beneficiary/](references/beneficiary/)             | 卡模版查询、绑定/删除/编辑/查询收款人、绑定结果通知                | [📄 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_solution/beneficiary_management)        | Java、Golang、PHP  |
| 账户管理     | [references/account-management/](references/account-management/)     | 查询账户信息/余额/结汇额度/子账号/店铺，接收充值/余额变动通知       | [📄 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_solution/account_management)            | Java、Golang、PHP  |
| 账单管理     | [references/statement-management/](references/statement-management/) | 查询账户交易流水列表及流水详情                                 | [📄 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_solution/statement_management)          | Java、Golang、Python、PHP |
| 交易信息管理 | [references/trade-order/](references/trade-order/)             | 上传交易订单（B2C 结汇 / B2B 订单关联）、查询结果、处理异步通知     | [📄 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_solution/trade_order_management)        | Java、Golang、PHP  |

## 快速决策树

```
用户咨询 WF API 对接
        |
        +-- 在 WF 账户之间划转资金？ --> 万里汇转账
        |       |
        |       +-- 需要提前获取汇率/手续费？ --> 咨询转账（consultTransfer）
        |       +-- 发起转账？ --> 户到户转账（createTransfer）
        |       +-- 查询转账结果？ --> 查询转账结果（inquiryTransfer）
        |       +-- 接收转账结果回调？ --> 转账结果通知（notifyTransfer）
        |
        +-- 付款到第三方银行卡或电子钱包（发工资、供应商付款、支付宝代发）？ --> 全球分发（代发）
        |       |
        |       +-- 需要提前获取汇率/报价？ --> 咨询代发汇率（consultPayout）
        |       +-- 发起代发？ --> 创建代发（createPayout）
        |       +-- 查询代发结果？ --> 查询代发结果（inquiryPayout）
        |       +-- 接收代发结果回调？ --> 代发结果通知（notifyPayout）
        |
        +-- 管理收款人银行卡（增删改查、卡模版）？ --> 收款人管理
        |       |
        |       +-- 查看卡模版字段要求？ --> 查询卡模版（inquiryTemplate）
        |       +-- 绑定收款人？ --> 绑定收款人（bindBeneficiary）
        |       +-- 删除/编辑/查询收款人？ --> 对应接口
        |       +-- 接收绑卡结果回调？ --> 绑定收款人通知（notifyBindBeneficiary）
        |
        +-- 账户相关查询？ --> 账户管理
        |       |
        |       +-- 查看账户有多少钱？ --> 查询余额（inquiryBalance）
        |       +-- 查看账户基本信息？ --> 查询账户信息（inquiryAccount）
        |       +-- 查看可申报结汇额度？ --> 查询结汇额度（inquiryAvailableQuota）
        |       +-- 查看主/子账号信息？ --> 查询子账号（inquirySubuser）
        |       +-- 查看店铺及关联账号？ --> 查询店铺信息（inquiryStore）
        |       +-- 接收充值/垫付回调？ --> 充值通知（notifyVostro）
        |       +-- 接收余额变动回调？ --> 余额变动通知（notifyBalanceChange）
        |
        +-- 查看账户交易流水或对账？ --> 账单管理
        |       |
        |       +-- 查询流水列表？ --> 查询账单流水（inquiryStatementList）
        |       +-- 查询流水详情？ --> 查询账单详情（inquiryStatementDetail）
        |
        +-- 上传交易订单（跨境结汇 / B2B 订单关联）？ --> 交易信息管理
                |
                +-- 上传交易订单？ --> 提交交易订单（submitTradeOrder）
                +-- 查询上传结果？ --> 查询订单结果（inquiryTradeOrder）
                +-- 接收异步通知？ --> 订单回调通知（notifyTradeOrder）
```

## 场景关键词匹配


| 关键词                                                                                                                                                                                                                    | 路由模块         |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- |
| 转账、汇款、WF账户转账、内部转账、账户间转账、户到户、transfer、consultTransfer、createTransfer、inquiryTransfer、notifyTransfer、转账汇率、转账手续费、转账咨询、发起转账、转账状态、转账结果、转账通知、转账回调            | 万里汇转账       |
| 代发、payout、发工资、付款到银行卡、付款到第三方、全球分发、跨境代发、批量付款、代发汇率、代发报价、quoteId、consultPayout、createPayout、inquiryPayout、notifyPayout、代发状态、代发结果、代发通知、代发回调、电子钱包、支付宝代发、ALIPAY_CN_DETAIL、REFERENCE_ALIPAY_CN、代发到支付宝               | 全球分发（代发） |
| 收款人、beneficiary、银行卡管理、绑卡、收款方、收款账户、卡模版、银行卡字段、inquiryTemplate、bindBeneficiary、editBeneficiary、removeBeneficiary、inquiryBeneficiaryList、notifyBindBeneficiary、添加收款人、删除收款人、修改收款人、收款人列表、绑定收款人通知、绑卡回调 | 收款人管理       |
| 余额、账户余额、balance、查余额、inquiryBalance、账户可用余额、币种余额、账户信息、inquiryAccount、结汇额度、inquiryAvailableQuota、子账号、inquirySubuser、店铺信息、inquiryStore、充值通知、notifyVostro、余额变动、notifyBalanceChange、垫付回调、动账通知 | 账户管理         |
| 账单、流水、交易记录、statement、对账、账户流水、inquiryStatementList、inquiryStatementDetail、流水列表、流水详情、账单详情                                                                                               | 账单查询         |
| 交易订单、trade order、结汇、B2B订单、订单上传、PAY_INTO_CHINA、CREATE_B2B_ORDERS、submitTradeOrder、inquiryTradeOrder、notifyTradeOrder、订单回调、异步通知、webhook                                                     | 交易订单管理     |

## 澄清话术

当用户描述模糊时：

```
请确认您需要对接的万里汇（WorldFirst）业务模块：

1. 万里汇转账
   在 WF 账户之间划转资金，支持转账前汇率咨询、发起转账、查询转账结果、接收转账结果回调通知
   适用：WF 账户间资金划转

2. 全球分发（代发）
   代发资金到第三方银行卡，支持跨币种汇率咨询、发起代发、查询代发结果、接收代发结果回调通知
   适用：发工资、供应商付款、跨境分发等场景

3. 收款人管理
   管理代发目标银行卡，支持卡模版查询、绑定/编辑/删除/查询收款人、接收绑定结果回调通知
   适用：维护代发收款方的银行卡信息

4. 账户管理
   查询 WF 账户信息、余额、结汇额度、子账号、店铺信息，接收充值/余额变动回调通知
   适用：账户信息查询、余额监控、充值垫付通知处理

5. 账单查询
   查询账户交易流水列表及流水详情
   适用：对账、交易记录查询

6. 交易订单管理
   上传交易订单、查询处理结果、处理异步回调通知
   适用：跨境电商 B2C 结汇、B2B 订单关联

请告诉我您需要对接哪个模块？
```

## 使用流程

1. **确认模块和接口**：读取模块 `README.md` 与接口 `GUIDE.md`，明确契约
2. **选择生成模式**：四种原生语言读取公共层和接口模板；其他语言读取 [语言无关适配流程](references/language-agnostic/README.md)，检查客户项目并形成 Stack Profile
3. **生成代码**：原生语言替换 `{basePackage}`、`{moduleName}` 或 `{baseNamespace}`；其他语言按接口契约和项目既有规范适配，不做机械翻译
4. **代码检查**：执行「代码生成后检查清单」及目标技术栈的契约、安全检查
5. **测试验证**：执行目标项目可用的构建、静态检查、单元测试和签名交叉验证；未执行的项目必须明确标注

### 其他语言（Language-Agnostic Mode）

- 触发条件：客户不使用 Java、Golang、Python、PHP，或明确要求沿用其他语言/框架
- 必须先识别运行时、框架、HTTP、JSON、RSA、序列化、测试和构建方式，再选择最接近的参考实现
- 必须满足 HTTPS、RSA256、64 位金额整数、原始 Webhook 请求体与实际 request URI 等能力边界
- 禁止凭空选择依赖、硬编码密钥、浮点金额、跳过验签或未经验证就宣称支持

## Pre-Generation Questions (MUST ASK)

在生成代码前，**必须**通过 AskUserQuestion 工具询问用户以下所有问题。所有问题必须在开始生成任何代码之前全部收集完毕。

### 基础信息

1. **项目路径**：请问你的项目路径是什么？
2. **语言选择**：你需要生成 Java、Golang、Python、PHP，还是其他技术栈的代码？其他技术栈先检查项目并确认 Stack Profile
3. **Base Package / Module Name / Base Namespace / 项目模块约定**：
   - Java：请提供 base package（如 `com.example.project`）
   - Golang：请提供 Go module name（如 `github.com/example/project`）
   - Python：请提供 base package（如 `wf_integration`），用于组织 import 路径
   - PHP：请提供 base namespace（如 `Acme\Integration`），用于 namespace 和 Composer PSR-4 自动加载
   - 其他语言：确认项目现有的模块、命名空间、目录和依赖注入约定

### WfConfig 配置属性（用于生成 WfConfig 类，禁止使用占位符）

以下属性**必须**在生成 WfConfig 前向用户收集真实值，**严禁**在代码中生成 `YOUR_CLIENT_ID` 等占位符让用户自行替换。

4. **WF Client ID**：请提供您的万里汇 Client ID
5. **WF User ID**：请提供您的万里汇登录 User ID
6. **API Base URL**：请提供 API 网关地址（默认 `https://open-sea.worldfirst.com`）
7. **私钥文件路径**：请提供 RSA 私钥文件路径（PKCS#8 格式），如 `/etc/wf/certs/private_key.pem`
8. **公钥文件路径**：请提供万里汇 RSA 公钥文件路径，如 `/etc/wf/certs/wf_public_key.pem`

> **重要**：用户提供的值直接填入生成的 WfConfig 代码中。如果用户表示暂时不确定某个值，应使用 Spring XML property placeholder（如 `${wf.clientId}`）、Go 环境变量读取（如 `os.Getenv("WF_CLIENT_ID")`）、Python 环境变量读取（如 `os.environ.get("WF_CLIENT_ID")`）、PHP 环境变量读取（如 `getenv("WF_CLIENT_ID")`）或目标项目的安全配置机制，而非硬编码占位符字符串。

### 模块特定问题（按需询问）

以下问题仅在用户选择对应模块时询问：

**收款人管理模块**：
- **集成模式**：请问您的 Payout 集成场景是使用**卡详情模式**（每次代发直接传银行卡信息）还是**卡 token 模式**（先绑定收款人获取 token）？
  - 卡详情模式：仅需集成 `inquiry-template/`（查询卡模版）
  - 卡 token 模式：需集成全部 6 个接口

**交易订单管理模块**：
- **业务场景**：请问需要支持哪些业务场景？
  - PAY_INTO_CHINA（B2C 结汇）：需集成全部 3 个接口（submitTradeOrder、inquiryTradeOrder、notifyTradeOrder）
  - CREATE_B2B_ORDERS（B2B 订单关联）：仅需 submitTradeOrder
  - 两者都需要：生成完整套件

## 公共依赖

所有模块共用以下组件，位于 [references/common/](references/common/)：

### Java ([references/common/java/](references/common/java/))


| 组件             | 路径                                                                                                                               | 说明                                          |
| ---------------- | ---------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------- |
| WfConfig         | [config/WfConfig.java](references/common/java/config/WfConfig.java)             | 配置管理（clientId、baseUrl、密钥路径、超时） |
| WfSigner         | [signer/WfSigner.java](references/common/java/signer/WfSigner.java)             | RSA256 签名/验签                              |
| WfHttpClientUtil | [util/WfHttpClientUtil.java](references/common/java/util/WfHttpClientUtil.java)       | HTTP 客户端封装（签名注入、响应验签）         |
| Result           | [model/response/Result.java](references/common/java/model/response/Result.java)       | 统一响应结果对象                              |
| WfErrorCode      | [model/exception/WfErrorCode.java](references/common/java/model/exception/WfErrorCode.java) | 错误码枚举                                    |
| WfException      | [model/exception/WfException.java](references/common/java/model/exception/WfException.java) | 业务异常类                                    |

### Golang ([references/common/golang/](references/common/golang/))


| 组件         | 路径                                                                                                                    | 说明                                   |
| ------------ | ----------------------------------------------------------------------------------------------------------------------- | -------------------------------------- |
| WfConfig     | [config/config.go](references/common/golang/config/config.go)                | 配置管理                               |
| WfSigner     | [signer/signer.go](references/common/golang/signer/signer.go)                | RSA256 签名/验签（Signer 接口 + 实现） |
| WfHttpClient | [util/wf_http_client.go](references/common/golang/util/wf_http_client.go)          | HTTP 客户端封装                        |
| Result       | [model/response/result.go](references/common/golang/model/response/result.go)        | 统一响应结果                           |
| WfErrorCode  | [model/exception/error_code.go](references/common/golang/model/exception/error_code.go)   | 错误码定义                             |
| WfException  | [model/exception/wf_exception.go](references/common/golang/model/exception/wf_exception.go) | 异常定义                               |

### Python ([references/common/python/](references/common/python/))


| 组件         | 路径                                                                                                                         | 说明                           |
| ------------ | ---------------------------------------------------------------------------------------------------------------------------- | ------------------------------ |
| WfConfig     | [config/wf_config.py](references/common/python/config/wf_config.py)               | 配置管理                       |
| WfSigner     | [signer/wf_signer.py](references/common/python/signer/wf_signer.py)               | RSA256 签名/验签               |
| WfHttpClient | [util/wf_http_client.py](references/common/python/util/wf_http_client.py)            | HTTP 客户端封装                |
| Result       | [model/response/result.py](references/common/python/model/response/result.py)          | 统一响应结果                   |
| WfErrorCode  | [model/exception/wf_error_code.py](references/common/python/model/exception/wf_error_code.py)  | 错误码定义                     |
| WfException  | [model/exception/wf_exception.py](references/common/python/model/exception/wf_exception.py)    | 异常定义                       |

### PHP ([references/common/php/](references/common/php/))

| 组件 | 路径 | 说明 |
| ---- | ---- | ---- |
| WfConfig | [Config/WfConfig.php](references/common/php/Config/WfConfig.php) | 配置管理与环境变量回退 |
| WfSigner | [Signer/WfSigner.php](references/common/php/Signer/WfSigner.php) | 基于 OpenSSL 的 RSA256 签名/验签 |
| WfHttpClient | [Util/WfHttpClient.php](references/common/php/Util/WfHttpClient.php) | Guzzle HTTP 封装、HTTPS 校验、强制响应验签 |
| WfWebhookHandler | [Util/WfWebhookHandler.php](references/common/php/Util/WfWebhookHandler.php) | 通知原始请求体验签与签名响应 |
| AbstractWfClient | [Client/AbstractWfClient.php](references/common/php/Client/AbstractWfClient.php) | 主动调用结果与异常统一处理 |
| WfModel | [Model/WfModel.php](references/common/php/Model/WfModel.php) | 字段声明、嵌套模型和 JSON 序列化 |
| Result | [Model/Response/Result.php](references/common/php/Model/Response/Result.php) | 统一响应结果 |
| WfErrorCode | [Model/Exception/WfErrorCode.php](references/common/php/Model/Exception/WfErrorCode.php) | 错误码枚举 |
| WfException | [Model/Exception/WfException.php](references/common/php/Model/Exception/WfException.php) | 业务异常 |

## 公共代码生成规则

- **WfConfig**：使用 Pre-Generation Questions 中收集到的用户真实值（第 4-8 项）填充字段默认值，**禁止使用占位符**
- **Result.java / result.go / result.py / Result.php**：共享，仅首次生成，已存在则复用
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

### Python

- Python 3.10+，使用 type hints 标注参数和返回值
- snake_case 模块名、函数名、变量名，PascalCase 类名，UPPER_SNAKE_CASE 常量名
- 4 空格缩进，每行最多 120 字符，遵循 PEP 8 规范
- 使用 `dataclass` 或普通类定义 Model，属性使用 snake_case
- 依赖管理通过 `requirements.txt` 声明，核心依赖：`requests>=2.28.0`、`cryptography>=41.0.0`
- `{basePackage}` 占位符替换为用户实际 base package 名称（用于 import 路径）

### PHP

- PHP 8.1+，所有文件启用 `declare(strict_types=1)`，遵循 PSR-12 和 PSR-4
- PascalCase 类名和目录名，lowerCamelCase 方法名和变量名，UPPER_SNAKE_CASE 常量名
- Model 继承 `WfModel`，通过 `FIELDS` 保留 API camelCase 字段，通过 `RELATIONS` 声明嵌套对象
- Domain 按业务模块隔离命名空间（Transfer/Payout/Beneficiary/Account/Statement/TradeOrder），禁止用同名模型互相覆盖
- 同一业务模块的 Client 必须合并方法和 import，转账统一使用 `consult-transfer/php/Client/TransferClient.php`
- Webhook 从框架路由取得实际 request URI（路径及查询字符串），请求验签和响应签名必须使用同一 URI
- 使用 Guzzle 发送 HTTPS 请求，使用 OpenSSL RSA256 签名；响应缺少签名或验签失败必须抛出异常
- 依赖通过 `composer.json` 声明，核心依赖：`guzzlehttp/guzzle:^7.9`、`ext-json`、`ext-openssl`
- `{baseNamespace}` 占位符替换为用户实际 base namespace（如 `Acme\Integration`），并同步 Composer PSR-4 前缀

## 签名算法

所有 WF API 使用 RSA256 签名，签名内容格式：

```
POST {apiPath}\n{clientId}.{requestTime}.{requestBody}
```

详见 [references/common/](references/common/) 下的签名工具代码。

## 测试代码生成

每个 Client 类生成一个集成测试类，真实调用 WF API。

### 测试代码规范

1. **仅生成 Client 类的测试**：为各语言的 Client 生成对应集成测试，不为 Model 单独生成测试
2. **仅保留一个成功测试方法**：每个测试类只包含一个成功场景测试方法
3. **真实HTTP调用**：不Mock HTTP响应，通过对应语言的公共 HTTP Client 真实调用 WF API
4. **配置使用安全的外部配置**：clientId、密钥路径等通过环境变量或语言对应的配置占位符注入，禁止写入真实密钥

## 代码生成后检查清单 (POST-GENERATION CHECKLIST)

> ⚠️ **重要**：每次生成代码后，**必须**执行以下检查，确保代码质量和完整性。

### 1. Model 类字段一致性检查

**目的**：确保生成的 Model 类字段与模板文件完全一致，避免字段遗漏。

**检查步骤**：

1. 遍历生成的所有 Model 类（位于 `model/domain/`、`model/request/`、`model/response/`）
2. 对比每个生成的类与对应模板文件（[references/{module}/{interface}/{language}/](references/)，其中 `{language}` 为 `java`、`golang`、`python` 或 `php`）
3. 检查字段数量、字段名称、字段类型是否一致
4. Java：检查 Getter/Setter 方法是否完整；Python：检查 `dataclass` 字段或属性定义是否完整；PHP：检查 `FIELDS` 与 `RELATIONS` 是否完整

**检查命令示例**：

```bash
# 对比生成的 FundMoveDetail.java 与模板
diff generated/FundMoveDetail.java references/statement-management/inquiry-statement-list/java/model/domain/FundMoveDetail.java
```

### 2. 单元测试运行检查

**目的**：确保生成的代码可以正常编译和运行，没有语法错误。

**检查步骤**：

1. 进入用户项目目录
2. 运行编译命令，确保无编译错误
3. 运行生成的单元测试，确保测试用例可正常执行

**预期结果**：

- 编译成功，无错误
- 单元测试可正常运行（即使测试用例失败，也要确保代码能跑起来）

### 3. 包名和导入检查

**目的**：确保生成的代码包名正确，导入语句无冲突。

**检查要点**：

- [ ]  Java：包名与用户提供的路径一致；Python：模块路径与项目结构一致；PHP：namespace 与 PSR-4 路径一致
- [ ]  无缺失的 import/use 语句（PHP 同时检查 Composer 自动加载前缀）
- [ ]  无循环依赖

### 检查清单执行记录

每次生成代码后，在回复中必须包含以下检查记录：

```
✅ 代码生成后检查完成

1. Model 类字段一致性检查：
   - [x] FundMoveDetail.java - 18个字段，与模板一致
   - [x] StatementRecord.java - 24个字段，与模板一致
   - [x] ForeignExchangeQuote.java - 3个字段，与模板一致
   - [x] OperatorInfo.java - 2个字段，与模板一致
   - [x] Amount.java - 2个字段，与模板一致
   - [x] RelatedStatement.java - 3个字段，与模板一致

2. 单元测试运行检查：
   - [x] 编译成功
   - [x] 单元测试可正常运行

3. 包名和导入检查：
   - [x] 包名正确
   - [x] 导入语句完整

```

---

## 模块接口详情

### 万里汇转账（transfer）

> 📄 [官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/transfer)

| 接口         | 目录                                                                                                                                                  | 说明                                             | 官方文档                                                                                                                                                                  |
| ------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 咨询转账     | [consult-transfer/](references/transfer/consult-transfer/)  | 转账前获取汇率、手续费等信息                     | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/consult_transfer)          |
| 户到户转账   | [create-transfer/](references/transfer/create-transfer/)    | 在万里汇账户之间转账                             | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/create_transfer)           |
| 查询转账结果 | [inquiry-transfer/](references/transfer/inquiry-transfer/)  | 查询转账结果（PROCESSING 状态需轮询）            | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_transfer)          |
| 转账结果通知 | [notify-transfer/](references/transfer/notify-transfer/)    | 回调通知：接收万里汇推送的转账结果通知（WF → 集成商） | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/notify_transfer)           |

注意事项：
- 转账为异步接口，`PROCESSING` 状态必须轮询
- `transferRequestId` 是幂等键，相同 ID + 不同 body → `REPEAT_REQ_INCONSISTENT`
- `businessSceneCode` 在主/子账号余额互转时必填 `MULTI_ACCOUNT_TRANSFER`
- `notifyTransfer` 是回调通知接口（WF → 集成商），需验签后返回 SUCCESS，业务逻辑异步处理
- 回调通知需返回成功响应，否则万里汇将重试最多 7 次

### 全球分发（payout）

> 📄 [官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/payout)

| 接口         | 目录                                                                                                                                           | 说明                                     | 官方文档                                                                                                                                                         |
| ------------ | ---------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 咨询代发汇率 | [consult-payout/](references/payout/consult-payout/) | 咨询费用、校验卡模版、获取跨币种汇率报价 | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/consult_payout)       |
| 创建代发     | [create-payout/](references/payout/create-payout/)   | 代发到银行卡或电子钱包                   | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/create_payout)         |
| 查询代发结果 | [inquiry-payout/](references/payout/inquiry-payout/)  | 查询代发单状态                           | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_payout)        |
| 代发结果通知 | [notify-payout/](references/payout/notify-payout/)    | 回调通知：接收万里汇推送的代发结果通知（WF → 集成商） | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/notify_payout)         |
注意事项：
- 支持四种收款模式（互斥）：
  - 卡详情模式（BANK_ACCOUNT_DETAIL）— 直接传银行卡详情
  - 卡 token 模式（BENEFICIARY_TOKEN）— 使用已绑定收款人的 token
  - 支付宝账户详情模式（ALIPAY_CN_DETAIL）— 代发到支付宝账户，paymentMethodMetaData 传空对象
  - 关联支付宝钱包模式（REFERENCE_ALIPAY_CN）— 代发到关联的支付宝钱包，paymentMethodId 传 referenceCustomerId
- 跨币种代发必须先调 consultPayout 获取 quoteId
- `notifyPayout` 是回调通知接口（WF → 集成商），需验签后返回 SUCCESS，业务逻辑异步处理
- 回调通知需返回成功响应，否则万里汇将重试最多 7 次
- 完整嵌套对象字段定义见 [references/payout/field-reference.md](references/payout/field-reference.md)

### 收款人管理（beneficiary）

> 📄 [官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/beneficiary)

| 接口             | 目录                                                                                                                                                    | 说明                                            | 官方文档                                                                                                                                                                     | 语言支持     |
| ---------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------ |
| 查询卡模版       | [inquiry-template/](references/beneficiary/inquiry-template/) | 查询指定国家/币种/账户类型的卡模版字段要求      | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_template)                 | Java、Golang、PHP |
| 绑定收款人       | [bind/](references/beneficiary/bind/)                         | 绑定收款人到 WF 账户，获取 beneficiaryToken     | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/bind_beneficiary)                 | Java、Golang、PHP |
| 删除收款人       | [remove/](references/beneficiary/remove/)                     | 删除已绑定的收款人                              | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/remove_beneficiary)               | Java、Golang、PHP |
| 编辑收款人       | [edit/](references/beneficiary/edit/)                         | 修改收款人昵称                                  | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/edit_beneficiary)                  | Java、Golang、PHP |
| 查询收款人列表   | [inquiry-list/](references/beneficiary/inquiry-list/)         | 分页查询已绑定的收款人                          | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_beneficiary_list)         | Java、Golang、PHP |
| 绑定收款人通知   | [notify-bind/](references/beneficiary/notify-bind/)           | 回调通知：接收收款人绑定结果通知（WF → 集成商） | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/notify_bind_beneficiary)          | Java、Golang、PHP |

### 账户管理（account-management）

> 📄 [官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/account)

| 接口           | 目录                                                                                                                                                                       | 说明                                                 | 官方文档                                                                                                                                                                                 |
| -------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 查询账户信息   | [inquiry-account/](references/account-management/inquiry-account/)                   | 查询 WF 账户信息（账户类型、账号、激活状态、币种等） | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_account)                               |
| 查询余额       | [inquiry-balance/](references/account-management/inquiry-balance/)                   | 查询 WF 账户余额，支持按币种和余额类型过滤          | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_balance)                               |
| 查询结汇额度   | [inquiry-available-quota/](references/account-management/inquiry-available-quota/)   | 查询可申报的结汇额度，支持四种累计方式               | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_available_quota)                       |
| 查询子账号信息 | [inquiry-subuser/](references/account-management/inquiry-subuser/)                   | 查询万里汇主账号及子账号信息，支持分页               | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_subuser)                               |
| 查询店铺信息   | [inquiry-store/](references/account-management/inquiry-store/)                       | 查询店铺信息及店铺关联账号信息，支持分页             | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_store)                                 |
| 充值通知       | [notify-vostro/](references/account-management/notify-vostro/)                       | 接收万里汇充值/垫付回调通知（WF → 集成商）          | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/notify_vostro)                                 |
| 余额变动通知   | [notify-balance-change/](references/account-management/notify-balance-change/)       | 接收万里汇余额账户动账变动回调通知（WF → 集成商）   | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/notify_balance_change)                         |

注意事项：
- `notifyVostro` 和 `notifyBalanceChange` 是回调通知接口（WF → 集成商），非主动调用接口
- 回调通知需返回成功响应，否则万里汇将重试最多 7 次
- `inquirySubuser` 仅主账号可调用，子账号调用返回 `USER_ACCOUNT_NOT_PRIMARY`
- 余额 `value` 为最小货币单位的整数（如 USD 100.00 → value = 10000）

### 账单管理（statement-management）

> 📄 [官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/statement)

| 接口         | 目录                                                                                                                                                                           | 说明                       | 官方文档                                                                                                                                                                                 |
| ------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 查询账单流水 | [inquiry-statement-list/](references/statement-management/inquiry-statement-list/)     | 分页查询 WF 账户交易流水   | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_statement_list)                         |
| 查询账单详情 | [inquiry-statement-detail/](references/statement-management/inquiry-statement-detail/) | 查询指定账单流水的详细信息 | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_statement_detail)                       |

注意事项：
- `pageSize` 固定为 10，不允许调用方修改
- `pageNumber` 范围 1-50
- `inquiryStatementDetail` 的 `accountingBizNo` 必须通过 `inquiryStatementList` 获取

### 交易信息管理（trade-order）

> 📄 [官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/trade_order)

| 接口         | 目录                                                                                                                                                      | 说明                                                   | 官方文档                                                                                                                                                                             |
| ------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| 提交交易订单 | [submit-trade-order/](references/trade-order/submit-trade-order/)  | 上传交易订单（B2C 结汇 / B2B 订单关联）               | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/submit_trade_order)                       |
| 查询订单结果 | [inquiry-trade-order/](references/trade-order/inquiry-trade-order/) | 查询上传结果（仅 PAY_INTO_CHINA）                      | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_trade_order)                      |
| 订单回调通知 | [notify-trade-order/](references/trade-order/notify-trade-order/)   | 处理 WF 异步回调通知（仅 PAY_INTO_CHINA）              | [📄](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/notify_trade_order)                       |

注意事项：
- sceneCode 决定必填字段集合
- tradeOrders 上限：B2C 最多 100 笔，B2B 最多 10 笔
- 完整嵌套对象字段定义见 [references/trade-order/field-reference.md](references/trade-order/field-reference.md)

---

## 安全红线

> ⛔ 以下规则为万里汇 API 对接的**安全红线**，违反可能导致资金损失或安全事故，必须严格遵守。

+ **私钥禁止硬编码**：RSA 私钥必须通过文件路径或密钥管理服务加载，严禁将私钥内容硬编码在源代码中。
+ **私钥禁止记日志**：私钥内容不得出现在任何日志输出中，包括 debug 级别日志。
+ **私钥禁止传公共仓库**：私钥文件不得上传到 GitHub、GitLab 等公共代码仓库，必须加入 `.gitignore`。
+ **clientId / secretKey 禁止明文存储**：clientId 和密钥配置必须通过环境变量、配置中心或加密文件管理，禁止明文写入代码或配置文件提交到版本库。
+ **响应必须验签**：收到 WF API 响应后必须使用 WF 公钥验签，确认响应来自万里汇，防止中间人篡改。
+ **异步通知必须验签**：收到 notifyTradeOrder、notifyVostro、notifyBalanceChange、notifyTransfer、notifyPayout、notifyBindBeneficiary 等异步回调通知后，必须先验签再处理业务逻辑，防止伪造通知。
+ **幂等性保障**：转账（createTransfer）和代发（createPayout）等资金类接口必须使用唯一的 transferRequestId / payoutRequestId，防止因重试导致重复扣款。
+ **HTTPS 强制**：所有 API 请求必须通过 HTTPS 发送，禁止使用 HTTP 明文传输。
+ **转账/代发结果不可假定**：发起转账或代发后，必须通过查询接口（inquiryTransfer / inquiryPayout）或异步通知确认最终状态，禁止仅凭请求响应的 status 判定最终结果。
+ **生产密钥与测试密钥隔离**：生产环境和测试环境必须使用不同的 clientId 和密钥对，严禁混用。