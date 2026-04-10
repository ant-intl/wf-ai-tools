package exception

// WfErrorCode represents WorldFirst error codes
type WfErrorCode string

const (
	ParamIllegal          WfErrorCode = "PARAM_ILLEGAL"
	OAuthFail             WfErrorCode = "OAUTH_FAIL"
	InvalidAPI            WfErrorCode = "INVALID_API"
	InvalidClient         WfErrorCode = "INVALID_CLIENT"
	InvalidSignature      WfErrorCode = "INVALID_SIGNATURE"
	MethodNotSupported    WfErrorCode = "METHOD_NOT_SUPPORTED"
	UserNotExist          WfErrorCode = "USER_NOT_EXIST"
	AccountNotExist       WfErrorCode = "ACCOUNT_NOT_EXIST"
	SystemError           WfErrorCode = "SYSTEM_ERROR"
	ServiceNotAllowed     WfErrorCode = "SERVICE_NOT_ALLOWED"
	CurrencyNotSupport    WfErrorCode = "CURRENCY_NOT_SUPPORT"
	ContractCheckFail     WfErrorCode = "CONTRACT_CHECK_FAIL"
	AccessTokenExpired    WfErrorCode = "ACCESS_TOKEN_EXPIRED"
	AuthorizationNotExist WfErrorCode = "AUTHORIZATION_NOT_EXIST"
	InvalidResponseFormat WfErrorCode = "INVALID_RESPONSE_FORMAT"

	// createTransfer specific
	UnSupportBusiness      WfErrorCode = "UN_SUPPORT_BUSINESS"
	UserNoPermission       WfErrorCode = "USER_NO_PERMISSION"
	UserAccountAbnormal    WfErrorCode = "USER_ACCOUNT_ABNORMAL"
	RepeatReqInconsistent  WfErrorCode = "REPEAT_REQ_INCONSISTENT"
	UserStatusAbnormal     WfErrorCode = "USER_STATUS_ABNORMAL"
	BalanceNotEnough       WfErrorCode = "BALANCE_NOT_ENOUGH"
	AmountExceedLimit      WfErrorCode = "AMOUNT_EXCEED_LIMIT"
	QuoteExpired           WfErrorCode = "QUOTE_EXPIRED"

	// retryable
	UnknownException          WfErrorCode = "UNKNOWN_EXCEPTION"
	RequestTrafficExceedLimit WfErrorCode = "REQUEST_TRAFFIC_EXCEED_LIMIT"
)

// IsRetryable returns true if the error is retryable
func (e WfErrorCode) IsRetryable() bool {
	switch e {
	case UnknownException, RequestTrafficExceedLimit:
		return true
	}
	return false
}

// FromCode converts string to WfErrorCode
func FromCode(code string) WfErrorCode {
	return WfErrorCode(code)
}

