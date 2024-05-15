package com.batavia.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batavia.ecommerce.model.ClientOrder;
import com.batavia.ecommerce.model.User;

public interface OrderRepository extends JpaRepository<ClientOrder, Long>{
    List<ClientOrder> findByClient(User client);
    Optional<ClientOrder> findById(ClientOrder order);
}
