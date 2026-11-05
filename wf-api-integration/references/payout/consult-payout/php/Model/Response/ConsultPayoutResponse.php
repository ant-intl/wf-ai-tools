<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Payout\Model\Domain\Amount;
use {baseNamespace}\Wf\Payout\Model\Domain\TransferFromDetail;
use {baseNamespace}\Wf\Payout\Model\Domain\TransferToDetail;
use {baseNamespace}\Wf\Model\WfModel;

final class ConsultPayoutResponse extends WfModel
{
    protected const FIELDS = ['result', 'chargeMode', 'transferFromDetail', 'transferToDetail', 'availableQuota'];
    protected const RELATIONS = [
        'result' => Result::class,
        'transferFromDetail' => TransferFromDetail::class,
        'transferToDetail' => TransferToDetail::class,
        'availableQuota' => Amount::class,
    ];

    public function getQuoteId(): string
    {
        return $this->transferToDetail?->transferQuote?->quoteId ?? '';
    }
}
