# Create a Global Account 接口接入指引

## 接口说明

开通全球收款账户。支持指定银行地区和收款能力（币种 + 支付方式），账户开通后可用于本地清算和跨境汇款收款。

## 官方文档

- [create_a_global_account 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_global_account)

## 请求地址

`POST /api/open/v1/globalAccounts/create`

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
| `requestId` | string | Yes | 幂等键，唯一请求标识。每次操作使用唯一值（如 UUID） |
| `bankRegion` | string | Yes | 银行所在地区（ISO 3166），如 `US`、`GB`、`HK` |
| `requiredFeatures` | array[RequiredFeature] | Yes | 申请的收款能力列表，每个元素指定一种币种 + 支付方式组合，最多 10 个 |
| `nickName` | string | No | 用户自定义账户标签，不显示在银行对账单上。不提供时默认使用商户名称 |

### RequiredFeature Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `currency` | string | Yes | 货币代码（ISO-4217），如 `USD`、`EUR` |
| `paymentType` | string | Yes | 支付方式：`LOCAL`（本地清算）或 `CROSS`（跨境汇款） |

### 请求示例

```json
{
  "requestId": "f18d0117-095a-4782-a8f1-eeb16c3be3b4",
  "bankRegion": "US",
  "nickName": "USD in US for Subsidiary Company ABC",
  "requiredFeatures": [
    { "currency": "USD", "paymentType": "LOCAL" },
    { "currency": "USD", "paymentType": "CROSS" }
  ]
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 账户唯一标识，用于后续查询、更新、关闭操作 |
| `accountName` | string | 账户持有人合法名称（银行登记名称） |
| `nickName` | string | 用户自定义账户标签 |
| `accountType` | string | 账户类型，目前仅支持 `CHECKING` |
| `status` | string | 账户状态：`PROCESSING`、`ACTIVE`、`FAILED`、`CLOSED` |
| `failureReason` | string | 开户失败原因，`status` 为 `FAILED` 时返回 |
| `accountNumber` | string | 银行账号（用于本地收款），`status` 为 `ACTIVE` 时返回 |
| `iban` | string | 国际银行账号（IBAN），适用于支持 IBAN 的地区 |
| `swiftCode` | string | SWIFT/BIC 代码（用于跨境汇款） |
| `institution` | Institution | 银行机构信息 |
| `requiredFeatures` | array[RequiredFeature] | 申请的收款能力（回显） |
| `supportedFeatures` | array[SupportedFeature] | 已开通的收款能力，`status` 为 `ACTIVE` 时返回 |
| `createdAt` | string | 账户创建时间（ISO 8601） |

### Institution Object

| Field | Type | Description |
|-------|------|-------------|
| `bankAddress` | string | 银行地址 |
| `branchCode` | string | 支行代码 |
| `bankName` | string | 银行名称 |
| `bankRegion` | string | 银行所在地区（ISO 3166） |

### SupportedFeature Object

| Field | Type | Description |
|-------|------|-------------|
| `type` | string | 能力类型，如 `RECEIVING` |
| `currency` | string | 货币代码 |
| `paymentNetwork` | string | 支付网络，如 `ACH`、`SWIFT`、`SEPA` |
| `routingCodes` | array[RoutingCode] | 路由码列表 |
| `paymentType` | string | 支付方式：`LOCAL` 或 `CROSS` |

### RoutingCode Object

| Field | Type | Description |
|-------|------|-------------|
| `type` | string | 路由码类型，如 `ACH`、`SWIFT`、`SORT_CODE` |
| `value` | string | 路由码值 |

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
  "status": "ACTIVE",
  "accountNumber": "8484346726",
  "iban": "GB82XXXX0000012345678900",
  "swiftCode": "CMFGUS33",
  "institution": {
    "bankAddress": "",
    "branchCode": "",
    "bankName": "Example Bank Ltd",
    "bankRegion": "US"
  },
  "requiredFeatures": [
    { "currency": "USD", "paymentType": "LOCAL" },
    { "currency": "USD", "paymentType": "CROSS" }
  ],
  "supportedFeatures": [
    {
      "type": "RECEIVING",
      "currency": "USD",
      "paymentNetwork": "ACH",
      "routingCodes": [{ "type": "ACH", "value": "026073150" }],
      "paymentType": "LOCAL"
    },
    {
      "type": "RECEIVING",
      "currency": "USD",
      "paymentNetwork": "SWIFT",
      "routingCodes": [{ "type": "SWIFT", "value": "CMFGUS33" }],
      "paymentType": "CROSS"
    }
  ],
  "createdAt": "2026-03-30T12:08:56+08:00"
}
```

