<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Payout\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class TransferQuote extends WfModel
{
    protected const FIELDS = ['quoteId', 'quoteCurrencyPair', 'quotePrice', 'quoteStartTime', 'quoteExpiryTime'];
}
