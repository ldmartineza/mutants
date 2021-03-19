package com.meli.challenge.mutants.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.challenge.mutants.rs.request.ValidateMutantRequest;
import com.meli.challenge.mutants.service.IMutantValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MutantControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private IMutantValidatorService mutantValidatorService;

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @WithMockUser
    @Test
    void checkMutantDNA_ok() throws Exception {
        String[] dna = new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        ValidateMutantRequest request = new ValidateMutantRequest();
        request.setDna(Arrays.asList(dna));
        BDDMockito.given(mutantValidatorService.isMutant(dna)).willReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
                    .content(mapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithMockUser
    @Test
    void checkMutantDNA_forbidden() throws Exception {
        String[] dna = new String[]{"ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"};
        ValidateMutantRequest request = new ValidateMutantRequest();
        request.setDna(Arrays.asList(dna));
        BDDMockito.given(mutantValidatorService.isMutant(dna)).willReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
                    .content(mapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser
    @Test
    void checkMutantDNA_badRequest() throws Exception {
        ValidateMutantRequest request = new ValidateMutantRequest();
        request.setDna(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
                    .content(mapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}