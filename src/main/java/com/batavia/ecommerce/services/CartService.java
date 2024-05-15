package com.batavia.ecommerce.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batavia.ecommerce.model.Cart;
import com.batavia.ecommerce.model.User;
import com.batavia.ecommerce.repositories.CartRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CartService implements CartServiceInterface {

    @Autowired
    private final CartRepository cartRepo;

    @Override
    public Boolean addToCart(Cart cart) {
        try {
            cartRepo.save(cart);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateCart(Cart cart) {
        try {
            cartRepo.save(cart);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean removeFromCart(Cart cart) {
        try {
            cartRepo.delete(cart);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Cart getCart(User user) {
        try {
            Optional<Cart> cartOptional = cartRepo.findByUser(user);
            if (cartOptional.isPresent()) {
                return cartOptional.get();
            } else {
                return null; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
