# -*- coding: utf-8 -*-
"""
WorldFirst inquiryStatementList 响应模型

@author Qoder
@version inquiry_statement_response.py, v 0.1 2026-04-14
"""

from dataclasses import dataclass, field
from typing import List, Optional

from {basePackage}.wf.model.response.result import Result
from {basePackage}.wf.model.domain.statement_record import StatementRecord


@dataclass
class InquiryStatementResponse:
    """inquiryStatementList 响应

    Attributes:
        result: 通用响应结果
        response_id: 响应唯一 ID
        fee_item_type: 手续费类型
        statement_list: 流水记录列表
        total_count: 总记录数
        total_page_number: 总页数
        current_page_number: 当前页码
    """

    result: Optional[Result] = None
    response_id: Optional[str] = None
    fee_item_type: Optional[str] = None
    statement_list: Optional[List[StatementRecord]] = None
    total_count: Optional[int] = None
    total_page_number: Optional[int] = None
    current_page_number: Optional[int] = None

    @classmethod
    def from_dict(cls, data: dict) -> "InquiryStatementResponse":
        """从字典创建响应对象

        Args:
            data: 响应字典

        Returns:
            InquiryStatementResponse 实例
        """
        if data is None:
            return cls()

        statement_list_data = data.get("statementList")
        statement_list = None
        if statement_list_data is not None:
            statement_list = [StatementRecord.from_dict(item) for item in statement_list_data]

        return cls(
            result=Result.from_dict(data.get("result")),
            response_id=data.get("responseId"),
            fee_item_type=data.get("feeItemType"),
            statement_list=statement_list,
            total_count=data.get("totalCount"),
            total_page_number=data.get("totalPageNumber"),
            current_page_number=data.get("currentPageNumber"),
        )

