package domain

// Amount represents a monetary amount in WF API.
// Value is in minor units (e.g. cents): USD 10.00 = value 1000.
// 2-decimal currencies (USD/EUR/GBP/CNY etc): value = face_amount × 100
// 0-decimal currencies (JPY/KRW): value = face_amount × 1
type Amount struct {
	Currency string `json:"currency"`
	Value    *int64 `json:"value,omitempty"`
}

// TransferFromDetail represents the payer detail in transfer
type TransferFromDetail struct {
	TransferFromAmount *Amount `json:"transferFromAmount,omitempty"`
}

// TransferToDetail represents the payee detail in transfer
type TransferToDetail struct {
	TransferToAmount *Amount `json:"transferToAmount,omitempty"`
}

