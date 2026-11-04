<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\TradeOrder\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class TradeOrderResult extends WfModel
{
    protected const FIELDS = [
        'referenceOrderNo',
        'orderStatus',
        'orderType',
        'statusMessage',
        'errorCode',
        'transAmount',
        'tradeAmount',
        'remainAmount',
    ];
    protected const RELATIONS = [
        'transAmount' => Amount::class,
        'tradeAmount' => Amount::class,
        'remainAmount' => Amount::class,
    ];
}
