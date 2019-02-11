package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.JobTimeLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobTimeLog entity.
 */
public interface JobTimeLogSearchRepository extends ElasticsearchRepository<JobTimeLog, Long> {
}
