package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.UserAddressMap;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserAddressMap entity.
 */
public interface UserAddressMapSearchRepository extends ElasticsearchRepository<UserAddressMap, Long> {
}
