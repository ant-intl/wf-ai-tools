# List Cardholders 接口接入指引

## 接口说明

分页查询当前账户下的持卡人列表，可按持卡人状态过滤。采用游标分页，适用于持卡人台账核对、批量筛选待审核或已通过持卡人等场景。

## 官方文档

- [list_cardholders 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/list_cardholders)
- 枚举取值（`CardholderType`、`CardholderStatus`）见 [Cardholders Overview](https://docs.worldfirst.com/wfdocs/api-sdk/cardholders_overview)

## 请求地址

`POST /api/open/v1/issuing/cardholders/list`

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
| `status` | string | No | 按持卡人状态过滤（CardholderStatus 枚举）；不传时返回全部状态 |
| `limit` | integer | Yes | 每页记录数，取值范围 1-50；不传时默认 20 |
| `cursor` | string | No | 分页游标；首次请求省略，后续传入上次响应的 `nextCursor` |

### 游标分页说明

- **首次请求**：不传 `cursor`，API 返回 `nextCursor`
- **后续请求**：将上次响应的 `nextCursor` 作为 `cursor` 传入
- **最后一页**：响应中**不返回** `nextCursor`，以其缺失作为结果集结束的标志
- **totalCount**：仅在查询条件允许时返回，**不可**用于分页控制

### 请求示例

```json
{
  "status": "ACTIVE",
  "limit": 20
}
```

> 带游标的后续请求示例：

```json
{
  "status": "ACTIVE",
  "limit": 20,
  "cursor": "eyJsYXN0SWQiOiJjaF9hYmMxMjN4eXoifQ=="
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `items` | array[CardholderRecord] | 当前页持卡人列表，无匹配时返回空列表 |
| `nextCursor` | string | 下一页游标，未返回表示已无更多数据 |
| `prevCursor` | string | 上一页游标 |
| `totalCount` | integer | 匹配的持卡人总数，仅在查询条件允许时返回 |

### CardholderRecord Object

字段与 query_a_cardholder 响应一致。

| Field | Type | Description |
|-------|------|-------------|
| `id` | string | 持卡人唯一标识，最大 64 字符 |
| `type` | string | 持卡人类型（CardholderType 枚举） |
| `status` | string | 持卡人当前状态（CardholderStatus 枚举） |
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
  "items": [
    {
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
  ],
  "nextCursor": "eyJsYXN0SWQiOiJjaF9hYmMxMjN4eXoifQ==",
  "totalCount": 42
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 确认 `limit` 在 1-50 区间且 `status` 为支持的取值 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 | 重试前确认 `account-id` / `access-token` 授权关系有效 |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用正确的用户信息重试 |
| `UNKNOWN_EXCEPTION` | F | 未知异常 | 稍后重试；若问题持续联系万里汇支持 |

## 示例代码

参考 [references/issuing/cardholders/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
cardholders/java/
├── service/
│   └── CardholderService.java                         # 薄封装 Service，包含 listCardholders 方法
└── model/
    ├── domain/
    │   ├── PersonInfo.java                            # 持卡人个人信息（userName, dateOfBirth）
    │   └── CardholderRecord.java                      # 持卡人记录（用于 list 响应的 items）
    ├── request/
    │   └── ListCardholdersRequest.java                # 请求参数（status, limit, cursor）
    └── response/
        └── ListCardholdersResponse.java               # 响应结果（result, items, nextCursor, prevCursor, totalCount）
```

> `UserName`、`Result` 为通用对象，复用 `model/domain`、`model/response` 包下的已有定义。

## 集成使用方式

CardholderService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `ListCardholdersRequest` 设置 `limit` 与可选的 `status`（首次请求不传 `cursor`）
2. 调用 `CardholderService.listCardholders(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理 `items`
4. 若 `nextCursor` 不为 `null`，将其作为 `cursor` 传入下一次请求，重复步骤 1-3

### 业务代码示例

```java
// 构造请求
ListCardholdersRequest request = new ListCardholdersRequest();
request.setLimit(20);
request.setStatus("ACTIVE");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
ListCardholdersResponse response = cardholderService.listCardholders(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    for (CardholderRecord holder : response.getItems()) {
        System.out.println("持卡人 ID: " + holder.getId());
        System.out.println("类型: " + holder.getType());
        System.out.println("状态: " + holder.getStatus());
        System.out.println("姓名: " + holder.getPersonInfo().getUserName().getFullName());
    }
    System.out.println("总数（可能为空）: " + response.getTotalCount());

    // 游标分页：nextCursor 缺失即表示已到末尾
    if (response.getNextCursor() != null) {
        request.setCursor(response.getNextCursor());
        // 再次调用 cardholderService.listCardholders(request) 获取下一页
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 游标分页完整遍历示例

```java
List<CardholderRecord> allCardholders = new ArrayList<>();
String cursor = null;

do {
    ListCardholdersRequest request = new ListCardholdersRequest();
    request.setLimit(50);
    request.setStatus("PENDING");
    if (cursor != null) {
        request.setCursor(cursor);
    }

    // 调用 Service（内部已强制验签，验签失败抛 WfException）
    ListCardholdersResponse response = cardholderService.listCardholders(request);
    if (!"S".equals(response.getResult().getResultStatus())) {
        throw new RuntimeException("查询失败: " + response.getResult().getResultMessage());
    }

    allCardholders.addAll(response.getItems());
    cursor = response.getNextCursor();
} while (cursor != null);

System.out.println("共查询到 " + allCardholders.size() + " 位待审核持卡人");
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 CardholderService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new CardholderService(config)` 创建实例。
