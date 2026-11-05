<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Account\Model\Domain\Amount;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryAvailableQuotaResponse extends WfModel
{
    protected const FIELDS = [
        'result',
        'quotaAccumulationMethod',
        'quotaAccumulationId',
        'availableQuota',
        'tradeType',
    ];
    protected const RELATIONS = [
        'result' => Result::class,
        'availableQuota' => Amount::class,
    ];
}
