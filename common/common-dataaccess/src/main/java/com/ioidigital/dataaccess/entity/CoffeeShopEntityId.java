package com.ioidigital.dataaccess.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeShopEntityId implements Serializable {

    private UUID coffeeshopId;
    private UUID productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoffeeShopEntityId that = (CoffeeShopEntityId) o;
        return coffeeshopId.equals(that.coffeeshopId) && productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coffeeshopId, productId);
    }
}
