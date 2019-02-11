package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.RateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Rate.
 */
public interface RateService {

    /**
     * Save a rate.
     *
     * @param rateDTO the entity to save
     * @return the persisted entity
     */
    RateDTO save(RateDTO rateDTO);

    /**
     * Get all the rates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RateDTO> findAll(Pageable pageable);


    /**
     * Get the "id" rate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RateDTO> findOne(Long id);

    /**
     * Delete the "id" rate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the rate corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RateDTO> search(String query, Pageable pageable);
}
