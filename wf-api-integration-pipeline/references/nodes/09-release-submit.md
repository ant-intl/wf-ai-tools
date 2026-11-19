# 节点 9 · 提交发布

> 对应 Harness 节点 `release_submit`（skill：wf-release-submit）。

**目标**：校验 KYB 认证等级 → 确认应用信息并提交 Go-Live 审批 → 跟进审批 → 审批通过后确认上线。
**完成标志**：审批状态为 `APPROVED` 且 `app detail` 返回的 `state` 为 `LIVE`，两者同时成立。

## 第 0 步：KYB 认证门禁（进入本节点必做，先于其它动作）

执行 `worldfirst whoami --verify --json`，读取 `data.server_biz_certification_level`：

| 取值 | 处理 |
|---|---|
| `L3` | 通过，继续第 1 步 |
| `L0` / `L1` | 未达标，引导认证后停止本节点 |
| 缺失或空值 | 视为未知，**不得当作已认证**；说明暂时无法确认认证等级（可能是上游查询暂不可用），引导认证或稍后重试 |

未达 L3 时告诉客户：「提交发布需要商户完成 KYB 企业认证（等级 L3）。你当前的认证等级是 <实际等级或"未知">，请先完成认证再回来提交发布。」认证入口：https://portal.worldfirst.com.cn/onboard/desicion 。客户说已完成认证时重新执行 `whoami --verify` 复查；仍不是 L3 就如实告知并再次给出入口。认证只能由客户本人完成，你不得代为操作或编造认证结果。

## 第 1 步：查询现状（续跑与首次进入都执行）

1. `worldfirst app detail --app-id <ClientId> --json`：`state` 已是 `LIVE` → 直接按第 5 步双重校验后完成本节点。
2. `worldfirst app approval detail --app-id <ClientId> --json`（默认查询最新的 GO_LIVE 审批；进度文件有 `release.processId` 时加 `--process-id`）：
   - 返回空：还没有提交过审批，进入第 2 步；
   - `APPROVED`：进入第 5 步；
   - `PENDING` / `INREVIEW`：按第 3 步的格式展示审批进度；
   - `MANAGER_REJECTED` / `SA_REJECTED`：按第 4 步展示拒绝原因。

## 第 2 步：确认发布

1. 前置检查：`state` 必须是 `TESTING`；`acceptance cases` 的必过用例必须全部 `FINISH`。不满足时说明原因并引导回对应节点（服务端也会拒绝提交）。
2. 说明要点：即将提交 Go-Live 审批，审批通过并确认上线后应用进入生产环境；审批包含客户经理审批与 SA（解决方案架构师）审批两个环节；审批期间应用状态为 `GO_LIVE_SUBMITTED`，无法进行其它变更。
3. 用 `app overview` 展示：应用名、ClientId、状态、已集成 API 数、Webhook 订阅数。询问：1）确认提交发布　2）取消。
4. 客户取消：回复「已取消发布操作，改变主意时随时告诉我：提交发布」，等待下一步指令。

## 第 3 步：提交审批

客户确认后执行：

```bash
worldfirst app approval submit --app-id <ClientId> --action-type GO_LIVE --reason "客户确认发布" --json
```

1. 成功拿到 `processId` 后写入进度文件 `release.processId`、`release.submittedAt`，再执行 `worldfirst app approval detail --app-id <ClientId> --process-id <processId> --json`。
2. 展示审批进度：状态（中文）、应用名、提交人（`fromName`）、提交时间（`createdTime`）。状态中文：`PENDING`→待客户经理审批、`INREVIEW`→SA 审批中、`APPROVED`→已通过、`MANAGER_REJECTED`→客户经理已拒绝、`SA_REJECTED`→SA 已拒绝。
3. 告诉客户：审批需要人工处理，可能需要一段时间；可以随时说「检查审批状态」让你重新查询，下次打开会话时你也会先检查；审批通过后你会引导确认上线。宿主支持定时或后台任务、且客户希望自动跟进时，可以每 5 分钟查询一次；否则不要在前台循环等待。
4. 提交失败时不展示审批进度，先解读错误原文：应用状态不是 `TESTING`（例如已经是 `GO_LIVE_SUBMITTED` 或 `LIVE`）、验收用例未全部通过等，引导客户处理后重试。

