package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

    @Resource
    private OrderService orderService;

    @Test
    void createOrder() {
        CommandOderCreateDTO commandOderCreateDTO = new CommandOderCreateDTO();
        commandOderCreateDTO.setAmount(2);
        commandOderCreateDTO.setSkuId("1561651");
        orderService.createOrder(commandOderCreateDTO);
    }

    @Test
    void payCallback() {
        CommandPayDTO commandPayDTO = new CommandPayDTO();
        commandPayDTO.setPayStatus(PayStatusEnum.SUCCESS.getKey());
        commandPayDTO.setOrderId("165165156");
        orderService.payCallback(commandPayDTO);
    }
}