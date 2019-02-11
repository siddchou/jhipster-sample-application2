package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.RateService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.RateDTO;
import io.github.jhipster.application.service.dto.RateCriteria;
import io.github.jhipster.application.service.RateQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Rate.
 */
@RestController
@RequestMapping("/api")
public class RateResource {

    private final Logger log = LoggerFactory.getLogger(RateResource.class);

    private static final String ENTITY_NAME = "rate";

    private final RateService rateService;

    private final RateQueryService rateQueryService;

    public RateResource(RateService rateService, RateQueryService rateQueryService) {
        this.rateService = rateService;
        this.rateQueryService = rateQueryService;
    }

    /**
     * POST  /rates : Create a new rate.
     *
     * @param rateDTO the rateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rateDTO, or with status 400 (Bad Request) if the rate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rates")
    public ResponseEntity<RateDTO> createRate(@RequestBody RateDTO rateDTO) throws URISyntaxException {
        log.debug("REST request to save Rate : {}", rateDTO);
        if (rateDTO.getId() != null) {
            throw new BadRequestAlertException("A new rate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RateDTO result = rateService.save(rateDTO);
        return ResponseEntity.created(new URI("/api/rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rates : Updates an existing rate.
     *
     * @param rateDTO the rateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rateDTO,
     * or with status 400 (Bad Request) if the rateDTO is not valid,
     * or with status 500 (Internal Server Error) if the rateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rates")
    public ResponseEntity<RateDTO> updateRate(@RequestBody RateDTO rateDTO) throws URISyntaxException {
        log.debug("REST request to update Rate : {}", rateDTO);
        if (rateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RateDTO result = rateService.save(rateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rates : get all the rates.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of rates in body
     */
    @GetMapping("/rates")
    public ResponseEntity<List<RateDTO>> getAllRates(RateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Rates by criteria: {}", criteria);
        Page<RateDTO> page = rateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /rates/count : count all the rates.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/rates/count")
    public ResponseEntity<Long> countRates(RateCriteria criteria) {
        log.debug("REST request to count Rates by criteria: {}", criteria);
        return ResponseEntity.ok().body(rateQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /rates/:id : get the "id" rate.
     *
     * @param id the id of the rateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rates/{id}")
    public ResponseEntity<RateDTO> getRate(@PathVariable Long id) {
        log.debug("REST request to get Rate : {}", id);
        Optional<RateDTO> rateDTO = rateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rateDTO);
    }

    /**
     * DELETE  /rates/:id : delete the "id" rate.
     *
     * @param id the id of the rateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rates/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable Long id) {
        log.debug("REST request to delete Rate : {}", id);
        rateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/rates?query=:query : search for the rate corresponding
     * to the query.
     *
     * @param query the query of the rate search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/rates")
    public ResponseEntity<List<RateDTO>> searchRates(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Rates for query {}", query);
        Page<RateDTO> page = rateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/rates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
