package com.batavia.ecommerce.model;

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.batavia.ecommerce.enums.Category;
import com.batavia.ecommerce.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Jewelry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private int price;
    @Column(name = "date_added")
    private Date dateAdded;
    @Column(name = "quantity_in_stock")
    private int quantityStock;
    
    @JsonBackReference
    @OneToMany(mappedBy = "jewelry", orphanRemoval = true)
    private Set<CartJewelry> cartItems = new HashSet<>();

    @Column(name = "main_picture", length = 5000000)
    private byte[] mainJewelryPicture;
    @Column(name = "additional_picture", length = 5000000)
    private List<byte[]> additionalPictures;
    private String base64Image;

    @Column(name = "product_status")
    private ProductStatus status;




    public Jewelry(String name, Category category, int price, Date dateAdded, int quantityStock,
            byte[] mainJewelryPicture, List<byte[]> additionalPictures, ProductStatus status) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.dateAdded = dateAdded;
        this.quantityStock = quantityStock;
        this.mainJewelryPicture = mainJewelryPicture;
        this.additionalPictures = additionalPictures;
        this.status = status;
    }




    public Jewelry(String name, Category category, int price, Date dateAdded, int quantityStock, ProductStatus status) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.dateAdded = dateAdded;
        this.quantityStock = quantityStock;
        this.status = status;
    }

    public String getBase64Image() {
        if (this.mainJewelryPicture != null) {
            base64Image = Base64.getEncoder().encodeToString(this.mainJewelryPicture);
            return base64Image;
        } else {
            return null;
        }
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
    

}
