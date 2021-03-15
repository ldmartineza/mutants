package com.meli.challenge.mutants.entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "dna_basic_info")
@Data
@Builder
public class DNABasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String dnaSequence;

    private boolean mutant;
}
