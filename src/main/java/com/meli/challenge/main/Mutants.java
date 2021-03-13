package com.meli.challenge.main;

import com.meli.challenge.service.IMutantValidatorService;
import com.meli.challenge.service.impl.MutantValidatorService;

public class Mutants {

    public static void main (String[] args) {
        IMutantValidatorService mutantValidatorService = new MutantValidatorService();

        // It should be true
        String[] dnaMutant = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        System.out.println(mutantValidatorService.isMutant(dnaMutant));

        // It should be false
        String[] dnaNotMutant = {"ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"};
        System.out.println(mutantValidatorService.isMutant(dnaNotMutant));
    }
}
