# worldfirst CLI 使用约定与命令速查

## 调用约定

- 所有命令都加 `--json`，解析 stdout 的统一信封：成功为 `{"ok":true,"data":...}`，失败为 `{"ok":false,"error":{"code":"...","message":"..."}}`。按 `error.code` 决策，不要匹配 message 文案；过程提示走 stderr，不影响解析。
- 应用级命令每次显式指定应用：
  - 大多数命令用 `--app-id <ClientId>`；
  - `acceptance` 系列用 `--client-id <ClientId>`；
  - `webhooks delete` 没有这两个参数，用只作用于本条命令的环境变量：`WF_CLIENT_ID=<ClientId> worldfirst webhooks delete ...`（PowerShell：先 `$env:WF_CLIENT_ID='<ClientId>'`，执行完 `Remove-Item Env:WF_CLIENT_ID`）。
- 不要执行 `worldfirst app use`：它会改写客户终端里全局选中的应用；本 skill 一律显式传 ID。
- 不要传 `--base-url`：服务地址由 CLI 配置统一管理，传入会被持久化改写。
- `app overview`、`app catalog`、`webhooks events`、`acceptance cases` 的输出较长，一次取全量，不要用 `head` / `tail` 截断。
- 直接按本文件的模板调用；只有报 `unknown command` / `unknown flag`（CLI 版本差异）时才查 `worldfirst <命令> --help`，并建议客户升级 CLI。
- 长驻命令（`listen`、`logs tail`）会一直运行到 Ctrl+C，不要在前台阻塞对话：请客户在单独的终端运行；宿主支持后台任务时也可后台运行，用完及时停止。
- 含换行的 PEM 公钥优先用文件参数（`--key-file`）；没有文件参数的命令（`--public-key`）在 bash/zsh 里用 `"$(cat public_key.pem)"`，在 PowerShell 里用 `(Get-Content -Raw .\public_key.pem)`。
- 时间参数（如 `logs api --start`）用 RFC3339 UTC 时间（如 `2026-11-19T08:00:00Z`）或毫秒时间戳。

## 前置检查（每次会话开始执行）

1. `worldfirst --version`：命令不存在时，告知客户需要先安装 worldfirst CLI（按 WorldFirst 开发者中心提供的安装说明，支持 macOS 与 Windows），装好后重新打开终端再继续。不要猜测下载地址。
2. `worldfirst whoami --json`：
   - `data.authenticated=true`：已登录，继续。`data.expired=true` 时 CLI 会自动刷新凭证，无需处理。
   - `data.authenticated=false`：请客户在自己的终端执行 `worldfirst login`（浏览器完成设备授权；没有浏览器的环境用 `worldfirst login --no-browser`，手动打开显示的链接），完成后告诉你；你再执行 `worldfirst whoami --verify --json` 确认。不要在对话里前台代跑 `login`，它会一直阻塞到浏览器授权完成。
3. 需要账号地区或 KYB 认证等级时，执行 `worldfirst whoami --verify --json`，读取 `data.region` 与 `data.server_biz_certification_level`。

## 错误码处理

| 错误 | 含义 | 处理 |
|---|---|---|
| `AUTH_REQUIRED` / `AUTH_EXPIRED` | 未登录 / 凭证过期且无法刷新 | 请客户执行 `worldfirst login` 后重试 |
| `SCOPE_REQUIRED` | 授权范围缺少开发者中心权限 | 请客户执行 `worldfirst login --reauth`，在授权页勾选所需权限 |
| `AUTH_DENIED` / `AUTH_PENDING_TIMEOUT` | 客户拒绝授权 / 等待授权超时 | 请客户重新执行 `worldfirst login` |
| message 含 `CLI_APP_NOT_GRANTED` | 该应用未授权给当前 CLI 登录 | 请客户执行 `worldfirst login --reauth`，在授权页勾选该应用 |
| message 含 `not synchronized` 或 `run worldfirst login` | 本地应用目录未同步（如刚创建或刚上线的应用） | `whoami` 显示已登录时，直接执行 `worldfirst login --json` 静默同步（会话有效时不会弹浏览器），再重试；否则请客户登录 |
| `APP_REQUIRED` / `APP_MISMATCH` / `CONTEXT_MISMATCH` | 未指定应用 / 多个应用 ID 冲突 | 确认显式传了正确的 ClientId；检查客户环境变量 `WF_CLIENT_ID` 是否与参数冲突 |
| `NEED_CONFIRMATION` | 写操作缺少 `--consent` / `--yes` | 先取得客户明确同意，再带上对应参数重试 |
| `WRITE_FORBIDDEN` | 对生产应用执行了写操作或 `trigger` | 改用沙箱 ClientId，或改用生产配置节点规定的命令 |
| `NETWORK_ERROR` | 网络不可达 | 提示客户检查网络或代理后重试，不要连续重试 |
| `API_ERROR` | 服务端业务错误 | 读取 message 中的原因与错误码，向客户解释，按节点文件的失败分支处理 |
| `INVALID_INPUT` | 参数非法 | 修正参数；不确定时查该命令的 `--help` |
| `INVALID_RESPONSE` / `INTERNAL_ERROR` | 非预期响应 / 内部错误 | 如实告知，建议稍后重试或升级 CLI |

