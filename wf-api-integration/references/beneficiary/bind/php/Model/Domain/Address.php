<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Beneficiary\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class Address extends WfModel
{
    protected const FIELDS = ['region', 'state', 'city', 'address1', 'address2', 'zipCode'];
}
