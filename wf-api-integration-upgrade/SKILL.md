---
name: wf-api-integration-upgrade
description: |
  WorldFirst Enhanced API 自动集成技能。当客户需要将 WorldFirst API 集成到其 Java 项目时触发。
  Agent 自动检测客户代码环境，生成适配客户项目的生产级集成代码，包含 RSA256 签名验签、HTTP 客户端、Service 和数据模型。不生成测试代码。
---
# WF API Integration Upgrade Skill

帮助用户将 WorldFirst（万里汇）Enhanced API 集成到其 Java 项目中，基于 `/api/open/v1/` 新前缀。

本技能**不是内容仓库，而是路由 + 流程指南**：API 规格放在各接口的 GUIDE.md 中，参考代码放在 `references/` 目录中，SKILL.md 只负责告诉 Agent 集成流程和信息位置。后期新增 API 模块时，需同步更新三处：①模块索引表新增一行；②快速决策树新增模块分支；③场景关键词匹配表新增一行。

## 触发条件

当用户提到以下关键词时触发本技能：
- 集成 WorldFirst / 万里汇 API
- WorldFirst API SDK 集成
- WF API 对接
- 万里汇接口对接

## 快速决策树

决策树分为两层：**业务级**（用户想做什么）→ **模块级**（路由到哪个模块），模块内再根据具体操作路由到接口。Agent 先做模块级判断，读取模块 README 后再做接口级细分。

