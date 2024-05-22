package com.delitech.revealing.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(value = 0, message = "la calificación mínima es de 0")
    @Max(value = 5, message = "la calificación máxima es de 5")
    private Double score;
    private String comment;
    private LocalDateTime dateTime;
}
