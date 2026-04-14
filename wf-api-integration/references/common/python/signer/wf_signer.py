# -*- coding: utf-8 -*-
"""
WorldFirst API RSA256 签名工具类

签名内容格式: POST {apiPath}\n{clientId}.{requestTime}.{requestBody}

@author Qoder
@version wf_signer.py, v 0.1 2026-03-24
"""

import base64
import logging
import urllib.parse
from datetime import datetime, timezone, timedelta

from cryptography.hazmat.primitives import hashes, serialization
from cryptography.hazmat.primitives.asymmetric import padding

from {basePackage}.wf.config.wf_config import WfConfig

logger = logging.getLogger(__name__)

# Asia/Shanghai 时区
_CST = timezone(timedelta(hours=8))


class WfSigner:
    """WorldFirst API RSA256 签名/验签工具

    Attributes:
        config: WfConfig 配置实例
    """

    def __init__(self, config: WfConfig):
        """构造函数

        Args:
            config: WF 配置
        """
        self._config = config

    def generate_signature(self, api_path: str, request_time: str, request_body: str) -> str | None:
        """生成请求签名

        Args:
            api_path: API 路径，例如 /amsin/api/v1/business/account/inquiryStatementList
            request_time: 请求时间，ISO 8601 格式
            request_body: 请求体 JSON 字符串

        Returns:
            Base64 + URL 编码的签名字符串，失败时返回 None
        """
        try:
            sign_content = f"POST {api_path}\n{self._config.client_id}.{request_time}.{request_body}"
            logger.debug("WfSigner sign content: %s", sign_content)

            private_key = self._load_private_key()
            signature_bytes = private_key.sign(
                sign_content.encode("utf-8"),
                padding.PKCS1v15(),
                hashes.SHA256(),
            )
            base64_signature = base64.b64encode(signature_bytes).decode("utf-8")
            return urllib.parse.quote(base64_signature, safe="")
        except Exception:
            logger.exception("WfSigner failed to generate signature, api_path=%s", api_path)
            return None

    def verify_signature(
        self, signature_header: str, api_path: str, request_time: str, response_body: str
    ) -> bool:
        """验证 WF 响应签名

        Args:
            signature_header: 响应头中的 Signature 值
            api_path: API 路径
            request_time: 原始请求时间（与发送请求时一致）
            response_body: 响应体 JSON 字符串

        Returns:
            签名有效返回 True，否则返回 False
        """
        try:
            base64_sig = None
            for part in signature_header.split(","):
                trimmed = part.strip()
                if trimmed.startswith("signature="):
                    base64_sig = trimmed[len("signature="):].strip()
                    break

            if base64_sig is None:
                logger.warning("WfSigner no signature value found in header: %s", signature_header)
                return False

            sign_content = f"POST {api_path}\n{self._config.client_id}.{request_time}.{response_body}"

            public_key = self._load_public_key()
            decoded_sig = urllib.parse.unquote(base64_sig)
            signature_bytes = base64.b64decode(decoded_sig)

            public_key.verify(
                signature_bytes,
                sign_content.encode("utf-8"),
                padding.PKCS1v15(),
                hashes.SHA256(),
            )
            return True
        except Exception:
            logger.warning("WfSigner response signature verification failed, api_path=%s", api_path, exc_info=True)
            return False

    @staticmethod
    def get_current_timestamp() -> str:
        """获取当前时间戳（ISO 8601，Asia/Shanghai 时区）

        Returns:
            格式如 2024-01-15T10:30:00+08:00
        """
        return datetime.now(_CST).strftime("%Y-%m-%dT%H:%M:%S+08:00")

    def _load_private_key(self):
        """加载 PKCS#8 私钥"""
        with open(self._config.private_key_path, "rb") as f:
            return serialization.load_pem_private_key(f.read(), password=None)

    def _load_public_key(self):
        """加载 X.509 公钥"""
        with open(self._config.public_key_path, "rb") as f:
            return serialization.load_pem_public_key(f.read())
