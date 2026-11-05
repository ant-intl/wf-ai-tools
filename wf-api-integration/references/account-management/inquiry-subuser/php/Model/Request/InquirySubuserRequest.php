<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\Model\WfModel;

final class InquirySubuserRequest extends WfModel
{
    protected const FIELDS = ['pageSize', 'pageNumber'];

    public function validate(): void
    {
        if (!is_int($this->pageSize) || $this->pageSize < 1 || !is_int($this->pageNumber) || $this->pageNumber < 1) {
            throw new InvalidArgumentException('pageSize and pageNumber must be positive integers.');
        }
    }
}
