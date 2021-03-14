package com.meli.challenge.mutants.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DNAStats {
    private int countMutantDna;
    private int countHumanDna;
    private double ratio;
}
