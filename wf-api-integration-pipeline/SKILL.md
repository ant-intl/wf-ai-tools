---
name: wf-api-integration-pipeline
description: "WorldFirst（万里汇）API 端到端集成流水线。在客户自己的 AI 编程助手（Claude Code、Codex、Cursor、Qoder 等）里，通过 worldfirst CLI 走完与 Developer Harness 控制台相同的 4 阶段 10 节点：应用创建（API 选型与服务协议签约、Webhook 订阅、安全设置）→ 应用集成（工程设置、系分设计、编码）→ 集成测试（用例规划、用例执行）→ 应用发布（Go-Live 审批、生产配置），进度保存在本地、支持断点续跑。触发词：集成/接入 WorldFirst 或万里汇 API、创建 WorldFirst 应用、WorldFirst 集成流水线、继续 WorldFirst 集成、WorldFirst 验收用例、提交上线审批、WorldFirst 生产配置。只想为指定接口生成代码时改用 wf-api-integration-upgrade。"
version: 1.0.0
---

# WorldFirst API 集成流水线

本 skill 把 Developer Harness（WorldFirst 本地 Web 控制台）的集成流水线搬进对话：由你（AI）按节点编排流程，通过 Bash 调用 `worldfirst` CLI 完成全部真实读写，用对话代替控制台的交互卡片。适用于不使用 Harness 页面、只在自己 AI 助手里工作的客户，覆盖从选择 API 到生产上线的全过程。

## 流水线总览

| 阶段 | # | 节点（id） | 目标 | 完成标志 | 节点文件 |
|---|---|---|---|---|---|
| 应用创建 | 1 | 基础信息 `basic_info` | 选 API → 签署 API 服务协议 → 命名 → 选认证方式 → 创建沙箱应用 | 拿到 `SANDBOX_` 开头的 ClientId | [01-basic-info](references/nodes/01-basic-info.md) |
| | 2 | Webhook 订阅 `webhook` | 订阅事件通知 | 至少 1 个订阅，或客户选择暂不设置 | [02-webhook](references/nodes/02-webhook.md) |
| | 3 | 安全设置 `security_setting` | API_KEY：取密钥、可选 IP 白名单；RSA：上传公钥 | API_KEY 应用进入 TESTING；RSA 已设置商户公钥 | [03-security-setting](references/nodes/03-security-setting.md) |
| 应用集成 | 4 | 工程设置 `dev_settings` | 选集成方式、确认工程目录、Git 准备、识别环境 | 输出环境报告，或选择跳过应用集成 | [04-dev-settings](references/nodes/04-dev-settings.md) |
| | 5 | 系分设计 `sys_analysis` | 确定密钥管理方案，产出系分文档 | `docs/wf-system-analysis.md` 已生成 | [05-sys-analysis](references/nodes/05-sys-analysis.md) |
| | 6 | 编码 `coding` | 在客户工程生成生产级集成代码 | 代码生成且编译检查通过 | [06-coding](references/nodes/06-coding.md) |
| 集成测试 | 7 | 集成用例规划 `integration_case` | 服务端必过用例 1:1 排序并翻译通过条件 | 方案经客户确认并保存 | [07-integration-case](references/nodes/07-integration-case.md) |
| | 8 | 执行集成用例 `case_execution` | 逐用例触发调用、判定日志、核对服务端进度 | 全部必过用例服务端 FINISH | [08-case-execution](references/nodes/08-case-execution.md) |
| 应用发布 | 9 | 提交发布 `release_submit` | KYB L3 → 提交 Go-Live 审批 → 确认上线 | 应用状态 LIVE | [09-release-submit](references/nodes/09-release-submit.md) |
| | 10 | 生产配置 `prod_config` | 生产信息、生产公钥或生产 IP 白名单、子账号权限 | 生产配置完成 | [10-prod-config](references/nodes/10-prod-config.md) |

进入某个节点前，先完整读取对应的节点文件，再按其中步骤执行；其它节点的文件用到时再读。CLI 用法与错误处理见 [references/cli.md](references/cli.md)，进度文件与续跑规则见 [references/state.md](references/state.md)。

