package com.gymstarter.admin.controller;


import com.gymstarter.library.dto.WorkoutDto;
import com.gymstarter.library.model.Subscription;
import com.gymstarter.library.model.Workout;
import org.springframework.data.domain.Page;
import com.gymstarter.library.service.SubscriptionService;
import com.gymstarter.library.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/workouts")
    public String workout(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<WorkoutDto> workoutDtoList = workoutService.findAll();
        model.addAttribute("title", "Manage Workout");
        model.addAttribute("workouts",workoutDtoList);
        model.addAttribute("size", workoutDtoList.size());
        return "workouts";
    }


    @GetMapping("/workouts/{pageNo}")
    public String workoutsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        Page<WorkoutDto> workouts = workoutService.pageWorkout(pageNo);
        model.addAttribute("title", "Manage Workout");
        model.addAttribute("size", workouts.getSize());
        model.addAttribute("totalPages", workouts.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("workouts", workouts);
        return "workouts";
    }

    @GetMapping("/search-result/{pageNo}")
    public String searchWorkouts(@PathVariable("pageNo")int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model,
                                 Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        Page<WorkoutDto> workouts = workoutService.searchWorkouts(pageNo, keyword);
        model.addAttribute("title", "Search Result");
        model.addAttribute("workouts", workouts);
        model.addAttribute("size", workouts.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", workouts.getTotalPages());
        return "result-workouts";
    }

    @GetMapping("/add-workout")
    public String addWorkoutForm(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<Subscription> subscriptions = subscriptionService.findAllByActivated();
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("workout", new WorkoutDto());
        return "add-workout";
    }

    @PostMapping("/save-workout")
    public String saveWorkout(@ModelAttribute("workout")WorkoutDto workoutDto,
                              @RequestParam("imageWorkout")MultipartFile imageWorkout,
                              RedirectAttributes attributes){
        try {
            workoutService.save(imageWorkout, workoutDto);
            attributes.addFlashAttribute("success", "Add successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to add!");
        }

        return "redirect:/workouts";
    }

    @GetMapping("/update-workout/{id}")
    public String updateWorkoutForm(@PathVariable("id") Long id, Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title", "Update workouts");
        List<Subscription> subscriptions = subscriptionService.findAllByActivated();
        WorkoutDto workoutDto = workoutService.getById(id);
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("workoutDto", workoutDto);
        return "update-workout";
    }

    @PostMapping("/update-workout/{id}")
    public String processUpdate(@PathVariable("id") Long id,
                                @ModelAttribute("workoutDto") WorkoutDto workoutDto,
                                @RequestParam("imageWorkout")MultipartFile imageWorkout,
                                RedirectAttributes attributes)
    {
        try {
            workoutService.update(imageWorkout, workoutDto);
            attributes.addFlashAttribute("success", "Update successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/workouts";
    }
    @RequestMapping(value = "/enable-workout/{id}", method = {RequestMethod.PUT , RequestMethod.GET})
    public String enabledWorkout(@PathVariable("id")Long id, RedirectAttributes attributes){
        try {
            workoutService.enableById(id);
            attributes.addFlashAttribute("success", "Enabled successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to enabled!");
        }
        return "redirect:/workouts";
    }

    @RequestMapping(value = "/delete-workout/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedWorkout(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            workoutService.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to deleted");
        }
        return "redirect:/workouts";
    }
}
