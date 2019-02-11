package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Services;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Services entity.
 */
public interface ServicesSearchRepository extends ElasticsearchRepository<Services, Long> {
}
