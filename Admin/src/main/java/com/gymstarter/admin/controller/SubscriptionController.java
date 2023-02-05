package com.gymstarter.admin.controller;

import com.gymstarter.library.model.Subscription;
import com.gymstarter.library.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/subscriptions")
    public String subscriptions(Model model, Principal principal) {
        if (principal == null){
            return "redirect:/login";
        }
        List<Subscription> subscriptions = subscriptionService.findAll();
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("size", subscriptions.size());
        model.addAttribute("title", "Subscription");
        model.addAttribute("subscriptionNew", new Subscription());
        return "subscriptions";
    }

    @PostMapping("/add-subscription")
    public String add(@ModelAttribute("subscriptionNew") Subscription subscription, RedirectAttributes attributes) {
        try {
            subscriptionService.save(subscription);
            attributes.addFlashAttribute("success", "Added successfully");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/subscriptions";
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Subscription findById(Long id){
        return subscriptionService.findById(id);
    }

    @GetMapping("/update-subscription")
    public String update(Subscription subscription, RedirectAttributes attributes){
        try {
            subscriptionService.update(subscription);
            attributes.addFlashAttribute("success","Updated successfully");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/subscriptions";
    }

    @RequestMapping(value = "/delete-subscription", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id, RedirectAttributes attributes){
        try {
            subscriptionService.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to deleted");
        }
        return "redirect:/subscriptions";
    }
    @RequestMapping(value = "/enable-subscription", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes attributes){
        try {
            subscriptionService.enabledById(id);
            attributes.addFlashAttribute("success", "Enabled successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enabled");
        }
        return "redirect:/subscriptions";
    }

}
