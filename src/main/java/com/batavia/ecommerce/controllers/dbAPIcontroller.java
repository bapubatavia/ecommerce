package com.batavia.ecommerce.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.batavia.ecommerce.DTOs.JewelryDTO;
import com.batavia.ecommerce.enums.Category;
import com.batavia.ecommerce.enums.ProductStatus;
import com.batavia.ecommerce.services.JewelryServiceInterface;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class dbAPIcontroller {

    @Autowired
    private JewelryServiceInterface jewelryService;
    
    @GetMapping
    public String getHomePage() {
        return "home";
    }


    @PostMapping("/add-item")
    public ResponseEntity<String> addItem(@ModelAttribute("jewelry") JewelryDTO jewelDTO, @ModelAttribute("categoryPicked") String pickedCategory, @RequestParam("mainImage") MultipartFile mainJewelryPictureFile,@RequestParam("additionalImages") List<MultipartFile> additionalImages,RedirectAttributes redirectAttributes){
        List<byte[]> images = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now().withSecond(0).withNano(0);
        Date currentDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
        jewelDTO.setDateAdded(currentDate);
        Category category = Category.valueOf(pickedCategory);
        jewelDTO.setCategory(category);
        jewelDTO.setStatus(ProductStatus.Available);

        try {
            byte[] mainJewelryPicture = mainJewelryPictureFile.getBytes();
            jewelDTO.setMainJewelryPicture(mainJewelryPicture);

            for(MultipartFile img: additionalImages){
                byte[] image = img.getBytes();
                images.add(image);
            }
            jewelDTO.setAdditionalPictures(images);
            jewelryService.addItem(jewelDTO);
            redirectAttributes.addFlashAttribute("Message", "Item added successfully!");
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/admin/new_item?success").build();         
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("errorMessage", "Adding item failed. Please try again.");
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/admin/new_item?fail").build();      
        }

    }


    
    @PostMapping("/update-item")
    public ResponseEntity<String> updateItem(@RequestParam("modalName") String name, @RequestParam("modalPrice") String price, @RequestParam("modalCategoryPicked") String categoryPicked, 
    @RequestParam("modalQuantityStock") String quantityStock, @RequestParam("id") String id,RedirectAttributes redirectAttributes){
        JewelryDTO jewelryDTO = jewelryService.displayItemById(id);
        System.out.println(jewelryDTO.getId());
        System.out.println(jewelryDTO.getName());
        Category category = Category.valueOf(categoryPicked);

        jewelryDTO.setName(name);
        jewelryDTO.setCategory(category);
        jewelryDTO.setPrice(Integer.parseInt(price));
        jewelryDTO.setQuantityStock(Integer.parseInt(quantityStock));
        if(jewelryDTO.getQuantityStock() > 0){
            jewelryDTO.setStatus(ProductStatus.Available);
        } else {
            jewelryDTO.setStatus(ProductStatus.OutOfStock);
        }
        try {

            redirectAttributes.addFlashAttribute("Message", "Item updated successfully!");
            jewelryService.updateItem(jewelryDTO);
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/admin/product-list?success").build();         
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("errorMessage", "Updating item failed. Please try again.");
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/admin/product-list?fail").build();      
        }

    }

    @PostMapping("/delete-item")
    public ResponseEntity<String> deleteItem(@RequestParam("id") String id,RedirectAttributes redirectAttributes){
        JewelryDTO jewelryDTO = jewelryService.displayItemById(id);
        jewelryDTO.setStatus(ProductStatus.Discontinued);
        try {
            redirectAttributes.addFlashAttribute("Message", "Item deleted successfully!");
            jewelryService.updateItem(jewelryDTO);
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/admin/product-list?success").build();         
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("errorMessage", "Deletion of item failed. Please try again.");
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/admin/product-list?fail").build();      
        }

    }


}
