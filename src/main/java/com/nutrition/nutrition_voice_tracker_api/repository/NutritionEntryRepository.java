package com.nutrition.nutrition_voice_tracker_api.repository;

import com.nutrition.nutrition_voice_tracker_api.domain.NutritionEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NutritionEntryRepository extends JpaRepository<NutritionEntry, UUID> {

    List<NutritionEntry> findByUserIdOrderByLoggedAtDesc(UUID userId, Pageable pageable);

    List<NutritionEntry> findByUserIdAndLoggedAtBetweenOrderByLoggedAtDesc(
            UUID userId, Instant from, Instant to, Pageable pageable
    );

    Optional<NutritionEntry> findByIdAndUserId(UUID id, UUID userId);

    void deleteByIdAndUserId(UUID id, UUID userId);
}