```
用户咨询 WF API 对接
        |
        +-- OAuth 授权或令牌管理？ --> 授权模块（authorization/oauth2）
        |       |
        |       +-- 发起 OAuth 授权流程（浏览器重定向）？ --> authorize
        |       +-- 使用授权码或刷新令牌换取访问令牌？ --> request_a_token
        |       +-- 撤销访问令牌？ --> revoke_a_token
        |
        +-- 查询账户余额或余额变动历史？ --> 余额模块（accounts/balance）
        |       |
        |       +-- 查询当前账户余额？ --> query_balance
        |       +-- 查询余额变动历史记录？ --> list_balance_history
        |
        +-- 管理全球收款账户？ --> 全局账户模块（accounts/global-accounts）
        |       |
        |       +-- 开通新的全球收款账户？ --> create_a_global_account
        |       +-- 查询某个全球账户详情？ --> query_a_global_account
        |       +-- 列出所有全球账户？ --> list_global_accounts
        |       +-- 更新全球账户信息？ --> update_a_global_account
        |       +-- 关闭全球账户？ --> close_a_global_account
        |
        +-- 查询存款记录？ --> 存款模块（receiving/deposits）
        |       |
        |       +-- 查询单笔存款详情？ --> query_deposit
        |       +-- 分页查询存款列表？ --> list_deposits
        |
        +-- 查询对账单或交易记录？ --> 对账单模块（reporting/statement-report）
        |       |
        |       +-- 分页查询对账单列表？ --> list_statements
        |       +-- 查询单笔对账单详情？ --> query_a_statement
        |
        +-- 管理关联账户？ --> 关联账户模块（connected/connected-accounts）
        |       |
        |       +-- 创建关联账户？ --> create_a_connected_account
        |       +-- 查询关联账户详情？ --> query_a_connected_account
        |       +-- 查询关联账户列表？ --> list_connected_accounts
        |
        +-- 查询汇率或创建报价？ --> 外汇报价模块（foreign-exchange/quote）
        |       |
        |       +-- 批量查询实时汇率？ --> query_rates
        |       +-- 查询历史汇率？ --> query_rate_history
        |       +-- 创建绑定报价？ --> create_quote
        |
        +-- 管理外汇交易？ --> 外汇交易模块（foreign-exchange/deal）
        |       |
        |       +-- 创建交易？ --> create_deal
        |       +-- 查询交易详情？ --> query_deal
        |       +-- 取消交易？ --> cancel_deal
        |       +-- 分页查询交易列表？ --> list_deals
        |       +-- 查询保证金追缴记录？ --> list_margin_charge_records
        |
        +-- 管理外汇结算？ --> 外汇结算模块（foreign-exchange/settlement）
        |       |
        |       +-- 创建结算？ --> create_settlement
        |       +-- 查询结算详情？ --> query_settlement
        |       +-- 分页查询结算列表？ --> list_settlements
        |
        +-- 查询外汇参考数据（交易日历等）？ --> 外汇参考数据模块（foreign-exchange/reference）
        |       |
        |       +-- 查询交易日历（可用交易日/结算日）？ --> query_calendar
        |       +-- 查询支持的货币对？ --> query_supported_currencies
        |
        +-- 上传或下载文件（开户/KYC 材料等）？ --> 文件模块（supporting-service/file）
        |       |
        |       +-- 上传文件（base64 编码）？ --> upload_a_file
        |       +-- 获取文件临时下载链接？ --> download_a_file
        |
        +-- 创建或查询确认函下载任务？ --> 确认函模块（supporting-service/confirmation-letters）
        |       |
        |       +-- 创建确认函下载任务（交易证明函/账户验证函）？ --> create_a_confirmation_letter
        |       +-- 查询确认函任务状态和下载链接？ --> query_a_confirmation_letter
        |
        +-- 提交或查询贸易订单？ --> 贸易订单模块（supporting-service/tradeOrders）
        |       |
        |       +-- 提交一批贸易订单？ --> submit_trade_orders
        |       +-- 查询贸易订单批次处理状态？ --> query_trade_orders
        |       +-- 查询可用结算额度？ --> query_quota
        |
        +-- 管理发卡持卡人？ --> 发卡模块（issuing/cardholders）
        |       |
        |       +-- 注册持卡人并提交 KYC 材料？ --> create_a_cardholder
        |       +-- 查询持卡人详情与审核状态？ --> query_a_cardholder
        |       +-- 分页查询持卡人列表？ --> list_cardholders
        |       +-- 删除持卡人？ --> delete_a_cardholder
        |
        +-- 管理发卡预算账户？ --> 发卡预算模块（issuing/budgets）
        |       |
        |       +-- 创建预算账户？ --> create_a_budget
        |       +-- 查询预算账户详情与多币种余额？ --> query_a_budget
        |       +-- 列出全部预算账户？ --> list_budgets
        |       +-- 预算账户入金（余额账户 → 预算账户）？ --> deposit_to_a_budget
        |       +-- 预算账户出金（预算账户 → 余额账户）？ --> withdraw_from_a_budget
        |       +-- 查询日终余额与当日资金流水？ --> list_daily_balances
        |
        +-- 管理卡片 3DS OTP 通知配置？ --> OTP 配置管理模块（issuing/otp-management）
        |       |
        |       +-- 查询 OTP 是否通过 API 投递？ --> query_otp_config
        |       +-- 开启/关闭 OTP API 通知投递？ --> update_otp_config
        |
        +-- 管理收款人或发起代发？ --> Payouts 分类
                |
                +-- 注册/管理收款人？ --> 收款人模块（payouts/beneficiaries）
                |       |
                |       +-- 查询字段模板？ --> query_beneficiary_template
                |       +-- 创建收款人？ --> create_a_beneficiary
                |       +-- 查询收款人详情？ --> query_a_beneficiary
                |       +-- 列表查询收款人？ --> list_beneficiaries
                |       +-- 更新收款人？ --> update_a_beneficiary
                |       +-- 删除收款人？ --> delete_a_beneficiary
                |       +-- 校验收款人信息？ --> validate_a_beneficiary
                |
                +-- 发起或查询代发？ --> 代发模块（payouts/payouts）
                        |
                        +-- 咨询代发费用和汇率？ --> consult_a_payout
                        +-- 发起代发？ --> create_a_payout
                        +-- 查询代发状态？ --> query_a_payout
```

## 场景关键词匹配

关键词匹配为**模块级路由**，Agent 匹配到模块后，读取模块 README 进一步确定具体接口：

