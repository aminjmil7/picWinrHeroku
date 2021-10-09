package com.pickwinr.repository;

import com.pickwinr.domain.Opcode;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Opcode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpcodeRepository extends JpaRepository<Opcode, Long>, JpaSpecificationExecutor<Opcode> {
    @Query("select opcode from Opcode opcode where opcode.user.login = ?#{principal.username}")
    List<Opcode> findByUserIsCurrentUser();
}
