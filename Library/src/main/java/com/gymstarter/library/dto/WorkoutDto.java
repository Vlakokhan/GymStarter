package com.gymstarter.library.dto;

import com.gymstarter.library.model.Subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDto {
    private Long id;
    private String name;
    private String description;
    private double Duration;
    private double salePrice;
    private int numberOfExercises;
    private String image;
    private Subscription subscription;
    private boolean deleted;
    private boolean activated;
}
