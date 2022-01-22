package com.pickwinr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pickwinr.IntegrationTest;
import com.pickwinr.domain.Opcode;
import com.pickwinr.repository.OpcodeRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OpcodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpcodeResourceIT {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final Instant DEFAULT_CEATION_DATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CEATION_DATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/opcodes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OpcodeRepository opcodeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpcodeMockMvc;

    private Opcode opcode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opcode createEntity(EntityManager em) {
        Opcode opcode = new Opcode().count(DEFAULT_COUNT).ceationDated(DEFAULT_CEATION_DATED).expirationDate(DEFAULT_EXPIRATION_DATE);
        return opcode;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opcode createUpdatedEntity(EntityManager em) {
        Opcode opcode = new Opcode().count(UPDATED_COUNT).ceationDated(UPDATED_CEATION_DATED).expirationDate(UPDATED_EXPIRATION_DATE);
        return opcode;
    }

    @BeforeEach
    public void initTest() {
        opcode = createEntity(em);
    }

    @Test
    @Transactional
    void createOpcode() throws Exception {
        int databaseSizeBeforeCreate = opcodeRepository.findAll().size();
        // Create the Opcode
        restOpcodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opcode)))
            .andExpect(status().isCreated());

        // Validate the Opcode in the database
        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeCreate + 1);
        Opcode testOpcode = opcodeList.get(opcodeList.size() - 1);
        assertThat(testOpcode.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testOpcode.getCeationDated()).isEqualTo(DEFAULT_CEATION_DATED);
        assertThat(testOpcode.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void createOpcodeWithExistingId() throws Exception {
        // Create the Opcode with an existing ID
        opcode.setId(1L);

        int databaseSizeBeforeCreate = opcodeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpcodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opcode)))
            .andExpect(status().isBadRequest());

        // Validate the Opcode in the database
        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = opcodeRepository.findAll().size();
        // set the field null
        opcode.setCount(null);

        // Create the Opcode, which fails.

        restOpcodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opcode)))
            .andExpect(status().isBadRequest());

        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCeationDatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = opcodeRepository.findAll().size();
        // set the field null
        opcode.setCeationDated(null);

        // Create the Opcode, which fails.

        restOpcodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opcode)))
            .andExpect(status().isBadRequest());

        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOpcodes() throws Exception {
        // Initialize the database
        opcodeRepository.saveAndFlush(opcode);

        // Get all the opcodeList
        restOpcodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opcode.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].ceationDated").value(hasItem(DEFAULT_CEATION_DATED.toString())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())));
    }

    @Test
    @Transactional
    void getOpcode() throws Exception {
        // Initialize the database
        opcodeRepository.saveAndFlush(opcode);

        // Get the opcode
        restOpcodeMockMvc
            .perform(get(ENTITY_API_URL_ID, opcode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opcode.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.ceationDated").value(DEFAULT_CEATION_DATED.toString()))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOpcode() throws Exception {
        // Get the opcode
        restOpcodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOpcode() throws Exception {
        // Initialize the database
        opcodeRepository.saveAndFlush(opcode);

        int databaseSizeBeforeUpdate = opcodeRepository.findAll().size();

        // Update the opcode
        Opcode updatedOpcode = opcodeRepository.findById(opcode.getId()).get();
        // Disconnect from session so that the updates on updatedOpcode are not directly saved in db
        em.detach(updatedOpcode);
        updatedOpcode.count(UPDATED_COUNT).ceationDated(UPDATED_CEATION_DATED).expirationDate(UPDATED_EXPIRATION_DATE);

        restOpcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpcode.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOpcode))
            )
            .andExpect(status().isOk());

        // Validate the Opcode in the database
        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeUpdate);
        Opcode testOpcode = opcodeList.get(opcodeList.size() - 1);
        assertThat(testOpcode.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testOpcode.getCeationDated()).isEqualTo(UPDATED_CEATION_DATED);
        assertThat(testOpcode.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOpcode() throws Exception {
        int databaseSizeBeforeUpdate = opcodeRepository.findAll().size();
        opcode.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opcode.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opcode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opcode in the database
        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpcode() throws Exception {
        int databaseSizeBeforeUpdate = opcodeRepository.findAll().size();
        opcode.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opcode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opcode in the database
        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpcode() throws Exception {
        int databaseSizeBeforeUpdate = opcodeRepository.findAll().size();
        opcode.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opcode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opcode in the database
        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpcodeWithPatch() throws Exception {
        // Initialize the database
        opcodeRepository.saveAndFlush(opcode);

        int databaseSizeBeforeUpdate = opcodeRepository.findAll().size();

        // Update the opcode using partial update
        Opcode partialUpdatedOpcode = new Opcode();
        partialUpdatedOpcode.setId(opcode.getId());

        partialUpdatedOpcode.ceationDated(UPDATED_CEATION_DATED).expirationDate(UPDATED_EXPIRATION_DATE);

        restOpcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpcode.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpcode))
            )
            .andExpect(status().isOk());

        // Validate the Opcode in the database
        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeUpdate);
        Opcode testOpcode = opcodeList.get(opcodeList.size() - 1);
        assertThat(testOpcode.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testOpcode.getCeationDated()).isEqualTo(UPDATED_CEATION_DATED);
        assertThat(testOpcode.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOpcodeWithPatch() throws Exception {
        // Initialize the database
        opcodeRepository.saveAndFlush(opcode);

        int databaseSizeBeforeUpdate = opcodeRepository.findAll().size();

        // Update the opcode using partial update
        Opcode partialUpdatedOpcode = new Opcode();
        partialUpdatedOpcode.setId(opcode.getId());

        partialUpdatedOpcode.count(UPDATED_COUNT).ceationDated(UPDATED_CEATION_DATED).expirationDate(UPDATED_EXPIRATION_DATE);

        restOpcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpcode.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpcode))
            )
            .andExpect(status().isOk());

        // Validate the Opcode in the database
        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeUpdate);
        Opcode testOpcode = opcodeList.get(opcodeList.size() - 1);
        assertThat(testOpcode.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testOpcode.getCeationDated()).isEqualTo(UPDATED_CEATION_DATED);
        assertThat(testOpcode.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOpcode() throws Exception {
        int databaseSizeBeforeUpdate = opcodeRepository.findAll().size();
        opcode.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opcode.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opcode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opcode in the database
        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpcode() throws Exception {
        int databaseSizeBeforeUpdate = opcodeRepository.findAll().size();
        opcode.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opcode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opcode in the database
        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpcode() throws Exception {
        int databaseSizeBeforeUpdate = opcodeRepository.findAll().size();
        opcode.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcodeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(opcode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opcode in the database
        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpcode() throws Exception {
        // Initialize the database
        opcodeRepository.saveAndFlush(opcode);

        int databaseSizeBeforeDelete = opcodeRepository.findAll().size();

        // Delete the opcode
        restOpcodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, opcode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Opcode> opcodeList = opcodeRepository.findAll();
        assertThat(opcodeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
