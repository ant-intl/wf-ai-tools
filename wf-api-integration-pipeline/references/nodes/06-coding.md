# 节点 6 · 编码

> 对应 Harness 节点 `coding`（skill：wf-coding，接口知识来自 wf-api-integration-upgrade）。

**目标**：以系分文档为蓝本，在客户工程中生成生产级 WorldFirst 集成代码。
**完成标志**：集成代码已生成、生成后检查（含编译）通过，并向客户列出全部新增 / 修改的文件。

进入本节点即视为客户同意在工程中写入代码；开始前仍要用一句话说明「将在 `<工程目录>` 的哪些位置生成哪些内容」。不生成测试代码；不替客户 commit / push。

## 接口定义获取

WorldFirst 接口持续更新，接口的字段、参数、枚举、错误码**一律在运行时从官方文档中心获取**（每个接口页 URL 加 `.md` 即得纯 Markdown），本地模板、记忆与历史生成结果都不能作为字段依据。对每个目标接口：

1. **确定 endpoint**：取 `app overview` 中 `integratedInterfaces` 的 `requestMethod` + `url`（如 `POST /api/open/v1/globalAccounts/create`）。
2. **找文档页**（按顺序，命中即止）：
   - 已安装 `wf-api-integration-upgrade`（通常与本 skill 位于同一个 skills 目录）且其中有 `references/api-doc-index.json`：在 `apis[]` 中按 `path` 匹配，取 `mdUrl`；
   - 执行 `worldfirst app catalog --json`，按 `url` 找到该接口的 `docUrl`；形如 `https://docs.worldfirst.com/wfdocs/api-sdk/<slug>` 的具体接口页，直接在末尾加 `.md`；
   - `docUrl` 缺失、指向首页或 `*_overview` 概览页时，读取 `https://docs.worldfirst.com/llms.txt`，按接口名找到候选的 api-sdk `.md` 页逐个核对。
3. **一致性校验**：文档中 Definition 行的 `METHOD /api/open/v1/...` 必须与目标 endpoint 完全一致才能采用。
4. **通用文档**（按需获取）：认证 `https://docs.worldfirst.com/wfdocs/api-sdk/authentication.md`，Webhook `https://docs.worldfirst.com/wfdocs/api-sdk/listen_for_webhook_events.md` 与 `https://docs.worldfirst.com/wfdocs/api-sdk/webhooks.md`，公共类型与 Result 见 `llms.txt` 中的 Data Types、Result 页。
5. **获取失败**：明确告知客户「无法获取 <接口> 的最新接口定义」，停止生成该接口的代码；严禁凭记忆、旧模板或推测编造字段、参数、错误码。

## 第 1 步：准备

1. 读取系分文档 `docs/wf-system-analysis.md` 与进度文件中的 `integration.environment`、`integration.keyManagement`、`authMethod`。
2. 按上一节取得每个接口及通用文档的 `.md`。
3. 确定目标代码位置（优先级从高到低）：
   1. 客户指定的路径；
   2. 工程里已有的 WorldFirst 集成代码（搜索 `WfClientConfig`、`WfApiClient`、`WfSignatureUtil` 等类名或同类封装）所在位置，复用不重复生成；
   3. 按工程结构推导：DDD 端口适配器（有 `adapter/port`、`gateway`、`infrastructure`）→ `<基础包>.infrastructure.adapter.port.worldfirst`；DDD 无适配器 → `<基础包>.infrastructure.gateway.worldfirst`；传统分层（有 `service`、`dao`、`controller`）→ `<基础包>.integration.worldfirst`；扁平结构 → `<基础包>.wf`。其它语言按同样思路放在负责外部调用的模块或目录；
   4. 多模块工程放到负责外部调用的模块（infrastructure / gateway），而非 app / trigger 模块；
   5. 仍无法判断时询问客户，不要默认放置。
4. 依赖：只补充工程里完全缺失的依赖（JSON 库、HTTP 客户端等已有功能等效依赖时直接复用），不升级、不替换已有依赖。

## 第 2 步：生成代码

**公共基础设施**（已存在则复用，只生成一次）：

