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

import io.github.jhipster.application.domain.UserAddressMap;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.UserAddressMapRepository;
import io.github.jhipster.application.repository.search.UserAddressMapSearchRepository;
import io.github.jhipster.application.service.dto.UserAddressMapCriteria;
import io.github.jhipster.application.service.dto.UserAddressMapDTO;
import io.github.jhipster.application.service.mapper.UserAddressMapMapper;

/**
 * Service for executing complex queries for UserAddressMap entities in the database.
 * The main input is a {@link UserAddressMapCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserAddressMapDTO} or a {@link Page} of {@link UserAddressMapDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserAddressMapQueryService extends QueryService<UserAddressMap> {

    private final Logger log = LoggerFactory.getLogger(UserAddressMapQueryService.class);

    private final UserAddressMapRepository userAddressMapRepository;

    private final UserAddressMapMapper userAddressMapMapper;

    private final UserAddressMapSearchRepository userAddressMapSearchRepository;

    public UserAddressMapQueryService(UserAddressMapRepository userAddressMapRepository, UserAddressMapMapper userAddressMapMapper, UserAddressMapSearchRepository userAddressMapSearchRepository) {
        this.userAddressMapRepository = userAddressMapRepository;
        this.userAddressMapMapper = userAddressMapMapper;
        this.userAddressMapSearchRepository = userAddressMapSearchRepository;
    }

    /**
     * Return a {@link List} of {@link UserAddressMapDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserAddressMapDTO> findByCriteria(UserAddressMapCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserAddressMap> specification = createSpecification(criteria);
        return userAddressMapMapper.toDto(userAddressMapRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserAddressMapDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserAddressMapDTO> findByCriteria(UserAddressMapCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserAddressMap> specification = createSpecification(criteria);
        return userAddressMapRepository.findAll(specification, page)
            .map(userAddressMapMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserAddressMapCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserAddressMap> specification = createSpecification(criteria);
        return userAddressMapRepository.count(specification);
    }

    /**
     * Function to convert UserAddressMapCriteria to a {@link Specification}
     */
    private Specification<UserAddressMap> createSpecification(UserAddressMapCriteria criteria) {
        Specification<UserAddressMap> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserAddressMap_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), UserAddressMap_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), UserAddressMap_.endDate));
            }
            if (criteria.getAppUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getAppUserId(),
                    root -> root.join(UserAddressMap_.appUser, JoinType.LEFT).get(AppUser_.id)));
            }
            if (criteria.getAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressId(),
                    root -> root.join(UserAddressMap_.address, JoinType.LEFT).get(Address_.id)));
            }
        }
        return specification;
    }
}
