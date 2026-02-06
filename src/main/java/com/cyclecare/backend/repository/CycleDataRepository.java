package com.cyclecare.backend.repository;

import com.cyclecare.backend.entity.CycleData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CycleDataRepository extends JpaRepository<CycleData, Long> {
    
    List<CycleData> findByUserIdOrderByStartDateDesc(Long userId);
    
    @Query("SELECT c FROM CycleData c WHERE c.user.id = :userId ORDER BY c.startDate DESC")
    List<CycleData> findLatestCyclesByUserId(Long userId);
    
    Optional<CycleData> findTopByUserIdOrderByStartDateDesc(Long userId);
    
    List<CycleData> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
}
