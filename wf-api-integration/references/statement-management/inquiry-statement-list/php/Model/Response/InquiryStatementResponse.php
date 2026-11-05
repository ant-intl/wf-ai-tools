<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Statement\Model\Domain\StatementRecord;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryStatementResponse extends WfModel
{
    protected const FIELDS = [
        'result',
        'responseId',
        'statementList',
        'totalCount',
        'totalPageNumber',
        'currentPageNumber',
    ];
    protected const RELATIONS = [
        'result' => Result::class,
        'statementList' => [StatementRecord::class],
    ];
}
