package com.meli.challenge.mutants.controller;

import com.meli.challenge.mutants.rs.response.StatsResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface IStatsController {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An object with the stats information", response = StatsResponse.class)
    })
    ResponseEntity<StatsResponse> getStats();
}
