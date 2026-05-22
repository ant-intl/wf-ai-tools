# -*- coding: utf-8 -*-
"""
WorldFirst API 业务异常
"""

from {basePackage}.wf.model.exception.wf_error_code import WfErrorCode


class WfException(Exception):
    """WorldFirst API 业务异常

    Attributes:
        error_code: 错误码枚举
        message: 错误描述
    """

    def __init__(self, error_code: WfErrorCode, message: str, cause: Exception = None):
        """构造函数

        Args:
            error_code: 错误码枚举
            message: 错误描述
            cause: 原始异常（可选）
        """
        super().__init__(message)
        self.error_code = error_code
        self.cause = cause

    @property
    def retryable(self) -> bool:
        """是否可重试"""
        return self.error_code is not None and self.error_code.retryable

    def __repr__(self) -> str:
        return f"WfException(error_code={self.error_code}, message='{self}')"
