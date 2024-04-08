package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.infra.db.dataobject.OrderMainDO;
import com.hixtrip.sample.infra.db.dataobject.OrderMainDetailDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * DO对像 -> 领域对象转换器
 * todo 自由实现
 */
@Mapper
public interface OrderDOConvertor {
    OrderDOConvertor INSTANCE = Mappers.getMapper(OrderDOConvertor.class);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "userId", target = "buyerId")
    OrderMainDO orderToOrderMainDO(Order order);

    @Mapping(source = "id", target = "orderId")
    OrderMainDetailDO orderToOrderDetailDO(Order order);

}
