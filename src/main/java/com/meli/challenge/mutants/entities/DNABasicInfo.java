package com.meli.challenge.mutants.entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

/**
 * This entity is the representation on the database to store the information of a scanned DNA sequence. This will be
 * composed of simple fields to store the required information during the scanning process. This data will be used for
 * the stats endpoint later
 */
@Entity
@Table(name = "dna_basic_info")
@Data
@Builder
public class DNABasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The DNA sequence will be stored in plain string to minimize the complexity
     */
    private String dnaSequence;

    /**
     * Flag to store if the sequence was scanned/validated as mutant or not
     */
    private boolean mutant;
}
