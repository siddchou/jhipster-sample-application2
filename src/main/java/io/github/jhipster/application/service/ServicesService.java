package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ServicesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Services.
 */
public interface ServicesService {

    /**
     * Save a services.
     *
     * @param servicesDTO the entity to save
     * @return the persisted entity
     */
    ServicesDTO save(ServicesDTO servicesDTO);

    /**
     * Get all the services.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServicesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" services.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ServicesDTO> findOne(Long id);

    /**
     * Delete the "id" services.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the services corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServicesDTO> search(String query, Pageable pageable);
}
