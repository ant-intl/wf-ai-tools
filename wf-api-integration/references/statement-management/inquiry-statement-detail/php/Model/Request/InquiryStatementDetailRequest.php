<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use {baseNamespace}\Wf\Model\WfModel;

final class InquiryStatementDetailRequest extends WfModel
{
    protected const FIELDS = ['accountingBizNo'];

    public function validate(): void
    {
        $this->requireString('accountingBizNo');
    }
}
