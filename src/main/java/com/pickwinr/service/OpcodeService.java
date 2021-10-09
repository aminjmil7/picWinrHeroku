package com.pickwinr.service;

import com.pickwinr.domain.Opcode;
import com.pickwinr.repository.OpcodeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Opcode}.
 */
@Service
@Transactional
public class OpcodeService {

    private final Logger log = LoggerFactory.getLogger(OpcodeService.class);

    private final OpcodeRepository opcodeRepository;

    public OpcodeService(OpcodeRepository opcodeRepository) {
        this.opcodeRepository = opcodeRepository;
    }

    /**
     * Save a opcode.
     *
     * @param opcode the entity to save.
     * @return the persisted entity.
     */
    public Opcode save(Opcode opcode) {
        log.debug("Request to save Opcode : {}", opcode);
        return opcodeRepository.save(opcode);
    }

    /**
     * Partially update a opcode.
     *
     * @param opcode the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Opcode> partialUpdate(Opcode opcode) {
        log.debug("Request to partially update Opcode : {}", opcode);

        return opcodeRepository
            .findById(opcode.getId())
            .map(
                existingOpcode -> {
                    if (opcode.getOpirationCode() != null) {
                        existingOpcode.setOpirationCode(opcode.getOpirationCode());
                    }
                    if (opcode.getCount() != null) {
                        existingOpcode.setCount(opcode.getCount());
                    }
                    if (opcode.getCeationDated() != null) {
                        existingOpcode.setCeationDated(opcode.getCeationDated());
                    }
                    if (opcode.getExpirationDate() != null) {
                        existingOpcode.setExpirationDate(opcode.getExpirationDate());
                    }

                    return existingOpcode;
                }
            )
            .map(opcodeRepository::save);
    }

    /**
     * Get all the opcodes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Opcode> findAll() {
        log.debug("Request to get all Opcodes");
        return opcodeRepository.findAll();
    }

    /**
     * Get one opcode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Opcode> findOne(Long id) {
        log.debug("Request to get Opcode : {}", id);
        return opcodeRepository.findById(id);
    }

    /**
     * Delete the opcode by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Opcode : {}", id);
        opcodeRepository.deleteById(id);
    }
}
