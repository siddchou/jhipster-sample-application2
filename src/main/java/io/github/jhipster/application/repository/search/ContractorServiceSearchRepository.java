package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ContractorService;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ContractorService entity.
 */
public interface ContractorServiceSearchRepository extends ElasticsearchRepository<ContractorService, Long> {
}
