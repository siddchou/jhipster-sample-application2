package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.UserAddressMapDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserAddressMap.
 */
public interface UserAddressMapService {

    /**
     * Save a userAddressMap.
     *
     * @param userAddressMapDTO the entity to save
     * @return the persisted entity
     */
    UserAddressMapDTO save(UserAddressMapDTO userAddressMapDTO);

    /**
     * Get all the userAddressMaps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserAddressMapDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userAddressMap.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserAddressMapDTO> findOne(Long id);

    /**
     * Delete the "id" userAddressMap.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the userAddressMap corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserAddressMapDTO> search(String query, Pageable pageable);
}
