package com.gymstarter.library.service;

import com.gymstarter.library.dto.SubscriptionDto;
import com.gymstarter.library.model.Subscription;
import com.gymstarter.library.model.Workout;

import java.util.List;

public interface SubscriptionService {
    /*Admin*/
    List<Subscription> findAll();

    Subscription save(Subscription subscription);

    Subscription findById(Long id);

    Subscription update(Subscription subscription);

    void deleteById(Long id);

    void enabledById(Long id);

    List<Subscription> findAllByActivated();

    /*Client*/
    List<SubscriptionDto> getSubscriptionAndWorkout();


}
