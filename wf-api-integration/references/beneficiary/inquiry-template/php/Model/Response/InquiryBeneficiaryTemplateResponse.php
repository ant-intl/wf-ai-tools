<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Response;

use {baseNamespace}\Wf\Beneficiary\Model\Domain\CardTemplateField;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryBeneficiaryTemplateResponse extends WfModel
{
    protected const FIELDS = [
        'result',
        'responseId',
        'cardTemplateData',
        'localCardTemplateData',
        'crossBorderCardTemplateData',
    ];
    protected const RELATIONS = [
        'result' => Result::class,
        'cardTemplateData' => [CardTemplateField::class],
        'localCardTemplateData' => [CardTemplateField::class],
        'crossBorderCardTemplateData' => [CardTemplateField::class],
    ];
}
