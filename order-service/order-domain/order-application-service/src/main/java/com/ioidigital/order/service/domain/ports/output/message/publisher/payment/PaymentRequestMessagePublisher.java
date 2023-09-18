package com.ioidigital.order.service.domain.ports.output.message.publisher.payment;

import com.ioidigital.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.ioidigital.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PaymentRequestMessagePublisher {

    void publish(OrderPaymentOutboxMessage orderPaymentOutboxMessage,
                 BiConsumer<OrderPaymentOutboxMessage, OutboxStatus> outboxCallback);
}
