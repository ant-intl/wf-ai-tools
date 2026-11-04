<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use DateTimeImmutable;
use Exception;
use InvalidArgumentException;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryStatementRequest extends WfModel
{
    protected const FIELDS = [
        'startTime',
        'endTime',
        'pageSize',
        'pageNumber',
        'transactionTypeList',
        'currencyList',
        'balanceTypes',
        'budgetAccountIds',
        'fuzzyName',
    ];

    public function validate(): void
    {
        if (!is_string($this->startTime) || $this->startTime === '' || !is_string($this->endTime) || $this->endTime === '') {
            throw new InvalidArgumentException('startTime and endTime are required.');
        }
        if (!is_int($this->pageNumber) || $this->pageNumber < 1 || $this->pageNumber > 50) {
            throw new InvalidArgumentException('pageNumber must be between 1 and 50.');
        }
        $this->pageSize = 10;

        if (trim($this->optionalString('fuzzyName') ?? '') === '') {
            $this->validateTimeRange();
        }
    }

    private function validateTimeRange(): void
    {
        try {
            $start = new DateTimeImmutable($this->startTime);
            $end = new DateTimeImmutable($this->endTime);
        } catch (Exception $exception) {
            throw new InvalidArgumentException('startTime and endTime must use ISO 8601 format.', 0, $exception);
        }

        $seconds = $end->getTimestamp() - $start->getTimestamp();
        if ($seconds < 0 || $seconds > 100 * 86400) {
            throw new InvalidArgumentException('Time range must be between 0 and 100 days when fuzzyName is not set.');
        }
    }
}
