package com.nutrition.nutrition_voice_tracker_api.nutrition.dto;


import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class DailySummaryRow {
    private final String date; // YYYY-MM-DD (UTC day)
    private final int calories;
    private final BigDecimal proteinG;
    private final BigDecimal carbsG;
    private final BigDecimal fatG;

    public DailySummaryRow(String date, int calories, BigDecimal proteinG, BigDecimal carbsG, BigDecimal fatG) {
        this.date = date;
        this.calories = calories;
        this.proteinG = proteinG;
        this.carbsG = carbsG;
        this.fatG = fatG;
    }
}