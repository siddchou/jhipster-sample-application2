package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Contractor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Contractor entity.
 */
public interface ContractorSearchRepository extends ElasticsearchRepository<Contractor, Long> {
}
