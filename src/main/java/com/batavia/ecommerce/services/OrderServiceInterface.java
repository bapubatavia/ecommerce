package com.batavia.ecommerce.services;

import java.util.List;


import com.batavia.ecommerce.model.ClientOrder;
import com.batavia.ecommerce.model.User;

public interface OrderServiceInterface {
    Boolean makeOrder(ClientOrder clientOrder);
    Boolean updateOrder(ClientOrder clientOrder);
    List<ClientOrder> findClientOrders(User user);
    ClientOrder displayOrder(String orderId);
    List<ClientOrder> findAllOrders();
}
