<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Statement\Model\Domain\Amount;
use {baseNamespace}\Wf\Statement\Model\Domain\ForeignExchangeQuote;
use {baseNamespace}\Wf\Statement\Model\Domain\FundMoveDetail;
use {baseNamespace}\Wf\Statement\Model\Domain\OperatorInfo;
use {baseNamespace}\Wf\Statement\Model\Domain\RelatedStatement;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryStatementDetailResponse extends WfModel
{
    protected const FIELDS = [
        'result',
        'responseId',
        'transactionId',
        'extTransactionId',
        'transactionStatus',
        'transactionTime',
        'transactionType',
        'transactionAmount',
        'originalTransactionAmount',
        'feeAmount',
        'feeItemType',
        'netAmount',
        'receiveAmount',
        'accountBalance',
        'fundMoveDetail',
        'foreignExchangeQuote',
        'refundForeignExchangeQuote',
        'balanceType',
        'accountingBizNo',
        'failReason',
        'combinedTransactionList',
        'operatorInfo',
        'goodsName',
        'goodsAmount',
        'originalFeeAmount',
        'discountFeeAmount',
    ];
    protected const RELATIONS = [
        'result' => Result::class,
        'transactionAmount' => Amount::class,
        'originalTransactionAmount' => Amount::class,
        'feeAmount' => Amount::class,
        'netAmount' => Amount::class,
        'receiveAmount' => Amount::class,
        'accountBalance' => Amount::class,
        'fundMoveDetail' => FundMoveDetail::class,
        'foreignExchangeQuote' => ForeignExchangeQuote::class,
        'refundForeignExchangeQuote' => ForeignExchangeQuote::class,
        'failReason' => Result::class,
        'combinedTransactionList' => [RelatedStatement::class],
        'operatorInfo' => OperatorInfo::class,
        'goodsAmount' => Amount::class,
        'originalFeeAmount' => Amount::class,
        'discountFeeAmount' => Amount::class,
    ];
}
