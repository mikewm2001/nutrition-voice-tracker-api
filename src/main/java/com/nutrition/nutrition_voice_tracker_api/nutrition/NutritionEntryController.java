package com.nutrition.nutrition_voice_tracker_api.nutrition;

import com.nutrition.nutrition_voice_tracker_api.domain.NutritionEntry;
import com.nutrition.nutrition_voice_tracker_api.nutrition.dto.CreateNutritionEntryRequest;
import com.nutrition.nutrition_voice_tracker_api.nutrition.dto.NutritionEntryResponse;
import com.nutrition.nutrition_voice_tracker_api.repository.NutritionEntryRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/nutrition-entries")
public class NutritionEntryController {

    private final NutritionEntryRepository repo;

    public NutritionEntryController(NutritionEntryRepository repo) {
        this.repo = repo;
    }

    private UUID currentUserId() {
        // because JwtAuthFilter sets principal = userId string
        String principal = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();
        return UUID.fromString(principal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NutritionEntryResponse create(@Valid @RequestBody CreateNutritionEntryRequest req) {
        UUID userId = currentUserId();

        NutritionEntry entry = new NutritionEntry(
                userId,
                req.getLabel(),
                req.getRawInput(),
                req.getSource(),
                req.getExternalFoodId(),
                req.getAmount(),
                req.getUnit(),
                req.getCalories(),
                req.getProteinG(),
                req.getCarbsG(),
                req.getFatG(),
                req.getLoggedAt()
        );

        return NutritionEntryResponse.from(repo.save(entry));
    }

    @GetMapping
    public List<NutritionEntryResponse> list(
            @RequestParam(defaultValue = "30") int limit,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        UUID userId = currentUserId();
        var pageable = PageRequest.of(0, Math.min(Math.max(limit, 1), 200));

        List<NutritionEntry> entries;

        if (from != null || to != null) {
            LocalDate fromDate = (from == null) ? LocalDate.of(1970, 1, 1) : from;
            LocalDate toDate = (to == null) ? LocalDate.of(3000, 1, 1) : to;

            Instant start = fromDate.atStartOfDay().toInstant(ZoneOffset.UTC);
            Instant endExclusive = toDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

            entries = repo.findByUserIdAndLoggedAtBetweenOrderByLoggedAtDesc(userId, start, endExclusive, pageable);
        } else {
            entries = repo.findByUserIdOrderByLoggedAtDesc(userId, pageable);
        }

        return entries.stream().map(NutritionEntryResponse::from).toList();
    }

    @GetMapping("/{id}")
    public NutritionEntryResponse getOne(@PathVariable UUID id) {
        UUID userId = currentUserId();
        NutritionEntry entry = repo.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return NutritionEntryResponse.from(entry);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        UUID userId = currentUserId();
        repo.deleteByIdAndUserId(id, userId);
    }
}