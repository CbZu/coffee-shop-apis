package com.ioidigital.order.service.domain.outbox.scheduler.payment;

import com.ioidigital.common.domain.valueobject.OrderStatus;
import com.ioidigital.order.service.domain.exception.OrderDomainException;
import com.ioidigital.order.service.domain.outbox.model.payment.OrderPaymentEventPayload;
import com.ioidigital.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.ioidigital.outbox.OutboxStatus;
import com.ioidigital.saga.SagaStatus;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ioidigital.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Slf4j
@Component
@NoArgsConstructor
public class PaymentOutboxHelper {

    @Transactional(readOnly = true)
    public Optional<List<OrderPaymentOutboxMessage>> getPaymentOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        // fake return
        return Optional.of(Collections.emptyList());
    }

    @Transactional(readOnly = true)
    public Optional<OrderPaymentOutboxMessage> getPaymentOutboxMessageBySagaIdAndSagaStatus(UUID sagaId,
                                                                                            SagaStatus... sagaStatus) {
        // fake return
        return Optional.of(OrderPaymentOutboxMessage.builder().build());
    }

    @Transactional
    public void save(OrderPaymentOutboxMessage orderPaymentOutboxMessage) {
       OrderPaymentOutboxMessage response = OrderPaymentOutboxMessage.builder().build();
       if (response == null) {
           log.error("Could not save OrderPaymentOutboxMessage with outbox id: {}", orderPaymentOutboxMessage.getId());
           throw new OrderDomainException("Could not save OrderPaymentOutboxMessage with outbox id: " +
                   orderPaymentOutboxMessage.getId());
       }
       log.info("OrderPaymentOutboxMessage saved with outbox id: {}", orderPaymentOutboxMessage.getId());
    }

    @Transactional
    public void savePaymentOutboxMessage(OrderPaymentEventPayload paymentEventPayload,
                                         OrderStatus orderStatus,
                                         SagaStatus sagaStatus,
                                         OutboxStatus outboxStatus,
                                         UUID sagaId) {
        save(OrderPaymentOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(paymentEventPayload.getCreatedAt())
                .type(ORDER_SAGA_NAME)
                .payload(createPayload(paymentEventPayload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .build());
    }

    @Transactional
    public void deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus outboxStatus,
                                                                      SagaStatus... sagaStatus) {
        // fake delete
    }

    private String createPayload(OrderPaymentEventPayload paymentEventPayload) {
        return "Payload as String";
    }

}
