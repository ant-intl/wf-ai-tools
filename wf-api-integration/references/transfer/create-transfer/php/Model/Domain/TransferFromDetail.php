<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Transfer\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class TransferFromDetail extends WfModel
{
    protected const FIELDS = [
        'transferFromMethod',
        'transferFromAmount',
        'actualTransferFromAmount',
        'feeAmount',
    ];

    protected const RELATIONS = [
        'transferFromMethod' => PaymentMethod::class,
        'transferFromAmount' => Amount::class,
        'actualTransferFromAmount' => Amount::class,
        'feeAmount' => Amount::class,
    ];
}
