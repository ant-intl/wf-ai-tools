# 节点 3 · 安全设置

> 对应 Harness 节点 `security_setting`（skill：wf-security-setting）。

**目标**：按应用认证方式完成沙箱安全设置。API_KEY：客户取得并保管 API-KEY 与 Webhook Secret，可选设置 IP 白名单，完成沙箱安全设置；RSA：上传 2048 位商户公钥，取得 WorldFirst 平台公钥。
**完成标志**：API_KEY 应用状态已离开 `SANDBOX_SETUP`（通常为 `TESTING`）；RSA 应用 `publicKeyInfoList` 中已有 `issuer=partner` 的商户公钥。

先执行 `worldfirst app overview --app-id <ClientId> --json`，读取 `appInfo.authMethod`：`API_KEY` 走 API_KEY 分支；`RSA` 或缺省走 RSA 分支。不得凭应用名或客户猜测判断。`app overview` 已脱敏，只使用其中的 `authMethod`、`state`、`apiKeyInfos[].keyUsage`、`apiKeyInfos[].ipWhiteList` 与 `publicKeyInfoList` 等元数据。

本版本**不支持密钥轮换**：客户提出时说明暂不支持，不提供任何轮换命令或指引。

## API_KEY 分支

### 第 1 步：客户自行取得 API-KEY 与 Webhook Secret

确认 `apiKeyInfos` 中同时有 `keyUsage=API` 与 `keyUsage=WEBHOOK` 两条记录（只看元数据）。然后告诉客户：

- API-KEY（`irak_` 前缀）用于请求认证（`Authorization: Bearer`），Webhook Secret（`iwsc_` 前缀）用于验证 Webhook 通知签名；
- 请在**自己单独打开的终端**执行下面的命令查看明文（不要在本对话里执行，也不要用 AI 工具的 `!` 透传），沙箱密钥可以反复查看；也可以在开发者中心查看：

  ```bash
  worldfirst app api-key info --app-id <SANDBOX_ClientId>
  ```

- 输出中 `[API]` 为 API-KEY，`[WEBHOOK]` 为 Webhook Secret。请保存到自己的密钥管理处（如本地环境变量、密钥管理服务），后续系分设计会确定正式的加载方案；不要粘贴到对话、工单或日志里。

请客户保存好后回复「已保存」（不要回复密钥本身），再进入第 2 步。你不得执行上述命令，也不得以任何方式读取密钥值。

### 第 2 步：是否设置 IP 白名单（可选）

说明：IP 白名单用于限制可调用沙箱 API 的来源 IPv4 地址，不设置也可以继续集成。展示当前白名单（来自 `apiKeyInfos[].ipWhiteList`，去重，可能为空）。选项：1）设置 IP 白名单　2）暂不设置，继续。

- 暂不设置 → 进入「完成安全设置」。
- 设置 → 第 3 步。

### 第 3 步：设置最终完整的白名单

1. 请客户给出**最终生效的完整** IPv4 清单（英文逗号分隔；最多 50 个；不支持 IPv6、CIDR 号段，不能重复）。客户是在现有名单上追加时，把「现有名单 + 新地址」合并成完整清单，不得只传新增项而覆盖旧名单。
2. 拆分、去空格并校验每一项为合法 IPv4、无重复、总数 1–50；不合规时指出原因并重新询问。
3. 复述最终清单，客户确认后执行：

   ```bash
   worldfirst app set-ip-whitelist --app-id <ClientId> --ip "<IPv4,IPv4,...>" --json
   ```

4. 返回 `updated=true` → 进入「完成安全设置」；失败 → 解释真实原因并重新询问，不得当作成功，也不得继续执行完成命令。

### 完成安全设置

无论是否设置白名单，都按以下顺序收尾：

1. `app overview` 显示 `state` 已不是 `SANDBOX_SETUP`（如 `TESTING`）时，说明此前已完成，直接视为完成。
2. 否则执行（**不得**带 `--key` 或 `--key-file`，API_KEY 应用不上传任何公钥）：

   ```bash
   worldfirst app set-key --app-id <ClientId> --json
   ```

3. 命令失败 → 解释真实错误，停留在本节点。
4. 成功后再执行 `app overview` 复查：`state=TESTING` 才算完成；复查失败或仍是其它状态，如实展示并停留在本节点。
5. 完成后告知「API-KEY 与 Webhook Secret 已就绪，IP 白名单为可选配置，后续需要时可回到本节点调整」，本节点 `DONE`，询问是否进入应用集成的工程设置。

