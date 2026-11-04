<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Payout\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class PaymentMethodMetaData extends WfModel
{
    protected const FIELDS = [
        'bankAccountName',
        'bankAccountNo',
        'bankName',
        'bankBIC',
        'bankAccountIBAN',
        'routingNumber',
        'beneficiaryAddress',
        'bankCountryCode',
        'beneficiaryPhone',
        'bankBranchCode',
        'bankLocalName',
        'bankAccountLocalName',
        'beneficiaryType',
    ];
}
