<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Beneficiary\Model\Domain\Beneficiary;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryBeneficiaryListResponse extends WfModel
{
    protected const FIELDS = [
        'result',
        'responseId',
        'beneficiaries',
        'totalCount',
        'totalPageNumber',
        'currentPageNumber',
    ];
    protected const RELATIONS = [
        'result' => Result::class,
        'beneficiaries' => [Beneficiary::class],
    ];
}
