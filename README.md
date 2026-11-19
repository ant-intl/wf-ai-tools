<p align="center">
  <img alt="WF API Integration Skill" src="resources/logos/wf-api-skill-logo.svg" width="80" height="80">
</p>

<p align="center">
  <a href="https://github.com/trending">
    <img src="https://img.shields.io/badge/WorldFirst-API%20Integration-blue?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJ3aGl0ZSI+PHBhdGggZD0iTTEyIDJMMyA3djEwbDkgNSA5LTVWN2wtOS01eiIvPjwvc3ZnPg=="/>
  </a>
</p>

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Skills](https://img.shields.io/badge/Skills-3-green)](#which-skill-do-i-need)
[![Endpoints](https://img.shields.io/badge/Endpoints-106-orange)](#which-skill-do-i-need)
[![Languages](https://img.shields.io/badge/Languages-Java%20|%20Golang%20|%20Python%20|%20PHP%20|%20Adaptive-orange)](wf-api-integration/references/)

🌐 **Language / 语言:** [English](README.md) | [中文](README_zh.md)

# WorldFirst API Integration Skills

Packaged WorldFirst (WF) API integration knowledge — endpoint specs, RSA256 signing, request/response models, and production-grade code templates — that an AI coding agent reads to generate working integration code from a natural-language requirement. The classic skill natively supports Java, Golang, Python, and PHP, and adapts the same API contracts to other technology stacks through project-aware generation.

This repository ships **three skills**: two code-generation skills, one per API version, and an end-to-end integration pipeline skill that takes an application from creation to production.

## Which Skill Do I Need?

- To go through the whole journey — create the application, integrate, pass acceptance, go live — from your own AI assistant via the worldfirst CLI, without the Developer Harness console: use [WF API Integration Pipeline](#wf-api-integration-pipeline).
- To generate integration code for specific endpoints only: pick from the table below.

| | **WF API Integration** | **WF API Integration Upgrade** |
|---|---|---|
| Directory | [`wf-api-integration/`](wf-api-integration/) | [`wf-api-integration-upgrade/`](wf-api-integration-upgrade/) |
| API surface | `/api/v1/business/*` | `/api/open/v1/*` (Enhanced API) |
| Languages | Java, Golang, PHP (Python: common + statement) | Java |
| Modules / endpoints | 6 / 26 | 23 / 80 |
| Extra scope | language-agnostic adaptation | FX trading, card issuing, OAuth2, files, confirmation letters |
| Entry point | [SKILL.md](wf-api-integration/SKILL.md) | [SKILL.md](wf-api-integration-upgrade/SKILL.md) |

> Unsure which prefix applies to you? Check the credentials and endpoint host in your WF onboarding material — each skill's `SKILL.md` lists the exact base URLs.

---

## WF API Integration

Fund flows and account operations on `/api/v1/business/*`. Java, Golang, and PHP cover every module; Python covers `common` and Statement only.

| Domain | Module | Endpoints | Covers |
|---|---|---|---|
| Fund Transfer | [Transfer](wf-api-integration/references/transfer/README.md) | 4 | Consult rate/fee, transfer between WF accounts, inquiry, result notification |
| Payout | [Payout](wf-api-integration/references/payout/README.md) | 4 | Consult, pay to bank card or e-wallet, inquiry, result notification |
| Beneficiary | [Beneficiary](wf-api-integration/references/beneficiary/README.md) | 6 | Card-template inquiry, bind / remove / edit / list, bind notification |
| Accounts | [Account Management](wf-api-integration/references/account-management/README.md) | 7 | Account info, balance, FX quota, sub-users, stores, vostro & balance-change notifications |
| Statements & Reports | [Statement Management](wf-api-integration/references/statement-management/README.md) | 2 | Statement list and detail (Java, Golang, Python, PHP) |
| Trade Documents | [Trade Order](wf-api-integration/references/trade-order/README.md) | 3 | Submit trade orders (B2C settlement / B2B association), query, notify |

Endpoint-level parameters, examples and error codes live in each module's `<endpoint>/GUIDE.md`; module-level flows and enums live in the module `README.md`.

---

## WF API Integration Upgrade

The Enhanced API (`/api/open/v1/*`), Java only. Each module ships a thin-wrapper `*Service`, request/response models, and per-endpoint guides.

| Domain | Module | Endpoints | Covers |
|---|---|---|---|
| Authorization | [oauth2](wf-api-integration-upgrade/references/authorization/oauth2/README.md) | 3 | Start authorization, request access token, revoke token |
| Accounts | [balance](wf-api-integration-upgrade/references/accounts/balance/README.md) | 2 | Current balances, balance change history |
| Accounts | [global-accounts](wf-api-integration-upgrade/references/accounts/global-accounts/README.md) | 5 | Open, query, update, close a global receiving account; list |
| Connected Accounts | [connected-accounts](wf-api-integration-upgrade/references/connected/connected-accounts/README.md) | 3 | Create a connected account (sub-merchant onboarding), query, list |
| Receiving | [deposits](wf-api-integration-upgrade/references/receiving/deposits/README.md) | 2 | Deposit list and single-deposit detail |
| Statements & Reports | [statement-report](wf-api-integration-upgrade/references/reporting/statement-report/README.md) | 2 | Statement list and single-statement detail |
| Beneficiary | [beneficiaries](wf-api-integration-upgrade/references/payouts/beneficiaries/README.md) | 7 | Template inquiry, create / query / list / update / remove, validate |
| Payout | [payouts](wf-api-integration-upgrade/references/payouts/payouts/README.md) | 3 | Consult fees and rate, create payout, query payout |
| Foreign Exchange | [quote](wf-api-integration-upgrade/references/foreign-exchange/quote/README.md) | 3 | Live rates, rate history, create binding quote |
| Foreign Exchange | [deal](wf-api-integration-upgrade/references/foreign-exchange/deal/README.md) | 5 | Create / query / cancel deal, list deals, margin charge records |
| Foreign Exchange | [settlement](wf-api-integration-upgrade/references/foreign-exchange/settlement/README.md) | 3 | Create settlement, query settlement, list settlements |
| Foreign Exchange | [reference](wf-api-integration-upgrade/references/foreign-exchange/reference/README.md) | 2 | Trading calendar, supported currency pairs |
| Issuing | [cardholders](wf-api-integration-upgrade/references/issuing/cardholders/README.md) | 4 | Register cardholder with KYC, query, list, delete |
| Issuing | [budgets](wf-api-integration-upgrade/references/issuing/budgets/README.md) | 6 | Create / query / list budget accounts, deposit, withdraw, daily balances |
| Issuing | [otp-management](wf-api-integration-upgrade/references/issuing/otp-management/README.md) | 2 | Query and update whether 3DS OTP is delivered via API |
| Supporting Services | [file](wf-api-integration-upgrade/references/supporting-service/file/README.md) | 2 | Upload file (base64), get temporary download URL |
| Supporting Services | [confirmation-letters](wf-api-integration-upgrade/references/supporting-service/confirmation-letters/README.md) | 2 | Create confirmation letter download task, query task and link |
| Trade Documents | [tradeOrders](wf-api-integration-upgrade/references/supporting-service/tradeOrders/README.md) | 3 | Submit a trade order batch, query batch status, query available settlement quota |
| payouts | [batch-payouts](wf-api-integration-upgrade/references/payouts/batch-payouts/README.md) | 9 | Batch Payouts |
| issuing | [cards-management](wf-api-integration-upgrade/references/issuing/cards-management/README.md) | 5 | Cards Management |
| issuing | [transactions](wf-api-integration-upgrade/references/issuing/transactions/README.md) | 1 | Transactions |
| reporting | [download-report](wf-api-integration-upgrade/references/reporting/download-report/README.md) | 3 | Download Report |
| supporting-service | [request-for-information](wf-api-integration-upgrade/references/supporting-service/request-for-information/README.md) | 3 | Request for Information |

The [module & endpoint index](wf-api-integration-upgrade/SKILL.md#模块与接口索引) in `SKILL.md` is the authoritative routing table; the [decision tree](wf-api-integration-upgrade/SKILL.md#快速决策树) maps a business question to a module, and each module `README.md` maps a module to its endpoints.

---

## WF API Integration Pipeline

The whole Developer Harness (local web console) integration pipeline packaged as one skill: the customer chats with their own AI coding assistant (Claude Code, Codex, Cursor, Qoder, …), and the AI follows the skill to orchestrate the flow and call the `worldfirst` CLI for every real operation — the same outcome as the Harness, without its UI. V2 (Enhanced, `/api/open/v1/*`) applications only.

| Stage | Nodes | Main CLI commands |
|---|---|---|
| Create | Basic info (API selection → service agreement → create app), webhook subscriptions, security setup | `app catalog`, `contract`, `app create`, `webhooks`, `app set-key` / `set-ip-whitelist` |
| Integrate | Project setup (Git, environment detection), system design, coding | `app overview`; endpoint definitions fetched live from the official docs |
| Accept | Acceptance case planning (1:1 with server-side mandatory cases), case execution | `acceptance cases/start/detail/cancel`, `logs api` |
| Release | Go-live submission (KYB L3 → approval → activation), production configuration | `whoami --verify`, `app approval`, `app prod-config` |

- **Entry point**: [SKILL.md](wf-api-integration-pipeline/SKILL.md) covers the overview, start-up and resume, interaction rules and security red lines; the 10 files under [references/nodes/](wf-api-integration-pipeline/references/nodes/) map one-to-one to the Harness nodes; [cli.md](wf-api-integration-pipeline/references/cli.md) is the command reference and error-code playbook; [state.md](wf-api-integration-pipeline/references/state.md) defines the progress file and resume rules.
- **Prerequisites**: the worldfirst CLI is installed and `worldfirst login` is done. Once the skill is published to the skills index, `worldfirst agent skills install --skill wf-api-integration-pipeline --yes` installs it into the local AI tools.
- **Resume**: progress is kept in `.worldfirst/pipeline-state.json` inside the customer project (added to `.git/info/exclude`, never committed, no secrets) and reconciled with server-side facts on every resume.
- **Credential safety**: the AI never runs commands that print secrets (such as `app api-key info/reveal`); the customer views them in their own terminal, and private keys never leave the customer's machine.

Maintenance: node rules mirror the Harness node skills — `wf-api-intent-recognition` (basic info), `wf-webhook-setting`, `wf-security-setting`, `wf-dev-setting`, `wf-sys-analysis`, `wf-coding`, `wf-integration-case`, case execution (Harness front-end panel), `wf-release-submit`, `wf-prod-config`. When a node rule changes, update the matching node file and bump `version`.

---

## Quick Start

1. **Route to a module** — describe the business outcome (e.g. "pay a supplier's bank card") and let the skill's decision tree pick the module, or start from a module README above.
2. **Read the endpoint guide** — `<module>/<endpoint>/GUIDE.md` holds the endpoint path, request/response fields, enums, error codes and samples. Endpoint directory names follow the module README's endpoint table, not the interface name.
3. **Supply environment inputs** — target project path, language (Java, Golang, Python, PHP, or another stack via the [language-agnostic adaptation workflow](wf-api-integration/references/language-agnostic/README.md)), base package (`{basePackage}` is replaced during generation), plus clientId, base URL and RSA private/public key file paths. Keys are always loaded from paths or environment variables, never inlined.
4. **Generate, then verify** — the skill runs a post-generation checklist (model field parity, compile, package/import check). Review the generated code before it reaches production.

## Repository Layout

```
.
├── wf-api-integration/                 # Skill 1 — /api/v1/business/*
│   ├── SKILL.md                        # routing, flows, security rules
│   └── references/
│       ├── common/                     # config, signer, HTTP client, models (Java/Golang/Python/PHP)
│       ├── language-agnostic/          # contract-driven adaptation for other stacks
│       └── <module>/<endpoint>/        # GUIDE.md + java/ golang/ python/ php/
├── wf-api-integration-upgrade/         # Skill 2 — /api/open/v1/*
│   ├── SKILL.md                        # decision tree, keyword routing, module index
│   └── references/
│       ├── common/java/                # WfClientConfig, WfSignatureUtil, WfApiClient, Result, WfErrorCode, WfException, Amount/Address/UserName
│       └── <domain>/<module>/          # GUIDE.md + java/{service,model/{domain,request,response}}
└── wf-api-integration-pipeline/        # Skill 3 — end-to-end integration pipeline (via the worldfirst CLI)
    ├── SKILL.md                        # pipeline overview, start-up & resume, interaction rules, security red lines
    └── references/
        ├── cli.md                      # CLI conventions, error-code playbook, command reference
        ├── state.md                    # local progress file and resume rules
        └── nodes/                      # 01-basic-info … 10-prod-config, one per Harness node
```

## Common Ground

Both skills use the same RSA256 scheme — `SHA256withRSA`, PKCS#8 private key, X.509 WF public key, signature header `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>`. Every response and every async notification must be verified; the Upgrade skill enforces verification inside its HTTP client and raises on failure.

Security rules that apply to any generated code:

- **Keys never in source** — load from file paths, environment variables or a secrets manager; keep `.pem` files out of version control.
- **Idempotency on writes** — fund-moving operations carry a unique request ID; retrying with the same ID and identical payload must be safe.
- **Never assume a final result** — confirm via inquiry endpoints or async notifications; an unknown outcome is queried, not blindly re-sent with a new ID.
- **Environment isolation** — sandbox and production use different client IDs and key pairs; HTTPS only, no plaintext logging of keys or full response bodies.

## Notes

- Generated code is a starting point: verify business logic, amounts, currencies and status handling in your own review.
- Where a guide and the live WF documentation disagree, follow the live documentation and raise it so the skill can be corrected.
- New modules and endpoints are added under the owning skill's `references/` directory; remember to update that skill's `SKILL.md` routing tables and module index.

## License

Maintainers can validate the five new Enhanced API modules with `python3 tests/upgrade-modules/validate.py`. See [template validation](tests/upgrade-modules/README.md) for dependencies, coverage, and limits.

[MIT](LICENSE)
