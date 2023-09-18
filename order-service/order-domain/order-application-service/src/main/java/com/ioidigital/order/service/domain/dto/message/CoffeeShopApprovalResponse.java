package com.ioidigital.order.service.domain.dto.message;

import com.ioidigital.common.domain.valueobject.OrderApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CoffeeShopApprovalResponse {
    private String id;
    private String sagaId;
    private String orderId;
    private String coffeeshopId;
    private Instant createdAt;
    private OrderApprovalStatus orderApprovalStatus;
    private List<String> failureMessages;
}
