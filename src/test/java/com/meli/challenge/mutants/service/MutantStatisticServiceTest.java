package com.meli.challenge.mutants.service;

import com.meli.challenge.mutants.dto.DNAStats;
import com.meli.challenge.mutants.entities.DNABasicInfo;
import com.meli.challenge.mutants.repository.IDNABasicInfoRepository;
import com.meli.challenge.mutants.service.impl.MutantStatisticService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class MutantStatisticServiceTest {

    @Mock
    private IDNABasicInfoRepository dnaBasicInfoRepository;

    @Captor
    private ArgumentCaptor<Example<DNABasicInfo>> exampleArgumentCaptor;

    @Captor
    private ArgumentCaptor<DNABasicInfo> dnaBasicInfoArgumentCaptor;

    private IMutantStatisticService mutantStatisticService;

    @BeforeEach
    void setUp() {
        mutantStatisticService = new MutantStatisticService(dnaBasicInfoRepository);
    }

    @Test
    void getStats_mixedStats() {
        Mockito.when(dnaBasicInfoRepository.count()).thenReturn(140L);
        Mockito.when(dnaBasicInfoRepository.count(Mockito.any())).thenReturn(40L);

        DNAStats stats = mutantStatisticService.getStats();

        Assertions.assertThat(stats).isNotNull();
        Assertions.assertThat(stats.getCountMutantDna()).isEqualTo(40L);
        Assertions.assertThat(stats.getCountHumanDna()).isEqualTo(100L);
        Assertions.assertThat(stats.getRatio()).isEqualTo(0.4);
        Mockito.verify(dnaBasicInfoRepository).count();
        Mockito.verify(dnaBasicInfoRepository).count(exampleArgumentCaptor.capture());

        Example<DNABasicInfo> example = exampleArgumentCaptor.getValue();
        Assertions.assertThat(example).isNotNull();
        Assertions.assertThat(example.getProbe()).isNotNull();
        Assertions.assertThat(example.getProbe()).usingRecursiveComparison()
                .isEqualTo(DNABasicInfo.builder().mutant(true).build());
    }

    @Test
    void getStats_onlyMutants() {
        Mockito.when(dnaBasicInfoRepository.count()).thenReturn(140L);
        Mockito.when(dnaBasicInfoRepository.count(Mockito.any())).thenReturn(140L);

        DNAStats stats = mutantStatisticService.getStats();

        Assertions.assertThat(stats).isNotNull();
        Assertions.assertThat(stats.getCountMutantDna()).isEqualTo(140L);
        Assertions.assertThat(stats.getCountHumanDna()).isEqualTo(0L);
        Assertions.assertThat(stats.getRatio()).isEqualTo(140);
        Mockito.verify(dnaBasicInfoRepository).count();
        Mockito.verify(dnaBasicInfoRepository).count(exampleArgumentCaptor.capture());

        Example<DNABasicInfo> example = exampleArgumentCaptor.getValue();
        Assertions.assertThat(example).isNotNull();
        Assertions.assertThat(example.getProbe()).isNotNull();
        Assertions.assertThat(example.getProbe()).usingRecursiveComparison()
                .isEqualTo(DNABasicInfo.builder().mutant(true).build());
    }

    @Test
    void addStat_newRecord() {
        String[] dna = new String[]{"AACC", "AACC", "AACC", "AACC"};
        StringBuilder sb = new StringBuilder();
        Stream.of(dna).forEach(sb::append);

        Mockito.when(dnaBasicInfoRepository.count(Mockito.any())).thenReturn(0L);

        mutantStatisticService.addStat(dna, true);

        Mockito.verify(dnaBasicInfoRepository).count(exampleArgumentCaptor.capture());
        Mockito.verify(dnaBasicInfoRepository).saveAndFlush(dnaBasicInfoArgumentCaptor.capture());
        Example<DNABasicInfo> example = exampleArgumentCaptor.getValue();
        Assertions.assertThat(example).isNotNull();
        Assertions.assertThat(example.getProbe()).isNotNull();
        Assertions.assertThat(example.getProbe()).usingRecursiveComparison()
                .isEqualTo(DNABasicInfo.builder().dnaSequence(sb.toString()).build());
        DNABasicInfo dnaBasicInfo = dnaBasicInfoArgumentCaptor.getValue();
        Assertions.assertThat(dnaBasicInfo).isNotNull();
        Assertions.assertThat(dnaBasicInfo.isMutant()).isTrue();
        Assertions.assertThat(dnaBasicInfo.getDnaSequence()).isEqualTo(sb.toString());
    }

    @Test
    void addStat_existentRecord() {
        String[] dna = new String[]{"AACC", "AACC", "AACC", "AACC"};
        StringBuilder sb = new StringBuilder();
        Stream.of(dna).forEach(sb::append);

        Mockito.when(dnaBasicInfoRepository.count(Mockito.any())).thenReturn(1L);

        mutantStatisticService.addStat(dna, true);

        Mockito.verify(dnaBasicInfoRepository).count(exampleArgumentCaptor.capture());
        Mockito.verifyNoMoreInteractions(dnaBasicInfoRepository);
        Example<DNABasicInfo> example = exampleArgumentCaptor.getValue();
        Assertions.assertThat(example).isNotNull();
        Assertions.assertThat(example.getProbe()).isNotNull();
        Assertions.assertThat(example.getProbe()).usingRecursiveComparison()
                .isEqualTo(DNABasicInfo.builder().dnaSequence(sb.toString()).build());
    }
}