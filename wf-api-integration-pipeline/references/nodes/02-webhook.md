# 节点 2 · Webhook 订阅

> 对应 Harness 节点 `webhook`（skill：wf-webhook-setting）。

**目标**：为应用订阅需要的 Webhook 事件通知，支持查看、新增、修改、删除订阅。
**完成标志**：应用至少有 1 个订阅配置；或应用从未设置过订阅、客户选择「暂不设置」（记为 `SKIPPED`）。

## 产品限制

- 一个应用最多 **5** 个订阅配置：已有 5 个时不再提供新增，引导客户修改或删除已有配置。
- 一个事件只能被一个订阅配置订阅：已被其它配置订阅的事件（`occupied=true`）不得出现在任何新增或追加选项里。
- 每个订阅配置最多 **100** 个事件。

## 数据源

- 当前订阅关系：`worldfirst app overview --app-id <ClientId> --json` 的 `webhookConfigs`（`id`、`name`、`url`、`eventGroups[].events[].eventCode`）。
- 可订阅事件目录（唯一来源，无静态兜底）：`worldfirst webhooks events --app-id <ClientId> --json`，按模块分组；每个事件：

| 字段 | 用法 |
|---|---|
| `moduleName` | 英文模块名，展示时用下表转中文 |
| `funcName` / `url` | 事件编码（两者相同，如 `payouts.SUCCESS`），一律以返回原文为准（如 `FAIL`、`CANCELED` 这类拼写不得自行改写） |
| `interfaceId` | `webhooks subscribe --webhook-id` 需要的事件 ID，只能从这里取得 |
| `recommended` | 后端按应用已选方案算出的推荐事件，推荐集直接取它 |
| `occupied` | 已被本应用其它配置订阅，排除 |
| `selected` | 已被指定配置订阅（带 `--config-id` 查询时有意义） |

模块名中文对照（表中没有的模块直接用 `moduleName` 原名，不要自己编中文）：

| moduleName | 中文名 | 典型事件 |
|---|---|---|
| GlobalAccounts | 全球收款账户 | `globalAccounts.ACTIVE/FAILED` → 全球账户状态变更通知 |
| Balance | 余额管理 | `balances.CHANGED` → 余额变动通知 |
| Payouts | 全球付款 | `payouts.SUCCESS/FAILED` → 付款结果通知 |
| Beneficiaries | 收款人管理 | `beneficiaries.ACTIVE/REJECTED` → 收款人绑定结果通知 |
| Settlement | 外汇交割 | `fx.settlements.*` → 外汇交割结果通知 |
| ForwardDeal | 远期外汇 | `fx.deals.*` → 远期外汇交易状态 / 保证金变更通知 |
| ConfirmationLetter | 凭证文件 | `confirmationLetters.*` → 凭证生成完成通知 |

事件目录不含中文描述，按「模块中文名 + 状态」生成说明：SUCCESS→结果成功、FAILED/FAIL→结果失败、CANCELED/CANCELLED→已取消、PENDING→待处理、OVERDUE→已逾期、CHANGED→变动；同类事件的成功、失败可合并描述（如 `payouts.*` 统一为「付款结果通知」）。客户问到目录里没有的事件，回复「该事件暂未对你开放订阅」。

## 第 0 步：展示订阅关系

读取 `app overview`：

- **已有订阅**：用表格展示（名称、URL、事件数与事件编码），说明「共 N 个订阅配置」，然后询问：1）新增订阅（订阅数未满 5 个时才提供）　2）修改某个订阅　3）订阅已满足需要，完成本节点。客户选择完成 → 本节点 `DONE`。
- **没有订阅**：进入第 1 步。

## 第 1 步：推荐事件确认（新增流程第 1 步）

执行 `webhooks events`（一次取全量），推荐集 = `recommended=true` 且 `occupied=false` 的事件，记下每个事件的 `interfaceId` 供第 3 步复用。推荐集为空（或推荐事件都已被订阅）时跳过本步，直接进入第 2 步。

`recommended` 全部为 false 时，按已集成 API 的分类兜底推荐：账户管理→GlobalAccounts、Balance；全球付款→Payouts、Beneficiaries；外汇交易→Settlement、ForwardDeal；文件与凭证管理→ConfirmationLetter；其余分类暂无对应模块。仍无法识别时推荐全部可订阅事件。

先说：「我先根据你集成的 API 推荐应该订阅的 Webhook 事件。」然后按模块分组编号列出推荐事件（中文说明 + 事件编码），说明**默认全部选中**，客户可回复要排除的编号。选项：

