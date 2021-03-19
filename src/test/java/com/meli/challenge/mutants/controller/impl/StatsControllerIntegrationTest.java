package com.meli.challenge.mutants.controller.impl;

import com.meli.challenge.mutants.dto.DNAStats;
import com.meli.challenge.mutants.service.IMutantStatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatsControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private IMutantStatisticService mutantStatisticService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getStats_ok() throws Exception {
        DNAStats stats = DNAStats.builder()
                .countMutantDna(40L)
                .countHumanDna(100L)
                .ratio(0.4)
                .build();
        BDDMockito.given(mutantStatisticService.getStats()).willReturn(stats);

        mockMvc.perform(MockMvcRequestBuilders.get("/stats"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count_mutant_dna").value(stats.getCountMutantDna()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.count_human_dna").value(stats.getCountHumanDna()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ratio").value(stats.getRatio()));
    }
}