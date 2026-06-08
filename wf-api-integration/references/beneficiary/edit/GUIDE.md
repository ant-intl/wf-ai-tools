# editBeneficiary 接口接入指引

## 接口说明

修改收款人昵称。

## 官方文档

- [editBeneficiary 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/edit_beneficiary)

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

### Java 模板结构

```
java/
└── model/
    ├── request/EditBeneficiaryRequest.java
    └── response/EditBeneficiaryResponse.java
```

### Golang 模板结构

```
golang/
└── model/
    ├── request/
    │   └── edit_beneficiary_request.go
    └── response/
        └── edit_beneficiary_response.go
```

> **注意**: Client 实现集中在 `inquiry-template/golang/client/beneficiary_management_client.go` 中，已包含 editBeneficiary 接口。

