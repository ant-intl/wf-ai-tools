package {basePackage}.wf.model.exception;

/**
 * WorldFirst API 错误码枚举。
 * <p>
 * 包含所有 WF API 返回的错误码及其可重试标记。每个错误码由 {@code code}（字符串值）
 * 和 {@code retryable}（是否可重试）两个属性组成。
 * <p>
 * 分类说明：
 * <ul>
 *   <li>不可重试错误（retryable=false）：参数非法、鉴权失败、业务逻辑错误等</li>
 *   <li>可重试错误（retryable=true）：未知异常、流量超限、HTTP 请求失败等</li>
 * </ul>
 * <p>
 * 包含通用错误码、各业务模块专用错误码（全局账户、外汇、授权、关联账户、收款人、代发、
 * 存款、文件、确认函、贸易订单、发卡持卡人、发卡预算账户）和客户端内部错误码。
 */
public enum WfErrorCode {

    // ====================== 成功 ======================

    /** 成功 */
    SUCCESS("SUCCESS", false),

    // ====================== 通用错误码 ======================

    /** 参数非法 */
    PARAM_ILLEGAL("PARAM_ILLEGAL", false),

    /** 未知异常，可重试 */
    UNKNOWN_EXCEPTION("UNKNOWN_EXCEPTION", true),

    /** 请求流量超限，可重试 */
    REQUEST_TRAFFIC_EXCEED_LIMIT("REQUEST_TRAFFIC_EXCEED_LIMIT", true),

    /** 游标无效 */
    INVALID_CURSOR("INVALID_CURSOR", false),

    /** 账户不存在 */
    ACCOUNT_NOT_EXIST("ACCOUNT_NOT_EXIST", false),

    /** 合约校验失败 */
    CONTRACT_CHECK_FAIL("CONTRACT_CHECK_FAIL", false),

    /** 授权不存在 */
    AUTHORIZATION_NOT_EXIST("AUTHORIZATION_NOT_EXIST", false),

    /** 用户不存在 */
    USER_NOT_EXIST("USER_NOT_EXIST", false),

    /** 系统错误，不可重试 */
    SYSTEM_ERROR("SYSTEM_ERROR", false),

    /** 服务不允许 */
    SERVICE_NOT_ALLOWED("SERVICE_NOT_ALLOWED", false),

    /** 货币不支持 */
    CURRENCY_NOT_SUPPORT("CURRENCY_NOT_SUPPORT", false),

    /** Access Token 过期 */
    ACCESS_TOKEN_EXPIRED("ACCESS_TOKEN_EXPIRED", false),

    /** OAuth 鉴权失败 */
    OAUTH_FAIL("OAUTH_FAIL", false),

    /** API 无效或未激活 */
    INVALID_API("INVALID_API", false),

    /** Client ID 无效 */
    INVALID_CLIENT("INVALID_CLIENT", false),

    /** 签名无效 */
    INVALID_SIGNATURE("INVALID_SIGNATURE", false),

    /** HTTP 方法不支持 */
    METHOD_NOT_SUPPORTED("METHOD_NOT_SUPPORTED", false),

    /** 业务处理失败（多个业务模块均可能返回） */
    PROCESS_FAIL("PROCESS_FAIL", false),

    /** 不支持的业务 */
    UN_SUPPORT_BUSINESS("UN_SUPPORT_BUSINESS", false),

    /** 业务订单/交易不存在 */
    FUND_ORDER_NOT_EXIST("FUND_ORDER_NOT_EXIST", false),

    /** 参数非法（部分接口使用此取值，与 PARAM_ILLEGAL 并存） */
    INVALID_PARAMETER("INVALID_PARAMETER", false),

    /** 指定币种不支持（注意与 {@link #CURRENCY_NOT_SUPPORT} 为两个不同的 wire 取值） */
    CURRENCY_NOT_SUPPORTED("CURRENCY_NOT_SUPPORTED", false),

    /** 用户无权限 */
    USER_NO_PERMISSION("USER_NO_PERMISSION", false),

    /** 请求被风控拒绝 */
    RISK_REJECT("RISK_REJECT", false),

    /** 相同 requestId 已存在但请求参数不一致 */
    REPEAT_REQ_INCONSISTENT("REPEAT_REQ_INCONSISTENT", false),

    // ====================== 全局账户模块错误码 ======================

    /** 参数非法（global-accounts 专用） */
    INVALID_ARGUMENT("INVALID_ARGUMENT", false),

    /** 账户状态不支持此操作 */
    ACCOUNT_STATUS_INVALID("ACCOUNT_STATUS_INVALID", false),

    /** 地区不支持 */
    REGION_NOT_SUPPORTED("REGION_NOT_SUPPORTED", false),

    /** 需完成账户验证 */
    ACCOUNT_VERIFICATION_REQUIRED("ACCOUNT_VERIFICATION_REQUIRED", false),

