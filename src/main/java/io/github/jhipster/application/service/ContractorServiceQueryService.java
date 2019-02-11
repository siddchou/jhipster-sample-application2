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

import io.github.jhipster.application.domain.ContractorService;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.ContractorServiceRepository;
import io.github.jhipster.application.repository.search.ContractorServiceSearchRepository;
import io.github.jhipster.application.service.dto.ContractorServiceCriteria;
import io.github.jhipster.application.service.dto.ContractorServiceDTO;
import io.github.jhipster.application.service.mapper.ContractorServiceMapper;

/**
 * Service for executing complex queries for ContractorService entities in the database.
 * The main input is a {@link ContractorServiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContractorServiceDTO} or a {@link Page} of {@link ContractorServiceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContractorServiceQueryService extends QueryService<ContractorService> {

    private final Logger log = LoggerFactory.getLogger(ContractorServiceQueryService.class);

    private final ContractorServiceRepository contractorServiceRepository;

    private final ContractorServiceMapper contractorServiceMapper;

    private final ContractorServiceSearchRepository contractorServiceSearchRepository;

    public ContractorServiceQueryService(ContractorServiceRepository contractorServiceRepository, ContractorServiceMapper contractorServiceMapper, ContractorServiceSearchRepository contractorServiceSearchRepository) {
        this.contractorServiceRepository = contractorServiceRepository;
        this.contractorServiceMapper = contractorServiceMapper;
        this.contractorServiceSearchRepository = contractorServiceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ContractorServiceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContractorServiceDTO> findByCriteria(ContractorServiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContractorService> specification = createSpecification(criteria);
        return contractorServiceMapper.toDto(contractorServiceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContractorServiceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContractorServiceDTO> findByCriteria(ContractorServiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContractorService> specification = createSpecification(criteria);
        return contractorServiceRepository.findAll(specification, page)
            .map(contractorServiceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContractorServiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContractorService> specification = createSpecification(criteria);
        return contractorServiceRepository.count(specification);
    }

    /**
     * Function to convert ContractorServiceCriteria to a {@link Specification}
     */
    private Specification<ContractorService> createSpecification(ContractorServiceCriteria criteria) {
        Specification<ContractorService> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ContractorService_.id));
            }
            if (criteria.getIsVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getIsVerified(), ContractorService_.isVerified));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), ContractorService_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), ContractorService_.endDate));
            }
            if (criteria.getContractorId() != null) {
                specification = specification.and(buildSpecification(criteria.getContractorId(),
                    root -> root.join(ContractorService_.contractor, JoinType.LEFT).get(Contractor_.id)));
            }
            if (criteria.getServicesId() != null) {
                specification = specification.and(buildSpecification(criteria.getServicesId(),
                    root -> root.join(ContractorService_.services, JoinType.LEFT).get(Services_.id)));
            }
        }
        return specification;
    }
}
