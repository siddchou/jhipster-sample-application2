package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ServicesService;
import io.github.jhipster.application.domain.Services;
import io.github.jhipster.application.repository.ServicesRepository;
import io.github.jhipster.application.repository.search.ServicesSearchRepository;
import io.github.jhipster.application.service.dto.ServicesDTO;
import io.github.jhipster.application.service.mapper.ServicesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Services.
 */
@Service
@Transactional
public class ServicesServiceImpl implements ServicesService {

    private final Logger log = LoggerFactory.getLogger(ServicesServiceImpl.class);

    private final ServicesRepository servicesRepository;

    private final ServicesMapper servicesMapper;

    private final ServicesSearchRepository servicesSearchRepository;

    public ServicesServiceImpl(ServicesRepository servicesRepository, ServicesMapper servicesMapper, ServicesSearchRepository servicesSearchRepository) {
        this.servicesRepository = servicesRepository;
        this.servicesMapper = servicesMapper;
        this.servicesSearchRepository = servicesSearchRepository;
    }

    /**
     * Save a services.
     *
     * @param servicesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServicesDTO save(ServicesDTO servicesDTO) {
        log.debug("Request to save Services : {}", servicesDTO);
        Services services = servicesMapper.toEntity(servicesDTO);
        services = servicesRepository.save(services);
        ServicesDTO result = servicesMapper.toDto(services);
        servicesSearchRepository.save(services);
        return result;
    }

    /**
     * Get all the services.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServicesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        return servicesRepository.findAll(pageable)
            .map(servicesMapper::toDto);
    }


    /**
     * Get one services by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServicesDTO> findOne(Long id) {
        log.debug("Request to get Services : {}", id);
        return servicesRepository.findById(id)
            .map(servicesMapper::toDto);
    }

    /**
     * Delete the services by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Services : {}", id);        servicesRepository.deleteById(id);
        servicesSearchRepository.deleteById(id);
    }

    /**
     * Search for the services corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServicesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Services for query {}", query);
        return servicesSearchRepository.search(queryStringQuery(query), pageable)
            .map(servicesMapper::toDto);
    }
}