## RSA 分支

### 已设置过商户公钥

`publicKeyInfoList` 中已有 `issuer=partner` 的公钥时：

1. 执行 `worldfirst app platform-key --app-id <ClientId> --json` 取得 WorldFirst 平台公钥（`data.publicKey`）。
2. 展示商户公钥与平台公钥（公钥不是秘密，可以展示），本节点 `DONE`。
3. 提示：如需更换商户公钥，可以说「更新公钥」，按下方第 1–3 步重新上传。该指引只适用于 RSA，不要引导 API_KEY 轮换。

### 第 1 步：说明与密钥来源

说明：需要一个 2048 位 RSA 公钥，WorldFirst 用它验证你请求的签名；私钥由你自己保管，**不要发送到本对话**。选项：1）我已有 RSA 公钥　2）请协助我生成密钥对。

### 第 2 步 A：使用已有公钥

请客户提供公钥文件路径（推荐），或直接粘贴 PEM 格式公钥。

### 第 2 步 B：生成密钥对

推荐客户在自己的终端执行（私钥全程不经过 AI）。客户明确希望由你代为执行时，可以运行同样的命令，但**只能读取公钥文件，不得查看私钥文件内容**：

```bash
mkdir -p ~/.worldfirst/keys/<SANDBOX_ClientId> && cd ~/.worldfirst/keys/<SANDBOX_ClientId>
openssl genpkey -algorithm RSA -pkeyopt rsa_keygen_bits:2048 -out private_key.pem
openssl pkey -in private_key.pem -pubout -out public_key.pem
chmod 600 private_key.pem
```

- 密钥放在工程目录之外，避免误提交；`private_key.pem` 为 PKCS#8 格式（`-----BEGIN PRIVATE KEY-----`），可直接用于后续集成代码。
- Windows 可在 Git Bash 等自带 OpenSSL 的环境中执行，或使用客户自己的密钥工具；`chmod` 改为限制文件访问权限。
- 提醒客户妥善保管 `private_key.pem`；生产环境建议另生成一对密钥（生产配置节点使用）。

### 第 3 步：校验并上传公钥

1. 校验（先只看第一行判断类型，确认是公钥后才读取全文，避免误读私钥）：
   - `head -n 1 <文件>` 为 `-----BEGIN PUBLIC KEY-----`：符合要求；
   - 为 `-----BEGIN RSA PUBLIC KEY-----`（PKCS#1）：提示转换 `openssl rsa -RSAPublicKey_in -in <文件> -pubout -out public_key.pem`；
   - 含 `PRIVATE KEY`：立即提醒「这是私钥，私钥不应提交给任何平台」，请客户改为提供公钥；
   - 可用 OpenSSL 时执行 `openssl pkey -pubin -in <公钥文件> -noout -text`，首行须为 `Public-Key: (2048 bit)`；没有 OpenSSL 时以服务端校验结果为准。
2. 复述「为应用 <ClientId> 上传该公钥」，客户确认后执行：

   ```bash
   worldfirst app set-key --app-id <ClientId> --key-file <公钥文件路径> --json
   ```

   客户粘贴的是 PEM 文本时改用 `--key "<PEM 全文>"`。失败时解释原因并请客户重新提供公钥。
3. 成功后执行 `app platform-key` 取得 WorldFirst 平台公钥并展示。建议客户把平台公钥保存为文件（如 `~/.worldfirst/keys/<SANDBOX_ClientId>/wf_public_key.pem`，客户同意后可由你写入），集成代码用它验证响应与通知签名。
4. `app overview` 复查已有 `issuer=partner` 的公钥 → 本节点 `DONE`，询问是否进入应用集成的工程设置。

## 边界

- 认证方式缺失：先执行 `app overview` 补齐；仍无法识别时说明查询失败，不得默认创建或修改任何密钥。
- 客户把私钥或 API-KEY、Webhook Secret 贴进对话：不要复述，提醒其撤回；该值已进入对话记录，若是生产密钥建议在开发者中心轮换。
- API_KEY 客户想跳过：IP 白名单可以跳过，但「完成安全设置」必须执行。
- RSA 客户想跳过：RSA 公钥是 API 通信安全的必要步骤，完成后才能进入应用集成。
