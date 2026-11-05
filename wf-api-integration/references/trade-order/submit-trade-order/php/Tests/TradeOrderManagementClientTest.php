<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Tests;

use {baseNamespace}\Wf\Client\TradeOrderManagementClient;
use {baseNamespace}\Wf\Model\Request\InquiryTradeOrderRequest;

final class TradeOrderManagementClientTest extends WfIntegrationTestCase
{
    public function testInquiryTradeOrderSuccess(): void
    {
        $requestId = $this->env('WF_TEST_TRADE_ORDER_REQUEST_ID');
        $quotaId = $this->env('WF_TEST_QUOTA_ACCUMULATION_ID');
        if ($requestId === '' || $quotaId === '') {
            self::markTestSkipped('Set WF_TEST_TRADE_ORDER_REQUEST_ID and WF_TEST_QUOTA_ACCUMULATION_ID.');
        }

        $request = new InquiryTradeOrderRequest([
            'requestId' => $requestId,
            'sceneCode' => 'PAY_INTO_CHINA',
            'quotaAccumulationMethod' => 'USER_ID',
            'quotaAccumulationId' => $quotaId,
            'tradeType' => 'GOODS',
        ]);
        $response = (new TradeOrderManagementClient($this->httpClient()))->inquiryTradeOrder($request);

        self::assertSame('S', $response->result->resultStatus);
    }
}
