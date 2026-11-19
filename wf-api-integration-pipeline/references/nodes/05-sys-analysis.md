# 节点 5 · 系分设计

> 对应 Harness 节点 `sys_analysis`（skill：wf-sys-analysis）。

**目标**：确定密钥管理方案与接入参数（只收集元信息），结合接口文档与客户工程结构产出系统分析设计文档。
**完成标志**：`<工程目录>/docs/wf-system-analysis.md` 已生成，并在对话中输出了结构化摘要。

## 本节点用到的事实

- ClientId、认证方式：进度文件与 `app overview`（不要再问客户）。
- API Base URL：取法同基础信息节点（`worldfirst config get base_api_url --json` → 账号地区对应的网关）。
- 已集成接口：`app overview` 的 `integratedInterfaces`（`url`、`requestMethod`、`funcName`）。
- 工程目录与环境报告：工程设置节点的记录。

需要保护的凭证随认证方式不同：

| 认证方式 | 凭证 1（秘密） | 凭证 2 |
|---|---|---|
| API_KEY | API-KEY（请求认证） | Webhook Secret（秘密，用于通知 HMAC 验签） |
| RSA | 商户私钥（请求签名） | WorldFirst 平台公钥（非秘密但需防篡改，用于验证响应与通知签名） |

两项凭证采用同一种管理方案。**严禁索取或收集凭证内容本身**：凭证不会出现在系分文档与生成的代码中，本节点只确定它们的存储与加载机制。

## 第 1 步：选择密钥管理方案

先简短说明本节点目标与已知的 ClientId、API Base URL，此时不读文件、不做工程分析。然后询问（说明「生产系统中凭证不应进入代码库，代码只保留获取机制」）：

1. **环境变量注入**：凭证通过环境变量注入，适合容器化 / K8s / 云原生部署
2. **外部配置文件**：凭证放在部署目录的配置文件中，由运维注入、不进 Git，适合传统虚拟机部署或 Spring Boot 外部化配置
3. **KMS 密钥托管**：凭证托管在 KMS，配置中只存 keyId 或密文引用，运行时获取，适合有安全合规要求的团队
4. **配置中心**：凭证存于 Nacos / Apollo / Vault 等配置中心的加密配置项，适合已有配置中心的团队

## 第 2 步：按方案收集接入参数（只收集元信息）

只问所选方案对应的参数；每项给出默认值，客户可直接采用。不得要求客户提供凭证内容。

| 方案 | 参数 | RSA 默认值 | API_KEY 默认值 |
|---|---|---|---|
| 环境变量 | 凭证 1 变量名 / 凭证 2 变量名 | `WF_PRIVATE_KEY` / `WF_WF_PUBLIC_KEY` | `WORLDFIRST_API_KEY` / `WORLDFIRST_WEBHOOK_SECRET` |
| 外部配置文件 | 配置文件路径（相对部署目录） | `config/wf-credentials.properties` | `config/wf-credentials.properties` |
| | 凭证 1 配置项 / 凭证 2 配置项 | `wf.private-key` / `wf.wf-public-key` | `wf.api-key` / `wf.webhook-secret` |

**KMS 或配置中心**：

1. 先问集成深度：1）生成完整集成代码（引入对应 SDK 并完整实现获取逻辑）　2）生成 Provider 骨架 + TODO（获取逻辑由客户补全）。
2. 选完整集成时再问提供方（KMS 与配置中心只问其一）：
   - KMS：阿里云 KMS / AWS KMS（Secrets Manager）/ 其它 KMS
   - 配置中心：Nacos / Apollo / HashiCorp Vault / 其它配置中心
   - 选「其它」时明确告知：暂不支持为该提供方生成完整代码，编码阶段将生成 Provider 骨架 + TODO，需要客户自行完成凭证获取调用；然后按骨架模式收集参数。
3. 参数默认值：

