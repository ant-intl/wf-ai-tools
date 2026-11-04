<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Account\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class BankAccount extends WfModel
{
    protected const FIELDS = [
        'bankAccountNo',
        'holderName',
        'holderAccountType',
        'holderAddress',
        'currencyList',
        'bankName',
        'routingNumber',
        'bankAddress',
        'bankRegion',
        'bankBIC',
        'bankAccountIBAN',
        'bankAccountBSB',
        'accountCreationDate',
        'bankCode',
        'branchCode',
        'bankAccountType',
        'collectionArea',
        'wireRoutingNumber',
        'sortCode',
    ];
    protected const RELATIONS = [
        'holderName' => UserName::class,
        'holderAddress' => Address::class,
        'bankAddress' => Address::class,
    ];
}