| 关键词 | 路由模块 |
|--------|----------|
| OAuth、授权、authorize、request_a_token、revoke_a_token、access token、refresh token、授权码、令牌、撤销令牌、OAuth2、scope、权限范围、authCode、回调授权 | 授权（authorization/oauth2） |
| 余额、账户余额、balance、查余额、query_balance、账户可用余额、币种余额、余额查询、余额变动、余额历史、balance history、list_balance_history、动账记录、余额流水 | 余额（accounts/balance） |
| 全球账户、global account、create_a_global_account、query_a_global_account、list_global_accounts、update_a_global_account、close_a_global_account、开立账户、关闭账户、收款账户、全球收款 | 全局账户（accounts/global-accounts） |
| 存款、deposit、query_deposit、list_deposits、收款记录、入账记录、存款详情、存款列表、退款存款、deposit status | 存款（receiving/deposits） |
| 收款人、beneficiary、create_a_beneficiary、query_a_beneficiary、list_beneficiaries、update_a_beneficiary、delete_a_beneficiary、validate_a_beneficiary、query_beneficiary_template、绑定收款人、删除收款人、收款人列表、收款人模板 | 收款人（payouts/beneficiaries） |
| 代发、payout、consult_a_payout、create_a_payout、query_a_payout、发起代发、代发查询、代发咨询、跨币种代发、汇率报价、payout status | 代发（payouts/payouts） |
| 关联账户、connected account、create_a_connected_account、query_a_connected_account、list_connected_accounts、创建关联账户、查询关联账户、关联账户列表、商户入驻、KYC、KYB、referenceAccountId | 关联账户（connected/connected-accounts） |
| 对账单、statement、list_statements、query_a_statement、交易记录、账单查询、对账记录、statement report、交易流水、资金流水、reconciliation | 对账单（reporting/statement-report） |
| 汇率、exchange rate、query_rates、query_rate_history、create_quote、实时汇率、历史汇率、报价、FX quote、currency pair、货币对、spot deal、forward deal、即期、远期、外汇 | 外汇报价（foreign-exchange/quote） |
| 交易、deal、create_deal、query_deal、cancel_deal、list_deals、list_margin_charge_records、外汇交易、创建交易、查询交易、取消交易、deal status、交易状态、PROCESSING、SUCCESS、CANCELED、FAILED、保证金、margin、保证金追缴、margin charge、保证金冻结、保证金释放、MarginScene、MarginChargeStatus、初始保证金、追加保证金、强平、CLOSED_OUT、交易列表、分页查询交易、交易对账 | 外汇交易（foreign-exchange/deal） |
| 结算、settlement、create_settlement、query_settlement、list_settlements、创建结算、查询结算、结算状态、settlement status、PROCESSING、SUCCESS、FAILED、SPOT、FORWARD、UNFUNDED_SPOT、结算列表、分页查询结算、交割核对 | 外汇结算（foreign-exchange/settlement） |
| 交易日历、trading calendar、query_calendar、queryCalendar、可用日期、availableDates、交易日、结算日、DEAL_CALENDAR、SETTLEMENT_CALENDAR、calendarType、参考数据、reference、query_supported_currencies、支持币种、支持的货币对、supported currencies、币种支持 | 外汇参考数据（foreign-exchange/reference） |
| 文件上传、文件下载、upload_a_file、download_a_file、upload file、download file、base64 文件、fileContent、fileName、fileId、下载链接、downloadUrl、KYC 材料、开户附件、supporting file | 文件（supporting-service/file） |
| 确认函、confirmation letter、create_a_confirmation_letter、query_a_confirmation_letter、交易证明函、账户验证函、STATEMENT_DETAIL_LETTER、ACCOUNT_VERIFICATION_LETTER、statementId、globalAccountId、electronicallySigned、下载任务、task status、PROCESSING、SUCCESS、FAIL、downloadUrl、failureCode | 确认函（supporting-service/confirmation-letters） |
| 贸易订单、trade order、submit_trade_orders、query_trade_orders、query_quota、提交贸易订单、查询贸易订单、贸易订单批次、可用结算额度、available settlement quota、B2B 贸易、B2C 贸易、额度累积、quotaAccumulation、sceneCode、tradeCategory、GOODS、SERVICE、referenceOrderNo、batchRequestId | 贸易订单（supporting-service/tradeOrders） |
| 持卡人、cardholder、create_a_cardholder、query_a_cardholder、list_cardholders、delete_a_cardholder、注册持卡人、创建持卡人、查询持卡人、删除持卡人、发卡、issuing、KYC 审核、审核状态、CardholderType、CardholderStatus、INDIVIDUAL、EMPLOYEE、SHAREHOLDER、PENDING、ACTIVE、FAILED、身份证明文件、证件材料 | 发卡持卡人（issuing/cardholders） |
| 预算账户、budget、budget account、create_a_budget、query_a_budget、list_budgets、deposit_to_a_budget、withdraw_from_a_budget、list_daily_balances、发卡预算、创建预算、预算充值、预算提现、入金、出金、划拨资金、预算余额、多币种余额、balanceAmounts、balanceType、NORMAL_BALANCE、SAME_NAME_TOP_UP_BALANCE、日终余额、每日余额、daily balance、endOfDayBalance、资金流水、31 天、游标分页 | 发卡预算账户（issuing/budgets） |
| OTP、otp、3DS、验证码、一次性密码、query_otp_config、update_otp_config、otpApiNotifyPreference、OtpApiNotifyPreference、ON、OFF、OTP 通知、OTP 配置、验证码投递、验证码接收、API 投递、发卡配置 | OTP 配置管理（issuing/otp-management） |

