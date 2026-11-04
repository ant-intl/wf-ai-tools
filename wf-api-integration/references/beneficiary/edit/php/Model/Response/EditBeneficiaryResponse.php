<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Model\WfModel;

final class EditBeneficiaryResponse extends WfModel
{
    protected const FIELDS = ['result', 'beneficiaryToken'];
    protected const RELATIONS = ['result' => Result::class];
}
