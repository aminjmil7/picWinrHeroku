package com.pickwinr.service;

import com.pickwinr.domain.*; // for static metamodels
import com.pickwinr.domain.Opcode;
import com.pickwinr.repository.OpcodeRepository;
import com.pickwinr.service.criteria.OpcodeCriteria;
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
 * Service for executing complex queries for {@link Opcode} entities in the database.
 * The main input is a {@link OpcodeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Opcode} or a {@link Page} of {@link Opcode} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OpcodeQueryService extends QueryService<Opcode> {

    private final Logger log = LoggerFactory.getLogger(OpcodeQueryService.class);

    private final OpcodeRepository opcodeRepository;

    public OpcodeQueryService(OpcodeRepository opcodeRepository) {
        this.opcodeRepository = opcodeRepository;
    }

    /**
     * Return a {@link List} of {@link Opcode} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Opcode> findByCriteria(OpcodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Opcode> specification = createSpecification(criteria);
        return opcodeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Opcode} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Opcode> findByCriteria(OpcodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Opcode> specification = createSpecification(criteria);
        return opcodeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OpcodeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Opcode> specification = createSpecification(criteria);
        return opcodeRepository.count(specification);
    }

    /**
     * Function to convert {@link OpcodeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Opcode> createSpecification(OpcodeCriteria criteria) {
        Specification<Opcode> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Opcode_.id));
            }
            if (criteria.getOpirationCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOpirationCode(), Opcode_.opirationCode));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), Opcode_.count));
            }
            if (criteria.getCeationDated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCeationDated(), Opcode_.ceationDated));
            }
            if (criteria.getExpirationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpirationDate(), Opcode_.expirationDate));
            }
            if (criteria.getCycleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCycleId(), root -> root.join(Opcode_.cycles, JoinType.LEFT).get(Cycle_.id))
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(Opcode_.user, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}
