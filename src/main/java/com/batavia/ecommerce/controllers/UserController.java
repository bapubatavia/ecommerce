package com.batavia.ecommerce.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.batavia.ecommerce.DTOs.JewelryDTO;
import com.batavia.ecommerce.enums.OrderStatus;
import com.batavia.ecommerce.enums.ProductStatus;
import com.batavia.ecommerce.model.BillingAddress;
import com.batavia.ecommerce.model.Cart;
import com.batavia.ecommerce.model.CartJewelry;
import com.batavia.ecommerce.model.ClientOrder;
import com.batavia.ecommerce.model.Jewelry;
import com.batavia.ecommerce.model.User;
import com.batavia.ecommerce.services.BillingAddressService;
import com.batavia.ecommerce.services.CartJewelryService;
import com.batavia.ecommerce.services.CartService;
import com.batavia.ecommerce.services.JewelryService;
import com.batavia.ecommerce.services.OrderService;
import com.batavia.ecommerce.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private JewelryService jewelryService;
    
    @Autowired
    private CartJewelryService cartJewelryService;

    @Autowired
    private BillingAddressService billingAddressService;

    @Autowired
    private OrderService orderService;

    

    @PostMapping(value = "/add-to-cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postToCart(@RequestParam("id") String id) {
        Cart cart = new Cart();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JewelryDTO jewelryDTO = jewelryService.displayItemById(id);
        Jewelry jewelry = jewelryService.convertToEntity(jewelryDTO);

        if (auth != null && auth.isAuthenticated()){
            Object principal = auth.getPrincipal();
            UserDetails currentUser = (UserDetails) principal;
            String email = currentUser.getUsername();
            User userFound = userService.findByEmail(email);
            cart = cartService.getCart(userFound);
            CartJewelry cartJewelry = new CartJewelry();
            if(cart != null){
                cartJewelry.setJewelry(jewelry);
                List<CartJewelry> jewelrySet = cart.getCartJewelry();
                CartJewelry jewelryFoundInCartSet = null;
                for(CartJewelry j : jewelrySet){
                    if(j.getJewelry().getId().equals(cartJewelry.getJewelry().getId())){
                        jewelryFoundInCartSet = j;
                        System.out.println("Found an item that matches!");
                        break;
                    }
                }

                if(jewelryFoundInCartSet != null){
                    int quantityInOrder = jewelryFoundInCartSet.getQuantityInOrder();
                    System.out.println(jewelryFoundInCartSet.getQuantityInOrder());
                    quantityInOrder++;
                    
                    System.out.println("Found in set, checking if we can add more to order");
                    if(quantityInOrder <= jewelryFoundInCartSet.getJewelry().getQuantityStock()){
                        System.out.println("All good, adding more to quantity in order");
                        jewelryFoundInCartSet.setQuantityInOrder(quantityInOrder);
                        System.out.println(jewelryFoundInCartSet.getQuantityInOrder());
                        cartJewelryService.updateCart(jewelryFoundInCartSet);
                    } else{
                        System.out.println("Can't add more!");

                        return ResponseEntity.ok("Not enough items in stock, please select something else!");
                    }

                } else{
                    System.out.println("Wasn't in set, adding it to the order");
                    cartJewelry.setQuantityInOrder(1);
                    cartJewelryService.addToCart(cartJewelry);
                    jewelrySet.add(cartJewelry);
                }
                int subTotal = 0;
                for(CartJewelry cj: jewelrySet) {
                    subTotal += cj.getJewelry().getPrice() * cj.getQuantityInOrder();
                }
                cart.setSubTotal(subTotal);
                System.out.println(cart.getSubTotal());
                cartService.updateCart(cart);
            }else{
                cart = new Cart();
                cart.setUser(userFound);
                cartService.addToCart(cart);
                cartJewelry.setJewelry(jewelry);
                cartJewelry.setQuantityInOrder(1);
                System.out.println(cartJewelry.getQuantityInOrder());
                cartJewelryService.addToCart(cartJewelry);
                List<CartJewelry> jewelrySet = new ArrayList<CartJewelry>();
                jewelrySet.add(cartJewelry);
                cart.setCartJewelry(jewelrySet);
                System.out.println("Jewelries added to cart!");

                cart.setSubTotal(jewelry.getPrice());
                System.out.println(cart.getSubTotal());
                cartService.addToCart(cart);
            }
            
        }

        return ResponseEntity.ok().build();
    }


    @PostMapping("/process-order")
    public ResponseEntity<String> processOrder(@ModelAttribute("billingAddress") BillingAddress billingAddress, @RequestParam("addressPartOne") String addOne, @RequestParam("addressPartTwo") String addTwo
    , @RequestParam("addressPartThree") String addThree, @RequestParam("addressPartFour") String addFour, RedirectAttributes redirectAttributes){
        billingAddress.setAddress(addOne + ", " + addThree + ", " + addTwo + ", " + addFour);
        Cart cart = new Cart();
        ClientOrder order = new ClientOrder();
        List<CartJewelry> jewelryInCart = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime currentDateTime = LocalDateTime.now().withSecond(0).withNano(0);
        Date currentDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());

        if (auth != null && auth.isAuthenticated()){
            Object principal = auth.getPrincipal();
            UserDetails currentUser = (UserDetails) principal;
            String email = currentUser.getUsername();
            User userFound = userService.findByEmail(email);
            cart = cartService.getCart(userFound);
            int total = cart.getSubTotal();
            for(CartJewelry jewelry: cart.getCartJewelry()){
                jewelryInCart.add(jewelry);
            }
            cartService.removeFromCart(cart);
            System.out.println("Cart cleared!");
            try {
                billingAddressService.saveBillingAddress(billingAddress);
                System.out.println("Address saved in db!");
                order.setBillingAddress(billingAddress);
                order.setClient(userFound);
                order.setCartJewelryItems(jewelryInCart);
                System.out.println(jewelryInCart);
                order.setTotalPrice(total);
                order.setDateOrdered(currentDate);
                order.setStatus(OrderStatus.Pending);
                orderService.makeOrder(order);
                System.out.println("Order made!");
                for(CartJewelry cj: jewelryInCart){
                    Jewelry jewelry = cj.getJewelry();
                    int qtyStock = jewelry.getQuantityStock();
                    int qtyRemaining = qtyStock - cj.getQuantityInOrder();
                    if(qtyRemaining <= 0){
                        jewelry.setStatus(ProductStatus.OutOfStock);
                        jewelry.setQuantityStock(0);
                    }else{                        
                        jewelry.setQuantityStock(qtyRemaining);
                    }
                    
                    jewelryService.updateItem(jewelryService.convertToDTO(jewelry));
                };
                redirectAttributes.addFlashAttribute("Message", "Order successful! You can now continue shopping.");
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/browse?success").build();         
            } catch (Exception e) {
                e.printStackTrace();     
            }
        }
        redirectAttributes.addAttribute("errorMessage", "Order processing failed. Please try again.");
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/browse?fail").build(); 


    }

}
