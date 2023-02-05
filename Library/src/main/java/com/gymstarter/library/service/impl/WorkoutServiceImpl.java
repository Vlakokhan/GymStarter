package com.gymstarter.library.service.impl;

import com.gymstarter.library.dto.WorkoutDto;
import com.gymstarter.library.model.Workout;
import com.gymstarter.library.repository.WorkoutRepository;
import com.gymstarter.library.service.WorkoutService;
import com.gymstarter.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class WorkoutServiceImpl implements WorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private ImageUpload imageUpload;

    /*Admin*/
    @Override
    public List<WorkoutDto> findAll() {
        List<Workout> workouts = workoutRepository.findAll();
        List<WorkoutDto> workoutDtoList = transfer(workouts);
        return workoutDtoList;
    }

    @Override
    public Workout save(MultipartFile imageWorkout, WorkoutDto workoutDto) {
        try {
            Workout workout = new Workout();
            if (imageWorkout == null) {
                workout.setImage(null);
            } else {
                if (imageUpload.uploadImage(imageWorkout)) {
                    System.out.println("Upload successfully");
                }
                workout.setImage(Base64.getEncoder().encodeToString(imageWorkout.getBytes()));
            }
            workout.setName(workoutDto.getName());
            workout.setDescription(workoutDto.getDescription());
            workout.setSubscription(workoutDto.getSubscription());
            workout.setDuration(workoutDto.getDuration());
            workout.setSalePrice(workoutDto.getSalePrice());
            workout.setNumberOfExercises(workoutDto.getNumberOfExercises());
            workout.set_activated(true);
            workout.set_deleted(false);
            return workoutRepository.save(workout);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Workout update(MultipartFile imageWorkout, WorkoutDto workoutDto) {
        try {
            Workout workout = workoutRepository.getById(workoutDto.getId());
            if (imageWorkout == null) {
                workout.setImage(workout.getImage());
            } else {
                if (imageUpload.checkExisted(imageWorkout) == false) {
                    imageUpload.uploadImage(imageWorkout);
                }
                workout.setImage(Base64.getEncoder().encodeToString(imageWorkout.getBytes()));
            }
            workout.setName(workoutDto.getName());
            workout.setDescription(workoutDto.getDescription());
            workout.setSubscription(workoutDto.getSubscription());
            workout.setDuration(workoutDto.getDuration());
            workout.setSalePrice(workoutDto.getSalePrice());
            workout.setNumberOfExercises(workoutDto.getNumberOfExercises());
            return workoutRepository.save(workout);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        Workout workout = workoutRepository.getById(id);
        workout.set_deleted(true);
        workout.set_activated(false);
        workoutRepository.save(workout);
    }

    @Override
    public void enableById(Long id) {
        Workout workout = workoutRepository.getById(id);
        workout.set_deleted(false);
        workout.set_activated(true);
        workoutRepository.save(workout);
    }

    @Override
    public WorkoutDto getById(Long id) {
        Workout workout = workoutRepository.getById(id);
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setName(workout.getName());
        workoutDto.setDescription(workout.getDescription());
        workoutDto.setDuration(workout.getDuration());
        workoutDto.setSalePrice(workout.getSalePrice());
        workoutDto.setNumberOfExercises(workout.getNumberOfExercises());
        workoutDto.setImage(workout.getImage());
        workoutDto.setSubscription(workout.getSubscription());
        workoutDto.setActivated(workout.is_activated());
        workoutDto.setDeleted(workout.is_deleted());
        return workoutDto;
    }

    @Override
    public Page<WorkoutDto> pageWorkout(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<WorkoutDto> workouts = transfer(workoutRepository.findAll());
        Page<WorkoutDto> workoutPage = toPage(workouts, pageable);
        return workoutPage;
    }

    @Override
    public Page<WorkoutDto> searchWorkouts(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<WorkoutDto> workoutDtoList = transfer(workoutRepository.searchWorkoutsList(keyword));
        Page<WorkoutDto> workouts = toPage(workoutDtoList, pageable);
        return workouts;
    }

    private Page toPage(List<WorkoutDto> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    private List<WorkoutDto> transfer(List<Workout> workouts) {
        List<WorkoutDto> workoutDtoList = new ArrayList<>();
        for (Workout workout : workouts) {
            WorkoutDto workoutDto = new WorkoutDto();
            workoutDto.setId(workout.getId());
            workoutDto.setName(workout.getName());
            workoutDto.setDescription(workout.getDescription());
            workoutDto.setDuration(workout.getDuration());
            workoutDto.setSalePrice(workout.getSalePrice());
            workoutDto.setNumberOfExercises(workout.getNumberOfExercises());
            workoutDto.setImage(workout.getImage());
            workoutDto.setSubscription(workout.getSubscription());
            workoutDto.setActivated(workout.is_activated());
            workoutDto.setDeleted(workout.is_deleted());
            workoutDtoList.add(workoutDto);
        }
        return workoutDtoList;
    }

    /*Client*/
    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.getAllWorkouts();
    }

    @Override
    public List<Workout> listViewWorkouts() {
        return workoutRepository.listViewWorkouts();
    }

    @Override
    public Workout getWorkoutById(Long id) {
        return workoutRepository.getById(id);
    }

    @Override
    public List<Workout> getRelatedWorkouts(Long subscriptionId) {
        return workoutRepository.getRelatedWorkouts(subscriptionId);
    }

    @Override
    public List<Workout> getWorkoutsInSubscription(Long subscriptionId) {
        return workoutRepository.getWorkoutsInSubscription(subscriptionId);
    }

    @Override
    public List<Workout> filterHighPrice() {
        return workoutRepository.filterHighPrice();
    }

    @Override
    public List<Workout> filterLowPrice() {
        return workoutRepository.filterLowPrice();
    }

}
