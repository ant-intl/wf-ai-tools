<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Account\Model\Domain\AccountInfo;
use {baseNamespace}\Wf\Account\Model\Domain\AlipayCustomer;
use {baseNamespace}\Wf\Account\Model\Domain\Customer;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryAccountResponse extends WfModel
{
    protected const FIELDS = [
        'result',
        'responseId',
        'accountId',
        'accountInfos',
        'customer',
        'alipayCustomer',
        'affiliatedCustomer',
    ];
    protected const RELATIONS = [
        'result' => Result::class,
        'accountInfos' => [AccountInfo::class],
        'customer' => Customer::class,
        'alipayCustomer' => AlipayCustomer::class,
        'affiliatedCustomer' => AlipayCustomer::class,
    ];
}
