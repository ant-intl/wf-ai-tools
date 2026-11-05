<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Account\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class UserName extends WfModel
{
    protected const FIELDS = ['firstName', 'middleName', 'lastName', 'fullName'];
}
