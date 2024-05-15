package com.batavia.ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batavia.ecommerce.DTOs.JewelryDTO;
import com.batavia.ecommerce.enums.Category;
import com.batavia.ecommerce.model.Jewelry;
import com.batavia.ecommerce.repositories.JewelryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JewelryService implements JewelryServiceInterface{

    
    @Autowired
    private final JewelryRepository jewelRepo;

    @Override
    public Boolean addItem(JewelryDTO jewel) {
        try {
            Jewelry jewelry = convertToEntity(jewel);
            jewelRepo.save(jewelry);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateItem(JewelryDTO jewel) {
        try {
            Jewelry jewelry = convertToEntity(jewel);
            jewelRepo.save(jewelry);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<JewelryDTO> displayAllItems() {
        try {
            List<Jewelry> jewels =  jewelRepo.findAll();

            return convertToDTOList(jewels);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<JewelryDTO> displaySearchedItems(String searchedItem) {
        try {
            List<Jewelry> foundJewels =  jewelRepo.findByNameContaining(searchedItem);
            return convertToDTOList(foundJewels);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JewelryDTO displayItemById(String id) {
        Long idLong = Long.parseLong(id);
        Jewelry jewelryFound = jewelRepo.findById(idLong).get();
        return convertToDTO(jewelryFound);
    }

    @Override
    public List<JewelryDTO> displayCategoryItems(Category category) {
        List<Jewelry> categoryJewels = jewelRepo.findByCategory(category);
        return convertToDTOList(categoryJewels);
    }

    @Override
    public Jewelry convertToEntity(JewelryDTO jewelryDTO) {
        Jewelry jewelry = new Jewelry();
        jewelry.setId(jewelryDTO.getId());
        jewelry.setName(jewelryDTO.getName());
        jewelry.setCategory(jewelryDTO.getCategory());
        jewelry.setPrice(jewelryDTO.getPrice());
        jewelry.setDateAdded(jewelryDTO.getDateAdded());
        jewelry.setQuantityStock(jewelryDTO.getQuantityStock());
        jewelry.setMainJewelryPicture(jewelryDTO.getMainJewelryPicture());
        jewelry.setAdditionalPictures(jewelryDTO.getAdditionalPictures());
        jewelry.setStatus(jewelryDTO.getStatus());
        return jewelry;
    }

    @Override
    public JewelryDTO convertToDTO(Jewelry jewelry) {
        JewelryDTO jewelryDTO = new JewelryDTO();
        jewelryDTO.setId(jewelry.getId());
        jewelryDTO.setName(jewelry.getName());
        jewelryDTO.setCategory(jewelry.getCategory());
        jewelryDTO.setPrice(jewelry.getPrice());
        jewelryDTO.setDateAdded(jewelry.getDateAdded());
        jewelryDTO.setQuantityStock(jewelry.getQuantityStock());
        jewelryDTO.setMainJewelryPicture(jewelry.getMainJewelryPicture());
        jewelryDTO.setAdditionalPictures(jewelry.getAdditionalPictures());
        jewelryDTO.setStatus(jewelry.getStatus());
        return jewelryDTO;
    }

    @Override
    public List<JewelryDTO> convertToDTOList(List<Jewelry> jewelryList) {
        List<JewelryDTO> dtoList = new ArrayList<>();
        for (Jewelry jewelry : jewelryList) {
            JewelryDTO dto = new JewelryDTO();
            dto.setId(jewelry.getId());
            dto.setName(jewelry.getName());
            dto.setCategory(jewelry.getCategory());
            dto.setPrice(jewelry.getPrice());
            dto.setDateAdded(jewelry.getDateAdded());
            dto.setQuantityStock(jewelry.getQuantityStock());
            dto.setMainJewelryPicture(jewelry.getMainJewelryPicture());
            dto.setAdditionalPictures(jewelry.getAdditionalPictures());
            dto.setStatus(jewelry.getStatus());
            dtoList.add(dto);
        }
        return dtoList;
    }
    
}
