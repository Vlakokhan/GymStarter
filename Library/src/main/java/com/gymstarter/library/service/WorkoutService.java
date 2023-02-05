package com.gymstarter.library.service;

import com.gymstarter.library.dto.WorkoutDto;
import com.gymstarter.library.model.Workout;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WorkoutService {
    /*Admin*/
    List<WorkoutDto> findAll();

    Workout save(MultipartFile imageWorkout, WorkoutDto workoutDto);

    Workout update(MultipartFile imageWorkout, WorkoutDto workoutDto);

    void deleteById(Long id);

    void enableById(Long id);

    WorkoutDto getById(Long id);

    Page<WorkoutDto> pageWorkout(int pageNo);

    Page<WorkoutDto> searchWorkouts(int pageNo, String keyword);

    /*Client*/

    List<Workout> getAllWorkouts();

    List<Workout> listViewWorkouts();

    Workout getWorkoutById(Long id);

    List<Workout> getRelatedWorkouts(Long subscriptionId);

    List<Workout> getWorkoutsInSubscription(Long subscriptionId);

    List<Workout> filterHighPrice();

    List<Workout> filterLowPrice();

}
