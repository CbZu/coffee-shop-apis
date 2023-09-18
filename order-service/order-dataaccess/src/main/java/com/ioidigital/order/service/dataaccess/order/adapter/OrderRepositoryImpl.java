package com.ioidigital.order.service.dataaccess.order.adapter;

import com.ioidigital.order.service.domain.ports.output.repository.OrderRepository;
import com.ioidigital.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import com.ioidigital.order.service.dataaccess.order.repository.OrderJpaRepository;
import com.ioidigital.common.domain.valueobject.OrderId;
import com.ioidigital.order.service.domain.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository,
                               OrderDataAccessMapper orderDataAccessMapper) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderDataAccessMapper = orderDataAccessMapper;
    }

    @Override
    public Order save(Order order) {
        return orderDataAccessMapper.orderEntityToOrder(orderJpaRepository
                .save(orderDataAccessMapper.orderToOrderEntity(order)));
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderJpaRepository.findById(orderId.getValue()).map(orderDataAccessMapper::orderEntityToOrder);
    }
}
