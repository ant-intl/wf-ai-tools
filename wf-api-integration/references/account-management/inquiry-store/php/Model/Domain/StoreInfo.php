<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Account\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class StoreInfo extends WfModel
{
    protected const FIELDS = ['storeName', 'marketplaceName', 'authorizedStatus', 'accountInformation'];
    protected const RELATIONS = ['accountInformation' => [AccountInfo::class]];
}
