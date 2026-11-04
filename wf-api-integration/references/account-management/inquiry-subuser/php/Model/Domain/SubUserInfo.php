<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Account\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class SubUserInfo extends WfModel
{
    protected const FIELDS = [
        'userId',
        'userName',
        'logonId',
        'userNickName',
        'userAddress',
        'userEmail',
        'userMobile',
    ];
    protected const RELATIONS = [
        'userName' => UserName::class,
        'userNickName' => UserName::class,
        'userAddress' => Address::class,
    ];
}
