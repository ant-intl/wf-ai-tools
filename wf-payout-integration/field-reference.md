# Payout Field Reference

## Amount Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `currency` | String | **Yes** | ISO-4217 currency code, e.g. `USD`, `CNY` |
| `value` | **Long** | Conditional | Amount in smallest currency unit (INTEGER). See [WF Amount Usage Specification](https://developers.worldfirst.com.cn/docs/alipay-worldfirst/worldfirst_enterprise_solution_zh/amount_usage).<br>• 2-decimal currencies (USD/CNY/EUR/GBP/HKD/SGD/AUD/CAD/etc): `value = face_amount × 100`<br>• 0-decimal currencies (JPY/KRW/VND/CLP): `value = face_amount × 1` |

---

## TransferFromDetail Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferFromAmount` | Amount | **Yes** | Payer amount. **Must specify `currency`** to determine deduction currency. |
| `transferFromMethod` | TransferFromMethod | No | Payer transfer method (returned in response) |

### TransferFromMethod Object

| Field | Type | Description |
|-------|------|-------------|
| `customerId` | String | Customer ID |
| `paymentMethodType` | String | Payment method type, e.g. `BALANCE` |

---

## TransferToDetail Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferToAmount` | Amount | **Yes** | Payee amount |
| `transferToMethod` | TransferToMethod | **Yes** | Transfer method |
| `transferQuote` | TransferQuote | No | Quote info (pass `quoteId` for cross-currency) |
| `purposeCode` | String | **Yes** | Transaction purpose code, default `GDS` |
| `transferNotifyUrl` | String | No | Async notification callback URL |
| `feeAmount` | Amount | No | Fee amount (returned in response) |

---

## TransferToMethod Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `paymentMethodType` | String | **Yes** | `BANK_ACCOUNT_DETAIL` (card detail mode) or `BENEFICIARY_TOKEN` (token mode) |
| `paymentMethodMetaData` | PaymentMethodMetaData | Conditional | **Required** when `paymentMethodType=BANK_ACCOUNT_DETAIL` |
| `paymentMethodId` | String | Conditional | **Required** when `paymentMethodType=BENEFICIARY_TOKEN`. Pass `beneficiaryToken` from `bindBeneficiary` API |

> **Mode Selection** — Two mutually exclusive modes:
> - **Card Detail Mode**: `paymentMethodType=BANK_ACCOUNT_DETAIL` + `paymentMethodMetaData`
> - **Card Token Mode**: `paymentMethodType=BENEFICIARY_TOKEN` + `paymentMethodId`

---

## PaymentMethodMetaData Object (Card Detail Mode)

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `bankAccountName` | String | Conditional | Account name (English) |
| `bankAccountNo` | String | **Yes** | Bank account number or card number |
| `bankName` | String | Conditional | Bank name (English) |
| `bankBIC` | String | Conditional | Bank BIC/SWIFT code (cross-border required) |
| `bankAccountIBAN` | String | Conditional | IBAN (required for some European countries) |
| `routingNumber` | String | Conditional | Routing number (required for US, etc.) |
| `beneficiaryAddress` | String | Conditional | Beneficiary address |
| `bankCountryCode` | String | Conditional | Beneficiary country code (ISO-3166, 2-letter) |
| `beneficiaryPhone` | String | Conditional | Beneficiary phone |
| `bankBranchCode` | String | Conditional | Bank branch code |
| `bankLocalName` | String | Conditional | Bank name (local language) |
| `bankAccountLocalName` | String | Conditional | Account name (local language) |
| `beneficiaryType` | String | No | `THIRD_PARTY_PERSONAL_BANK_ACCOUNT` / `THIRD_PARTY_COMPANY_BANK_ACCOUNT` / `SAME_NAME_BANK_ACCOUNT` |

---

## TransferQuote Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `quoteId` | String | Conditional | Quote ID (pass in request for cross-currency) |
| `quoteCurrencyPair` | String | No | Currency pair, e.g. `USD/CNY` (returned in response) |
| `quotePrice` | String | No | Exchange rate (returned in response) |
| `quoteStartTime` | String | No | Quote start time, ISO 8601 (returned in response) |
| `quoteExpiryTime` | String | No | Quote expiry time, ISO 8601 (returned in response) |

---

## TransferResult Object (inquiryPayout response)

| Field | Type | Description |
|-------|------|-------------|
| `resultStatus` | String | `S` — success or processing; `F` — failed; `U` — retryable |
| `resultCode` | String | `SUCCESS` / `PROCESSING` / error code |
| `resultMessage` | String | Result description |

> **Two-level result distinction**:
> - `result.resultStatus=S` → API call succeeded, check `transferResult`
> - `transferResult.resultCode=SUCCESS` → Transfer completed
> - `transferResult.resultCode=PROCESSING` → Still processing, continue polling
> - `transferResult.resultStatus=F` → Transfer failed

---

## Key Constraints

1. **transferFromDetail is required**: Must specify `transferFromAmount.currency` to tell WF which currency to deduct
2. **value mutual exclusion**: `transferFromAmount.value` and `transferToAmount.value` cannot both be specified — pick one
3. **When specifying payee amount**: `transferFromAmount` only passes `currency`, `value` = null; WF calculates payer deduction
4. **When specifying payer amount**: `transferToAmount` only passes `currency`, `value` = null; WF calculates payee receipt
5. **CNY requires businessSceneCode**: When `transferToAmount.currency=CNY`, must pass `businessSceneCode`
6. **Cross-currency payout requires consultPayout**: Call `consultPayout` first to get `quoteId`, then pass it in `createPayout` via `transferToDetail.transferQuote.quoteId`. Quote has expiry time (`quoteExpiryTime`)
