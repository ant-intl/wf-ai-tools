# inquiryBeneficiaryList 接口接入指引

## 接口说明

分页查询已绑定的收款人列表。

## 官方文档

- [inquiryBeneficiaryList 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/inquiry_beneficiary_list)

## 请求地址

`POST /amsin/api/v1/business/account/inquiryBeneficiaryList`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `pageSize` | Integer | **Yes** | 每页条数，最大 50 |
| `pageNumber` | Integer | **Yes** | 页码（从 1 开始） |
| `beneficiaryToken` | String | No | 精确匹配 |
| `beneficiaryNick` | String | No | 模糊匹配 |
| `bankAccountNo` | String | No | 模糊匹配 |
| `currencyList` | List\<String\> | No | 币种过滤 |
| `assetType` | String | No | `BANK_ACCOUNT` 或 `ALIPAY_ACCOUNT` |

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API result |
| `responseId` | String | 响应 ID |
| `beneficiaries` | List\<Beneficiary\> | 收款人列表 |
| `totalCount` | Integer | 总条数 |
| `totalPageNumber` | Integer | 总页数 |
| `currentPageNumber` | Integer | 当前页码 |

## 示例代码

### Java 模板结构

```
java/
└── model/
    ├── request/InquiryBeneficiaryListRequest.java
    └── response/InquiryBeneficiaryListResponse.java
```

### Golang 模板结构

```
golang/
└── model/
    ├── domain/
    │   └── beneficiary.go
    ├── request/
    │   └── inquiry_beneficiary_list_request.go
    └── response/
        └── inquiry_beneficiary_list_response.go
```

> **注意**: Client 实现集中在 `inquiry-template/golang/client/beneficiary_management_client.go` 中，已包含 inquiryBeneficiaryList 接口。

