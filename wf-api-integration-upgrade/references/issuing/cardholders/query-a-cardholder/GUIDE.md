# Query a Cardholder 接口接入指引

## 接口说明

通过持卡人 ID 查询持卡人详情与审核状态。创建持卡人后轮询本接口即可获知 KYC 审核结果：状态达到 `ACTIVE` 表示可发卡，`FAILED` 表示审核未通过或已被拒绝。

## 官方文档

- [query_a_cardholder 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_cardholder)
- 枚举取值（`CardholderType`、`CardholderStatus`）见 [Cardholders Overview](https://docs.worldfirst.com/wfdocs/api-sdk/cardholders_overview)

## 请求地址

`POST /api/open/v1/issuing/cardholders/query`

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
| `id` | string | Yes | 持卡人唯一标识，由 create_a_cardholder 返回，最大 64 字符 |

### 请求示例

```json
{
  "id": "ch_abc123xyz"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 持卡人唯一标识，最大 64 字符 |
| `type` | string | 持卡人类型（CardholderType 枚举） |
| `status` | string | 持卡人当前状态（CardholderStatus 枚举），创建后轮询此字段获知审核结果 |
| `personInfo` | PersonInfo | 持卡人个人信息 |
| `nationality` | string | 持卡人国籍，ISO 3166-1 alpha-3 三字母国家代码 |
| `createdAt` | datetime | 持卡人创建时间（ISO 8601 格式） |

### PersonInfo Object

| Field | Type | Description |
|-------|------|-------------|
| `userName` | UserName | 持卡人姓名，见 Data Types |
| `dateOfBirth` | string | 出生日期，格式 `yyyy-MM-dd` |

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
  "status": "ACTIVE",
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
| `SUCCESS` | S | 查询成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 确认 `id` 已传入且格式正确 |
| `CARDHOLDER_NOT_EXIST` | F | 持卡人不存在 | ID 有误或该持卡人已被删除，可通过 List Cardholders 确认 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 | 重试前确认 `account-id` / `access-token` 授权关系有效 |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用正确的用户信息重试 |
| `UNKNOWN_EXCEPTION` | F | 未知异常 | 稍后重试；若问题持续联系万里汇支持 |

## 示例代码

参考 [references/issuing/cardholders/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
cardholders/java/
├── service/
│   └── CardholderService.java                         # 薄封装 Service，包含 queryCardholder 方法
└── model/
    ├── domain/
    │   └── PersonInfo.java                            # 持卡人个人信息（userName, dateOfBirth）
    ├── request/
    │   └── QueryCardholderRequest.java                # 请求参数（id）
    └── response/
        └── QueryCardholderResponse.java               # 响应结果（result, id, type, status, personInfo, nationality, createdAt）
```

> `UserName`、`Result` 为通用对象，复用 `model/domain`、`model/response` 包下的已有定义。

## 集成使用方式

CardholderService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryCardholderRequest` 设置持卡人 `id`
2. 调用 `CardholderService.queryCardholder(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，根据 `status` 判断能否发卡

### 业务代码示例

```java
// 构造请求
QueryCardholderRequest request = new QueryCardholderRequest();
request.setId("ch_abc123xyz");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryCardholderResponse response = cardholderService.queryCardholder(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("持卡人 ID: " + response.getId());
    System.out.println("类型: " + response.getType());
    System.out.println("状态: " + response.getStatus());
    System.out.println("姓名: " + response.getPersonInfo().getUserName().getFullName());
    System.out.println("出生日期: " + response.getPersonInfo().getDateOfBirth());
    System.out.println("国籍: " + response.getNationality());

    if ("ACTIVE".equals(response.getStatus())) {
        // 审核通过，可继续发卡
    } else if ("PENDING".equals(response.getStatus())) {
        // 审核中，稍后继续轮询
    } else if ("FAILED".equals(response.getStatus())) {
        // 审核失败或已拒绝，终态
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### KYC 审核结果轮询示例

```java
// 创建持卡人后轮询状态，直至进入终态（ACTIVE / FAILED）
String cardholderId = createResponse.getId();
QueryCardholderRequest request = new QueryCardholderRequest();
request.setId(cardholderId);

int maxRetries = 60;
int intervalSeconds = 30;

for (int i = 0; i < maxRetries; i++) {
    QueryCardholderResponse response = cardholderService.queryCardholder(request);

    if ("S".equals(response.getResult().getResultStatus())) {
        String status = response.getStatus();
        System.out.println("当前状态: " + status);

        if ("ACTIVE".equals(status)) {
            // 审核通过，可发起发卡
            break;
        } else if ("FAILED".equals(status)) {
            // 终态：审核失败或被拒绝，停止轮询并转人工处理
            break;
        }
        // PENDING 继续轮询
    }

    Thread.sleep(intervalSeconds * 1000L);
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 CardholderService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new CardholderService(config)` 创建实例。
