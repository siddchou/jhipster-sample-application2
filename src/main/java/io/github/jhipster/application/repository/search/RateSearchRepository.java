package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Rate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Rate entity.
 */
public interface RateSearchRepository extends ElasticsearchRepository<Rate, Long> {
}
