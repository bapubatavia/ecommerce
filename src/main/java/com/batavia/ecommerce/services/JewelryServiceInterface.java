package com.batavia.ecommerce.services;

import java.util.List;

import com.batavia.ecommerce.DTOs.JewelryDTO;
import com.batavia.ecommerce.enums.Category;
import com.batavia.ecommerce.model.Jewelry;

public interface JewelryServiceInterface {
    Boolean addItem(JewelryDTO jewel);
    Boolean updateItem(JewelryDTO jewel);
    List<JewelryDTO> displaySearchedItems(String searchedItem);
    List<JewelryDTO> displayCategoryItems(Category category);
    List<JewelryDTO> displayAllItems();
    JewelryDTO displayItemById(String id);
    Jewelry convertToEntity(JewelryDTO jewelryDTO);
    JewelryDTO convertToDTO(Jewelry jewelry);
    List<JewelryDTO> convertToDTOList(List<Jewelry> jewelryList);
}
