<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Client;

use {baseNamespace}\Wf\Model\Request\ConsultPayoutRequest;
use {baseNamespace}\Wf\Model\Request\CreatePayoutRequest;
use {baseNamespace}\Wf\Model\Request\InquiryPayoutRequest;
use {baseNamespace}\Wf\Model\Response\ConsultPayoutResponse;
use {baseNamespace}\Wf\Model\Response\CreatePayoutResponse;
use {baseNamespace}\Wf\Model\Response\InquiryPayoutResponse;

final class PayoutClient extends AbstractWfClient
{
    private const CONSULT_PATH = '/amsin/api/v1/business/fund/consultPayout';
    private const CREATE_PATH = '/amsin/api/v1/business/fund/createPayout';
    private const INQUIRY_PATH = '/amsin/api/v1/business/fund/inquiryPayout';

    public function consultPayout(ConsultPayoutRequest $request): ConsultPayoutResponse
    {
        return $this->post(self::CONSULT_PATH, $request, ConsultPayoutResponse::class);
    }

    public function createPayout(CreatePayoutRequest $request): CreatePayoutResponse
    {
        return $this->post(self::CREATE_PATH, $request, CreatePayoutResponse::class);
    }

    public function inquiryPayout(InquiryPayoutRequest $request): InquiryPayoutResponse
    {
        return $this->post(self::INQUIRY_PATH, $request, InquiryPayoutResponse::class);
    }
}
