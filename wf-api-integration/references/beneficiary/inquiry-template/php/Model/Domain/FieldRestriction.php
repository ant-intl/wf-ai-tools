<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Beneficiary\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class FieldRestriction extends WfModel
{
    protected const FIELDS = ['restrictionMsg', 'restrictionRegex', 'restrictionType'];
}
