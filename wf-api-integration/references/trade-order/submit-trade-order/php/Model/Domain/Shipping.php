<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\TradeOrder\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class Shipping extends WfModel
{
    protected const FIELDS = [
        'isShipped',
        'isDeclared',
        'isNewBuyer',
        'wayBillInfos',
        'shippingAddress',
        'shippingMethod',
        'shippingProofAttachmentList',
        'logisticsCompany',
        'declarationInfos',
        'expectedShippingDate',
        'inquiryChatRecordAttachmentList',
        'logisticsChatRecordAttachmentList',
    ];
    protected const RELATIONS = [
        'wayBillInfos' => [WayBillInfo::class],
        'shippingAddress' => Address::class,
        'shippingProofAttachmentList' => [AttachmentInfo::class],
        'logisticsCompany' => LogisticsCompany::class,
        'declarationInfos' => [DeclarationInfo::class],
        'inquiryChatRecordAttachmentList' => [AttachmentInfo::class],
        'logisticsChatRecordAttachmentList' => [AttachmentInfo::class],
    ];
}
