# 节点 4 · 工程设置

> 对应 Harness 节点 `dev_settings`（skill：wf-dev-setting）。

**目标**：确认集成方式；选择代码集成时，确认工程目录、按 Git 决策树准备分支、识别项目环境。
**完成标志**：输出项目环境报告并记录工程目录；或客户选择「跳过应用集成」。

## 第 1 步：集成方式

说明本步骤目的：为 WorldFirst API 集成开发做准备。询问：

1. **请帮我完成代码编写**：分析你的工程并直接生成集成代码。
2. **跳过应用集成**：暂不在这里写代码（例如已有集成代码，或由其他团队开发），直接进入集成测试。

- 选 1 → 第 2 步。
- 选 2 → 本节点 `DONE`，`integration.mode="skip"`，系分设计与编码记为 `SKIPPED`，下一节点为集成用例规划。客户之后改主意要写代码，可以回到本节点重新选择。

除这里以外，本节点其它步骤都不提供「跳过应用集成」。

## 第 2 步：确认工程目录

默认使用当前工作区根目录，向客户确认「是否在 `<当前目录>` 这个工程里集成」；客户也可以给出其它工程的绝对路径。记下这个绝对路径，后续所有文件读写与 Git 操作都基于它（用 `git -C "<工程目录>" ...` 或先进入该目录），不要在其它目录操作。

工程目录与当前工作区不同时，把 `.worldfirst/` 整个迁移到工程目录下（同样写入该工程的 `.git/info/exclude`），并提醒客户以后在工程目录里打开 AI 会话，以便续跑。

## 第 3 步：Git 检测（拿到目录后必须先做）

拿到工程目录后，**先**用 Bash 真实执行下面的检测并按决策树向客户确认，客户做出选择、执行完对应 Git 操作（或选择不用 Git）之后，才进入第 4 步。不要拿到目录就直接读工程、出环境报告。

```bash
git --version                                         # ① 是否安装 Git
git -C "<工程目录>" rev-parse --is-inside-work-tree   # ② 是否 Git 仓库
git -C "<工程目录>" rev-parse --abbrev-ref HEAD       # ③ 当前分支
git -C "<工程目录>" status --porcelain                # ③ 未提交变更
```

**铁律**：本节点只允许 `git --version`、`rev-parse`、`status` 这类读操作，以及客户同意后的 `git init`、`git checkout -b`、`git checkout <分支>`。**绝不** `commit`、`push`、`stash`、`reset`、`clean`，也不以任何方式丢弃客户代码。

### A. 是否安装了 Git

- 已安装 → B。
- 未安装：探测平台与包管理器（`uname -s`；`command -v brew` / `apt-get` / `dnf` / `yum`；Windows 用 `winget --version`），然后询问：
  1. 允许帮我安装 Git（用系统包管理器安装，可能需要管理员密码）
  2. 我自行安装后重试
  3. 不使用 Git，直接生成代码（无法使用分支隔离与变更追踪）
  - 选 1：执行对应安装命令（如 `brew install git`、`sudo apt-get install -y git`、`sudo dnf install -y git`、`winget install --id Git.Git -e`），如实回报输出，再用 `git --version` 复核后进入 B。需要交互输入密码而无法完成、或没有可用包管理器时，**不得谎报成功**，说明原因并建议客户自行安装。
  - 选 2：等客户装好后重新检测。
  - 选 3：跳过所有 Git 操作，进入第 4 步，报告中注明「未使用 Git」。

### B. 是否为 Git 仓库

- 不是 Git 仓库，询问：
  1. 为我初始化 Git 并新建集成分支（`git init` 后切到 `worldfirst-integration` 分支）
  2. 重新选择工程目录
  3. 不使用 Git，直接生成代码
  - 选 1：执行 `git -C "<工程目录>" init` 与 `git -C "<工程目录>" checkout -b worldfirst-integration`，把 `.worldfirst/` 写入新仓库的 `.git/info/exclude`，回报结果后进入第 4 步。
  - 选 2：回到第 2 步，拿到新目录后重新从 A 检测。
  - 选 3：进入第 4 步，报告中注明「未使用 Git」。
- 是 Git 仓库 → C。

### C. 分支与未提交变更

如实展示当前分支与 `status --porcelain` 的变更清单。注意：本 skill 自己的 `.worldfirst/` 已写入 `.git/info/exclude`，不会出现在变更清单里；如果出现，说明 exclude 未生效，先补上再检测。

- **工作区干净**，询问：
  1. 新建集成分支 `worldfirst-integration`（推荐，不影响当前分支）
  2. 使用当前分支
  - 选 1：执行 `git -C "<工程目录>" checkout -b worldfirst-integration`（同名分支已存在时改为 `git -C "<工程目录>" checkout worldfirst-integration`），回报后进入第 4 步。
  - 选 2：不建分支，进入第 4 步。
- **有其它未提交代码**（必须让客户自己决定，不要替客户 stash、commit 或丢弃），询问：
  1. 我自行处理这些变更，稍后重新检测（推荐）
  2. 把未提交变更一并带到新集成分支（`checkout -b`，变更随工作区带过去）
  3. 使用当前分支继续（集成代码与现有变更在同一分支）
  - 选 1：不执行任何 Git 写操作，请客户提交或回滚后告诉你「已处理完」；再执行 `status --porcelain`，仍有变更就说明还剩几项并继续等待，已干净则执行 `checkout -b worldfirst-integration` 后进入第 4 步。
  - 选 2：执行 `git -C "<工程目录>" checkout -b worldfirst-integration`，回报后进入第 4 步。
  - 选 3：不建分支，进入第 4 步。

## 第 4 步：项目环境报告

进入前自检：已真实执行 ①②③ 检测；已向客户确认 Git 决策并执行了对应操作（或客户明确选择不用 Git）。任一项不满足就回到第 3 步补齐。

读取工程的构建与配置文件（如 `pom.xml`、`build.gradle`、`package.json`、`go.mod`、`requirements.txt` / `pyproject.toml`、`composer.json`），输出 Markdown 环境报告：开发语言与版本、构建工具、框架、关键依赖、包结构 / 目录结构、配置方式（如 `application.yml`、`.env`），以及 Git 使用情况（分支或「未使用 Git」）。

报告输出后本节点 `DONE`：写入 `integration.mode="code"`、`projectDir`、`git`、`environment`，告知「接下来进入系分设计，再由编码节点完成集成」，询问是否继续。
