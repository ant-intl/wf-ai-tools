# Create a Cardholder 接口接入指引

## 接口说明

注册持卡人并提交身份证明材料进入 KYC 审核。创建成功后持卡人状态固定为 `PENDING`，需通过查询接口轮询审核结果，状态达到 `ACTIVE` 后方可向其发卡。

## 官方文档

- [create_a_cardholder 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_cardholder)
- 枚举取值（`CardholderType`、`CardholderStatus`）见 [Cardholders Overview](https://docs.worldfirst.com/wfdocs/api-sdk/cardholders_overview)

## 请求地址

`POST /api/open/v1/issuing/cardholders/create`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2024-01-15T08:00:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `requestId` | string | Yes | 幂等键，最大 64 字符；每次创建请求需唯一（建议 UUID），重试须复用同一取值以免重复创建 |
| `type` | string | Yes | 持卡人类型（CardholderType 枚举），决定所上传身份材料文件的解析方式 |
| `personInfo` | PersonInfo | Yes | 持卡人个人详情，用于 KYC 审核 |
| `nationality` | string | Yes | 持卡人国籍，ISO 3166-1 alpha-3 三字母国家代码（如 `CHN`） |
| `identification` | Identification | Yes | 提交 KYC 审核的身份证明材料 |

### PersonInfo Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `userName` | UserName | Yes | 持卡人姓名，见 Data Types |
| `dateOfBirth` | string | Yes | 出生日期，格式 `yyyy-MM-dd` |

### Identification Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `fileList` | array[IdentificationFile] | Yes | 已上传的证明文件列表。`EMPLOYEE` 与 `SHAREHOLDER` 必须包含身份证明文件，关系证明可选但建议提供；万里汇按列表顺序与业务规则识别各文件 |

### IdentificationFile Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `fileKey` | string | Yes | 文件上传接口返回的文件 key；需先上传各证明材料文件，再将返回的 key 填入此处 |
| `fileName` | string | Yes | 原始文件名（含扩展名），如 `passport_front.jpg` |

### 请求示例

```json
{
  "requestId": "req_20240115_001",
  "type": "EMPLOYEE",
  "personInfo": {
    "userName": {
      "firstName": "San",
      "lastName": "Zhang"
    },
    "dateOfBirth": "1990-06-15"
  },
  "nationality": "CHN",
  "identification": {
    "fileList": [
      { "fileKey": "file_key_001", "fileName": "passport_front.jpg" },
      { "fileKey": "file_key_002", "fileName": "relationship_proof.jpg" }
    ]
  }
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 持卡人唯一标识，最大 64 字符；用于后续查询、列表、删除及发卡 |
| `type` | string | 持卡人类型，回显请求值 |
| `status` | string | 持卡人当前状态，创建后始终为 `PENDING` |
| `personInfo` | PersonInfo | 持卡人个人信息，回显请求值 |
| `nationality` | string | 持卡人国籍，ISO 3166-1 alpha-3 三字母国家代码 |
| `createdAt` | datetime | 持卡人创建时间（ISO 8601 格式） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "ch_abc123xyz",
  "type": "EMPLOYEE",
  "status": "PENDING",
  "personInfo": {
    "userName": {
      "firstName": "San",
      "lastName": "Zhang",
      "fullName": "San Zhang"
    },
    "dateOfBirth": "1990-06-15"
  },
  "nationality": "CHN",
  "createdAt": "2024-01-15T08:00:00Z"
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 创建成功 | 持卡人已创建并提交审核 |
| `PARAM_ILLEGAL` | F | 参数非法 | 核对 `type`、`nationality`（ISO 3166-1 alpha-3）与 `personInfo.dateOfBirth`（`yyyy-MM-dd`）是否符合本规范 |
| `DUPLICATE_CARDHOLDER` | F | 持卡人信息重复 | 相同身份的持卡人已存在，改用 List Cardholders 查询已有记录，不要重复创建 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败 | 联系万里汇支持确认合约状态后重试 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 | 重试前确认 `account-id` / `access-token` 授权关系有效 |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用正确的用户信息重试 |
| `RISK_REJECT` | F | 请求被风控拒绝 | 不要重试，携带 `requestId` 联系万里汇支持 |
| `UNKNOWN_EXCEPTION` | F | 未知异常 | 使用相同 `requestId` 稍后重试；若问题持续联系万里汇支持 |

## 示例代码

参考 [references/issuing/cardholders/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
cardholders/java/
├── service/
│   └── CardholderService.java                         # 薄封装 Service，包含 createCardholder 方法
└── model/
    ├── domain/
    │   ├── PersonInfo.java                            # 持卡人个人信息（userName, dateOfBirth）
    │   ├── Identification.java                        # 身份证明材料（fileList）
    │   └── IdentificationFile.java                    # 单个证明文件（fileKey, fileName）
    ├── request/
    │   └── CreateCardholderRequest.java               # 请求参数（requestId, type, personInfo, nationality, identification）
    └── response/
        └── CreateCardholderResponse.java              # 响应结果（result, id, type, status, personInfo, nationality, createdAt）
```

> `UserName`、`Result` 为通用对象，复用 `model/domain`、`model/response` 包下的已有定义。

## 集成使用方式

CardholderService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 先调用文件上传接口（`upload_a_file`）逐个上传证明材料，取得 `fileKey`
2. 构造 `CreateCardholderRequest`，设置唯一 `requestId` 及 `personInfo`、`identification`
3. 调用 `CardholderService.createCardholder(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
4. 检查 `result.resultStatus` 是否为 `S`，保存返回的 `id` 用于后续轮询与发卡

### 业务代码示例

```java
// 构造姓名
UserName userName = new UserName();
userName.setFirstName("San");
userName.setLastName("Zhang");

// 构造个人信息
PersonInfo personInfo = new PersonInfo();
personInfo.setUserName(userName);
personInfo.setDateOfBirth("1990-06-15");

// 构造身份材料文件列表（fileKey 来自文件上传接口）
IdentificationFile idCard = new IdentificationFile();
idCard.setFileKey("file_key_001");
idCard.setFileName("passport_front.jpg");

Identification identification = new Identification();
identification.setFileList(Collections.singletonList(idCard));

// 构造请求（requestId 必须唯一）
CreateCardholderRequest request = new CreateCardholderRequest();
request.setRequestId("req_20240115_001");
request.setType("EMPLOYEE");
request.setPersonInfo(personInfo);
request.setNationality("CHN");
request.setIdentification(identification);

// 调用 Service（内部已强制验签，验签失败抛 WfException）
CreateCardholderResponse response = cardholderService.createCardholder(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("持卡人 ID: " + response.getId());
    System.out.println("状态: " + response.getStatus());   // 创建后为 PENDING
    System.out.println("创建时间: " + response.getCreatedAt());
} else if ("DUPLICATE_CARDHOLDER".equals(response.getResult().getResultCode())) {
    System.out.println("该持卡人已存在，请改用查询/列表接口");
} else if ("RISK_REJECT".equals(response.getResult().getResultCode())) {
    System.out.println("风控拒绝，不要重试，联系万里汇支持");
} else {
    System.err.println("创建失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 幂等重试示例

```java
// 网络超时等场景下，使用同一 requestId 重试不会产生重复持卡人
String requestId = "req_20240115_001";
request.setRequestId(requestId);

CreateCardholderResponse response = cardholderService.createCardholder(request);

if ("U".equals(response.getResult().getResultStatus())) {
    // 结果未知：先用相同 requestId 重试，再考虑查询确认
    response = cardholderService.createCardholder(request);
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 CardholderService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new CardholderService(config)` 创建实例。
