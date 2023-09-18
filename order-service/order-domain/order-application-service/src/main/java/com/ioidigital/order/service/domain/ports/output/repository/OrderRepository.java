package com.ioidigital.order.service.domain.ports.output.repository;

import com.ioidigital.common.domain.valueobject.OrderId;
import com.ioidigital.order.service.domain.entity.Order;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(OrderId orderId);

}
