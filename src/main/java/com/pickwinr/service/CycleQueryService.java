package com.pickwinr.service;

import com.pickwinr.domain.*; // for static metamodels
import com.pickwinr.domain.Cycle;
import com.pickwinr.repository.CycleRepository;
import com.pickwinr.service.criteria.CycleCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Cycle} entities in the database.
 * The main input is a {@link CycleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Cycle} or a {@link Page} of {@link Cycle} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CycleQueryService extends QueryService<Cycle> {

    private final Logger log = LoggerFactory.getLogger(CycleQueryService.class);

    private final CycleRepository cycleRepository;

    public CycleQueryService(CycleRepository cycleRepository) {
        this.cycleRepository = cycleRepository;
    }

    /**
     * Return a {@link List} of {@link Cycle} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Cycle> findByCriteria(CycleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cycle> specification = createSpecification(criteria);
        return cycleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Cycle} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Cycle> findByCriteria(CycleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cycle> specification = createSpecification(criteria);
        return cycleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CycleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cycle> specification = createSpecification(criteria);
        return cycleRepository.count(specification);
    }

    /**
     * Function to convert {@link CycleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cycle> createSpecification(CycleCriteria criteria) {
        Specification<Cycle> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cycle_.id));
            }
            if (criteria.getRunDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRunDate(), Cycle_.runDate));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Cycle_.email));
            }
            if (criteria.getWinners() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWinners(), Cycle_.winners));
            }
            if (criteria.getAlternatives() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlternatives(), Cycle_.alternatives));
            }
            if (criteria.getPostId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPostId(), root -> root.join(Cycle_.post, JoinType.LEFT).get(Post_.id))
                    );
            }
            if (criteria.getOpcodeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getOpcodeId(), root -> root.join(Cycle_.opcode, JoinType.LEFT).get(Opcode_.id))
                    );
            }
        }
        return specification;
    }
}
