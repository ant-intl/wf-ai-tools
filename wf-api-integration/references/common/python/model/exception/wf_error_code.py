# -*- coding: utf-8 -*-
"""
WorldFirst API 错误码枚举

@author Qoder
@version wf_error_code.py, v 0.1 2026-04-01
"""

from enum import Enum


class WfErrorCode(Enum):
    """WorldFirst API 错误码

    每个枚举值为 (code, retryable) 元组。
    """

    # ====================== 通用错误码（共享） ======================

    PARAM_ILLEGAL = ("PARAM_ILLEGAL", False)
    OAUTH_FAIL = ("OAUTH_FAIL", False)
    INVALID_API = ("INVALID_API", False)
    INVALID_CLIENT = ("INVALID_CLIENT", False)
    INVALID_SIGNATURE = ("INVALID_SIGNATURE", False)
    METHOD_NOT_SUPPORTED = ("METHOD_NOT_SUPPORTED", False)
    USER_NOT_EXIST = ("USER_NOT_EXIST", False)
    ACCOUNT_NOT_EXIST = ("ACCOUNT_NOT_EXIST", False)
    SYSTEM_ERROR = ("SYSTEM_ERROR", False)
    SERVICE_NOT_ALLOWED = ("SERVICE_NOT_ALLOWED", False)
    CURRENCY_NOT_SUPPORT = ("CURRENCY_NOT_SUPPORT", False)
    CONTRACT_CHECK_FAIL = ("CONTRACT_CHECK_FAIL", False)
    ACCESS_TOKEN_EXPIRED = ("ACCESS_TOKEN_EXPIRED", False)
    AUTHORIZATION_NOT_EXIST = ("AUTHORIZATION_NOT_EXIST", False)
    PROCESS_FAIL = ("PROCESS_FAIL", False)

    # ====================== 业务错误码 ======================

    UN_SUPPORT_BUSINESS = ("UN_SUPPORT_BUSINESS", False)
    USER_NO_PERMISSION = ("USER_NO_PERMISSION", False)
    USER_ACCOUNT_ABNORMAL = ("USER_ACCOUNT_ABNORMAL", False)
    REPEAT_REQ_INCONSISTENT = ("REPEAT_REQ_INCONSISTENT", False)
    USER_STATUS_ABNORMAL = ("USER_STATUS_ABNORMAL", False)
    BALANCE_NOT_ENOUGH = ("BALANCE_NOT_ENOUGH", False)
    AMOUNT_EXCEED_LIMIT = ("AMOUNT_EXCEED_LIMIT", False)
    QUOTE_EXPIRED = ("QUOTE_EXPIRED", False)
    RISK_REJECT = ("RISK_REJECT", False)
    CARD_INFO_NOT_MATCH = ("CARD_INFO_NOT_MATCH", False)
    ORDER_IS_REVERSED = ("ORDER_IS_REVERSED", False)
    ORDER_IS_CLOSED = ("ORDER_IS_CLOSED", False)

    # ====================== 可重试错误码 (resultStatus=U) ======================

    UNKNOWN_EXCEPTION = ("UNKNOWN_EXCEPTION", True)
    REQUEST_TRAFFIC_EXCEED_LIMIT = ("REQUEST_TRAFFIC_EXCEED_LIMIT", True)

    # ====================== 客户端内部错误码 ======================

    HTTP_REQUEST_FAILED = ("HTTP_REQUEST_FAILED", False)
    INVALID_RESPONSE_FORMAT = ("INVALID_RESPONSE_FORMAT", False)
    SIGNATURE_GENERATION_FAILED = ("SIGNATURE_GENERATION_FAILED", False)
    UNKNOWN = ("UNKNOWN", False)

    def __init__(self, code: str, retryable: bool):
        self._code = code
        self._retryable = retryable

    @property
    def code(self) -> str:
        """错误码字符串"""
        return self._code

    @property
    def retryable(self) -> bool:
        """是否可重试"""
        return self._retryable

    @classmethod
    def from_code(cls, code: str) -> "WfErrorCode":
        """根据错误码字符串查找对应枚举，找不到时返回 UNKNOWN

        Args:
            code: 错误码字符串

        Returns:
            对应的 WfErrorCode 枚举值
        """
        if code is None:
            return cls.UNKNOWN
        for error_code in cls:
            if error_code.code == code:
                return error_code
        return cls.UNKNOWN
