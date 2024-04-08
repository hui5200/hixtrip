package com.hixtrip.sample.domain.pay.strategy.impl;

import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PaymentCallbackStrategy;

public class DuplicatePaymentCallback implements PaymentCallbackStrategy {
    @Override
    public void handleCallback(CommandPay order) {
        order.setPayStatus(PayStatusEnum.DUPLICATE.getKey());
        System.out.println("重复支付回调处理逻辑...");
    }
}