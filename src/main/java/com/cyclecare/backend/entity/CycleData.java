package com.cyclecare.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cycle_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CycleData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @NotNull(message = "Cycle length is required")
    @Column(name = "cycle_length", nullable = false)
    private Integer cycleLength;
    
    @Column(columnDefinition = "TEXT")
    private String symptoms;
    
    @Column(name = "flow_intensity")
    private String flowIntensity; // Light, Medium, Heavy
    
    @Column(name = "mood")
    private String mood;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
