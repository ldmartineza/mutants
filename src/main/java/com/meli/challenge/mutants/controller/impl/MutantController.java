package com.meli.challenge.mutants.controller.impl;

import com.meli.challenge.mutants.controller.IMutantController;
import com.meli.challenge.mutants.rs.request.ValidateMutantRequest;
import com.meli.challenge.mutants.service.IMutantValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/mutant")
public class MutantController implements IMutantController {

    private final IMutantValidatorService mutantValidatorService;

    @Autowired
    public MutantController(IMutantValidatorService mutantValidatorService) {
        this.mutantValidatorService = mutantValidatorService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> checkMutantDNA(@RequestBody @Valid ValidateMutantRequest validateMutantRequest) {
        String[] dnaArray = validateMutantRequest.getDna().toArray(new String[0]);
        return ResponseEntity.status(mutantValidatorService.isMutant(dnaArray) ? HttpStatus.OK : HttpStatus.FORBIDDEN)
                .build();
    }
}
