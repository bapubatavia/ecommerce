package com.batavia.ecommerce.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
        name = "jewelryInCart",
        joinColumns = @JoinColumn(name = "cartJewelry_id"),
        inverseJoinColumns = @JoinColumn(name = "cart_id")
    )
    private List<CartJewelry> cartJewelry;

    @Column(name = "sub_total")
    private int subTotal;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;



    public Cart(Long id, List<CartJewelry> cartJewelry, int subTotal) {
        this.id = id;
        this.cartJewelry = cartJewelry;
        this.subTotal = subTotal;
    }

    
}