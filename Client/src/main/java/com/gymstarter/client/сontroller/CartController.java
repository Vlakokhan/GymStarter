package com.gymstarter.client.сontroller;


import com.gymstarter.library.model.Client;
import com.gymstarter.library.model.ShoppingCart;
import com.gymstarter.library.model.Workout;
import com.gymstarter.library.service.ClientService;
import com.gymstarter.library.service.ShoppingCartService;
import com.gymstarter.library.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class CartController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private WorkoutService workoutService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Client client = clientService.findByUsername(username);
        ShoppingCart shoppingCart = client.getShoppingCart();
        if (shoppingCart == null) {
            model.addAttribute("check", "No item in your cart");
        }
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("subTotal", shoppingCart.getTotalPrices());
        model.addAttribute("shoppingCart", shoppingCart);
        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(
            @RequestParam("id") Long workoutId,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            Principal principal,
            HttpServletRequest request) {

        if (principal == null) {
            return "redirect:/login";
        }
        Workout workout = workoutService.getWorkoutById(workoutId);
        String username = principal.getName();
        Client client = clientService.findByUsername(username);

        ShoppingCart cart = cartService.addItemToCart(workout, quantity, client);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("quantity") int quantity,
                             @RequestParam("id") Long workoutId,
                             Model model,
                             Principal principal
    ) {

        if (principal == null) {
            return "redirect:/login";
        } else {
            String username = principal.getName();
            Client client = clientService.findByUsername(username);
            Workout workout = workoutService.getWorkoutById(workoutId);
            ShoppingCart cart = cartService.updateItemInCart(workout, quantity, client);
            model.addAttribute("shoppingCart", cart);
            return "redirect:/cart";
        }

    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItemFromCart(@RequestParam("id") Long workoutId,
                                     Model model,
                                     Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            String username = principal.getName();
            Client client = clientService.findByUsername(username);
            Workout workout = workoutService.getWorkoutById(workoutId);
            ShoppingCart cart = cartService.deleteItemFromCart(workout, client);
            model.addAttribute("shoppingCart", cart);
            return "redirect:/cart";
        }

    }


}
