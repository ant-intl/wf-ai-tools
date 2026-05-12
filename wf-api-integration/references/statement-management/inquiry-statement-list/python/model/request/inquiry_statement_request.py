# -*- coding: utf-8 -*-
"""
WorldFirst inquiryStatementList 请求模型

@author Qoder
@version inquiry_statement_request.py, v 0.1 2026-04-14
"""

from dataclasses import dataclass, field
from datetime import datetime
from typing import List, Optional

from {basePackage}.wf.model.exception.wf_error_code import WfErrorCode
from {basePackage}.wf.model.exception.wf_exception import WfException

# pageSize 固定值
FIXED_PAGE_SIZE = 10
# pageNumber 最大值
MAX_PAGE_NUMBER = 50
# 时间范围最大天数（fuzzyName 为空时生效）
MAX_TIME_RANGE_DAYS = 100


@dataclass
class InquiryStatementRequest:
    """inquiryStatementList 请求参数

    Attributes:
        start_time: 起始时间（ISO 8601），必填。fuzzyName 为空时，时间跨度 ≤ 100 天
        end_time: 结束时间（ISO 8601），必填
        page_size: 固定值 10，不允许调用方修改
        page_number: 页码 1-50，必填
        transaction_type_list: 交易类型过滤（TRANSFER, COLLECTION 等），可选
        currency_list: 币种过滤（ISO-4217），可选
        balance_types: 余额类型过滤（NORMAL_BALANCE 等），可选
        budget_account_ids: 预算账户 ID 列表，可选
        fuzzy_name: 模糊关键字，设置后 100 天限制取消，可选
    """

    start_time: Optional[str] = None
    end_time: Optional[str] = None
    page_size: int = FIXED_PAGE_SIZE
    page_number: int = 1
    transaction_type_list: Optional[List[str]] = None
    currency_list: Optional[List[str]] = None
    balance_types: Optional[List[str]] = None
    budget_account_ids: Optional[List[str]] = None
    fuzzy_name: Optional[str] = None

    def validate(self) -> None:
        """校验请求参数

        Raises:
            WfException: 参数校验失败时抛出
        """
        if not self.start_time or not self.start_time.strip():
            raise WfException(WfErrorCode.PARAM_ILLEGAL, "startTime is required")
        if not self.end_time or not self.end_time.strip():
            raise WfException(WfErrorCode.PARAM_ILLEGAL, "endTime is required")
        if self.page_number < 1 or self.page_number > MAX_PAGE_NUMBER:
            raise WfException(
                WfErrorCode.PARAM_ILLEGAL,
                f"pageNumber must be between 1 and {MAX_PAGE_NUMBER}, actual: {self.page_number}",
            )

        # 强制覆盖 pageSize 为固定值 10
        self.page_size = FIXED_PAGE_SIZE

        # 当 fuzzyName 为空时，校验时间范围不超过 100 天
        if not self.fuzzy_name or not self.fuzzy_name.strip():
            self._validate_time_range()

    def _validate_time_range(self) -> None:
        """校验时间范围不超过 MAX_TIME_RANGE_DAYS 天"""
        try:
            start = datetime.fromisoformat(self.start_time)
            end = datetime.fromisoformat(self.end_time)
            days = (end - start).days
            if days > MAX_TIME_RANGE_DAYS or days < 0:
                raise WfException(
                    WfErrorCode.PARAM_ILLEGAL,
                    f"Time range must not exceed {MAX_TIME_RANGE_DAYS} days when fuzzyName is not set. "
                    f"Actual days: {days}. Set fuzzyName to bypass this limit.",
                )
        except WfException:
            raise
        except Exception as e:
            raise WfException(
                WfErrorCode.PARAM_ILLEGAL,
                f"Invalid time format, expected ISO 8601, e.g. 2024-01-01T00:00:00+08:00. Error: {e}",
            )

    def to_dict(self) -> dict:
        """构建请求体字典，仅包含非空字段

        Returns:
            请求体字典
        """
        body = {
            "startTime": self.start_time,
            "endTime": self.end_time,
            "pageSize": FIXED_PAGE_SIZE,
            "pageNumber": self.page_number,
        }
        if self.transaction_type_list:
            body["transactionTypeList"] = self.transaction_type_list
        if self.currency_list:
            body["currencyList"] = self.currency_list
        if self.balance_types:
            body["balanceTypes"] = self.balance_types
        if self.budget_account_ids:
            body["budgetAccountIds"] = self.budget_account_ids
        if self.fuzzy_name and self.fuzzy_name.strip():
            body["fuzzyName"] = self.fuzzy_name
        return body

