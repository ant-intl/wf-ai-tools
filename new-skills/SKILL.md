---
name: wf-api-integration
description: Generate Java or Golang integration code for WorldFirst (WF) APIs including transfer, payout, beneficiary management, balance inquiry, statement inquiry, and trade order management. Supports RSA256 signing, shared infrastructure reuse, and production-ready code generation with Alibaba coding standards.
---
# WF API Integration Skill

帮助用户对接万里汇(WorldFirst) API，支持 Java 和 Golang 两种语言，涵盖转账、代发、收款人管理、余额查询、账单查询、交易订单管理等模块。

## 接口索引

<table>
  <thead>
    <tr>
      <th>模块</th>
      <th>模块目录</th>
      <th>模块说明</th>
      <th>接口</th>
      <th>接口目录</th>
      <th>接口说明</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>转账</td>
      <td><code>references/transfer/</code></td>
      <td>户到户转账</td>
      <td>户到户转账</td>
      <td><code>references/transfer/create-transfer/</code></td>
      <td>调用 createTransfer 接口，在万里汇账户之间转账</td>
    </tr>
    <tr>
      <td rowspan="3">单据支付</td>
      <td rowspan="3"><code>references/payout/</code></td>
      <td rowspan="3">代发到三方卡</td>
      <td>咨询代发汇率</td>
      <td><code>references/payout/consult-payout/</code></td>
      <td>调用 consultPayout 接口，获取跨币种代发汇率报价（quoteId）</td>
    </tr>
    <tr>
      <td>创建代发</td>
      <td><code>references/payout/create-payout/</code></td>
      <td>调用 createPayout 接口，代发到第三方银行卡</td>
    </tr>
    <tr>
      <td>查询代发结果</td>
      <td><code>references/payout/inquiry-payout/</code></td>
      <td>调用 inquiryPayout 接口，查询代发单状态</td>
    </tr>
    <tr>
      <td rowspan="5">收款人管理</td>
      <td rowspan="5"><code>references/beneficiary/</code></td>
      <td rowspan="5">卡模版查询、绑定/删除/编辑/查询收款人</td>
      <td>查询卡模版</td>
      <td><code>references/beneficiary/inquiry-template/</code></td>
      <td>查询指定国家/币种/账户类型的卡模版字段要求</td>
    </tr>
    <tr>
      <td>绑定收款人</td>
      <td><code>references/beneficiary/bind/</code></td>
      <td>绑定收款人到 WF 账户，获取 beneficiaryToken</td>
    </tr>
    <tr>
      <td>删除收款人</td>
      <td><code>references/beneficiary/remove/</code></td>
      <td>删除已绑定的收款人</td>
    </tr>
    <tr>
      <td>编辑收款人</td>
      <td><code>references/beneficiary/edit/</code></td>
      <td>修改收款人昵称</td>
    </tr>
    <tr>
      <td>查询收款人列表</td>
      <td><code>references/beneficiary/inquiry-list/</code></td>
      <td>分页查询已绑定的收款人</td>
    </tr>
    <tr>
      <td>余额查询</td>
      <td><code>references/balance-inquiry/</code></td>
      <td>查询账户余额</td>
      <td>查询余额</td>
      <td><code>references/balance-inquiry/inquiry-balance/</code></td>
      <td>查询 WF 账户余额，支持按币种和余额类型过滤</td>
    </tr>
    <tr>
      <td>账单查询</td>
      <td><code>references/statement-inquiry/</code></td>
      <td>查询账户流水</td>
      <td>查询账单流水</td>
      <td><code>references/statement-inquiry/inquiry-statement-list/</code></td>
      <td>分页查询 WF 账户交易流水</td>
    </tr>
    <tr>
      <td rowspan="3">交易订单管理</td>
      <td rowspan="3"><code>references/trade-order/</code></td>
      <td rowspan="3">上传交易订单（B2C 结汇 / B2B 订单关联）</td>
      <td>提交交易订单</td>
      <td><code>references/trade-order/submit-trade-order/</code></td>
      <td>调用 submitTradeOrder 接口，上传交易订单（PAY_INTO_CHINA / CREATE_B2B_ORDERS）</td>
    </tr>
    <tr>
      <td>查询订单结果</td>
      <td><code>references/trade-order/inquiry-trade-order/</code></td>
      <td>调用 inquiryTradeOrder 接口，查询上传结果（仅 PAY_INTO_CHINA）</td>
    </tr>
    <tr>
      <td>订单回调通知</td>
      <td><code>references/trade-order/notify-trade-order/</code></td>
      <td>处理 WF notifyTradeOrder 异步回调通知（仅 PAY_INTO_CHINA）</td>
    </tr>
  </tbody>
