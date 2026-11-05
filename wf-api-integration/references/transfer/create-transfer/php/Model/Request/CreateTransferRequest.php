<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\Transfer\Model\Domain\Amount;
use {baseNamespace}\Wf\Transfer\Model\Domain\TransferFromDetail;
use {baseNamespace}\Wf\Transfer\Model\Domain\TransferToDetail;
use {baseNamespace}\Wf\Model\WfModel;

final class CreateTransferRequest extends WfModel
{
    protected const FIELDS = ['transferRequestId', 'businessSceneCode', 'transferFromDetail', 'transferToDetail'];
    protected const RELATIONS = [
        'transferFromDetail' => TransferFromDetail::class,
        'transferToDetail' => TransferToDetail::class,
    ];

    public function validate(): void
    {
        if (!is_string($this->transferRequestId) || $this->transferRequestId === '' || strlen($this->transferRequestId) > 64) {
            throw new InvalidArgumentException('transferRequestId is required and must not exceed 64 characters.');
        }
        $fromAmount = $this->transferFromDetail?->transferFromAmount;
        $toAmount = $this->transferToDetail?->transferToAmount;
        if (!$fromAmount instanceof Amount || !is_string($fromAmount->currency) || $fromAmount->currency === '') {
            throw new InvalidArgumentException('transferFromDetail.transferFromAmount.currency is required.');
        }
        if (!$toAmount instanceof Amount || !is_string($toAmount->currency) || $toAmount->currency === '') {
            throw new InvalidArgumentException('transferToDetail.transferToAmount.currency is required.');
        }
        if ($fromAmount->value === null && $toAmount->value === null) {
            throw new InvalidArgumentException('Either transferFromAmount.value or transferToAmount.value is required.');
        }
    }
}
