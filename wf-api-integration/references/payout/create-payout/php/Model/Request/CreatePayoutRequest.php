<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Model\Request;

use InvalidArgumentException;
use JsonException;
use stdClass;
use {baseNamespace}\Wf\Payout\Model\Domain\Amount;
use {baseNamespace}\Wf\Payout\Model\Domain\TransferFromDetail;
use {baseNamespace}\Wf\Payout\Model\Domain\TransferToDetail;
use {baseNamespace}\Wf\Model\WfModel;

final class CreatePayoutRequest extends WfModel
{
    protected const FIELDS = ['transferRequestId', 'transferFromDetail', 'transferToDetail', 'businessSceneCode'];
    protected const RELATIONS = [
        'transferFromDetail' => TransferFromDetail::class,
        'transferToDetail' => TransferToDetail::class,
    ];

    public function validate(): void
    {
        if (!is_string($this->transferRequestId) || $this->transferRequestId === '' || strlen($this->transferRequestId) > 64) {
            throw new InvalidArgumentException('transferRequestId is required and must not exceed 64 characters.');
        }

        $toDetail = $this->transferToDetail;
        $fromAmount = $this->transferFromDetail?->transferFromAmount;
        $toAmount = $toDetail?->transferToAmount;
        if (!$fromAmount instanceof Amount || !is_string($fromAmount->currency) || $fromAmount->currency === '') {
            throw new InvalidArgumentException('transferFromDetail.transferFromAmount.currency is required.');
        }
        if (!$toAmount instanceof Amount || !is_string($toAmount->currency) || $toAmount->currency === '') {
            throw new InvalidArgumentException('transferToDetail.transferToAmount.currency is required.');
        }

        $fromHasValue = $fromAmount->value !== null;
        $toHasValue = $toAmount->value !== null;
        if ($fromHasValue === $toHasValue) {
            throw new InvalidArgumentException('Exactly one of transferFromAmount.value and transferToAmount.value is required.');
        }
        if (($fromHasValue && $fromAmount->value <= 0) || ($toHasValue && $toAmount->value <= 0)) {
            throw new InvalidArgumentException('Transfer amount value must be positive.');
        }
        if ($toAmount->currency === 'CNY') {
            $this->requireString('businessSceneCode');
        }

        if (($toDetail->purposeCode ?? '') === '') {
            $toDetail->purposeCode = 'GDS';
        }
        $method = $toDetail->transferToMethod;
        if ($method === null) {
            throw new InvalidArgumentException('transferToDetail.transferToMethod is required.');
        }

        $type = $method->paymentMethodType;
        $metadata = $method->paymentMethodMetaData ?? '';
        $methodId = $method->paymentMethodId ?? '';
        if (!is_string($metadata)) {
            throw new InvalidArgumentException('paymentMethodMetaData must be a JSON string.');
        }
        if (!is_string($methodId)) {
            throw new InvalidArgumentException('paymentMethodId must be a string.');
        }
        switch ($type) {
            case 'BANK_ACCOUNT_DETAIL':
            case 'WALLET_ACCOUNT_DETAIL':
                $this->assertJsonObject($metadata, 'paymentMethodMetaData');
                if ($type === 'BANK_ACCOUNT_DETAIL' && $methodId !== '') {
                    throw new InvalidArgumentException('paymentMethodId is not allowed for BANK_ACCOUNT_DETAIL.');
                }
                break;
            case 'ALIPAY_CN_DETAIL':
                $this->assertJsonObject($metadata, 'paymentMethodMetaData');
                break;
            case 'BENEFICIARY_TOKEN':
                if ($methodId === '' || $metadata !== '') {
                    throw new InvalidArgumentException('BENEFICIARY_TOKEN requires only paymentMethodId.');
                }
                break;
            case 'REFERENCE_ALIPAY_CN':
                if ($methodId === '') {
                    throw new InvalidArgumentException('REFERENCE_ALIPAY_CN requires paymentMethodId.');
                }
                break;
            default:
                throw new InvalidArgumentException('Unsupported paymentMethodType.');
        }
    }

    private function assertJsonObject(string $json, string $field): void
    {
        if ($json === '') {
            throw new InvalidArgumentException($field . ' is required.');
        }
        try {
            $decoded = json_decode($json, false, 512, JSON_THROW_ON_ERROR);
        } catch (JsonException $exception) {
            throw new InvalidArgumentException($field . ' must be valid JSON.', 0, $exception);
        }
        if (!$decoded instanceof stdClass) {
            throw new InvalidArgumentException($field . ' must contain a JSON object.');
        }
    }
}
