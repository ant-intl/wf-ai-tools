<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\TradeOrder\Model\Domain\TradeOrderResult;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryTradeOrderResponse extends WfModel
{
    protected const FIELDS = ['result', 'requestId', 'batchStatus', 'tradeOrderResults'];
    protected const RELATIONS = [
        'result' => Result::class,
        'tradeOrderResults' => [TradeOrderResult::class],
    ];

    public function isProcessing(): bool
    {
        return $this->batchStatus === 'PROCESSING';
    }

    public function isFinished(): bool
    {
        return $this->batchStatus === 'FINISHED';
    }
}
