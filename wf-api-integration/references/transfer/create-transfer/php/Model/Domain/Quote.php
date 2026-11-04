<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Transfer\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class Quote extends WfModel
{
    protected const FIELDS = [
        'quoteId',
        'quoteCurrencyPair',
        'quotePrice',
        'quoteStartTime',
        'quoteExpiryTime',
        'guaranteed',
    ];
}
