# 节点 10 · 生产配置

> 对应 Harness 节点 `prod_config`（skill：wf-prod-config）。

**目标**：应用上线后完成生产配置：展示生产环境信息；RSA 应用设置生产公钥，API_KEY 应用按需设置生产 IP 白名单；检测子账号并按需开通子账号访问权限。
**完成标志**：上述配置生效（或客户选择跳过可选项），并输出最终的生产接入信息。

**生产 ClientId**：本节点所有命令的 `--app-id` 一律使用 `<ProdClientId>`，即沙箱 ClientId 去掉 `SANDBOX_` 前缀；不得再传沙箱 ClientId。写入进度文件 `prodConfig.productionClientId`。

## 第 0 步：前置校验与分流

1. `worldfirst app detail --app-id <ProdClientId> --json`：
   - 报「本地应用目录未同步」类错误（生产应用在上线时才授权给 CLI 登录）：按 cli.md 执行 `worldfirst login --json` 静默同步后重试；
   - `state` 不是 `LIVE`：告诉客户「你的应用尚未上线（当前状态：<state>），请先完成提交发布」，停止本节点；
   - `state=LIVE`：读取 `authMethod` 分流——API_KEY：第 1 步 → 第 2B 步 → 第 3 步；RSA：第 1 步 → 第 2 步 → 第 3 步。
2. **API_KEY 应用铁律**：用 API-KEY 鉴权，没有 RSA 签名，也就没有需要上传的公钥。不得引导上传任何 RSA 公钥，不得调用 `app approval submit --public-key`，不得查询 `EDIT_PUBLIC_KEY` 审批；`prod-info` 返回的 `wfPublicKey` 为空，展示时省略该项；生产密钥已在提交发布节点领取，本节点不再涉及。
3. **RSA 应用恢复探测**：执行 `worldfirst app approval detail --app-id <ProdClientId> --action-type EDIT_PUBLIC_KEY --json`：
   - `PENDING` / `INREVIEW`：公钥审批进行中，按第 2 步的方式展示审批进度；
   - `APPROVED`：公钥已生效，跳过第 1、2 步，进入第 3 步；
   - 返回空：进入第 1 步。

## 第 1 步：生产环境信息

执行 `worldfirst app prod-config prod-info --app-id <ProdClientId> --json`（严禁臆测），先说「恭喜你，应用已经发布到生产了！生产环境信息如下」，再展示：生产 ClientId（`appId`）、访问地址（`gatewayUrl`）、WorldFirst 生产环境公钥（`wfPublicKey`，仅 RSA 应用展示）。提醒客户：生产环境使用独立的 ClientId、凭证与网关配置，不要沿用沙箱值。

客户确认继续后：

- API_KEY：说明「你的应用使用 API-KEY 鉴权，无需设置 RSA 公钥，接下来配置生产环境的 IP 白名单」，进入第 2B 步。
- RSA：说明「接下来需要设置生产环境公钥。WorldFirst 用它验证你请求的签名；建议生产环境使用与沙箱不同的密钥对；私钥请妥善保管，不要发送到本对话」，进入第 2 步。

## 第 2 步：提交生产公钥（仅 RSA）

1. 请客户提供生产公钥文件路径（推荐）或粘贴 PEM。需要新密钥对时，按 [03-security-setting](03-security-setting.md) 的生成方式在单独目录（如 `~/.worldfirst/keys/<ProdClientId>/`）生成，校验方式也相同（先看第一行、禁止私钥、2048 位）。
2. 复述「为生产应用 <ProdClientId> 提交该公钥」，客户确认后执行（PowerShell 改用 `(Get-Content -Raw .\public_key.pem)`）：

   ```bash
   worldfirst app approval submit --app-id <ProdClientId> --public-key "$(cat <公钥文件路径>)" --reason "设置生产环境公钥" --json
   ```

3. 按顺序判定结果：
   - CLI 报错且错误原文含 `IPAY_RS_510620530`（`PROCESS_NOT_EXIST`）：公钥已直接生效、无需审批（开发经理或主账号提交时如此）。告知「生产公钥已直接生效，无需审批」，进入第 3 步；**不得**再次提交、不得查询或展示审批进度；
   - 其它错误：解读原文，请客户重新提供公钥后重试；
   - 成功：`approvalRequired=false` 或 `processId` 为空同样表示直接生效，进入第 3 步；`approvalRequired=true` 且有 `processId` 时写入 `prodConfig.publicKeyProcessId`，执行 `worldfirst app approval detail --app-id <ProdClientId> --action-type EDIT_PUBLIC_KEY --process-id <processId> --json`。
4. 需要审批时：
   - `PENDING` / `INREVIEW`：展示审批进度（状态中文：`PENDING`→待开发经理审批、`INREVIEW`→审批中、`APPROVED`→已通过、`MANAGER_REJECTED`→开发经理已拒绝、`SA_REJECTED`→SA 已拒绝；以及应用名、提交人、提交时间），说明「开发人员提交需开发经理审批通过后生效；开发经理或主账号提交则直接生效」。客户可随时说「检查审批状态」，你重新查询；
   - `APPROVED`：告知「生产公钥审批已通过，公钥已生效」，进入第 3 步；
   - `MANAGER_REJECTED` / `SA_REJECTED`：读取拒绝原因并说明，请客户修正后重新提交公钥。

