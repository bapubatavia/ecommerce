package com.batavia.ecommerce.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.batavia.ecommerce.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClientOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
        name = "jewelryInOrder",
        joinColumns = @JoinColumn(name = "cartJewelry_id"),
        inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<CartJewelry> cartJewelryItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;
    @Column(name = "total_price")
    private int totalPrice;
    @Column(name = "date_ordered")
    private Date dateOrdered;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id")
    private BillingAddress billingAddress;

    @Column(name = "order_status")
    private OrderStatus status;


    
    
    public ClientOrder(List<CartJewelry> cartJewelryItems, User client, int totalPrice, Date dateOrdered, BillingAddress billingAddress, OrderStatus status) {
        this.cartJewelryItems = cartJewelryItems;
        this.client = client;
        this.totalPrice = totalPrice;
        this.dateOrdered = dateOrdered;
        this.billingAddress = billingAddress;
        this.status = status;
    }

    public ClientOrder(List<CartJewelry> cartJewelryItems, int totalPrice, Date dateOrdered, OrderStatus status) {
        this.cartJewelryItems = cartJewelryItems;
        this.totalPrice = totalPrice;
        this.dateOrdered = dateOrdered;
        this.status = status;
    }

    

}
