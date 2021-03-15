package com.meli.challenge.mutants.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DNAStats {
    private long countMutantDna;
    private long countHumanDna;
    private double ratio;
}
