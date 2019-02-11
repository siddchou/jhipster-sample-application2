package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.ContractorServiceService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ContractorServiceDTO;
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
 * REST controller for managing ContractorService.
 */
@RestController
@RequestMapping("/api")
public class ContractorServiceResource {

    private final Logger log = LoggerFactory.getLogger(ContractorServiceResource.class);

    private static final String ENTITY_NAME = "contractorService";

    private final ContractorServiceService contractorServiceService;

    public ContractorServiceResource(ContractorServiceService contractorServiceService) {
        this.contractorServiceService = contractorServiceService;
    }

    /**
     * POST  /contractor-services : Create a new contractorService.
     *
     * @param contractorServiceDTO the contractorServiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contractorServiceDTO, or with status 400 (Bad Request) if the contractorService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contractor-services")
    public ResponseEntity<ContractorServiceDTO> createContractorService(@RequestBody ContractorServiceDTO contractorServiceDTO) throws URISyntaxException {
        log.debug("REST request to save ContractorService : {}", contractorServiceDTO);
        if (contractorServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new contractorService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContractorServiceDTO result = contractorServiceService.save(contractorServiceDTO);
        return ResponseEntity.created(new URI("/api/contractor-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contractor-services : Updates an existing contractorService.
     *
     * @param contractorServiceDTO the contractorServiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contractorServiceDTO,
     * or with status 400 (Bad Request) if the contractorServiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the contractorServiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contractor-services")
    public ResponseEntity<ContractorServiceDTO> updateContractorService(@RequestBody ContractorServiceDTO contractorServiceDTO) throws URISyntaxException {
        log.debug("REST request to update ContractorService : {}", contractorServiceDTO);
        if (contractorServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContractorServiceDTO result = contractorServiceService.save(contractorServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contractorServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contractor-services : get all the contractorServices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contractorServices in body
     */
    @GetMapping("/contractor-services")
    public ResponseEntity<List<ContractorServiceDTO>> getAllContractorServices(Pageable pageable) {
        log.debug("REST request to get a page of ContractorServices");
        Page<ContractorServiceDTO> page = contractorServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contractor-services");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /contractor-services/:id : get the "id" contractorService.
     *
     * @param id the id of the contractorServiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contractorServiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contractor-services/{id}")
    public ResponseEntity<ContractorServiceDTO> getContractorService(@PathVariable Long id) {
        log.debug("REST request to get ContractorService : {}", id);
        Optional<ContractorServiceDTO> contractorServiceDTO = contractorServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contractorServiceDTO);
    }

    /**
     * DELETE  /contractor-services/:id : delete the "id" contractorService.
     *
     * @param id the id of the contractorServiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contractor-services/{id}")
    public ResponseEntity<Void> deleteContractorService(@PathVariable Long id) {
        log.debug("REST request to delete ContractorService : {}", id);
        contractorServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/contractor-services?query=:query : search for the contractorService corresponding
     * to the query.
     *
     * @param query the query of the contractorService search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/contractor-services")
    public ResponseEntity<List<ContractorServiceDTO>> searchContractorServices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ContractorServices for query {}", query);
        Page<ContractorServiceDTO> page = contractorServiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/contractor-services");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
