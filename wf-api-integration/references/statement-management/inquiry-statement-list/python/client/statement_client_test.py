# -*- coding: utf-8 -*-
"""
WorldFirst StatementClient 集成测试

使用前需填入真实的 WF 沙箱/生产环境凭证。

@author Qoder
@version statement_client_test.py, v 0.1 2026-04-14
"""

import unittest

from {basePackage}.wf.config.wf_config import WfConfig
from {basePackage}.wf.signer.wf_signer import WfSigner
from {basePackage}.wf.util.wf_http_client import WfHttpClient
from {basePackage}.wf.client.statement.statement_client import StatementClient
from {basePackage}.wf.model.request.inquiry_statement_request import InquiryStatementRequest
from {basePackage}.wf.model.request.inquiry_statement_detail_request import InquiryStatementDetailRequest
from {basePackage}.wf.model.exception.wf_exception import WfException

# ============ 填入你的 WF 沙箱/生产凭证 ============
WF_CLIENT_ID = "{clientId}"
WF_USER_ID = "{userId}"
WF_BASE_URL = "{baseUrl}"  # 沙箱: https://open-sitprod-sg.alipay.com
WF_PRIVATE_KEY_PATH = "{privateKeyPath}"
WF_PUBLIC_KEY_PATH = "{publicKeyPath}"


def _create_real_client() -> StatementClient:
    """创建真实的 StatementClient 实例"""
    config = WfConfig(
        client_id=WF_CLIENT_ID,
        user_id=WF_USER_ID,
        base_url=WF_BASE_URL,
        private_key_path=WF_PRIVATE_KEY_PATH,
        public_key_path=WF_PUBLIC_KEY_PATH,
    )
    signer = WfSigner(config)
    http_client = WfHttpClient(config, signer)
    return StatementClient(http_client)


class TestStatementClient(unittest.TestCase):
    """StatementClient 集成测试"""

    def setUp(self):
        self.client = _create_real_client()

    def test_inquiry_statement_list(self):
        """测试查询账单流水列表"""
        request = InquiryStatementRequest(
            start_time="2026-01-01T00:00:00+08:00",
            end_time="2026-03-27T23:59:59+08:00",
            page_number=1,
        )

        try:
            response = self.client.inquiry_statement_list(request)
            print(f"[PASS] ResponseID: {response.response_id} | "
                  f"Total: {response.total_count} | "
                  f"Pages: {response.total_page_number} | "
                  f"Current: {response.current_page_number}")

            if response.statement_list:
                for i, record in enumerate(response.statement_list):
                    print(f"  [{i + 1}] {record.transaction_time} | "
                          f"{record.transaction_type} | "
                          f"{record.transaction_status} | "
                          f"{record.accounting_biz_no}")
        except WfException as e:
            print(f"[FAIL] Code: {e.error_code.code} | "
                  f"Message: {e} | Retryable: {e.retryable}")
            self.fail(str(e))

    def test_inquiry_statement_detail(self):
        """测试查询账单流水详情

        先调用 inquiry_statement_list 获取真实的 accounting_biz_no，再查询详情。
        """
        # Step 1: 查询流水列表，获取真实的 accounting_biz_no
        list_request = InquiryStatementRequest(
            start_time="2026-01-01T00:00:00+08:00",
            end_time="2026-03-27T23:59:59+08:00",
            page_number=1,
        )
        list_response = self.client.inquiry_statement_list(list_request)
        accounting_biz_no = list_response.statement_list[1].accounting_biz_no

        # Step 2: 查询流水详情
        request = InquiryStatementDetailRequest(
            accounting_biz_no=accounting_biz_no,
        )

        try:
            response = self.client.inquiry_statement_detail(request)
            print(f"[PASS] ResponseID: {response.response_id} | "
                  f"TransactionID: {response.transaction_id} | "
                  f"Status: {response.transaction_status} | "
                  f"Type: {response.transaction_type} | "
                  f"Time: {response.transaction_time}")

            if response.transaction_amount:
                print(f"  TransactionAmount: {response.transaction_amount}")
            if response.fund_move_detail:
                print(f"  FundMoveDetail: {response.fund_move_detail}")
            if response.combined_transaction_list:
                print(f"  CombinedTransactionList count: {len(response.combined_transaction_list)}")
        except WfException as e:
            print(f"[FAIL] Code: {e.error_code.code} | "
                  f"Message: {e} | Retryable: {e.retryable}")
            self.fail(str(e))


if __name__ == "__main__":
    unittest.main()

