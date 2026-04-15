# -*- coding: utf-8 -*-
"""
WorldFirst 账单流水记录及相关 domain 模型

@author Qoder
@version statement_record.py, v 0.1 2026-04-14
"""

from dataclasses import dataclass, field
from typing import Optional


@dataclass
class Amount:
    """金额对象

    Attributes:
        value: 金额数值（字符串，保留精度）
        currency: 币种（ISO-4217）
    """

    value: Optional[str] = None
    currency: Optional[str] = None

    @classmethod
    def from_dict(cls, data: dict) -> Optional["Amount"]:
        if data is None:
            return None
        return cls(
            value=data.get("value"),
            currency=data.get("currency"),
        )

    def to_dict(self) -> dict:
        result = {}
        if self.value is not None:
            result["value"] = self.value
        if self.currency is not None:
            result["currency"] = self.currency
        return result


@dataclass
class ForeignExchangeQuote:
    """汇率报价信息

    用于正常交易（foreignExchangeQuote）和退款交易（refundForeignExchangeQuote）。

    Attributes:
        quote_price: 汇率价格
        transfer_from_currency: 源币种
        transfer_to_currency: 目标币种
    """

    quote_price: Optional[str] = None
    transfer_from_currency: Optional[str] = None
    transfer_to_currency: Optional[str] = None

    @classmethod
    def from_dict(cls, data: dict) -> Optional["ForeignExchangeQuote"]:
        if data is None:
            return None
        return cls(
            quote_price=data.get("quotePrice"),
            transfer_from_currency=data.get("transferFromCurrency"),
            transfer_to_currency=data.get("transferToCurrency"),
        )

    def to_dict(self) -> dict:
        result = {}
        if self.quote_price is not None:
            result["quotePrice"] = self.quote_price
        if self.transfer_from_currency is not None:
            result["transferFromCurrency"] = self.transfer_from_currency
        if self.transfer_to_currency is not None:
            result["transferToCurrency"] = self.transfer_to_currency
        return result


@dataclass
class FundMoveDetail:
    """资金流动详情

    包含付款方信息、收款方信息及交易说明。

    Attributes:
        payer_name: 付款方名称
        payer_account_no: 付款方账号
        payer_account_type: 付款方账户类型
        payer_bank_name: 付款方银行名称
        payer_user_id: 付款方用户 ID
        beneficiary_name: 收款方名称
        beneficiary_account_no: 收款方账号
        beneficiary_account_type: 收款方账户类型
        beneficiary_bank_country: 收款方银行国家
        beneficiary_bank_name: 收款方银行名称
        beneficiary_store_name: 收款方店铺名称
        beneficiary_marketplace_name: 收款方平台名称
        receive_account: 收款账户
        remarks: 备注
        description: 描述
        payment_explanation: 付款说明
        payment_subject: 付款主题
        payment_voucher_no: 付款凭证号
    """

    # 付款方信息
    payer_name: Optional[str] = None
    payer_account_no: Optional[str] = None
    payer_account_type: Optional[str] = None
    payer_bank_name: Optional[str] = None
    payer_user_id: Optional[str] = None

    # 收款方信息
    beneficiary_name: Optional[str] = None
    beneficiary_account_no: Optional[str] = None
    beneficiary_account_type: Optional[str] = None
    beneficiary_bank_country: Optional[str] = None
    beneficiary_bank_name: Optional[str] = None
    beneficiary_store_name: Optional[str] = None
    beneficiary_marketplace_name: Optional[str] = None
    receive_account: Optional[str] = None

    # 交易说明
    remarks: Optional[str] = None
    description: Optional[str] = None
    payment_explanation: Optional[str] = None
    payment_subject: Optional[str] = None
    payment_voucher_no: Optional[str] = None

    @classmethod
    def from_dict(cls, data: dict) -> Optional["FundMoveDetail"]:
        if data is None:
            return None
        return cls(
            payer_name=data.get("payerName"),
            payer_account_no=data.get("payerAccountNo"),
            payer_account_type=data.get("payerAccountType"),
            payer_bank_name=data.get("payerBankName"),
            payer_user_id=data.get("payerUserId"),
            beneficiary_name=data.get("beneficiaryName"),
            beneficiary_account_no=data.get("beneficiaryAccountNo"),
            beneficiary_account_type=data.get("beneficiaryAccountType"),
            beneficiary_bank_country=data.get("beneficiaryBankCountry"),
            beneficiary_bank_name=data.get("beneficiaryBankName"),
            beneficiary_store_name=data.get("beneficiaryStoreName"),
            beneficiary_marketplace_name=data.get("beneficiaryMarketplaceName"),
            receive_account=data.get("receiveAccount"),
            remarks=data.get("remarks"),
            description=data.get("description"),
            payment_explanation=data.get("paymentExplanation"),
            payment_subject=data.get("paymentSubject"),
            payment_voucher_no=data.get("paymentVoucherNo"),
        )

    def to_dict(self) -> dict:
        result = {}
        _fields = {
            "payerName": self.payer_name,
            "payerAccountNo": self.payer_account_no,
            "payerAccountType": self.payer_account_type,
            "payerBankName": self.payer_bank_name,
            "payerUserId": self.payer_user_id,
            "beneficiaryName": self.beneficiary_name,
            "beneficiaryAccountNo": self.beneficiary_account_no,
            "beneficiaryAccountType": self.beneficiary_account_type,
            "beneficiaryBankCountry": self.beneficiary_bank_country,
            "beneficiaryBankName": self.beneficiary_bank_name,
            "beneficiaryStoreName": self.beneficiary_store_name,
            "beneficiaryMarketplaceName": self.beneficiary_marketplace_name,
            "receiveAccount": self.receive_account,
            "remarks": self.remarks,
            "description": self.description,
            "paymentExplanation": self.payment_explanation,
            "paymentSubject": self.payment_subject,
            "paymentVoucherNo": self.payment_voucher_no,
        }
        for k, v in _fields.items():
            if v is not None:
                result[k] = v
        return result


