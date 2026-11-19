# Enhanced API 新模块模板验证

验证 Batch Payouts（9）、Cards Management（5）、Transactions（1）、Download Report（3）、Request for Information（3），合计 21 个接口。此目录验证的是 Skill 模板，不是 wf-api-mock 的业务实现。

需要 Python 3、Maven、JDK 8+。在仓库根目录运行：

```bash
python3 tests/upgrade-modules/validate.py
```

若机器默认 Java 版本与目标不同，可先设置 `JAVA_HOME`。验证器将 `{basePackage}` 替换为 `validation`，在临时目录生成 Maven 项目并编译，不改模板或 mock 工作区。首次运行通过 Maven Central 下载依赖；已有缓存后可用 `--offline` 禁止 Maven 联网。静态检查可单独运行 `--static-only`。

验证内容：

- 三处路由、21 个 GUIDE/endpoint、68 个模型字段及本地引用路径。
- `ExtendedModuleContractTest.java`：21 个接口 × 5 个测试方法 = 105 个执行项，覆盖官方 JSON、endpoint、真实 RSA 请求签名/响应验签、F/U 结果、篡改响应，以及两类代理账户头。
- `ModelMappingTest.java`：68 个模型分别验证所有字段的 JSON 往返，包括嵌套费用、限额、附件、64 位金额、精确汇率和日志脱敏。
- `ModuleBoundaryTest.java`：10 个关键边界，覆盖批次/项字段隔离、卡片更新及敏感数据、交易退款、报表嵌套结构、RFI 三类答案及请求头。

合计 **183 个测试执行项**，不是 183 个 API 或 183 个手写方法。还编译旧 Payouts、OTP 模板以检查公共客户端重载的兼容性。

`contracts.json` 的请求/响应示例与 endpoint 来自各 GUIDE 链接的官方文档（2026-11-18 核对）；模型字段清单覆盖示例未出现的可选字段。更新接口时须对照官网同步 GUIDE、模板和测试契约，不能仅修改断言让测试通过。

边界：HTTP 发送由测试中的 OkHttp interceptor 在进程内模拟，RSA 密钥临时生成；不发出任何 WF 请求。未验证真实账户权限、服务端参数校验、审批/清算、异步状态时序、实际文件下载或 Agent 自动选择 Skill 的效果。Java 模板按仓库规则采用薄封装，字段约束和业务流程交给调用方按 GUIDE 实现。