## 模块与接口索引

Agent 通过 `references/` 目录结构发现可用模块和接口。每个接口的完整 API 规格（endpoint、请求参数、响应参数、错误码、示例 JSON）位于对应的 GUIDE.md 中。

| 模块 | 目录 | 接口 |
|------|------|------|
| 授权 | references/authorization/oauth2 | authorize, request_a_token, revoke_a_token |
| 余额 | references/accounts/balance | query_balance, list_balance_history |
| 全局账户 | references/accounts/global-accounts | create_a_global_account, query_a_global_account, list_global_accounts, update_a_global_account, close_a_global_account |
| 关联账户 | references/connected/connected-accounts | create_a_connected_account, query_a_connected_account, list_connected_accounts |
| 存款 | references/receiving/deposits | query_deposit, list_deposits |
| 收款人 | references/payouts/beneficiaries | query_beneficiary_template, create_a_beneficiary, query_a_beneficiary, list_beneficiaries, update_a_beneficiary, delete_a_beneficiary, validate_a_beneficiary |
| 代发 | references/payouts/payouts | consult_a_payout, create_a_payout, query_a_payout |
| 对账单 | references/reporting/statement-report | list_statements, query_a_statement |
| 外汇报价 | references/foreign-exchange/quote | query_rates, query_rate_history, create_quote |
| 外汇交易 | references/foreign-exchange/deal | create_deal, query_deal, cancel_deal, list_deals, list_margin_charge_records |
| 外汇结算 | references/foreign-exchange/settlement | create_settlement, query_settlement, list_settlements |
| 外汇参考数据 | references/foreign-exchange/reference | query_calendar, query_supported_currencies |
| 文件上传/下载 | references/supporting-service/file | upload_a_file, download_a_file |
| 确认函 | references/supporting-service/confirmation-letters | create_a_confirmation_letter, query_a_confirmation_letter |
| 贸易订单 | references/supporting-service/tradeOrders | submit_trade_orders, query_trade_orders, query_quota |
| 发卡持卡人 | references/issuing/cardholders | create_a_cardholder, query_a_cardholder, list_cardholders, delete_a_cardholder |
| 发卡预算账户 | references/issuing/budgets | create_a_budget, query_a_budget, list_budgets, deposit_to_a_budget, withdraw_from_a_budget, list_daily_balances |
| OTP 配置管理 | references/issuing/otp-management | query_otp_config, update_otp_config |

> Agent 在生成代码前，必须读取目标接口的 GUIDE.md 获取 API 规格。
>
> **GUIDE 所在目录名以目标模块 README「接口列表」列给出的路径为准，不要由接口名直译拼路径**。多数接口目录名等于接口名（如 `list_deals` → `list-deals/`）。

存量有 8 个接口目录名省略了 `_a_`，直译会读空：

