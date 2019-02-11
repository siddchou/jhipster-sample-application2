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

import io.github.jhipster.application.domain.JobTimeLog;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.JobTimeLogRepository;
import io.github.jhipster.application.repository.search.JobTimeLogSearchRepository;
import io.github.jhipster.application.service.dto.JobTimeLogCriteria;
import io.github.jhipster.application.service.dto.JobTimeLogDTO;
import io.github.jhipster.application.service.mapper.JobTimeLogMapper;

/**
 * Service for executing complex queries for JobTimeLog entities in the database.
 * The main input is a {@link JobTimeLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JobTimeLogDTO} or a {@link Page} of {@link JobTimeLogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobTimeLogQueryService extends QueryService<JobTimeLog> {

    private final Logger log = LoggerFactory.getLogger(JobTimeLogQueryService.class);

    private final JobTimeLogRepository jobTimeLogRepository;

    private final JobTimeLogMapper jobTimeLogMapper;

    private final JobTimeLogSearchRepository jobTimeLogSearchRepository;

    public JobTimeLogQueryService(JobTimeLogRepository jobTimeLogRepository, JobTimeLogMapper jobTimeLogMapper, JobTimeLogSearchRepository jobTimeLogSearchRepository) {
        this.jobTimeLogRepository = jobTimeLogRepository;
        this.jobTimeLogMapper = jobTimeLogMapper;
        this.jobTimeLogSearchRepository = jobTimeLogSearchRepository;
    }

    /**
     * Return a {@link List} of {@link JobTimeLogDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobTimeLogDTO> findByCriteria(JobTimeLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JobTimeLog> specification = createSpecification(criteria);
        return jobTimeLogMapper.toDto(jobTimeLogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JobTimeLogDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JobTimeLogDTO> findByCriteria(JobTimeLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JobTimeLog> specification = createSpecification(criteria);
        return jobTimeLogRepository.findAll(specification, page)
            .map(jobTimeLogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobTimeLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JobTimeLog> specification = createSpecification(criteria);
        return jobTimeLogRepository.count(specification);
    }

    /**
     * Function to convert JobTimeLogCriteria to a {@link Specification}
     */
    private Specification<JobTimeLog> createSpecification(JobTimeLogCriteria criteria) {
        Specification<JobTimeLog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), JobTimeLog_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), JobTimeLog_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), JobTimeLog_.endDate));
            }
            if (criteria.getIsValidated() != null) {
                specification = specification.and(buildSpecification(criteria.getIsValidated(), JobTimeLog_.isValidated));
            }
            if (criteria.getJobHistoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobHistoryId(),
                    root -> root.join(JobTimeLog_.jobHistory, JoinType.LEFT).get(JobHistory_.id)));
            }
        }
        return specification;
    }
}
