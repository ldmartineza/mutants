package com.meli.challenge.mutants.service;

/**
 * This interface will define the public capabilities required to validate a DNA sequence. The validation must follow
 * the rules defined to determine the sequence describes a human or mutant DNA.
 */
public interface IMutantValidatorService {

    /**
     * Method to validate if a DNA sequence belongs to a human or a mutant. The incoming array will describe a DNA sequence
     * split in rows, with each of the allowed bases available for the scanning. The sequence will be considered a mutant
     * DNA if there are more than 1 string that has the minimum length parameterized (length 4 by default). The found
     * strings can be horizontal, vertical or diagonal. Otherwise, the sequence will be considered human DNA
     * @param dna an array of strings representing the DNA sequence to scan and validate
     * @return a flag with the validation result, true for mutant, false otherwise
     */
    boolean isMutant(String[] dna);
}
