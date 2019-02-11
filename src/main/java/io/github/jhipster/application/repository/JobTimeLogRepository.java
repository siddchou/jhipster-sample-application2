package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.JobTimeLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JobTimeLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobTimeLogRepository extends JpaRepository<JobTimeLog, Long>, JpaSpecificationExecutor<JobTimeLog> {

}
