package com.batavia.ecommerce.model;

import java.util.Collection;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @NaturalId
    private String email;
    private String password;
    @Column(name = "is_enabled")
    private boolean isEnabled = true;

    @Enumerated(EnumType.STRING)
    private Collection<Role> roles;
    @OneToMany(mappedBy = "client")
    private Collection<ClientOrder> orders;
    @OneToOne(mappedBy = "user")
    private Cart cart;

    @Column(name = "phone_no")
    private String phoneNo;
    private String address;

    
    public User(String email) {
        this.email = email;
    }

    

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }



    public User(String firstName, String lastName, String email, String password, Collection<Role> roles,
            String phoneNo, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.phoneNo = phoneNo;
        this.address = address;
    }



    
}