    /** requestId 已被使用（重复请求） */
    REPEAT_REQUEST("REPEAT_REQUEST", false),

    /** 账户未授权 */
    ACCOUNT_AUTHORIZATION_REQUIRED("ACCOUNT_AUTHORIZATION_REQUIRED", false),

    /** 无权限执行此操作 */
    PERMISSION_DENIED("PERMISSION_DENIED", false),

    // ====================== 外汇（Foreign Exchange）模块错误码 ======================

    /** 报价已过期（deal / settlement） */
    FX_QUOTE_EXPIRED("FX_QUOTE_EXPIRED", false),

    /** 取消费用余额不足 */
    FX_DEAL_CANCEL_FEE_NOT_ENOUGH("FX_DEAL_CANCEL_FEE_NOT_ENOUGH", false),

    /** 初始保证金冻结失败 */
    FX_DEAL_INITIAL_MARGIN_FREEZE_FAILED("FX_DEAL_INITIAL_MARGIN_FREEZE_FAILED", false),

    /** 交易额度不足 */
    FX_DEAL_QUOTA_NOT_ENOUGH("FX_DEAL_QUOTA_NOT_ENOUGH", false),

    /** 远期 LEI 注册检查失败（quote / deal） */
    FX_FORWARD_LEI_CHECK_FAILED("FX_FORWARD_LEI_CHECK_FAILED", false),

    /** 货币对触发风控熔断 */
    FX_CURRENCY_PAIR_RISK_BREAK("FX_CURRENCY_PAIR_RISK_BREAK", false),

    /** 结算日期不支持（quote / settlement） */
    FX_SETTLEMENT_DATE_NOT_SUPPORTED("FX_SETTLEMENT_DATE_NOT_SUPPORTED", false),

    /** 可交易日期范围不支持 */
    FX_DEAL_DATE_RANGE_NOT_SUPPORTED("FX_DEAL_DATE_RANGE_NOT_SUPPORTED", false),

    /** 结算金额超出交易金额 */
    FX_SETTLEMENT_AMOUNT_EXCEED_DEAL_AMOUNT("FX_SETTLEMENT_AMOUNT_EXCEED_DEAL_AMOUNT", false),

    /** 结算收款人不存在 */
    FX_SETTLEMENT_BENEFICIARY_NOT_EXISTED("FX_SETTLEMENT_BENEFICIARY_NOT_EXISTED", false),

    /** 结算详情与交易订单不一致 */
    FX_SETTLEMENT_INCONSISTENT_WITH_DEAL_ORDER("FX_SETTLEMENT_INCONSISTENT_WITH_DEAL_ORDER", false),

    /** 结算方式不支持 */
    FX_SETTLEMENT_METHOD_NOT_SUPPORTED("FX_SETTLEMENT_METHOD_NOT_SUPPORTED", false),

    // ====================== 授权（OAuth2）模块错误码 ======================

    /** 授权码不存在 */
    AUTH_CODE_NOT_EXIST("AUTH_CODE_NOT_EXIST", false),

    /** 授权码已过期 */
    AUTH_CODE_EXPIRED("AUTH_CODE_EXPIRED", false),

    // ====================== 关联账户（Connected Accounts）模块错误码 ======================

    /** 访问被拒绝，仅可查询自己名下账户 */
    ACCESS_DENIED("ACCESS_DENIED", false),

    /** 重复注册，referenceAccountId 已存在 */
    REPEAT_REGISTRATION("REPEAT_REGISTRATION", false),

    /** 账户正在审核中，无法重复提交 */
    REGISTRATION_UNDER_REVIEW("REGISTRATION_UNDER_REVIEW", false),

    /** 注册通道已关闭 */
    REGISTRATION_CLOSED("REGISTRATION_CLOSED", false),

    /** 邮箱格式无效 */
    INVALID_EMAIL_FORMAT("INVALID_EMAIL_FORMAT", false),

    /** 电话号码格式无效 */
    INVALID_PHONE_FORMAT("INVALID_PHONE_FORMAT", false),

    // ====================== 收款人（Beneficiaries）模块错误码 ======================

    /** 收款人不存在 */
    BENEFICIARY_NOT_FOUND("BENEFICIARY_NOT_FOUND", false),

    /** 收款人已存在 */
    BENEFICIARY_ALREADY_EXISTED("BENEFICIARY_ALREADY_EXISTED", false),

    /** 收款人状态非 ACTIVE，无法更新 */
    BENEFICIARY_STATUS_INVALID("BENEFICIARY_STATUS_INVALID", false),

    /** 银行代码未注册 */
    INVALID_BANK_CODE("INVALID_BANK_CODE", false),

    // ====================== 代发（Payouts）模块错误码 ======================

