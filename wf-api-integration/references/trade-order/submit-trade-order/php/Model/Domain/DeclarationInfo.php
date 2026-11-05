<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\TradeOrder\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class DeclarationInfo extends WfModel
{
    protected const FIELDS = [
        'declarationOrderReferenceNo',
        'supervisionMethod',
        'customsDeclarationAttachmentList',
    ];
    protected const RELATIONS = ['customsDeclarationAttachmentList' => [AttachmentInfo::class]];
}
