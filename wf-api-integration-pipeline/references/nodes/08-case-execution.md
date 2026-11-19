# 节点 8 · 执行集成用例

> 对应 Harness 节点 `case_execution`（Harness 中由前端执行面板驱动、AI 只做日志判定；这里由你按同样规则在对话中驱动）。

**目标**：按规划顺序逐个执行必过验收用例：客户的集成代码发起真实沙箱调用 → 你从请求日志中找到对应调用并按通过条件判定 → 以开发者中心服务端的步骤进度为准确认通过。
**完成标志**：`acceptance cases` 返回的全部必过用例 `status=FINISH`。

**AI 判定仅供参考，步骤是否通过以开发者中心服务端结果为准**——执行前把这句话告诉客户。

## 前置条件

1. 方案文件 `.worldfirst/acceptance-plan.json` 存在，且其 `caseOrderId` 集合与 `acceptance cases` 一致；否则回到集成用例规划节点。
2. 客户的集成代码已按系分方案配置好**沙箱**凭证并可运行；涉及 Webhook 的用例需要接收端点可达（本地开发可用 `worldfirst listen --app-id <ClientId> --forward-to http://localhost:<端口>`，请客户在单独终端运行）。
3. 验收的目的是验证客户的集成代码：调用必须由客户的集成代码发起（运行业务服务、调用其接口、执行其已有脚本或入口均可）。客户明确同意时，你可以代为运行客户工程里已有的入口；不要用 `worldfirst` 的资源命令、`post` 或 `trigger` 代替集成代码去推进步骤。

## 第 1 步：同步进度

执行 `worldfirst acceptance cases --client-id <ClientId> --json`，按 `caseOrderId` / `nodeDefineId` 与方案文件合并：用例名、步骤顺序、`interfaceUrl`、`requestMethod`、状态取服务端最新值，执行顺序与通过条件取方案文件。向客户展示总览：用例（按 `execOrder`）、步骤数、当前状态、剩余未通过数量。全部已 `FINISH` 时直接进入「完成」。

## 第 2 步：执行当前用例

当前用例 = `execOrder` 最小且 `status` 不是 `FINISH` 的用例（客户也可以指定先执行某个用例）。

1. **同一应用同时只能运行一个用例流程**。有其它用例处于 `PROCESSING` 时，告知客户后执行 `worldfirst acceptance cancel --case-order-id <那个用例> --client-id <ClientId> --json` 释放名额（已完成的步骤进度会保留）。
2. 当前用例不是 `PROCESSING` 时执行 `worldfirst acceptance start --case-order-id <caseOrderId> --client-id <ClientId> --json` 激活流程（已在运行时幂等成功）。没有激活的流程，步骤永远不会推进。
3. 按服务端 `step` 升序，对每个状态不是 `FINISH` 的步骤执行第 3 步；全部步骤通过后告知「用例 <名称> 的全部步骤已在开发者中心通过」，进入下一个用例。

## 第 3 步：执行一个步骤

1. 向客户展示：`步骤 i/n · <步骤名>`、目标接口 `<METHOD> <path>`、通过条件 `<expect>`。
2. 记下开始时间 T0（当前 UTC 时间减 2 分钟，容忍时钟偏差），请客户用集成代码发起这次调用，完成后告诉你。
3. 查询请求日志：

   ```bash
   worldfirst logs api --app-id <ClientId> --env SANDBOX --start <T0 RFC3339> --size 50 --json
   ```

   展开 `logDateGroups[].logMessages[]`，筛选命中目标接口的记录，按请求时间从新到旧处理：
   - 路径匹配：去掉 scheme、host 与 query，去掉首尾斜杠后不区分大小写比较，`requestMessage.url` 与步骤 `interfaceUrl` 相等或以对方结尾即视为同一接口；
   - 方法匹配：双方都有 HTTP 方法时必须一致；
   - 步骤 `interfaceUrl` 不是以 `/` 开头的对外路径（如内部接口名或事件编码）时无法从请求日志匹配：事件编码类步骤改查 `worldfirst logs notify --app-id <ClientId> --env SANDBOX --start <T0> --json`，看通知是否投递、客户端点是否应答成功；其余情况直接以第 5 步的服务端进度为准，并向客户说明。
4. 没有命中记录：告诉客户「还没有捕获到 <METHOD path> 的调用」，提示常见原因（用了生产或错误的凭证、网关地址不对、请求在到达网关前就失败、日志略有延迟），请客户确认后重试，或稍等片刻重新查询。
5. **判定**（对命中的记录逐条进行，直到有一条通过）：
   - 【通过条件】是唯一依据，不得自行放宽或追加条件；
   - 解析 `responseMessage.responseBody`（可能是转义后的 JSON 字符串，需再解析一层）后按通过条件断言业务字段；不以顶层 success 之类字段或内部结果码为依据；
   - 日志明显属于其它接口，或信息不足无法判断时，判为不通过并说明缺失点；
   - 先用一句话告诉客户结论与关键依据（只摘取相关字段，不要整段倾倒请求 / 响应报文）。
6. **判定通过 → 核对服务端**：执行 `worldfirst acceptance detail --case-order-id <caseOrderId> --client-id <ClientId> --json`，找到该 `nodeDefineId` 的步骤：
   - `status=FINISH` → 本步骤通过，进入下一步骤；
   - 尚未 `FINISH`：服务端由异步事件推进，等待约 10 秒后重查，最多 3 次；仍未推进时告诉客户「开发者中心尚未推进该步骤（当前状态：X），请稍后让我重新检测」，**不得**宣称通过。
7. **判定不通过**：说明未满足通过条件的具体原因与修正建议；客户修正代码并重新发起调用后，从本步骤第 2 点重新开始（重新记录 T0）。

## 停止与切换

- 客户要求停止：对当前运行中的用例执行 `acceptance cancel`，告诉客户已完成的步骤进度会保留，下次可以继续。
- 客户要求切换到别的用例：先 `acceptance cancel` 当前用例，再按第 2 步执行目标用例。

## 完成

再次执行 `acceptance cases`，全部必过用例 `status=FINISH` 时本节点 `DONE`：告知「全部必过验收用例已通过」，询问是否进入提交发布。仍有未通过的用例时列出清单，继续执行。
