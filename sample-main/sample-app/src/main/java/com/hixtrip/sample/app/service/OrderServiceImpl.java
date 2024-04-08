package com.hixtrip.sample.app.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    private static final String PREFIX = "ORDER";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private static final AtomicLong counter = new AtomicLong(0);

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private PayDomainService payDomainService;

    @Autowired
    private CommodityDomainService commodityDomainService;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
    public String createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        //获取sku价格
        if(StringUtils.isBlank(commandOderCreateDTO.getSkuId()) || Objects.isNull(commandOderCreateDTO.getAmount())) {
            throw new RuntimeException("订单信息有问题，请确认！");
        }
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(commandOderCreateDTO.getSkuId());
        // 库存扣减,预占库存和占用库存暂时设置为0
        Long sellableQuantity = Long.valueOf(commandOderCreateDTO.getAmount());
        boolean inventoryDecreased = inventoryDomainService.changeInventory(commandOderCreateDTO.getSkuId(), sellableQuantity, 0L, 0L);
        if (!inventoryDecreased) {
            throw new RuntimeException("库存不足，下单失败");
        }
        Order order = new Order().toBuilder()
                .id(generateOrderNumber())
                .money(skuPrice.multiply(BigDecimal.valueOf(commandOderCreateDTO.getAmount())))
                .payStatus(PayStatusEnum.CREATED.getKey())
                .build();
        orderDomainService.createOrder(order);
        return order.getId();
    }

    public static String generateOrderNumber() {
        //可以基于业务规则生成
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(formatter);
        long sequenceNumber = counter.getAndIncrement() % 1000; // 取最后三位作为序列号
        return PREFIX + timestamp + String.format("%03d", sequenceNumber);
    }

    @Override
    public Boolean payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = OrderConvertor.INSTANCE.commandPayDTOToCommandPay(commandPayDTO);
        payDomainService.payRecord(commandPay);
        return Boolean.TRUE;
    }
}
