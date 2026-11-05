<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use {baseNamespace}\Wf\Beneficiary\Model\Domain\BeneficiaryAlipayAccount;
use {baseNamespace}\Wf\Beneficiary\Model\Domain\BeneficiaryBankAccount;
use {baseNamespace}\Wf\Beneficiary\Model\Domain\ThirdPartyIdentity;
use {baseNamespace}\Wf\Model\WfModel;

final class BindBeneficiaryRequest extends WfModel
{
    protected const FIELDS = [
        'bindBeneficiaryRequestId',
        'beneficiaryType',
        'countryCode',
        'currency',
        'beneficiaryNick',
        'templateCategory',
        'referenceBeneficiaryId',
        'beneficiaryBankAccount',
        'beneficiaryAlipayAccount',
        'thirdPartyIdentity',
    ];
    protected const RELATIONS = [
        'beneficiaryBankAccount' => BeneficiaryBankAccount::class,
        'beneficiaryAlipayAccount' => BeneficiaryAlipayAccount::class,
        'thirdPartyIdentity' => ThirdPartyIdentity::class,
    ];

    public function validate(): void
    {
        $this->requireString('bindBeneficiaryRequestId');
        $this->requireString('beneficiaryType');
    }
}
