<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\TradeOrder\Model\Domain\TradeOrderResult;
use {baseNamespace}\Wf\Model\WfModel;

final class NotifyTradeOrderRequest extends WfModel
{
    protected const FIELDS = ['requestId', 'tradeOrderResults'];
    protected const RELATIONS = ['tradeOrderResults' => [TradeOrderResult::class]];

    public function validate(): void
    {
        $this->requireString('requestId');
        if (!is_array($this->tradeOrderResults)) {
            throw new InvalidArgumentException('tradeOrderResults is required.');
        }
    }
}
