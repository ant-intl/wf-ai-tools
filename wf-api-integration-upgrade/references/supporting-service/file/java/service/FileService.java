package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.DownloadFileRequest;
import {basePackage}.wf.model.request.UploadFileRequest;
import {basePackage}.wf.model.response.DownloadFileResponse;
import {basePackage}.wf.model.response.UploadFileResponse;

/**
 * WorldFirst 文件上传/下载（File）服务。
 *
 * <p>内部使用 {@link WfApiClient} 处理 HTTP 请求与加验签，采用薄封装模式：
 * 不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。
 * <p><b>安全强制</b>：{@link WfApiClient} 在返回响应前会执行验签，
 * 验签失败时抛出 {@link WfException}（错误码 {@link WfErrorCode#INVALID_SIGNATURE}），
 * 确保调用方拿到的响应均已完成验签，避免中间人攻击。
 *
 * <pre>{@code
 * // 1. 创建配置
 * WfClientConfig config = WfClientConfig.builder()
 *     .clientId("YOUR_CLIENT_ID")
 *     .privateKeyFromPath("/path/to/private_key.pem")
 *     .publicKeyFromPath("/path/to/wf_public_key.pem")
 *     .baseUrl("https://YOUR_BASE_URL")
 *     .build();
 *
 * // 2. 创建服务
 * FileService fileService = new FileService(config);
 *
 * // 3. 上传文件（内部已强制验签，验签失败抛 WfException）
 * UploadFileRequest uploadRequest = new UploadFileRequest();
 * uploadRequest.setFileContent(base64EncodedContent);
 * uploadRequest.setFileName("business_license.pdf");
 * UploadFileResponse uploadResponse = fileService.uploadFile(uploadRequest);
 *
 * // 4. 下载文件（内部已强制验签，验签失败抛 WfException）
 * DownloadFileRequest downloadRequest = new DownloadFileRequest();
 * downloadRequest.setId(uploadResponse.getId());
 * DownloadFileResponse downloadResponse = fileService.downloadFile(downloadRequest);
 * }</pre>
 */
public class FileService {

    /** 上传文件 API 路径 */
    private static final String UPLOAD_FILE_ENDPOINT = "/api/open/v1/files/upload";

    /** 下载文件 API 路径 */
    private static final String DOWNLOAD_FILE_ENDPOINT = "/api/open/v1/files/download";

    private final WfApiClient apiClient;

    /**
     * 创建 FileService 实例。
     *
     * @param config 客户端配置
     */
    public FileService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 FileService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public FileService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 文件 API ========================

    /**
     * 上传文件。
     * <p>
     * 将 base64 编码的文件上传至 WorldFirst，用于开户、KYC/KYB 验证等场景。
     * 支持格式：PDF、JPG、JPEG、PNG、ZIP，原始文件最大 7 MB。
     * <p>
     * 对应 WF API: POST /api/open/v1/files/upload
     *
     * @param request 上传文件请求（包含 fileContent、fileName）
     * @return 上传文件响应（已验签并反序列化），包含文件 ID
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public UploadFileResponse uploadFile(UploadFileRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(UPLOAD_FILE_ENDPOINT, body, UploadFileResponse.class).getServiceResponse();
    }

    /**
     * 下载文件。
     * <p>
     * 根据文件 ID 获取临时下载链接，链接有效期 1 小时。
     * <p>
     * 对应 WF API: POST /api/open/v1/files/download
     *
     * @param request 下载文件请求（包含 id）
     * @return 下载文件响应（已验签并反序列化），包含临时下载链接及过期时间
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public DownloadFileResponse downloadFile(DownloadFileRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(DOWNLOAD_FILE_ENDPOINT, body, DownloadFileResponse.class).getServiceResponse();
    }
}
