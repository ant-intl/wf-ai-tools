<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use {baseNamespace}\Wf\Transfer\Model\Domain\TransferFromDetail;
use {baseNamespace}\Wf\Transfer\Model\Domain\TransferToDetail;
use {baseNamespace}\Wf\Model\Response\Result;
use {baseNamespace}\Wf\Model\WfModel;

final class NotifyTransferRequest extends WfModel
{
    protected const FIELDS = [
        'transferResult',
        'transferRequestId',
        'transferId',
        'transferFinishTime',
        'transferFromDetail',
        'transferToDetail',
        'transferOrderAddition',
    ];
    protected const RELATIONS = [
        'transferResult' => Result::class,
        'transferFromDetail' => TransferFromDetail::class,
        'transferToDetail' => TransferToDetail::class,
        'transferOrderAddition' => TransferOrderAddition::class,
    ];

    public function validate(): void
    {
        $this->requireString('transferRequestId');
    }

    public function isTransferSuccess(): bool
    {
        return $this->transferResult?->resultStatus === 'S' && $this->transferResult?->resultCode === 'SUCCESS';
    }
}
