<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\TradeOrder\Model\Domain\TradeOrderResult;
use {baseNamespace}\Wf\Model\WfModel;

final class SubmitTradeOrderResponse extends WfModel
{
    protected const FIELDS = ['result', 'requestId', 'tradeOrderResult', 'acceptOrderId'];
    protected const RELATIONS = [
        'result' => Result::class,
        'tradeOrderResult' => [TradeOrderResult::class],
    ];

    public function isProcessing(): bool
    {
        return $this->result?->resultStatus === 'S' && $this->result?->resultCode === 'PROCESSING';
    }
}