| 接口名 | 实际 GUIDE 目录 |
|--------|-----------------|
| `request_a_token` / `revoke_a_token` | `request-token/` / `revoke-token/` |
| `create_a_connected_account` / `query_a_connected_account` | `create-connected-account/` / `query-connected-account/` |
| `upload_a_file` / `download_a_file` | `upload-file/` / `download-file/` |
| `create_a_confirmation_letter` / `query_a_confirmation_letter` | `create-confirmation-letter/` / `query-confirmation-letter/` |

## 集成流程

集成过程分为 4 个阶段，**必须按顺序执行**。

### Phase 0: 配置参数收集（最先执行）

在开始任何环境检测和代码生成之前，向客户询问并收集以下必要配置参数：

| 参数 | 说明 | 示例                                                                                                                            |
|------|------|-------------------------------------------------------------------------------------------------------------------------------|
| clientId | WorldFirst 分配的客户端 ID | —                                                                                                                             |
| privateKeyPath | RSA 私钥文件路径（PKCS#8 格式） | /path/to/private_key.pem                                                                                                      |
| publicKeyPath | WorldFirst 公钥文件路径 | /path/to/public_key.pem                                                                                                       |
| baseUrl | API 基础 URL | 生产: <br/>SG: https://open-sea.worldfirst.com <br/>EU: https://open-eu.worldfirst.com <br/>US: https://open-na.worldfirst.com，<br/>沙箱: https://open-sitprod-sg.alipay.com |

> ⛔ 安全要求：密钥不可硬编码在代码中，必须通过文件路径、环境变量或配置中心加载。

### Phase 1: 环境检测

Agent 必须读取客户项目文件，检测以下信息，检测结果将决定 Phase 3 的代码生成策略：

| 检测项 | 检测方法 | 用途 |
|--------|----------|------|
| 构建工具 | 读取 `pom.xml`（Maven）或 `build.gradle`（Gradle） | 确定构建系统，决定依赖添加方式 |
| Java 版本 | 从构建文件的 `maven.compiler.source` / `sourceCompatibility` 或 `.java-version` 文件获取 | OkHttpClient（OkHttp 4.x）需要 Java 8+，低于则告知客户升级 |
| 框架类型 | 检查是否有 `spring-boot-starter` 依赖 | Spring Boot 项目生成 AutoConfiguration，普通 Java 项目生成手动 Builder |
| 基础包名 | 定位 `@SpringBootApplication` 注解所在类的包名作为基础包名；若无 Spring Boot，找到 `src/main/java` 下最深的公共业务包前缀（排除 java/javax/org/com 等标准包名单级） | 生成代码的包名，替换参考代码中的 `{basePackage}` 占位符 |
| 目标代码路径 | 分析项目的包组织结构模式（见下方「目标代码路径检测规则」），确定 WF 集成代码应放入的完整包路径 | 决定代码生成的目标目录和 package 声明，替换参考代码中的 `{basePackage}.wf` 占位符 |
| 现有依赖 | 检查是否已有 fastjson2、commons-lang3、HTTP 客户端等依赖 | Phase 2 仅补充缺失依赖 |
| 代码风格 | 检查是否使用 Lombok（搜索 `@Data`/`@Builder` 注解或 lombok 依赖） | Lombok 项目用 `@Data + @Builder`，非 Lombok 项目用手写 getter/setter + ToStringBuilder |
| 配置方式 | Spring Boot 检查 `application.yml`/`application.properties`；普通 Java 检查配置文件 | Spring Boot 生成 yml 配置项模板，普通 Java 生成 Builder 示例 |

#### 目标代码路径检测规则

Agent 必须分析客户项目的包组织结构，确定 WF 集成代码的目标包路径（`{wfPackage}`）。检测优先级如下：

1. **用户指定**：如果用户明确指定了代码生成路径（如"生成到 adapter/port/worldfirst 下"），直接使用用户指定的路径
2. **已有 WF 代码**：搜索项目中是否已存在 WF 相关代码（搜索 `WfClientConfig`、`WfApiClient`、`WfSignatureUtil` 等类名），如已存在则复用其所在包路径
3. **项目结构模式识别**：分析 `src/main/java` 下的包结构，识别项目采用的架构模式，按以下规则推导目标包路径：

