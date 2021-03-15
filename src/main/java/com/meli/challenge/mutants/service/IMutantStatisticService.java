package com.meli.challenge.mutants.service;

import com.meli.challenge.mutants.dto.DNAStats;

public interface IMutantStatisticService {

    DNAStats getStats();

    void addStat(String[] dna, boolean result);
}
