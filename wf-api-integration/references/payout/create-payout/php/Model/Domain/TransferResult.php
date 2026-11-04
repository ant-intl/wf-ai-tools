<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Payout\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class TransferResult extends WfModel
{
    protected const FIELDS = ['resultStatus', 'resultCode', 'resultMessage'];

    public function isSuccess(): bool
    {
        return $this->resultCode === 'SUCCESS';
    }

    public function isProcessing(): bool
    {
        return $this->resultCode === 'PROCESSING';
    }
}
