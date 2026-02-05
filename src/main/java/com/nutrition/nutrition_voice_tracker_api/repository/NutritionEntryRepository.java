package com.nutrition.nutrition_voice_tracker_api.repository;

import com.nutrition.nutrition_voice_tracker_api.domain.NutritionEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("""
        select
          coalesce(sum(e.calories), 0),
          coalesce(sum(e.proteinG), 0),
          coalesce(sum(e.carbsG), 0),
          coalesce(sum(e.fatG), 0)
        from NutritionEntry e
        where e.userId = :userId
          and e.loggedAt >= :start
          and e.loggedAt < :end
    """)
    List<Object[]> sumTotalsForRange(
            @Param("userId") UUID userId,
            @Param("start") Instant start,
            @Param("end") Instant end
    );

    @Query(value = """
        select
          to_char(e.logged_at at time zone 'UTC', 'YYYY-MM-DD') as day,
          coalesce(sum(e.calories), 0) as calories,
          coalesce(sum(e.protein_g), 0) as protein_g,
          coalesce(sum(e.carbs_g), 0) as carbs_g,
          coalesce(sum(e.fat_g), 0) as fat_g
        from nutrition_entries e
        where e.user_id = :userId
          and e.logged_at >= :start
          and e.logged_at < :end
        group by day
        order by day asc
    """, nativeQuery = true)
    List<Object[]> dailySummaryNative(
            @Param("userId") UUID userId,
            @Param("start") Instant start,
            @Param("end") Instant end
    );
}