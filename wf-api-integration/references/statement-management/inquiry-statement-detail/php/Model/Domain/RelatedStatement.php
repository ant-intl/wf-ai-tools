<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Statement\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class RelatedStatement extends WfModel
{
    protected const FIELDS = ['transactionId', 'accountingBizNo', 'transactionType'];
}