1. 确认订阅这些事件，进入命名与 URL 配置
2. 继续添加其它分类的事件
3. 应用从未设置过订阅时：暂时不设置 Webhook；否则：放弃本次新增

- 选 1 → 已选事件即最终清单，进入第 3 步；客户把事件全部排除时提示「请至少选择一个事件才能继续」并重新确认。
- 选 2 → 进入第 2 步。
- 「暂时不设置」→ 本节点记为 `SKIPPED`，告知客户以后可随时回来订阅，询问是否进入安全设置。
- 「放弃本次新增」→ 确认放弃，回到第 0 步展示现有订阅。

## 第 2 步：选模块 → 选事件（新增流程第 2 步）

1. 列出所有「仍有可订阅事件」的模块（附「N 个可订阅事件」），请客户选模块；
2. 按所选模块列出全部未订阅且 `occupied=false` 的事件，**默认不选中**，每次最多 4 个模块，请客户选择；
3. 至少选 1 个后，最终清单 = 第 1 步已选（如有）+ 本步已选，进入第 3 步。

客户要「全部」时，选中所有模块的全部可订阅事件（排除已占用，不超过 100 个）。

## 第 3 步：命名与 URL，执行订阅

1. 请客户确认订阅名称与接收 URL：
   - 名称 3–30 个字符（字母、数字、中文、下划线），需在应用内唯一。建议名：单分类「WF_」+ 关键词 +「事件通知」；双分类拼接两个关键词；三个及以上「WF_」+ 主关键词 +「综合事件通知」。
   - URL 必须是有效的 http/https 地址，用于接收 WorldFirst 的 POST 通知。客户暂时没有公网地址时，可先填写计划使用的地址，联调阶段用 `worldfirst listen --app-id <ClientId> --forward-to http://localhost:<端口>` 把沙箱通知转发到本地（见 cli.md「调试工具」）。
2. 把最终事件清单换算成事件 ID：直接复用 `webhooks events` 返回的 `interfaceId`（期间发生过订阅变更、或已拿不到该结果时才重新查询）。某个事件找不到 ID 时，说明它暂不可订阅并从清单移除，**不得猜测或沿用历史 ID**。
3. 复述「名称、URL、N 个事件」，客户确认后执行：

   ```bash
   worldfirst webhooks subscribe --app-id <ClientId> --name "<名称>" --url <URL> --webhook-id <事件ID列表> --json
   ```

4. **成功**：根据返回的 `config` 更新订阅关系，用表格展示，告知「订阅成功」。本节点 `DONE`，询问客户继续新增，还是进入安全设置。
5. **失败**：解释原因（名称重复、URL 非法、事件已被占用、超出数量限制等），重新询问名称 / URL 或调整事件清单，直到成功。

## 修改订阅（客户说「修改订阅 X」或在第 0 步选择修改）

1. 从 `app overview` 定位该配置的 ID、名称、URL 与当前事件。
2. 询问要做什么：1）增加订阅事件　2）删除订阅事件　3）修改名称或 URL　4）删除整个订阅　5）放弃修改。
3. **增加事件**：执行 `webhooks events --app-id <ClientId> --config-id <配置ID> --json`，先列出「存在本配置未订阅、且未被其它配置占用的事件」的模块，再按所选模块列出可增加的事件（默认不选中）。确认后执行 `webhooks subscribe --app-id <ClientId> --config-id <配置ID> --webhook-id <当前事件ID + 新增事件ID 的全量>`。
4. **删除事件**：编号列出该配置当前订阅的事件，请客户给出要删除的编号，展示保留清单并确认，执行 `webhooks subscribe --app-id <ClientId> --config-id <配置ID> --webhook-id <保留事件ID>`。保留清单不能为空；要全部删除时改为删除整个订阅。
5. **修改名称或 URL**：展示现值，收集新值并确认后执行 `webhooks subscribe --app-id <ClientId> --config-id <配置ID> [--name "<新名称>"] [--url <新URL>]`。
6. **删除整个订阅**：明确告知删除后不再接收这些事件，客户确认后执行 `WF_CLIENT_ID=<ClientId> worldfirst webhooks delete --id <配置ID> --yes --json`。
7. **放弃修改**：确认放弃，展示原订阅关系。
8. 每次变更成功后展示最新订阅关系；失败时解释原因继续引导。

## 边界

- 客户不确定要订阅哪些事件时，简要介绍每类事件的用途。
- 客户中途改主意，允许回到之前的步骤修改选择。
- `webhooks events` / `app overview` 调用失败（网络不可达、未授权等）时，说明真实原因并建议重试；严禁凭记忆罗列事件或编造事件编码与 ID。
