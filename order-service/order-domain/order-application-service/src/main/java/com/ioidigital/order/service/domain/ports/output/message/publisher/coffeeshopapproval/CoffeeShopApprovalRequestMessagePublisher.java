package com.ioidigital.order.service.domain.ports.output.message.publisher.coffeeshopapproval;

import com.ioidigital.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import com.ioidigital.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface CoffeeShopApprovalRequestMessagePublisher {

    void publish(OrderApprovalOutboxMessage orderApprovalOutboxMessage,
                 BiConsumer<OrderApprovalOutboxMessage, OutboxStatus> outboxCallback);
}
