package {basePackage}.wf.model.request;

import java.util.List;

import {basePackage}.wf.model.domain.Buyer;
import {basePackage}.wf.model.domain.Goods;
import {basePackage}.wf.model.domain.Merchant;
import {basePackage}.wf.model.domain.Seller;
import {basePackage}.wf.model.domain.Shipping;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst submit_trade_orders 请求对象。
 *
 * <p>用于提交一批 B2B/B2C 贸易订单，关联来款或累积 CNY 跨境结算额度。
 */
public class SubmitTradeOrdersRequest {

    /** 批次幂等键，每次请求使用唯一值（如 UUID），重试时使用相同值不产生重复提交 */
    private String batchRequestId;

    /** 业务场景码（SceneCode 枚举），如 PAY_INTO_CHINA */
    private String sceneCode;

    /** 子场景码，用于自定义路由，不适用时可省略 */
    private String subSceneCode;

    /** 额度累积方式（QuotaAccumulationMethod 枚举） */
    private String quotaAccumulationMethod;

    /** 与 quotaAccumulationMethod 配对的累积标识 */
    private String quotaAccumulationId;

    /** 贸易来源平台（如 AE） */
    private String platform;

    /** 贸易订单详情列表，最多 100 条，至少 1 条 */
    private List<TradeOrder> tradeOrders;

    public SubmitTradeOrdersRequest() {
    }

    public String getBatchRequestId() {
        return batchRequestId;
    }

    public void setBatchRequestId(String batchRequestId) {
        this.batchRequestId = batchRequestId;
    }

    public String getSceneCode() {
        return sceneCode;
    }

    public void setSceneCode(String sceneCode) {
        this.sceneCode = sceneCode;
    }

    public String getSubSceneCode() {
        return subSceneCode;
    }

    public void setSubSceneCode(String subSceneCode) {
        this.subSceneCode = subSceneCode;
    }

    public String getQuotaAccumulationMethod() {
        return quotaAccumulationMethod;
    }

    public void setQuotaAccumulationMethod(String quotaAccumulationMethod) {
        this.quotaAccumulationMethod = quotaAccumulationMethod;
    }

    public String getQuotaAccumulationId() {
        return quotaAccumulationId;
    }

    public void setQuotaAccumulationId(String quotaAccumulationId) {
        this.quotaAccumulationId = quotaAccumulationId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public List<TradeOrder> getTradeOrders() {
        return tradeOrders;
    }

    public void setTradeOrders(List<TradeOrder> tradeOrders) {
        this.tradeOrders = tradeOrders;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * 单笔贸易订单详情。
     */
    public static class TradeOrder {

        /** 商户分配的订单号，用于在 query 响应中关联结果 */
        private String referenceOrderNo;

        /** 订单下单时间（ISO 8601 扩展格式） */
        private String orderedAt;

        /** 支付完成时间（ISO 8601 扩展格式） */
        private String paidAt;

        /** 资金方向：CREDIT（收款）或 DEBIT（退款） */
        private String orderDirection;

        /** 贸易类别：GOODS（货物贸易）或 SERVICE（服务贸易） */
        private String tradeCategory;

        /** 实际交易收款金额 */
        private Amount transAmount;

        /** 申报结算金额，可能因手续费或折扣与 transAmount 不同 */
        private Amount tradeAmount;

        /** 商户信息 */
        private Merchant merchant;

        /** 卖家（收款方）信息 */
        private Seller seller;

        /** 买家（付款方）信息 */
        private Buyer buyer;

        /** 商品列表，最多 200 条，至少 1 条 */
        private List<Goods> goods;

        /** 物流发货详情，tradeCategory 为 GOODS 时必填 */
        private Shipping shipping;

        /** 子平台来源标识 */
        private String subPlatform;

        /** 官方业务参考号（如保险单号） */
        private String officialBizNo;

        /** 物流模式：DROPSHIPPING（供应商直发）或 REGULAR_MODE（标准物流） */
        private String logisticsMode;

        /** 是否用于换汇（CNY 结算额度累积），设为 true 表示用于额度累积 */
        private Boolean usedForExchange;

        public TradeOrder() {
        }

        public String getReferenceOrderNo() {
            return referenceOrderNo;
        }

        public void setReferenceOrderNo(String referenceOrderNo) {
            this.referenceOrderNo = referenceOrderNo;
        }

        public String getOrderedAt() {
            return orderedAt;
        }

        public void setOrderedAt(String orderedAt) {
            this.orderedAt = orderedAt;
        }

        public String getPaidAt() {
            return paidAt;
        }

        public void setPaidAt(String paidAt) {
            this.paidAt = paidAt;
        }

        public String getOrderDirection() {
            return orderDirection;
        }

        public void setOrderDirection(String orderDirection) {
            this.orderDirection = orderDirection;
        }

        public String getTradeCategory() {
            return tradeCategory;
        }

        public void setTradeCategory(String tradeCategory) {
            this.tradeCategory = tradeCategory;
        }

        public {basePackage}.wf.model.domain.Amount getTransAmount() {
            return transAmount;
        }

        public void setTransAmount({basePackage}.wf.model.domain.Amount transAmount) {
            this.transAmount = transAmount;
        }

        public {basePackage}.wf.model.domain.Amount getTradeAmount() {
            return tradeAmount;
        }

        public void setTradeAmount({basePackage}.wf.model.domain.Amount tradeAmount) {
            this.tradeAmount = tradeAmount;
        }

        public Merchant getMerchant() {
            return merchant;
        }

        public void setMerchant(Merchant merchant) {
            this.merchant = merchant;
        }

        public Seller getSeller() {
            return seller;
        }

        public void setSeller(Seller seller) {
            this.seller = seller;
        }

        public Buyer getBuyer() {
            return buyer;
        }

        public void setBuyer(Buyer buyer) {
            this.buyer = buyer;
        }

        public List<Goods> getGoods() {
            return goods;
        }

        public void setGoods(List<Goods> goods) {
            this.goods = goods;
        }

        public Shipping getShipping() {
            return shipping;
        }

        public void setShipping(Shipping shipping) {
            this.shipping = shipping;
        }

        public String getSubPlatform() {
            return subPlatform;
        }

        public void setSubPlatform(String subPlatform) {
            this.subPlatform = subPlatform;
        }

        public String getOfficialBizNo() {
            return officialBizNo;
        }

        public void setOfficialBizNo(String officialBizNo) {
            this.officialBizNo = officialBizNo;
        }

        public String getLogisticsMode() {
            return logisticsMode;
        }

        public void setLogisticsMode(String logisticsMode) {
            this.logisticsMode = logisticsMode;
        }

        public Boolean getUsedForExchange() {
            return usedForExchange;
        }

        public void setUsedForExchange(Boolean usedForExchange) {
            this.usedForExchange = usedForExchange;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }
}
