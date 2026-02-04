package com.nutrition.nutrition_voice_tracker_api.nutrition.dto;

import com.nutrition.nutrition_voice_tracker_api.domain.NutritionEntry;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class NutritionEntryResponse {
    private UUID id;
    private String label;
    private String rawInput;
    private String source;
    private String externalFoodId;
    private BigDecimal amount;
    private String unit;
    private Integer calories;
    private BigDecimal proteinG;
    private BigDecimal carbsG;
    private BigDecimal fatG;
    private Instant loggedAt;
    private Instant createdAt;

    public static NutritionEntryResponse from(NutritionEntry e) {
        NutritionEntryResponse r = new NutritionEntryResponse();
        r.id = e.getId();
        r.label = e.getLabel();
        r.rawInput = e.getRawInput();
        r.source = e.getSource();
        r.externalFoodId = e.getExternalFoodId();
        r.amount = e.getAmount();
        r.unit = e.getUnit();
        r.calories = e.getCalories();
        r.proteinG = e.getProteinG();
        r.carbsG = e.getCarbsG();
        r.fatG = e.getFatG();
        r.loggedAt = e.getLoggedAt();
        r.createdAt = e.getCreatedAt();
        return r;
    }
}
