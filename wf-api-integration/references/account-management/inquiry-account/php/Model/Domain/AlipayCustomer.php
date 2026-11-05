<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Account\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class AlipayCustomer extends WfModel
{
    protected const FIELDS = ['alipayNo', 'companyName', 'region'];
}
