package com.batavia.ecommerce.controllers;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.batavia.ecommerce.DTOs.JewelryDTO;
import com.batavia.ecommerce.enums.OrderStatus;
import com.batavia.ecommerce.model.ClientOrder;
import com.batavia.ecommerce.services.JewelryServiceInterface;
import com.batavia.ecommerce.services.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;





@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private JewelryServiceInterface jewelryService;

    @Autowired
    private OrderService orderService;
    

    @GetMapping("/new_item")
    public String showNewItemForm(Model model){
        model.addAttribute("user", new JewelryDTO());
        model.addAttribute("categoryPicked", "");
        return "new-item";       
    }
   
    @GetMapping("/product-list")
    public String getAdminAllItems(Model model) {
        List<JewelryDTO> allJewelry = jewelryService.displayAllItems();
        Iterator<JewelryDTO> iterator = allJewelry.iterator();
        while (iterator.hasNext()){
            JewelryDTO dto = iterator.next();
            if (dto.getStatus().name().equals("Discontinued")){
                iterator.remove();
            }
        }

        model.addAttribute("items", allJewelry);
        return "admin-all-items" ;
    }

    @GetMapping("/orders")
    public String getAdminAllOrders(Model model) {
        List<ClientOrder> allOrders = orderService.findAllOrders();
        model.addAttribute("orders", allOrders);
        return "admin-all-orders";
    }
    
    @PostMapping("/update-order")
    public String updateOrder(@RequestParam("orderId") String id, @RequestParam("statusPicked") String status) {
        ClientOrder order = new ClientOrder();
        order = orderService.displayOrder(id);
        order.setStatus(OrderStatus.valueOf(status));
        orderService.updateOrder(order);
        
        return "redirect:/admin/orders";
    }
    

    


}
