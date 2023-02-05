package com.gymstarter.library.service.impl;

import com.gymstarter.library.model.CartItem;
import com.gymstarter.library.model.Client;
import com.gymstarter.library.model.ShoppingCart;
import com.gymstarter.library.model.Workout;
import com.gymstarter.library.repository.CartItemRepository;
import com.gymstarter.library.repository.ShoppingCartRepository;
import com.gymstarter.library.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private CartItemRepository itemRepository;

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Override
    public ShoppingCart addItemToCart(Workout workout, int quantity, Client client) {
        ShoppingCart cart = client.getShoppingCart();

        if (cart == null) {
            cart = new ShoppingCart();
        }
        Set<CartItem> cartItems = cart.getCartItem();
        CartItem cartItem = findCartItem(cartItems, workout.getId());
        if (cartItems == null) {
            cartItems = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setWorkout(workout);
                cartItem.setTotalPrice(quantity * workout.getSalePrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                itemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setWorkout(workout);
                cartItem.setTotalPrice(quantity * workout.getSalePrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                itemRepository.save(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * workout.getSalePrice()));
                itemRepository.save(cartItem);
            }
        }
        cart.setCartItem(cartItems);

        int totalItems = totalItems(cart.getCartItem());
        double totalPrice = totalPrice(cart.getCartItem());
        cart.setTotalItems(totalItems);
        cart.setTotalPrices(totalPrice);
        cart.setClient(client);

        return cartRepository.save(cart);
    }

    @Override
    public ShoppingCart updateItemInCart(Workout workout, int quantity, Client client) {
        ShoppingCart cart = client.getShoppingCart();

        Set<CartItem> cartItems = cart.getCartItem();

        CartItem item = findCartItem(cartItems, workout.getId());
        item.setQuantity(quantity);
        item.setTotalPrice(quantity * workout.getSalePrice());

        itemRepository.save(item);
        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrice(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalPrices(totalPrice);
        return cartRepository.save(cart);
    }

    @Override
    public ShoppingCart deleteItemFromCart(Workout workout, Client client) {
        ShoppingCart cart = client.getShoppingCart();
        Set<CartItem> cartItems = cart.getCartItem();
        CartItem item = findCartItem(cartItems, workout.getId());
        cartItems.remove(item);
        itemRepository.delete(item);
        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrice(cartItems);
        cart.setTotalItems(totalItems);
        cart.setCartItem(cartItems);
        cart.setTotalPrices(totalPrice);
        return cartRepository.save(cart);
    }

    private CartItem findCartItem(Set<CartItem> cartItems, Long workoutId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;
        for (CartItem item : cartItems) {
            if (item.getWorkout().getId() == workoutId) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItems(Set<CartItem> cartItems) {
        int totalItems = 0;
        for (CartItem item : cartItems) {
            totalItems += item.getQuantity();
        }
        return totalItems;
    }

    private double totalPrice(Set<CartItem> cartItems) {
        double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }

}
