package com.ioidigital.domain;

import com.ioidigital.order.service.domain.OrderDomainService;
import com.ioidigital.order.service.domain.OrderDomainServiceImpl;
import com.ioidigital.order.service.domain.ports.input.service.OrderApplicationService;
import com.ioidigital.order.service.domain.ports.output.message.publisher.coffeeshopapproval.CoffeeShopApprovalRequestMessagePublisher;
import com.ioidigital.order.service.domain.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
import com.ioidigital.order.service.domain.ports.output.repository.*;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.ioidigital")
public class OrderTestConfiguration {

    @Bean
    public PaymentRequestMessagePublisher paymentRequestMessagePublisher() {
        return Mockito.mock(PaymentRequestMessagePublisher.class);
    }

    @Bean
    public CoffeeShopApprovalRequestMessagePublisher restaurantApprovalRequestMessagePublisher() {
        return Mockito.mock(CoffeeShopApprovalRequestMessagePublisher.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public CoffeeShopRepository restaurantRepository() {
        return Mockito.mock(CoffeeShopRepository.class);
    }

    @Bean
    public PaymentOutboxRepository paymentOutboxRepository() {
        return Mockito.mock(PaymentOutboxRepository.class);
    }

    @Bean
    public ApprovalOutboxRepository approvalOutboxRepository() {
        return Mockito.mock(ApprovalOutboxRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }

}
