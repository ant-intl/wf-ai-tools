<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Beneficiary\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class BeneficiaryBankAccount extends WfModel
{
    protected const FIELDS = [
        'bankAccountName',
        'bankAccountNo',
        'bankName',
        'bankBIC',
        'bankAccountIBAN',
        'routingNumber',
        'beneficiaryAddress',
        'beneficiaryCountryCode',
        'beneficiaryPhone',
        'bankBranchCode',
        'bankLocalName',
        'bankAccountLocalName',
        'bankCountryCode',
    ];
}
