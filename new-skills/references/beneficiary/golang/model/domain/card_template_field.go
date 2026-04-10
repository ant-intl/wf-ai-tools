package domain

// FieldRestriction contains validation rules for a card template field.
type FieldRestriction struct {
	// RestrictionMsg is the restriction message for validation failure (限制提示信息)
	RestrictionMsg string `json:"restrictionMsg,omitempty"`

	// RestrictionRegex is the regex pattern for validation (限制正则表达式)
	RestrictionRegex string `json:"restrictionRegex,omitempty"`

	// RestrictionType is the restriction type, e.g., PATTERN_RESTRICTION (限制类型)
	RestrictionType string `json:"restrictionType,omitempty"`
}

// CardTemplateField represents a single field definition in a card template.
// Returned by inquiryBeneficiaryTemplate to indicate which fields are required
// when binding a beneficiary.
// Note: WF API returns "required" as a string ("Y"/"N"), not a boolean.
type CardTemplateField struct {
	// FieldName is the field name, e.g., bankAccountName, bankBIC, bankAccountNo (字段名称)
	FieldName string `json:"fieldName"`

	// FieldDescription is the field description (字段描述)
	FieldDescription string `json:"fieldDescription,omitempty"`

	// Required indicates whether the field is required: "Y" or "N" (是否必填)
	Required string `json:"required,omitempty"`

	// Restriction contains field restriction rules (字段限制规则)
	Restriction *FieldRestriction `json:"restriction,omitempty"`
}
