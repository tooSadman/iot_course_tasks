package com.kaminovskiy.booking.web.rest;

import com.kaminovskiy.booking.BookingApp;
import com.kaminovskiy.booking.domain.Manager;
import com.kaminovskiy.booking.domain.Owner;
import com.kaminovskiy.booking.domain.Customer;
import com.kaminovskiy.booking.domain.Inventory;
import com.kaminovskiy.booking.repository.ManagerRepository;
import com.kaminovskiy.booking.service.ManagerService;
import com.kaminovskiy.booking.service.dto.ManagerCriteria;
import com.kaminovskiy.booking.service.ManagerQueryService;

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
 * Integration tests for the {@link ManagerResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ManagerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "4967483764";
    private static final String UPDATED_PHONE = "+1163353241354";

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ManagerQueryService managerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restManagerMockMvc;

    private Manager manager;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Manager createEntity(EntityManager em) {
        Manager manager = new Manager()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE);
        return manager;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Manager createUpdatedEntity(EntityManager em) {
        Manager manager = new Manager()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE);
        return manager;
    }

    @BeforeEach
    public void initTest() {
        manager = createEntity(em);
    }

    @Test
    @Transactional
    public void createManager() throws Exception {
        int databaseSizeBeforeCreate = managerRepository.findAll().size();

        // Create the Manager
        restManagerMockMvc.perform(post("/api/managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(manager)))
            .andExpect(status().isCreated());

        // Validate the Manager in the database
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeCreate + 1);
        Manager testManager = managerList.get(managerList.size() - 1);
        assertThat(testManager.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testManager.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createManagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = managerRepository.findAll().size();

        // Create the Manager with an existing ID
        manager.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restManagerMockMvc.perform(post("/api/managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(manager)))
            .andExpect(status().isBadRequest());

        // Validate the Manager in the database
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = managerRepository.findAll().size();
        // set the field null
        manager.setName(null);

        // Create the Manager, which fails.

        restManagerMockMvc.perform(post("/api/managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(manager)))
            .andExpect(status().isBadRequest());

        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllManagers() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList
        restManagerMockMvc.perform(get("/api/managers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manager.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getManager() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get the manager
        restManagerMockMvc.perform(get("/api/managers/{id}", manager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(manager.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }


    @Test
    @Transactional
    public void getManagersByIdFiltering() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        Long id = manager.getId();

        defaultManagerShouldBeFound("id.equals=" + id);
        defaultManagerShouldNotBeFound("id.notEquals=" + id);

        defaultManagerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultManagerShouldNotBeFound("id.greaterThan=" + id);

        defaultManagerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultManagerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllManagersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where name equals to DEFAULT_NAME
        defaultManagerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the managerList where name equals to UPDATED_NAME
        defaultManagerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllManagersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where name not equals to DEFAULT_NAME
        defaultManagerShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the managerList where name not equals to UPDATED_NAME
        defaultManagerShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllManagersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultManagerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the managerList where name equals to UPDATED_NAME
        defaultManagerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllManagersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where name is not null
        defaultManagerShouldBeFound("name.specified=true");

        // Get all the managerList where name is null
        defaultManagerShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllManagersByNameContainsSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where name contains DEFAULT_NAME
        defaultManagerShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the managerList where name contains UPDATED_NAME
        defaultManagerShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllManagersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where name does not contain DEFAULT_NAME
        defaultManagerShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the managerList where name does not contain UPDATED_NAME
        defaultManagerShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllManagersByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where phone equals to DEFAULT_PHONE
        defaultManagerShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the managerList where phone equals to UPDATED_PHONE
        defaultManagerShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllManagersByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where phone not equals to DEFAULT_PHONE
        defaultManagerShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the managerList where phone not equals to UPDATED_PHONE
        defaultManagerShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllManagersByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultManagerShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the managerList where phone equals to UPDATED_PHONE
        defaultManagerShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllManagersByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where phone is not null
        defaultManagerShouldBeFound("phone.specified=true");

        // Get all the managerList where phone is null
        defaultManagerShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllManagersByPhoneContainsSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where phone contains DEFAULT_PHONE
        defaultManagerShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the managerList where phone contains UPDATED_PHONE
        defaultManagerShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllManagersByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);

        // Get all the managerList where phone does not contain DEFAULT_PHONE
        defaultManagerShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the managerList where phone does not contain UPDATED_PHONE
        defaultManagerShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllManagersByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);
        Owner owner = OwnerResourceIT.createEntity(em);
        em.persist(owner);
        em.flush();
        manager.setOwner(owner);
        managerRepository.saveAndFlush(manager);
        Long ownerId = owner.getId();

        // Get all the managerList where owner equals to ownerId
        defaultManagerShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the managerList where owner equals to ownerId + 1
        defaultManagerShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllManagersByCustomersIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);
        Customer customers = CustomerResourceIT.createEntity(em);
        em.persist(customers);
        em.flush();
        manager.addCustomers(customers);
        managerRepository.saveAndFlush(manager);
        Long customersId = customers.getId();

        // Get all the managerList where customers equals to customersId
        defaultManagerShouldBeFound("customersId.equals=" + customersId);

        // Get all the managerList where customers equals to customersId + 1
        defaultManagerShouldNotBeFound("customersId.equals=" + (customersId + 1));
    }


    @Test
    @Transactional
    public void getAllManagersByInventoryIsEqualToSomething() throws Exception {
        // Initialize the database
        managerRepository.saveAndFlush(manager);
        Inventory inventory = InventoryResourceIT.createEntity(em);
        em.persist(inventory);
        em.flush();
        manager.setInventory(inventory);
        managerRepository.saveAndFlush(manager);
        Long inventoryId = inventory.getId();

        // Get all the managerList where inventory equals to inventoryId
        defaultManagerShouldBeFound("inventoryId.equals=" + inventoryId);

        // Get all the managerList where inventory equals to inventoryId + 1
        defaultManagerShouldNotBeFound("inventoryId.equals=" + (inventoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultManagerShouldBeFound(String filter) throws Exception {
        restManagerMockMvc.perform(get("/api/managers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manager.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));

        // Check, that the count call also returns 1
        restManagerMockMvc.perform(get("/api/managers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultManagerShouldNotBeFound(String filter) throws Exception {
        restManagerMockMvc.perform(get("/api/managers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restManagerMockMvc.perform(get("/api/managers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingManager() throws Exception {
        // Get the manager
        restManagerMockMvc.perform(get("/api/managers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManager() throws Exception {
        // Initialize the database
        managerService.save(manager);

        int databaseSizeBeforeUpdate = managerRepository.findAll().size();

        // Update the manager
        Manager updatedManager = managerRepository.findById(manager.getId()).get();
        // Disconnect from session so that the updates on updatedManager are not directly saved in db
        em.detach(updatedManager);
        updatedManager
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE);

        restManagerMockMvc.perform(put("/api/managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedManager)))
            .andExpect(status().isOk());

        // Validate the Manager in the database
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeUpdate);
        Manager testManager = managerList.get(managerList.size() - 1);
        assertThat(testManager.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testManager.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingManager() throws Exception {
        int databaseSizeBeforeUpdate = managerRepository.findAll().size();

        // Create the Manager

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagerMockMvc.perform(put("/api/managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(manager)))
            .andExpect(status().isBadRequest());

        // Validate the Manager in the database
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteManager() throws Exception {
        // Initialize the database
        managerService.save(manager);

        int databaseSizeBeforeDelete = managerRepository.findAll().size();

        // Delete the manager
        restManagerMockMvc.perform(delete("/api/managers/{id}", manager.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Manager> managerList = managerRepository.findAll();
        assertThat(managerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
