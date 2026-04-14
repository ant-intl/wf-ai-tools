# -*- coding: utf-8 -*-
"""
WorldFirst API HTTP 客户端工具类

@author Qoder
@version wf_http_client.py, v 0.1 2026-03-24
"""

import logging
from datetime import datetime, timezone, timedelta

import requests

from {basePackage}.wf.config.wf_config import WfConfig
from {basePackage}.wf.model.exception.wf_error_code import WfErrorCode
from {basePackage}.wf.model.exception.wf_exception import WfException
from {basePackage}.wf.signer.wf_signer import WfSigner

logger = logging.getLogger(__name__)

_CST = timezone(timedelta(hours=8))


class WfHttpClient:
    """WorldFirst API HTTP 客户端

    封装签名注入、请求发送、响应验签的完整流程。

    Attributes:
        config: WfConfig 配置实例
        signer: WfSigner 签名工具实例
    """

    def __init__(self, config: WfConfig, signer: WfSigner = None):
        """构造函数

        Args:
            config: WF 配置，不得为 None
            signer: 签名器，为 None 时自动创建。测试场景可注入 mock signer。
        """
        if config is None:
            raise ValueError("config must not be None")
        self._config = config
        self._signer = signer or WfSigner(config)
        self._session = requests.Session()
        self._session.timeout = (config.connect_timeout, config.read_timeout)

    @property
    def config(self) -> WfConfig:
        return self._config

    @property
    def signer(self) -> WfSigner:
        return self._signer

    def send_post_request(self, url: str, api_path: str, request_body: str) -> str:
        """发送已签名的 POST 请求并返回响应体

        Args:
            url: 完整请求 URL
            api_path: API 路径（用于生成签名）
            request_body: 请求体 JSON 字符串

        Returns:
            响应体字符串

        Raises:
            WfException: 请求失败时抛出
        """
        request_time = datetime.now(_CST).strftime("%Y-%m-%dT%H:%M:%S+08:00")
        signature = self._signer.generate_signature(api_path, request_time, request_body)

        if signature is None:
            raise WfException(
                WfErrorCode.SIGNATURE_GENERATION_FAILED,
                f"Failed to generate signature for path: {api_path}",
            )

        logger.info("WfHttpClient sending POST request, url=%s, request_time=%s", url, request_time)
        logger.debug("WfHttpClient request body: %s", request_body)

        headers = {
            "Content-Type": "application/json; charset=UTF-8",
            "Client-Id": self._config.client_id,
            "Request-Time": request_time,
            "Signature": f"algorithm=RSA256, keyVersion=2, signature={signature}",
        }

        try:
            response = self._session.post(
                url,
                data=request_body.encode("utf-8"),
                headers=headers,
                timeout=(self._config.connect_timeout, self._config.read_timeout),
            )
            return self._handle_response(response, api_path)
        except WfException:
            raise
        except Exception as e:
            logger.exception("WfHttpClient HTTP request failed, url=%s", url)
            raise WfException(WfErrorCode.HTTP_REQUEST_FAILED, str(e), cause=e)

    def close(self):
        """关闭底层 HTTP 会话"""
        self._session.close()

    def _handle_response(self, response: requests.Response, api_path: str) -> str:
        """处理 HTTP 响应并验签

        Args:
            response: HTTP 响应对象
            api_path: API 路径（用于验签）

        Returns:
            响应体字符串

        Raises:
            WfException: HTTP 状态码非 200 或验签失败时抛出
        """
        status_code = response.status_code
        body = response.text
        logger.info("WfHttpClient received response, status_code=%d", status_code)
        logger.debug("WfHttpClient response body: %s", body)

        if status_code != 200:
            raise WfException(
                WfErrorCode.HTTP_REQUEST_FAILED,
                f"HTTP status {status_code}, body: {body}",
            )

        # 验证 WF 响应签名
        signature_header = response.headers.get("Signature")
        response_time = response.headers.get("response-time")
        if signature_header:
            valid = self._signer.verify_signature(signature_header, api_path, response_time, body)
            if not valid:
                logger.error("WfHttpClient response signature verification failed, api_path=%s", api_path)
                raise WfException(
                    WfErrorCode.INVALID_SIGNATURE,
                    "Response signature verification failed",
                )
            logger.debug("WfHttpClient response signature verified, api_path=%s", api_path)
        else:
            logger.warning("WfHttpClient no Signature header in response, api_path=%s", api_path)

        return body
