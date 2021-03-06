package com.meli.challenge.mutants.rs.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the definition of the response used in the GET /stats request. It uses a snake case strategy to ensure the final
 * JSON mapping follows the rules/structure defined in the exercise
 */
@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StatsResponse {

    @ApiModelProperty(value = "The amount of mutants scanned", required = true)
    private long countMutantDna;
    @ApiModelProperty(value = "The amount of humans scanned", required = true)
    private long countHumanDna;
    @ApiModelProperty(value = "The ratio between mutants and humans scanned", required = true)
    private double ratio;
}
