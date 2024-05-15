package com.batavia.ecommerce.DTOs;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.batavia.ecommerce.enums.Category;
import com.batavia.ecommerce.enums.ProductStatus;

import lombok.Data;

@Data
public class JewelryDTO {
    private Long id;
    private String name;
    private Category category;
    private int price;
    private Date dateAdded;
    private int quantityStock;
    private byte[] mainJewelryPicture;
    private List<byte[]> additionalPictures;
    private ProductStatus status;
    
    public String getBase64Image() {
        if (this.mainJewelryPicture != null) {
            return Base64.getEncoder().encodeToString(this.mainJewelryPicture);
        } else {
            return null;
        }
    }
    
    public List<String> getBase64AdditionalPictures() {
        List<String> base64AdditionalPictures = new ArrayList<>();
        if (this.additionalPictures != null) {
            for (byte[] additionalPicture : this.additionalPictures) {
                if (additionalPicture != null) {
                    base64AdditionalPictures.add(Base64.getEncoder().encodeToString(additionalPicture));
                } else {
                    base64AdditionalPictures.add(null);
                }
            }
        }
        return base64AdditionalPictures;
    }
    
    
}
