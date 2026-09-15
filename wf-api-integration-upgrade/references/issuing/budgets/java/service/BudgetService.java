package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.CreateBudgetRequest;
import {basePackage}.wf.model.request.DepositToBudgetRequest;
import {basePackage}.wf.model.request.ListBudgetsRequest;
import {basePackage}.wf.model.request.ListDailyBalancesRequest;
import {basePackage}.wf.model.request.QueryBudgetRequest;
import {basePackage}.wf.model.request.WithdrawFromBudgetRequest;
import {basePackage}.wf.model.response.CreateBudgetResponse;
import {basePackage}.wf.model.response.DepositToBudgetResponse;
import {basePackage}.wf.model.response.ListBudgetsResponse;
import {basePackage}.wf.model.response.ListDailyBalancesResponse;
import {basePackage}.wf.model.response.QueryBudgetResponse;
import {basePackage}.wf.model.response.WithdrawFromBudgetResponse;

/**
 * WorldFirst 发卡（Issuing）预算账户服务。
 *
 * <p>内部使用 {@link WfApiClient} 处理 HTTP 请求与加验签，采用薄封装模式：
 * 不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。
 * <p><b>安全强制</b>：{@link WfApiClient} 在返回响应前会执行验签，
 * 验签失败时抛出 {@link WfException}（错误码 INVALID_SIGNATURE），
 * 确保调用方拿到的响应均已完成验签，避免中间人攻击。
 *
 * <p>预算账户用于划拨并管控卡片消费资金。单个商户账户最多持有 3 个预算账户，
 * 预算账户一经创建不可关闭；仅 ACTIVE 状态的预算账户可进行入金与出金。
 * deposit / withdraw 属于资金变动操作，必须携带唯一 {@code requestId} 保证幂等。
 *
 * <pre>{@code
 * // 1. 创建配置
 * WfClientConfig config = WfClientConfig.builder()
 *     .clientId("YOUR_CLIENT_ID")
 *     .privateKeyFromPath("/path/to/private_key.pem")
 *     .publicKeyFromPath("/path/to/wf_public_key.pem")
 *     .baseUrl("https://YOUR_BASE_URL")
 *     .build();
 *
 * // 2. 创建服务
 * BudgetService budgetService = new BudgetService(config);
 *
 * // 3. 创建预算账户（内部已强制验签，验签失败抛 WfException）
 * CreateBudgetRequest request = new CreateBudgetRequest();
 * request.setRequestId("req_budget_20260808_001");
 * request.setName("Marketing Cards Q3");
 *
 * CreateBudgetResponse response = budgetService.createBudget(request);
 * }</pre>
 */
public class BudgetService {

    /** 创建预算账户 API 路径 */
    private static final String CREATE_BUDGET_ENDPOINT = "/api/open/v1/issuing/budgets/create";

    /** 查询预算账户 API 路径 */
    private static final String QUERY_BUDGET_ENDPOINT = "/api/open/v1/issuing/budgets/query";

    /** 查询预算账户列表 API 路径 */
    private static final String LIST_BUDGETS_ENDPOINT = "/api/open/v1/issuing/budgets/list";

    /** 预算账户入金 API 路径 */
    private static final String DEPOSIT_TO_BUDGET_ENDPOINT = "/api/open/v1/issuing/budgets/deposit";

    /** 预算账户出金 API 路径 */
    private static final String WITHDRAW_FROM_BUDGET_ENDPOINT = "/api/open/v1/issuing/budgets/withdraw";

    /** 查询预算账户每日余额 API 路径 */
    private static final String LIST_DAILY_BALANCES_ENDPOINT = "/api/open/v1/issuing/budgets/listDailyBalance";

    private final WfApiClient apiClient;

    /**
     * 创建 BudgetService 实例。
     *
     * @param config 客户端配置
     */
    public BudgetService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 BudgetService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public BudgetService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 预算账户 API ========================

