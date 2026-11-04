<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Beneficiary\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class ThirdPartyIdentity extends WfModel
{
    protected const FIELDS = ['certificateNo', 'address', 'phoneNumber', 'email'];
    protected const RELATIONS = ['address' => Address::class];
}
