<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Tests;

use {baseNamespace}\Wf\Client\InquiryAccountInfoClient;
use {baseNamespace}\Wf\Model\Request\InquiryBalanceRequest;

final class InquiryAccountInfoClientTest extends WfIntegrationTestCase
{
    public function testInquiryBalanceSuccess(): void
    {
        $response = (new InquiryAccountInfoClient($this->httpClient()))->inquiryBalance(
            new InquiryBalanceRequest()
        );

        self::assertSame('S', $response->result->resultStatus);
    }
}
