package com.pickwinr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pickwinr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpcodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Opcode.class);
        Opcode opcode1 = new Opcode();
        opcode1.setId(1L);
        Opcode opcode2 = new Opcode();
        opcode2.setId(opcode1.getId());
        assertThat(opcode1).isEqualTo(opcode2);
        opcode2.setId(2L);
        assertThat(opcode1).isNotEqualTo(opcode2);
        opcode1.setId(null);
        assertThat(opcode1).isNotEqualTo(opcode2);
    }
}
