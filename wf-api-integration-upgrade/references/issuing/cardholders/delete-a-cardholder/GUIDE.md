# Delete a Cardholder 接口接入指引

## 接口说明

永久删除持卡人，仅在名下已无任何关联卡片时可执行。删除不可恢复；响应仅返回调用结果，`resultStatus=S` 即表示删除成功。

## 官方文档

- [delete_a_cardholder 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/delete_a_cardholder)

## 请求地址

`POST /api/open/v1/issuing/cardholders/delete`

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
| `id` | string | Yes | 待删除的持卡人唯一标识，最大 64 字符 |

### 请求示例

```json
{
  "id": "ch_abc123xyz"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | 删除结果；`resultStatus=S` 确认持卡人已删除 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  }
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 删除成功 | 持卡人已删除 |
| `PARAM_ILLEGAL` | F | 参数非法 | 确认 `id` 已传入且格式正确 |
| `CARDHOLDER_NOT_EXIST` | F | 持卡人不存在 | ID 有误或已被删除，可通过 List Cardholders 确认 |
| `CARDHOLDER_HAS_ASSOCIATED_CARD` | F | 持卡人仍有卡片关联，无法删除 | 先取消或解绑该持卡人名下所有卡片，再重试删除 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 | 重试前确认 `account-id` / `access-token` 授权关系有效 |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用正确的用户信息重试 |
| `UNKNOWN_EXCEPTION` | F | 未知异常 | **先用 Query a Cardholder 确认删除是否已生效再决定是否重试**；若问题持续联系万里汇支持 |

## 示例代码

参考 [references/issuing/cardholders/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
cardholders/java/
├── service/
│   └── CardholderService.java                         # 薄封装 Service，包含 deleteCardholder 方法
└── model/
    ├── request/
    │   └── DeleteCardholderRequest.java               # 请求参数（id）
    └── response/
        └── DeleteCardholderResponse.java              # 响应结果（仅 result）
```

> `Result` 为通用对象，复用 `model/response` 包下的已有定义。本模块无嵌套 domain 对象。

## 集成使用方式

CardholderService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `DeleteCardholderRequest` 设置持卡人 `id`
2. 调用 `CardholderService.deleteCardholder(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S` 确认删除成功

### 业务代码示例

```java
// 构造请求
DeleteCardholderRequest request = new DeleteCardholderRequest();
request.setId("ch_abc123xyz");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
DeleteCardholderResponse response = cardholderService.deleteCardholder(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("持卡人已永久删除");
} else if ("CARDHOLDER_HAS_ASSOCIATED_CARD".equals(response.getResult().getResultCode())) {
    System.out.println("持卡人名下仍有卡片，需先取消或解绑全部卡片");
} else {
    System.err.println("删除失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 删除前置校验与结果不确定处理示例

```java
// 1. 删除前确认持卡人存在且非审核中状态
QueryCardholderRequest queryRequest = new QueryCardholderRequest();
queryRequest.setId("ch_abc123xyz");
QueryCardholderResponse queryResponse = cardholderService.queryCardholder(queryRequest);

if (!"S".equals(queryResponse.getResult().getResultStatus())) {
    System.out.println("持卡人不存在或不可操作，终止删除");
    return;
}

// 2. 执行删除
DeleteCardholderRequest deleteRequest = new DeleteCardholderRequest();
deleteRequest.setId("ch_abc123xyz");
DeleteCardholderResponse deleteResponse = cardholderService.deleteCardholder(deleteRequest);

// 3. 结果不确定时，以查询结果为准，不要盲目重试（删除不可恢复）
if (!"S".equals(deleteResponse.getResult().getResultStatus())) {
    QueryCardholderResponse confirm = cardholderService.queryCardholder(queryRequest);
    if ("CARDHOLDER_NOT_EXIST".equals(confirm.getResult().getResultCode())) {
        System.out.println("复查确认：持卡人实际已删除成功");
    } else {
        System.out.println("复查确认：删除未生效，可按业务策略重试");
    }
}
```

> ⛔ 删除为永久操作，不存在软删除或回滚；`UNKNOWN_EXCEPTION` 等结果不确定场景必须先查询确认，避免重复删除或对已删除对象继续发卡。

> **Spring Boot 项目**：通过 `@Autowired` 注入 CardholderService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new CardholderService(config)` 创建实例。
