package com.meli.challenge.mutants.service;

import com.meli.challenge.mutants.service.impl.MutantValidatorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MutantValidatorServiceTest {

    @Mock
    private IMutantStatisticService mutantStatisticService;

    private IMutantValidatorService mutantValidatorService;

    @BeforeEach
    void setUp() {
        mutantValidatorService = new MutantValidatorService(4, mutantStatisticService);
    }

    @Test
    void isMutant_nullInput() {
        boolean result = mutantValidatorService.isMutant(null);

        Assertions.assertThat(result).isFalse();
        Mockito.verifyNoInteractions(mutantStatisticService);
    }

    @Test
    void isMutant_wrongInput() {
        String[] dna = new String[]{"AACC"};
        boolean result = mutantValidatorService.isMutant(dna);

        Assertions.assertThat(result).isFalse();
        Mockito.verify(mutantStatisticService).addStat(dna, false);
    }

    @Test
    void isMutant_notNxNInput() {
        String[] dna = new String[]{"A", "T", "G", "C"};
        boolean result = mutantValidatorService.isMutant(dna);

        Assertions.assertThat(result).isFalse();
        Mockito.verify(mutantStatisticService).addStat(dna, false);
    }

    @Test
    void isMutant_wrongNxNInput() {
        String[] dna = new String[]{"ATG", "TGC", "GAT"};
        boolean result = mutantValidatorService.isMutant(dna);

        Assertions.assertThat(result).isFalse();
        Mockito.verify(mutantStatisticService).addStat(dna, false);
    }

    @Test
    void isMutant_mutant() {
        String[] dna = new String[]{"ATGCCA", "CAGAGC", "TTATGT", "AAAATT", "CTCTTA", "TCACTG"};
        boolean result = mutantValidatorService.isMutant(dna);

        Assertions.assertThat(result).isTrue();
        Mockito.verify(mutantStatisticService).addStat(dna, true);
    }

    @Test
    void isMutant_human() {
        String[] dna = new String[]{"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        boolean result = mutantValidatorService.isMutant(dna);

        Assertions.assertThat(result).isFalse();
        Mockito.verify(mutantStatisticService).addStat(dna, false);
    }
}