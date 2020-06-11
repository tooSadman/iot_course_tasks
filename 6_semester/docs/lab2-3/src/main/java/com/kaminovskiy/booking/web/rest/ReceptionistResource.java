package com.kaminovskiy.booking.web.rest;

import com.kaminovskiy.booking.domain.Receptionist;
import com.kaminovskiy.booking.service.ReceptionistService;
import com.kaminovskiy.booking.web.rest.errors.BadRequestAlertException;
import com.kaminovskiy.booking.service.dto.ReceptionistCriteria;
import com.kaminovskiy.booking.service.ReceptionistQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kaminovskiy.booking.domain.Receptionist}.
 */
@RestController
@RequestMapping("/api")
public class ReceptionistResource {

    private final Logger log = LoggerFactory.getLogger(ReceptionistResource.class);

    private static final String ENTITY_NAME = "receptionist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceptionistService receptionistService;

    private final ReceptionistQueryService receptionistQueryService;

    public ReceptionistResource(ReceptionistService receptionistService, ReceptionistQueryService receptionistQueryService) {
        this.receptionistService = receptionistService;
        this.receptionistQueryService = receptionistQueryService;
    }

    /**
     * {@code POST  /receptionists} : Create a new receptionist.
     *
     * @param receptionist the receptionist to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receptionist, or with status {@code 400 (Bad Request)} if the receptionist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/receptionists")
    public ResponseEntity<Receptionist> createReceptionist(@Valid @RequestBody Receptionist receptionist) throws URISyntaxException {
        log.debug("REST request to save Receptionist : {}", receptionist);
        if (receptionist.getId() != null) {
            throw new BadRequestAlertException("A new receptionist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Receptionist result = receptionistService.save(receptionist);
        return ResponseEntity.created(new URI("/api/receptionists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /receptionists} : Updates an existing receptionist.
     *
     * @param receptionist the receptionist to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receptionist,
     * or with status {@code 400 (Bad Request)} if the receptionist is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receptionist couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/receptionists")
    public ResponseEntity<Receptionist> updateReceptionist(@Valid @RequestBody Receptionist receptionist) throws URISyntaxException {
        log.debug("REST request to update Receptionist : {}", receptionist);
        if (receptionist.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Receptionist result = receptionistService.save(receptionist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, receptionist.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /receptionists} : get all the receptionists.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of receptionists in body.
     */
    @GetMapping("/receptionists")
    public ResponseEntity<List<Receptionist>> getAllReceptionists(ReceptionistCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Receptionists by criteria: {}", criteria);
        Page<Receptionist> page = receptionistQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /receptionists/count} : count all the receptionists.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/receptionists/count")
    public ResponseEntity<Long> countReceptionists(ReceptionistCriteria criteria) {
        log.debug("REST request to count Receptionists by criteria: {}", criteria);
        return ResponseEntity.ok().body(receptionistQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /receptionists/:id} : get the "id" receptionist.
     *
     * @param id the id of the receptionist to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receptionist, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/receptionists/{id}")
    public ResponseEntity<Receptionist> getReceptionist(@PathVariable Long id) {
        log.debug("REST request to get Receptionist : {}", id);
        Optional<Receptionist> receptionist = receptionistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(receptionist);
    }

    /**
     * {@code DELETE  /receptionists/:id} : delete the "id" receptionist.
     *
     * @param id the id of the receptionist to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/receptionists/{id}")
    public ResponseEntity<Void> deleteReceptionist(@PathVariable Long id) {
        log.debug("REST request to delete Receptionist : {}", id);
        receptionistService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
