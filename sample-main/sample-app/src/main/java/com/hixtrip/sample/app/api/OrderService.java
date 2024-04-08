package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;

/**
 * 订单的service层
 */
public interface OrderService {

    /**
     * 创建订单
     * @param commandOderCreateDTO
     */
    String createOrder(CommandOderCreateDTO commandOderCreateDTO);

    /**
     * 支付结果的回调通知
     * @param commandPayDTO
     * @return
     */
    Boolean payCallback(CommandPayDTO commandPayDTO);
}
