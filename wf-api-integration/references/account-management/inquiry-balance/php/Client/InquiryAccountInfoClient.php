<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Client;

use {baseNamespace}\Wf\Model\Request\InquiryAccountRequest;
use {baseNamespace}\Wf\Model\Request\InquiryAvailableQuotaRequest;
use {baseNamespace}\Wf\Model\Request\InquiryBalanceRequest;
use {baseNamespace}\Wf\Model\Request\InquiryStoreRequest;
use {baseNamespace}\Wf\Model\Request\InquirySubuserRequest;
use {baseNamespace}\Wf\Model\Response\InquiryAccountResponse;
use {baseNamespace}\Wf\Model\Response\InquiryAvailableQuotaResponse;
use {baseNamespace}\Wf\Model\Response\InquiryBalanceResponse;
use {baseNamespace}\Wf\Model\Response\InquiryStoreResponse;
use {baseNamespace}\Wf\Model\Response\InquirySubuserResponse;

final class InquiryAccountInfoClient extends AbstractWfClient
{
    private const ACCOUNT_PATH = '/amsin/api/v1/business/account/inquiryAccount';
    private const BALANCE_PATH = '/amsin/api/v1/business/account/inquiryBalance';
    private const QUOTA_PATH = '/amsin/api/v1/business/account/inquiryAvailableQuota';
    private const SUBUSER_PATH = '/amsin/api/v1/business/user/inquirySubuser';
    private const STORE_PATH = '/amsin/api/v1/business/store/inquiryStore';

    public function inquiryAccount(InquiryAccountRequest $request): InquiryAccountResponse
    {
        return $this->post(self::ACCOUNT_PATH, $request, InquiryAccountResponse::class);
    }

    public function inquiryBalance(InquiryBalanceRequest $request): InquiryBalanceResponse
    {
        return $this->post(self::BALANCE_PATH, $request, InquiryBalanceResponse::class);
    }

    public function inquiryAvailableQuota(InquiryAvailableQuotaRequest $request): InquiryAvailableQuotaResponse
    {
        return $this->post(self::QUOTA_PATH, $request, InquiryAvailableQuotaResponse::class);
    }

    public function inquirySubuser(InquirySubuserRequest $request): InquirySubuserResponse
    {
        return $this->post(self::SUBUSER_PATH, $request, InquirySubuserResponse::class);
    }

    public function inquiryStore(InquiryStoreRequest $request): InquiryStoreResponse
    {
        return $this->post(self::STORE_PATH, $request, InquiryStoreResponse::class);
    }
}
