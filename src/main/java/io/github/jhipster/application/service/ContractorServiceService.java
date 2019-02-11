package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ContractorServiceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ContractorService.
 */
public interface ContractorServiceService {

    /**
     * Save a contractorService.
     *
     * @param contractorServiceDTO the entity to save
     * @return the persisted entity
     */
    ContractorServiceDTO save(ContractorServiceDTO contractorServiceDTO);

    /**
     * Get all the contractorServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ContractorServiceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" contractorService.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ContractorServiceDTO> findOne(Long id);

    /**
     * Delete the "id" contractorService.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the contractorService corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ContractorServiceDTO> search(String query, Pageable pageable);
}
