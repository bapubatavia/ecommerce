package com.batavia.ecommerce.services;


import com.batavia.ecommerce.model.CartJewelry;

public interface CartJewelryServiceInterface {
    Boolean addToCart(CartJewelry cart);
    Boolean updateCart(CartJewelry cart);
    Boolean removeFromCart(CartJewelry cart);
}