## 错误码

| resultCode | resultStatus | 说明 | 排查建议 |
|------------|--------------|------|----------|
| `SUCCESS` | S | 成功 | — |
| `INVALID_ARGUMENT` | F | 参数非法 | 检查 `bankRegion` 是否为 ISO 3166 格式，`requiredFeatures` 是否为空，`paymentType` 是否支持 |
| `ACCOUNT_STATUS_INVALID` | F | 账户状态不支持此操作 | 仅 ACTIVE 或 PROCESSING 账户可修改 |
| `REGION_NOT_SUPPORTED` | F | 地区不支持 | 检查 `bankRegion` 是否为支持的 ISO 3166 两位代码 |
| `ACCOUNT_VERIFICATION_REQUIRED` | F | 需完成账户验证 | 先完成账户验证再开通全局账户 |
| `REPEAT_REQUEST` | F | requestId 已被使用 | 每次请求使用唯一的 `requestId`（如 UUID） |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查 `bankRegion` 格式，`requiredFeatures` 至少一个元素 |
| `ACCOUNT_AUTHORIZATION_REQUIRED` | F | 账户未授权 | 联系支持团队授予访问权限 |
| `PERMISSION_DENIED` | F | 无权限 | 验证是否有执行此操作的权限 |

## 示例代码

参考 [references/accounts/global-accounts/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
global-accounts/java/
├── service/
│   └── GlobalAccountService.java              # 薄封装 Service，包含 createGlobalAccount 方法
└── model/
    ├── domain/
    │   ├── GlobalAccount.java                 # 全局账户对象
    │   ├── Institution.java                   # 银行机构信息
    │   ├── RequiredFeature.java               # 申请的收款能力
    │   ├── SupportedFeature.java              # 已开通的收款能力
    │   └── RoutingCode.java                   # 路由码
    ├── request/
    │   └── CreateGlobalAccountRequest.java    # 请求参数
    └── response/
        └── GlobalAccountResponse.java          # 响应结果
```

## 集成使用方式

GlobalAccountService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `CreateGlobalAccountRequest` 设置 requestId、bankRegion 和 requiredFeatures
2. 调用 `GlobalAccountService.createGlobalAccount(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理账户数据

### 业务代码示例

```java
// 构造请求
CreateGlobalAccountRequest request = new CreateGlobalAccountRequest();
request.setRequestId(UUID.randomUUID().toString());
request.setBankRegion("US");
request.setNickName("USD in US for Subsidiary Company ABC");

List<RequiredFeature> features = new ArrayList<>();
features.add(new RequiredFeature("USD", "LOCAL"));
features.add(new RequiredFeature("USD", "CROSS"));
request.setRequiredFeatures(features);

// 调用 Service（内部已强制验签，验签失败抛 WfException）
GlobalAccountResponse response = globalAccountService.createGlobalAccount(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("账户ID: " + response.getId());
    System.out.println("状态: " + response.getStatus());
    System.out.println("账号: " + response.getAccountNumber());
} else {
    System.err.println("创建失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 GlobalAccountService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new GlobalAccountService(config)` 创建实例。
