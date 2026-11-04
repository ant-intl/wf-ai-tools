<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\TradeOrder\Model\Domain\TradeOrder;
use {baseNamespace}\Wf\Model\WfModel;

final class SubmitTradeOrderRequest extends WfModel
{
    protected const FIELDS = [
        'requestId',
        'sceneCode',
        'quotaAccumulationMethod',
        'quotaAccumulationId',
        'tradeOrders',
        'notifyUrl',
        'platform',
        'extendInfo',
    ];
    protected const RELATIONS = ['tradeOrders' => [TradeOrder::class]];

    public function validate(): void
    {
        if (!is_string($this->requestId) || $this->requestId === '' || strlen($this->requestId) > 64) {
            throw new InvalidArgumentException('requestId is required and must not exceed 64 characters.');
        }
        $this->requireString('quotaAccumulationMethod');
        $this->requireString('quotaAccumulationId');
        $orders = $this->tradeOrders;
        if (!is_array($orders) || $orders === []) {
            throw new InvalidArgumentException('tradeOrders must not be empty.');
        }

        $scene = $this->sceneCode;
        if ($scene === 'PAY_INTO_CHINA') {
            if (count($orders) > 100) {
                throw new InvalidArgumentException('PAY_INTO_CHINA supports at most 100 trade orders.');
            }
            $this->requireString('platform');
        } elseif ($scene === 'CREATE_B2B_ORDERS') {
            if (count($orders) > 10) {
                throw new InvalidArgumentException('CREATE_B2B_ORDERS supports at most 10 trade orders.');
            }
        } else {
            throw new InvalidArgumentException('Unsupported sceneCode.');
        }
    }
}
