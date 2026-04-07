package client

import (
	"fmt"
	"testing"
	"time"

	"{moduleName}/wf/config"
	"{moduleName}/wf/model/domain"
	"{moduleName}/wf/model/exception"
	"{moduleName}/wf/model/request"
	"{moduleName}/wf/signer"
	"{moduleName}/wf/util"
)

func newRealBeneficiaryClient(t *testing.T) *BeneficiaryManagementClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfBaseURL, wfPrivateKey, wfPublicKey)
	s, err := signer.NewWfSigner(wfPrivateKey, wfPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewBeneficiaryManagementClient(util.NewWfHttpClient(cfg, s))
}

// TestIntegration_InquiryBeneficiaryTemplate queries card template for HK USD third-party personal account
func TestIntegration_InquiryBeneficiaryTemplate(t *testing.T) {
	c := newRealBeneficiaryClient(t)

	req := &request.InquiryBeneficiaryTemplateRequest{
		CountryCode:     "HK",
		Currency:        "USD",
		BeneficiaryType: "THIRD_PARTY_PERSONAL_BANK_ACCOUNT",
	}

	resp, err := c.InquiryBeneficiaryTemplate(req)
	if err != nil {
		printBeneficiaryWfError("InquiryBeneficiaryTemplate", err)
		t.FailNow()
	}

	fmt.Printf("[PASS] InquiryBeneficiaryTemplate\n")
	fmt.Printf("  ResponseID: %s\n", resp.ResponseID)
	fmt.Printf("  CardTemplateData fields: %d\n", len(resp.CardTemplateData))
	for _, f := range resp.CardTemplateData {
		fmt.Printf("  - Field: %s | Description: %s | Required: %s\n", f.FieldName, f.FieldDescription, f.Required)
		if f.Restriction != nil {
			fmt.Printf("    Restriction: %s | Regex: %s\n", f.Restriction.RestrictionType, f.Restriction.RestrictionRegex)
		}
	}
	fmt.Printf("  LocalCardTemplateData fields: %d\n", len(resp.LocalCardTemplateData))
	fmt.Printf("  CrossBorderCardTemplateData fields: %d\n", len(resp.CrossBorderCardTemplateData))
}

// TestIntegration_BindBeneficiary binds a USD/HK third-party personal bank account
func TestIntegration_BindBeneficiary(t *testing.T) {
	c := newRealBeneficiaryClient(t)

	req := &request.BindBeneficiaryRequest{
		BindBeneficiaryRequestID: fmt.Sprintf("bind-test-%d", time.Now().UnixMilli()),
		BeneficiaryType:          "THIRD_PARTY_PERSONAL_BANK_ACCOUNT",
		CountryCode:              "HK",
		Currency:                 "USD",
		BeneficiaryNick:          "TestBeneficiary",
		BeneficiaryBankAccount: &domain.BeneficiaryBankAccount{
			BankAccountName:        "vaL2LTest",
			BankAccountNo:          "100100004623",
			BankName:               "STARK bankName",
			BankBIC:                "CITIHKHX",
			BeneficiaryCountryCode: "HK",
		},
	}

	resp, err := c.BindBeneficiary(req)
	if err != nil {
		printBeneficiaryWfError("BindBeneficiary", err)
		t.FailNow()
	}

	fmt.Printf("[PASS] BindBeneficiary\n")
	if resp.Beneficiary != nil {
		fmt.Printf("  BeneficiaryToken: %s\n", resp.Beneficiary.BeneficiaryToken)
		fmt.Printf("  BeneficiaryNick: %s\n", resp.Beneficiary.BeneficiaryNick)
		fmt.Printf("  Status: %s\n", resp.Beneficiary.Status)
	}
}

// TestIntegration_InquiryBeneficiaryList queries the first page of bound beneficiaries
func TestIntegration_InquiryBeneficiaryList(t *testing.T) {
	c := newRealBeneficiaryClient(t)

	req := &request.InquiryBeneficiaryListRequest{
		PageSize:   10,
		PageNumber: 1,
	}

	resp, err := c.InquiryBeneficiaryList(req)
	if err != nil {
		printBeneficiaryWfError("InquiryBeneficiaryList", err)
		t.FailNow()
	}

	fmt.Printf("[PASS] InquiryBeneficiaryList\n")
	fmt.Printf("  ResponseID: %s\n", resp.ResponseID)
	fmt.Printf("  TotalCount: %d | TotalPages: %d | CurrentPage: %d\n",
		resp.TotalCount, resp.TotalPageNumber, resp.CurrentPageNumber)
	for _, b := range resp.Beneficiaries {
		fmt.Printf("  - Token: %s | Nick: %s | Type: %s | Status: %s\n",
			b.BeneficiaryToken, b.BeneficiaryNick, b.BeneficiaryType, b.Status)
	}
}

// TestIntegration_RemoveBeneficiary removes a beneficiary by token.
// Replace the placeholder with an actual token obtained from BindBeneficiary.
func TestIntegration_RemoveBeneficiary(t *testing.T) {
	c := newRealBeneficiaryClient(t)

	req := &request.RemoveBeneficiaryRequest{
		RemoveBeneficiaryRequestID: fmt.Sprintf("remove-test-%d", time.Now().UnixMilli()),
		BeneficiaryToken:           "replace-with-real-beneficiary-token",
	}

	resp, err := c.RemoveBeneficiary(req)
	if err != nil {
		printBeneficiaryWfError("RemoveBeneficiary", err)
		t.FailNow()
	}

	fmt.Printf("[PASS] RemoveBeneficiary\n")
	fmt.Printf("  RemovedToken: %s\n", resp.BeneficiaryToken)
}

// TestIntegration_EditBeneficiary updates the nick name of an existing beneficiary.
// Replace the placeholder with an actual token obtained from BindBeneficiary.
func TestIntegration_EditBeneficiary(t *testing.T) {
	c := newRealBeneficiaryClient(t)

	req := &request.EditBeneficiaryRequest{
		BeneficiaryToken: "replace-with-real-beneficiary-token",
		BeneficiaryNick:  "UpdatedNick",
	}

	resp, err := c.EditBeneficiary(req)
	if err != nil {
		printBeneficiaryWfError("EditBeneficiary", err)
		t.FailNow()
	}

	fmt.Printf("[PASS] EditBeneficiary\n")
	fmt.Printf("  BeneficiaryToken: %s\n", resp.BeneficiaryToken)
}

// printBeneficiaryWfError prints WF error details in a consistent format
func printBeneficiaryWfError(api string, err error) {
	if wfErr, ok := err.(*exception.WfException); ok {
		fmt.Printf("[FAIL] %s — Code: %s | Message: %s\n", api, wfErr.Code, wfErr.Message)
	} else {
		fmt.Printf("[FAIL] %s — Error: %v\n", api, err)
	}
}
