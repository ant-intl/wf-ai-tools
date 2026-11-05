<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use {baseNamespace}\Wf\Model\WfModel;

final class EditBeneficiaryRequest extends WfModel
{
    protected const FIELDS = ['beneficiaryToken', 'beneficiaryNick'];

    public function validate(): void
    {
        $this->requireString('beneficiaryToken');
        $this->requireString('beneficiaryNick');
    }
}
