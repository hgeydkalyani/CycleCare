package com.cyclecare.backend.service;

import com.cyclecare.backend.dto.CycleDataDTO;
import com.cyclecare.backend.dto.CyclePredictionDTO;
import com.cyclecare.backend.entity.CycleData;
import com.cyclecare.backend.entity.User;
import com.cyclecare.backend.repository.CycleDataRepository;
import com.cyclecare.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CycleService {
    
    private final CycleDataRepository cycleDataRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public CycleDataDTO saveCycleData(CycleDataDTO cycleDataDTO) {
        CycleData cycleData = new CycleData();
        cycleData.setStartDate(cycleDataDTO.getStartDate());
        cycleData.setCycleLength(cycleDataDTO.getCycleLength());
        cycleData.setSymptoms(cycleDataDTO.getSymptoms());
        cycleData.setFlowIntensity(cycleDataDTO.getFlowIntensity());
        cycleData.setMood(cycleDataDTO.getMood());
        cycleData.setNotes(cycleDataDTO.getNotes());
        
        if (cycleDataDTO.getUserId() != null) {
            User user = userRepository.findById(cycleDataDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            cycleData.setUser(user);
        }
        
        CycleData saved = cycleDataRepository.save(cycleData);
        return convertToDTO(saved);
    }
    
    public List<CycleDataDTO> getAllCycleData(Long userId) {
        List<CycleData> cycleDataList;
        
        if (userId != null) {
            cycleDataList = cycleDataRepository.findByUserIdOrderByStartDateDesc(userId);
        } else {
            cycleDataList = cycleDataRepository.findAll();
        }
        
        return cycleDataList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public CyclePredictionDTO predictNextCycle(Long userId) {
        List<CycleData> recentCycles = cycleDataRepository.findLatestCyclesByUserId(userId);
        
        if (recentCycles.isEmpty()) {
            CyclePredictionDTO prediction = new CyclePredictionDTO();
            prediction.setMessage("No cycle data available. Please log your periods to get predictions.");
            prediction.setPredictionAccuracy("Low");
            return prediction;
        }
        
        // Calculate average cycle length
        double averageCycleLength = recentCycles.stream()
                .mapToInt(CycleData::getCycleLength)
                .average()
                .orElse(28.0);
        
        // Get the most recent cycle
        CycleData lastCycle = recentCycles.get(0);
        LocalDate lastPeriodStart = lastCycle.getStartDate();
        
        // Predict next period
        LocalDate nextPeriodDate = lastPeriodStart.plusDays((long) averageCycleLength);
        
        // Calculate ovulation (typically 14 days before next period)
        LocalDate ovulationDate = nextPeriodDate.minusDays(14);
        
        // Fertile window (5 days before ovulation to 1 day after)
        LocalDate fertileWindowStart = ovulationDate.minusDays(5);
        LocalDate fertileWindowEnd = ovulationDate.plusDays(1);
        
        // Determine prediction accuracy
        String accuracy;
        if (recentCycles.size() >= 3) {
            // Calculate variance
            double variance = calculateVariance(recentCycles);
            if (variance < 3) {
                accuracy = "High";
            } else if (variance < 5) {
                accuracy = "Medium";
            } else {
                accuracy = "Low";
            }
        } else {
            accuracy = "Medium";
        }
        
        CyclePredictionDTO prediction = new CyclePredictionDTO();
        prediction.setNextPeriodDate(nextPeriodDate);
        prediction.setOvulationDate(ovulationDate);
        prediction.setFertileWindowStart(fertileWindowStart);
        prediction.setFertileWindowEnd(fertileWindowEnd);
        prediction.setAverageCycleLength((int) Math.round(averageCycleLength));
        prediction.setPredictionAccuracy(accuracy);
        prediction.setMessage("Your next period is predicted to start on " + nextPeriodDate);
        
        return prediction;
    }
    
    private double calculateVariance(List<CycleData> cycles) {
        double mean = cycles.stream()
                .mapToInt(CycleData::getCycleLength)
                .average()
                .orElse(28.0);
        
        double variance = cycles.stream()
                .mapToDouble(c -> Math.pow(c.getCycleLength() - mean, 2))
                .average()
                .orElse(0.0);
        
        return Math.sqrt(variance);
    }
    
    private CycleDataDTO convertToDTO(CycleData cycleData) {
        CycleDataDTO dto = new CycleDataDTO();
        dto.setId(cycleData.getId());
        dto.setStartDate(cycleData.getStartDate());
        dto.setCycleLength(cycleData.getCycleLength());
        dto.setSymptoms(cycleData.getSymptoms());
        dto.setFlowIntensity(cycleData.getFlowIntensity());
        dto.setMood(cycleData.getMood());
        dto.setNotes(cycleData.getNotes());
        if (cycleData.getUser() != null) {
            dto.setUserId(cycleData.getUser().getId());
        }
        return dto;
    }
}
