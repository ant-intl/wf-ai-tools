<?php

declare(strict_types=1);

namespace {baseNamespace}\Wf\Client;

use {baseNamespace}\Wf\Model\Request\BindBeneficiaryRequest;
use {baseNamespace}\Wf\Model\Request\EditBeneficiaryRequest;
use {baseNamespace}\Wf\Model\Request\InquiryBeneficiaryListRequest;
use {baseNamespace}\Wf\Model\Request\InquiryBeneficiaryTemplateRequest;
use {baseNamespace}\Wf\Model\Request\RemoveBeneficiaryRequest;
use {baseNamespace}\Wf\Model\Response\BindBeneficiaryResponse;
use {baseNamespace}\Wf\Model\Response\EditBeneficiaryResponse;
use {baseNamespace}\Wf\Model\Response\InquiryBeneficiaryListResponse;
use {baseNamespace}\Wf\Model\Response\InquiryBeneficiaryTemplateResponse;
use {baseNamespace}\Wf\Model\Response\RemoveBeneficiaryResponse;

final class BeneficiaryManagementClient extends AbstractWfClient
{
    private const TEMPLATE_PATH = '/amsin/api/v1/business/account/inquiryBeneficiaryTemplate';
    private const BIND_PATH = '/amsin/api/v1/business/account/bindBeneficiary';
    private const REMOVE_PATH = '/amsin/api/v1/business/account/removeBeneficiary';
    private const EDIT_PATH = '/amsin/api/v1/business/account/editBeneficiary';
    private const LIST_PATH = '/amsin/api/v1/business/account/inquiryBeneficiaryList';

    public function inquiryBeneficiaryTemplate(
        InquiryBeneficiaryTemplateRequest $request
    ): InquiryBeneficiaryTemplateResponse {
        return $this->post(self::TEMPLATE_PATH, $request, InquiryBeneficiaryTemplateResponse::class);
    }

    public function bindBeneficiary(BindBeneficiaryRequest $request): BindBeneficiaryResponse
    {
        return $this->post(self::BIND_PATH, $request, BindBeneficiaryResponse::class);
    }

    public function removeBeneficiary(RemoveBeneficiaryRequest $request): RemoveBeneficiaryResponse
    {
        return $this->post(self::REMOVE_PATH, $request, RemoveBeneficiaryResponse::class);
    }

    public function editBeneficiary(EditBeneficiaryRequest $request): EditBeneficiaryResponse
    {
        return $this->post(self::EDIT_PATH, $request, EditBeneficiaryResponse::class);
    }

    public function inquiryBeneficiaryList(
        InquiryBeneficiaryListRequest $request
    ): InquiryBeneficiaryListResponse {
        return $this->post(self::LIST_PATH, $request, InquiryBeneficiaryListResponse::class);
    }
}
