package com.pickwinr.web.rest;

import com.pickwinr.domain.Opcode;
import com.pickwinr.repository.OpcodeRepository;
import com.pickwinr.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.pickwinr.domain.Opcode}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OpcodeResource {

    private final Logger log = LoggerFactory.getLogger(OpcodeResource.class);

    private static final String ENTITY_NAME = "opcode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpcodeRepository opcodeRepository;

    public OpcodeResource(OpcodeRepository opcodeRepository) {
        this.opcodeRepository = opcodeRepository;
    }

    /**
     * {@code POST  /opcodes} : Create a new opcode.
     *
     * @param opcode the opcode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opcode, or with status {@code 400 (Bad Request)} if the opcode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opcodes")
    public ResponseEntity<Opcode> createOpcode(@Valid @RequestBody Opcode opcode) throws URISyntaxException {
        log.debug("REST request to save Opcode : {}", opcode);
        if (opcode.getId() != null) {
            throw new BadRequestAlertException("A new opcode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Opcode result = opcodeRepository.save(opcode);
        return ResponseEntity
            .created(new URI("/api/opcodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opcodes/:id} : Updates an existing opcode.
     *
     * @param id the id of the opcode to save.
     * @param opcode the opcode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opcode,
     * or with status {@code 400 (Bad Request)} if the opcode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opcode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opcodes/{id}")
    public ResponseEntity<Opcode> updateOpcode(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Opcode opcode
    ) throws URISyntaxException {
        log.debug("REST request to update Opcode : {}, {}", id, opcode);
        if (opcode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opcode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opcodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Opcode result = opcodeRepository.save(opcode);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opcode.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /opcodes/:id} : Partial updates given fields of an existing opcode, field will ignore if it is null
     *
     * @param id the id of the opcode to save.
     * @param opcode the opcode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opcode,
     * or with status {@code 400 (Bad Request)} if the opcode is not valid,
     * or with status {@code 404 (Not Found)} if the opcode is not found,
     * or with status {@code 500 (Internal Server Error)} if the opcode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/opcodes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Opcode> partialUpdateOpcode(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Opcode opcode
    ) throws URISyntaxException {
        log.debug("REST request to partial update Opcode partially : {}, {}", id, opcode);
        if (opcode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opcode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opcodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Opcode> result = opcodeRepository
            .findById(opcode.getId())
            .map(
                existingOpcode -> {
                    if (opcode.getCount() != null) {
                        existingOpcode.setCount(opcode.getCount());
                    }
                    if (opcode.getCeationDated() != null) {
                        existingOpcode.setCeationDated(opcode.getCeationDated());
                    }
                    if (opcode.getExpirationDate() != null) {
                        existingOpcode.setExpirationDate(opcode.getExpirationDate());
                    }
                    if (opcode.getOpirationCode() != null) {
                        existingOpcode.setOpirationCode(opcode.getOpirationCode());
                    }

                    return existingOpcode;
                }
            )
            .map(opcodeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opcode.getId().toString())
        );
    }

    /**
     * {@code GET  /opcodes} : get all the opcodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opcodes in body.
     */
    @GetMapping("/opcodes")
    public List<Opcode> getAllOpcodes() {
        log.debug("REST request to get all Opcodes");
        return opcodeRepository.findAll();
    }

    /**
     * {@code GET  /opcodes/:id} : get the "id" opcode.
     *
     * @param id the id of the opcode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opcode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opcodes/{id}")
    public ResponseEntity<Opcode> getOpcode(@PathVariable Long id) {
        log.debug("REST request to get Opcode : {}", id);
        Optional<Opcode> opcode = opcodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(opcode);
    }

    /**
     * {@code DELETE  /opcodes/:id} : delete the "id" opcode.
     *
     * @param id the id of the opcode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opcodes/{id}")
    public ResponseEntity<Void> deleteOpcode(@PathVariable Long id) {
        log.debug("REST request to delete Opcode : {}", id);
        opcodeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
