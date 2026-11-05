<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Payout\Model\Domain\TransferFromDetail;
use {baseNamespace}\Wf\Payout\Model\Domain\TransferResult;
use {baseNamespace}\Wf\Payout\Model\Domain\TransferToDetail;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryPayoutResponse extends WfModel
{
    protected const FIELDS = [
        'result',
        'transferResult',
        'transferRequestId',
        'transferId',
        'transferFinishTime',
        'chargeMode',
        'transferFromDetail',
        'transferToDetail',
    ];
    protected const RELATIONS = [
        'result' => Result::class,
        'transferResult' => TransferResult::class,
        'transferFromDetail' => TransferFromDetail::class,
        'transferToDetail' => TransferToDetail::class,
    ];
}
