package com.ioidigital.order.service.domain;

import com.ioidigital.order.service.domain.dto.create.CreateOrderCommand;
import com.ioidigital.order.service.domain.mapper.OrderDataMapper;
import com.ioidigital.order.service.domain.ports.output.repository.CoffeeShopRepository;
import com.ioidigital.order.service.domain.ports.output.repository.CustomerRepository;
import com.ioidigital.order.service.domain.ports.output.repository.OrderRepository;
import com.ioidigital.order.service.domain.entity.CoffeeShop;
import com.ioidigital.order.service.domain.entity.Customer;
import com.ioidigital.order.service.domain.entity.Order;
import com.ioidigital.order.service.domain.event.OrderCreatedEvent;
import com.ioidigital.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final CoffeeShopRepository coffeeShopRepository;

    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             CustomerRepository customerRepository,
                             CoffeeShopRepository coffeeShopRepository,
                             OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.coffeeShopRepository = coffeeShopRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        CoffeeShop coffeeShop = checkCoffeeShop(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, coffeeShop);
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }

    private CoffeeShop checkCoffeeShop(CreateOrderCommand createOrderCommand) {
        CoffeeShop coffeeShop = orderDataMapper.createOrderCommandToCoffeeShop(createOrderCommand);
        Optional<CoffeeShop> optionalCoffeeShop = coffeeShopRepository.findCoffeeShopInformation(coffeeShop);
        if (optionalCoffeeShop.isEmpty()) {
            log.warn("Could not find coffee shop with coffee shop id: {}", createOrderCommand.getCoffeeshopId());
            throw new OrderDomainException("Could not find coffee shop with restaurant id: " +
                    createOrderCommand.getCoffeeshopId());
        }
        return optionalCoffeeShop.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with customer id: {}", customerId);
            throw new OrderDomainException("Could not find customer with customer id: " + customer);
        }
    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order is saved with id: {}", orderResult.getId().getValue());
        return orderResult;
    }
}
