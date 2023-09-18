package com.ioidigital.dataaccess.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CoffeeShopEntityId.class)
@Table(name = "order_coffeeshop_m_view", schema = "coffeeshop")
@Entity
public class CoffeeShopEntity {

    @Id
    private UUID coffeeshopId;
    @Id
    private UUID productId;
    private String coffeeshopName;
    private Boolean coffeeshopActive;
    private String productName;
    private BigDecimal productPrice;
    private Boolean productAvailable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoffeeShopEntity that = (CoffeeShopEntity) o;
        return coffeeshopId.equals(that.coffeeshopId) && productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coffeeshopId, productId);
    }
}
