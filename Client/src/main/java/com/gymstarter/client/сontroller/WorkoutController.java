package com.gymstarter.client.сontroller;

import com.gymstarter.library.dto.SubscriptionDto;
import com.gymstarter.library.model.Subscription;
import com.gymstarter.library.model.Workout;
import com.gymstarter.library.service.SubscriptionService;
import com.gymstarter.library.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/workouts")
    public String workouts(Model model) {
        List<SubscriptionDto> subscriptionDtoList = subscriptionService.getSubscriptionAndWorkout();
        List<Workout> workouts = workoutService.getAllWorkouts();
        List<Workout> listViewWorkouts = workoutService.listViewWorkouts();
        model.addAttribute("subscriptions", subscriptionDtoList);
        model.addAttribute("viewWorkouts", listViewWorkouts);
        model.addAttribute("workouts", workouts);
        return "gym";
    }

    @GetMapping("/find-workout/{id}")
    public String findWorkoutById(@PathVariable("id") Long id, Model model) {
        Workout workout = workoutService.getWorkoutById(id);
        Long subscriptionId = workout.getSubscription().getId();
        List<Workout> workouts = workoutService.getRelatedWorkouts(subscriptionId);
        model.addAttribute("workout", workout);
        model.addAttribute("workouts", workouts);
        return "workout-detail";
    }

    @GetMapping("/workouts-in-subscription/{id}")
    public String getProductsInCategory(@PathVariable("id") Long subscriptionId, Model model) {
        Subscription subscription = subscriptionService.findById(subscriptionId);
        List<SubscriptionDto> subscriptions = subscriptionService.getSubscriptionAndWorkout();
        List<Workout> workouts = workoutService.getWorkoutsInSubscription(subscriptionId);
        model.addAttribute("subscription", subscription);
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("workouts", workouts);
        return "workouts-in-subscription";
    }

    @GetMapping("/high-price")
    public String filterHighPrice(Model model) {
        List<Subscription> subscriptions = subscriptionService.findAllByActivated();
        List<SubscriptionDto> subscriptionDtoList = subscriptionService.getSubscriptionAndWorkout();
        List<Workout> workouts = workoutService.filterHighPrice();
        model.addAttribute("subscriptionDtoList", subscriptionDtoList);
        model.addAttribute("workouts", workouts);
        model.addAttribute("subscriptions", subscriptions);
        return "filter-high-price";
    }

    @GetMapping("/low-price")
    public String filterLowPrice(Model model) {
        List<Subscription> subscriptions = subscriptionService.findAllByActivated();
        List<SubscriptionDto> subscriptionDtoList = subscriptionService.getSubscriptionAndWorkout();
        List<Workout> workouts = workoutService.filterLowPrice();
        model.addAttribute("subscriptionDtoList", subscriptionDtoList);
        model.addAttribute("workouts", workouts);
        model.addAttribute("subscriptions", subscriptions);
        return "filter-low-price";
    }

}
