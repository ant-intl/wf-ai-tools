<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use {baseNamespace}\Wf\Model\WfModel;

final class RemoveBeneficiaryRequest extends WfModel
{
    protected const FIELDS = ['removeBeneficiaryRequestId', 'beneficiaryToken'];

    public function validate(): void
    {
        $this->requireString('removeBeneficiaryRequestId');
        $this->requireString('beneficiaryToken');
    }
}
