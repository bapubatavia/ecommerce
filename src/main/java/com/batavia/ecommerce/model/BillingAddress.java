package com.batavia.ecommerce.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BillingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    private String address;
    @Column(name="payment_method")
    private String paymentMethod;

    @OneToMany(mappedBy = "billingAddress")
    private Set<ClientOrder> clientOrders = new HashSet<>();

    public BillingAddress(String firstName, String lastName, String address, String paymentMethod) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return firstName + " " +
                lastName + " " +
                address;
    }
    

}