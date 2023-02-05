package com.gymstarter.library.service;

import com.gymstarter.library.model.ShoppingCart;

public interface OrderService {

    void saveOrder(ShoppingCart cart);

    void deleteOrder(Long id);
}
