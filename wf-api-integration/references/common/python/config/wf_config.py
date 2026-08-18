# -*- coding: utf-8 -*-
"""
WorldFirst API 配置类
"""

import os


class WfConfig:
    """WorldFirst API 配置管理

    Attributes:
        client_id: WF client identifier（由 WF 分配）
        user_id: WF user identifier（即登录 WF 的 userId）
        base_url: WF API base URL
        private_key_path: RSA 私钥文件路径（PKCS#8 PEM）
        public_key_path: 万里汇 RSA 公钥文件路径
        connect_timeout: HTTP 连接超时（秒）
        read_timeout: HTTP 读取超时（秒）
    """

    def __init__(
        self,
        client_id: str = None,
        user_id: str = None,
        base_url: str = "https://open-sea.worldfirst.com",
        private_key_path: str = None,
        public_key_path: str = None,
        connect_timeout: int = 10,
        read_timeout: int = 30,
    ):
        """初始化 WfConfig

        优先使用构造参数，其次从环境变量读取，避免硬编码敏感信息。

        Args:
            client_id: WF Client ID，未提供时从环境变量 WF_CLIENT_ID 读取
            user_id: WF User ID，未提供时从环境变量 WF_USER_ID 读取
            base_url: API 网关地址
            private_key_path: RSA 私钥文件路径，未提供时从环境变量 WF_PRIVATE_KEY_PATH 读取
            public_key_path: WF RSA 公钥文件路径，未提供时从环境变量 WF_PUBLIC_KEY_PATH 读取
            connect_timeout: HTTP 连接超时（秒），默认 10
            read_timeout: HTTP 读取超时（秒），默认 30
        """
        self.client_id = client_id or os.getenv("WF_CLIENT_ID", "")
        self.user_id = user_id or os.getenv("WF_USER_ID", "")
        self.base_url = base_url or os.getenv("WF_BASE_URL", "https://open-sea.worldfirst.com")
        self.private_key_path = private_key_path or os.getenv("WF_PRIVATE_KEY_PATH", "")
        self.public_key_path = public_key_path or os.getenv("WF_PUBLIC_KEY_PATH", "")
        self.connect_timeout = connect_timeout
        self.read_timeout = read_timeout

    def __repr__(self) -> str:
        return (
            f"WfConfig(client_id='{self.client_id}', user_id='{self.user_id}', "
            f"base_url='{self.base_url}', connect_timeout={self.connect_timeout}, "
            f"read_timeout={self.read_timeout})"
        )
