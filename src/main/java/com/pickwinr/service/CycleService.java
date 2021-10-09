package com.pickwinr.service;

import com.pickwinr.domain.Cycle;
import com.pickwinr.repository.CycleRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cycle}.
 */
@Service
@Transactional
public class CycleService {

    private final Logger log = LoggerFactory.getLogger(CycleService.class);

    private final CycleRepository cycleRepository;

    public CycleService(CycleRepository cycleRepository) {
        this.cycleRepository = cycleRepository;
    }

    /**
     * Save a cycle.
     *
     * @param cycle the entity to save.
     * @return the persisted entity.
     */
    public Cycle save(Cycle cycle) {
        log.debug("Request to save Cycle : {}", cycle);
        return cycleRepository.save(cycle);
    }

    /**
     * Partially update a cycle.
     *
     * @param cycle the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Cycle> partialUpdate(Cycle cycle) {
        log.debug("Request to partially update Cycle : {}", cycle);

        return cycleRepository
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
    }

    /**
     * Get all the cycles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Cycle> findAll() {
        log.debug("Request to get all Cycles");
        return cycleRepository.findAll();
    }

    /**
     *  Get all the cycles where Post is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Cycle> findAllWherePostIsNull() {
        log.debug("Request to get all cycles where Post is null");
        return StreamSupport
            .stream(cycleRepository.findAll().spliterator(), false)
            .filter(cycle -> cycle.getPost() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one cycle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Cycle> findOne(Long id) {
        log.debug("Request to get Cycle : {}", id);
        return cycleRepository.findById(id);
    }

    /**
     * Delete the cycle by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cycle : {}", id);
        cycleRepository.deleteById(id);
    }
}
