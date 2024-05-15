package com.batavia.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batavia.ecommerce.enums.Category;
import com.batavia.ecommerce.model.Jewelry;

public interface JewelryRepository extends JpaRepository<Jewelry, Long>{
    List<Jewelry> findByNameContaining(String searchedItem);

    List<Jewelry> findByCategory(Category category);

    
}