任何失败都不能当作成功处理，也不能用记忆或推测的数据继续流程。

## 命令速查

### 账号、协议与地区

| 用途 | 命令 | 关键返回 |
|---|---|---|
| 登录状态 | `worldfirst whoami --json` | `authenticated`、`expired` |
| 服务端校验、地区、KYB 等级 | `worldfirst whoami --verify --json` | `region`、`server_biz_certification_level` |
| 协议签约状态 | `worldfirst contract status --json` | `signed` |
| 协议版本与正文链接 | `worldfirst contract config --json` | `agreementVersion`、`apiProtocolUrl` |
| 签署协议（需同意） | `worldfirst contract sign --consent --json` | `signed`、`alreadySigned` |
| 业务网关地址 | `worldfirst config get base_api_url --json` | `value`（登录时按账号地区设置） |

### 应用

| 用途 | 命令 | 关键返回 |
|---|---|---|
| 可集成接口目录 | `worldfirst app catalog --json` | `categoryList[].moduleList[].interfaceList[]`：`interfaceId`、`funcName`、`requestMethod`、`url`、`docUrl`、`integrated` |
| 应用列表 | `worldfirst app list --page-size 50 --json` | `dataList[]`：`appId`、`name`、`state`、`appVersion`、`authMethod` |
| 已授权给当前 CLI 登录的应用 | `worldfirst app granted --json` | `granted_app_ids`（可能不带 `SANDBOX_` 前缀，比对时两边都去掉该前缀） |
| 创建应用（需同意） | `worldfirst app create --name "<应用名>" --interface-ids <id1,id2> --auth-method <API_KEY\|RSA> --consent --json` | `appId`（`SANDBOX_` 前缀）、`authMethod` |
| 应用总览（已脱敏） | `worldfirst app overview --app-id <ClientId> --json` | `appInfo`（`name`、`state`、`appVersion`、`authMethod`、`publicKeyInfoList`、`apiKeyInfos` 的 `keyUsage` 与 `ipWhiteList`）、`integratedInterfaces[]`、`webhookConfigs[]` |
| 应用详情 | `worldfirst app detail --app-id <ClientId> --json` | `state`、`authMethod`、`publicKeyInfoList` |
| 全量替换集成接口（需同意） | `worldfirst app update-interfaces --app-id <ClientId> --interface-ids <最终全部ID> --json` | — |

### Webhook

| 用途 | 命令 | 关键返回 |
|---|---|---|
| 可订阅事件目录 | `worldfirst webhooks events --app-id <ClientId> [--config-id <配置ID>] --json` | `[].moduleName`、`[].webhookList[]`：`funcName`（事件编码）、`interfaceId`（事件 ID）、`recommended`、`occupied`、`selected` |
| 订阅配置列表 | `worldfirst webhooks list --app-id <ClientId> --json` | `list[]`：`id`、`name`、`url`、`eventCount` |
| 新建订阅（需同意） | `worldfirst webhooks subscribe --app-id <ClientId> --name "<名称>" --url <URL> --webhook-id <事件ID列表> --json` | `config.id` |
| 修改订阅（需同意） | `worldfirst webhooks subscribe --app-id <ClientId> --config-id <配置ID> [--name "<新名称>"] [--url <新URL>] [--webhook-id <全量事件ID>] --json` | 只更新传入字段；传 `--webhook-id` 即全量替换事件 |
| 删除订阅（需同意） | `WF_CLIENT_ID=<ClientId> worldfirst webhooks delete --id <配置ID> --yes --json` | `deleted` |

### 安全设置

