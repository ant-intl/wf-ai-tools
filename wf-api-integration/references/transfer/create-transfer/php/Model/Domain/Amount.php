<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Transfer\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

/** 金额使用最小货币单位，例如 USD 10.00 的 value 为 1000。 */
final class Amount extends WfModel
{
    protected const FIELDS = ['currency', 'value'];
}
