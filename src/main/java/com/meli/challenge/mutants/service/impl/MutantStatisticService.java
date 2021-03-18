package com.meli.challenge.mutants.service.impl;

import com.meli.challenge.mutants.dto.DNAStats;
import com.meli.challenge.mutants.entities.DNABasicInfo;
import com.meli.challenge.mutants.repository.IDNABasicInfoRepository;
import com.meli.challenge.mutants.service.IMutantStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class MutantStatisticService implements IMutantStatisticService {

    @Autowired
    private IDNABasicInfoRepository dnaBasicInfoRepository;

    @Override
    public DNAStats getStats() {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id", "dnaSequence");
        Example<DNABasicInfo> queryExample = Example.of(DNABasicInfo.builder().mutant(true).build(), matcher);

        long totalRecords = dnaBasicInfoRepository.count();
        long mutantRecords = dnaBasicInfoRepository.count(queryExample);
        long humanRecords = totalRecords - mutantRecords;
        double ratio = humanRecords != 0 ? (double) mutantRecords / (double) humanRecords : 0;

        return DNAStats.builder()
                .countHumanDna(humanRecords)
                .countMutantDna(mutantRecords)
                .ratio(ratio)
                .build();
    }

    @Override
    public void addStat(String[] dna, boolean result) {
        StringBuilder sb = new StringBuilder();
        Stream.of(dna).forEach(sb::append);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id", "mutant");
        Example<DNABasicInfo> queryExample = Example.of(DNABasicInfo.builder().dnaSequence(sb.toString()).build(), matcher);
        if (dnaBasicInfoRepository.count(queryExample) == 0) {
            DNABasicInfo record = DNABasicInfo.builder()
                    .mutant(result)
                    .dnaSequence(sb.toString())
                    .build();
            dnaBasicInfoRepository.saveAndFlush(record);
        }
    }
}
