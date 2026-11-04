<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use {baseNamespace}\Wf\Model\WfModel;

final class NotifyBeneficiary extends WfModel
{
    protected const FIELDS = [
        'beneficiaryToken',
        'bindBeneficiaryRequestId',
        'beneficiaryBankAccount',
        'countryCode',
        'currency',
        'beneficiaryType',
        'beneficiaryNick',
        'status',
        'referenceBeneficiaryId',
    ];
}
