package com.cyclecare.backend.controller;

import com.cyclecare.backend.dto.CycleDataDTO;
import com.cyclecare.backend.dto.CyclePredictionDTO;
import com.cyclecare.backend.service.CycleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cycle")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CycleController {
    
    private final CycleService cycleService ;
    
    @GetMapping("/predict")
    public ResponseEntity<CyclePredictionDTO> predictNextCycle(@RequestParam(required = false) Long userId) {
        // For demo purposes, using userId 1 as default
        Long effectiveUserId = userId != null ? userId : 1L;
        CyclePredictionDTO prediction = cycleService.predictNextCycle(effectiveUserId);
        return ResponseEntity.ok(prediction);
    }
    
    @PostMapping
    public ResponseEntity<CycleDataDTO> saveCycleData(@Valid @RequestBody CycleDataDTO cycleDataDTO) {
        // For demo purposes, default to userId 1 if not provided
        if (cycleDataDTO.getUserId() == null) {
            cycleDataDTO.setUserId(1L);
        }
        CycleDataDTO savedData = cycleService.saveCycleData(cycleDataDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedData);
    }
    
    @GetMapping
    public ResponseEntity<List<CycleDataDTO>> getAllCycleData(@RequestParam(required = false) Long userId) {
        List<CycleDataDTO> cycleDataList = cycleService.getAllCycleData(userId);
        return ResponseEntity.ok(cycleDataList);
    }
}
