package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderMainDO;
import com.hixtrip.sample.infra.db.dataobject.OrderMainDetailDO;
import com.hixtrip.sample.infra.db.mapper.OrderDetailMapper;
import com.hixtrip.sample.infra.db.mapper.OrderMainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMainMapper orderMainMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Long saveOrder(Order order) {
        OrderMainDO orderMainDO = OrderDOConvertor.INSTANCE.orderToOrderMainDO(order);
        orderMainMapper.insert(orderMainDO);
        OrderMainDetailDO orderMainDetailDO = OrderDOConvertor.INSTANCE.orderToOrderDetailDO(order);
        orderDetailMapper.insert(orderMainDetailDO);
        return orderMainDO.getId();
    }
}
