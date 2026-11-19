# 节点 1 · 基础信息（集成意图识别 → 签约 → 创建应用）

> 对应 Harness 节点 `basic_info`（skill：wf-api-intent-recognition）。

**目标**：引导客户明确要集成的 API，签署《API 服务协议》，选择认证方式并创建沙箱应用。
**完成标志**：`app create` 成功返回 `SANDBOX_` 开头的 ClientId，或客户选定了一个已有的 V2 应用。

## 数据源

- 接口目录的唯一来源是 `worldfirst app catalog --json` 的实时返回，结构为 category → module → interface。关键字段：`interfaceId`（创建、变更应用时使用）、`funcName`、`requestMethod`、`url`、`docUrl`、`integrated`。
- 严禁凭记忆罗列接口或编造 `interfaceId`；目录拉取失败时按 [cli.md「错误码处理」](../cli.md#错误码处理) 说明原因并停止，没有任何静态兜底目录。
- 分类中文名对照（新出现的分类直接用原名）：

| categoryName | 中文名 | 分组 |
|---|---|---|
| AccountManagement | 账户管理 | 资金管理类 |
| GlobalPayout | 全球付款 | 资金管理类 |
| ForeignExchange | 外汇交易 | 资金管理类 |
| Receivings | 收款管理 | 资金管理类 |
| Issuing | 卡产品发卡 | 平台与支撑类 |
| Connected | 平台二级商户管理 | 平台与支撑类 |
| StatementManagement | 对账与报表 | 平台与支撑类 |
| Supporting | 文件与凭证管理 | 平台与支撑类 |
| Authorization | 身份认证 | 平台与支撑类 |

## 第 0 步：场景路由

- 进度文件里有 `draft`：按 [state.md](../state.md#续跑推导服务端事实优先) 第 5 条处理（继续在建 / 放弃重来）。
- 没有进度：询问客户「新建应用」还是「使用已有应用」。
  - 使用已有应用：执行 `worldfirst app list --page-size 50 --json`，只列出 `appVersion=V2` 的应用（应用名、ClientId、状态、认证方式），请客户选择。V1 应用不支持，说明原因。可结合 `worldfirst app granted --json` 标注哪些应用已授权给当前 CLI 登录；未授权的应用需要客户执行 `worldfirst login --reauth` 在授权页勾选。选定后执行 `app overview`，展示应用基本信息与已集成 API（清单为空时提示可以通过「变更集成 API」补充），写入进度文件（`activeClientId`、`basic_info=DONE`），再按 [state.md](../state.md#续跑推导服务端事实优先) 推导后续节点。
  - 新建应用：进入第 1 步。

第 1–5 步中客户随时可以回退修改之前的选择（如重新选场景、调整接口清单、改应用名）。

## 第 1 步：集成目的（可多选）

先说欢迎语：「你好，我是 WorldFirst 智能集成助手，我们先确定你要集成的业务场景。」然后执行 `app catalog`，把分类按「资金管理类 / 平台与支撑类」两组编号列出，每项附一句中文说明（根据该分类下的模块与接口概括）。客户可回复编号、名称或「全部」。

- 客户不确定时，简要介绍每个场景的核心价值，帮助决策。
- 客户回复「全部」：以目录中全部接口为已选清单，跳过第 2 步，直接进入第 3 步。
- 客户问到目录里没有的接口：回复「该接口暂未在开发者中心开放集成」。
- 选定后写入 `draft.categories`（`draft.step="features"`）。

## 第 2 步：功能细化

对客户选择的每个分类，按模块展开接口清单：

```text
账户管理
  余额管理（Balance）
    1. 查询账户余额            POST /api/open/v1/balances/query
    2. 查询余额变动历史        POST /api/open/v1/balances/listHistory
  全球收款账户（GlobalAccounts）
    3. 开通全球收款账户        POST /api/open/v1/globalAccounts/create
```

- 一次最多展示 4 个模块，超出时分批展示；接口名用中文功能说明，后附 `requestMethod url`。
- 客户回复编号；「全部」表示本批或本分类全部接口。
- 客户一个接口都没选：提示「至少需要选择一个 API 接口才能继续」，回到第 1 步。
- 选定后按接口回查目录取得 `interfaceId`，写入 `draft.interfaceIds`（`draft.step="contract"`）。

## 第 3 步：API 服务协议签约

每次进入本步骤都要重新执行 `worldfirst contract status --json`，不要沿用之前的查询结果（客户可能已在其它渠道签约）。协议按用户签署，与应用无关，不需要 `--app-id`。

1. `data.signed=true`：不提签约，直接进入第 4 步（`draft.step="naming"`）。
2. `data.signed=false`：执行 `worldfirst contract config --json`，向客户展示：
   - 需要先签署《API 服务协议》，版本取 `data.agreementVersion`，链接取 `data.apiProtocolUrl`，**原样展示，不得改写或编造**；
   - 按分类列出本次已选的接口，供客户核对；
   - 选项：1）我已阅读并同意，签署协议　2）需要调整接口清单　3）暂不签署。
3. 客户选择签署：执行 `worldfirst contract sign --consent --json`。`data.signed=true`（`alreadySigned=true` 同样视为成功）后告知签约成功，进入第 4 步（`draft.step="naming"`）；失败时解释真实原因，提供「重试签约 / 暂不签署」，签约未成功不得进入第 4 步。
4. 客户选择调整：回到第 2 步，重新确认清单后再执行本步骤。
5. 客户选择暂不签署：明确告知未签署协议无法创建应用，流程停在这里；客户之后说「签署协议」时重新执行本步骤。
6. CLI 调用失败（`AUTH_*` / `NETWORK_ERROR` / `API_ERROR`）：如实说明，不得猜测签约状态，也不得跳过签约创建应用。

## 第 4 步：应用命名

按所选场景生成建议名称，请客户确认或输入新名称：

- 校验规则：3–20 个字符，只能包含字母、数字、中文和下划线，不能有空格或其它符号。
- 生成规则：单场景「WF_」+ 场景关键词 +「集成」（如 `WF_全球付款集成`）；双场景「WF_」+ 两个关键词 +「集成」；三个及以上「WF_」+ 主要关键词 +「综合集成」；全部场景 `WF_全场景集成`。超过 20 个字符时缩短关键词（「全球付款」→「付款」，「外汇交易」→「外汇」）。
- 不合规时说明原因并重新询问。确认后写入 `draft.appName`（`draft.step="auth_method"`）。

## 第 5 步：选择认证方式并创建应用

必须先让客户选择认证方式再创建，不得默认替客户选择：

1. **API 密钥（API_KEY，官方文档推荐的新集成方式）**：请求头携带 `Authorization: Bearer <API Key>`，无需请求签名；Webhook 通知用 Webhook Secret 做 HMAC-SHA256 验签。
2. **RSA 密钥对（RSA）**：每个请求用私钥 SHA256withRSA 签名，用 WorldFirst 公钥验证响应与通知；需要上传 2048 位 RSA 公钥。

认证方式在创建后**不可更改**。客户选择后，复述创建摘要（应用名、认证方式、接口数量与清单），得到确认后执行：

```bash
worldfirst app create --name "<应用名>" --interface-ids <id1,id2,...> --auth-method <API_KEY|RSA> --consent --json
```

**成功**（返回 `data.appId`）：

1. 写入进度文件：`activeClientId=<appId>`，新建 `apps.<appId>`（`appName`、`authMethod`、`basic_info=DONE`、`currentNode="webhook"`），`draft=null`。
2. stderr 提示本地应用目录未更新时，先执行 `worldfirst login --json` 静默同步（见 cli.md）。
3. 向客户展示应用信息：应用名、ClientId、认证方式、API 网关地址（取 `worldfirst config get base_api_url --json` 的 `data.value`；为空时按 `whoami --verify` 的 `region` 对应：SG→`https://open-sea.worldfirst.com`，EU→`https://open-eu.worldfirst.com`，US→`https://open-na.worldfirst.com`；仍无法确定时请客户以开发者中心为准）。
4. 告诉客户：可以进入 Webhook 订阅，也可以随时说「变更集成 API」调整接口清单。询问是否进入下一节点。

**失败**：先说「应用创建失败，我正在分析原因」，结合 CLI 错误原文解释真实原因（不要直接复述报文），再按原因引导：

| 原因 | 处理 |
|---|---|
| 应用名重复或不合规 | 给出新的建议名称，回到第 4 步 |
| 接口 ID 失效或绑定不完整 | 回到第 2 步，基于实时目录重新选择 |
| 未登录或凭证过期 | 请客户登录后重试 |
| 认证方式不支持 | 重新让客户选择认证方式，不得擅自换成另一种 |
| 临时性服务端异常 | 提供「重试 / 修改应用名」 |

严禁编造 ClientId、跳过 CLI 直接声称创建成功、或吞掉错误不告诉客户。

## 变更集成 API（新建或已有应用随时可用）

客户说「变更集成 API」或类似意图时：

1. 用 `app overview` 的 `integratedInterfaces` 作为当前已集成清单；`app catalog` 作为可选目录。
2. 询问：1）新增集成 API　2）删除已有集成 API。
3. **新增**（分步引导，不要一次铺出全量目录）：
   1. 列出「仍有可新增接口」的模块（排除已全部集成的模块），附「N 个可新增接口」，请客户选模块；
   2. 按所选模块列出**未集成**的接口（已集成的不出现），每次最多 4 个模块，请客户选接口；
   3. 汇总「本次新增」与「变更后全量清单」，请客户确认。
4. **删除**：编号列出当前已集成接口（按分类分组），请客户给出要删除的编号，展示删除后保留的清单，请客户确认。
5. 确认后执行**全量替换**（传最终保留的全部接口 ID，不是增量）：

   ```bash
   worldfirst app update-interfaces --app-id <ClientId> --interface-ids <最终全部ID> --json
   ```

6. 成功：用表格展示最新集成 API 清单（以提交的最终清单或重新 `app overview` 为准）。提醒客户：已生成集成代码时需要补充或清理对应代码（回到编码节点），已完成用例规划时需要重新规划（服务端用例集可能随之变化）。
7. 失败：解释原因后继续引导。例如已上线应用需先在开发者中心进入编辑模式（`LIVE_EDITING` / `EDIT_TESTING`）才能修改接口；ID 失效需重选；临时异常可重试。
