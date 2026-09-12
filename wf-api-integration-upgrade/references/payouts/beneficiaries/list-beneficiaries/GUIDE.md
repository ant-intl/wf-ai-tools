# List Beneficiaries 接口接入指引

## 接口说明

分页查询收款人列表，支持多维度过滤。使用游标分页，支持向前/向后翻页。

## 官方文档

- [list_beneficiaries 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/list_beneficiaries)

## 请求地址

`POST /api/open/v1/beneficiaries/list`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `limit` | integer | No | 每页记录数，范围 1–100，默认 `20` |
| `cursor` | string | No | 分页游标，取自上次响应的 `nextCursor` 或 `prevCursor`，首次请求不传 |
| `accountType` | string | No | 按账户类型过滤：`BANK_ACCOUNT`、`DIGITAL_WALLET` |
| `entityType` | string | No | 按实体类型过滤：`COMPANY`、`PERSONAL` |
| `relationType` | string | No | 按关系类型过滤：`SAME_NAME`、`THIRD_PARTY`、`RELATED_MERCHANT` |
| `paymentType` | string | No | 按支付方式过滤：`LOCAL`、`CROSS` |
| `nickname` | string | No | 按显示名称过滤，支持模糊匹配，最多 128 字符 |
| `accountName` | string | No | 按账户持有人姓名过滤 |
| `accountNumber` | string | No | 按银行账号过滤，支持模糊匹配 |
| `iban` | string | No | 按 IBAN 过滤 |
| `certificateNo` | string | No | 按证件号过滤 |
| `currencyList` | array[string] | No | 按币种过滤，每个元素为 ISO 4217 三字母代码，最多 20 个元素 |

### 请求示例

```json
{
  "limit": 10,
  "accountType": "BANK_ACCOUNT",
  "entityType": "PERSONAL",
  "relationType": "THIRD_PARTY"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果，见 [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result) |
| `items` | array[Beneficiary] | 收款人列表，每页最多 100 条 |
| `nextCursor` | string | 下一页游标，最后一页时为 `null` |
| `prevCursor` | string | 上一页游标，第一页时为 `null` |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "items": [
    {
      "id": "20260401123123123",
      "status": "ACTIVE",
      "createdAt": "2026-03-30T12:08:56+08:00",
      "nickname": "Supplier A",
      "currency": "USD",
      "region": "US",
      "paymentType": "LOCAL",
      "accountType": "BANK_ACCOUNT",
      "entityType": "PERSONAL",
      "relationType": "THIRD_PARTY",
      "bankDetails": {
        "beneficiaryRegion": "US",
        "accountNumber": "123243253511",
        "routingNumber": "021000021",
        "bankName": "JPMorgan Chase Bank, NA"
      }
    }
  ],
  "prevCursor": null,
  "nextCursor": "eyJpZCI6fafafsf12313"
}
```

## 错误码

| resultCode | resultStatus | 说明 | 排查建议 |
|------------|--------------|------|----------|
| `SUCCESS` | S | 成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查过滤参数格式（`currencyList` 需 ISO 4217、`accountNumber` 和 `iban` 正则） |
| `PROCESS_FAIL` | F | 业务处理失败，请勿重试 | 联系 WorldFirst 支持 |
| `BENEFICIARY_NOT_FOUND` | F | 收款人不存在 | 放宽搜索条件 |
| `UN_SUPPORT_BUSINESS` | F | 不支持的业务 | 确认参数值在支持范围内后重试 |
| `USER_NO_PERMISSION` | F | 无权限 | 使用有权限的账户重试 |
| `INVALID_CLIENT` | F | 客户端无效 | 确认 Client ID 存在且有效 |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用有效的用户凭证重试 |

## 示例代码

参考 [references/payouts/beneficiaries/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
beneficiaries/java/
├── service/
│   └── BeneficiaryService.java              # 薄封装 Service，包含 listBeneficiaries 方法
└── model/
    ├── domain/
    │   ├── Beneficiary.java                 # 收款人对象
    │   ├── BankDetail.java                  # 银行账户详情
    │   └── WalletDetail.java                # 钱包账户详情
    ├── request/
    │   └── ListBeneficiariesRequest.java    # 列表查询请求
    └── response/
        └── ListBeneficiariesResponse.java   # 列表响应结果
```

## 集成使用方式

BeneficiaryService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `ListBeneficiariesRequest` 设置分页和过滤条件
2. 调用 `BeneficiaryService.listBeneficiaries(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，遍历 `items` 处理收款人数据

### 业务代码示例

```java
// 构造请求
ListBeneficiariesRequest request = new ListBeneficiariesRequest();
request.setLimit(10);
request.setAccountType("BANK_ACCOUNT");
request.setEntityType("PERSONAL");
request.setRelationType("THIRD_PARTY");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
ListBeneficiariesResponse response = beneficiaryService.listBeneficiaries(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    for (Beneficiary item : response.getItems()) {
        System.out.println(item.getId() + " " + item.getStatus() + " " + item.getNickname());
    }
    // 使用 nextCursor 翻页
    if (response.getNextCursor() != null) {
        request.setCursor(response.getNextCursor());
        // 再次调用获取下一页...
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BeneficiaryService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BeneficiaryService(config)` 创建实例。
