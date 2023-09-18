package com.ioidigital.order.service.domain;

import com.ioidigital.order.service.domain.entity.CoffeeShop;
import com.ioidigital.order.service.domain.entity.Order;
import com.ioidigital.order.service.domain.event.OrderCancelledEvent;
import com.ioidigital.order.service.domain.event.OrderPaidEvent;
import com.ioidigital.order.service.domain.event.OrderCreatedEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, CoffeeShop coffeeShop);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
