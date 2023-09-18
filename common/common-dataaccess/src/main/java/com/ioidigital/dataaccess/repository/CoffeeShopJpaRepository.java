package com.ioidigital.dataaccess.repository;

import com.ioidigital.dataaccess.entity.CoffeeShopEntity;
import com.ioidigital.dataaccess.entity.CoffeeShopEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CoffeeShopJpaRepository extends JpaRepository<CoffeeShopEntity, CoffeeShopEntityId> {

    Optional<List<CoffeeShopEntity>> findByCoffeeshopIdAndProductIdIn(UUID coffeeshopId, List<UUID> productIds);
}
