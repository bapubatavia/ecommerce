package com.batavia.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batavia.ecommerce.model.ClientOrder;
import com.batavia.ecommerce.model.User;
import com.batavia.ecommerce.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface{

    
    @Autowired
    private final OrderRepository ordRepo;


    @Override
    public Boolean makeOrder(ClientOrder clientOrder) {
        try {
            ordRepo.save(clientOrder);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateOrder(ClientOrder clientOrder) {
        try {
            ordRepo.save(clientOrder);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }    

    @Override
    public List<ClientOrder> findClientOrders(User user) {
        try {
            return ordRepo.findByClient(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ClientOrder displayOrder(String orderId) {
        Long idLong = Long.parseLong(orderId);

        try {
            return ordRepo.findById(idLong).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ClientOrder> findAllOrders() {
        try {
            return ordRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    
}
