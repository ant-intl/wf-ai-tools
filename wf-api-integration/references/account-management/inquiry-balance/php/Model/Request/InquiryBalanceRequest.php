<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryBalanceRequest extends WfModel
{
    protected const FIELDS = ['currencyList', 'balanceTypes', 'budgetAccountId'];

    public function validate(): void
    {
        $types = $this->balanceTypes ?? [];
        if (!is_array($types)) {
            throw new InvalidArgumentException('balanceTypes must be an array.');
        }
        if (in_array('BUDGET_BALANCE', $types, true)) {
            $this->requireString('budgetAccountId');
        }
    }
}
