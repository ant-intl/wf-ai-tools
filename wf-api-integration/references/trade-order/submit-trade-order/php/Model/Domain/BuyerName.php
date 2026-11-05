<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\TradeOrder\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class BuyerName extends WfModel
{
    protected const FIELDS = ['firstName', 'middleName', 'lastName', 'fullName'];
}
