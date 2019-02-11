package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.AppUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AppUser.
 */
public interface AppUserService {

    /**
     * Save a appUser.
     *
     * @param appUserDTO the entity to save
     * @return the persisted entity
     */
    AppUserDTO save(AppUserDTO appUserDTO);

    /**
     * Get all the appUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AppUserDTO> findAll(Pageable pageable);


    /**
     * Get the "id" appUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AppUserDTO> findOne(Long id);

    /**
     * Delete the "id" appUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the appUser corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AppUserDTO> search(String query, Pageable pageable);
}
