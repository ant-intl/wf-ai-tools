package request

// InquiryAvailableQuotaRequest 查询可申报结汇额度请求
type InquiryAvailableQuotaRequest struct {
	// 结汇额度累计方式：USER_ID/RECEIVING_ACCOUNT/VIRTUAL_ACCOUNT/BENEFICIARY
	QuotaAccumulationMethod string `json:"quotaAccumulationMethod"`

	// 累计方式标识（用户ID/RA号/VA号/外部平台用户ID）
	QuotaAccumulationId string `json:"quotaAccumulationId"`

	// 币种（ISO 4217标准，如"USD"）
	Currency string `json:"currency"`

	// 贸易类型：GOODS（货物）/SERVICE（服务）；BENEFICIARY时必传
	TradeType string `json:"tradeType,omitempty"`
}
