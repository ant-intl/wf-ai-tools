# -*- coding: utf-8 -*-
"""
WorldFirst API 通用响应结果对象

@author Qoder
@version result.py, v 0.1 2026-04-01
"""

from dataclasses import dataclass, field
from typing import Optional


@dataclass
class Result:
    """WorldFirst API 通用响应结果

    Attributes:
        result_status: 结果状态 S=成功, F=失败, U=未知（可重试）
        result_code: 结果码
        result_message: 结果描述
    """

    result_status: Optional[str] = None
    result_code: Optional[str] = None
    result_message: Optional[str] = None

    @classmethod
    def from_dict(cls, data: dict) -> "Result":
        """从字典创建 Result 对象

        Args:
            data: 响应字典

        Returns:
            Result 实例
        """
        if data is None:
            return cls()
        return cls(
            result_status=data.get("resultStatus"),
            result_code=data.get("resultCode"),
            result_message=data.get("resultMessage"),
        )
