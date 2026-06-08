# WF API Integration Skill

A Skill for integrating WorldFirst (WF) APIs with LLMs or agent frameworks. Supports code generation in Java, Golang, and Python, covering transfer, payout, beneficiary management, account management, statement management, and trade order management — with RSA256 signing and production-ready templates.

## What Problem Does It Solve

WorldFirst provides a comprehensive set of APIs for cross-border payment, fund transfer, and account management. However, integrating these APIs involves complex steps including RSA256 signing, request/response model construction, error handling, and multi-language boilerplate.

This Skill packages WF API integration knowledge into standardized templates that AI can read and generate code from. Developers can integrate WF APIs in Vibe Coding mode — simply describe your needs in natural language, and the Skill helps AI:

- **Select the right API** based on your business scenario
- **Generate production-ready code** with proper signing, error handling, and coding standards
- **Avoid common pitfalls** such as hardcoded secrets, missing signature verification, and idempotency issues

## Supported Modules

| Module | Description | Languages |
|--------|-------------|-----------|
| **Transfer** | Fund transfers between WF accounts (consult / create / inquiry / notify) | Java, Golang |
| **Payout** | Disburse funds to bank cards or e-wallets (consult / create / inquiry / notify) | Java, Golang |
| **Beneficiary Management** | Beneficiary management (template inquiry / bind / remove / edit / list / bind notify) | Java, Golang |
| **Account Management** | Query account info, balance, quota, sub-users, stores; receive credit / balance change notifications | Java, Golang |
| **Statement Management** | Query transaction statement list and details | Java, Golang, Python |
| **Trade Order** | Submit trade orders (B2C settlement / B2B order association), query results, handle async notifications | Java, Golang |

> **Note**: Python currently only supports common modules (`common/`) and statement management (`statement-management/`). Other modules do not yet have Python templates.

## Quick Start

### 1. Choose Your Module

Determine which WF API module you need:

- **Transfer** — Moving funds between WF accounts
- **Payout** — Sending money to third-party bank accounts or e-wallets (payroll, supplier payments, etc.)
- **Beneficiary Management** — Managing beneficiary bank card information
- **Account Management** — Querying account balance, info, quotas, sub-users, stores, and receiving notifications
- **Statement Management** — Viewing transaction records and reconciling accounts
- **Trade Order** — Uploading trade orders for B2C settlement or B2B order association

### 2. Select an Interface

