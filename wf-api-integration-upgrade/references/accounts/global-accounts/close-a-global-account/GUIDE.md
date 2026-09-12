# Close a Global Account 接口接入指引

## 接口说明

永久关闭全局账户。关闭后账户无法收款且无法重新开启，仅 `ACTIVE` 状态的账户可被关闭。

## 官方文档

- [close_a_global_account 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/close_a_global_account)

## 请求地址

`POST /api/open/v1/globalAccounts/close`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2024-01-01T12:08:56+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | 要关闭的账户唯一标识 |

### 请求示例

```json
{
  "id": "2026032519121000470000009473"
}
```

## 响应参数

返回关闭后的完整账户信息，`status` 将变为 `CLOSED`。

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果 |
| `id` | string | 账户唯一标识 |
| `accountName` | string | 账户持有人合法名称 |
| `nickName` | string | 用户自定义账户标签 |
| `accountType` | string | 账户类型 |
| `status` | string | 账户状态，关闭成功后为 `CLOSED` |
| `closeReason` | string | 关户原因，`status` 为 `CLOSED` 时返回 |
| `failureReason` | string | 开户失败原因，`status` 为 `FAILED` 时返回 |
| `accountNumber` | string | 银行账号（账户此前为 `ACTIVE` 时返回） |
| `iban` | string | 国际银行账号 |
| `swiftCode` | string | SWIFT/BIC 代码 |
| `institution` | Institution | 银行机构信息 |
| `requiredFeatures` | array[RequiredFeature] | 申请的收款能力 |
| `supportedFeatures` | array[SupportedFeature] | 已开通的收款能力 |
| `createdAt` | string | 账户创建时间（ISO 8601） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "2026032519121000470000009473",
  "accountName": "Sandbox Business",
  "nickName": "USD in US for Subsidiary Company ABC",
  "accountType": "CHECKING",
  "status": "CLOSED",
  "accountNumber": "8484346726",
  "iban": "IVAJPNIUM201000002",
  "swiftCode": "CMFGUS33",
  "institution": {
    "bankAddress": "",
    "branchCode": "",
    "bankName": "Example Bank Ltd",
    "bankRegion": "US"
  },
  "requiredFeatures": [
    { "currency": "USD", "paymentType": "LOCAL" }
  ],
  "supportedFeatures": [
    {
      "type": "RECEIVING",
      "currency": "USD",
      "paymentNetwork": "ACH",
      "routingCodes": [{ "type": "ACH", "value": "026073150" }],
      "paymentType": "LOCAL"
    }
  ],
  "createdAt": "2026-03-30T12:08:56+08:00"
}
```

> 对象结构（Institution、SupportedFeature、RoutingCode）与 create_a_global_account 相同，详见 [create_a_global_account GUIDE](../create-a-global-account/GUIDE.md)。

## 错误码

| resultCode | resultStatus | 说明 | 排查建议 |
|------------|--------------|------|----------|
| `SUCCESS` | S | 成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查 `id` 是否为有效的账户标识 |
| `ACCOUNT_NOT_EXIST` | F | 账户不存在 | 验证 `id` 指向的账户是否存在 |
| `ACCOUNT_STATUS_INVALID` | F | 账户状态不支持此操作 | 仅 `ACTIVE` 账户可关闭，确认账户未已关闭或未失败 |
| `ACCOUNT_AUTHORIZATION_REQUIRED` | F | 账户未授权 | 联系支持团队获取授权 |
| `SYSTEM_ERROR` | F | 系统错误 | 重试请求，持续失败请联系支持 |
| `PERMISSION_DENIED` | F | 无权限 | 验证是否有执行此操作的权限 |

## 示例代码

参考 [references/accounts/global-accounts/java/](../java/) 中的参考实现代码。

### 集成使用方式

```java
// 构造请求
CloseGlobalAccountRequest request = new CloseGlobalAccountRequest();
request.setId("2026032519121000470000009473");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
GlobalAccountResponse response = globalAccountService.closeGlobalAccount(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("账户已关闭，状态: " + response.getStatus());
} else {
    System.err.println("关闭失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 GlobalAccountService。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new GlobalAccountService(config)` 创建实例。
