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

    @Autowired
    private IMutantStatisticService mutantStatisticService;

    @Autowired
    private IStatsMapper statsMapper;

    @Override
    @GetMapping
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsMapper
                .dnaStatsToResponse(mutantStatisticService.getStats()));
    }
}
