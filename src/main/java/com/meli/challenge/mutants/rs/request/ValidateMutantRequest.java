package com.meli.challenge.mutants.rs.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * This is the definition of the request used in the POST /mutants request
 */
@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ValidateMutantRequest {

    @NotEmpty(message = "dna must contain records")
    @ApiModelProperty(value = "A list of strings representing the DNA to check", required = true)
    private List<String> dna;
}
