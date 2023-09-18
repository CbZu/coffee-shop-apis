package com.ioidigital.order.service.domain.entity;

import com.ioidigital.common.domain.valueobject.CoffeeshopId;
import com.ioidigital.common.domain.entity.BaseEntity;

import java.util.List;

public class CoffeeShop extends BaseEntity<CoffeeshopId> {

    private final List<Product> products;
    private boolean active;

    private CoffeeShop(Builder builder) {
        super.setId(builder.coffeeshopId);
        products = builder.products;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private CoffeeshopId coffeeshopId;
        private List<Product> products;
        private boolean active;

        private Builder() {
        }

        public Builder coffeeshopId(CoffeeshopId val) {
            coffeeshopId = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public CoffeeShop build() {
            return new CoffeeShop(this);
        }
    }

}
