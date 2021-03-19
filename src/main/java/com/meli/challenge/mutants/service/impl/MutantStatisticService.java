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
        // To retrieve the stats, the filter is applied to just match the mutant flag true and ignore the other fields
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
        // The DNA is converted to a string by using streams to traverse the array and a StringBuilder to append values
        StringBuilder sb = new StringBuilder();
        Stream.of(dna).forEach(sb::append);

        // To ensure each DNA sequence is unique during storage, the filter is applied to match the same DNA string and ignore the other fields
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id", "mutant");
        Example<DNABasicInfo> queryExample = Example.of(DNABasicInfo.builder().dnaSequence(sb.toString()).build(), matcher);
        // In case there are no records for the given DNA string, this is stored into the database
        if (dnaBasicInfoRepository.count(queryExample) == 0) {
            DNABasicInfo record = DNABasicInfo.builder()
                    .mutant(result)
                    .dnaSequence(sb.toString())
                    .build();
            dnaBasicInfoRepository.saveAndFlush(record);
        }
    }
}