Each module contains specific interfaces. See [Module Details](#module-details) below for the full list.

### 3. Provide Required Information

Before code generation, you'll need to provide:

- **Project path** — Where to generate the code
- **Language** — Java, Golang, or Python
- **Base package / module name** — e.g., `com.example.project` (Java), `github.com/example/project` (Go), `wf_integration` (Python)
- **WF credentials** — Client ID, User ID, API base URL, RSA key paths

### 4. Generate and Verify

The Skill generates code with your configuration, then runs through a post-generation checklist to ensure correctness.

## Directory Structure

```
wf-api-integration/
├── SKILL.md                          # Skill definition and instructions
├── README.md                         # This file
└── references/                       # All reference code and guides
    ├── common/                       # Shared infrastructure (signing, config, HTTP client, models)
    │   ├── golang/
    │   ├── java/
    │   ├── python/
    │   └── README.md
    ├── transfer/                     # Transfer module
    │   ├── consult-transfer/
    │   ├── create-transfer/
    │   ├── inquiry-transfer/
    │   ├── notify-transfer/
    │   └── README.md
    ├── payout/                       # Payout module
    │   ├── consult-payout/
    │   ├── create-payout/
    │   ├── inquiry-payout/
    │   ├── notify-payout/
    │   ├── field-reference.md
    │   └── README.md
    ├── beneficiary/                  # Beneficiary management
    │   ├── inquiry-template/
    │   ├── bind/
    │   ├── remove/
    │   ├── edit/
    │   ├── inquiry-list/
    │   ├── notify-bind/
    │   └── README.md
    ├── account-management/              # Account management
    │   ├── inquiry-account/
    │   ├── inquiry-balance/
    │   ├── inquiry-available-quota/
    │   ├── inquiry-subuser/
    │   ├── inquiry-store/
    │   ├── notify-vostro/
    │   ├── notify-balance-change/
    │   └── README.md
    ├── statement-management/            # Statement management
    │   ├── inquiry-statement-list/
    │   ├── inquiry-statement-detail/
    │   └── README.md
    └── trade-order/                  # Trade order management
        ├── submit-trade-order/
        ├── inquiry-trade-order/
        ├── notify-trade-order/
        ├── field-reference.md
        └── README.md
```

Each interface directory contains:
- `GUIDE.md` — API specification and integration guide
- `java/` — Java code templates
- `golang/` — Golang code templates
- `python/` — Python code templates (where available)

## Module Details

### Transfer (万里汇转账)

| Interface | Description |
|-----------|-------------|
| consult-transfer | Get exchange rate and fees before transfer |
| create-transfer | Transfer funds between WF accounts |
| inquiry-transfer | Query transfer result (poll for PROCESSING status) |
| notify-transfer | Receive async transfer result notification (WF → integrator) |

### Payout (全球分发)

| Interface | Description |
|-----------|-------------|
| consult-payout | Consult fees, validate card template, get exchange rate quote |
| create-payout | Disburse funds to bank card or e-wallet |
| inquiry-payout | Query payout status |
| notify-payout | Receive async payout result notification (WF → integrator) |

### Beneficiary Management (收款人管理)

| Interface | Description |
|-----------|-------------|
| inquiry-template | Query card template field requirements by country/currency |
| bind | Bind a beneficiary to WF account |
| remove | Remove a bound beneficiary |
| edit | Update beneficiary nickname |
| inquiry-list | Query bound beneficiaries with pagination |
| notify-bind | Receive beneficiary bind result notification (WF → integrator) |

### Account Management (账户管理)

| Interface | Description |
|-----------|-------------|
| inquiry-account | Query WF account info (type, account number, activation status, currencies) |
| inquiry-balance | Query account balance, filterable by currency and balance type |
| inquiry-available-quota | Query available settlement quota (4 accumulation methods) |
| inquiry-subuser | Query primary and sub-account info with pagination |
| inquiry-store | Query store info and associated accounts with pagination |
| notify-vostro | Receive credit / advance payment notification (WF → integrator) |
| notify-balance-change | Receive balance change notification (WF → integrator) |

### Statement Management (账单管理)

| Interface | Description |
|-----------|-------------|
| inquiry-statement-list | Query transaction statement list with pagination |
| inquiry-statement-detail | Query detailed info for a specific statement record |

### Trade Order (交易信息管理)

| Interface | Description |
|-----------|-------------|
| submit-trade-order | Upload trade orders (B2C settlement / B2B order association) |
| inquiry-trade-order | Query upload result (PAY_INTO_CHINA only) |
| notify-trade-order | Handle async notification (PAY_INTO_CHINA only) |

## Common Infrastructure

All modules share the following components located in `references/common/`:

| Component | Java | Golang | Python | Description |
|-----------|------|--------|--------|-------------|
| WfConfig | `config/WfConfig.java` | `config/config.go` | `config/wf_config.py` | Configuration management (clientId, baseUrl, key paths, timeout) |
| WfSigner | `signer/WfSigner.java` | `signer/signer.go` | `signer/wf_signer.py` | RSA256 signing and verification |
| HttpClient | `util/WfHttpClientUtil.java` | `util/wf_http_client.go` | `util/wf_http_client.py` | HTTP client with signing injection and response verification |
| Result | `model/response/Result.java` | `model/response/result.go` | `model/response/result.py` | Unified response object |
| ErrorCode | `model/exception/WfErrorCode.java` | `model/exception/error_code.go` | `model/exception/wf_error_code.py` | Error code definitions |
| Exception | `model/exception/WfException.java` | `model/exception/wf_exception.go` | `model/exception/wf_exception.py` | Business exception class |

## Signing Algorithm

All WF APIs use RSA256 signing. The signature content format:

```
POST {apiPath}\n{clientId}.{requestTime}.{requestBody}
```

See `references/common/` for signing utility code in each language.

## Important Notes

1. **Be specific about your scenario** — When describing requirements to AI, specify the exact business scenario (e.g., "transfer between WF accounts" or "payout to bank card") to avoid ambiguity.
2. **Always review generated code** — Verify the logic yourself before deploying to production.
3. **Never hardcode secrets** — RSA private keys, clientId, and secretKey must be loaded via file paths, environment variables, or secret management services. Never store them in source code.
4. **Always verify signatures** — Verify the signature on every WF API response and async notification to prevent man-in-the-middle attacks.
5. **Ensure idempotency** — Use unique `transferRequestId` / `payoutRequestId` for fund-related operations to prevent duplicate deductions caused by retries.
6. **Use HTTPS only** — All API requests must use HTTPS. HTTP is not allowed.
7. **Don't assume final results** — After initiating a transfer or payout, confirm the final status via inquiry APIs or async notifications, never rely on the request response status alone.
8. **Separate production and test keys** — Production and test environments must use different clientId and key pairs.
