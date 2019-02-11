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

import io.github.jhipster.application.domain.Contractor;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.ContractorRepository;
import io.github.jhipster.application.repository.search.ContractorSearchRepository;
import io.github.jhipster.application.service.dto.ContractorCriteria;
import io.github.jhipster.application.service.dto.ContractorDTO;
import io.github.jhipster.application.service.mapper.ContractorMapper;

/**
 * Service for executing complex queries for Contractor entities in the database.
 * The main input is a {@link ContractorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContractorDTO} or a {@link Page} of {@link ContractorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContractorQueryService extends QueryService<Contractor> {

    private final Logger log = LoggerFactory.getLogger(ContractorQueryService.class);

    private final ContractorRepository contractorRepository;

    private final ContractorMapper contractorMapper;

    private final ContractorSearchRepository contractorSearchRepository;

    public ContractorQueryService(ContractorRepository contractorRepository, ContractorMapper contractorMapper, ContractorSearchRepository contractorSearchRepository) {
        this.contractorRepository = contractorRepository;
        this.contractorMapper = contractorMapper;
        this.contractorSearchRepository = contractorSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ContractorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContractorDTO> findByCriteria(ContractorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Contractor> specification = createSpecification(criteria);
        return contractorMapper.toDto(contractorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContractorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContractorDTO> findByCriteria(ContractorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contractor> specification = createSpecification(criteria);
        return contractorRepository.findAll(specification, page)
            .map(contractorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContractorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Contractor> specification = createSpecification(criteria);
        return contractorRepository.count(specification);
    }

    /**
     * Function to convert ContractorCriteria to a {@link Specification}
     */
    private Specification<Contractor> createSpecification(ContractorCriteria criteria) {
        Specification<Contractor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Contractor_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Contractor_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Contractor_.lastName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Contractor_.email));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Contractor_.phoneNumber));
            }
            if (criteria.getHireDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHireDate(), Contractor_.hireDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Contractor_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Contractor_.endDate));
            }
        }
        return specification;
    }
}
