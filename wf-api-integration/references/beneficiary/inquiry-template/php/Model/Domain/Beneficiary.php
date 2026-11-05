<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Beneficiary\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class Beneficiary extends WfModel
{
    protected const FIELDS = [
        'beneficiaryToken',
        'beneficiaryNick',
        'beneficiaryType',
        'status',
        'referenceBeneficiaryId',
    ];
}
