package com.delitech.revealing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto implements Serializable {
    private UUID id;
    private RestaurantDto restaurant;
    private ClientDto client;
    private Double score;
    private String comment;
    private LocalDateTime dateTime;
}