| 组件 | API_KEY 应用 | RSA 应用 |
|---|---|---|
| 配置 | Client ID、网关地址、超时；API-KEY 与 Webhook Secret 按系分方案加载 | Client ID、网关地址、超时；商户私钥与平台公钥按系分方案加载 |
| HTTP 客户端 | HTTPS + JSON；请求头 `Authorization: Bearer <API-KEY>`；不发送 `Client-Id` / `Request-Time` / `Signature` | HTTPS + JSON；请求 SHA256withRSA 签名并带 `Client-Id`、`Request-Time`、`Signature`；响应**强制验签**，失败抛异常 |
| 通知验签 | HMAC-SHA256（Webhook Secret，校验 `request-time` 时间容差，同时尝试 `signature` 与 `previousSignature`，常量时间比较） | 用平台公钥验签；应答按文档签名 |
| 公共模型 | Result、错误码、异常；Amount、Address、UserName 等公共类型 | 同左 |

Java 工程且已安装 `wf-api-integration-upgrade` 时，复用其 `references/common/java` 作为基础设施参考实现（替换 `{basePackage}` 占位符为实际包名）；API_KEY 应用在其基础上改为 Bearer 认证，去掉请求签名与响应验签。其它语言或未安装时，按认证文档生成等价实现。认证细节以 `authentication.md` 为准。

**按接口生成**：每个已集成接口生成 Service 方法与 request / response / domain 模型，字段、类型、必填性、枚举逐一来自该接口的线上 `.md`；新的错误码追加到已有错误码定义末尾，不覆盖已有内容。

**Webhook 接收端点**：基于原始请求体先验签，验签失败拒绝并记录告警；以事件 `id` 去重（重复事件也返回成功应答）；按 `event` 分发处理；处理后返回 HTTP 200 与 `{"result":{"resultStatus":"S","resultCode":"SUCCESS","resultMessage":"success"}}`。端点路径与已订阅的 Webhook URL 保持一致。

**配置与凭证**：配置项按工程既有方式接入（如 Spring Boot 的 `application.yml` 占位、`.env.example`）；凭证严格按系分「认证与密钥管理方案」实现——环境变量 / 外部配置文件 / KMS / 配置中心的加载入口；「完整集成」引入对应 SDK 并完整实现，「Provider 骨架」生成实现骨架并用 TODO 注释标明待补全项。**严禁**在代码中硬编码凭证内容或凭证文件的绝对路径；外部配置文件方案需把凭证文件加入 `.gitignore`。

**框架适配**：Spring Boot 生成 `@Configuration` / `@Bean` 配置与 `@Service`；普通工程用 Builder 手动构建配置；有 Lombok 用 `@Data` / `@Builder`，否则手写 getter / setter；沿用工程已有的 JSON 库与代码风格。

**安全要求**：资金类写接口必须携带唯一 `requestId`；资金结果不可只凭创建接口的响应判定，以查询接口或通知为准；不在日志中打印凭证、配置对象或完整响应体。

## 第 3 步：生成后检查

1. **模型字段一致性**：逐个对比生成的模型与线上 `.md` 的 Request / Response Parameters（字段数量、名称、类型、必填性、枚举），不一致按文档修正后重新检查。
2. **编译**：在工程目录执行对应的编译命令（如 `mvn -q compile`、`./gradlew compileJava -q`、`go build ./...`、`npx tsc --noEmit`、`python -m compileall <目录>`），有错误就修复，直到通过。编译需要下载依赖而网络不可用时如实说明。
3. **包名与导入**：包名与目标位置一致、无缺失导入、无循环依赖、注解与配置的包路径正确。
4. **凭证扫描**：在本次新增 / 修改的文件中搜索 `irak_`、`iwsc_`、`BEGIN PRIVATE KEY`、`BEGIN RSA PRIVATE KEY`，必须没有命中。

在回复中给出检查记录，例如：

```text
生成后检查完成
1. 模型字段一致性：PayoutCreateRequest（14 个字段）、PayoutCreateResponse（6 个字段）与线上文档一致
2. 编译：mvn -q compile 通过
3. 包名与导入：com.example.integration.worldfirst，导入完整，无循环依赖
4. 凭证扫描：无命中
```

## 第 4 步：完成

1. 用表格列出本次新增（added）/ 修改（modified）的全部文件（相对工程根目录）与关键实现说明，写入进度文件 `integration.files`。
2. 提醒客户：按系分方案在本地或部署环境配置好凭证（API_KEY 应用为 API-KEY 与 Webhook Secret，RSA 应用为商户私钥与平台公钥），再启动应用联调；可以先调用一个只读接口（如查询余额）做冒烟验证。
3. 本节点 `DONE`，询问是否进入集成用例规划。
