<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryTradeOrderRequest extends WfModel
{
    protected const FIELDS = [
        'requestId',
        'sceneCode',
        'quotaAccumulationMethod',
        'quotaAccumulationId',
        'tradeType',
    ];

    public function validate(): void
    {
        $this->requireString('requestId');
        if ($this->requireString('sceneCode') !== 'PAY_INTO_CHINA') {
            throw new InvalidArgumentException('sceneCode must be PAY_INTO_CHINA.');
        }
        $this->requireString('quotaAccumulationMethod');
        $this->requireString('quotaAccumulationId');
        if (!in_array($this->requireString('tradeType'), ['GOODS', 'SERVICE'], true)) {
            throw new InvalidArgumentException('tradeType must be GOODS or SERVICE.');
        }
    }
}
