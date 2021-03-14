package com.meli.challenge.mutants.service.impl;

import com.meli.challenge.mutants.dto.DNAStats;
import com.meli.challenge.mutants.service.IMutantStatisticService;
import org.springframework.stereotype.Service;

@Service
public class MutantStatisticService implements IMutantStatisticService {

    @Override
    public DNAStats getStats() {
        return DNAStats.builder().build();
    }
}
