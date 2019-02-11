package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Rate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RateRepository extends JpaRepository<Rate, Long>, JpaSpecificationExecutor<Rate> {

}