</table>

## 使用流程

1. **确认模块**：确认用户需要对接的模块，读取对应模块的 `README.md` 了解接口列表
2. **确认接口**：确认用户需要对接的具体接口，读取对应接口的 `GUIDE.md` 了解接口规范
3. **加载公共代码**：读取 `references/common/` 下的公共代码模板
4. **加载接口代码**：读取对应接口目录下的代码模板
5. **生成代码**：根据用户项目结构生成代码，替换 `{basePackage}`（Java）或 `{moduleName}`（Golang）占位符

## Pre-Generation Questions (MUST ASK)

在生成代码前，**必须**询问用户以下问题：

1. **项目路径**：请问你的项目路径是什么？
2. **语言选择**：你需要生成 Java 还是 Golang 的代码？
3. **Base Package / Module Name**：
   - Java：请提供 base package（如 `com.example.project`）
   - Golang：请提供 Go module name（如 `github.com/example/project`）

## 公共依赖

所有模块共用以下组件，位于 `references/common/`：

### Java (`references/common/java/`)


| 组件             | 路径                               | 说明                                          |
| ---------------- | ---------------------------------- | --------------------------------------------- |
| WfConfig         | `config/WfConfig.java`             | 配置管理（clientId、baseUrl、密钥路径、超时） |
| WfSigner         | `signer/WfSigner.java`             | RSA256 签名/验签                              |
| WfHttpClientUtil | `util/WfHttpClientUtil.java`       | HTTP 客户端封装（签名注入、响应验签）         |
| Result           | `model/response/Result.java`       | 统一响应结果对象                              |
| WfErrorCode      | `model/exception/WfErrorCode.java` | 错误码枚举                                    |
| WfException      | `model/exception/WfException.java` | 业务异常类                                    |

### Golang (`references/common/golang/`)


| 组件         | 路径                              | 说明                                   |
| ------------ | --------------------------------- | -------------------------------------- |
| WfConfig     | `config/config.go`                | 配置管理                               |
| WfSigner     | `signer/signer.go`                | RSA256 签名/验签（Signer 接口 + 实现） |
| WfHttpClient | `util/wf_http_client.go`          | HTTP 客户端封装                        |
| Result       | `model/response/result.go`        | 统一响应结果                           |
| WfErrorCode  | `model/exception/error_code.go`   | 错误码定义                             |
| WfException  | `model/exception/wf_exception.go` | 异常定义                               |

## 公共代码生成规则

- **WfConfig**：生成前必须通过交互询问 clientId、baseUrl、privateKeyPath、publicKeyPath
- **Result.java / result.go**：共享，仅首次生成，已存在则复用
- **WfErrorCode**：共享，新接口的错误码追加到已有文件，不重复生成
- **WfException**：共享，已存在则复用

## 代码规范

### Java

- Java 8（禁止使用 Java 9+ 专有语法）
- CamelCase 类名，lowerCamelCase 变量名，UPPER_SNAKE_CASE 常量名
- 同行左花括号，4 空格缩进，每行最多 120 字符
- 所有 public 方法必须有 Javadoc，使用 SLF4J 日志

### Golang

- 标准库优先，按包分文件
- 依赖注入模式（构造函数注入）
- `{moduleName}` 占位符替换为用户实际 go.mod 中的 module path

## 签名算法

所有 WF API 使用 RSA256 签名，签名内容格式：

```
POST {apiPath}\n{clientId}.{requestTime}.{requestBody}
```

详见 `references/common/` 下的签名工具代码。

## 测试代码生成

生成测试代码前，**必须**询问签名模式：


| 模式      | 说明                                                                              |
| --------- | --------------------------------------------------------------------------------- |
| Mock 签名 | Mock WfSigner 固定返回`"TESTING_SIGNATURE"`，跳过真实签名，适用于快速验证请求格式 |
| 真实签名  | 使用用户提供的私钥/公钥文件路径，可完整跑通接口                                   |
