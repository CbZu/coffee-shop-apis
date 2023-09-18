package com.ioidigital.order.service.messaging.publisher.kafka;

import com.ioidigital.order.service.domain.config.OrderServiceConfigData;
import com.ioidigital.order.service.domain.outbox.model.approval.OrderApprovalEventPayload;
import com.ioidigital.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import com.ioidigital.order.service.domain.ports.output.message.publisher.coffeeshopapproval.CoffeeShopApprovalRequestMessagePublisher;
import com.ioidigital.kafka.producer.KafkaMessageHelper;
import com.ioidigital.kafka.producer.service.KafkaProducer;
import com.ioidigital.kafka.order.avro.model.CoffeeShopApprovalRequestAvroModel;
import com.ioidigital.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.ioidigital.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class OrderApprovalEventKafkaPublisher implements CoffeeShopApprovalRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final KafkaProducer<String, CoffeeShopApprovalRequestAvroModel> kafkaProducer;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public OrderApprovalEventKafkaPublisher(OrderMessagingDataMapper orderMessagingDataMapper,
                                            KafkaProducer<String, CoffeeShopApprovalRequestAvroModel> kafkaProducer,
                                            OrderServiceConfigData orderServiceConfigData,
                                            KafkaMessageHelper kafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }


    @Override
    public void publish(OrderApprovalOutboxMessage orderApprovalOutboxMessage,
                        BiConsumer<OrderApprovalOutboxMessage, OutboxStatus> outboxCallback) {
        OrderApprovalEventPayload orderApprovalEventPayload =
                kafkaMessageHelper.getOrderEventPayload(orderApprovalOutboxMessage.getPayload(),
                        OrderApprovalEventPayload.class);

        String sagaId = orderApprovalOutboxMessage.getSagaId().toString();

        log.info("Received OrderApprovalOutboxMessage for order id: {} and saga id: {}",
                orderApprovalEventPayload.getOrderId(),
                sagaId);

        try {
            CoffeeShopApprovalRequestAvroModel coffeeShopApprovalRequestAvroModel =
                    orderMessagingDataMapper
                            .orderApprovalEventToRestaurantApprovalRequestAvroModel(sagaId,
                                    orderApprovalEventPayload);

            kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                    sagaId,
                    coffeeShopApprovalRequestAvroModel,
                    kafkaMessageHelper.getKafkaCallback(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                            coffeeShopApprovalRequestAvroModel,
                            orderApprovalOutboxMessage,
                            outboxCallback,
                            orderApprovalEventPayload.getOrderId(),
                            "RestaurantApprovalRequestAvroModel"));

            log.info("OrderApprovalEventPayload sent to kafka for order id: {} and saga id: {}",
                    coffeeShopApprovalRequestAvroModel.getOrderId(), sagaId);
        } catch (Exception e) {
            log.error("Error while sending OrderApprovalEventPayload to kafka for order id: {} and saga id: {}," +
                    " error: {}", orderApprovalEventPayload.getOrderId(), sagaId, e.getMessage());
        }


    }
}