## 第 4 步：跟进审批

客户要求检查、或续跑时，执行 `app approval detail --app-id <ClientId> --process-id <processId> --json`：

- `PENDING` / `INREVIEW`：告知当前进度，继续等待。
- `MANAGER_REJECTED` / `SA_REJECTED`：从详情中读取拒绝环节与理由（`executeNodes` 中的 `reason` / `remark`），向客户说明；引导客户按原因修正（如补充验收、修改配置），修正后回到第 2 步再次确认提交。
- `APPROVED`：进入第 5 步。

审批提交后客户无法自行撤回；需要撤回时请客户联系客户经理或 SA。

## 第 5 步：确认上线与完成校验

完成本节点的唯一标准是两条**同时成立**：`app approval detail` 的 `processStatus=APPROVED`，且 `app detail` 的 `state=LIVE`。按顺序执行：

1. `worldfirst app detail --app-id <ClientId> --json` 查看 `state` 与 `authMethod`。
2. 已是 `LIVE`：不重复激活，直接完成本节点。
3. 是 `GO_LIVE_SUBMITTED` / `EDIT_GO_LIVE_SUBMITTED`：
   - **API_KEY 应用必须先领取生产 API-KEY**（见下节），客户确认已保存后才能激活；
   - RSA 应用可直接激活。
   - 告知客户「审批已通过，现在确认上线」，客户确认后执行：

     ```bash
     worldfirst app approval activate --app-id <ClientId> --process-id <processId> --json
     ```

   - 返回 `activated=true` 后再次执行 `app detail` 复查，`state=LIVE` 才算完成。
4. `activate` 失败、返回 `activated=false`、或复查仍不是 `LIVE`：解读错误并停留在本节点，稍后再检查；其它状态不得盲目调用 `activate`。返回 `PRODUCTION_API_KEY_NOT_COLLECTED` 说明生产 API-KEY 还没领取：引导客户领取，不要反复重试 `activate`。
5. 完成后本节点 `DONE`：告诉客户应用已上线到生产环境，下一步进行生产配置，询问是否继续。

### 生产密钥领取（API_KEY 应用上线前必做）

生产 API-KEY 在审批通过时由服务端生成，**明文只能领取一次**，之后永久脱敏；生产 Webhook Secret 没有次数限制。未领取时服务端会拒绝 `activate`，这是硬性门禁。

请客户在**自己单独打开的终端**执行（不要在本对话里执行，也不要用 AI 工具的 `!` 透传），终端提示时输入 `y` 确认：

```bash
worldfirst app api-key reveal --app-id <ClientId> --webhook
```

- 输出中 `[API]` 为生产 API-KEY，只显示这一次，请立即保存到生产环境的密钥管理处；`[WEBHOOK]` 为生产 Webhook Secret，之后可用 `worldfirst app api-key info --app-id <ProdClientId> --webhook` 再次查看。
- 请客户保存好后回复「已保存」（不要回复密钥本身），你再执行激活。

你不得执行 `app api-key reveal` / `app api-key info`，也不得以任何方式读取密钥值。

## 边界

- 客户说生产 API-KEY 没保存或丢了：明确告知明文只有一次机会、无法再次获取，需要在开发者中心走密钥轮换流程重新生成；不要承诺能再查一次。
- 客户说生产 Webhook Secret 丢了：用 `app api-key info --app-id <ProdClientId> --webhook` 即可再次查看（请客户在自己的终端执行），无需轮换。
- 客户询问审批进度：执行 `app approval detail` 并按第 3 步的格式展示。
- CLI 调用失败：解读错误原文，引导客户检查登录状态（`worldfirst whoami`）或稍后重试。
