---
name: wf-http-client
description: Generate Java or Golang HTTP client utility code for WorldFirst (WF) API requests. Produces WfHttpClientUtil/WfHttpClient with RSA256 signature injection, standard WF headers, and response handling. Use when implementing WF API HTTP communication, building reusable WF HTTP utilities, or adding HTTP client support to any WF API integration.
---

# WF HTTP Client Utility

Generates `WfHttpClientUtil.java` or `wf_http_client.go` — the reusable HTTP layer for all WF API integrations.

**Note**: All Java template files use `{basePackage}` placeholder. Golang templates use `{moduleName}` placeholder. Replace with actual values when generating code.

## Design Principle

**Single responsibility**: all WF HTTP request logic lives here. No API client class should duplicate HTTP handling. Every WF integration reuses this utility.

## Class: WfHttpClient

**Package:** `{basePackage}.util`

**Dependencies:** Apache HttpClient 4.x (`org.apache.http.impl.client.CloseableHttpClient`)

**Constructor:** `WfHttpClientUtil(WfConfig config)`
- Creates `CloseableHttpClient` with `connectTimeout` and `socketTimeout` from config
- Creates `WfSigner(config)` internally

**Public methods:**

| Method | Returns | Description |
|--------|---------|-------------|
| `sendPostRequest(String url, String apiPath, String requestBody)` | `String` | Send signed POST, return response body |
| `close()` | `void` | Close underlying HTTP client |
| `getConfig()` | `WfConfig` | Accessor |
| `getSigner()` | `WfSigner` | Accessor (for callers needing direct signer access) |

## Required Headers

Every request MUST set these four headers:

| Header | Value |
|--------|-------|
| `Content-Type` | `application/json; charset=UTF-8` |
| `Client-Id` | `config.getClientId()` |
| `Request-Time` | 直接用 Java 代码生成当前时间，ISO 8601 格式，Asia/Shanghai 时区，格式：`yyyy-MM-dd'T'HH:mm:ssXXX` |
| `Signature` | `algorithm=RSA256, keyVersion=2, signature={base64}` |

## Code Template

```java
public String sendPostRequest(String url, String apiPath, String requestBody)
    throws WfException {

    // 直接用 Java 代码生成当前时间，ISO 8601，Asia/Shanghai 时区
    String requestTime = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"))
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
    String signature = signer.generateSignature(apiPath, requestTime, requestBody);

    if (signature == null) {
        throw new WfException(
            WfErrorCode.HTTP_REQUEST_FAILED, "Failed to generate signature");
    }

    HttpPost httpPost = new HttpPost(url);
    httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
    httpPost.setHeader("Client-Id", config.getClientId());
    httpPost.setHeader("Request-Time", requestTime);
    httpPost.setHeader("Signature", "algorithm=RSA256, keyVersion=2, signature=" + signature);
    httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));

    try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
        return handleResponse(response, apiPath);
    } catch (WfException e) {
        throw e;
    } catch (IOException e) {
        throw new WfException(
            WfErrorCode.HTTP_REQUEST_FAILED, e.getMessage(), e);
    }
}
```

## Response Handling

```java
private String handleResponse(CloseableHttpResponse response, String apiPath)
    throws WfException, IOException {

    int statusCode = response.getStatusLine().getStatusCode();
    String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

    if (statusCode != 200) {
        throw new WfException(
            WfErrorCode.HTTP_REQUEST_FAILED,
            "HTTP status " + statusCode + ", body: " + body);
    }

    Header signatureHeader = response.getFirstHeader("Signature");
    Header responseTime = response.getFirstHeader("response-time");
    if (signatureHeader != null && signatureHeader.getValue() != null) {
        boolean valid = signer.verifySignature(
            signatureHeader.getValue(), apiPath, responseTime.getValue(), body);
        if (!valid) {
            LOGGER.error("WfHttpClientUtil response signature verification failed, apiPath=" + apiPath);
            throw new WfException(WfErrorCode.INVALID_SIGNATURE,
                "Response signature verification failed");
        }
        LOGGER.debug("WfHttpClientUtil response signature verified, apiPath=" + apiPath);
    } else {
        LOGGER.warn("WfHttpClientUtil no Signature header in response, apiPath=" + apiPath);
    }
    return body;
}
```

## HttpClient Initialization

```java
RequestConfig requestConfig = RequestConfig.custom()
    .setConnectTimeout(config.getConnectTimeout())
    .setSocketTimeout(config.getReadTimeout())
    .setConnectionRequestTimeout(config.getConnectTimeout())
    .build();

this.httpClient = HttpClients.custom()
    .setDefaultRequestConfig(requestConfig)
    .build();
```

## Logging Requirements

- `INFO`: Log URL and `requestTime` before each request
- `INFO`: Log response HTTP status code
- `DEBUG`: Log request body and response body (may contain sensitive data — confirm log level is appropriate)
- `WARN`: Log close() failures (non-fatal)
- `ERROR`: Log unexpected exceptions

## Rules

- **Always** use `try-with-resources` for `CloseableHttpResponse`
- **Never** catch `WfException` and re-wrap it (rethrow as-is)
- **Always** check signature is non-null before sending
- `close()` must be idempotent and swallow `IOException`

---

## Golang Template

Pre-built Golang implementation is available under `template/golang/`. Use this when the user requests Golang code.

**Note**: Template uses `{moduleName}` placeholder. Replace with actual module path from user's `go.mod`.

### Template Structure

```
template/golang/
└── wf_http_client.go
```

### Key Design Points

- `WfHttpClient` struct holds config, signer, and http.Client
- Constructor takes `*config.WfConfig` and `signer.Signer` interface (dependency injection)
- `PostJSON` method handles signing, header setting, and request/response
- Business logic (request building, response parsing) stays in each API client
- Returns raw response body bytes; caller handles JSON unmarshaling

### Dependencies

```go
import (
    "{moduleName}/wf/config"
    "{moduleName}/wf/signer"
)
```

### How to Generate

1. Ask user for: project path, language (Java/Golang)
2. If Golang: read `go.mod` to get module name, replace `{moduleName}`
3. If Java: ask for base package, replace `{basePackage}`
4. Generate code based on user's actual project structure
5. Template files under `template/golang/` are for reference only
