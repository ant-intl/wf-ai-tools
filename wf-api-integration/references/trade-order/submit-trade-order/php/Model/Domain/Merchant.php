<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\TradeOrder\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class Merchant extends WfModel
{
    protected const FIELDS = ['store', 'merchantId'];
    protected const RELATIONS = ['store' => Store::class];
}
