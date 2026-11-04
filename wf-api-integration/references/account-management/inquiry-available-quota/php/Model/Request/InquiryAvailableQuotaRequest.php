<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryAvailableQuotaRequest extends WfModel
{
    protected const FIELDS = ['quotaAccumulationMethod', 'quotaAccumulationId', 'currency', 'tradeType'];

    public function validate(): void
    {
        $methods = ['USER_ID', 'RECEIVING_ACCOUNT', 'VIRTUAL_ACCOUNT', 'BENEFICIARY'];
        $method = $this->requireString('quotaAccumulationMethod');
        if (!in_array($method, $methods, true)) {
            throw new InvalidArgumentException('Unsupported quotaAccumulationMethod.');
        }
        $this->requireString('quotaAccumulationId');
        $this->requireString('currency');
        if ($method === 'BENEFICIARY') {
            $this->requireString('tradeType');
        }
    }
}
