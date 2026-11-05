<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Account\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class Customer extends WfModel
{
    protected const FIELDS = [
        'accountId',
        'referenceCustomerId',
        'customerCompanyName',
        'certificateList',
        'legalEntityType',
        'logonId',
        'verificationLevel',
    ];
    protected const RELATIONS = ['certificateList' => [Certificate::class]];
}
