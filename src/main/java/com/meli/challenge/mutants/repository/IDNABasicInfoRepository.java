package com.meli.challenge.mutants.repository;

import com.meli.challenge.mutants.entities.DNABasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDNABasicInfoRepository extends JpaRepository<DNABasicInfo, Long> {
}
