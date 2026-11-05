<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Client;

use {baseNamespace}\Wf\Model\Request\ConsultTransferRequest;
use {baseNamespace}\Wf\Model\Request\CreateTransferRequest;
use {baseNamespace}\Wf\Model\Request\InquiryTransferRequest;
use {baseNamespace}\Wf\Model\Response\ConsultTransferResponse;
use {baseNamespace}\Wf\Model\Response\CreateTransferResponse;
use {baseNamespace}\Wf\Model\Response\InquiryTransferResponse;

final class TransferClient extends AbstractWfClient
{
    private const CONSULT_TRANSFER_PATH = '/amsin/api/v1/business/fund/consultTransfer';
    private const CREATE_TRANSFER_PATH = '/amsin/api/v1/business/fund/createTransfer';
    private const INQUIRY_TRANSFER_PATH = '/amsin/api/v1/business/fund/inquiryTransfer';

    public function consultTransfer(ConsultTransferRequest $request): ConsultTransferResponse
    {
        return $this->post(self::CONSULT_TRANSFER_PATH, $request, ConsultTransferResponse::class);
    }

    public function createTransfer(CreateTransferRequest $request): CreateTransferResponse
    {
        return $this->post(self::CREATE_TRANSFER_PATH, $request, CreateTransferResponse::class);
    }

    public function inquiryTransfer(InquiryTransferRequest $request): InquiryTransferResponse
    {
        return $this->post(self::INQUIRY_TRANSFER_PATH, $request, InquiryTransferResponse::class);
    }
}
