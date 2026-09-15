# Delete a Beneficiary 接口接入指引

## 接口说明

永久删除收款人。删除后 ID 不可在代发请求中复用。

## 官方文档

- [delete_a_beneficiary 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/delete_a_beneficiary)

## 请求地址

`POST /api/open/v1/beneficiaries/delete`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | 收款人 ID |

## 示例代码

参考 [references/payouts/beneficiaries/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
beneficiaries/java/
├── service/
│   └── BeneficiaryService.java              # 薄封装 Service，包含 deleteBeneficiary 方法
└── model/
    ├── domain/
    │   ├── Beneficiary.java                 # 收款人对象
    │   ├── BankDetail.java                  # 银行账户详情
    │   └── WalletDetail.java                # 钱包账户详情
    ├── request/
    │   └── DeleteBeneficiaryRequest.java    # 删除请求
    └── response/
        └── BeneficiaryResponse.java         # 响应结果
```

## 集成使用方式

BeneficiaryService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `DeleteBeneficiaryRequest` 设置收款人 ID
2. 调用 `BeneficiaryService.deleteBeneficiary(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，确认删除结果

### 业务代码示例

```java
// 构造请求
DeleteBeneficiaryRequest request = new DeleteBeneficiaryRequest();
request.setId("202604011001010101");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
BeneficiaryResponse response = beneficiaryService.deleteBeneficiary(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("删除成功，状态: " + response.getStatus());
} else {
    System.err.println("删除失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BeneficiaryService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BeneficiaryService(config)` 创建实例。