| 架构模式 | 识别特征 | 目标包路径规则 | 示例 |
|----------|----------|----------------|------|
| **DDD 分层 + 端口适配器** | 存在 `adapter/port/`、`gateway/`、`infrastructure/` 等目录 | `{basePackage}.infrastructure.adapter.port.worldfirst` | `com.example.infrastructure.adapter.port.worldfirst` |
| **DDD 分层（无适配器）** | 存在 `infrastructure/` 但无 `adapter/` | `{basePackage}.infrastructure.gateway.worldfirst` | `com.example.infrastructure.gateway.worldfirst` |
| **传统分层** | 存在 `service/`、`dao/`、`controller/` 等目录 | `{basePackage}.integration.worldfirst` | `com.example.integration.worldfirst` |
| **扁平结构** | 无明显分层，所有类在同一包下 | `{basePackage}.wf` | `com.example.wf` |

4. **多模块项目**：如果项目有多个 Maven/Gradle 模块，WF 集成代码应放入 `infrastructure` 或 `gateway` 相关的模块中（通常是负责外部调用的模块），而非 `app` 或 `trigger` 模块
5. **兜底策略**：如果无法识别架构模式，向用户确认目标包路径，不要默认使用 `{basePackage}.wf`

**检测输出**中必须包含目标代码路径，例如：

```
- 目标代码路径：org.provider.infrastructure.adapter.port.worldfirst（DDD 端口适配器模式）
```

**检测输出**：Agent 应在回复中明确列出检测结果，例如：

```
环境检测结果：
- 构建工具：Maven (pom.xml)
- Java 版本：17
- 框架类型：Spring Boot 3.2
- 基础包名：com.example.mypayment
- 现有依赖：fastjson2 ✓, commons-lang3 ✗, Lombok ✓
- 配置方式：application.yml
```

### Phase 2: 依赖补充

根据 Phase 1 检测结果，**仅补充项目中完全缺失的依赖**。如果项目已有功能等效的依赖（即使是较低版本），只要能满足 WF API 集成需求，就不应升级或替换。

> **核心原则**：已有依赖能用就不动。只有在依赖完全不存在时才添加新依赖。升级已有依赖可能引入兼容性问题，应尽量避免。

| 依赖 | 坐标 | 推荐版本 | 条件 |
|------|------|----------|------|
| fastjson2 | `com.alibaba.fastjson2:fastjson2` | `2.0.51` | 仅在项目中完全不存在任何 JSON 库时添加（如已有 fastjson/fastjson2/Jackson/Gson 则复用） |
| okhttp | `com.squareup.okhttp3:okhttp` | `4.12.0` | 仅在项目中完全不存在 HTTP 客户端时添加（如已有 okhttp/HttpClient/Apache HttpClient 则复用） |
| commons-lang3 | `org.apache.commons:commons-lang3` | `3.12.0` | 仅在项目中完全不存在时添加 |

### Phase 3: 代码生成

代码统一放入 `{wfPackage}.*` 包下（`{wfPackage}` 由 Phase 1 的「目标代码路径检测规则」确定），分两层生成：

1. 读取 `references/common/java/` 中的参考实现，生成公共基础设施（WfClientConfig、WfSignatureUtil、WfApiClient、Result、WfErrorCode、WfException、Amount、Address、UserName）到客户项目
2. 读取 `references/{目标模块}/java/` 中的参考实现，生成模块 Service 和数据模型
3. 读取目标接口的 GUIDE.md（接口目录名见目标模块 README 的「接口列表」）获取 API endpoint 和参数规格
4. 根据环境适配规则调整生成的代码
5. 使用 Phase 0 收集的配置参数填充配置类

**环境适配规则**：

- **Spring Boot 项目**：参考 `references/common/java/spring/WfAutoConfiguration.java`，生成 `@Configuration` + `@Bean` 配置，在 `application.yml` 中添加 wf 配置项模板；Service 可加 `@Service` 注解，通过依赖注入使用
- **普通 Java 项目**：使用 Builder 手动构建 WfClientConfig，配置通过代码中的 Builder 或外部配置文件管理
- **Lombok 项目**：模型类使用 `@Data` + `@Builder` 注解，移除手写 getter/setter 和 ToStringBuilder
- **非 Lombok 项目**：标准 POJO + 手写 getter/setter + ToStringBuilder（Apache Commons Lang）
- **JSON 库适配**：如果客户已有 Jackson 而非 fastjson2，Agent 可选择适配为 ObjectMapper，或仍添加 fastjson2 依赖
- **包名适配**：使用 Phase 1 检测到的 `{wfPackage}`（目标代码路径），替换参考代码中的 `{basePackage}.wf` 占位符，直接用实际包名（如 `com.example.infrastructure.adapter.port.worldfirst`）

