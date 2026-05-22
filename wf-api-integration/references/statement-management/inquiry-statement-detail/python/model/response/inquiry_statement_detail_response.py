# -*- coding: utf-8 -*-
"""
WorldFirst inquiryStatementDetail 响应模型
"""

from dataclasses import dataclass, field
from typing import List, Optional

from {basePackage}.wf.model.response.result import Result
from {basePackage}.wf.model.domain.statement_record import (
    Amount,
    ForeignExchangeQuote,
    FundMoveDetail,
    OperatorInfo,
)
from {basePackage}.wf.model.domain.related_statement import RelatedStatement


@dataclass
class InquiryStatementDetailResponse:
    """inquiryStatementDetail 响应

    Attributes:
        result: 通用响应结果
        response_id: 响应唯一 ID，最大 32 位
        transaction_id: 交易 ID
        ext_transaction_id: 外部交易 ID，最大 256 位
        transaction_status: 交易状态（INIT/PROCESSING/PENDING/SUCCESS/FAIL/REFUNDED）
        transaction_time: 余额变动时间（ISO 8601）
        transaction_type: 交易类型（TRANSFER, COLLECTION 等）
        transaction_amount: 本次余额变动金额
        original_transaction_amount: 原始提交的交易金额
        fee_amount: 手续费金额
        fee_item_type: 手续费类型（OBO_SERVICE_FEE / REMIT_SERVICE_FEE）
        net_amount: 扣除手续费后净额
        receive_amount: 换汇后实际到账金额
        account_balance: 交易后账户实时余额
        fund_move_detail: 资金流动详情
        foreign_exchange_quote: 汇率报价信息
        refund_foreign_exchange_quote: 退款汇率报价信息
        balance_type: 余额类型（NORMAL_BALANCE / SAME_NAME_TOP_UP_BALANCE / BUDGET_BALANCE）
        accounting_biz_no: 账单流水唯一 ID
        fail_reason: 失败原因（transactionStatus=FAIL 时返回）
        combined_transaction_list: 关联交易列表
        operator_info: 操作员信息
        goods_name: 商品名称
        goods_amount: 商品金额
        original_fee_amount: 优惠前手续费金额
        discount_fee_amount: 手续费优惠金额
    """

    result: Optional[Result] = None
    response_id: Optional[str] = None
    transaction_id: Optional[str] = None
    ext_transaction_id: Optional[str] = None
    transaction_status: Optional[str] = None
    transaction_time: Optional[str] = None
    transaction_type: Optional[str] = None
    transaction_amount: Optional[Amount] = None
    original_transaction_amount: Optional[Amount] = None
    fee_amount: Optional[Amount] = None
    fee_item_type: Optional[str] = None
    net_amount: Optional[Amount] = None
    receive_amount: Optional[Amount] = None
    account_balance: Optional[Amount] = None
    fund_move_detail: Optional[FundMoveDetail] = None
    foreign_exchange_quote: Optional[ForeignExchangeQuote] = None
    refund_foreign_exchange_quote: Optional[ForeignExchangeQuote] = None
    balance_type: Optional[str] = None
    accounting_biz_no: Optional[str] = None
    fail_reason: Optional[Result] = None
    combined_transaction_list: Optional[List[RelatedStatement]] = None
    operator_info: Optional[OperatorInfo] = None
    goods_name: Optional[str] = None
    goods_amount: Optional[Amount] = None
    original_fee_amount: Optional[Amount] = None
    discount_fee_amount: Optional[Amount] = None

    @classmethod
    def from_dict(cls, data: dict) -> "InquiryStatementDetailResponse":
        """从字典创建响应对象

        Args:
            data: 响应字典

        Returns:
            InquiryStatementDetailResponse 实例
        """
        if data is None:
            return cls()

        combined_list_data = data.get("combinedTransactionList")
        combined_list = None
        if combined_list_data is not None:
            combined_list = [RelatedStatement.from_dict(item) for item in combined_list_data]

        return cls(
            result=Result.from_dict(data.get("result")),
            response_id=data.get("responseId"),
            transaction_id=data.get("transactionId"),
            ext_transaction_id=data.get("extTransactionId"),
            transaction_status=data.get("transactionStatus"),
            transaction_time=data.get("transactionTime"),
            transaction_type=data.get("transactionType"),
            transaction_amount=Amount.from_dict(data.get("transactionAmount")),
            original_transaction_amount=Amount.from_dict(data.get("originalTransactionAmount")),
            fee_amount=Amount.from_dict(data.get("feeAmount")),
            fee_item_type=data.get("feeItemType"),
            net_amount=Amount.from_dict(data.get("netAmount")),
            receive_amount=Amount.from_dict(data.get("receiveAmount")),
            account_balance=Amount.from_dict(data.get("accountBalance")),
            fund_move_detail=FundMoveDetail.from_dict(data.get("fundMoveDetail")),
            foreign_exchange_quote=ForeignExchangeQuote.from_dict(data.get("foreignExchangeQuote")),
            refund_foreign_exchange_quote=ForeignExchangeQuote.from_dict(data.get("refundForeignExchangeQuote")),
            balance_type=data.get("balanceType"),
            accounting_biz_no=data.get("accountingBizNo"),
            fail_reason=Result.from_dict(data.get("failReason")),
            combined_transaction_list=combined_list,
            operator_info=OperatorInfo.from_dict(data.get("operatorInfo")),
            goods_name=data.get("goodsName"),
            goods_amount=Amount.from_dict(data.get("goodsAmount")),
            original_fee_amount=Amount.from_dict(data.get("originalFeeAmount")),
            discount_fee_amount=Amount.from_dict(data.get("discountFeeAmount")),
        )

