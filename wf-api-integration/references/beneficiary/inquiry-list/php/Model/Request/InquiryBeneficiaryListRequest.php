<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryBeneficiaryListRequest extends WfModel
{
    protected const FIELDS = [
        'pageSize',
        'pageNumber',
        'beneficiaryToken',
        'beneficiaryNick',
        'referenceBeneficiaryId',
        'bankAccountNo',
        'bankAccountIBAN',
        'currencyList',
        'accountName',
        'assetType',
        'relationFilter',
    ];

    public function validate(): void
    {
        if (!is_int($this->pageSize) || $this->pageSize < 1 || $this->pageSize > 50) {
            throw new InvalidArgumentException('pageSize must be between 1 and 50.');
        }
        if (!is_int($this->pageNumber) || $this->pageNumber < 1) {
            throw new InvalidArgumentException('pageNumber must be greater than or equal to 1.');
        }
    }
}
