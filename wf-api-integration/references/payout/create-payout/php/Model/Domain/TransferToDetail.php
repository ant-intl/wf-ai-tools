<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Payout\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class TransferToDetail extends WfModel
{
    protected const FIELDS = [
        'transferToAmount',
        'transferToMethod',
        'transferQuote',
        'purposeCode',
        'transferNotifyUrl',
        'feeAmount',
    ];
    protected const RELATIONS = [
        'transferToAmount' => Amount::class,
        'transferToMethod' => TransferToMethod::class,
        'transferQuote' => TransferQuote::class,
        'feeAmount' => Amount::class,
    ];
}
