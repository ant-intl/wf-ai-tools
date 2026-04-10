# inquiryBeneficiaryList 接口接入指引

## 接口说明

分页查询已绑定的收款人列表。

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

```
java/
└── model/
    ├── request/InquiryBeneficiaryListRequest.java
    └── response/InquiryBeneficiaryListResponse.java
```

