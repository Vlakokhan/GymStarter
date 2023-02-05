package com.gymstarter.client.сontroller;


import com.gymstarter.library.model.Client;
import com.gymstarter.library.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class AccountController {

    @Autowired
    private ClientService clientService;


    @GetMapping("/account")
    public String accountHome(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Client client = clientService.findByUsername(username);


        model.addAttribute("client", client);

        return "account";
    }

    @RequestMapping(value = "/update-info", method = {RequestMethod.GET, RequestMethod.PUT})
    public String updateCustomer(@ModelAttribute("client") Client client,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Client clientSave = clientService.saveInfo(client);

        redirectAttributes.addFlashAttribute("client", clientSave);

        return "redirect:/account";
    }

}
