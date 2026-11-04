<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Transfer\Model\Domain\TransferFromDetail;
use {baseNamespace}\Wf\Transfer\Model\Domain\TransferToDetail;
use {baseNamespace}\Wf\Model\WfModel;

final class ConsultTransferResponse extends WfModel
{
    protected const FIELDS = ['result', 'transferFromDetail', 'transferToDetail'];
    protected const RELATIONS = [
        'result' => Result::class,
        'transferFromDetail' => TransferFromDetail::class,
        'transferToDetail' => TransferToDetail::class,
    ];
}
