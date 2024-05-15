package com.batavia.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batavia.ecommerce.model.Cart;
import com.batavia.ecommerce.model.User;


public interface CartRepository extends JpaRepository<Cart, Long>{

    Optional<Cart> findByUser(User user);
}
