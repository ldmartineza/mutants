package com.meli.challenge.mutants.controller;

import com.meli.challenge.mutants.controller.impl.MutantController;
import com.meli.challenge.mutants.rs.request.ValidateMutantRequest;
import com.meli.challenge.mutants.service.IMutantValidatorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class MutantControllerTest {

    @Mock
    private IMutantValidatorService mutantValidatorService;

    private IMutantController mutantController;

    @BeforeEach
    void setUp() {
        mutantController = new MutantController(mutantValidatorService);
    }

    @Test
    void checkMutantDNA_Ok() {
        ValidateMutantRequest request = new ValidateMutantRequest();
        request.setDna(Collections.singletonList("any_string"));
        String[] dna = request.getDna().toArray(new String[0]);
        Mockito.when(mutantValidatorService.isMutant(AdditionalMatchers.aryEq(dna))).thenReturn(true);

        ResponseEntity<Void> response = mutantController.checkMutantDNA(request);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(mutantValidatorService).isMutant(AdditionalMatchers.aryEq(dna));
    }

    @Test
    void checkMutantDNA_Forbidden() {
        ValidateMutantRequest request = new ValidateMutantRequest();
        request.setDna(Collections.singletonList("any_string"));
        String[] dna = request.getDna().toArray(new String[0]);
        Mockito.when(mutantValidatorService.isMutant(AdditionalMatchers.aryEq(dna))).thenReturn(false);

        ResponseEntity<Void> response = mutantController.checkMutantDNA(request);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        Mockito.verify(mutantValidatorService).isMutant(AdditionalMatchers.aryEq(dna));
    }
}