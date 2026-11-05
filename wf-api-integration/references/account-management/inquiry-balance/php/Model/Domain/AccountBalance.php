<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Account\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class AccountBalance extends WfModel
{
    protected const FIELDS = [
        'accountNo',
        'currency',
        'balanceType',
        'totalBalance',
        'availableBalance',
        'frozenBalance',
        'budgetAccountId',
    ];
    protected const RELATIONS = [
        'totalBalance' => Money::class,
        'availableBalance' => Money::class,
        'frozenBalance' => Money::class,
    ];
}
