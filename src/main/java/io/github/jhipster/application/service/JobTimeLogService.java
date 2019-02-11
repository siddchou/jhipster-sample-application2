package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.JobTimeLogDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing JobTimeLog.
 */
public interface JobTimeLogService {

    /**
     * Save a jobTimeLog.
     *
     * @param jobTimeLogDTO the entity to save
     * @return the persisted entity
     */
    JobTimeLogDTO save(JobTimeLogDTO jobTimeLogDTO);

    /**
     * Get all the jobTimeLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<JobTimeLogDTO> findAll(Pageable pageable);


    /**
     * Get the "id" jobTimeLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<JobTimeLogDTO> findOne(Long id);

    /**
     * Delete the "id" jobTimeLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the jobTimeLog corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<JobTimeLogDTO> search(String query, Pageable pageable);
}
