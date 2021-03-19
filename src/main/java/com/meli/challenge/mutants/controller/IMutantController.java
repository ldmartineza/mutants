package com.meli.challenge.mutants.controller;

import com.meli.challenge.mutants.rs.request.ValidateMutantRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

/**
 * This interface will contain the required capabilities to expose for /mutant endpoint
 */
public interface IMutantController {

    /**
     * Method to check the DNA sequence. This will validate the sequence based in the given rules. The result will determine
     * the response of the API. In case the sequence is a mutant DNA, it will return a 200 OK. Otherwise, it will return
     * a 403 FORBIDDEN
     * @param validateMutantRequest A Json representing the DNA to validate
     * @return 200 OK in case of mutant DNA, 403 FORBIDDEN otherwise
     */
    @ApiOperation("Operation to validate if the given DNA sequence belongs to a mutant or a human")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Not mutant"),
            @ApiResponse(code = 200, message = "Mutant")
    })
    ResponseEntity<Void> checkMutantDNA(@ApiParam(value = "The object containing the DNA to validate") ValidateMutantRequest validateMutantRequest);
}
