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

@RestController
@RequestMapping("/mutant")
public class MutantController implements IMutantController {

    @Autowired
    private IMutantValidatorService mutantValidatorService;

    @Override
    @PostMapping
    public ResponseEntity<Void> checkMutantDNA(@RequestBody ValidateMutantRequest validateMutantRequest) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
