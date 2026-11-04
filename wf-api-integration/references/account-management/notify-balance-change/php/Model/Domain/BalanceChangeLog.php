<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Account\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class BalanceChangeLog extends WfModel
{
    protected const FIELDS = [
        'balanceChangeTime',
        'accountNo',
        'balanceType',
        'accountingBizNo',
        'transactionAmount',
        'accountBalance',
        'transactionType',
        'transactionId',
        'extTransactionId',
        'beneficiaryName',
        'beneficiaryAccountNo',
        'remarks',
    ];
    protected const RELATIONS = [
        'transactionAmount' => Amount::class,
        'accountBalance' => Amount::class,
    ];
}
