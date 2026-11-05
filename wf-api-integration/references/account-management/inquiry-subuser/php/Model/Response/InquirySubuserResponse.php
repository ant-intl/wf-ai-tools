<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Account\Model\Domain\SubUserInfo;
use {baseNamespace}\Wf\Model\WfModel;

final class InquirySubuserResponse extends WfModel
{
    protected const FIELDS = [
        'result',
        'primaryUserInformation',
        'userInformations',
        'totalCount',
        'totalPageNumber',
        'currentPageNumber',
    ];
    protected const RELATIONS = [
        'result' => Result::class,
        'primaryUserInformation' => SubUserInfo::class,
        'userInformations' => [SubUserInfo::class],
    ];
}
