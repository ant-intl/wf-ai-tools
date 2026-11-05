# Language-Agnostic WF API Integration

当客户不使用 Java、Golang、Python 或 PHP 时，使用本适配流程，在**客户项目内**生成符合其技术栈和既有规范的 WorldFirst API 集成代码。

这里的“任意语言”是**契约驱动的适配能力**，不是对所有语言的零验证承诺。目标技术栈必须满足下方能力要求，且生成结果必须经过可用的构建、静态检查和签名测试。

## 1. 适用范围与硬边界

目标语言或其可靠依赖必须提供：

| 能力 | 要求 |
|------|------|
| HTTPS | 支持证书校验、超时、禁用或显式控制重定向，禁止明文 HTTP |
| JSON | 能保留 API 字段名、嵌套对象、数组、布尔值、空对象及空数组 |
| RSA256 | 使用经过验证的库执行 SHA256withRSA / RSA PKCS#1 v1.5 签名与验签 |
| 密钥格式 | 能读取 PKCS#8 PEM 私钥和 WF PEM 公钥，或先安全转换为受支持格式 |
| 编码 | 支持 UTF-8、Base64、URL encode/decode |
| 金额整数 | 支持有符号 64 位整数或无损的更大整数，禁止使用浮点数表示 `Amount.value` |
| Webhook | 能读取未经改写的原始请求体、请求头和实际 request URI |

如果出现以下情况，**停止生成并报告阻塞**，不得自行发明密码算法或用不安全替代方案：

- 没有可维护的 RSA256 或 HTTPS 实现；
- JSON 无法保留字段名或 64 位整数精度；
- 无法取得 Webhook 原始请求体或实际 request URI；
- 目标依赖存在未接受的高危漏洞；
- 无法执行任何目标技术栈的语法、构建或签名验证。

## 2. 事实来源与优先级

生成过程中必须区分“接口契约”和“实现参考”：

1. **接口契约**：目标接口的 `GUIDE.md`；嵌套字段复杂时追加读取模块的 `field-reference.md`。
2. **公共规则**：`references/common/README.md` 和主 `SKILL.md` 的签名算法、安全红线。
3. **字段交叉检查**：同接口的现有 request/response/domain 模型。
4. **实现参考**：现有 Java、Golang、Python、PHP 的公共层、Client、Controller 和测试。
5. **项目适配**：客户项目的目录、依赖、编码规范、框架和现有封装。

规则：

- `GUIDE.md` 决定 API 路径、字段、必填条件、业务状态和错误处理。
- 现有语言模板用于理解实现结构，**不得覆盖 GUIDE 中的契约**。
- 不同语言模板不一致时，以 GUIDE 为准；GUIDE 仍不明确时，先向用户指出差异，不得猜测。
- 不根据目标语言的命名习惯改变 JSON 字段名。

## 3. 技术栈识别

先检查客户项目，再提问。不得仅根据用户提到的语言名称假设项目结构。

### 3.1 项目证据

检查适用的证据，而不是机械扫描所有文件：

- 构建与依赖清单，例如 `package.json`、`Cargo.toml`、`*.csproj`、`Gemfile`、`mix.exs`、`pubspec.yaml`、`Package.swift`、`build.sbt`、`CMakeLists.txt`；
- lock 文件和运行时版本声明；
- 现有 HTTP、JSON、加密、配置和测试代码；
- 目录结构、命名、格式化配置、静态检查配置和 CI 命令；
- 框架原有的中间件、Controller、路由、依赖注入及异常处理机制。

### 3.2 形成 Stack Profile

生成代码前输出并确认以下信息：

```text
Language / Runtime：
Framework：
Package manager：
Project root：
Module / namespace convention：
HTTP client：
JSON serializer：
RSA / PEM library：
Test runner：
Build / lint commands：
Existing files to reuse：
New dependencies requiring approval：
Reference templates selected：
```

能从项目确认的内容不要重复询问。信息冲突或无法确认时，只询问缺失项。

同时按主 Skill 的要求收集目标接口、项目路径、Client ID、User ID、Base URL 和密钥**文件路径**。不得索取私钥内容，不得把真实凭证写入源代码。

## 4. 参考实现映射

根据语义相近程度选择一至两个参考实现，避免同时加载全部语言造成上下文浪费。

| 目标技术栈 | 优先参考 | 主要借鉴点 |
|------------|----------|------------|
| C# / .NET、Kotlin、Scala | Java | 类型模型、异常、配置、Client 结构 |
| Rust、C、C++ | Golang | 显式错误处理、原始字节、接口注入、轻量 Client |
| TypeScript | Java + Golang | 类型模型与 Promise/错误边界；仅读取所需部分 |
| JavaScript、Ruby、Lua | Python | 动态模型、字典序列化、模块组织 |
| Swift、Objective-C、Dart | Java 或 Golang | 类型映射、异步 HTTP、错误处理 |
| Elixir、R、其他动态语言 | Python | 数据映射和公共层职责划分 |
| 其他语言 | 语义最接近的一种 | 只参考职责与算法，不照搬语法 |

参考语言不等于目标实现。必须保留目标项目已有封装，除非它违反 WF 契约或安全红线。

## 5. 适配设计

编码前先生成简短映射表：

