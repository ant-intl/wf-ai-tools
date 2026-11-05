<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Transfer\Model\Domain\TransferFromDetail;
use {baseNamespace}\Wf\Transfer\Model\Domain\TransferToDetail;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryTransferResponse extends WfModel
{
    protected const FIELDS = [
        'result',
        'transferRequestId',
        'transferId',
        'businessSceneCode',
        'transferResult',
        'transferFinishTime',
        'transferFromDetail',
        'transferToDetail',
    ];
    protected const RELATIONS = [
        'result' => Result::class,
        'transferResult' => Result::class,
        'transferFromDetail' => TransferFromDetail::class,
        'transferToDetail' => TransferToDetail::class,
    ];

    public function isTransferProcessing(): bool
    {
        return $this->transferResult?->resultStatus === 'S' && $this->transferResult?->resultCode === 'PROCESSING';
    }

    public function isTransferSuccess(): bool
    {
        return $this->transferResult?->resultStatus === 'S' && $this->transferResult?->resultCode === 'SUCCESS';
    }
}
