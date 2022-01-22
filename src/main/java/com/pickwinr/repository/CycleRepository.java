package com.pickwinr.repository;

import com.pickwinr.domain.Cycle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cycle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CycleRepository extends JpaRepository<Cycle, Long>, JpaSpecificationExecutor<Cycle> {}
