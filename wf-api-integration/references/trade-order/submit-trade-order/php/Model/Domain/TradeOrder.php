<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\TradeOrder\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class TradeOrder extends WfModel
{
    protected const FIELDS = [
        'referenceOrderNo',
        'paymentTime',
        'transAmount',
        'tradeAmount',
        'tradeType',
        'orderTime',
        'orderType',
        'merchant',
        'seller',
        'buyer',
        'goods',
        'shipping',
        'tradeTerms',
        'isUsedForExchange',
        'bizContractInfo',
        'logisticsMode',
        'market',
        'revenueShare',
    ];
    protected const RELATIONS = [
        'transAmount' => Amount::class,
        'tradeAmount' => Amount::class,
        'merchant' => Merchant::class,
        'seller' => Customer::class,
        'buyer' => Buyer::class,
        'goods' => [Goods::class],
        'shipping' => Shipping::class,
        'bizContractInfo' => BizContractInfo::class,
    ];
}
