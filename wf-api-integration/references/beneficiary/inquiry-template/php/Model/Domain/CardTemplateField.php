<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Beneficiary\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class CardTemplateField extends WfModel
{
    protected const FIELDS = ['fieldName', 'fieldDescription', 'required', 'restriction'];
    protected const RELATIONS = ['restriction' => FieldRestriction::class];
}
