package com.meli.challenge.mutants.controller;

import com.meli.challenge.mutants.controller.impl.StatsController;
import com.meli.challenge.mutants.dto.DNAStats;
import com.meli.challenge.mutants.mapper.IStatsMapper;
import com.meli.challenge.mutants.rs.response.StatsResponse;
import com.meli.challenge.mutants.service.IMutantStatisticService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class StatsControllerTest {

    @Mock
    private IMutantStatisticService mutantStatisticService;
    @Mock
    private IStatsMapper statsMapper;

    private IStatsController statsController;

    @BeforeEach
    void setUp() {
        statsController = new StatsController(mutantStatisticService, statsMapper);
    }

    @Test
    void getStats_Ok() {
        DNAStats dnaStats = DNAStats.builder().build();

        Mockito.when(mutantStatisticService.getStats()).thenReturn(dnaStats);
        Mockito.when(statsMapper.dnaStatsToResponse(dnaStats)).thenReturn(new StatsResponse());

        ResponseEntity<StatsResponse> response = statsController.getStats();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Mockito.verify(mutantStatisticService).getStats();
        Mockito.verify(statsMapper).dnaStatsToResponse(dnaStats);
    }
}