# -*- coding: utf-8 -*-
"""
WorldFirst 关联交易记录 domain 模型

@author Qoder
@version related_statement.py, v 0.1 2026-04-14
"""

from dataclasses import dataclass
from typing import Optional


@dataclass
class RelatedStatement:
    """关联交易记录

    用于 combinedTransactionList 字段，表示与当前交易相关的其他交易。

    Attributes:
        transaction_id: 关联交易 ID
        accounting_biz_no: 关联账单流水唯一 ID
        transaction_type: 关联交易类型
    """

    transaction_id: Optional[str] = None
    accounting_biz_no: Optional[str] = None
    transaction_type: Optional[str] = None

    @classmethod
    def from_dict(cls, data: dict) -> Optional["RelatedStatement"]:
        """从字典创建 RelatedStatement 对象

        Args:
            data: 响应字典

        Returns:
            RelatedStatement 实例
        """
        if data is None:
            return None
        return cls(
            transaction_id=data.get("transactionId"),
            accounting_biz_no=data.get("accountingBizNo"),
            transaction_type=data.get("transactionType"),
        )

    def to_dict(self) -> dict:
        result = {}
        if self.transaction_id is not None:
            result["transactionId"] = self.transaction_id
        if self.accounting_biz_no is not None:
            result["accountingBizNo"] = self.accounting_biz_no
        if self.transaction_type is not None:
            result["transactionType"] = self.transaction_type
        return result

