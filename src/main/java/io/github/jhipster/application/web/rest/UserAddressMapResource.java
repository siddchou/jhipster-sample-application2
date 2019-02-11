package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.UserAddressMapService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.UserAddressMapDTO;
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
 * REST controller for managing UserAddressMap.
 */
@RestController
@RequestMapping("/api")
public class UserAddressMapResource {

    private final Logger log = LoggerFactory.getLogger(UserAddressMapResource.class);

    private static final String ENTITY_NAME = "userAddressMap";

    private final UserAddressMapService userAddressMapService;

    public UserAddressMapResource(UserAddressMapService userAddressMapService) {
        this.userAddressMapService = userAddressMapService;
    }

    /**
     * POST  /user-address-maps : Create a new userAddressMap.
     *
     * @param userAddressMapDTO the userAddressMapDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userAddressMapDTO, or with status 400 (Bad Request) if the userAddressMap has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-address-maps")
    public ResponseEntity<UserAddressMapDTO> createUserAddressMap(@RequestBody UserAddressMapDTO userAddressMapDTO) throws URISyntaxException {
        log.debug("REST request to save UserAddressMap : {}", userAddressMapDTO);
        if (userAddressMapDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAddressMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAddressMapDTO result = userAddressMapService.save(userAddressMapDTO);
        return ResponseEntity.created(new URI("/api/user-address-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-address-maps : Updates an existing userAddressMap.
     *
     * @param userAddressMapDTO the userAddressMapDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userAddressMapDTO,
     * or with status 400 (Bad Request) if the userAddressMapDTO is not valid,
     * or with status 500 (Internal Server Error) if the userAddressMapDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-address-maps")
    public ResponseEntity<UserAddressMapDTO> updateUserAddressMap(@RequestBody UserAddressMapDTO userAddressMapDTO) throws URISyntaxException {
        log.debug("REST request to update UserAddressMap : {}", userAddressMapDTO);
        if (userAddressMapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserAddressMapDTO result = userAddressMapService.save(userAddressMapDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userAddressMapDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-address-maps : get all the userAddressMaps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userAddressMaps in body
     */
    @GetMapping("/user-address-maps")
    public ResponseEntity<List<UserAddressMapDTO>> getAllUserAddressMaps(Pageable pageable) {
        log.debug("REST request to get a page of UserAddressMaps");
        Page<UserAddressMapDTO> page = userAddressMapService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-address-maps");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /user-address-maps/:id : get the "id" userAddressMap.
     *
     * @param id the id of the userAddressMapDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userAddressMapDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-address-maps/{id}")
    public ResponseEntity<UserAddressMapDTO> getUserAddressMap(@PathVariable Long id) {
        log.debug("REST request to get UserAddressMap : {}", id);
        Optional<UserAddressMapDTO> userAddressMapDTO = userAddressMapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAddressMapDTO);
    }

    /**
     * DELETE  /user-address-maps/:id : delete the "id" userAddressMap.
     *
     * @param id the id of the userAddressMapDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-address-maps/{id}")
    public ResponseEntity<Void> deleteUserAddressMap(@PathVariable Long id) {
        log.debug("REST request to delete UserAddressMap : {}", id);
        userAddressMapService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-address-maps?query=:query : search for the userAddressMap corresponding
     * to the query.
     *
     * @param query the query of the userAddressMap search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/user-address-maps")
    public ResponseEntity<List<UserAddressMapDTO>> searchUserAddressMaps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserAddressMaps for query {}", query);
        Page<UserAddressMapDTO> page = userAddressMapService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-address-maps");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
