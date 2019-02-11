package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.RateService;
import io.github.jhipster.application.domain.Rate;
import io.github.jhipster.application.repository.RateRepository;
import io.github.jhipster.application.repository.search.RateSearchRepository;
import io.github.jhipster.application.service.dto.RateDTO;
import io.github.jhipster.application.service.mapper.RateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Rate.
 */
@Service
@Transactional
public class RateServiceImpl implements RateService {

    private final Logger log = LoggerFactory.getLogger(RateServiceImpl.class);

    private final RateRepository rateRepository;

    private final RateMapper rateMapper;

    private final RateSearchRepository rateSearchRepository;

    public RateServiceImpl(RateRepository rateRepository, RateMapper rateMapper, RateSearchRepository rateSearchRepository) {
        this.rateRepository = rateRepository;
        this.rateMapper = rateMapper;
        this.rateSearchRepository = rateSearchRepository;
    }

    /**
     * Save a rate.
     *
     * @param rateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RateDTO save(RateDTO rateDTO) {
        log.debug("Request to save Rate : {}", rateDTO);
        Rate rate = rateMapper.toEntity(rateDTO);
        rate = rateRepository.save(rate);
        RateDTO result = rateMapper.toDto(rate);
        rateSearchRepository.save(rate);
        return result;
    }

    /**
     * Get all the rates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rates");
        return rateRepository.findAll(pageable)
            .map(rateMapper::toDto);
    }


    /**
     * Get one rate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RateDTO> findOne(Long id) {
        log.debug("Request to get Rate : {}", id);
        return rateRepository.findById(id)
            .map(rateMapper::toDto);
    }

    /**
     * Delete the rate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rate : {}", id);        rateRepository.deleteById(id);
        rateSearchRepository.deleteById(id);
    }

    /**
     * Search for the rate corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RateDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Rates for query {}", query);
        return rateSearchRepository.search(queryStringQuery(query), pageable)
            .map(rateMapper::toDto);
    }
}
