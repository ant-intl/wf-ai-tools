# editBeneficiary 接口接入指引

## 接口说明

修改收款人昵称。

## 请求地址

`POST /amsin/api/v1/business/account/editBeneficiary`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `beneficiaryToken` | String | **Yes** | 收款人令牌（Base64），最大 128 字符 |
| `beneficiaryNick` | String | **Yes** | 新昵称，最大 70 字符 |

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API result |
| `beneficiaryToken` | String | 收款人令牌 |

## 示例代码

```
java/
└── model/
    ├── request/EditBeneficiaryRequest.java
    └── response/EditBeneficiaryResponse.java
```

