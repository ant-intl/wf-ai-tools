<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Client;

use {baseNamespace}\Wf\Model\Request\InquiryTradeOrderRequest;
use {baseNamespace}\Wf\Model\Request\SubmitTradeOrderRequest;
use {baseNamespace}\Wf\Model\Response\InquiryTradeOrderResponse;
use {baseNamespace}\Wf\Model\Response\SubmitTradeOrderResponse;

final class TradeOrderManagementClient extends AbstractWfClient
{
    private const SUBMIT_PATH = '/amsin/api/v1/business/account/submitTradeOrder';
    private const INQUIRY_PATH = '/amsin/api/v1/business/account/inquiryTradeOrder';

    public function submitTradeOrder(SubmitTradeOrderRequest $request): SubmitTradeOrderResponse
    {
        return $this->post(self::SUBMIT_PATH, $request, SubmitTradeOrderResponse::class);
    }

    public function inquiryTradeOrder(InquiryTradeOrderRequest $request): InquiryTradeOrderResponse
    {
        return $this->post(self::INQUIRY_PATH, $request, InquiryTradeOrderResponse::class);
    }
}
