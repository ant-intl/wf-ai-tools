<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Payout\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

/** paymentMethodMetaData 是按卡模板或钱包字段构造的 JSON 字符串。 */
final class TransferToMethod extends WfModel
{
    protected const FIELDS = ['paymentMethodType', 'paymentMethodMetaData', 'paymentMethodId'];
}
