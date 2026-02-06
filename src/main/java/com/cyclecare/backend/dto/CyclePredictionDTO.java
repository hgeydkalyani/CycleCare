package com.cyclecare.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CyclePredictionDTO {
    
    private LocalDate nextPeriodDate;
    
    private LocalDate ovulationDate;
    
    private LocalDate fertileWindowStart;
    
    private LocalDate fertileWindowEnd;
    
    private Integer averageCycleLength;
    
    private String predictionAccuracy; // High, Medium, Low
    
    private String message;
}
