<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Beneficiary\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class BeneficiaryAlipayAccount extends WfModel
{
    protected const FIELDS = ['alipayAccountName', 'alipayAccountId'];
}
