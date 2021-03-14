package com.meli.challenge.mutants.controller;

import com.meli.challenge.mutants.rs.request.ValidateMutantRequest;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface IMutantController {

    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Not mutant", response = Void.class),
            @ApiResponse(code = 200, message = "Mutant", response = Void.class)
    })
    ResponseEntity<Void> checkMutantDNA(ValidateMutantRequest validateMutantRequest);
}
