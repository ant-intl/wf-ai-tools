<p align="center">
  <img alt="WF API Integration Skill" src="resources/logos/wf-api-skill-logo.svg" width="80" height="80">
</p>

<p align="center">
  <a href="https://github.com/trending">
    <img src="https://img.shields.io/badge/WorldFirst-API%20Integration-blue?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJ3aGl0ZSI+PHBhdGggZD0iTTEyIDJMMyA3djEwbDkgNSA5LTVWN2wtOS01eiIvPjwvc3ZnPg=="/>
  </a>
</p>

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Version](https://img.shields.io/badge/version-1.0.0-brightgreen)](CHANGELOG.md)
[![Languages](https://img.shields.io/badge/Languages-Java%20|%20Golang%20|%20Python-orange)](wf-api-integration/references/)

🌐 **Language / 语言:** [English](README.md) | [中文](README_zh.md)

# WF API Integration Skill

A skill for integrating WorldFirst APIs with LLM or Agent frameworks. Supports Java, Golang, and Python code generation, covering Transfer, Payout, Beneficiary Management, Account Management, Statement Management, and Trade Order Management — with built-in RSA256 signing and production-grade templates.

**[Quick Start](#quick-start)** | **[Supported Modules](#supported-modules)** | **[Browse References](wf-api-integration/references/)**

---

## Table of Contents

- [What Problem Does This Solve](#what-problem-does-this-solve)
- [Supported Modules](#supported-modules)
- [Quick Start](#quick-start)
- [Directory Structure](#directory-structure)
- [Module Details](#module-details)
- [Common Infrastructure](#common-infrastructure)
- [Signing Algorithm](#signing-algorithm)
- [Important Notes](#important-notes)

---

## What Problem Does This Solve

WorldFirst provides a comprehensive set of cross-border payment, fund transfer, and account management APIs. However, integrating these APIs involves complex steps including RSA256 signing, request/response model construction, error handling, and multi-language boilerplate code.

This skill packages WF API integration knowledge into standardized templates that AI can read and generate code from. Developers can integrate WF APIs in Vibe Coding mode — simply describe your requirements in natural language, and the skill helps AI to:

- **Select the right API** — Match the most appropriate endpoint based on your business scenario
- **Generate production-grade code** — With correct signing, error handling, and coding standards
- **Avoid common pitfalls** — Such as hardcoded keys, missing signature verification, idempotency issues, etc.

## Supported Modules

| Module | Description | Languages |
|--------|-------------|-----------|
| **Transfer** | Fund transfers between WorldFirst accounts (Consult / Create / Inquiry / Notify) | Java, Golang |
| **Payout** | Payments to bank cards or e-wallets (Consult / Create / Inquiry / Notify) | Java, Golang |
| **Beneficiary** | Beneficiary management (Template Inquiry / Bind / Remove / Edit / List / Bind Notify) | Java, Golang |
| **Account** | Query account info, balance, quota, sub-users, stores; receive deposit/balance change notifications | Java, Golang |
| **Statement** | Query transaction statement list and details | Java, Golang, Python |
| **Trade Order** | Submit trade orders (B2C settlement / B2B association), query results, handle async notifications | Java, Golang |

> **Note**: Python currently only supports the common module (`common/`) and Statement Management (`statement-management/`). Other modules do not have Python templates yet.

## Quick Start

### 1. Choose a Module

Determine which WF API module you need:

- **Transfer** — Fund transfers between WorldFirst accounts
- **Payout** — Payments to third-party bank accounts or e-wallets (payroll, supplier payments, etc.)
- **Beneficiary** — Manage beneficiary bank card information
- **Account** — Query account balance, info, quota, sub-users, stores; receive notifications
- **Statement** — View transaction records and reconciliation
- **Trade Order** — Upload trade orders for B2C settlement or B2B association

### 2. Choose an Endpoint

Each module contains specific endpoints. See [Module Details](#module-details) below for the full list.

### 3. Provide Required Information

Before code generation, you need to provide:

- **Project path** — Target location for generated code
- **Programming language** — Java, Golang, or Python
- **Base package / module name** — e.g., `com.example.project` (Java), `github.com/example/project` (Go), `wf_integration` (Python)
- **WF credentials** — Client ID, User ID, API base URL, RSA key paths

### 4. Generate and Verify

The skill generates code based on your configuration, then performs a post-generation checklist to ensure correctness.

## Directory Structure

```
wf-api-integration/
├── SKILL.md                          # Skill definition and instructions
├── README.md                         # This file
└── references/                       # All reference code and guides
    ├── common/                       # Common infrastructure (signing, config, HTTP client, models)
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

Each endpoint directory contains:
- `GUIDE.md` — API specification and integration guide
- `java/` — Java code templates
- `golang/` — Golang code templates
- `python/` — Python code templates (available for some endpoints)

## Module Details

### Transfer

| Endpoint | Description |
|----------|-------------|
| consult-transfer | Get exchange rates and fees before transferring |
| create-transfer | Transfer funds between WorldFirst accounts |
| inquiry-transfer | Query transfer result (poll for PROCESSING status) |
| notify-transfer | Receive async transfer result notification (WF → integrator) |

### Payout

| Endpoint | Description |
|----------|-------------|
| consult-payout | Consult fees, validate card templates, get exchange rate quotes |
| create-payout | Make payments to bank cards or e-wallets |
| inquiry-payout | Query payment status |
| notify-payout | Receive async payment result notification (WF → integrator) |

### Beneficiary Management

| Endpoint | Description |
|----------|-------------|
| inquiry-template | Query card template field requirements by country/currency |
| bind | Bind a beneficiary to a WorldFirst account |
| remove | Unbind a bound beneficiary |
| edit | Update beneficiary nickname |
| inquiry-list | Paginated query of bound beneficiary list |
| notify-bind | Receive beneficiary binding result notification (WF → integrator) |

### Account Management

| Endpoint | Description |
|----------|-------------|
| inquiry-account | Query WorldFirst account info (type, account number, activation status, currency) |
| inquiry-balance | Query account balance, filterable by currency and balance type |
| inquiry-available-quota | Query available FX quota (4 accumulation methods) |
| inquiry-subuser | Paginated query of master/sub-account info |
| inquiry-store | Paginated query of store info and associated accounts |
| notify-vostro | Receive deposit/prepayment notification (WF → integrator) |
| notify-balance-change | Receive balance change notification (WF → integrator) |

### Statement Management

| Endpoint | Description |
|----------|-------------|
| inquiry-statement-list | Paginated query of transaction statement list |
| inquiry-statement-detail | Query detailed information of a specific statement record |

### Trade Order

| Endpoint | Description |
|----------|-------------|
| submit-trade-order | Upload trade orders (B2C settlement / B2B association) |
| inquiry-trade-order | Query upload result (PAY_INTO_CHINA only) |
| notify-trade-order | Handle async notification (PAY_INTO_CHINA only) |

## Common Infrastructure

All modules share the following components located in `references/common/`:

| Component | Java | Golang | Python | Description |
|-----------|------|--------|--------|-------------|
| WfConfig | `config/WfConfig.java` | `config/config.go` | `config/wf_config.py` | Configuration management (clientId, baseUrl, key paths, timeout) |
| WfSigner | `signer/WfSigner.java` | `signer/signer.go` | `signer/wf_signer.py` | RSA256 signing and verification |
| HttpClient | `util/WfHttpClientUtil.java` | `util/wf_http_client.go` | `util/wf_http_client.py` | HTTP client with signature injection and response verification |
| Result | `model/response/Result.java` | `model/response/result.go` | `model/response/result.py` | Unified response object |
| ErrorCode | `model/exception/WfErrorCode.java` | `model/exception/error_code.go` | `model/exception/wf_error_code.py` | Error code definitions |
| Exception | `model/exception/WfException.java` | `model/exception/wf_exception.go` | `model/exception/wf_exception.py` | Business exception class |

## Signing Algorithm

All WF APIs use RSA256 signing. The signing content format:

```
POST {apiPath}\n{clientId}.{requestTime}.{requestBody}
```

See `references/common/` for signing utility code in each language.

## Important Notes

1. **Be specific about your business scenario** — When describing requirements to AI, specify the exact business scenario (e.g., "transfer between WorldFirst accounts" or "pay to a bank card") to avoid ambiguity.
2. **Always review generated code** — Verify logical correctness before deploying to production.
3. **Never hardcode keys** — RSA private keys, clientId, and secretKey must be loaded via file paths, environment variables, or key management services. Never store them in source code.
4. **Always verify signatures** — Verify signatures on every WF API response and async notification to prevent man-in-the-middle attacks.
5. **Ensure idempotency** — Use unique `transferRequestId` / `payoutRequestId` for fund operations to prevent duplicate charges from retries.
6. **Use HTTPS only** — All API requests must use HTTPS. Never use HTTP.
7. **Don't assume final results** — After initiating a transfer or payment, always confirm the final status via inquiry endpoints or async notifications. Never rely solely on the request response status.
8. **Separate production and test keys** — Production and test environments must use different clientId and key pairs.
