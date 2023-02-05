package com.gymstarter.library.service.impl;

import com.gymstarter.library.model.CartItem;
import com.gymstarter.library.model.Order;
import com.gymstarter.library.model.OrderDetail;
import com.gymstarter.library.model.ShoppingCart;
import com.gymstarter.library.repository.CartItemRepository;
import com.gymstarter.library.repository.OrderDetailRepository;
import com.gymstarter.library.repository.OrderRepository;
import com.gymstarter.library.repository.ShoppingCartRepository;
import com.gymstarter.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ShoppingCartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public void saveOrder(ShoppingCart cart) {
        Order order = new Order();
        order.setOrderStatus("PENDING");
        order.setSubscriptionStart(new Date());
        order.setClient(cart.getClient());
        order.setTotalPrice(cart.getTotalPrices());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem item : cart.getCartItem()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setWorkout(item.getWorkout());
            orderDetail.setTotalPrice(item.getWorkout().getSalePrice());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
            cartItemRepository.delete(item);
        }
        order.setOrderDetailList(orderDetailList);
        cart.setCartItem(new HashSet<>());
        cart.setTotalItems(0);
        cart.setTotalPrices(0);
        cartRepository.save(cart);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
