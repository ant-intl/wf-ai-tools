# -*- coding: utf-8 -*-
"""
WorldFirst 账单流水查询统一客户端

包含 2 个接口方法：
- inquiry_statement_list: 分页查询账户交易流水
- inquiry_statement_detail: 查询指定账单流水详情

典型调用流程：先调用 inquiry_statement_list 获取 accounting_biz_no，
再以此为入参调用 inquiry_statement_detail。

@author Qoder
@version statement_client.py, v 0.1 2026-04-14
"""

import json
import logging

from {basePackage}.wf.model.exception.wf_error_code import WfErrorCode
from {basePackage}.wf.model.exception.wf_exception import WfException
from {basePackage}.wf.model.request.inquiry_statement_request import InquiryStatementRequest
from {basePackage}.wf.model.request.inquiry_statement_detail_request import InquiryStatementDetailRequest
from {basePackage}.wf.model.response.inquiry_statement_response import InquiryStatementResponse
from {basePackage}.wf.model.response.inquiry_statement_detail_response import InquiryStatementDetailResponse
from {basePackage}.wf.util.wf_http_client import WfHttpClient

logger = logging.getLogger(__name__)

# API paths
PATH_LIST = "/amsin/api/v1/business/account/inquiryStatementList"
PATH_DETAIL = "/amsin/api/v1/business/account/inquiryStatementDetail"

RESULT_STATUS_SUCCESS = "S"
RESULT_STATUS_FAIL = "F"


class StatementClient:
    """WorldFirst 账单流水查询统一客户端

    Attributes:
        http_client: WfHttpClient 实例，负责签名注入和 HTTP 通信
    """

    def __init__(self, http_client: WfHttpClient):
        """构造函数

        Args:
            http_client: WfHttpClient 实例
        """
        self._http_client = http_client

    # ==================== inquiry_statement_list ====================

    def inquiry_statement_list(self, request: InquiryStatementRequest) -> InquiryStatementResponse:
        """查询 WF 账户流水列表

        pageSize 固定为 10，pageNumber 范围 1-50。
        当 fuzzyName 为空时，startTime 与 endTime 的间隔不超过 100 天。

        Args:
            request: 查询请求，start_time、end_time、page_number 必填

        Returns:
            账单流水响应

        Raises:
            WfException: 参数校验失败或调用失败时抛出
        """
        if request is None:
            raise WfException(WfErrorCode.PARAM_ILLEGAL, "InquiryStatementRequest must not be None")

        request.validate()

        request_body = json.dumps(request.to_dict(), ensure_ascii=False)
        url = self._http_client.config.base_url + PATH_LIST

        logger.info(
            "StatementClient invoking inquiry_statement_list, url=%s, page_number=%d",
            url, request.page_number,
        )

        response_body = self._http_client.send_post_request(url, PATH_LIST, request_body)
        return self._parse_list_response(response_body)

    # ==================== inquiry_statement_detail ====================

    def inquiry_statement_detail(self, request: InquiryStatementDetailRequest) -> InquiryStatementDetailResponse:
        """查询账单流水详情

        查询指定账单流水的详细信息。需先调用 inquiry_statement_list 获取 accounting_biz_no，
        再以此为入参调用本方法。

        Args:
            request: 查询请求，accounting_biz_no 必填

        Returns:
            账单流水详情响应

        Raises:
            WfException: 参数校验失败或调用失败时抛出
        """
        if request is None:
            raise WfException(WfErrorCode.PARAM_ILLEGAL, "InquiryStatementDetailRequest must not be None")

        request.validate()

        request_body = json.dumps(request.to_dict(), ensure_ascii=False)
        url = self._http_client.config.base_url + PATH_DETAIL

        logger.info(
            "StatementClient invoking inquiry_statement_detail, url=%s, accounting_biz_no=%s",
            url, request.accounting_biz_no,
        )

        response_body = self._http_client.send_post_request(url, PATH_DETAIL, request_body)
        return self._parse_detail_response(response_body)

    # ==================== 私有方法 ====================

    def _parse_list_response(self, response_body: str) -> InquiryStatementResponse:
        """解析列表查询响应体

        Args:
            response_body: 响应体 JSON 字符串

        Returns:
            解析后的响应对象

        Raises:
            WfException: 响应格式非法或业务失败时抛出
        """
        try:
            data = json.loads(response_body)
        except Exception as e:
            logger.error("StatementClient failed to parse inquiry_statement_list response, body=%s", response_body)
            raise WfException(
                WfErrorCode.INVALID_RESPONSE_FORMAT,
                f"Failed to parse inquiry_statement_list response: {e}",
                cause=e,
            )

        response = InquiryStatementResponse.from_dict(data)

        if response.result is None:
            raise WfException(
                WfErrorCode.INVALID_RESPONSE_FORMAT,
                "inquiry_statement_list response result is None",
            )

        result_status = response.result.result_status
        if result_status == RESULT_STATUS_SUCCESS:
            logger.info(
                "StatementClient inquiry_statement_list success, response_id=%s, total_count=%s",
                response.response_id, response.total_count,
            )
            return response
        elif result_status == RESULT_STATUS_FAIL:
            error_code = WfErrorCode.from_code(response.result.result_code)
            logger.warning(
                "StatementClient inquiry_statement_list failed, result_code=%s, result_message=%s",
                response.result.result_code, response.result.result_message,
            )
            raise WfException(error_code, response.result.result_message)
        else:
            # resultStatus=U 或未知状态，交由调用方重试
            error_code = WfErrorCode.from_code(response.result.result_code)
            logger.warning(
                "StatementClient inquiry_statement_list unknown/retryable status, "
                "result_status=%s, result_code=%s",
                result_status, response.result.result_code,
            )
            raise WfException(error_code, response.result.result_message)

    def _parse_detail_response(self, response_body: str) -> InquiryStatementDetailResponse:
        """解析详情查询响应体

        Args:
            response_body: 响应体 JSON 字符串

        Returns:
            解析后的响应对象

        Raises:
            WfException: 响应格式非法或业务失败时抛出
        """
        try:
            data = json.loads(response_body)
        except Exception as e:
            logger.error("StatementClient failed to parse inquiry_statement_detail response, body=%s", response_body)
            raise WfException(
                WfErrorCode.INVALID_RESPONSE_FORMAT,
                f"Failed to parse inquiry_statement_detail response: {e}",
                cause=e,
            )

        response = InquiryStatementDetailResponse.from_dict(data)

        if response.result is None:
            raise WfException(
                WfErrorCode.INVALID_RESPONSE_FORMAT,
                "inquiry_statement_detail response result is None",
            )

        result_status = response.result.result_status
        if result_status == RESULT_STATUS_SUCCESS:
            logger.info(
                "StatementClient inquiry_statement_detail success, response_id=%s, "
                "transaction_id=%s, transaction_status=%s",
                response.response_id, response.transaction_id, response.transaction_status,
            )
            return response
        elif result_status == RESULT_STATUS_FAIL:
            error_code = WfErrorCode.from_code(response.result.result_code)
            logger.warning(
                "StatementClient inquiry_statement_detail failed, result_code=%s, result_message=%s",
                response.result.result_code, response.result.result_message,
            )
            raise WfException(error_code, response.result.result_message)
        else:
            # resultStatus=U 或未知状态，交由调用方重试
            error_code = WfErrorCode.from_code(response.result.result_code)
            logger.warning(
                "StatementClient inquiry_statement_detail unknown/retryable status, "
                "result_status=%s, result_code=%s",
                result_status, response.result.result_code,
            )
            raise WfException(error_code, response.result.result_message)

