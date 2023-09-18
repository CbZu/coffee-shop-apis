package com.ioidigital.order.service.domain.ports.input.message.listener.customer;

import com.ioidigital.order.service.domain.dto.message.CustomerModel;

public interface CustomerMessageListener {

    void customerCreated(CustomerModel customerModel);
}
