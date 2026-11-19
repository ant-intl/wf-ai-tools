package {basePackage}.wf.client;

/**
 * 单次请求的代理账户上下文。account-id 与 access-token 互斥。
 * 不把凭证放入业务 JSON，不修改共享客户端，不在日志中输出令牌。
 */
public final class WfRequestContext {
    private final String accountId;
    private final String accessToken;

    private WfRequestContext(String accountId, String accessToken) {
        this.accountId = accountId;
        this.accessToken = accessToken;
    }

    public static WfRequestContext forAccount(String accountId) {
        requireHeaderValue(accountId);
        return new WfRequestContext(accountId, null);
    }

    public static WfRequestContext forAccessToken(String accessToken) {
        requireHeaderValue(accessToken);
        return new WfRequestContext(null, accessToken);
    }

    private static void requireHeaderValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("账户上下文不能为空");
        }
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) < 0x21 || value.charAt(i) > 0x7e) {
                throw new IllegalArgumentException("账户上下文包含非法请求头字符");
            }
        }
    }

    public String getAccountId() { return accountId; }

    public String getAccessToken() { return accessToken; }

    @Override
    public String toString() { return "WfRequestContext[REDACTED]"; }
}
