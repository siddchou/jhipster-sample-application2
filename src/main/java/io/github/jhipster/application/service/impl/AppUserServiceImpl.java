package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.AppUserService;
import io.github.jhipster.application.domain.AppUser;
import io.github.jhipster.application.repository.AppUserRepository;
import io.github.jhipster.application.repository.search.AppUserSearchRepository;
import io.github.jhipster.application.service.dto.AppUserDTO;
import io.github.jhipster.application.service.mapper.AppUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AppUser.
 */
@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);

    private final AppUserRepository appUserRepository;

    private final AppUserMapper appUserMapper;

    private final AppUserSearchRepository appUserSearchRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository, AppUserMapper appUserMapper, AppUserSearchRepository appUserSearchRepository) {
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
        this.appUserSearchRepository = appUserSearchRepository;
    }

    /**
     * Save a appUser.
     *
     * @param appUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AppUserDTO save(AppUserDTO appUserDTO) {
        log.debug("Request to save AppUser : {}", appUserDTO);
        AppUser appUser = appUserMapper.toEntity(appUserDTO);
        appUser = appUserRepository.save(appUser);
        AppUserDTO result = appUserMapper.toDto(appUser);
        appUserSearchRepository.save(appUser);
        return result;
    }

    /**
     * Get all the appUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AppUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppUsers");
        return appUserRepository.findAll(pageable)
            .map(appUserMapper::toDto);
    }


    /**
     * Get one appUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AppUserDTO> findOne(Long id) {
        log.debug("Request to get AppUser : {}", id);
        return appUserRepository.findById(id)
            .map(appUserMapper::toDto);
    }

    /**
     * Delete the appUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppUser : {}", id);        appUserRepository.deleteById(id);
        appUserSearchRepository.deleteById(id);
    }

    /**
     * Search for the appUser corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AppUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AppUsers for query {}", query);
        return appUserSearchRepository.search(queryStringQuery(query), pageable)
            .map(appUserMapper::toDto);
    }
}
