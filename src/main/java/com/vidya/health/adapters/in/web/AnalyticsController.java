package com.vidya.health.adapters.in.web;

import com.vidya.health.application.usecases.AnalyticsUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsUseCase analyticsUseCase;

    public AnalyticsController(AnalyticsUseCase analyticsUseCase) {
        this.analyticsUseCase = analyticsUseCase;
    }

    @GetMapping("/appointments-by-doctor")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Map<String, Object>> appointmentsByDoctor(@RequestParam OffsetDateTime from, @RequestParam OffsetDateTime to) {
        return analyticsUseCase.appointmentCountsByDoctor(from, to);
    }
}