> 公共基础设施仅首次生成，已存在则复用，不重复生成。详细复用策略见 [公共代码复用规则](#公共代码复用规则)。

### Phase 4: 代码生成后检查

代码生成完成后，**必须**执行以下检查，确保代码质量和完整性。

#### 1. Model 类字段一致性检查

遍历生成的所有 Model 类（位于 `model/domain/`、`model/request/`、`model/response/`），对比每个生成的类与对应参考模板，检查字段数量、字段名称、字段类型是否一致。

```bash
# 对比生成的 Model 与参考模板
# Maven 项目
diff src/main/java/com/example/wf/model/response/BalanceResponse.java \
  references/accounts/balance/java/model/response/BalanceResponse.java
```

#### 2. 编译检查

进入客户项目目录，运行编译命令，确保无编译错误：

```bash
# Maven
mvn compile -q

# Gradle
./gradlew compileJava -q
```

**预期结果**：编译成功，无错误。如出现编译错误，修复后重新编译直到通过。

#### 3. 包名和导入检查

- 包名与 Phase 1 检测到的 `{wfPackage}` 一致，所有 `{basePackage}.wf` 占位符已替换为实际包名（如 `com.example.infrastructure.adapter.port.worldfirst`）
- 无缺失的 import 语句
- 无循环依赖
- Spring Boot 项目确认 `@Service` / `@Configuration` 注解的包路径正确

#### 检查记录

每次生成代码后，在回复中必须包含以下检查记录：

```
✅ 代码生成后检查完成

1. Model 类字段一致性检查：
   - [x] xxxxResponse.java - N个字段，与参考一致
   - [x] xxxxResponse.java - N个字段，与参考一致

2. 编译检查：
   - [x] 编译成功，无错误

3. 包名和导入检查：
   - [x] 包名正确，{basePackage}.wf 已替换为 com.example.xxx（实际 {wfPackage} 路径）
   - [x] 导入语句完整
   - [x] 无循环依赖
```

## 签名算法

所有 WF Enhanced API 使用 RSA256（SHA256withRSA）签名。详细签名流程与密钥格式见 `references/common/README.md`，此处仅列关键要点。

### 请求签名

**签名串格式**（两行）：

```
POST {endpoint}
{clientId}.{requestTime}.{body}
```

- 第一行：HTTP 方法 + 空格 + API 路径（如 `POST /api/open/v1/balances/query`）
- 第二行：clientId + `.` + requestTime + `.` + 请求体 JSON 字符串

**签名步骤**：构造待签名内容 → 私钥 SHA256withRSA 签名 → Base64 编码 → URL 编码 → 放入 `Signature` 请求头：`algorithm=RSA256, keyVersion=2, signature={urlEncodedBase64}`

### 响应验签

- 验签串格式与请求签名相同，但用响应头中的 `Response-Time` 替代 `Request-Time`，用响应体替代请求体
- `WfApiClient` 在返回响应前已强制验签，验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`）
- 调用方无需手动检查验签结果，拿到的响应均已完成验签

### 密钥格式

| 密钥 | 格式 | 标记 |
|------|------|------|
| 私钥 | PKCS#8 | `-----BEGIN PRIVATE KEY-----` |
| 公钥 | X.509 | `-----BEGIN PUBLIC KEY-----` |

## 安全红线

> ⛔ 以下规则为万里汇 API 对接的安全红线，违反可能导致资金损失或安全事故，必须严格遵守。

- **私钥禁止硬编码**：RSA 私钥必须通过文件路径或密钥管理服务加载，严禁将私钥内容硬编码在源代码中。
- **私钥禁止记日志**：私钥内容不得出现在任何日志输出中，包括 debug 级别日志。
- **私钥禁止传公共仓库**：私钥文件不得上传到 GitHub、GitLab 等公共代码仓库，必须加入 `.gitignore`。
- **响应强制验签**：`WfApiClient` 在返回响应前已使用 WF 公钥强制验签，验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），确保响应来自万里汇，防范中间人攻击。
- **异步通知必须验签**：收到 WF API 异步回调通知后，必须先使用 WF 公钥验签再处理业务逻辑，防止伪造通知。验签通过后方可执行业务逻辑，否则丢弃通知并记录告警日志。
- **幂等性保障**：创建类接口（如 `create_a_global_account`）必须使用唯一的 `requestId`，防止因网络重试导致重复创建。相同 `requestId` + 不同 body 时，万里汇返回 `REPEAT_REQ_INCONSISTENT`。
- **资金类接口结果不可假定**：发起代发后，必须通过 `query_a_payout` 查询接口或异步通知确认最终状态，禁止仅凭 `create_a_payout` 响应的 status 判定最终结果。代发状态包括 `PROCESSING`（处理中）、`SUCCESS`（成功）、`FAIL`（失败）、`RETURN`（退回）。
- **HTTPS 强制**：所有 API 请求必须通过 HTTPS 发送，禁止使用 HTTP 明文传输。
- **环境隔离**：生产环境（`https://open-sea.worldfirst.com`）和沙箱环境（`https://open-sitprod-sg.alipay.com`）必须使用不同的 clientId 和密钥对，严禁混用。
- **clientId 禁止明文存储**：clientId 和密钥配置必须通过环境变量、配置中心或加密文件管理，禁止明文写入代码或配置文件提交到版本库。`WfClientConfig` 的 `Builder` 支持从环境变量读取配置值。
- **敏感对象日志脱敏**：禁止在日志中打印 `WfClientConfig` 对象（含私钥引用）和 `WfApiResponse` 完整响应体（含金融数据），参考代码已对 `toString()` 做脱敏处理（密钥字段显示为 `[PROTECTED]`）。

## 参考代码

参考实现代码位于 `references/` 目录，Agent 应将这些作为参考模式，根据环境检测结果适配后生成到客户项目中：

- `references/common/java/` — 公共基础设施（WfClientConfig, WfSignatureUtil, WfApiClient, Result, WfErrorCode, WfException, Amount, Address, UserName）
- `references/common/java/spring/WfAutoConfiguration.java` — Spring Boot 自动配置参考
- `references/{模块}/java/` — 模块 Service 和数据模型
- `references/{模块}/{接口目录}/GUIDE.md` — 接口 API 规格和集成指引；接口目录名以模块 README 的接口列表为准（见上方「模块与接口索引」的说明）

> **注意**：参考代码使用 `{basePackage}` 占位符，Agent 生成时必须替换为 Phase 1 检测到的实际包名。

### 公共代码复用规则

以下 9 个公共组件为共享基础设施，遵循不同的复用策略：

| 组件 | 复用策略 |
|------|----------|
| `WfClientConfig` | 仅首次生成，已存在则复用。如客户项目已有等效配置类，可直接适配复用 |
| `WfSignatureUtil` | 仅首次生成，已存在则复用 |
| `WfApiClient` | 仅首次生成，已存在则复用 |
| `Result` | 仅首次生成，已存在则复用 |
| `WfErrorCode` | **追加模式**：新接口的错误码追加到已有枚举文件末尾，不重复生成整个文件，不覆盖已有错误码 |
| `WfException` | 仅首次生成，已存在则复用 |
| `Amount` | 仅首次生成，已存在则复用。各业务模块的金额字段统一引用此类型 |
| `Address` | 仅首次生成，已存在则复用。各业务模块的地址字段统一引用此类型 |
| `UserName` | 仅首次生成，已存在则复用。各业务模块的姓名字段统一引用此类型 |

> **WfErrorCode 追加操作指引**：当集成新接口引入新的错误码时，读取客户项目中已有的 `WfErrorCode.java`，在枚举末尾追加新错误码常量，保持原有错误码不变。如文件不存在则按参考模板完整生成。
