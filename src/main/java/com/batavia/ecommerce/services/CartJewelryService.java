package com.batavia.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batavia.ecommerce.model.CartJewelry;
import com.batavia.ecommerce.repositories.CartJewelryRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CartJewelryService implements CartJewelryServiceInterface {

    @Autowired
    private final CartJewelryRepository cartRepo;

    @Override
    public Boolean addToCart(CartJewelry cartJewelry) {
        try {
            cartRepo.save(cartJewelry);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateCart(CartJewelry cartJewelry) {
        try {
            cartRepo.save(cartJewelry);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean removeFromCart(CartJewelry cartJewelry) {
        try {
            cartRepo.save(cartJewelry);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
