# inquirySubuser 接口接入指引

## 接口说明

查询万里汇主账号及子账号信息，支持分页。仅主账号可调用此接口。

## 请求地址

`POST /amsin/api/v1/business/user/inquirySubuser`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601 |
| `Connected-AccountId` | Conditional | 平台客户操作商户账户时必填 |
| `Access-Token` | Conditional | 使用 OAUTH 授权时必填 |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `pageSize` | Integer | **必填** | 每页条目数 |
| `pageNumber` | Integer | **必填** | 当前页码，从 1 开始 |

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Object | `resultStatus` (S/F/U) |
| `primaryUserInformation` | Object | 主账号信息，resultStatus=S 时按需返回 |
| `userInformations` | List | 子账号信息列表，resultStatus=S 时按需返回 |
| `totalCount` | Integer | 查询结果总条目数，最大 8 字符 |
| `totalPageNumber` | Integer | 查询结果总页数，最大 8 字符 |
| `currentPageNumber` | Integer | 当前页码，最大 8 字符 |

### SubUserInfo Object（primaryUserInformation / userInformations 元素）

| Field | Type | Max Length | Description |
|-------|------|------------|-------------|
| `userId` | String | 32 | 万里汇用户 ID |
| `userName` | UserName | — | 用户姓名（主账号返回） |
| `logonId` | String | 128 | 用户登录账号 |
| `userNickName` | UserName | — | 子账号昵称（子账号返回） |
| `userAddress` | Address | — | 用户地址 |
| `userEmail` | String | 67 | 用户邮箱 |
| `userMobile` | String | 32 | 用户手机号 |

### UserName Object

| Field | Type | Max Length | Description |
|-------|------|------------|-------------|
| `firstName` | String | 32 | 名字 |
| `middleName` | String | 32 | 中间名 |
| `lastName` | String | 32 | 姓氏 |
| `fullName` | String | 96 | 全名（必填） |

### Address Object

| Field | Type | Max Length | Description |
|-------|------|------------|-------------|
| `region` | String | 2 | 国家/地区 ISO-3166 二字母代码（必填） |
| `state` | String | 8 | 省/州/郡 |
| `city` | String | 32 | 城市 |
| `address1` | String | 128 | 地址第一行 |
| `address2` | String | 128 | 地址第二行 |
| `zipCode` | String | 32 | 邮编 |

## 错误码

### 不可重试 (resultStatus=F)

`PARAM_ILLEGAL`, `INVALID_SIGNATURE`, `INVALID_API`, `INVALID_CLIENT`,
`KEY_NOT_FOUND`, `ACCESS_DENIED`, `PROCESS_FAIL`, `UN_SUPPORT_BUSINESS`,
`ACCESS_TOKEN_EXPIRED`, `INVALID_ACCESS_TOKEN`, `USER_ACCOUNT_NOT_PRIMARY`,
`AUTHORIZATION_NOT_EXIST`, `METHOD_NOT_SUPPORTED`

### 可重试 (resultStatus=U)

`UNKNOWN_EXCEPTION`, `REQUEST_TRAFFIC_EXCEED_LIMIT`

## 注意事项

- 仅**主账号**可调用此接口，子账号调用将返回 `USER_ACCOUNT_NOT_PRIMARY`
- `pageNumber` 从 1 开始，不支持 0 或负数
- `primaryUserInformation` 返回主账号信息，`userInformations` 返回当前页的子账号列表
- 子账号使用 `userNickName` 字段表示昵称，主账号使用 `userName` 字段

## 示例代码

参考同目录下 `java/` 中的模板代码。

### Java 模板结构

```
java/
└── model/
    ├── domain/
    │   ├── SubUserInfo.java      # 主/子账号用户信息
    │   ├── Address.java          # 用户地址
    │   └── （UserName.java 复用 inquiry-account 模块）
    ├── request/InquirySubuserRequest.java
    └── response/InquirySubuserResponse.java
```

> 注意：`inquirySubuser` 方法已集成到 `inquiry-balance/java/client/InquiryAccountInfoClient.java` 统一客户端中。
> `UserName` 类复用 `inquiry-account` 模块下的同名类，无需重复定义。
