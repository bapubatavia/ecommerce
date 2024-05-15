package com.batavia.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batavia.ecommerce.model.CartJewelry;

public interface CartJewelryRepository extends JpaRepository<CartJewelry, Long> {
    
}
