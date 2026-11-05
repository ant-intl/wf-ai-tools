<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use {baseNamespace}\Wf\Account\Model\Domain\Amount;
use {baseNamespace}\Wf\Account\Model\Domain\PayerBankAccount;
use {baseNamespace}\Wf\Account\Model\Domain\VostroBeneficiaryAccount;
use {baseNamespace}\Wf\Model\Response\Result;
use {baseNamespace}\Wf\Model\WfModel;

final class NotifyVostroRequest extends WfModel
{
    protected const FIELDS = [
        'fundingId',
        'balanceResult',
        'payerBankAccount',
        'beneficiaryAccount',
        'balanceChangeAmount',
        'balanceChangeTime',
        'remitInfo',
    ];
    protected const RELATIONS = [
        'balanceResult' => Result::class,
        'payerBankAccount' => PayerBankAccount::class,
        'beneficiaryAccount' => VostroBeneficiaryAccount::class,
        'balanceChangeAmount' => Amount::class,
    ];

    public function validate(): void
    {
        $this->requireString('fundingId');
    }
}
