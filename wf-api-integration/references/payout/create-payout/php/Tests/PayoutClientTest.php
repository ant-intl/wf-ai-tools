<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Tests;

use {baseNamespace}\Wf\Client\PayoutClient;
use {baseNamespace}\Wf\Model\Request\ConsultPayoutRequest;

final class PayoutClientTest extends WfIntegrationTestCase
{
    public function testConsultPayoutSuccess(): void
    {
        $beneficiaryToken = $this->env('WF_TEST_BENEFICIARY_TOKEN');
        if ($beneficiaryToken === '') {
            self::markTestSkipped('Set WF_TEST_BENEFICIARY_TOKEN to an existing beneficiary token.');
        }

        $request = new ConsultPayoutRequest([
            'transferFromDetail' => ['transferFromAmount' => ['currency' => 'USD', 'value' => 100]],
            'transferToDetail' => [
                'transferToAmount' => ['currency' => 'USD'],
                'transferToMethod' => [
                    'paymentMethodType' => 'BENEFICIARY_TOKEN',
                    'paymentMethodId' => $beneficiaryToken,
                ],
            ],
        ]);
        $response = (new PayoutClient($this->httpClient()))->consultPayout($request);

        self::assertSame('S', $response->result->resultStatus);
    }
}
