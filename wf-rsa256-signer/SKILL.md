---
name: wf-rsa256-signer
description: Generate Java or Golang RSA256 signing and response verification code for WorldFirst (WF) API requests. Produces WfSigner with SHA256withRSA signing using PKCS#8 private keys and Base64 encoding. Use when implementing WF API signature generation, response signature verification, or any WF RSA256 signing requirement.
---

# WF RSA256 Signer

Generates `WfSigner.java` or `signer.go` for WF API request signing and response verification.

**Note**: All Java template files use `{basePackage}` placeholder. Golang templates use `{moduleName}` placeholder. Replace with actual values when generating code.

## Signing Algorithm

**Signature content format** (must be exact, including newline):
```
POST {apiPath}\n{clientId}.{requestTime}.{requestBody}
```

**Steps:**
1. Construct content string above (UTF-8)
2. Sign with `SHA256withRSA` using PKCS#8 private key
3. Base64-encode the signature bytes
4. **URL-encode** the Base64 string (`URLEncoder.encode(base64, "UTF-8")`)
5. Set header: `algorithm=RSA256, keyVersion=2, signature={urlEncodedBase64}`

**Request-Time format:** ISO 8601 with timezone offset — `yyyy-MM-dd'T'HH:mm:ssXXX`, e.g. `2024-01-15T10:30:00+08:00`

## Class: WfSigner

**Package:** `{basePackage}.signer`

**Constructor:** `WfSigner(WfConfig config)`

**Public methods:**

| Method | Returns | Description |
|--------|---------|-------------|
| `generateSignature(String apiPath, String requestTime, String requestBody)` | `String` | Generate Base64 signature and URL-encode it; return `null` on failure |
| `verifySignature(String signatureHeader, String apiPath, String requestTime, String responseBody)` | `boolean` | Verify WF response signature; return `false` on failure |
| `getCurrentTimestamp()` | `String` | ISO 8601 timestamp in Asia/Shanghai timezone |

**Private methods:**
- `loadPrivateKey()` — read file at `config.getPrivateKeyPath()`, strip PEM headers, Base64-decode, create `PKCS8EncodedKeySpec`
- `loadPublicKey()` — read file at `config.getPublicKeyPath()`, strip PEM headers, Base64-decode, create `X509EncodedKeySpec`
- `signWithPrivateKey(String content, PrivateKey key)` — `Signature.getInstance("SHA256withRSA")`, sign UTF-8 bytes, return byte[]
- `loadKeyFromFile(String path)` — read file, skip lines starting with `-----`, concatenate remaining lines

## Signing Code Template

```java
public String generateSignature(String apiPath, String requestTime, String requestBody) {
    try {
        String signContent = "POST " + apiPath + "\n"
            + config.getClientId() + "." + requestTime + "." + requestBody;
        PrivateKey privateKey = loadPrivateKey();
        byte[] signatureBytes = signWithPrivateKey(signContent, privateKey);
        String base64Signature = Base64.getEncoder().encodeToString(signatureBytes);
        return URLEncoder.encode(base64Signature, "UTF-8");
    } catch (Exception e) {
        logger.error("Failed to generate signature", e);
        return null;
    }
}
```

## Verification Algorithm

**Response signature content format** (same structure, using response data):
```
POST {apiPath}\n{clientId}.{requestTime}.{responseBody}
```

**Steps:**
1. Extract Base64 signature value from response `Signature` header — parse out the `signature=` segment
2. **URL-decode** the extracted signature value (`URLDecoder.decode(base64Sig, "UTF-8")`)
3. Reconstruct content string using the **same `requestTime`** sent with the request
4. Verify with `SHA256withRSA` using WF public key (`X509EncodedKeySpec`)
5. Return `true` if valid, `false` (+ log warning) otherwise

## Verification Code Template

```java
public boolean verifySignature(String signatureHeader, String apiPath,
                               String requestTime, String responseBody) {
    try {
        // Extract Base64 signature value from header, e.g.:
        // "algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>"
        String base64Sig = null;
        for (String part : signatureHeader.split(",")) {
            String trimmed = part.trim();
            if (trimmed.startsWith("signature=")) {
                base64Sig = trimmed.substring("signature=".length()).trim();
                break;
            }
        }
        if (base64Sig == null) {
            logger.warn("No signature value found in header: {}", signatureHeader);
            return false;
        }

        // URL-decode before verification
        base64Sig = URLDecoder.decode(base64Sig, "UTF-8");

        String signContent = "POST " + apiPath + "\n"
            + config.getClientId() + "." + requestTime + "." + responseBody;

        PublicKey publicKey = loadPublicKey();
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(signContent.getBytes(StandardCharsets.UTF_8));
        return sig.verify(Base64.getDecoder().decode(base64Sig));
    } catch (Exception e) {
        logger.warn("Response signature verification failed", e);
        return false;
    }
}
```

## Key Rules

- **Key format**: PKCS#8 only (not PKCS#1)
- **Encoding**: Always UTF-8 for content string
- **URL encoding**: `generateSignature` must URL-encode the Base64 result (`java.net.URLEncoder`); `verifySignature` must URL-decode the header signature before Base64-decoding (`java.net.URLDecoder`)
- **Signature null check**: Callers MUST check for null return before using
- **Verify return value**: `false` means tampered/invalid — caller must throw exception
- **Same requestTime**: verification MUST use the same `requestTime` sent with the original request
- **Logging**: Log signature content at DEBUG level, never log the key itself
- **Java 8**: Use `java.util.Base64` (not `sun.misc.BASE64Encoder`)

## Common Issues

| Issue | Cause | Fix |
|-------|-------|-----|
| `InvalidKeyException` on sign | PKCS#1 key used | Convert to PKCS#8 with `openssl pkcs8` |
| `InvalidKeyException` on verify | Wrong key spec | Public key must use `X509EncodedKeySpec`, not PKCS#8 |
| Signature mismatch | Extra spaces/newlines | Use exact format above |
| Verification always false | Wrong `requestTime` | Use original request's `requestTime`, not a new timestamp |
| `NullPointerException` | `null` return ignored | Always null-check `generateSignature()` result |

---

## Golang Template

Pre-built Golang implementation is available under `template/golang/`. Use this when the user requests Golang code.

**Note**: Template uses `{moduleName}` placeholder. Replace with actual module path from user's `go.mod`.

### Template Structure

```
template/golang/
└── signer.go
```

### Key Design Points

- `Signer` interface enables mock injection for testing
- `GenerateSignatureWithPath` takes explicit API path (called by WfHttpClient)
- `VerifySignatureWithPath` verifies response signatures
- `GetRequestTime` returns ISO 8601 formatted timestamp
- Keys loaded from PEM files (PKCS#8 for private key, PKIX for public key)

### How to Generate

1. Ask user for: project path, language (Java/Golang)
2. If Golang: read `go.mod` to get module name, replace `{moduleName}`
3. If Java: ask for base package, replace `{basePackage}`
4. Generate code based on user's actual project structure
5. Template files under `template/golang/` are for reference only
