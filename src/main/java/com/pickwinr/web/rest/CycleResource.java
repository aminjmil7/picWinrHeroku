package com.pickwinr.web.rest;

import com.pickwinr.domain.Cycle;
import com.pickwinr.repository.CycleRepository;
import com.pickwinr.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.pickwinr.domain.Cycle}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CycleResource {

    private final Logger log = LoggerFactory.getLogger(CycleResource.class);

    private static final String ENTITY_NAME = "cycle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CycleRepository cycleRepository;

    public CycleResource(CycleRepository cycleRepository) {
        this.cycleRepository = cycleRepository;
    }

    /**
     * {@code POST  /cycles} : Create a new cycle.
     *
     * @param cycle the cycle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cycle, or with status {@code 400 (Bad Request)} if the cycle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cycles")
    public ResponseEntity<Cycle> createCycle(@Valid @RequestBody Cycle cycle) throws URISyntaxException {
        log.debug("REST request to save Cycle : {}", cycle);
        if (cycle.getId() != null) {
            throw new BadRequestAlertException("A new cycle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cycle result = cycleRepository.save(cycle);
        return ResponseEntity
            .created(new URI("/api/cycles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cycles/:id} : Updates an existing cycle.
     *
     * @param id the id of the cycle to save.
     * @param cycle the cycle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cycle,
     * or with status {@code 400 (Bad Request)} if the cycle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cycle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cycles/{id}")
    public ResponseEntity<Cycle> updateCycle(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Cycle cycle)
        throws URISyntaxException {
        log.debug("REST request to update Cycle : {}, {}", id, cycle);
        if (cycle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cycle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cycleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cycle result = cycleRepository.save(cycle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cycle.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cycles/:id} : Partial updates given fields of an existing cycle, field will ignore if it is null
     *
     * @param id the id of the cycle to save.
     * @param cycle the cycle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cycle,
     * or with status {@code 400 (Bad Request)} if the cycle is not valid,
     * or with status {@code 404 (Not Found)} if the cycle is not found,
     * or with status {@code 500 (Internal Server Error)} if the cycle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cycles/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Cycle> partialUpdateCycle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Cycle cycle
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cycle partially : {}, {}", id, cycle);
        if (cycle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cycle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cycleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cycle> result = cycleRepository
            .findById(cycle.getId())
            .map(
                existingCycle -> {
                    if (cycle.getRunDate() != null) {
                        existingCycle.setRunDate(cycle.getRunDate());
                    }
                    if (cycle.getEmail() != null) {
                        existingCycle.setEmail(cycle.getEmail());
                    }
                    if (cycle.getWinners() != null) {
                        existingCycle.setWinners(cycle.getWinners());
                    }
                    if (cycle.getAlternatives() != null) {
                        existingCycle.setAlternatives(cycle.getAlternatives());
                    }

                    return existingCycle;
                }
            )
            .map(cycleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cycle.getId().toString())
        );
    }

    /**
     * {@code GET  /cycles} : get all the cycles.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cycles in body.
     */
    @GetMapping("/cycles")
    public List<Cycle> getAllCycles(@RequestParam(required = false) String filter) {
        if ("post-is-null".equals(filter)) {
            log.debug("REST request to get all Cycles where post is null");
            return StreamSupport
                .stream(cycleRepository.findAll().spliterator(), false)
                .filter(cycle -> cycle.getPost() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Cycles");
        return cycleRepository.findAll();
    }

    /**
     * {@code GET  /cycles/:id} : get the "id" cycle.
     *
     * @param id the id of the cycle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cycle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cycles/{id}")
    public ResponseEntity<Cycle> getCycle(@PathVariable Long id) {
        log.debug("REST request to get Cycle : {}", id);
        Optional<Cycle> cycle = cycleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cycle);
    }

    /**
     * {@code DELETE  /cycles/:id} : delete the "id" cycle.
     *
     * @param id the id of the cycle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cycles/{id}")
    public ResponseEntity<Void> deleteCycle(@PathVariable Long id) {
        log.debug("REST request to delete Cycle : {}", id);
        cycleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