| 提供方 | 参数 | RSA 默认值 | API_KEY 默认值 |
|---|---|---|---|
| 阿里云 KMS | 凭证 1 secretName / 凭证 2 secretName / regionId | `wf-private-key` / `wf-public-key` / `cn-hangzhou` | `wf-api-key` / `wf-webhook-secret` / `cn-hangzhou` |
| AWS KMS | 凭证 1 Secret ID / 凭证 2 Secret ID / Region | `wf/private-key` / `wf/public-key` / `ap-southeast-1` | `wf/api-key` / `wf/webhook-secret` / `ap-southeast-1` |
| Nacos | 凭证 1 dataId / 凭证 2 dataId / group | `wf.private-key` / `wf.public-key` / `DEFAULT_GROUP` | `wf.api-key` / `wf.webhook-secret` / `DEFAULT_GROUP` |
| Apollo | 凭证 1 key / 凭证 2 key / namespace | `wf.private-key` / `wf.public-key` / `application` | `wf.api-key` / `wf.webhook-secret` / `application` |
| Vault | 凭证 1 secret 路径 / 凭证 2 secret 路径 | `secret/wf/private-key` / `secret/wf/public-key` | `secret/wf/api-key` / `secret/wf/webhook-secret` |
| 骨架模式（KMS） | 凭证 1 / 凭证 2 标识配置项名 | `wf.kms.private-key-id` / `wf.kms.wf-public-key-id` | `wf.kms.api-key-id` / `wf.kms.webhook-secret-id` |
| 骨架模式（配置中心） | 凭证 1 / 凭证 2 配置项名 | `wf.private-key` / `wf.public-key` | `wf.api-key` / `wf.webhook-secret` |

参数确认后写入进度文件 `integration.keyManagement`（方案、深度、提供方、参数名）。

## 第 3 步：分析并生成系分文档

1. 按 [06-coding「接口定义获取」](06-coding.md#接口定义获取) 取得每个已集成接口的线上文档（`.md`），接口字段、参数、错误码以线上文档为唯一权威来源，不得凭记忆臆造；同时取得认证与 Webhook 的通用文档。文档无法访问时，告知客户「无法获取最新接口定义」并停止。
2. 结合工程目录的代码结构，分析 WorldFirst 集成的切入点（放在哪个模块 / 包、如何接入现有配置与 HTTP 能力）。
3. 告知客户将写入 `docs/wf-system-analysis.md`（目录不存在先创建；文件已存在时询问覆盖还是在原文基础上更新），文档包含：
   1. **集成架构**：应用 ↔ WorldFirst 网关的调用链路，以及 Webhook 回调链路。
   2. **集成接口清单**：逐个列出 endpoint、method、业务用途与线上文档链接。
   3. **配置参数**：Client ID、API Base URL（列出实际值），沙箱与生产分环境配置的方式。
   4. **认证与密钥管理方案**（独立章节）：认证方式；所选方案；集成深度与服务提供方（仅 KMS / 配置中心涉及）；接入参数（只写变量名、配置项名、keyId 等元信息，**凭证内容严禁写入**）；代码中的凭证加载入口（如复用 wf-api-integration-upgrade 的 `WfClientConfig`，按其实际提供的密钥入口方法设计）；凭证轮换说明（RSA：WorldFirst 轮换平台公钥时更新配置即可、无需发版；API_KEY：Webhook Secret 轮换重叠期同时校验 `signature` 与 `previousSignature`）；安全红线（凭证不进 Git、不入库、日志脱敏）。
   5. **请求认证与签名验签设计**：
      - API_KEY：请求头 `Authorization: Bearer <API-KEY>`，不发送 `Client-Id`、`Request-Time`、`Signature`；仅走 HTTPS；
      - RSA：请求按 SHA256withRSA 签名（签名串、`Signature` 头格式），响应强制验签，验签失败按异常处理。
   6. **Webhook 接收端点设计**：基于原始请求体先验签（API_KEY 为 HMAC-SHA256 并校验 `request-time` 时间容差；RSA 用平台公钥验签）；以事件 `id` 做幂等去重；处理后返回 HTTP 200 与 `{"result":{"resultStatus":"S","resultCode":"SUCCESS","resultMessage":"success"}}`（RSA 集成按文档对应答签名）；了解失败重试策略。具体规则以线上文档为准。
   7. **异常处理与重试策略**：写操作携带唯一 `requestId` 保证幂等；`REPEAT_REQ_INCONSISTENT` / `REPEAT_REQUEST` 的处理；结果未知时先查询，不换新 `requestId` 重复发起；超时与重试配置。
   8. **联调与验收说明**：沙箱联调方式（必要时用 `worldfirst listen` 转发 Webhook 到本地）；集成验收用例由集成代码发起调用。
4. 在对话中输出文档的结构化摘要（Markdown）。
5. 本节点 `DONE`：写入 `integration.sysAnalysisDoc`，询问是否进入编码。
