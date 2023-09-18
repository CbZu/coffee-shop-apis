package com.ioidigital.order.service.domain.ports.input.message.listener.coffeeshopapprovale;

import com.ioidigital.order.service.domain.dto.message.CoffeeShopApprovalResponse;

public interface CoffeeShopApprovalResponseMessageListener {

    void orderApproved(CoffeeShopApprovalResponse coffeeShopApprovalResponse);

    void orderRejected(CoffeeShopApprovalResponse coffeeShopApprovalResponse);
}
