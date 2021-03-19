package com.meli.challenge.mutants.controller;

import com.meli.challenge.mutants.rs.response.StatsResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

/**
 * This interface will contain the required capabilities to expose for /stats endpoint
 */
public interface IStatsController {

    /**
     * Method to retrieve the stats for the current stored DNA sequences. The stats will describe the number of scanned DNA
     * for mutants, humans, and a ratio of the interaction between these two types of sequences
     * @return a {@link StatsResponse} instance with the stats information
     */
    @ApiOperation("Operation to retrieve the current stats of the stored DNA")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An object with the stats information", response = StatsResponse.class)
    })
    ResponseEntity<StatsResponse> getStats();
}
