package com.meli.challenge.mutants.service;

import com.meli.challenge.mutants.dto.DNAStats;

/**
 * This interface will define the required services to handle the statistics around mutants. This will serve as a service
 * layer to interact with the mutants stored/to be stored in the database
 */
public interface IMutantStatisticService {

    /**
     * Method to retrieve the statistics on the mutants analyzed. This will contain information regarding human DNA, mutant
     * DNA and the ratio of these two DNA types. The data will be wrapped in a {@link DNAStats} instance with the data
     * @return a {@link DNAStats} instance with the stats gathered from the storage layer
     */
    DNAStats getStats();

    /**
     * Method to add a DNA to the statistics. This will interact with the storage layer defined (in this case, a simple
     * database) to store the DNA from the parameter. The boolean result will be used to mark the scanned DNA as mutant or
     * human, and keep the record of that scan. This method must ensure the DNA sequence is unique per each record stored
     * @param dna The array containing the DNA sequence to be stored
     * @param result A flag representing the scanning result: true for mutant, false for human
     */
    void addStat(String[] dna, boolean result);
}
