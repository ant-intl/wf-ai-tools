<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Statement\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class StatementRecord extends WfModel
{
    protected const FIELDS = [
        'transactionId',
        'transactionTime',
        'transactionType',
        'transactionStatus',
        'balanceType',
        'accountBalance',
        'feeAmount',
        'netAmount',
        'originalTransactionAmount',
        'receiveAmount',
        'transactionAmount',
        'goodsAmount',
        'platformFeeAmount',
        'originalFeeAmount',
        'discountFeeAmount',
        'extTransactionId',
        'accountingBizNo',
        'goodsName',
        'foreignExchangeQuote',
        'refundForeignExchangeQuote',
        'fundMoveDetail',
        'operatorInfo',
    ];
    protected const RELATIONS = [
        'accountBalance' => Amount::class,
        'feeAmount' => Amount::class,
        'netAmount' => Amount::class,
        'originalTransactionAmount' => Amount::class,
        'receiveAmount' => Amount::class,
        'transactionAmount' => Amount::class,
        'goodsAmount' => Amount::class,
        'platformFeeAmount' => Amount::class,
        'originalFeeAmount' => Amount::class,
        'discountFeeAmount' => Amount::class,
        'foreignExchangeQuote' => ForeignExchangeQuote::class,
        'refundForeignExchangeQuote' => ForeignExchangeQuote::class,
        'fundMoveDetail' => FundMoveDetail::class,
        'operatorInfo' => OperatorInfo::class,
    ];
}
