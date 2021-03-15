package com.meli.challenge.mutants.entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dna_basic_info")
@Data
@Builder
public class DNABasicInfo {

    @Id
    @GeneratedValue
    private long id;

    private String dnaSequence;

    private boolean mutant;
}
