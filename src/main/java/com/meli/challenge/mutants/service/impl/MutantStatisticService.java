package com.meli.challenge.mutants.service.impl;

import com.meli.challenge.mutants.dto.DNAStats;
import com.meli.challenge.mutants.entities.DNABasicInfo;
import com.meli.challenge.mutants.repository.IDNABasicInfoRepository;
import com.meli.challenge.mutants.service.IMutantStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class MutantStatisticService implements IMutantStatisticService {

    @Autowired
    private IDNABasicInfoRepository dnaBasicInfoRepository;

    @Override
    public DNAStats getStats() {
        Example<DNABasicInfo> queryExample = Example.of(DNABasicInfo.builder().mutant(true).build());
        long totalRecords = dnaBasicInfoRepository.count();
        long mutantRecords = dnaBasicInfoRepository.count(queryExample);
        long humanRecords = totalRecords - mutantRecords;
        double ratio = totalRecords != 0 ? (double) mutantRecords / (double) totalRecords : 0;

        return DNAStats.builder()
                .countHumanDna(humanRecords)
                .countMutantDna(mutantRecords)
                .ratio(ratio)
                .build();
    }
}
