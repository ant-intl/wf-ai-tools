# -*- coding: utf-8 -*-
"""
WorldFirst inquiryStatementDetail 请求模型
"""

from dataclasses import dataclass
from typing import Optional

from {basePackage}.wf.model.exception.wf_error_code import WfErrorCode
from {basePackage}.wf.model.exception.wf_exception import WfException


@dataclass
class InquiryStatementDetailRequest:
    """inquiryStatementDetail 请求参数

    Attributes:
        accounting_biz_no: 账单流水唯一 ID，必填，通过 inquiryStatementList 获取
    """

    accounting_biz_no: Optional[str] = None

    def validate(self) -> None:
        """校验请求参数

        Raises:
            WfException: 参数校验失败时抛出
        """
        if not self.accounting_biz_no or not self.accounting_biz_no.strip():
            raise WfException(WfErrorCode.PARAM_ILLEGAL, "accountingBizNo is required")

    def to_dict(self) -> dict:
        """构建请求体字典

        Returns:
            请求体字典
        """
        return {"accountingBizNo": self.accounting_biz_no}

