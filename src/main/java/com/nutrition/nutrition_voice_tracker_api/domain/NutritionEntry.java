package com.nutrition.nutrition_voice_tracker_api.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(name = "nutrition_entries")
public class NutritionEntry {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false, length = 255)
    private String label;

    @Column(name = "raw_input", length = 500)
    private String rawInput;

    @Column(nullable = false, length = 20)
    private String source = "manual";

    @Column(name = "external_food_id", length = 100)
    private String externalFoodId;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(length = 20)
    private String unit;

    @Column(nullable = false)
    private Integer calories;

    @Column(name = "protein_g", nullable = false, precision = 8, scale = 2)
    private BigDecimal proteinG;

    @Column(name = "carbs_g", nullable = false, precision = 8, scale = 2)
    private BigDecimal carbsG;

    @Column(name = "fat_g", nullable = false, precision = 8, scale = 2)
    private BigDecimal fatG;

    // We'll add micros later. For now leave it out (JSONB mapping is a small extra step).

    @Column(name = "logged_at", nullable = false)
    private Instant loggedAt;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Instant createdAt;

    protected NutritionEntry() {}

    public NutritionEntry(UUID userId,
                          String label,
                          String rawInput,
                          String source,
                          String externalFoodId,
                          BigDecimal amount,
                          String unit,
                          Integer calories,
                          BigDecimal proteinG,
                          BigDecimal carbsG,
                          BigDecimal fatG,
                          Instant loggedAt) {
        this.userId = userId;
        this.label = label;
        this.rawInput = rawInput;
        this.source = (source == null || source.isBlank()) ? "manual" : source;
        this.externalFoodId = externalFoodId;
        this.amount = amount;
        this.unit = unit;
        this.calories = calories;
        this.proteinG = proteinG;
        this.carbsG = carbsG;
        this.fatG = fatG;
        this.loggedAt = loggedAt == null ? Instant.now() : loggedAt;
    }

}