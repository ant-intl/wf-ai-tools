<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Account\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class AccountInfo extends WfModel
{
    protected const FIELDS = ['accountNo', 'currencyList', 'accountType', 'accountStatus', 'bankAccountList'];
    protected const RELATIONS = ['bankAccountList' => [BankAccount::class]];
}