    /** 余额不足 */
    BALANCE_NOT_ENOUGH("BALANCE_NOT_ENOUGH", false),

    /** 金额超限 */
    AMOUNT_EXCEED_LIMIT("AMOUNT_EXCEED_LIMIT", false),

    /** 收款人不存在（代发侧，与 BENEFICIARY_NOT_FOUND 为不同取值） */
    BENEFICIARY_NOT_EXIST("BENEFICIARY_NOT_EXIST", false),

    /** 卡片信息异常 */
    CARD_INFO_ABNORMAL("CARD_INFO_ABNORMAL", false),

    /** 钱包信息异常 */
    WALLET_INFO_ABNORMAL("WALLET_INFO_ABNORMAL", false),

    /** 钱包模板不存在 */
    WALLET_TEMPLATE_NOT_EXIST("WALLET_TEMPLATE_NOT_EXIST", false),

    /** 用户状态异常 */
    USER_STATUS_ABNORMAL("USER_STATUS_ABNORMAL", false),

    /** 功能不可用（未开通或不支持） */
    FEATURE_NOT_AVAILABLE("FEATURE_NOT_AVAILABLE", false),

    // ====================== 存款（Deposits）模块错误码 ======================

    /** 存款不存在或无权访问 */
    DEPOSIT_NOT_FOUND("DEPOSIT_NOT_FOUND", false),

    // ====================== 文件（File）模块错误码 ======================

    /** 文件不存在 */
    FILE_NOT_FOUND("FILE_NOT_FOUND", false),

    /** 文件大小超限 */
    FILE_SIZE_TOO_LARGE("FILE_SIZE_TOO_LARGE", false),

    // ====================== 确认函（Confirmation Letters）模块错误码 ======================

    /** 指定的对账单不存在 */
    ORDER_NOT_EXIST("ORDER_NOT_EXIST", false),

    // ====================== 贸易订单（Trade Orders）模块错误码 ======================

    /** 批次 ID 不存在 */
    ID_NOT_FOUND("ID_NOT_FOUND", false),

    /** 内部服务调用失败（契约标注 resultStatus=F） */
    INVOKE_ICIF_FAIL("INVOKE_ICIF_FAIL", false),

    // ====================== 发卡持卡人（Cardholders）模块错误码 ======================

    /** 持卡人不存在或已删除 */
    CARDHOLDER_NOT_EXIST("CARDHOLDER_NOT_EXIST", false),

    /** 持卡人信息重复 */
    DUPLICATE_CARDHOLDER("DUPLICATE_CARDHOLDER", false),

    /** 持卡人名下仍有卡片关联，无法删除 */
    CARDHOLDER_HAS_ASSOCIATED_CARD("CARDHOLDER_HAS_ASSOCIATED_CARD", false),

    // ====================== 发卡预算账户（Budgets）模块错误码 ======================

    /** 预算账户数量超过 3 个上限 */
    BUDGET_ACCOUNT_NUMBER_OVER_LIMIT("BUDGET_ACCOUNT_NUMBER_OVER_LIMIT", false),

    /** 预算账户不存在，id 有误 */
    BUDGET_NOT_FOUND("BUDGET_NOT_FOUND", false),

    /** 预算账户非 ACTIVE 状态，无法入金或出金 */
    BUDGET_ACCOUNT_STATUS_INACTIVE("BUDGET_ACCOUNT_STATUS_INACTIVE", false),

    // ====================== 客户端内部错误码 ======================

    /** 响应格式无效 */
    INVALID_RESPONSE_FORMAT("INVALID_RESPONSE_FORMAT", false),

    /** HTTP 请求失败，可重试 */
    HTTP_REQUEST_FAILED("HTTP_REQUEST_FAILED", true);

    /** 错误码字符串 */
    private final String code;

    /** 是否可重试 */
    private final boolean retryable;

    /**
     * 构造函数。
     *
     * @param code      错误码字符串
     * @param retryable 是否可重试
     */
    WfErrorCode(String code, boolean retryable) {
        this.code = code;
        this.retryable = retryable;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return 错误码字符串
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>retryable</tt>.
     *
     * @return 是否可重试
     */
    public boolean isRetryable() {
        return retryable;
    }

    /**
     * 根据错误码字符串查找对应枚举值。
     * <p>
     * 遍历所有枚举值进行匹配，未匹配时返回 {@link #UNKNOWN_EXCEPTION}。
     *
     * @param code 错误码字符串
     * @return 对应的 WfErrorCode 枚举值，未匹配时返回 UNKNOWN_EXCEPTION
     */
    public static WfErrorCode fromCode(String code) {
        if (code == null) {
            return UNKNOWN_EXCEPTION;
        }
        for (WfErrorCode errorCode : values()) {
            if (errorCode.code.equals(code)) {
                return errorCode;
            }
        }
        return UNKNOWN_EXCEPTION;
    }
}
