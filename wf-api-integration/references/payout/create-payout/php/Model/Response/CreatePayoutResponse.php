<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Payout\Model\Domain\TransferFromDetail;
use {baseNamespace}\Wf\Payout\Model\Domain\TransferToDetail;
use {baseNamespace}\Wf\Model\WfModel;

final class CreatePayoutResponse extends WfModel
{
    protected const FIELDS = [
        'result',
        'transferRequestId',
        'transferId',
        'chargeMode',
        'transferFromDetail',
        'transferToDetail',
    ];
    protected const RELATIONS = [
        'result' => Result::class,
        'transferFromDetail' => TransferFromDetail::class,
        'transferToDetail' => TransferToDetail::class,
    ];

    public function isProcessing(): bool
    {
        return $this->result?->resultStatus === 'S' && $this->result?->resultCode === 'PROCESSING';
    }
}
