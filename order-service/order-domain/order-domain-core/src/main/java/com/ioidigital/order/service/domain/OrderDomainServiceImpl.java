package com.ioidigital.order.service.domain;

import com.ioidigital.common.domain.DomainConstants;
import com.ioidigital.order.service.domain.entity.CoffeeShop;
import com.ioidigital.order.service.domain.entity.Order;
import com.ioidigital.order.service.domain.entity.Product;
import com.ioidigital.order.service.domain.event.OrderCancelledEvent;
import com.ioidigital.order.service.domain.event.OrderCreatedEvent;
import com.ioidigital.order.service.domain.event.OrderPaidEvent;
import com.ioidigital.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, CoffeeShop coffeeShop) {
        validateCoffeeShop(coffeeShop);
        setOrderProductInformation(order, coffeeShop);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: {} is initiated", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(DomainConstants.UTC)));
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id: {} is paid", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(DomainConstants.UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order payment is cancelling for order id: {}", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} is cancelled", order.getId().getValue());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(DomainConstants.UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} is cancelled", order.getId().getValue());
    }

    private void validateCoffeeShop(CoffeeShop coffeeShop) {
        if (!coffeeShop.isActive()) {
            throw new OrderDomainException("CoffeeShop with id " + coffeeShop.getId().getValue() +
                    " is currently not active!");
        }
    }

    private void setOrderProductInformation(Order order, CoffeeShop coffeeShop) {
        order.getItems().forEach(orderItem -> coffeeShop.getProducts().forEach(product -> {
            Product currentProduct = orderItem.getProduct();
            if (currentProduct.equals(product)) {
                currentProduct.updateWithConfirmedNameAndPrice(product.getName(),
                        product.getPrice());
            }
        }));
    }
}
