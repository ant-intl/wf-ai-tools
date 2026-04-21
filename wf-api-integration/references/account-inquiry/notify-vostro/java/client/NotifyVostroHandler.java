/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.client.account;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.alibaba.fastjson.JSON;
import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.request.NotifyVostroRequest;
import {basePackage}.wf.model.response.NotifyVostroResponse;
import {basePackage}.wf.model.response.Result;
import {basePackage}.wf.signer.WfSigner;

/**
 * WorldFirst notifyVostro 回调通知处理器。
 *
 * <p>当集成商的万里汇账户发生充值后，万里汇会调用 notifyVostro 接口通知集成商。
 * 本处理器负责：
 * <ol>
 *   <li>验签：校验请求签名，确保请求来自万里汇</li>
 *   <li>解析：将请求体 JSON 解析为 {@link NotifyVostroRequest}</li>
 *   <li>幂等校验：基于 {@code fundingId} 进行去重</li>
 *   <li>业务处理：处理垫付成功/退款通知</li>
 *   <li>构建响应：返回 {@link NotifyVostroResponse} 确认收到通知</li>
 * </ol>
 *
 * <p>若不返回成功响应，万里汇将按以下间隔重试（最多 7 次）：
 * 2分钟、10分钟、10分钟、1小时、2小时、6小时、15小时
 *
 * @author Qoder
 * @version NotifyVostroHandler.java, v 0.1 2026-04-21
 */
public class NotifyVostroHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyVostroHandler.class);

    private static final String RESULT_STATUS_SUCCESS = "S";

    private WfSigner signer;

    /**
     * 处理 notifyVostro 回调通知。
     *
     * <p>处理流程：
     * <ol>
     *   <li>验签（调用方应在 Controller 层完成验签，此处仅做业务处理）</li>
     *   <li>解析请求体</li>
     *   <li>基于 fundingId 做幂等校验</li>
     *   <li>根据 balanceResult 处理业务逻辑</li>
     *   <li>返回成功响应</li>
     * </ol>
     *
     * @param requestBody 请求体 JSON 字符串
     * @return 响应对象
     */
    public NotifyVostroResponse handleNotification(String requestBody) {
        LOGGER.info("NotifyVostroHandler received notification");

        // 1. 解析请求体
        NotifyVostroRequest request;
        try {
            request = JSON.parseObject(requestBody, NotifyVostroRequest.class);
        } catch (Exception e) {
            LOGGER.error("NotifyVostroHandler failed to parse request body, body=" + requestBody, e);
            return NotifyVostroResponse.fail("UNKNOWN_EXCEPTION",
                "Failed to parse request body: " + e.getMessage());
        }

        if (request == null || request.getFundingId() == null) {
            LOGGER.error("NotifyVostroHandler request or fundingId is null");
            return NotifyVostroResponse.fail("UNKNOWN_EXCEPTION", "fundingId is missing");
        }

        String fundingId = request.getFundingId();
        LOGGER.info("NotifyVostroHandler processing fundingId=" + fundingId);

        // 2. 幂等校验（集成商需自行实现去重逻辑）
        // TODO: 根据 fundingId 查询是否已处理过，若已处理则直接返回成功
        // if (isDuplicate(fundingId)) {
        //     LOGGER.info("NotifyVostroHandler duplicate fundingId=" + fundingId + ", skip processing");
        //     return NotifyVostroResponse.success();
        // }

        // 3. 处理业务逻辑
        Result balanceResult = request.getBalanceResult();
        if (balanceResult == null) {
            LOGGER.error("NotifyVostroHandler balanceResult is null, fundingId=" + fundingId);
            return NotifyVostroResponse.fail("UNKNOWN_EXCEPTION", "balanceResult is missing");
        }

        String resultCode = balanceResult.getResultCode();
        String resultStatus = balanceResult.getResultStatus();

        if (RESULT_STATUS_SUCCESS.equals(resultStatus)) {
            if ("SUCCESS".equals(resultCode)) {
                // 垫付成功
                handleFundingSuccess(request);
            } else if ("REFUND".equals(resultCode)) {
                // 退款成功
                handleRefundSuccess(request);
            } else {
                LOGGER.warn("NotifyVostroHandler unknown resultCode=" + resultCode
                    + " with resultStatus=S, fundingId=" + fundingId);
                handleFundingSuccess(request);
            }
        } else {
            LOGGER.warn("NotifyVostroHandler unexpected resultStatus=" + resultStatus
                + ", resultCode=" + resultCode + ", fundingId=" + fundingId);
        }

        // 4. 标记已处理（集成商需自行实现持久化逻辑）
        // TODO: markAsProcessed(fundingId);

        // 5. 返回成功响应
        LOGGER.info("NotifyVostroHandler processed successfully, fundingId=" + fundingId);
        return NotifyVostroResponse.success();
    }

    /**
     * 处理垫付成功通知。
     *
     * <p>集成商需在此方法中实现具体的业务逻辑，例如：
     * <ul>
     *   <li>更新账户余额</li>
     *   <li>记录充值流水</li>
     *   <li>发送内部通知</li>
     * </ul>
     *
     * @param request 通知请求
     */
    private void handleFundingSuccess(NotifyVostroRequest request) {
        Amount amount = request.getBalanceChangeAmount();
        LOGGER.info("NotifyVostroHandler funding success, fundingId=" + request.getFundingId()
            + ", amount=" + (amount != null ? amount.getValue() + " " + amount.getCurrency() : "null")
            + ", beneficiaryAccount=" + (request.getBeneficiaryAccount() != null
                ? request.getBeneficiaryAccount().getBeneficiaryBankAccountNo() : "null")
            + ", balanceChangeTime=" + request.getBalanceChangeTime());

        // TODO: 集成商实现具体业务逻辑
    }

    /**
     * 处理退款成功通知。
     *
     * <p>集成商需在此方法中实现退款相关的业务逻辑。
     *
     * @param request 通知请求
     */
    private void handleRefundSuccess(NotifyVostroRequest request) {
        Amount amount = request.getBalanceChangeAmount();
        LOGGER.info("NotifyVostroHandler refund success, fundingId=" + request.getFundingId()
            + ", amount=" + (amount != null ? amount.getValue() + " " + amount.getCurrency() : "null")
            + ", beneficiaryAccount=" + (request.getBeneficiaryAccount() != null
                ? request.getBeneficiaryAccount().getBeneficiaryBankAccountNo() : "null")
            + ", balanceChangeTime=" + request.getBalanceChangeTime());

        // TODO: 集成商实现退款业务逻辑
    }

    // ==================== Getter/Setter ====================

    /**
     * Setter method for property <tt>signer</tt>.
     *
     * @param signer value to be assigned to property signer
     */
    public void setSigner(WfSigner signer) {
        this.signer = signer;
    }

    /**
     * Getter method for property <tt>signer</tt>.
     *
     * @return property value of signer
     */
    public WfSigner getSigner() {
        return signer;
    }
}
