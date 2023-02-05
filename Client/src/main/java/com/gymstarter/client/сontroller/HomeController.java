package com.gymstarter.client.сontroller;

import com.gymstarter.library.dto.WorkoutDto;
import com.gymstarter.library.model.Client;
import com.gymstarter.library.model.ShoppingCart;
import com.gymstarter.library.model.Subscription;
import com.gymstarter.library.service.ClientService;
import com.gymstarter.library.service.SubscriptionService;
import com.gymstarter.library.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session) {
        if (principal != null) {
            session.setAttribute("username", principal.getName());
            Client client = clientService.findByUsername(principal.getName());
            ShoppingCart cart = client.getShoppingCart();
            session.setAttribute("totalItems", cart.getTotalItems());
        } else {
            session.removeAttribute("username");
        }
        return "home";
    }

    @GetMapping("/home")
    public String index(Model model) {
        List<Subscription> subscriptions = subscriptionService.findAll();
        List<WorkoutDto> workoutDtos = workoutService.findAll();
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("workouts", workoutDtos);
        return "index";
    }
}
