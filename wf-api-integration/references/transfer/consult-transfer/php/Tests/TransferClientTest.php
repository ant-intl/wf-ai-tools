<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Tests;

use {baseNamespace}\Wf\Client\TransferClient;
use {baseNamespace}\Wf\Model\Request\ConsultTransferRequest;

final class TransferClientTest extends WfIntegrationTestCase
{
    public function testConsultTransferSuccess(): void
    {
        $request = new ConsultTransferRequest([
            'transferFromDetail' => ['transferFromAmount' => ['currency' => 'USD']],
            'transferToDetail' => ['transferToAmount' => ['currency' => 'USD', 'value' => 1]],
        ]);
        $response = (new TransferClient($this->httpClient()))->consultTransfer($request);

        self::assertSame('S', $response->result->resultStatus);
    }
}
