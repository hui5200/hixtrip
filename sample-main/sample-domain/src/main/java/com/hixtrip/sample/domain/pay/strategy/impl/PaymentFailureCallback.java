package com.hixtrip.sample.domain.pay.strategy.impl;

import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PaymentCallbackStrategy;

public class PaymentFailureCallback implements PaymentCallbackStrategy {
    @Override
    public void handleCallback(CommandPay order) {
        order.setPayStatus(PayStatusEnum.FAILURE.getKey());
        System.out.println("支付失败回调处理逻辑...");
    }
}