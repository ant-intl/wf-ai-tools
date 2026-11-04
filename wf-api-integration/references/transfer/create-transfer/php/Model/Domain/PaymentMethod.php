<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Transfer\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class PaymentMethod extends WfModel
{
    protected const FIELDS = ['paymentMethodType', 'paymentMethodId', 'accountId'];
}