直接生效与审批通过是两条不同路径：前者看提交结果中的 `IPAY_RS_510620530`（或 `approvalRequired=false`），后者看审批详情的 `processStatus=APPROVED`，不要混用，也不要仅凭提交成功就展示审批进度。

## 第 2B 步：生产 IP 白名单（仅 API_KEY）

**沙箱与生产的 IP 白名单完全独立**，沙箱配置过不代表生产已配置。规则与沙箱一致：最多 50 个，只能是单个 IPv4 地址，不支持号段（CIDR）。

1. 先读取现状：`worldfirst app prod-config ip-whitelist list --app-id <ProdClientId> --json`。`pendingApproval=true` 时已有变更在审批中：展示审批进度并等待，不得重复提交，也不再询问。
2. **先问意愿**，不要直接让客户填 IP。说明「生产环境的 IP 白名单与沙箱独立配置；设置后只有名单内的公网 IP 才能调用生产接口，不设置则不限制来源 IP」，并展示当前名单（可能为空）。询问：1）要设置　2）暂不设置。
   - 暂不设置：告知「已跳过生产 IP 白名单，当前不做来源 IP 限制，后续需要时可回到本节点设置」，进入第 3 步；**不要**为了「清空」而提交一次变更。
3. 要设置：请客户给出最终生效的完整 IPv4 清单（英文逗号分隔），校验格式、去重、数量 ≤ 50；客户最终决定不限制 IP 时，明确告知风险后可传空清单 `--ip ""` 清空名单（这与上一步的「暂不设置」不同）。不要替客户编造 IP，校验失败时原样解读服务端错误并重新收集，不得自行截断列表。
4. 复述最终清单（全量替换：未包含的地址会被删除），客户确认后执行：

   ```bash
   worldfirst app prod-config ip-whitelist set --app-id <ProdClientId> --ip "<IPv4,IPv4,...>" --reason "设置生产环境 IP 白名单" --json
   ```

5. 判定结果：错误原文含 `IPAY_RS_510620530` → 已直接生效，进入第 3 步；返回 `processId` → 写入 `prodConfig.ipWhitelistProcessId`，执行 `worldfirst app approval detail --app-id <ProdClientId> --action-type EDIT_IP_WHITELIST --process-id <processId> --json`：`PENDING` / `INREVIEW` 展示审批进度并说明「开发人员提交需开发经理审批，开发经理或主账号提交直接生效」；`APPROVED` 告知「生产 IP 白名单已生效」，进入第 3 步。

## 第 3 步：子账号访问权限

1. `worldfirst app prod-config sub-accounts --app-id <ProdClientId> --json`。
2. `hasSubAccounts=false`：进入「完成」。
3. `hasSubAccounts=true`：说明「检测到你在 WorldFirst 还有子账号；开通后，请求头带上 `account-id: <子账号 ID>` 即可访问对应子账号的信息」，询问：1）允许开通　2）暂时不配置。
   - 允许：执行 `worldfirst app prod-config sub-account-auth enable --app-id <ProdClientId> --json`（幂等）；成功后进入「完成」，失败时解读原因并重新询问；
   - 暂不配置：告知「已跳过子账号访问配置，后续需要时可以告诉我：开通子账号权限」，进入「完成」。
4. 开通后如需关闭：当前不支持，请客户联系客户经理处理。

## 完成

1. 重新执行 `worldfirst app prod-config prod-info --app-id <ProdClientId> --json`，以最新返回展示最终的生产 ClientId 与访问地址（禁止复用旧值）。
2. 告诉客户：「恭喜你，一切都设置妥当了，你的应用已经可以在生产环境使用了。」并提醒：生产部署使用生产 ClientId、生产网关与生产凭证（API_KEY 应用为生产 API-KEY 与 Webhook Secret；RSA 应用为生产私钥与生产环境 WorldFirst 公钥），按系分设计的密钥管理方案注入，不要混用沙箱凭证。
3. 本节点 `DONE`，整条流水线完成。之后客户再提出生产信息查询、更换生产公钥（RSA，会创建新的 `EDIT_PUBLIC_KEY` 审批）、调整生产 IP 白名单（API_KEY）、开通子账号权限等需求时，按本节点对应步骤处理。

## 边界

- 应用未上线：引导回提交发布节点，不在本节点代做发布流程。
- 客户粘贴的内容含 `-----BEGIN PRIVATE KEY-----` 或 `-----BEGIN RSA PRIVATE KEY-----`：立即提醒「你粘贴的是私钥，私钥不应提交给任何平台」，不要复述，请客户改为提供公钥。
- 公钥明显不完整（缺少 BEGIN / END 行）：提示格式不正确，请客户重新提供完整的 PEM 公钥。
- API_KEY 应用的客户主动要求设置公钥：说明该应用使用 API-KEY 鉴权，不需要 RSA 公钥；确实要改用 RSA 鉴权时，引导其新建 RSA 应用。
