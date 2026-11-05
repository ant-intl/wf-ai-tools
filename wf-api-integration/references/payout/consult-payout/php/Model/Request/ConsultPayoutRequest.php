<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\Payout\Model\Domain\TransferFromDetail;
use {baseNamespace}\Wf\Payout\Model\Domain\TransferToDetail;
use {baseNamespace}\Wf\Model\WfModel;

final class ConsultPayoutRequest extends WfModel
{
    protected const FIELDS = ['transferFromDetail', 'transferToDetail', 'businessSceneCode'];
    protected const RELATIONS = [
        'transferFromDetail' => TransferFromDetail::class,
        'transferToDetail' => TransferToDetail::class,
    ];

    public function validate(): void
    {
        $fromCurrency = $this->transferFromDetail?->transferFromAmount?->currency;
        $toCurrency = $this->transferToDetail?->transferToAmount?->currency;
        if (!is_string($fromCurrency) || $fromCurrency === '' || !is_string($toCurrency) || $toCurrency === '') {
            throw new InvalidArgumentException('Both transferFromAmount.currency and transferToAmount.currency are required.');
        }
        if ($toCurrency === 'CNY') {
            $this->requireString('businessSceneCode');
        }
    }
}
