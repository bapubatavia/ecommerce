package com.batavia.ecommerce.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.batavia.ecommerce.model.Cart;
import com.batavia.ecommerce.model.CartJewelry;
import com.batavia.ecommerce.model.User;
import com.batavia.ecommerce.services.CartService;
import com.batavia.ecommerce.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class CartController {
    
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/cart/count")
    public ResponseEntity<Map<String, Object>> getCartCount(){
        Map<String, Object> responseData = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();
            UserDetails currentUser = (UserDetails) principal;
            String email = currentUser.getUsername();
            User userFound = userService.findByEmail(email);
            Cart cart = cartService.getCart(userFound);
            if(cart != null){
                System.out.println("ok we get the count");
                responseData.put("cartItemCount", cart.getCartJewelry().size());
                List<CartJewelry> jewelryList = new ArrayList<>(cart.getCartJewelry());

                responseData.put("jewelryList", jewelryList);
                responseData.put("subTotal", cart.getSubTotal());
                return ResponseEntity.ok(responseData);
            }
        }
        responseData.put("cartItemCount", 0);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/cart/check-out")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @PostMapping(value = "/cart/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCart(@RequestParam("quantity") int qty, @RequestParam("cj") String cjId, RedirectAttributes redirectAttributes) {
        Cart cart = new Cart();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()){
            Object principal = auth.getPrincipal();
            UserDetails currentUser = (UserDetails) principal;
            String email = currentUser.getUsername();
            User userFound = userService.findByEmail(email);
            cart = cartService.getCart(userFound);
            List<CartJewelry> cjInCart = cart.getCartJewelry();
            int updatedSubTotal = 0;
            for(CartJewelry cartJewl: cjInCart){
                if(cartJewl.getId().toString().equals(cjId)){
                    cartJewl.setQuantityInOrder(qty);
                }
            }
            for(CartJewelry cartJewl: cjInCart){
                updatedSubTotal += cartJewl.getJewelry().getPrice() * cartJewl.getQuantityInOrder();
            }


            cart.setSubTotal(updatedSubTotal);
            cart.setCartJewelry(cjInCart);
            cartService.updateCart(cart);
            
        }

        redirectAttributes.addAttribute("successMessage", "Update quantity successfully!");
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/my-cart?success").build();  
    }

    @PostMapping(value = "/cart/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCart(@RequestParam(name="cj", required= false) String cjId, RedirectAttributes redirectAttributes) {
        Cart cart = new Cart();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()){
            Object principal = auth.getPrincipal();
            UserDetails currentUser = (UserDetails) principal;
            String email = currentUser.getUsername();
            User userFound = userService.findByEmail(email);
            cart = cartService.getCart(userFound);
            List<CartJewelry> cjInCart = cart.getCartJewelry();
            List<CartJewelry> updatedCart = new ArrayList<CartJewelry>();
            int updatedSubTotal = 0;

            if(cjId != null){
                for(CartJewelry cartJewl: cjInCart){
                    if(!cartJewl.getId().toString().equals(cjId)){
                        updatedCart.add(cartJewl);
                    }
                }

                for(CartJewelry cartJewl: updatedCart){
                    updatedSubTotal += cartJewl.getJewelry().getPrice() * cartJewl.getQuantityInOrder();
                }


                cart.setSubTotal(updatedSubTotal);

                cart.setCartJewelry(updatedCart);
                cartService.updateCart(cart);
                
            }else{
                System.out.println("About to delete it all from cart!");
                cart.setSubTotal(0);

                cart.setCartJewelry(updatedCart);
                cartService.updateCart(cart);
                redirectAttributes.addAttribute("clearedCartMessage", "Cart Cleared. Please add things to it!");
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/browse?success").build();   
            }


            
        }

        redirectAttributes.addAttribute("errorMessage", "Cart modification failed. Please try again.");
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/my-cart?fail").build();  
    }


    
}
