package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ContractorService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContractorService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractorServiceRepository extends JpaRepository<ContractorService, Long> {

}
