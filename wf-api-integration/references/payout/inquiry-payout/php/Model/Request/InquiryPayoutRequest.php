<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryPayoutRequest extends WfModel
{
    protected const FIELDS = ['transferId', 'transferRequestId'];

    public function validate(): void
    {
        $transferId = $this->optionalString('transferId');
        $transferRequestId = $this->optionalString('transferRequestId');
        if (($transferId ?? '') === '' && ($transferRequestId ?? '') === '') {
            throw new InvalidArgumentException('Either transferId or transferRequestId must be provided.');
        }
    }
}
