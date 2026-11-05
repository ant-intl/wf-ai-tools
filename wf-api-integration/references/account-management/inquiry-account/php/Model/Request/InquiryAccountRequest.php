<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use {baseNamespace}\Wf\Model\WfModel;

final class InquiryAccountRequest extends WfModel
{
    protected const FIELDS = ['accountType', 'referenceCustomerId', 'accountId', 'accessToken'];

    public function validate(): void
    {
        $type = $this->requireString('accountType');
        $validTypes = [
            'RECEIVE_ACCOUNT',
            'VIRTUAL_ACCOUNT',
            'ALIPAY_WALLET',
            'ALIPAY_SHADOW_WALLET',
            'ALIPAY_ORIGIN_WALLET',
        ];
        if (!in_array($type, $validTypes, true)) {
            throw new InvalidArgumentException('Unsupported accountType.');
        }
        if (in_array($type, ['RECEIVE_ACCOUNT', 'ALIPAY_WALLET'], true)) {
            $this->requireString('referenceCustomerId');
        }
        if ($type === 'VIRTUAL_ACCOUNT') {
            $this->requireString('accessToken');
        }
        if ($type === 'ALIPAY_SHADOW_WALLET') {
            $this->requireString('accountId');
        }
    }
}