    /**
     * 创建预算账户。
     * <p>
     * 划拨并持有用于发卡消费的资金。单个商户账户最多持有 3 个预算账户，超出返回
     * BUDGET_ACCOUNT_NUMBER_OVER_LIMIT；预算账户一经创建不可关闭。
     * {@code requestId} 为幂等键，重试必须复用同一取值以避免重复创建。
     * <p>
     * 对应 WF API: POST /api/open/v1/issuing/budgets/create
     *
     * @param request 创建预算账户请求（包含 requestId、name）
     * @return 创建预算账户响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public CreateBudgetResponse createBudget(CreateBudgetRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CREATE_BUDGET_ENDPOINT, body, CreateBudgetResponse.class).getServiceResponse();
    }

    /**
     * 查询预算账户详情。
     * <p>
     * 按预算账户 ID 检索详情及其多币种余额（{@code balanceAmounts} 每币种至多一条，
     * 状态为 FAILED 时返回空列表）。
     * <p>
     * 对应 WF API: POST /api/open/v1/issuing/budgets/query
     *
     * @param request 查询预算账户请求（包含 id）
     * @return 查询预算账户响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryBudgetResponse queryBudget(QueryBudgetRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_BUDGET_ENDPOINT, body, QueryBudgetResponse.class).getServiceResponse();
    }

    /**
     * 查询预算账户列表。
     * <p>
     * 列出当前账户下全部预算账户（含状态与创建时间）。本接口无请求参数、不分页，
     * 最多返回 3 条记录，无数据时返回空列表。
     * <p>
     * 对应 WF API: POST /api/open/v1/issuing/budgets/list
     *
     * @param request 查询预算账户列表请求（无业务字段，保留用于扩展）
     * @return 查询预算账户列表响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public ListBudgetsResponse listBudgets(ListBudgetsRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(LIST_BUDGETS_ENDPOINT, body, ListBudgetsResponse.class).getServiceResponse();
    }

    /**
     * 预算账户入金。
     * <p>
     * 从源余额账户向预算账户划拨资金。仅 ACTIVE 状态的预算账户可入金，
     * 源余额账户余额不足时返回 BALANCE_NOT_ENOUGH。属于资金变动操作，
     * {@code requestId} 必填且需在 partnerId 内唯一，重试必须复用同一取值以避免重复扣款。
     * <p>
     * 对应 WF API: POST /api/open/v1/issuing/budgets/deposit
     *
     * @param request 预算账户入金请求（包含 requestId、id、balanceType、amount）
     * @return 预算账户入金响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public DepositToBudgetResponse depositToBudget(DepositToBudgetRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(DEPOSIT_TO_BUDGET_ENDPOINT, body, DepositToBudgetResponse.class).getServiceResponse();
    }

    /**
     * 预算账户出金。
     * <p>
     * 将预算账户资金退回余额账户。仅 ACTIVE 状态的预算账户可出金，
     * 预算账户可用余额不足时返回 BALANCE_NOT_ENOUGH。属于资金变动操作，
     * {@code requestId} 必填且需在 partnerId 内唯一，重试必须复用同一取值以避免重复出金。
     * <p>
     * 对应 WF API: POST /api/open/v1/issuing/budgets/withdraw
     *
     * @param request 预算账户出金请求（包含 requestId、id、amount）
     * @return 预算账户出金响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public WithdrawFromBudgetResponse withdrawFromBudget(WithdrawFromBudgetRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(WITHDRAW_FROM_BUDGET_ENDPOINT, body, WithdrawFromBudgetResponse.class).getServiceResponse();
    }

    /**
     * 查询预算账户每日余额。
     * <p>
     * 按日期区间查询各预算账户的日终余额与当日资金流入/流出，采用游标分页。
     * {@code startDate} 与 {@code endDate} 区间跨度不得超过 31 天；未返回
     * {@code nextCursor} 即表示已无更多数据。
     * <p>
     * 对应 WF API: POST /api/open/v1/issuing/budgets/listDailyBalance
     *
     * @param request 查询每日余额请求（包含 startDate、endDate、budgetId、currencies、cursor、limit）
     * @return 查询每日余额响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public ListDailyBalancesResponse listDailyBalances(ListDailyBalancesRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(LIST_DAILY_BALANCES_ENDPOINT, body, ListDailyBalancesResponse.class).getServiceResponse();
    }
}
