<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\TradeOrder\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class BizContractInfo extends WfModel
{
    protected const FIELDS = [
        'buyerEnName',
        'tradeCountry',
        'deliverCountry',
        'contractList',
        'otherAttachmentList',
        'attachmentDesc',
    ];
    protected const RELATIONS = [
        'contractList' => [AttachmentInfo::class],
        'otherAttachmentList' => [AttachmentInfo::class],
    ];
}
