<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Statement\Model\Domain;

use {baseNamespace}\Wf\Model\WfModel;

final class FundMoveDetail extends WfModel
{
    protected const FIELDS = [
        'payerName',
        'payerAccountNo',
        'payerAccountType',
        'payerBankName',
        'payerUserId',
        'beneficiaryName',
        'beneficiaryAccountNo',
        'beneficiaryAccountType',
        'beneficiaryBankCountry',
        'beneficiaryBankName',
        'beneficiaryStoreName',
        'beneficiaryMarketplaceName',
        'receiveAccount',
        'remarks',
        'description',
        'paymentExplanation',
        'paymentSubject',
        'paymentVoucherNo',
    ];
}
