package com.cyclecare.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CycleDataDTO {
    
    private Long id;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "Cycle length is required")
    private Integer cycleLength;
    
    private String symptoms;
    
    private String flowIntensity;
    
    private String mood;
    
    private String notes;
    
    private Long userId;
}
