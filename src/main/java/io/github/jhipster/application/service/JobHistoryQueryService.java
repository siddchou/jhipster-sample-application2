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

import io.github.jhipster.application.domain.JobHistory;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.JobHistoryRepository;
import io.github.jhipster.application.repository.search.JobHistorySearchRepository;
import io.github.jhipster.application.service.dto.JobHistoryCriteria;
import io.github.jhipster.application.service.dto.JobHistoryDTO;
import io.github.jhipster.application.service.mapper.JobHistoryMapper;

/**
 * Service for executing complex queries for JobHistory entities in the database.
 * The main input is a {@link JobHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JobHistoryDTO} or a {@link Page} of {@link JobHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobHistoryQueryService extends QueryService<JobHistory> {

    private final Logger log = LoggerFactory.getLogger(JobHistoryQueryService.class);

    private final JobHistoryRepository jobHistoryRepository;

    private final JobHistoryMapper jobHistoryMapper;

    private final JobHistorySearchRepository jobHistorySearchRepository;

    public JobHistoryQueryService(JobHistoryRepository jobHistoryRepository, JobHistoryMapper jobHistoryMapper, JobHistorySearchRepository jobHistorySearchRepository) {
        this.jobHistoryRepository = jobHistoryRepository;
        this.jobHistoryMapper = jobHistoryMapper;
        this.jobHistorySearchRepository = jobHistorySearchRepository;
    }

    /**
     * Return a {@link List} of {@link JobHistoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobHistoryDTO> findByCriteria(JobHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JobHistory> specification = createSpecification(criteria);
        return jobHistoryMapper.toDto(jobHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JobHistoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JobHistoryDTO> findByCriteria(JobHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JobHistory> specification = createSpecification(criteria);
        return jobHistoryRepository.findAll(specification, page)
            .map(jobHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JobHistory> specification = createSpecification(criteria);
        return jobHistoryRepository.count(specification);
    }

    /**
     * Function to convert JobHistoryCriteria to a {@link Specification}
     */
    private Specification<JobHistory> createSpecification(JobHistoryCriteria criteria) {
        Specification<JobHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), JobHistory_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), JobHistory_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), JobHistory_.endDate));
            }
            if (criteria.getJobStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getJobStatus(), JobHistory_.jobStatus));
            }
            if (criteria.getPaymentId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentId(),
                    root -> root.join(JobHistory_.payment, JoinType.LEFT).get(Payment_.id)));
            }
            if (criteria.getContractorServiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getContractorServiceId(),
                    root -> root.join(JobHistory_.contractorService, JoinType.LEFT).get(ContractorService_.id)));
            }
            if (criteria.getUserAddressMapId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserAddressMapId(),
                    root -> root.join(JobHistory_.userAddressMap, JoinType.LEFT).get(UserAddressMap_.id)));
            }
            if (criteria.getJobTimeLogId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobTimeLogId(),
                    root -> root.join(JobHistory_.jobTimeLogs, JoinType.LEFT).get(JobTimeLog_.id)));
            }
        }
        return specification;
    }
}
