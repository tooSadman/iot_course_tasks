package com.kaminovskiy.booking.web.rest;

import com.kaminovskiy.booking.BookingApp;
import com.kaminovskiy.booking.domain.Receptionist;
import com.kaminovskiy.booking.domain.Owner;
import com.kaminovskiy.booking.domain.Room;
import com.kaminovskiy.booking.domain.Bill;
import com.kaminovskiy.booking.repository.ReceptionistRepository;
import com.kaminovskiy.booking.service.ReceptionistService;
import com.kaminovskiy.booking.service.dto.ReceptionistCriteria;
import com.kaminovskiy.booking.service.ReceptionistQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReceptionistResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ReceptionistResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "+4 3254544581";
    private static final String UPDATED_PHONE = "+9-7837037168";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private ReceptionistService receptionistService;

    @Autowired
    private ReceptionistQueryService receptionistQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReceptionistMockMvc;

    private Receptionist receptionist;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receptionist createEntity(EntityManager em) {
        Receptionist receptionist = new Receptionist()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .address(DEFAULT_ADDRESS);
        return receptionist;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receptionist createUpdatedEntity(EntityManager em) {
        Receptionist receptionist = new Receptionist()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS);
        return receptionist;
    }

    @BeforeEach
    public void initTest() {
        receptionist = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceptionist() throws Exception {
        int databaseSizeBeforeCreate = receptionistRepository.findAll().size();

        // Create the Receptionist
        restReceptionistMockMvc.perform(post("/api/receptionists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receptionist)))
            .andExpect(status().isCreated());

        // Validate the Receptionist in the database
        List<Receptionist> receptionistList = receptionistRepository.findAll();
        assertThat(receptionistList).hasSize(databaseSizeBeforeCreate + 1);
        Receptionist testReceptionist = receptionistList.get(receptionistList.size() - 1);
        assertThat(testReceptionist.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReceptionist.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testReceptionist.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createReceptionistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receptionistRepository.findAll().size();

        // Create the Receptionist with an existing ID
        receptionist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceptionistMockMvc.perform(post("/api/receptionists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receptionist)))
            .andExpect(status().isBadRequest());

        // Validate the Receptionist in the database
        List<Receptionist> receptionistList = receptionistRepository.findAll();
        assertThat(receptionistList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = receptionistRepository.findAll().size();
        // set the field null
        receptionist.setName(null);

        // Create the Receptionist, which fails.

        restReceptionistMockMvc.perform(post("/api/receptionists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receptionist)))
            .andExpect(status().isBadRequest());

        List<Receptionist> receptionistList = receptionistRepository.findAll();
        assertThat(receptionistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReceptionists() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList
        restReceptionistMockMvc.perform(get("/api/receptionists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receptionist.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getReceptionist() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get the receptionist
        restReceptionistMockMvc.perform(get("/api/receptionists/{id}", receptionist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receptionist.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }


    @Test
    @Transactional
    public void getReceptionistsByIdFiltering() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        Long id = receptionist.getId();

        defaultReceptionistShouldBeFound("id.equals=" + id);
        defaultReceptionistShouldNotBeFound("id.notEquals=" + id);

        defaultReceptionistShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReceptionistShouldNotBeFound("id.greaterThan=" + id);

        defaultReceptionistShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReceptionistShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReceptionistsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where name equals to DEFAULT_NAME
        defaultReceptionistShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the receptionistList where name equals to UPDATED_NAME
        defaultReceptionistShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReceptionistsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where name not equals to DEFAULT_NAME
        defaultReceptionistShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the receptionistList where name not equals to UPDATED_NAME
        defaultReceptionistShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReceptionistsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReceptionistShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the receptionistList where name equals to UPDATED_NAME
        defaultReceptionistShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReceptionistsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where name is not null
        defaultReceptionistShouldBeFound("name.specified=true");

        // Get all the receptionistList where name is null
        defaultReceptionistShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllReceptionistsByNameContainsSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where name contains DEFAULT_NAME
        defaultReceptionistShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the receptionistList where name contains UPDATED_NAME
        defaultReceptionistShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReceptionistsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where name does not contain DEFAULT_NAME
        defaultReceptionistShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the receptionistList where name does not contain UPDATED_NAME
        defaultReceptionistShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllReceptionistsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where phone equals to DEFAULT_PHONE
        defaultReceptionistShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the receptionistList where phone equals to UPDATED_PHONE
        defaultReceptionistShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllReceptionistsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where phone not equals to DEFAULT_PHONE
        defaultReceptionistShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the receptionistList where phone not equals to UPDATED_PHONE
        defaultReceptionistShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllReceptionistsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultReceptionistShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the receptionistList where phone equals to UPDATED_PHONE
        defaultReceptionistShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllReceptionistsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where phone is not null
        defaultReceptionistShouldBeFound("phone.specified=true");

        // Get all the receptionistList where phone is null
        defaultReceptionistShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllReceptionistsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where phone contains DEFAULT_PHONE
        defaultReceptionistShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the receptionistList where phone contains UPDATED_PHONE
        defaultReceptionistShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllReceptionistsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where phone does not contain DEFAULT_PHONE
        defaultReceptionistShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the receptionistList where phone does not contain UPDATED_PHONE
        defaultReceptionistShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllReceptionistsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where address equals to DEFAULT_ADDRESS
        defaultReceptionistShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the receptionistList where address equals to UPDATED_ADDRESS
        defaultReceptionistShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllReceptionistsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where address not equals to DEFAULT_ADDRESS
        defaultReceptionistShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the receptionistList where address not equals to UPDATED_ADDRESS
        defaultReceptionistShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllReceptionistsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultReceptionistShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the receptionistList where address equals to UPDATED_ADDRESS
        defaultReceptionistShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllReceptionistsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where address is not null
        defaultReceptionistShouldBeFound("address.specified=true");

        // Get all the receptionistList where address is null
        defaultReceptionistShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllReceptionistsByAddressContainsSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where address contains DEFAULT_ADDRESS
        defaultReceptionistShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the receptionistList where address contains UPDATED_ADDRESS
        defaultReceptionistShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllReceptionistsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList where address does not contain DEFAULT_ADDRESS
        defaultReceptionistShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the receptionistList where address does not contain UPDATED_ADDRESS
        defaultReceptionistShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllReceptionistsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);
        Owner owner = OwnerResourceIT.createEntity(em);
        em.persist(owner);
        em.flush();
        receptionist.setOwner(owner);
        receptionistRepository.saveAndFlush(receptionist);
        Long ownerId = owner.getId();

        // Get all the receptionistList where owner equals to ownerId
        defaultReceptionistShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the receptionistList where owner equals to ownerId + 1
        defaultReceptionistShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllReceptionistsByManagedRoomsIsEqualToSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);
        Room managedRooms = RoomResourceIT.createEntity(em);
        em.persist(managedRooms);
        em.flush();
        receptionist.addManagedRooms(managedRooms);
        receptionistRepository.saveAndFlush(receptionist);
        Long managedRoomsId = managedRooms.getId();

        // Get all the receptionistList where managedRooms equals to managedRoomsId
        defaultReceptionistShouldBeFound("managedRoomsId.equals=" + managedRoomsId);

        // Get all the receptionistList where managedRooms equals to managedRoomsId + 1
        defaultReceptionistShouldNotBeFound("managedRoomsId.equals=" + (managedRoomsId + 1));
    }


    @Test
    @Transactional
    public void getAllReceptionistsByBillIsEqualToSomething() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);
        Bill bill = BillResourceIT.createEntity(em);
        em.persist(bill);
        em.flush();
        receptionist.setBill(bill);
        receptionistRepository.saveAndFlush(receptionist);
        Long billId = bill.getId();

        // Get all the receptionistList where bill equals to billId
        defaultReceptionistShouldBeFound("billId.equals=" + billId);

        // Get all the receptionistList where bill equals to billId + 1
        defaultReceptionistShouldNotBeFound("billId.equals=" + (billId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReceptionistShouldBeFound(String filter) throws Exception {
        restReceptionistMockMvc.perform(get("/api/receptionists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receptionist.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));

        // Check, that the count call also returns 1
        restReceptionistMockMvc.perform(get("/api/receptionists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReceptionistShouldNotBeFound(String filter) throws Exception {
        restReceptionistMockMvc.perform(get("/api/receptionists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReceptionistMockMvc.perform(get("/api/receptionists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReceptionist() throws Exception {
        // Get the receptionist
        restReceptionistMockMvc.perform(get("/api/receptionists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceptionist() throws Exception {
        // Initialize the database
        receptionistService.save(receptionist);

        int databaseSizeBeforeUpdate = receptionistRepository.findAll().size();

        // Update the receptionist
        Receptionist updatedReceptionist = receptionistRepository.findById(receptionist.getId()).get();
        // Disconnect from session so that the updates on updatedReceptionist are not directly saved in db
        em.detach(updatedReceptionist);
        updatedReceptionist
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS);

        restReceptionistMockMvc.perform(put("/api/receptionists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReceptionist)))
            .andExpect(status().isOk());

        // Validate the Receptionist in the database
        List<Receptionist> receptionistList = receptionistRepository.findAll();
        assertThat(receptionistList).hasSize(databaseSizeBeforeUpdate);
        Receptionist testReceptionist = receptionistList.get(receptionistList.size() - 1);
        assertThat(testReceptionist.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReceptionist.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testReceptionist.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingReceptionist() throws Exception {
        int databaseSizeBeforeUpdate = receptionistRepository.findAll().size();

        // Create the Receptionist

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceptionistMockMvc.perform(put("/api/receptionists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receptionist)))
            .andExpect(status().isBadRequest());

        // Validate the Receptionist in the database
        List<Receptionist> receptionistList = receptionistRepository.findAll();
        assertThat(receptionistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReceptionist() throws Exception {
        // Initialize the database
        receptionistService.save(receptionist);

        int databaseSizeBeforeDelete = receptionistRepository.findAll().size();

        // Delete the receptionist
        restReceptionistMockMvc.perform(delete("/api/receptionists/{id}", receptionist.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Receptionist> receptionistList = receptionistRepository.findAll();
        assertThat(receptionistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
