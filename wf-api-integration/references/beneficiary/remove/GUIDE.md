# removeBeneficiary 接口接入指引

## 接口说明

删除已绑定的收款人。

## 官方文档

- [removeBeneficiary 官方文档](https://developers.worldfirst.com/docs/alipay-worldfirst/worldfirst_enterprise_service/remove_beneficiary)

## 请求地址

`POST /amsin/api/v1/business/account/removeBeneficiary`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `removeBeneficiaryRequestId` | String | **Yes** | 幂等请求 ID，最大 64 字符 |
| `beneficiaryToken` | String | **Yes** | 收款人令牌（Base64），最大 128 字符 |

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API result |
| `beneficiaryToken` | String | 被删除的收款人令牌 |

## 示例代码

```
java/
└── model/
    ├── request/RemoveBeneficiaryRequest.java
    └── response/RemoveBeneficiaryResponse.java
```

