<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryBeneficiaryTemplateRequest extends WfModel
{
    protected const FIELDS = ['countryCode', 'currency', 'beneficiaryType'];

    public function validate(): void
    {
        $countryCode = $this->optionalString('countryCode');
        $currency = $this->optionalString('currency');
        $beneficiaryType = $this->optionalString('beneficiaryType');
        if (($countryCode ?? '') === '' && ($currency ?? '') === '' && ($beneficiaryType ?? '') === '') {
            throw new InvalidArgumentException('At least one of countryCode, currency and beneficiaryType is required.');
        }
    }
}
