<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryTransferRequest extends WfModel
{
    protected const FIELDS = ['transferRequestId'];

    public function validate(): void
    {
        if (!is_string($this->transferRequestId) || $this->transferRequestId === '' || strlen($this->transferRequestId) > 64) {
            throw new InvalidArgumentException('transferRequestId is required and must not exceed 64 characters.');
        }
    }
}
