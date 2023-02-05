package com.gymstarter.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    private Long subscriptionId;
    private String subscriptionName;
    private Long numberOfWorkout;
}