@dataclass
class OperatorInfo:
    """操作员信息（仅通过 WF 门户操作时返回）

    Attributes:
        operator_name: 操作员名称
        operator_email: 操作员邮箱
    """

    operator_name: Optional[str] = None
    operator_email: Optional[str] = None

    @classmethod
    def from_dict(cls, data: dict) -> Optional["OperatorInfo"]:
        if data is None:
            return None
        return cls(
            operator_name=data.get("operatorName"),
            operator_email=data.get("operatorEmail"),
        )

    def to_dict(self) -> dict:
        result = {}
        if self.operator_name is not None:
            result["operatorName"] = self.operator_name
        if self.operator_email is not None:
            result["operatorEmail"] = self.operator_email
        return result


@dataclass
class StatementRecord:
    """账单流水记录

    Attributes:
        transaction_id: 交易 ID
        transaction_time: 交易时间（ISO 8601）
        transaction_type: 交易类型（TRANSFER, CHARGE, COLLECTION 等）
        transaction_status: 交易状态（SUCCESS, PROCESSING, FAIL, REFUNDED）
        balance_type: 余额类型
        account_balance: 交易后账户余额
        fee_amount: 手续费金额
        net_amount: 扣除手续费后净额
        original_transaction_amount: 原始提交的交易金额
        receive_amount: 换汇后实际到账金额
        transaction_amount: 本次余额变动金额
        goods_amount: 商品金额
        platform_fee_amount: 平台手续费金额
        original_fee_amount: 优惠前手续费金额
        discount_fee_amount: 手续费优惠金额
        ext_transaction_id: 外部交易 ID
        accounting_biz_no: 账单流水唯一 ID
        goods_name: 商品名称
        foreign_exchange_quote: 汇率报价信息
        refund_foreign_exchange_quote: 退款汇率报价信息
        fund_move_detail: 资金流动详情
        operator_info: 操作员信息
    """

    transaction_id: Optional[str] = None
    transaction_time: Optional[str] = None
    transaction_type: Optional[str] = None
    transaction_status: Optional[str] = None
    balance_type: Optional[str] = None
    account_balance: Optional[Amount] = None
    fee_amount: Optional[Amount] = None
    net_amount: Optional[Amount] = None
    original_transaction_amount: Optional[Amount] = None
    receive_amount: Optional[Amount] = None
    transaction_amount: Optional[Amount] = None
    goods_amount: Optional[Amount] = None
    platform_fee_amount: Optional[Amount] = None
    original_fee_amount: Optional[Amount] = None
    discount_fee_amount: Optional[Amount] = None
    ext_transaction_id: Optional[str] = None
    accounting_biz_no: Optional[str] = None
    goods_name: Optional[str] = None
    foreign_exchange_quote: Optional[ForeignExchangeQuote] = None
    refund_foreign_exchange_quote: Optional[ForeignExchangeQuote] = None
    fund_move_detail: Optional[FundMoveDetail] = None
    operator_info: Optional[OperatorInfo] = None

    @classmethod
    def from_dict(cls, data: dict) -> "StatementRecord":
        if data is None:
            return cls()
        return cls(
            transaction_id=data.get("transactionId"),
            transaction_time=data.get("transactionTime"),
            transaction_type=data.get("transactionType"),
            transaction_status=data.get("transactionStatus"),
            balance_type=data.get("balanceType"),
            account_balance=Amount.from_dict(data.get("accountBalance")),
            fee_amount=Amount.from_dict(data.get("feeAmount")),
            net_amount=Amount.from_dict(data.get("netAmount")),
            original_transaction_amount=Amount.from_dict(data.get("originalTransactionAmount")),
            receive_amount=Amount.from_dict(data.get("receiveAmount")),
            transaction_amount=Amount.from_dict(data.get("transactionAmount")),
            goods_amount=Amount.from_dict(data.get("goodsAmount")),
            platform_fee_amount=Amount.from_dict(data.get("platformFeeAmount")),
            original_fee_amount=Amount.from_dict(data.get("originalFeeAmount")),
            discount_fee_amount=Amount.from_dict(data.get("discountFeeAmount")),
            ext_transaction_id=data.get("extTransactionId"),
            accounting_biz_no=data.get("accountingBizNo"),
            goods_name=data.get("goodsName"),
            foreign_exchange_quote=ForeignExchangeQuote.from_dict(data.get("foreignExchangeQuote")),
            refund_foreign_exchange_quote=ForeignExchangeQuote.from_dict(data.get("refundForeignExchangeQuote")),
            fund_move_detail=FundMoveDetail.from_dict(data.get("fundMoveDetail")),
            operator_info=OperatorInfo.from_dict(data.get("operatorInfo")),
        )

