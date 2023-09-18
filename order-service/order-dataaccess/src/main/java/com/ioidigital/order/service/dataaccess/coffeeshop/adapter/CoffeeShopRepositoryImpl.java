package com.ioidigital.order.service.dataaccess.coffeeshop.adapter;

import com.ioidigital.order.service.domain.ports.output.repository.CoffeeShopRepository;
import com.ioidigital.dataaccess.entity.CoffeeShopEntity;
import com.ioidigital.dataaccess.repository.CoffeeShopJpaRepository;
import com.ioidigital.order.service.dataaccess.coffeeshop.mapper.CoffeeShopDataAccessMapper;
import com.ioidigital.order.service.domain.entity.CoffeeShop;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CoffeeShopRepositoryImpl implements CoffeeShopRepository {

    private final CoffeeShopJpaRepository coffeeShopJpaRepository;
    private final CoffeeShopDataAccessMapper coffeeShopDataAccessMapper;

    public CoffeeShopRepositoryImpl(CoffeeShopJpaRepository coffeeShopJpaRepository,
                                    CoffeeShopDataAccessMapper coffeeShopDataAccessMapper) {
        this.coffeeShopJpaRepository = coffeeShopJpaRepository;
        this.coffeeShopDataAccessMapper = coffeeShopDataAccessMapper;
    }

    @Override
    public Optional<CoffeeShop> findCoffeeShopInformation(CoffeeShop coffeeShop) {
        List<UUID> coffeeShopProducts =
                coffeeShopDataAccessMapper.coffeeShopToCoffeeShopProducts(coffeeShop);
        Optional<List<CoffeeShopEntity>> coffeeShopEntities = coffeeShopJpaRepository
                .findByCoffeeshopIdAndProductIdIn(coffeeShop.getId().getValue(),
                        coffeeShopProducts);
        return coffeeShopEntities.map(coffeeShopDataAccessMapper::coffeeShopEntityToCoffeeShop);
    }
}
