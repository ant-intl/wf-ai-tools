<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Account\Model\Domain\AccountBalance;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryBalanceResponse extends WfModel
{
    protected const FIELDS = ['result', 'responseId', 'accountBalances'];
    protected const RELATIONS = [
        'result' => Result::class,
        'accountBalances' => [AccountBalance::class],
    ];
}
