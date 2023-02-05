package com.gymstarter.library.service.impl;

import com.gymstarter.library.dto.SubscriptionDto;
import com.gymstarter.library.model.Subscription;
import com.gymstarter.library.repository.SubscriptionRepository;
import com.gymstarter.library.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository repo;

    @Override
    public List<Subscription> findAll() {
        return repo.findAll();
    }

    @Override
    public Subscription save(Subscription subscription) {
        try {
            Subscription subscriptionSave = new Subscription(subscription.getName(), subscription.getCost());
            return repo.save(subscriptionSave);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Subscription findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Subscription update(Subscription subscription) {
        Subscription subscriptionUpdate = null;
        try {
            subscriptionUpdate = repo.findById(subscription.getId()).get();
            subscriptionUpdate.setName(subscription.getName());
            subscriptionUpdate.setCost(subscription.getCost());
            subscriptionUpdate.set_activated(subscription.is_activated());
            subscriptionUpdate.set_deleted(subscription.is_deleted());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repo.save(subscriptionUpdate);
    }

    @Override
    public void deleteById(Long id) {
        Subscription subscription = repo.getById(id);
        subscription.set_deleted(true);
        subscription.set_activated(false);
        repo.save(subscription);
    }

    @Override
    public void enabledById(Long id) {
        Subscription subscription = repo.getById(id);
        subscription.set_activated(true);
        subscription.set_deleted(false);
        repo.save(subscription);
    }

    @Override
    public List<Subscription> findAllByActivated() {
        return repo.findAllByActivated();
    }

    @Override
    public List<SubscriptionDto> getSubscriptionAndWorkout() {
        return repo.getSubscriptionAndWorkout();
    }
}
