package com.ioidigital.order.service.dataaccess.coffeeshop.mapper;

import com.ioidigital.dataaccess.entity.CoffeeShopEntity;
import com.ioidigital.dataaccess.exception.CoffeeShopDataAccessException;
import com.ioidigital.common.domain.valueobject.CoffeeshopId;
import com.ioidigital.common.domain.valueobject.Money;
import com.ioidigital.common.domain.valueobject.ProductId;
import com.ioidigital.order.service.domain.entity.CoffeeShop;
import com.ioidigital.order.service.domain.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CoffeeShopDataAccessMapper {

    public List<UUID> coffeeShopToCoffeeShopProducts(CoffeeShop coffeeShop) {
        return coffeeShop.getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public CoffeeShop coffeeShopEntityToCoffeeShop(List<CoffeeShopEntity> coffeeShopEntities) {
        CoffeeShopEntity coffeeShopEntity =
                coffeeShopEntities.stream().findFirst().orElseThrow(() ->
                        new CoffeeShopDataAccessException("Coffee shop could not be found!"));

        List<Product> coffeeShopProducts = coffeeShopEntities.stream().map(entity ->
                new Product(new ProductId(entity.getProductId()), entity.getProductName(),
                        new Money(entity.getProductPrice()))).toList();

        return CoffeeShop.builder()
                .coffeeshopId(new CoffeeshopId(coffeeShopEntity.getCoffeeshopId()))
                .products(coffeeShopProducts)
                .active(coffeeShopEntity.getCoffeeshopActive())
                .build();
    }
}
