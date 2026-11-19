# 本地进度文件与续跑

Harness 把流水线进度存在控制台本地数据库里；本 skill 改为存放在客户工作区的进度文件中，并在每次续跑时用服务端事实校准。

## 位置与写入规则

- 路径：`<工作区根>/.worldfirst/pipeline-state.json`。工作区根默认是当前会话所在的目录；该目录明显不是客户工程（如用户主目录、系统目录）时，先请客户给出工程目录。首次创建时告诉客户进度文件的位置。
- 首次创建 `.worldfirst/` 时，如果工作区是 Git 仓库，把一行 `.worldfirst/` 追加到 `.git/info/exclude`（仅本地生效，不改动 `.gitignore`，也不会产生待提交变更）。
- 只存服务端查不到的本地事实与客户决策；**不存任何密钥、私钥、公钥正文或完整 CLI 输出**。
- 每完成一个节点、或客户做出影响后续步骤的决策时立即写入；写入时读取 → 修改 → 整体写回，保持 JSON 合法，并更新 `updatedAt`。

## 结构

```json
{
  "schemaVersion": 1,
  "skill": "wf-api-integration-pipeline",
  "updatedAt": "2026-11-19T10:00:00+08:00",
  "activeClientId": "SANDBOX_XXXXXXXX",
  "draft": null,
  "apps": {
    "SANDBOX_XXXXXXXX": {
      "appName": "WF_全球付款集成",
      "authMethod": "API_KEY",
      "currentNode": "dev_settings",
      "nodes": {
        "basic_info": { "status": "DONE", "at": "2026-11-19T09:00:00+08:00" },
        "webhook": { "status": "DONE", "at": "2026-11-19T09:10:00+08:00" },
        "security_setting": { "status": "DONE", "at": "2026-11-19T09:20:00+08:00" },
        "dev_settings": { "status": "IN_PROGRESS" },
        "sys_analysis": { "status": "INIT" },
        "coding": { "status": "INIT" },
        "integration_case": { "status": "INIT" },
        "case_execution": { "status": "INIT" },
        "release_submit": { "status": "INIT" },
        "prod_config": { "status": "INIT" }
      },
      "integration": {
        "mode": "code",
        "projectDir": "/Users/me/projects/my-app",
        "git": { "used": true, "branch": "worldfirst-integration" },
        "environment": { "language": "Java 17", "buildTool": "Maven", "framework": "Spring Boot 3.2" },
        "keyManagement": { "scheme": "env", "depth": null, "provider": null, "params": { "apiKeyEnv": "WORLDFIRST_API_KEY", "webhookSecretEnv": "WORLDFIRST_WEBHOOK_SECRET" } },
        "sysAnalysisDoc": "docs/wf-system-analysis.md",
        "files": [{ "path": "src/main/java/com/example/wf/WfApiClient.java", "changeType": "added" }]
      },
      "acceptance": { "planFile": ".worldfirst/acceptance-plan.json", "confirmedAt": null },
      "release": { "processId": null, "submittedAt": null },
      "prodConfig": { "productionClientId": null, "publicKeyProcessId": null, "ipWhitelistProcessId": null, "subAccountAuth": null }
    }
  }
}
```

| 字段 | 说明 |
|---|---|
| `activeClientId` | 当前推进的应用（沙箱 ClientId）；创建应用前为 `null` |
| `draft` | 创建应用前的在建选择，如 `{"step":"naming","categories":[...],"interfaceIds":[18000075],"appName":"WF_x","authMethod":null}`；应用创建成功后置为 `null` |
| `apps.<id>.nodes.<node>.status` | `INIT`（未开始）/ `IN_PROGRESS`（进行中）/ `DONE`（完成）/ `SKIPPED`（按规则跳过） |
| `integration.mode` | `code`（帮客户写代码）/ `skip`（跳过应用集成） |
| `integration.keyManagement` | 系分设计确定的密钥管理方案，只存方案、提供方与变量名 / 配置项名 / keyId 等元信息 |
| `integration.files` | 编码节点新增或修改的文件（相对工程根目录） |
| `acceptance.planFile` | 集成用例方案文件，结构见 [07-integration-case](nodes/07-integration-case.md#方案文件) |
| `release.processId` / `prodConfig.*ProcessId` | 审批流程 ID，用于续跑时精确查询审批状态 |

## 续跑推导（服务端事实优先）

1. 有 `activeClientId` 时执行 `worldfirst app overview --app-id <ClientId> --json` 刷新事实；应用不存在或无权限时如实告知，询问客户改用其它应用还是新建。
2. 按下表校准各节点状态。服务端能证明的以服务端为准；本地记录与服务端不一致时以服务端为准，并告诉客户发生了什么变化（如「服务端集成接口比上次多了 2 个」「订阅 X 已被删除」）。

   | 节点 | 依据 |
   |---|---|
   | `basic_info` | 应用存在且 `appVersion` 为 V2 |
   | `webhook` | `webhookConfigs` 非空即完成；为空且本地记为 `SKIPPED` 则保持 |
   | `security_setting` | API_KEY：`state` 已离开 `SANDBOX_SETUP`；RSA：`publicKeyInfoList` 含 `issuer=partner` |
   | `dev_settings` / `sys_analysis` / `coding` | 只有本地记录；工程目录或系分文档已不存在时提醒客户 |
   | `integration_case` | 方案文件存在，且其 `caseOrderId` 集合与 `acceptance cases` 返回一致；不一致需重新规划 |
   | `case_execution` | `acceptance cases` 返回的用例全部 `status=FINISH` |
   | `release_submit` | `state` 为 `LIVE` / `LIVE_EDITING` / `EDIT_TESTING` / `EDIT_GO_LIVE_SUBMITTED` 即完成；`GO_LIVE_SUBMITTED` 为进行中，先查审批 |
   | `prod_config` | 只有本地记录 |

3. 当前节点 = 按顺序第一个状态不是 `DONE` / `SKIPPED` 的节点。
4. 没有进度文件、但客户要继续一个已有应用（例如之前在 Harness 或开发者中心创建）：同样按上表从服务端推导。第 4–6 节点只有本地记录，询问客户是否已经完成集成代码：已有代码可从工程设置开始检查工程，或选择「跳过应用集成」直接进入用例规划。
5. 存在 `draft` 而 `activeClientId` 为空（应用还没创建成功）：询问客户继续在建流程还是放弃重来；继续时从 `draft.step` 对应的步骤接着引导（签约状态每次都要重新查询）。

## 应用状态参考

| state | 含义 | 所处阶段 |
|---|---|---|
| `SANDBOX_SETUP` | 沙箱配置中 | 应用创建 |
| `TESTING` | 测试中（安全设置完成后） | 应用集成、集成测试 |
| `GO_LIVE_SUBMITTED` | 上线审批中 | 提交发布 |
| `LIVE` | 已上线 | 生产配置 |
| `LIVE_EDITING` / `EDIT_TESTING` / `EDIT_GO_LIVE_SUBMITTED` | 上线后的编辑流程 | 已上线应用的变更 |
| `OFFLINE` / `DELETING` | 已下线 / 删除审批中 | 不再推进流水线 |
