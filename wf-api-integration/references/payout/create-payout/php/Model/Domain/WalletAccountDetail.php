<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Payout\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class WalletAccountDetail extends WfModel
{
    protected const FIELDS = ['walletFullName', 'walletAccountNo', 'walletBrandName', 'walletCountryCode'];
}
