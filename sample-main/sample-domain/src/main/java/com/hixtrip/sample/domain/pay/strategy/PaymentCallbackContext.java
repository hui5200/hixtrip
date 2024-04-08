package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.impl.DuplicatePaymentCallback;
import com.hixtrip.sample.domain.pay.strategy.impl.PaymentFailureCallback;
import com.hixtrip.sample.domain.pay.strategy.impl.PaymentSuccessCallback;

import java.util.HashMap;
import java.util.Map;

public class PaymentCallbackContext {
    private final Map<String, PaymentCallbackStrategy> paymentCallbackStrategies = new HashMap<>();
    private final PaymentCallbackStrategy defaultStrategy;

    public PaymentCallbackContext() {
        // 初始化回调策略
        paymentCallbackStrategies.put(PayStatusEnum.SUCCESS.getKey(), new PaymentSuccessCallback());
        paymentCallbackStrategies.put(PayStatusEnum.FAILURE.getKey(), new PaymentFailureCallback());
        paymentCallbackStrategies.put(PayStatusEnum.DUPLICATE.getKey(), new DuplicatePaymentCallback());
        // 设置默认策略
        defaultStrategy = paymentCallbackStrategies.get(PayStatusEnum.SUCCESS.getKey());
    }

    public void executeStrategy(CommandPay commandPay) {
        PaymentCallbackStrategy strategy = paymentCallbackStrategies.getOrDefault(commandPay.getPayStatus(), defaultStrategy);
        strategy.handleCallback(commandPay);
    }
}
