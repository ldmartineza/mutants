package com.meli.challenge.mutants.repository;

import com.meli.challenge.mutants.entities.DNABasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface defines a Spring Data repository to interact with the database
 */
@Repository
public interface IDNABasicInfoRepository extends JpaRepository<DNABasicInfo, Long> {
}
