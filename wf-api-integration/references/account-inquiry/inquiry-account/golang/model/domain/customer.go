package domain

// Customer represents customer information
type Customer struct {
	// 万里汇账户标识
	AccountID string `json:"accountId,omitempty"`

	// 集成商用户 ID
	ReferenceCustomerID string `json:"referenceCustomerId,omitempty"`

	// 注册公司名
	CustomerCompanyName string `json:"customerCompanyName"`

	// 营业执照信息
	CertificateList []Certificate `json:"certificateList,omitempty"`

	// 法律实体类型：INDIVIDUAL/COMPANY
	LegalEntityType string `json:"legalEntityType,omitempty"`

	// 登录账号
	LogonID string `json:"logonId,omitempty"`

	// 身份认证等级：NOT_ALLOW_COLLECTION/ALLOW_COLLECTION
	VerificationLevel string `json:"verificationLevel,omitempty"`
}

// Certificate represents business license information
type Certificate struct {
	// 营业执照编号
	CertificateNo string `json:"certificateNo,omitempty"`

	// 证书类型：ENTERPRISE_REGISTRATION
	CertificateType string `json:"certificateType,omitempty"`
}

// AlipayCustomer represents Alipay customer information
type AlipayCustomer struct {
	// 支付宝账号
	AlipayNo string `json:"alipayNo"`

	// 企业名称
	CompanyName string `json:"companyName"`

	// 所在国家/地区（ISO-3166 二字母）
	Region string `json:"region"`
}
