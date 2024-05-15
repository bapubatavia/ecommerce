package com.batavia.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batavia.ecommerce.model.BillingAddress;
import com.batavia.ecommerce.repositories.BillingAddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingAddressService implements BillingAddressServiceInterface{

    @Autowired
    private BillingAddressRepository billAddRepo;


    @Override
    public Boolean saveBillingAddress(BillingAddress billingAddress) {
        try {
            billAddRepo.save(billingAddress);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
