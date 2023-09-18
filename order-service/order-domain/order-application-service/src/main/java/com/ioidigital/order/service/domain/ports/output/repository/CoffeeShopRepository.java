package com.ioidigital.order.service.domain.ports.output.repository;

import com.ioidigital.order.service.domain.entity.CoffeeShop;

import java.util.Optional;

public interface CoffeeShopRepository {

    Optional<CoffeeShop> findCoffeeShopInformation(CoffeeShop coffeeShop);
}
