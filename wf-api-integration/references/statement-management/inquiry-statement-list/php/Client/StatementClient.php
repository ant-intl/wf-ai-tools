<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Client;

use {baseNamespace}\Wf\Model\Request\InquiryStatementDetailRequest;
use {baseNamespace}\Wf\Model\Request\InquiryStatementRequest;
use {baseNamespace}\Wf\Model\Response\InquiryStatementDetailResponse;
use {baseNamespace}\Wf\Model\Response\InquiryStatementResponse;

final class StatementClient extends AbstractWfClient
{
    private const LIST_PATH = '/amsin/api/v1/business/account/inquiryStatementList';
    private const DETAIL_PATH = '/amsin/api/v1/business/account/inquiryStatementDetail';

    public function inquiryStatementList(InquiryStatementRequest $request): InquiryStatementResponse
    {
        return $this->post(self::LIST_PATH, $request, InquiryStatementResponse::class);
    }

    public function inquiryStatementDetail(InquiryStatementDetailRequest $request): InquiryStatementDetailResponse
    {
        return $this->post(self::DETAIL_PATH, $request, InquiryStatementDetailResponse::class);
    }
}
