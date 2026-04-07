package domain

// Amount represents a monetary amount in WF API.
// Value is in minor units (e.g. cents): USD 10.00 = value 1000.
// 2-decimal currencies (USD/EUR/GBP/CNY etc): value = face_amount × 100
// 0-decimal currencies (JPY/KRW): value = face_amount × 1
type Amount struct {
	Currency string `json:"currency"`
	Value    *int64 `json:"value,omitempty"`
}

// Address represents a physical address used by shipping
type Address struct {
	Region   string `json:"region"`
	State    string `json:"state,omitempty"`
	City     string `json:"city,omitempty"`
	Address1 string `json:"address1,omitempty"`
	Address2 string `json:"address2,omitempty"`
	ZipCode  string `json:"zipCode,omitempty"`
}

// TradeOrder represents a single trade order in submitTradeOrder API.
// Supports two scenes: PAY_INTO_CHINA (B2C) and CREATE_B2B_ORDERS (B2B).
type TradeOrder struct {
	ReferenceOrderNo string  `json:"referenceOrderNo"`
	PaymentTime      string  `json:"paymentTime"`
	TransAmount      *Amount `json:"transAmount"`
	TradeAmount      *Amount `json:"tradeAmount"`
	TradeType        string  `json:"tradeType"`
	// B2C fields (PAY_INTO_CHINA)
	OrderTime string    `json:"orderTime,omitempty"`
	OrderType string    `json:"orderType,omitempty"`
	Merchant  *Merchant `json:"merchant,omitempty"`
	Seller    *Customer `json:"seller,omitempty"`
	Buyer     *Buyer    `json:"buyer,omitempty"`
	Goods     []Goods   `json:"goods,omitempty"`
	Shipping  *Shipping `json:"shipping,omitempty"`
	// B2B fields (CREATE_B2B_ORDERS)
	TradeTerms        string           `json:"tradeTerms,omitempty"`
	IsUsedForExchange string           `json:"isUsedForExchange,omitempty"`
	BizContractInfo   *BizContractInfo `json:"bizContractInfo,omitempty"`
	LogisticsMode     string           `json:"logisticsMode,omitempty"`
	// Shared optional fields
	Market       string `json:"market,omitempty"`
	RevenueShare string `json:"revenueShare,omitempty"`
}

// Merchant represents the merchant information in B2C trade order
type Merchant struct {
	Store      *Store `json:"store,omitempty"`
	MerchantID string `json:"merchantId,omitempty"`
}

// Store represents the merchant's store information
type Store struct {
	StoreShopURL string `json:"storeShopUrl"`
}

// Customer represents the seller in B2C trade order
type Customer struct {
	CustomerID          string        `json:"customerId"`
	ReferenceCustomerID string        `json:"referenceCustomerId"`
	CustomerCompanyName string        `json:"customerCompanyName,omitempty"`
	CertificateList     []Certificate `json:"certificateList,omitempty"`
}

// Certificate represents a business certificate
type Certificate struct {
	CertificateNo   string `json:"certificateNo,omitempty"`
	CertificateType string `json:"certificateType,omitempty"`
}

// Buyer represents the buyer in B2C trade order.
// At least one of ReferenceBuyerID, BuyerName, or BuyerEmail must be provided.
type Buyer struct {
	ReferenceBuyerID string     `json:"referenceBuyerId,omitempty"`
	BuyerName        *BuyerName `json:"buyerName,omitempty"`
	BuyerEmail       string     `json:"buyerEmail,omitempty"`
	BuyerCountry     string     `json:"buyerCountry,omitempty"`
	BuyerPhoneNo     string     `json:"buyerPhoneNo,omitempty"`
}

// BuyerName represents the buyer's name
type BuyerName struct {
	FirstName  string `json:"firstName,omitempty"`
	MiddleName string `json:"middleName,omitempty"`
	LastName   string `json:"lastName,omitempty"`
	FullName   string `json:"fullName"`
}

