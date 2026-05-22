# inquiryAvailableQuota 接口使用指南

## 接口说明

`inquiryAvailableQuota` 用于查询可申报的结汇额度，支持四种额度累计方式。

## 官方文档

- [inquiryAvailableQuota 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_available_quota)

## 接口路径

```
POST /amsin/api/v1/business/account/inquiryAvailableQuota
```

## 请求参数


| 字段                    | 类型   | 必填     | 说明                                                                            |
| ----------------------- | ------ | -------- | ------------------------------------------------------------------------------- |
| quotaAccumulationMethod | String | 是       | 结汇额度累计方式：`USER_ID`/`RECEIVING_ACCOUNT`/`VIRTUAL_ACCOUNT`/`BENEFICIARY` |
| quotaAccumulationId     | String | 是       | 累计方式标识（用户ID/RA号/VA号/外部平台用户ID）                                 |
| currency                | String | 是       | 币种（ISO 4217标准，如"USD"）                                                   |
| tradeType               | String | 条件必填 | 贸易类型：`GOODS`（货物）/`SERVICE`（服务）；`BENEFICIARY`时必传                |

## 响应参数


| 字段                    | 类型   | 说明                                    |
| ----------------------- | ------ | --------------------------------------- |
| result                  | Result | API调用结果                             |
| quotaAccumulationMethod | String | 结汇额度累计方式                        |
| quotaAccumulationId     | String | 累计方式标识                            |
| availableQuota          | Amount | 可申报的结汇额度（包含currency和value） |
| tradeType               | String | 贸易类型（`BENEFICIARY`时返回）         |

## 使用示例

### Java

```java
// 创建客户端（统一客户端，位于 inquiry-balance 模块下）
InquiryAccountInfoClient client = new InquiryAccountInfoClient();
client.setConfig(wfConfig);
client.init();

// 构建请求
InquiryAvailableQuotaRequest request = new InquiryAvailableQuotaRequest();
request.setQuotaAccumulationMethod("USER_ID");
request.setQuotaAccumulationId("YOUR_USER_ID");
request.setCurrency("USD");

// 调用接口
InquiryAvailableQuotaResponse response = client.inquiryAvailableQuota(request);

// 处理响应
if (response.isSuccess()) {
    System.out.println("可申报额度: " + response.getAvailableQuota().getValue() 
        + " " + response.getAvailableQuota().getCurrency());
}
```

> 注意：`InquiryAccountInfoClient` 位于 `inquiry-balance/` 模块下，
> 同时包含 `inquiryBalance` 和 `inquiryAvailableQuota` 两个方法。

### Golang

```go
// 创建客户端（统一客户端，位于 inquiry-balance 模块下）
cfg := config.NewWfConfig(clientID, userID, baseURL, privateKeyPath, publicKeyPath)
s, _ := signer.NewWfSigner(privateKeyPath, publicKeyPath)
client := client.NewInquiryAccountInfoClient(util.NewWfHttpClient(cfg, s))

// 构建请求
req := &request.InquiryAvailableQuotaRequest{
    QuotaAccumulationMethod: "USER_ID",
    QuotaAccumulationId:     "YOUR_USER_ID",
    Currency:                "USD",
}

// 调用接口
resp, err := client.InquiryAvailableQuota(req)
if err != nil {
    log.Fatal(err)
}

// 处理响应
if resp.IsSuccess() {
    fmt.Printf("可申报额度: %d %s\n", 
        resp.AvailableQuota.Value, 
        resp.AvailableQuota.Currency)
}
```

### Java 模板结构

```
java/
└── model/
    ├── request/InquiryAvailableQuotaRequest.java
    └── response/InquiryAvailableQuotaResponse.java
```

> 注意：`InquiryAccountInfoClient` 位于 `inquiry-balance/java/client/` 下。

### Golang 模板结构

```
golang/
└── model/
    ├── request/inquiry_available_quota_request.go
    └── response/inquiry_available_quota_response.go
```

> 注意：`InquiryAccountInfoClient` 位于 `inquiry-balance/golang/client/` 下。

## 四种累计方式说明

### 1. USER_ID（按用户ID累计）

适用于：查询指定用户的结汇额度

```json
{
  "quotaAccumulationMethod": "USER_ID",
  "quotaAccumulationId": "用户ID",
  "currency": "USD"
}
```

### 2. RECEIVING_ACCOUNT（按收款账户累计）

适用于：查询指定收款账户（RA）的结汇额度

```json
{
  "quotaAccumulationMethod": "RECEIVING_ACCOUNT",
  "quotaAccumulationId": "RA号",
  "currency": "USD"
}
```

### 3. VIRTUAL_ACCOUNT（按虚拟账户累计）

适用于：查询指定虚拟账户（VA）的结汇额度

```json
{
  "quotaAccumulationMethod": "VIRTUAL_ACCOUNT",
  "quotaAccumulationId": "VA号",
  "currency": "USD"
}
```

### 4. BENEFICIARY（按收款人累计）

适用于：查询指定收款人的结汇额度，**必须传入 tradeType**

```json
{
  "quotaAccumulationMethod": "BENEFICIARY",
  "quotaAccumulationId": "外部平台用户ID",
  "currency": "USD",
  "tradeType": "GOODS"
}
```

## 错误处理

### 参数校验错误

- `PARAM_ILLEGAL`: 必填参数为空或格式不正确
- 当 `quotaAccumulationMethod=BENEFICIARY` 时，必须传入 `tradeType`

### 响应状态

- `S`: 成功，返回可申报额度
- `F`: 失败，根据 resultCode 处理
- `U`: 未知/可重试状态，建议最多重试7次，间隔5/10/20/40/80/160/320分钟

## 注意事项

1. **重试策略**: 遇到 `U` 状态时建议实现指数退避重试机制
