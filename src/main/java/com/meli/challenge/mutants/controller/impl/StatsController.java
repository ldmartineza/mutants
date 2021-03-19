package com.meli.challenge.mutants.controller.impl;

import com.meli.challenge.mutants.controller.IStatsController;
import com.meli.challenge.mutants.mapper.IStatsMapper;
import com.meli.challenge.mutants.rs.response.StatsResponse;
import com.meli.challenge.mutants.service.IMutantStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController implements IStatsController {

    private final IMutantStatisticService mutantStatisticService;
    private final IStatsMapper statsMapper;

    @Autowired
    public StatsController(IMutantStatisticService mutantStatisticService, IStatsMapper statsMapper) {
        this.mutantStatisticService = mutantStatisticService;
        this.statsMapper = statsMapper;
    }

    @Override
    @GetMapping
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsMapper
                .dnaStatsToResponse(mutantStatisticService.getStats()));
    }
}
