package com.gymstarter.library.repository;

import com.gymstarter.library.dto.SubscriptionDto;
import com.gymstarter.library.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    /*Admin*/
    @Query("select c from Subscription c where c.is_activated = true and c.is_deleted = false")
    List<Subscription> findAllByActivated();

    /*Client*/
    @Query("select new com.gymstarter.library.dto.SubscriptionDto(c.id, c.name, count(p.subscription.id)) from Subscription c inner join Workout p on p.subscription.id = c.id " +
            " where c.is_activated = true and c.is_deleted = false group by c.id")
    List<SubscriptionDto> getSubscriptionAndWorkout();
}
