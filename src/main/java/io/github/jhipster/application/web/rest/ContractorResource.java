package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.ContractorService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ContractorDTO;
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
 * REST controller for managing Contractor.
 */
@RestController
@RequestMapping("/api")
public class ContractorResource {

    private final Logger log = LoggerFactory.getLogger(ContractorResource.class);

    private static final String ENTITY_NAME = "contractor";

    private final ContractorService contractorService;

    public ContractorResource(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    /**
     * POST  /contractors : Create a new contractor.
     *
     * @param contractorDTO the contractorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contractorDTO, or with status 400 (Bad Request) if the contractor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contractors")
    public ResponseEntity<ContractorDTO> createContractor(@RequestBody ContractorDTO contractorDTO) throws URISyntaxException {
        log.debug("REST request to save Contractor : {}", contractorDTO);
        if (contractorDTO.getId() != null) {
            throw new BadRequestAlertException("A new contractor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContractorDTO result = contractorService.save(contractorDTO);
        return ResponseEntity.created(new URI("/api/contractors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contractors : Updates an existing contractor.
     *
     * @param contractorDTO the contractorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contractorDTO,
     * or with status 400 (Bad Request) if the contractorDTO is not valid,
     * or with status 500 (Internal Server Error) if the contractorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contractors")
    public ResponseEntity<ContractorDTO> updateContractor(@RequestBody ContractorDTO contractorDTO) throws URISyntaxException {
        log.debug("REST request to update Contractor : {}", contractorDTO);
        if (contractorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContractorDTO result = contractorService.save(contractorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contractorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contractors : get all the contractors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contractors in body
     */
    @GetMapping("/contractors")
    public ResponseEntity<List<ContractorDTO>> getAllContractors(Pageable pageable) {
        log.debug("REST request to get a page of Contractors");
        Page<ContractorDTO> page = contractorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contractors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /contractors/:id : get the "id" contractor.
     *
     * @param id the id of the contractorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contractorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contractors/{id}")
    public ResponseEntity<ContractorDTO> getContractor(@PathVariable Long id) {
        log.debug("REST request to get Contractor : {}", id);
        Optional<ContractorDTO> contractorDTO = contractorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contractorDTO);
    }

    /**
     * DELETE  /contractors/:id : delete the "id" contractor.
     *
     * @param id the id of the contractorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contractors/{id}")
    public ResponseEntity<Void> deleteContractor(@PathVariable Long id) {
        log.debug("REST request to delete Contractor : {}", id);
        contractorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/contractors?query=:query : search for the contractor corresponding
     * to the query.
     *
     * @param query the query of the contractor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/contractors")
    public ResponseEntity<List<ContractorDTO>> searchContractors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Contractors for query {}", query);
        Page<ContractorDTO> page = contractorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/contractors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
