package com.meli.challenge.mutants.mapper;

import com.meli.challenge.mutants.dto.DNAStats;
import com.meli.challenge.mutants.rs.response.StatsResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Random;

class StatsMapperTest {

    private IStatsMapper statsMapper;

    @BeforeEach
    void setUp() {
        statsMapper = Mappers.getMapper(IStatsMapper.class);
    }

    @Test
    void dnaStatsToResponse_null() {
        StatsResponse mappedStats = statsMapper.dnaStatsToResponse(null);

        Assertions.assertThat(mappedStats).isNull();
    }

    @Test
    void dnaStatsToResponse_ok() {
        Random random = new Random();
        DNAStats sourceStats = DNAStats.builder()
                .ratio(random.nextDouble())
                .countHumanDna(random.nextLong())
                .countMutantDna(random.nextLong())
                .build();

        StatsResponse mappedStats = statsMapper.dnaStatsToResponse(sourceStats);

        Assertions.assertThat(mappedStats).isNotNull();
        Assertions.assertThat(mappedStats).usingRecursiveComparison().isEqualTo(sourceStats);
    }
}