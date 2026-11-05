<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\TradeOrder\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class Goods extends WfModel
{
    protected const FIELDS = ['goodsName', 'goodsCategory', 'goodsQuantity', 'goodsUnit', 'goodsCnName', 'storeUrl'];
}
