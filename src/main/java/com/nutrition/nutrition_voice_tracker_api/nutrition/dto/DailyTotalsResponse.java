package com.nutrition.nutrition_voice_tracker_api.nutrition.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class DailyTotalsResponse {
    private final int calories;
    private final BigDecimal proteinG;
    private final BigDecimal carbsG;
    private final BigDecimal fatG;

    public DailyTotalsResponse(int calories, BigDecimal proteinG, BigDecimal carbsG, BigDecimal fatG) {
        this.calories = calories;
        this.proteinG = proteinG;
        this.carbsG = carbsG;
        this.fatG = fatG;
    }
}