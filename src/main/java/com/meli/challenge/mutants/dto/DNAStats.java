package com.meli.challenge.mutants.dto;

import lombok.Builder;
import lombok.Data;

/**
 * This DTO will define the required data to show on the stats endpoint. This will filled based in the rules
 */
@Data
@Builder
public class DNAStats {
    private long countMutantDna;
    private long countHumanDna;
    private double ratio;
}
