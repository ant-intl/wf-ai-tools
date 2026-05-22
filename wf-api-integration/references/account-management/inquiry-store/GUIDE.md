# inquiryStore 接口接入指引

## 接口说明

集成商可调用此接口获取店铺信息以及店铺关联账号的信息。

## 官方文档

- [inquiryStore 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_store)

## 请求地址

`POST /amsin/api/v1/business/store/inquiryStore`

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
| `pageSize` | Integer | **必填** | 每页包含的条目数 |
| `pageNumber` | Integer | **必填** | 当前页码，从 1 开始 |

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | `resultStatus` (S/F/U) |
| `storeInformation` | List\<StoreInfo\> | 店铺信息列表，resultStatus=S 时按需返回 |
| `totalCount` | Integer | 查询结果总条目数，最大 8 字符，resultStatus=S 时返回 |
| `totalPageNumber` | Integer | 查询结果总页数，最大 8 字符，resultStatus=S 时返回 |
| `currentPageNumber` | Integer | 当前页码，最大 8 字符，resultStatus=S 时返回 |

### StoreInfo Object

| Field | Type | Description |
|-------|------|-------------|
| `storeName` | String | 店铺名称 |
| `marketplaceName` | String | 平台名称 |
| `authorizedStatus` | String | 店铺授权状态：`AUTHORIZED`（已授权）、`NEVER_AUTHORIZED`（未授权） |
| `accountInformation` | List\<AccountInfo\> | 店铺账号信息 |

### AccountInfo Object

| Field | Type | Description |
|-------|------|-------------|
| `accountNo` | String | 万里汇收款账户号码，当 accountType 为 VIRTUAL_ACCOUNT 或 RECEIVE_ACCOUNT 时必传。最大 64 字符 |
| `currencyList` | List\<String\> | 账户币种列表（ISO-4217）。支持：USD, EUR, GBP, NZD, CAD, AUD, JPY, SGD, HKD, CNH, PLN, SEK, MXN |
| `accountType` | String | 查询账号类型：`RECEIVE_ACCOUNT`、`VIRTUAL_ACCOUNT`、`ALIPAY_WALLET` |
| `accountStatus` | String | 账户状态：`ACTIVE`（已激活）、`ABNORMAL`（异常） |
| `bankAccountList` | List\<BankAccount\> | 银行账户信息，当 accountType 为 VIRTUAL_ACCOUNT 时必传 |

### BankAccount Object

| Field | Type | Description |
|-------|------|-------------|
| `bankAccountNo` | String | 银行账号。最大 64 字符 |
| `holderName` | UserName | 账号户主姓名 |
| `holderAccountType` | String | 户主账户类型：`INDIVIDUAL`、`COMPANY` |
| `holderAddress` | Address | 户主地址 |
| `currencyList` | List\<String\> | VA 对应币种列表 |
| `bankName` | String | 银行名称。最大 128 字符 |
| `routingNumber` | String | 汇款路径代码（USD+US 时必填）。最大 64 字符 |
| `bankAddress` | Address | 银行地址 |
| `bankRegion` | String | 银行所在国家/地区（ISO-3166 二字母）。最大 2 字符 |
| `bankBIC` | String | 银行 BIC 代码（8-11 位）。最大 11 字符 |
| `bankAccountIBAN` | String | IBAN（EUR+EU 或 GBP+GB 时必填）。最大 34 字符 |
| `bankAccountBSB` | String | BSB 号码（AUD+AU 或 NZD+NZ 时必填）。最大 128 字符 |
| `accountCreationDate` | String | 账号创建时间 |
| `bankCode` | String | 银行代码。最大 128 字符 |
| `branchCode` | String | 银行分行代码。最大 128 字符 |
| `bankAccountType` | String | 银行账户类型：`checking`、`saving`（JPY/CAD 时必填）。最大 34 字符 |
| `collectionArea` | String | 收款地区：`GLOBAL`、`LOCAL`（AUD/NZD 时必填）。最大 34 字符 |
| `wireRoutingNumber` | String | Wire 汇款路线号码（USD+US 时必填）。最大 64 字符 |
| `sortCode` | String | Sort Code（GBP+GB 或 EUR+GB 时必填）。固定 6 字符 |

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
| `region` | String | 2 | 国家/地区 ISO-3166 二字母代码 |
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

`UNKNOWN_EXCEPTION`

## 请求示例

```json
{
  "pageSize": 10,
  "pageNumber": 1
}
```

## 响应示例

```json
{
  "currentPageNumber": 1,
  "result": {
    "resultStatus": "S",
    "resultCode": "SUCCESS",
    "resultMessage": "success."
  },
  "storeInformation": [
    {
      "accountInformation": [
        {
          "accountNo": "***********",
          "accountStatus": "ACTIVE",
          "accountType": "VIRTUAL_ACCOUNT",
          "bankAccountList": [
            {
              "accountCreationDate": "2023-06-19",
              "bankAccountNo": "*******8396",
              "bankAddress": {
                "address1": "***************************************",
                "region": "HK"
              },
              "bankBIC": "CHASHKHH",
              "bankName": "JP Morgan Chase HONG KONG BRANCH",
              "collectionArea": "GLOBAL",
              "currencyList": ["AUD"],
              "holderName": {
                "fullName": "Z***g"
              }
            }
          ],
          "currencyList": ["AUD"]
        }
      ],
      "authorizedStatus": "NEVER_AUTHORIZED",
      "marketplaceName": "SameName Topup",
      "storeName": "-"
    }
  ],
  "totalCount": 281,
  "totalPageNumber": 29
}
```

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
└── model/
    ├── domain/
    │   └── StoreInfo.java        # 店铺信息
    │   └── （AccountInfo.java、BankAccount.java、UserName.java、Address.java 复用 inquiry-account 模块）
    ├── request/InquiryStoreRequest.java
    └── response/InquiryStoreResponse.java
```

> 注意：`inquiryStore` 方法已集成到 `inquiry-balance/java/client/InquiryAccountInfoClient.java` 统一客户端中。
> `AccountInfo`、`BankAccount`、`UserName`、`Address` 等类复用 `inquiry-account` 模块下的同名类，无需重复定义。

### Golang 模板结构

```
golang/
└── model/
    ├── domain/
    │   └── store_info.go         # 店铺信息
    ├── request/inquiry_store_request.go
    └── response/inquiry_store_response.go
```

## 注意事项

1. **分页参数**：`pageNumber` 从 1 开始，不支持 0 或负数
2. **店铺授权状态**：`authorizedStatus` 取值为 `AUTHORIZED`（已授权）或 `NEVER_AUTHORIZED`（未授权）
3. **accountInformation 复用**：店铺关联的账号信息结构与 `inquiryAccount` 接口返回的 `AccountInfo` 一致
4. **重试策略**：遇到 `U` 状态时建议实现指数退避重试机制，最多重试 7 次，时间间隔：5分钟、10分钟、20分钟、40分钟、80分钟、160分钟、320分钟
