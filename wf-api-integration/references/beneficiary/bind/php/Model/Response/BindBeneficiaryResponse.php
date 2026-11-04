<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Beneficiary\Model\Domain\Beneficiary;
use {baseNamespace}\Wf\Model\WfModel;

final class BindBeneficiaryResponse extends WfModel
{
    protected const FIELDS = ['result', 'beneficiary'];
    protected const RELATIONS = ['result' => Result::class, 'beneficiary' => Beneficiary::class];
}
