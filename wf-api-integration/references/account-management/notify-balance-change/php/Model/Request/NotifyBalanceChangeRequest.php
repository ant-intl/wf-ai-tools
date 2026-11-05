<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\Account\Model\Domain\BalanceChangeLog;
use {baseNamespace}\Wf\Model\WfModel;

final class NotifyBalanceChangeRequest extends WfModel
{
    protected const FIELDS = ['notifySequence', 'balanceChangeLogs'];
    protected const RELATIONS = ['balanceChangeLogs' => [BalanceChangeLog::class]];

    public function validate(): void
    {
        if (!is_int($this->notifySequence)) {
            throw new InvalidArgumentException('notifySequence is required.');
        }
        if (!is_array($this->balanceChangeLogs)) {
            throw new InvalidArgumentException('balanceChangeLogs is required.');
        }
    }
}
