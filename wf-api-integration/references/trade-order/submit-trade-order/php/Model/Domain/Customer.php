<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\TradeOrder\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class Customer extends WfModel
{
    protected const FIELDS = ['customerId', 'referenceCustomerId', 'customerCompanyName', 'certificateList'];
    protected const RELATIONS = ['certificateList' => [Certificate::class]];
}
