<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\TradeOrder\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class Buyer extends WfModel
{
    protected const FIELDS = ['referenceBuyerId', 'buyerName', 'buyerEmail', 'buyerCountry', 'buyerPhoneNo'];
    protected const RELATIONS = ['buyerName' => BuyerName::class];
}
