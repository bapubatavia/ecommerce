package com.batavia.ecommerce.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.batavia.ecommerce.DTOs.JewelryDTO;
import com.batavia.ecommerce.DTOs.RegistrationRequest;
import com.batavia.ecommerce.enums.Category;
import com.batavia.ecommerce.model.Cart;
import com.batavia.ecommerce.model.ClientOrder;
import com.batavia.ecommerce.model.User;
import com.batavia.ecommerce.services.CartService;
import com.batavia.ecommerce.services.JewelryServiceInterface;
import com.batavia.ecommerce.services.OrderService;
import com.batavia.ecommerce.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PagesController {

    @Autowired
    private JewelryServiceInterface jewelryService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new RegistrationRequest());
        return "registration";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("user", new RegistrationRequest());
        return "login";
    }

    @GetMapping("/browse")
    public String showBrowsingPage(Model model, @RequestParam(name = "search", required = false) String searchedString, @RequestParam(name = "category", required = false) String categoryPicked){
        
        if(searchedString != null){
            System.out.println(searchedString);
            List<JewelryDTO> searchedJewelry = jewelryService.displaySearchedItems(searchedString);
            for(JewelryDTO dto : searchedJewelry){
                if(dto.getStatus().name().equals("Discontinued") || dto.getStatus().name().equals("OutOfStock")){
                    searchedJewelry.remove(dto);
                }
            }
            model.addAttribute("items", searchedJewelry);
            return "browse";
        } else if (categoryPicked != null){
            Category category = Category.valueOf(categoryPicked);
            System.out.println(category);
            List<JewelryDTO> categoryJewelry = jewelryService.displayCategoryItems(category);
            for(JewelryDTO dto : categoryJewelry){
                if(dto.getStatus().name().equals("Discontinued") || dto.getStatus().name().equals("OutOfStock")){
                    categoryJewelry.remove(dto);
                }
            }

            model.addAttribute("items", categoryJewelry);
            return "browse";
        }else{
            List<JewelryDTO> allJewelry = jewelryService.displayAllItems();
            for(JewelryDTO dto : allJewelry){
                if(dto.getStatus().name().equals("Discontinued") || dto.getStatus().name().equals("OutOfStock")){
                    allJewelry.remove(dto);
                }
            }
            model.addAttribute("items", allJewelry);
            return "browse";
        }


        

    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
    

    @GetMapping("/check-out")
    public String showCheckOutPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();
            UserDetails currentUser = (UserDetails) principal;
            String email = currentUser.getUsername();
            User userFound = userService.findByEmail(email);
            Cart cart = cartService.getCart(userFound);
            if(cart != null){
                System.out.println("ok feeding cart to checkout Page");
                model.addAttribute("cart", cart);
                model.addAttribute("cartCount", cart.getCartJewelry().size());
                return "check-out";
            }
        }

        return "check-out";
    }
    
    @GetMapping("/item-details")
    public String getItemDetails(@RequestParam("id") String id, Model model) {
        System.out.println(id);
        JewelryDTO jewelryDTO = jewelryService.displayItemById(id);
        model.addAttribute("item", jewelryDTO);
        return "item-details";
    }

    @GetMapping("/my-cart")
    public String getCartDetails(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Cart cart = new Cart();
        if (auth != null && auth.isAuthenticated()){
            Object principal = auth.getPrincipal();
            UserDetails currentUser = (UserDetails) principal;
            String email = currentUser.getUsername();
            User userFound = userService.findByEmail(email);
            cart = cartService.getCart(userFound);            
        }
        model.addAttribute("cart", cart);
        return "user-cart";
    }


    @GetMapping("/my-orders")
    public String getUserOrders(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<ClientOrder> myOrders = new ArrayList<ClientOrder>();
        if (auth != null && auth.isAuthenticated()){
            Object principal = auth.getPrincipal();
            UserDetails currentUser = (UserDetails) principal;
            String email = currentUser.getUsername();
            User userFound = userService.findByEmail(email);
            myOrders = orderService.findClientOrders(userFound);
            model.addAttribute("orders", myOrders);
            return "user-orders";
        }
        return "user-orders";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "access-denied";
    }
    
}
