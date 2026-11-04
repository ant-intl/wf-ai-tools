<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Tests;

use DateTimeImmutable;
use DateTimeZone;
use {baseNamespace}\Wf\Client\StatementClient;
use {baseNamespace}\Wf\Model\Request\InquiryStatementRequest;

final class StatementClientTest extends WfIntegrationTestCase
{
    public function testInquiryStatementListSuccess(): void
    {
        $end = new DateTimeImmutable('now', new DateTimeZone('Asia/Shanghai'));
        $start = $end->modify('-1 day');
        $request = new InquiryStatementRequest([
            'startTime' => $start->format('Y-m-d\TH:i:sP'),
            'endTime' => $end->format('Y-m-d\TH:i:sP'),
            'pageNumber' => 1,
        ]);
        $response = (new StatementClient($this->httpClient()))->inquiryStatementList($request);

        self::assertSame('S', $response->result->resultStatus);
        self::assertSame(10, $request->pageSize);
    }
}
