<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Transfer\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class TransferToDetail extends WfModel
{
    protected const FIELDS = [
        'transferToMethod',
        'transferToAmount',
        'actualTransferToAmount',
        'feeAmount',
        'transferQuote',
        'needMigrateMaterial',
        'transferNotifyUrl',
        'transferRemark',
        'transferMemo',
        'extendInfo',
        'purposeCode',
    ];

    protected const RELATIONS = [
        'transferToMethod' => PaymentMethod::class,
        'transferToAmount' => Amount::class,
        'actualTransferToAmount' => Amount::class,
        'feeAmount' => Amount::class,
        'transferQuote' => Quote::class,
    ];
}