// Goods represents goods information in a trade order
type Goods struct {
	GoodsName     string `json:"goodsName"`
	GoodsCategory string `json:"goodsCategory,omitempty"`
	GoodsQuantity string `json:"goodsQuantity"`
	GoodsUnit     string `json:"goodsUnit,omitempty"`
	GoodsCnName   string `json:"goodsCnName,omitempty"`
	StoreURL      string `json:"storeUrl,omitempty"`
}

// Shipping represents shipping/logistics information in a trade order.
// Required fields depend on B2C/B2B scene and isUsedForExchange / isShipped / isDeclared flags.
type Shipping struct {
	// B2B conditional fields
	IsShipped  string `json:"isShipped,omitempty"`
	IsDeclared string `json:"isDeclared,omitempty"`
	IsNewBuyer string `json:"isNewBuyer,omitempty"`
	// Waybill info (B2C required; B2B conditional)
	WayBillInfos []WayBillInfo `json:"wayBillInfos,omitempty"`
	// B2C required
	ShippingAddress *Address `json:"shippingAddress,omitempty"`
	// B2B conditional fields
	ShippingMethod                    string           `json:"shippingMethod,omitempty"`
	ShippingProofAttachmentList       []AttachmentInfo `json:"shippingProofAttachmentList,omitempty"`
	LogisticsCompany                  *LogisticsCompany `json:"logisticsCompany,omitempty"`
	DeclarationInfos                  []DeclarationInfo `json:"declarationInfos,omitempty"`
	ExpectedShippingDate              string           `json:"expectedShippingDate,omitempty"`
	InquiryChatRecordAttachmentList   []AttachmentInfo `json:"inquiryChatRecordAttachmentList,omitempty"`
	LogisticsChatRecordAttachmentList []AttachmentInfo `json:"logisticsChatRecordAttachmentList,omitempty"`
}

// WayBillInfo represents a single waybill entry
type WayBillInfo struct {
	ShippingOrderReferenceNo string `json:"shippingOrderReferenceNo,omitempty"`
}

// LogisticsCompany represents the logistics provider
type LogisticsCompany struct {
	ProviderKey   string `json:"providerKey,omitempty"`
	ProviderValue string `json:"providerValue,omitempty"`
}

// DeclarationInfo represents customs declaration information
type DeclarationInfo struct {
	DeclarationOrderReferenceNo       string           `json:"declarationOrderReferenceNo"`
	SupervisionMethod                 string           `json:"supervisionMethod"`
	CustomsDeclarationAttachmentList  []AttachmentInfo `json:"customsDeclarationAttachmentList,omitempty"`
}

// AttachmentInfo represents an uploaded file reference
type AttachmentInfo struct {
	FileName string `json:"fileName"`
	FileKey  string `json:"fileKey"`
}

// BizContractInfo represents B2B business contract information
type BizContractInfo struct {
	BuyerEnName         string           `json:"buyerEnName,omitempty"`
	TradeCountry        string           `json:"tradeCountry,omitempty"`
	DeliverCountry      string           `json:"deliverCountry,omitempty"`
	ContractList        []AttachmentInfo `json:"contractList,omitempty"`
	OtherAttachmentList []AttachmentInfo `json:"otherAttachmentList,omitempty"`
	AttachmentDesc      string           `json:"attachmentDesc,omitempty"`
}

// TradeOrderResult represents the result for a single trade order in
// submitTradeOrder / inquiryTradeOrder / notifyTradeOrder responses.
type TradeOrderResult struct {
	ReferenceOrderNo string  `json:"referenceOrderNo"`
	OrderStatus      string  `json:"orderStatus"`
	OrderType        string  `json:"orderType,omitempty"`
	StatusMessage    string  `json:"statusMessage,omitempty"`
	ErrorCode        string  `json:"errorCode,omitempty"`
	TransAmount      *Amount `json:"transAmount,omitempty"`
	TradeAmount      *Amount `json:"tradeAmount,omitempty"`
	RemainAmount     *Amount `json:"remainAmount,omitempty"`
}
