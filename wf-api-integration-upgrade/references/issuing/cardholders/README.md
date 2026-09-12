# 发卡持卡人（Cardholders）模块

持卡人（Cardholder）是卡片的发放对象。本模块覆盖持卡人的注册与 KYC 材料提交、审核状态查询、列表核对与永久删除。

> 持卡人须达到 `ACTIVE` 状态后方可发卡；删除为永久操作，且要求名下已无任何关联卡片。

## 官方文档

- [WorldFirst 开发者文档 - Cardholders Overview](https://docs.worldfirst.com/wfdocs/api-sdk/cardholders_overview)
- [create_a_cardholder](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_cardholder) | [query_a_cardholder](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_cardholder) | [list_cardholders](https://docs.worldfirst.com/wfdocs/api-sdk/list_cardholders) | [delete_a_cardholder](https://docs.worldfirst.com/wfdocs/api-sdk/delete_a_cardholder)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 创建持卡人 | `create-a-cardholder/GUIDE.md` | 注册持卡人并提交身份材料进入 KYC 审核 | `POST /api/open/v1/issuing/cardholders/create` |
| 查询持卡人 | `query-a-cardholder/GUIDE.md` | 按 ID 查询持卡人详情与审核状态 | `POST /api/open/v1/issuing/cardholders/query` |
| 查询持卡人列表 | `list-cardholders/GUIDE.md` | 分页查询当前账户下持卡人，可按状态过滤 | `POST /api/open/v1/issuing/cardholders/list` |
| 删除持卡人 | `delete-a-cardholder/GUIDE.md` | 永久删除名下无关联卡片的持卡人 | `POST /api/open/v1/issuing/cardholders/delete` |

## CardholderService 说明

`CardholderService` 是发卡持卡人模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `CreateCardholderResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `createCardholder(CreateCardholderRequest)` | `CreateCardholderRequest` | `CreateCardholderResponse` | 注册持卡人并提交 KYC 审核 |
| `queryCardholder(QueryCardholderRequest)` | `QueryCardholderRequest` | `QueryCardholderResponse` | 查询持卡人详情与审核状态 |
| `listCardholders(ListCardholdersRequest)` | `ListCardholdersRequest` | `ListCardholdersResponse` | 分页查询持卡人列表（游标分页） |
| `deleteCardholder(DeleteCardholderRequest)` | `DeleteCardholderRequest` | `DeleteCardholderResponse` | 永久删除持卡人 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `CardholderService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `CardholderService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

## 对接流程

### 1. 配置

```java
WfClientConfig config = WfClientConfig.builder()
    .clientId("YOUR_CLIENT_ID")
    .privateKeyFromPath("/path/to/private_key.pem")
    .publicKeyFromPath("/path/to/wf_public_key.pem")
    .baseUrl("https://YOUR_BASE_URL")
    .build();
```

### 2. 创建 Service

```java
CardholderService cardholderService = new CardholderService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 上传证明材料后创建持卡人
UserName userName = new UserName();
userName.setFirstName("San");
userName.setLastName("Zhang");

PersonInfo personInfo = new PersonInfo();
personInfo.setUserName(userName);
personInfo.setDateOfBirth("1990-06-15");

IdentificationFile idFile = new IdentificationFile();
idFile.setFileKey("file_key_001");
idFile.setFileName("passport_front.jpg");

Identification identification = new Identification();
identification.setFileList(Collections.singletonList(idFile));

CreateCardholderRequest createRequest = new CreateCardholderRequest();
createRequest.setRequestId("req_20240115_001");
createRequest.setType("EMPLOYEE");
createRequest.setPersonInfo(personInfo);
createRequest.setNationality("CHN");
createRequest.setIdentification(identification);

CreateCardholderResponse createResponse = cardholderService.createCardholder(createRequest);

// 查询持卡人详情与审核状态
QueryCardholderRequest queryRequest = new QueryCardholderRequest();
queryRequest.setId("ch_abc123xyz");

QueryCardholderResponse queryResponse = cardholderService.queryCardholder(queryRequest);

// 分页查询持卡人列表
ListCardholdersRequest listRequest = new ListCardholdersRequest();
listRequest.setLimit(20);
listRequest.setStatus("ACTIVE");

ListCardholdersResponse listResponse = cardholderService.listCardholders(listRequest);

// 删除持卡人
DeleteCardholderRequest deleteRequest = new DeleteCardholderRequest();
deleteRequest.setId("ch_abc123xyz");

DeleteCardholderResponse deleteResponse = cardholderService.deleteCardholder(deleteRequest);
```

### 4. 检查业务结果

```java
if ("S".equals(createResponse.getResult().getResultStatus())) {
    System.out.println("持卡人 ID: " + createResponse.getId());
    System.out.println("状态: " + createResponse.getStatus());          // 创建后为 PENDING
}

if ("S".equals(queryResponse.getResult().getResultStatus())) {
    System.out.println("审核状态: " + queryResponse.getStatus());
    System.out.println("姓名: " + queryResponse.getPersonInfo().getUserName().getFullName());
}

if ("S".equals(listResponse.getResult().getResultStatus())) {
    for (CardholderRecord holder : listResponse.getItems()) {
        System.out.println("持卡人: " + holder.getId() + " " + holder.getStatus());
    }
    // nextCursor 缺失表示已到最后一页
    System.out.println("下一页游标: " + listResponse.getNextCursor());
}

if ("S".equals(deleteResponse.getResult().getResultStatus())) {
    System.out.println("持卡人已永久删除");
}
```

## 注意事项

### create_a_cardholder
- `requestId` 必填，为幂等键（最大 64 字符），每次创建请求必须唯一；网络重试须复用同一取值，避免重复创建
- `type`、`personInfo`、`nationality`、`identification` 均为必填
- `type` 决定身份材料文件的解析方式：`INDIVIDUAL`（非本单位员工）、`EMPLOYEE`（员工）、`SHAREHOLDER`（股东/受益所有人）
- `EMPLOYEE` 与 `SHAREHOLDER` 的材料列表必须包含身份证明文件，关系证明可选但建议提供
- `fileList` 中的 `fileKey` 必须先调用文件上传接口（`upload_a_file`）取得，万里汇按列表顺序与业务规则识别各文件
- `personInfo.dateOfBirth` 格式为 `yyyy-MM-dd`；`nationality` 为 ISO 3166-1 alpha-3 三字母代码
- 创建成功后状态固定为 `PENDING`，需轮询 `query_a_cardholder` 获知审核结果
- `RISK_REJECT` 不可重试，需携带 `requestId` 联系万里汇支持；`UNKNOWN_EXCEPTION` 使用相同 `requestId` 重试

### query_a_cardholder
- `id` 必填，为 `create_a_cardholder` 返回的持卡人标识
- 主要用于轮询 `status`：`PENDING`（审核中，非终态）、`ACTIVE`（审核通过，可发卡）、`FAILED`（审核失败或拒绝，终态）
- `CARDHOLDER_NOT_EXIST` 表示 ID 有误或持卡人已删除，可用列表接口核对

### list_cardholders
- `limit` 必填，取值范围 1-50，不传时默认 20（注意与其他模块的 1-100 不同）
- `status` 可选，不传时返回全部状态；`cursor` 首次请求省略
- 分页终止以响应中 **`nextCursor` 缺失**为准，不要用 `totalCount` 控制分页
- `items` 每项字段与 `query_a_cardholder` 响应一致，无匹配时返回空列表

### delete_a_cardholder
- `id` 必填；删除为**永久操作**，无软删除与回滚
- 仅当持卡人名下无任何关联卡片时可删除，否则返回 `CARDHOLDER_HAS_ASSOCIATED_CARD`，需先取消或解绑全部卡片
- 结果不确定（如 `UNKNOWN_EXCEPTION`）时，必须先 `query_a_cardholder` 复查删除是否生效，再决定是否重试

## 枚举类型说明

取值以 [官方 Cardholders Overview](https://docs.worldfirst.com/wfdocs/api-sdk/cardholders_overview) 为准。

### CardholderType（持卡人类型）

| 取值 | 说明 |
|------|------|
| `INDIVIDUAL` | 非本单位员工的个人持卡人 |
| `EMPLOYEE` | 本单位员工 |
| `SHAREHOLDER` | 本单位股东或受益所有人 |

### CardholderStatus（持卡人状态）

| 取值 | 说明 |
|------|------|
| `PENDING` | 审核中，等待 KYC 验证；非终态 |
| `ACTIVE` | 审核通过，可向该持卡人发卡 |
| `FAILED` | 审核失败或持卡人被拒绝；终态 |

## 模块专用错误码

以下错误码由本模块接口引入，已作「发卡持卡人（Cardholders）模块错误码」分节追加到共享枚举
`{basePackage}.wf.model.exception.WfErrorCode`（其余通用错误码也在同一枚举中定义，按追加模式不覆盖已有取值）：

| resultCode | resultStatus | 出现接口 | 说明 |
|------------|--------------|----------|------|
| `DUPLICATE_CARDHOLDER` | F | create | 相同身份的持卡人已存在，改用列表/查询接口核对 |
| `RISK_REJECT` | F | create | 风控拒绝，不可重试 |
| `CARDHOLDER_NOT_EXIST` | F | query / delete | 持卡人不存在或已删除 |
| `CARDHOLDER_HAS_ASSOCIATED_CARD` | F | delete | 名下仍有卡片关联，无法删除 |

## 文件结构

```
cardholders/
├── README.md
├── java/
│   ├── service/
│   │   └── CardholderService.java                     # 发卡持卡人服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── PersonInfo.java                        # 持卡人个人信息（userName, dateOfBirth）
│       │   ├── Identification.java                    # 身份证明材料（fileList）
│       │   ├── IdentificationFile.java                # 单个证明文件（fileKey, fileName）
│       │   └── CardholderRecord.java                  # 持卡人记录（用于 list 响应的 items）
│       ├── request/
│       │   ├── CreateCardholderRequest.java           # 创建持卡人请求
│       │   ├── QueryCardholderRequest.java            # 查询持卡人请求（id）
│       │   ├── ListCardholdersRequest.java            # 查询持卡人列表请求（status, limit, cursor）
│       │   └── DeleteCardholderRequest.java           # 删除持卡人请求（id）
│       └── response/
│           ├── CreateCardholderResponse.java          # 创建持卡人响应
│           ├── QueryCardholderResponse.java           # 查询持卡人响应
│           ├── ListCardholdersResponse.java           # 查询持卡人列表响应
│           └── DeleteCardholderResponse.java          # 删除持卡人响应（仅 result）
├── create-a-cardholder/
│   └── GUIDE.md                                       # create_a_cardholder 接口接入指引
├── query-a-cardholder/
│   └── GUIDE.md                                       # query_a_cardholder 接口接入指引
├── list-cardholders/
│   └── GUIDE.md                                       # list_cardholders 接口接入指引
└── delete-a-cardholder/
    └── GUIDE.md                                       # delete_a_cardholder 接口接入指引
```

> **复用 domain 对象**：`UserName`、`Result` 等通用对象与其他模块共享，引用 `{basePackage}.wf.model.domain`、`{basePackage}.wf.model.response` 包下的已有定义。`PersonInfo`、`Identification`、`IdentificationFile`、`CardholderRecord` 为本模块独有的 domain 对象。
>
> **依赖的其他模块**：创建持卡人前需通过文件模块（`references/supporting-service/file`）的 `upload_a_file` 上传身份证明材料，取得 `fileKey` 后填入 `identification.fileList`。