| 契约项 | WF 要求 | 参考实现 | 目标项目方案 |
|---------|---------|----------|--------------|
| Config | clientId、base URL、密钥路径、超时 | 选定的 common 模板 | 项目现有配置机制 |
| Model | camelCase 字段及嵌套关系 | 同接口模型 | 目标语言模型和序列化方式 |
| HTTP | 签名请求、强制响应验签 | WfHttpClient 对应实现 | 项目 HTTP Client |
| Error | S 成功，F/U 异常及可重试性 | WfException / Result | 项目异常或 Result 类型 |
| Webhook | 实际 URI + 原始体验签 | Notify Controller | 框架路由适配层 |
| Test | 签名、契约、构建、非生产集成 | 参考测试 | 项目测试框架 |

设计应保持公共层复用：配置、签名、HTTP、错误和模型序列化不得在每个 API 中重复实现。按目标项目习惯合并同一模块的 Client，避免同名模型或 Client 相互覆盖。

## 6. 不可改变的协议与安全规则

### 6.1 主动 API 请求

签名字节串必须严格为：

```text
POST {apiPath}\n{clientId}.{requestTime}.{requestBody}
```

处理顺序：

1. 按模型序列化出最终 JSON 请求体；
2. 对**该次实际发送的同一组字节**签名，不得签名后再次美化或重排 JSON；
3. 使用 RSA SHA256 + PKCS#1 v1.5；
4. Base64 编码签名结果，再进行 URL 编码；
5. 设置 `Client-Id`、`Request-Time`、`Content-Type` 和 `Signature`；
6. 使用 HTTPS 发送。

`Request-Time` 使用带时区偏移且精确到秒的 ISO 8601 格式。

### 6.2 API 响应

- 必须读取原始响应体、`Signature` 和 `Response-Time`。
- 使用 `POST {apiPath}\n{clientId}.{responseTime}.{responseBody}` 验签。
- 缺少签名头、缺少时间头或验签失败时必须拒绝响应，不得仅告警后继续。
- 必须先验签，再把响应体反序列化为业务模型。
- `resultStatus=F/U` 按统一错误模型处理；不得将 HTTP 200 等同于业务成功。

### 6.3 Webhook

- 签名 URI 是框架接收到的**实际 request URI（路径及查询字符串）**，不是根据通知名称拼出的 WF API 路径。
- 使用请求头中的 `Client-Id`、`Request-Time` 和未经改写的原始请求体验签。
- 验签成功后再解析 JSON；失败时不得执行处理器或返回 SUCCESS。
- 响应签名使用同一实际 URI 和新的 `Response-Time`。
- 按接口业务主键做持久化幂等，耗时业务异步处理。

### 6.4 数据与安全

- `Amount.value` 使用最小货币单位的整数，不使用 float/double；JavaScript 等场景使用 BigInt 或无损序列化方案。
- JSON 字段保持 camelCase；按契约省略 null 可选字段，但不得误删 `0`、`false`、必需的空对象或空数组。
- 私钥只从文件、环境变量指定的路径、KMS 或项目密钥系统加载；不得硬编码、记日志或提交仓库。
- 转账和代发使用唯一请求 ID，最终结果通过查询或通知确认。
- 生产与测试凭证隔离，真实资金测试必须显式开启。

## 7. 依赖选择

1. 优先复用客户项目已经锁定并验证的依赖。
2. 新依赖必须选择目标生态中持续维护、支持所需算法的库，并说明选择理由。
3. 添加前检查许可证、运行时兼容性和漏洞数据库；存在高危漏洞时停止并要求选择安全版本。
4. 未获得用户同意，不安装依赖、不升级现有依赖、不修改全局环境。
5. 不自行实现 RSA、TLS、Base64 或 JSON 解析器。

## 8. 生成顺序

1. 公共配置与密钥加载；
2. RSA256 签名器及独立测试；
3. HTTP Client 与响应验签；
4. 接口模型、校验和序列化；
5. 主动 API Client 与统一错误处理；
6. Webhook 路由适配、验签、幂等入口和签名响应；
7. 单元测试、契约测试及按需的非生产集成测试；
8. 目标项目的依赖和调用说明。

只在客户项目中生成代码，不因一次适配就在本仓库新增一套语言模板。

## 9. 强制验证闭环

按目标项目实际能力执行，不允许只做文本审阅就宣称完成：

- [ ] 依赖兼容性与漏洞检查通过；
- [ ] 格式化、语法解析、编译或类型检查通过；
- [ ] 模型字段与 GUIDE 一致，嵌套对象和数组无遗漏；
- [ ] 签名测试覆盖固定内容、UTF-8 正文、Base64 + URL 编码；
- [ ] 使用独立实现或 OpenSSL 交叉验证签名，测试密钥仅临时生成且不入库；
- [ ] 响应缺签名、错误签名、错误时间头的测试均被拒绝；
- [ ] Webhook 使用实际 URI 和原始请求体，篡改正文后验签失败；
- [ ] 金额大整数、0、false、空对象及 Unicode 序列化测试通过；
- [ ] F/U 错误、重试标记及请求幂等测试通过；
- [ ] 目标项目现有测试无回归。

真实 WF 集成测试仅在非生产环境、用户提供有效配置后执行。无法执行某项时，在交付报告中明确标为“未验证”及原因，不得标记成功。

## 10. 交付格式

交付时按以下顺序报告：

1. **Stack Profile**：识别到的技术栈和证据；
2. **适配决策**：参考模板、复用组件、新增依赖及理由；
3. **文件清单**：新增和修改文件及职责；
4. **契约映射**：接口、模型和特殊字段处理；
5. **验证记录**：实际执行的命令和结果；
6. **未验证项与风险**：缺少运行时、凭证、依赖或测试环境造成的限制；
7. **后续步骤**：用户需要执行的配置和非生产验证。

只有构建和测试真实通过后，才能将生成结果描述为“可编译”或“测试通过”。
