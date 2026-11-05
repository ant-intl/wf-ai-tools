<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use {baseNamespace}\Wf\Model\Response\Result;
use {baseNamespace}\Wf\Model\WfModel;

final class NotifyBindBeneficiaryRequest extends WfModel
{
    protected const FIELDS = ['bindBeneficiaryRequestId', 'result', 'beneficiary'];
    protected const RELATIONS = [
        'result' => Result::class,
        'beneficiary' => NotifyBeneficiary::class,
    ];

    public function validate(): void
    {
        $this->requireString('bindBeneficiaryRequestId');
    }
}
