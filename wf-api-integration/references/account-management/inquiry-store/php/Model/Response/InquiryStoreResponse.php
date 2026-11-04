<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Account\Model\Domain\StoreInfo;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryStoreResponse extends WfModel
{
    protected const FIELDS = ['result', 'storeInformation', 'totalCount', 'totalPageNumber', 'currentPageNumber'];
    protected const RELATIONS = [
        'result' => Result::class,
        'storeInformation' => [StoreInfo::class],
    ];
}
