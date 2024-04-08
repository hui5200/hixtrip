package com.hixtrip.sample.domain.pay.strategy.impl;

import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PaymentCallbackStrategy;

public class PaymentSuccessCallback implements PaymentCallbackStrategy {
    @Override
    public void handleCallback(CommandPay commandPay) {
        commandPay.setPayStatus(PayStatusEnum.SUCCESS.getKey());
        System.out.println("支付成功回调处理逻辑...");
    }
}