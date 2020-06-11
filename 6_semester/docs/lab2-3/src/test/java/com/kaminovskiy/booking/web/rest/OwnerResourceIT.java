package com.kaminovskiy.booking.web.rest;

import com.kaminovskiy.booking.BookingApp;
import com.kaminovskiy.booking.domain.Owner;
import com.kaminovskiy.booking.domain.Manager;
import com.kaminovskiy.booking.domain.Receptionist;
import com.kaminovskiy.booking.domain.Customer;
import com.kaminovskiy.booking.domain.FoodItem;
import com.kaminovskiy.booking.domain.Bill;
import com.kaminovskiy.booking.domain.Room;
import com.kaminovskiy.booking.repository.OwnerRepository;
import com.kaminovskiy.booking.service.OwnerService;
import com.kaminovskiy.booking.service.dto.OwnerCriteria;
import com.kaminovskiy.booking.service.OwnerQueryService;

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
 * Integration tests for the {@link OwnerResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class OwnerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "8875049321";
    private static final String UPDATED_PHONE = "6749664839";

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerQueryService ownerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOwnerMockMvc;

    private Owner owner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Owner createEntity(EntityManager em) {
        Owner owner = new Owner()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE);
        return owner;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Owner createUpdatedEntity(EntityManager em) {
        Owner owner = new Owner()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE);
        return owner;
    }

    @BeforeEach
    public void initTest() {
        owner = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwner() throws Exception {
        int databaseSizeBeforeCreate = ownerRepository.findAll().size();

        // Create the Owner
        restOwnerMockMvc.perform(post("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(owner)))
            .andExpect(status().isCreated());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeCreate + 1);
        Owner testOwner = ownerList.get(ownerList.size() - 1);
        assertThat(testOwner.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOwner.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createOwnerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ownerRepository.findAll().size();

        // Create the Owner with an existing ID
        owner.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnerMockMvc.perform(post("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(owner)))
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerRepository.findAll().size();
        // set the field null
        owner.setName(null);

        // Create the Owner, which fails.

        restOwnerMockMvc.perform(post("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(owner)))
            .andExpect(status().isBadRequest());

        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOwners() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList
        restOwnerMockMvc.perform(get("/api/owners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getOwner() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get the owner
        restOwnerMockMvc.perform(get("/api/owners/{id}", owner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(owner.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }


    @Test
    @Transactional
    public void getOwnersByIdFiltering() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        Long id = owner.getId();

        defaultOwnerShouldBeFound("id.equals=" + id);
        defaultOwnerShouldNotBeFound("id.notEquals=" + id);

        defaultOwnerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOwnerShouldNotBeFound("id.greaterThan=" + id);

        defaultOwnerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOwnerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOwnersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where name equals to DEFAULT_NAME
        defaultOwnerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ownerList where name equals to UPDATED_NAME
        defaultOwnerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where name not equals to DEFAULT_NAME
        defaultOwnerShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ownerList where name not equals to UPDATED_NAME
        defaultOwnerShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOwnerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ownerList where name equals to UPDATED_NAME
        defaultOwnerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where name is not null
        defaultOwnerShouldBeFound("name.specified=true");

        // Get all the ownerList where name is null
        defaultOwnerShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllOwnersByNameContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where name contains DEFAULT_NAME
        defaultOwnerShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ownerList where name contains UPDATED_NAME
        defaultOwnerShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where name does not contain DEFAULT_NAME
        defaultOwnerShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ownerList where name does not contain UPDATED_NAME
        defaultOwnerShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllOwnersByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where phone equals to DEFAULT_PHONE
        defaultOwnerShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the ownerList where phone equals to UPDATED_PHONE
        defaultOwnerShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllOwnersByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where phone not equals to DEFAULT_PHONE
        defaultOwnerShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the ownerList where phone not equals to UPDATED_PHONE
        defaultOwnerShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllOwnersByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultOwnerShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the ownerList where phone equals to UPDATED_PHONE
        defaultOwnerShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllOwnersByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where phone is not null
        defaultOwnerShouldBeFound("phone.specified=true");

        // Get all the ownerList where phone is null
        defaultOwnerShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllOwnersByPhoneContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where phone contains DEFAULT_PHONE
        defaultOwnerShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the ownerList where phone contains UPDATED_PHONE
        defaultOwnerShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllOwnersByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get all the ownerList where phone does not contain DEFAULT_PHONE
        defaultOwnerShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the ownerList where phone does not contain UPDATED_PHONE
        defaultOwnerShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllOwnersByManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);
        Manager manager = ManagerResourceIT.createEntity(em);
        em.persist(manager);
        em.flush();
        owner.setManager(manager);
        manager.setOwner(owner);
        ownerRepository.saveAndFlush(owner);
        Long managerId = manager.getId();

        // Get all the ownerList where manager equals to managerId
        defaultOwnerShouldBeFound("managerId.equals=" + managerId);

        // Get all the ownerList where manager equals to managerId + 1
        defaultOwnerShouldNotBeFound("managerId.equals=" + (managerId + 1));
    }


    @Test
    @Transactional
    public void getAllOwnersByReceptionistIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);
        Receptionist receptionist = ReceptionistResourceIT.createEntity(em);
        em.persist(receptionist);
        em.flush();
        owner.setReceptionist(receptionist);
        receptionist.setOwner(owner);
        ownerRepository.saveAndFlush(owner);
        Long receptionistId = receptionist.getId();

        // Get all the ownerList where receptionist equals to receptionistId
        defaultOwnerShouldBeFound("receptionistId.equals=" + receptionistId);

        // Get all the ownerList where receptionist equals to receptionistId + 1
        defaultOwnerShouldNotBeFound("receptionistId.equals=" + (receptionistId + 1));
    }


    @Test
    @Transactional
    public void getAllOwnersByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        owner.setCustomer(customer);
        customer.setOwner(owner);
        ownerRepository.saveAndFlush(owner);
        Long customerId = customer.getId();

        // Get all the ownerList where customer equals to customerId
        defaultOwnerShouldBeFound("customerId.equals=" + customerId);

        // Get all the ownerList where customer equals to customerId + 1
        defaultOwnerShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllOwnersByFoodItemIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);
        FoodItem foodItem = FoodItemResourceIT.createEntity(em);
        em.persist(foodItem);
        em.flush();
        owner.setFoodItem(foodItem);
        foodItem.setOwner(owner);
        ownerRepository.saveAndFlush(owner);
        Long foodItemId = foodItem.getId();

        // Get all the ownerList where foodItem equals to foodItemId
        defaultOwnerShouldBeFound("foodItemId.equals=" + foodItemId);

        // Get all the ownerList where foodItem equals to foodItemId + 1
        defaultOwnerShouldNotBeFound("foodItemId.equals=" + (foodItemId + 1));
    }


    @Test
    @Transactional
    public void getAllOwnersByBillIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);
        Bill bill = BillResourceIT.createEntity(em);
        em.persist(bill);
        em.flush();
        owner.setBill(bill);
        bill.setOwner(owner);
        ownerRepository.saveAndFlush(owner);
        Long billId = bill.getId();

        // Get all the ownerList where bill equals to billId
        defaultOwnerShouldBeFound("billId.equals=" + billId);

        // Get all the ownerList where bill equals to billId + 1
        defaultOwnerShouldNotBeFound("billId.equals=" + (billId + 1));
    }


    @Test
    @Transactional
    public void getAllOwnersByRoomIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRepository.saveAndFlush(owner);
        Room room = RoomResourceIT.createEntity(em);
        em.persist(room);
        em.flush();
        owner.setRoom(room);
        room.setOwner(owner);
        ownerRepository.saveAndFlush(owner);
        Long roomId = room.getId();

        // Get all the ownerList where room equals to roomId
        defaultOwnerShouldBeFound("roomId.equals=" + roomId);

        // Get all the ownerList where room equals to roomId + 1
        defaultOwnerShouldNotBeFound("roomId.equals=" + (roomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOwnerShouldBeFound(String filter) throws Exception {
        restOwnerMockMvc.perform(get("/api/owners?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));

        // Check, that the count call also returns 1
        restOwnerMockMvc.perform(get("/api/owners/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOwnerShouldNotBeFound(String filter) throws Exception {
        restOwnerMockMvc.perform(get("/api/owners?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOwnerMockMvc.perform(get("/api/owners/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOwner() throws Exception {
        // Get the owner
        restOwnerMockMvc.perform(get("/api/owners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwner() throws Exception {
        // Initialize the database
        ownerService.save(owner);

        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();

        // Update the owner
        Owner updatedOwner = ownerRepository.findById(owner.getId()).get();
        // Disconnect from session so that the updates on updatedOwner are not directly saved in db
        em.detach(updatedOwner);
        updatedOwner
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE);

        restOwnerMockMvc.perform(put("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOwner)))
            .andExpect(status().isOk());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
        Owner testOwner = ownerList.get(ownerList.size() - 1);
        assertThat(testOwner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOwner.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingOwner() throws Exception {
        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();

        // Create the Owner

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerMockMvc.perform(put("/api/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(owner)))
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOwner() throws Exception {
        // Initialize the database
        ownerService.save(owner);

        int databaseSizeBeforeDelete = ownerRepository.findAll().size();

        // Delete the owner
        restOwnerMockMvc.perform(delete("/api/owners/{id}", owner.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
