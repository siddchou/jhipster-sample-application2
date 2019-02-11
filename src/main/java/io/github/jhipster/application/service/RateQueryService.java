package io.github.jhipster.application.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.github.jhipster.application.domain.Rate;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.RateRepository;
import io.github.jhipster.application.repository.search.RateSearchRepository;
import io.github.jhipster.application.service.dto.RateCriteria;
import io.github.jhipster.application.service.dto.RateDTO;
import io.github.jhipster.application.service.mapper.RateMapper;

/**
 * Service for executing complex queries for Rate entities in the database.
 * The main input is a {@link RateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RateDTO} or a {@link Page} of {@link RateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RateQueryService extends QueryService<Rate> {

    private final Logger log = LoggerFactory.getLogger(RateQueryService.class);

    private final RateRepository rateRepository;

    private final RateMapper rateMapper;

    private final RateSearchRepository rateSearchRepository;

    public RateQueryService(RateRepository rateRepository, RateMapper rateMapper, RateSearchRepository rateSearchRepository) {
        this.rateRepository = rateRepository;
        this.rateMapper = rateMapper;
        this.rateSearchRepository = rateSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RateDTO> findByCriteria(RateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Rate> specification = createSpecification(criteria);
        return rateMapper.toDto(rateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RateDTO> findByCriteria(RateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Rate> specification = createSpecification(criteria);
        return rateRepository.findAll(specification, page)
            .map(rateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Rate> specification = createSpecification(criteria);
        return rateRepository.count(specification);
    }

    /**
     * Function to convert RateCriteria to a {@link Specification}
     */
    private Specification<Rate> createSpecification(RateCriteria criteria) {
        Specification<Rate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Rate_.id));
            }
            if (criteria.getRateName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRateName(), Rate_.rateName));
            }
            if (criteria.getRateDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRateDesc(), Rate_.rateDesc));
            }
            if (criteria.getFullRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFullRate(), Rate_.fullRate));
            }
            if (criteria.getIdleRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdleRate(), Rate_.idleRate));
            }
        }
        return specification;
    }
}