| 用途 | 命令 |
|---|---|
| 设置沙箱 IP 白名单（API_KEY，全量替换，需同意） | `worldfirst app set-ip-whitelist --app-id <ClientId> --ip "<IPv4,IPv4>" --json` |
| 完成 API_KEY 沙箱安全设置（不带任何公钥参数） | `worldfirst app set-key --app-id <ClientId> --json` |
| 上传 RSA 商户公钥（需同意） | `worldfirst app set-key --app-id <ClientId> --key-file <public_key.pem> --json` |
| 查询 WorldFirst 平台公钥（RSA） | `worldfirst app platform-key --app-id <ClientId> --json` |

### 集成验收

| 用途 | 命令 |
|---|---|
| 必过验收用例与进度 | `worldfirst acceptance cases --client-id <ClientId> --json` |
| 单个用例与步骤进度 | `worldfirst acceptance detail --case-order-id <caseOrderId> --client-id <ClientId> --json` |
| 激活用例流程（幂等） | `worldfirst acceptance start --case-order-id <caseOrderId> --client-id <ClientId> --json` |
| 取消运行中的用例流程 | `worldfirst acceptance cancel --case-order-id <caseOrderId> --client-id <ClientId> --json` |
| API 请求日志 | `worldfirst logs api --app-id <ClientId> --env SANDBOX --start <RFC3339> --size 50 --json` |
| Webhook 投递日志 | `worldfirst logs notify --app-id <ClientId> --env SANDBOX --start <RFC3339> --json` |

### 发布与生产配置

生产配置命令的 `<ProdClientId>` 为沙箱 ClientId 去掉 `SANDBOX_` 前缀。

| 用途 | 命令 |
|---|---|
| 提交 Go-Live 审批（需同意） | `worldfirst app approval submit --app-id <ClientId> --action-type GO_LIVE --reason "<原因>" --json` |
| 查询审批详情 | `worldfirst app approval detail --app-id <ClientId> [--action-type GO_LIVE] [--process-id <processId>] --json` |
| 审批通过后确认上线（需同意） | `worldfirst app approval activate --app-id <ClientId> --process-id <processId> --json` |
| 生产环境信息 | `worldfirst app prod-config prod-info --app-id <ProdClientId> --json` |
| 提交生产公钥（RSA，需同意） | `worldfirst app approval submit --app-id <ProdClientId> --public-key "$(cat public_key.pem)" --reason "设置生产环境公钥" --json` |
| 查询生产公钥审批 | `worldfirst app approval detail --app-id <ProdClientId> --action-type EDIT_PUBLIC_KEY [--process-id <processId>] --json` |
| 生产 IP 白名单（API_KEY） | `worldfirst app prod-config ip-whitelist list --app-id <ProdClientId> --json` |
| 设置生产 IP 白名单（全量替换，需同意） | `worldfirst app prod-config ip-whitelist set --app-id <ProdClientId> --ip "<IPv4,IPv4>" --reason "设置生产环境 IP 白名单" --json` |
| 检测子账号 | `worldfirst app prod-config sub-accounts --app-id <ProdClientId> --json` |
| 开通子账号访问权限（需同意） | `worldfirst app prod-config sub-account-auth enable --app-id <ProdClientId> --json` |

### 客户自行在单独终端执行的命令（你不得代跑）

| 用途 | 命令 |
|---|---|
| 登录 / 重新授权 | `worldfirst login` / `worldfirst login --reauth` |
| 查看沙箱 API-KEY 与 Webhook Secret 明文 | `worldfirst app api-key info --app-id <SANDBOX_ClientId>` |
| 领取生产 API-KEY（仅一次）并查看生产 Webhook Secret | `worldfirst app api-key reveal --app-id <ClientId> --webhook`（终端里输入 `y` 确认） |
| 再次查看生产 Webhook Secret | `worldfirst app api-key info --app-id <ProdClientId> --webhook` |
| 为 CLI 配置 RSA 签名私钥（仅调试用 `trigger` 时需要） | `worldfirst api-key set --app-id <ClientId> --file <私钥路径>` |

### 调试工具（可选）

- `worldfirst listen --app-id <ClientId> --forward-to http://localhost:<端口>`：本地没有公网地址时，把沙箱 Webhook 通知经该连接转发到本地端点（进程运行期间生效，订阅 URL 无需修改）。
- `worldfirst logs notify ...`：查看 Webhook 投递记录与客户端点的应答。
- `worldfirst trigger list` / `worldfirst trigger <场景> --app-id <ClientId>`：在沙箱发起真实调用来制造测试事件。会产生真实沙箱数据，执行前征得同意；RSA 应用还需客户先为 CLI 配置签名私钥。只用于调试，不能替代客户集成代码完成验收。
