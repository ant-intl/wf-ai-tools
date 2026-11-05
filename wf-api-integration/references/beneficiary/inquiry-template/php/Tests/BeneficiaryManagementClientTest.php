<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Tests;

use {baseNamespace}\Wf\Client\BeneficiaryManagementClient;
use {baseNamespace}\Wf\Model\Request\InquiryBeneficiaryTemplateRequest;

final class BeneficiaryManagementClientTest extends WfIntegrationTestCase
{
    public function testInquiryBeneficiaryTemplateSuccess(): void
    {
        $request = new InquiryBeneficiaryTemplateRequest([
            'countryCode' => 'HK',
            'currency' => 'USD',
            'beneficiaryType' => 'THIRD_PARTY_PERSONAL_BANK_ACCOUNT',
        ]);
        $response = (new BeneficiaryManagementClient($this->httpClient()))->inquiryBeneficiaryTemplate($request);

        self::assertSame('S', $response->result->resultStatus);
    }
}
