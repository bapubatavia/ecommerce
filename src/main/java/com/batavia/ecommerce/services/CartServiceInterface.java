package com.batavia.ecommerce.services;


import com.batavia.ecommerce.model.Cart;
import com.batavia.ecommerce.model.User;

public interface CartServiceInterface {
    Boolean addToCart(Cart cart);
    Boolean updateCart(Cart cart);
    Boolean removeFromCart(Cart cart);
    Cart getCart(User user);
}