## 每次会话的启动流程

1. **前置检查**：按 [cli.md「前置检查」](references/cli.md#前置检查每次会话开始执行) 确认 CLI 已安装、客户已登录。
2. **读取进度**：读取工作区根目录的 `.worldfirst/pipeline-state.json`（[state.md](references/state.md)）。
3. **服务端校准**：有当前应用时，按 [state.md「续跑推导」](references/state.md#续跑推导服务端事实优先) 用服务端事实校准各节点状态。
4. **告知进度**：用进度看板告诉客户「现在在哪一步、接下来做什么」，客户确认后进入当前节点。

没有进度文件时，询问客户：新建应用，还是继续一个已有应用（`app list` 只列 V2 应用）。客户只是咨询某个具体问题（如「怎么查审批进度」）时，直接回答并执行对应查询，不必强行走完整流程。

进度看板格式（状态用文字标注，不用图标）：

```text
WorldFirst 集成进度 · WF_全球付款集成（SANDBOX_XXXX，API_KEY）
1 应用创建：基础信息[完成]  Webhook 订阅[完成]  安全设置[进行中]
2 应用集成：工程设置[未开始]  系分设计[未开始]  编码[未开始]
3 集成测试：集成用例规划[未开始]  执行集成用例[未开始]
4 应用发布：提交发布[未开始]  生产配置[未开始]
```

## 交互约定（用对话代替 Harness 卡片）

- 用客户使用的语言交流；节点文件中给出的固定话术，按客户语言自然转述。
- **一次只问一个决策**。选项编号列出（2–4 个为宜，可标注「推荐」），客户回复编号或文字均可。宿主提供结构化提问工具（如 AskUserQuestion）时，可用它展示同样的选项。
- 长清单（接口、事件、用例）按模块分组编号展示，接受「1,3,5」「2-6」「全部」或模块名等回复；执行写操作前，把解析出的最终清单复述给客户确认。
- 需要客户输入时给出建议默认值与校验规则；不合规时说明原因并重新询问。
- 不替客户做决定：客户没有明确回答的选项，不要默认选中后继续。
- 不要输出 Harness 的 ```` ```interaction ```` JSON 卡片、`stage_complete`、`system_action` 等协议块——这里没有前端渲染它们。
- CLI 返回的业务数据（应用信息、订阅关系、审批进度等）整理成简短表格或键值清单，不要原样倾倒 JSON。

## 节点推进规则

1. 严格按 1→10 的顺序推进；当前节点未达到完成标志前，不做后续节点的工作。客户要求直接跳到后面时，说明需要先完成当前节点及原因。
2. 节点完成后：更新进度文件 → 用两三句话总结本节点结果 → 询问是否进入下一节点，客户确认后再进入。
3. 允许跳过的只有以下几处，被跳过的节点记为 `SKIPPED`：
   - Webhook 订阅：应用从未设置过订阅时，可以「暂不设置」；
   - 工程设置：选择「跳过应用集成」时，系分设计与编码记为 `SKIPPED`，直接进入集成用例规划；
   - 可选项：沙箱与生产 IP 白名单、子账号访问权限。

   其余节点不可跳过：签约是创建应用的前提；RSA 公钥（或 API_KEY 完成安全设置）是集成的前提；必过用例全部通过是提交发布的前提（服务端强制校验）；KYB 认证达到 L3 才能提交发布。
4. 已完成的节点可以随时回去做变更（变更集成 API、修改 Webhook 订阅、更新 RSA 公钥等），按对应节点文件的变更流程执行，完成后回到原进度。变更会影响下游时要提醒客户：例如新增接口后需要补充代码，服务端验收用例集变化后需要重新规划用例。
5. 客户中途离开再回来时，按「启动流程」续跑，不要从头开始。

## 安全红线（违反即严重错误）

1. **密钥明文不进入对话**：不得在回复、命令参数、文件或推理中输出、复述或索取 API-KEY、Webhook Secret、RSA 私钥（含片段）。
2. **以下命令会输出明文密钥，禁止由你执行**。需要时，请客户在**自己单独打开的终端**里执行；不要在本对话里代跑，也不要让客户用 AI 工具的 `!` 或 shell 透传执行，否则输出会进入对话上下文：
   - `worldfirst app api-key info --app-id <ClientId>`（沙箱应用返回 API-KEY 与 Webhook Secret 明文）
   - `worldfirst app api-key reveal --app-id <ClientId> --webhook`（生产 API-KEY 一次性领取）
   - `worldfirst app security-info`（Developer Harness 凭证通道专用，CLI 下会被拒绝）
   - 任何会打印私钥文件或密钥类环境变量的命令（如 `cat private_key.pem`、`printenv`、`env`）
3. **私钥只留在客户本地**：不索取、不读取、不上传私钥，只处理公钥（公钥文件可以读）。客户误把私钥或密钥贴进对话时，不要复述，提醒其撤回；生产密钥已暴露时，建议在开发者中心按流程轮换。
4. **代码与文件不硬编码密钥**：生成的代码、配置、文档、进度文件中不得出现任何密钥；密钥按系分设计确定的方案（环境变量 / 外部配置文件 / KMS / 配置中心）加载；私钥文件与含密钥的配置文件加入 `.gitignore`。
5. **沙箱与生产隔离**：两套 ClientId 与凭证严格分开；生产配置节点只使用去掉 `SANDBOX_` 前缀的生产 ClientId。
6. **不编造事实**：ClientId、interfaceId、事件 ID、订阅配置 ID、caseOrderId、processId、协议版本与链接、审批状态、认证等级等，一律来自 CLI 实时返回。CLI 调用失败时如实说明原因，停在当前步骤，不得凭记忆或推测继续。

## 必须先获得客户明确同意的操作

以下操作会产生真实且难以撤销的变更。执行前用一句话复述「将要做什么 + 关键参数」，得到客户明确同意（如「确认」「可以」）后再执行；`--consent`、`--yes` 只能在获得同意后传入。

| 操作 | 命令 |
|---|---|
| 签署 API 服务协议 | `contract sign --consent` |
| 创建应用（创建后认证方式不可更改） | `app create ... --consent` |
| 变更集成接口（全量替换） | `app update-interfaces` |
| 新建、修改、删除 Webhook 订阅 | `webhooks subscribe` / `webhooks delete --yes` |
| 设置沙箱 IP 白名单（全量替换） | `app set-ip-whitelist` |
| 上传 RSA 公钥 | `app set-key --key-file` |
| 安装 Git、`git init`、创建或切换分支 | 包管理器安装命令 / `git init` / `git checkout` |
| 在客户工程中写入集成代码 | 编码节点 |
| 提交 Go-Live 审批、确认上线 | `app approval submit` / `app approval activate` |
| 提交生产公钥、生产 IP 白名单、开通子账号权限 | `app approval submit --public-key` / `app prod-config ip-whitelist set` / `app prod-config sub-account-auth enable` |

只读查询（`app overview`、`app catalog`、`webhooks events`、`app approval detail`、`acceptance cases/detail`、`logs api` 等）无需征求同意，直接执行。

## 与其它 WorldFirst skill 的关系

- 本 skill 负责全流程编排。编码节点的接口定义一律从官方文档实时获取（见 [06-coding](references/nodes/06-coding.md)）；Java 工程且已安装 `wf-api-integration-upgrade` skill 时，复用它的公共基础设施参考实现（签名、HTTP 客户端、配置、异常等）与工程适配规则。
- `wf-api-intent-recognition`、`wf-webhook-setting`、`wf-security-setting`、`wf-dev-setting`、`wf-sys-analysis`、`wf-coding`、`wf-integration-case`、`wf-release-submit`、`wf-prod-config` 是 Developer Harness 各节点专用的 skill，依赖控制台前端渲染卡片。在客户自己的 AI 对话里以本 skill 为准，不要套用它们的卡片协议。
- 只支持 V2（Enhanced，`/api/open/v1/*`）应用。V1（Basic）应用无法通过 CLI 操作，请引导客户在开发者中心处理，或新建 V2 应用。
