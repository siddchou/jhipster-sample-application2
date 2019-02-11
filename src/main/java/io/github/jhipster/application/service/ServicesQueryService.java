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

import io.github.jhipster.application.domain.Services;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.ServicesRepository;
import io.github.jhipster.application.repository.search.ServicesSearchRepository;
import io.github.jhipster.application.service.dto.ServicesCriteria;
import io.github.jhipster.application.service.dto.ServicesDTO;
import io.github.jhipster.application.service.mapper.ServicesMapper;

/**
 * Service for executing complex queries for Services entities in the database.
 * The main input is a {@link ServicesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServicesDTO} or a {@link Page} of {@link ServicesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServicesQueryService extends QueryService<Services> {

    private final Logger log = LoggerFactory.getLogger(ServicesQueryService.class);

    private final ServicesRepository servicesRepository;

    private final ServicesMapper servicesMapper;

    private final ServicesSearchRepository servicesSearchRepository;

    public ServicesQueryService(ServicesRepository servicesRepository, ServicesMapper servicesMapper, ServicesSearchRepository servicesSearchRepository) {
        this.servicesRepository = servicesRepository;
        this.servicesMapper = servicesMapper;
        this.servicesSearchRepository = servicesSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ServicesDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServicesDTO> findByCriteria(ServicesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Services> specification = createSpecification(criteria);
        return servicesMapper.toDto(servicesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServicesDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServicesDTO> findByCriteria(ServicesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Services> specification = createSpecification(criteria);
        return servicesRepository.findAll(specification, page)
            .map(servicesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServicesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Services> specification = createSpecification(criteria);
        return servicesRepository.count(specification);
    }

    /**
     * Function to convert ServicesCriteria to a {@link Specification}
     */
    private Specification<Services> createSpecification(ServicesCriteria criteria) {
        Specification<Services> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Services_.id));
            }
            if (criteria.getServiceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceName(), Services_.serviceName));
            }
            if (criteria.getServiceDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceDescription(), Services_.serviceDescription));
            }
            if (criteria.getRateId() != null) {
                specification = specification.and(buildSpecification(criteria.getRateId(),
                    root -> root.join(Services_.rate, JoinType.LEFT).get(Rate_.id)));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryId(),
                    root -> root.join(Services_.category, JoinType.LEFT).get(Category_.id)));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationId(),
                    root -> root.join(Services_.location, JoinType.LEFT).get(Location_.id)));
            }
        }
        return specification;
    }
}
