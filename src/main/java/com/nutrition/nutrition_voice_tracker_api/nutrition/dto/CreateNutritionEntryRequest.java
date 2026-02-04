package com.nutrition.nutrition_voice_tracker_api.nutrition.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
public class CreateNutritionEntryRequest {

    @NotBlank
    private String label;

    private String rawInput;
    private String source;
    private String externalFoodId;

    private BigDecimal amount;
    private String unit;

    @NotNull
    private Integer calories;

    @NotNull
    private BigDecimal proteinG;

    @NotNull
    private BigDecimal carbsG;

    @NotNull
    private BigDecimal fatG;

    // optional: if omitted, server uses now()
    private Instant loggedAt;
}