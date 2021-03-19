package com.meli.challenge.mutants.mapper;

import com.meli.challenge.mutants.dto.DNAStats;
import com.meli.challenge.mutants.rs.response.StatsResponse;
import org.mapstruct.Mapper;

/**
 * This mapper will be used to convert the data from the stats DTOs to be used in the /stats endpoint. The mapper uses
 * MapStruct to define the mappings
 */
@Mapper
public interface IStatsMapper {

    /**
     * Method to map from {@link DNAStats} instance to {@link StatsResponse} instance. Since the classes contain the
     * same attributes, no more configuration is required
     * @param stats the {@link DNAStats} instance to be mapped
     * @return a {@link StatsResponse} instance with the mapped data
     */
    StatsResponse dnaStatsToResponse(DNAStats stats);
}
