package com.gymstarter.library.repository;

import com.gymstarter.library.model.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WorkoutRepository extends JpaRepository<Workout,Long> {
    /*Admin*/
    @Query("select p from Workout p")
    Page<Workout> pageWorkout(Pageable pageable);

    @Query("select p from Workout p where p.description like %?1% or p.name like %?1%")
    Page<Workout> searchWorkout(String keyword, Pageable pageable);

    @Query("select p from Workout p where p.description like %?1% or p.name like %?1%")
    List<Workout> searchWorkoutsList(String keyword);


    /*Client*/
    @Query("select p from Workout p where p.is_activated = true and p.is_deleted = false")
    List<Workout> getAllWorkouts();

    @Query(value = "select * from workout p where p.is_deleted = false and p.is_activated = true order by rand() asc limit 4 ", nativeQuery = true)
    List<Workout> listViewWorkouts();

    @Query(value = "select * from workout p inner join subscription c on c.subscription_id = p.subscription_id where p.subscription_id = ?1", nativeQuery = true)
    List<Workout> getRelatedWorkouts(Long subscriptionId);

    @Query(value = "select p from Workout p inner join Subscription c on c.id = p.subscription.id where c.id = ?1 and p.is_deleted = false and p.is_activated = true")
    List<Workout> getWorkoutsInSubscription(Long categoryId);

    @Query("select p from Workout p where p.is_activated = true and p.is_deleted = false" +
            " order by p.salePrice desc")
    List<Workout> filterHighPrice();

    @Query("select p from Workout p where p.is_activated = true and p.is_deleted = false order by p.salePrice ")
    List<Workout> filterLowPrice();

}
