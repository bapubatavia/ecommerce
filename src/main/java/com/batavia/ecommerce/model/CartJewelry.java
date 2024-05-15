package com.batavia.ecommerce.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class CartJewelry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jewelry_id")
    private Jewelry jewelry;

    @JsonIgnore
    @ManyToMany(mappedBy = "cartJewelry")
    private List<Cart> carts;

    @JsonIgnore
    @ManyToMany(mappedBy = "cartJewelryItems")
    private List<ClientOrder> orders;

    private int quantityInOrder;

    public CartJewelry(Long id, Jewelry jewelry, int quantityInOrder) {
        this.id = id;
        this.jewelry = jewelry;
        this.quantityInOrder = quantityInOrder;
    }

    public CartJewelry(Jewelry jewelry, int quantityInOrder) {
        this.jewelry = jewelry;
        this.quantityInOrder = quantityInOrder;
    }

    @Override
    public String toString() {
        return "Item=" + jewelry.getName() +
                ", Qty=" + quantityInOrder
                ;
    }
    

}