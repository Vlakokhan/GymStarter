package com.gymstarter.client.сontroller;

import com.gymstarter.library.dto.ClientDto;
import com.gymstarter.library.model.Client;
import com.gymstarter.library.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private ClientService clientService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("clientDto", new ClientDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute("clientDto") ClientDto clientDto,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("clientDto", clientDto);
                return "register";
            }
            Client client = clientService.findByUsername(clientDto.getUsername());
            if (client != null) {
                model.addAttribute("username", "Username have been registered");
                model.addAttribute("clientDto", clientDto);
                return "register";
            }
            if (clientDto.getPassword().equals(clientDto.getRepeatPassword())) {
                clientDto.setPassword(passwordEncoder.encode(clientDto.getPassword()));
                clientService.save(clientDto);
                model.addAttribute("success", "Register successfully");
            } else {
                model.addAttribute("password", "Password is not same");
                model.addAttribute("clientDto", clientDto);
            }
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", "Server have ran some problems");
            model.addAttribute("clientDto", clientDto);
        }
        return "register";
    }
}
