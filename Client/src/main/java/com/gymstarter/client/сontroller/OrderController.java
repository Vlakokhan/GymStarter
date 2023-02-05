package com.gymstarter.client.сontroller;


import com.gymstarter.library.model.Client;
import com.gymstarter.library.model.Order;
import com.gymstarter.library.model.ShoppingCart;
import com.gymstarter.library.service.ClientService;
import com.gymstarter.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Client client = clientService.findByUsername(username);
        if (client.getPhoneNumber().trim().isEmpty()
                || client.getAddress().trim().isEmpty()
                || client.getCity().trim().isEmpty()
                || client.getCountry().trim().isEmpty()) {
            model.addAttribute("client", client);
            model.addAttribute("error", "You must fill the information after checkout!");
            return "account";
        } else {
            model.addAttribute("client", client);
            ShoppingCart cart = client.getShoppingCart();
            model.addAttribute("cart", cart);
        }
        return "checkout";
    }

    @GetMapping("/order")
    public String order(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Client client = clientService.findByUsername(username);
        List<Order> orderList = client.getOrders();
        model.addAttribute("orders", orderList);
        return "order";
    }

    @GetMapping("/save-order")
    public String saveOrder(Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Client client = clientService.findByUsername(username);
        ShoppingCart cart = client.getShoppingCart();
        orderService.saveOrder(cart);
        return "redirect:/order";
    }
}
