<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use {baseNamespace}\Wf\Payout\Model\Domain\TransferFromDetail;
use {baseNamespace}\Wf\Payout\Model\Domain\TransferResult;
use {baseNamespace}\Wf\Payout\Model\Domain\TransferToDetail;
use {baseNamespace}\Wf\Model\WfModel;

final class NotifyPayoutRequest extends WfModel
{
    protected const FIELDS = [
        'transferResult',
        'transferRequestId',
        'transferId',
        'transferFinishTime',
        'chargeMode',
        'transferFromDetail',
        'transferToDetail',
    ];
    protected const RELATIONS = [
        'transferResult' => TransferResult::class,
        'transferFromDetail' => TransferFromDetail::class,
        'transferToDetail' => TransferToDetail::class,
    ];

    public function validate(): void
    {
        $this->requireString('transferRequestId');
    }
}
