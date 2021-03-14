package com.meli.challenge.mutants.mapper;

import com.meli.challenge.mutants.dto.DNAStats;
import com.meli.challenge.mutants.rs.response.StatsResponse;
import org.mapstruct.Mapper;

@Mapper
public interface IStatsMapper {

    StatsResponse dnaStatsToResponse(DNAStats stats);
}
