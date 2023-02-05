package com.gymstarter.library.service;

import com.gymstarter.library.model.Client;
import com.gymstarter.library.model.ShoppingCart;
import com.gymstarter.library.model.Workout;
import org.springframework.stereotype.Service;


public interface ShoppingCartService {
    ShoppingCart addItemToCart(Workout workout, int quantity, Client client);

    ShoppingCart updateItemInCart(Workout workout, int quantity, Client client);

    ShoppingCart deleteItemFromCart(Workout workout, Client client);
}
