package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.UserAddressMapService;
import io.github.jhipster.application.domain.UserAddressMap;
import io.github.jhipster.application.repository.UserAddressMapRepository;
import io.github.jhipster.application.repository.search.UserAddressMapSearchRepository;
import io.github.jhipster.application.service.dto.UserAddressMapDTO;
import io.github.jhipster.application.service.mapper.UserAddressMapMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserAddressMap.
 */
@Service
@Transactional
public class UserAddressMapServiceImpl implements UserAddressMapService {

    private final Logger log = LoggerFactory.getLogger(UserAddressMapServiceImpl.class);

    private final UserAddressMapRepository userAddressMapRepository;

    private final UserAddressMapMapper userAddressMapMapper;

    private final UserAddressMapSearchRepository userAddressMapSearchRepository;

    public UserAddressMapServiceImpl(UserAddressMapRepository userAddressMapRepository, UserAddressMapMapper userAddressMapMapper, UserAddressMapSearchRepository userAddressMapSearchRepository) {
        this.userAddressMapRepository = userAddressMapRepository;
        this.userAddressMapMapper = userAddressMapMapper;
        this.userAddressMapSearchRepository = userAddressMapSearchRepository;
    }

    /**
     * Save a userAddressMap.
     *
     * @param userAddressMapDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserAddressMapDTO save(UserAddressMapDTO userAddressMapDTO) {
        log.debug("Request to save UserAddressMap : {}", userAddressMapDTO);
        UserAddressMap userAddressMap = userAddressMapMapper.toEntity(userAddressMapDTO);
        userAddressMap = userAddressMapRepository.save(userAddressMap);
        UserAddressMapDTO result = userAddressMapMapper.toDto(userAddressMap);
        userAddressMapSearchRepository.save(userAddressMap);
        return result;
    }

    /**
     * Get all the userAddressMaps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserAddressMapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAddressMaps");
        return userAddressMapRepository.findAll(pageable)
            .map(userAddressMapMapper::toDto);
    }


    /**
     * Get one userAddressMap by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserAddressMapDTO> findOne(Long id) {
        log.debug("Request to get UserAddressMap : {}", id);
        return userAddressMapRepository.findById(id)
            .map(userAddressMapMapper::toDto);
    }

    /**
     * Delete the userAddressMap by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAddressMap : {}", id);        userAddressMapRepository.deleteById(id);
        userAddressMapSearchRepository.deleteById(id);
    }

    /**
     * Search for the userAddressMap corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserAddressMapDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserAddressMaps for query {}", query);
        return userAddressMapSearchRepository.search(queryStringQuery(query), pageable)
            .map(userAddressMapMapper::toDto);
    }
}
