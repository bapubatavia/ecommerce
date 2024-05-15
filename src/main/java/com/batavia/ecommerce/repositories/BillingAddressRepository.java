package com.batavia.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batavia.ecommerce.model.BillingAddress;

public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {
    
}
