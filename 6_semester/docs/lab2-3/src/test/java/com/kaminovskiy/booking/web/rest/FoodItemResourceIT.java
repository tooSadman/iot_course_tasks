package com.kaminovskiy.booking.web.rest;

import com.kaminovskiy.booking.BookingApp;
import com.kaminovskiy.booking.domain.FoodItem;
import com.kaminovskiy.booking.domain.Owner;
import com.kaminovskiy.booking.domain.Customer;
import com.kaminovskiy.booking.repository.FoodItemRepository;
import com.kaminovskiy.booking.service.FoodItemService;
import com.kaminovskiy.booking.service.dto.FoodItemCriteria;
import com.kaminovskiy.booking.service.FoodItemQueryService;

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
 * Integration tests for the {@link FoodItemResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class FoodItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private FoodItemService foodItemService;

    @Autowired
    private FoodItemQueryService foodItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFoodItemMockMvc;

    private FoodItem foodItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodItem createEntity(EntityManager em) {
        FoodItem foodItem = new FoodItem()
            .name(DEFAULT_NAME);
        return foodItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodItem createUpdatedEntity(EntityManager em) {
        FoodItem foodItem = new FoodItem()
            .name(UPDATED_NAME);
        return foodItem;
    }

    @BeforeEach
    public void initTest() {
        foodItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoodItem() throws Exception {
        int databaseSizeBeforeCreate = foodItemRepository.findAll().size();

        // Create the FoodItem
        restFoodItemMockMvc.perform(post("/api/food-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(foodItem)))
            .andExpect(status().isCreated());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeCreate + 1);
        FoodItem testFoodItem = foodItemList.get(foodItemList.size() - 1);
        assertThat(testFoodItem.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFoodItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodItemRepository.findAll().size();

        // Create the FoodItem with an existing ID
        foodItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodItemMockMvc.perform(post("/api/food-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(foodItem)))
            .andExpect(status().isBadRequest());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFoodItems() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        // Get all the foodItemList
        restFoodItemMockMvc.perform(get("/api/food-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getFoodItem() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        // Get the foodItem
        restFoodItemMockMvc.perform(get("/api/food-items/{id}", foodItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(foodItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getFoodItemsByIdFiltering() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        Long id = foodItem.getId();

        defaultFoodItemShouldBeFound("id.equals=" + id);
        defaultFoodItemShouldNotBeFound("id.notEquals=" + id);

        defaultFoodItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFoodItemShouldNotBeFound("id.greaterThan=" + id);

        defaultFoodItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFoodItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFoodItemsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        // Get all the foodItemList where name equals to DEFAULT_NAME
        defaultFoodItemShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the foodItemList where name equals to UPDATED_NAME
        defaultFoodItemShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFoodItemsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        // Get all the foodItemList where name not equals to DEFAULT_NAME
        defaultFoodItemShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the foodItemList where name not equals to UPDATED_NAME
        defaultFoodItemShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFoodItemsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        // Get all the foodItemList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFoodItemShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the foodItemList where name equals to UPDATED_NAME
        defaultFoodItemShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFoodItemsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        // Get all the foodItemList where name is not null
        defaultFoodItemShouldBeFound("name.specified=true");

        // Get all the foodItemList where name is null
        defaultFoodItemShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllFoodItemsByNameContainsSomething() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        // Get all the foodItemList where name contains DEFAULT_NAME
        defaultFoodItemShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the foodItemList where name contains UPDATED_NAME
        defaultFoodItemShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFoodItemsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        // Get all the foodItemList where name does not contain DEFAULT_NAME
        defaultFoodItemShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the foodItemList where name does not contain UPDATED_NAME
        defaultFoodItemShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllFoodItemsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);
        Owner owner = OwnerResourceIT.createEntity(em);
        em.persist(owner);
        em.flush();
        foodItem.setOwner(owner);
        foodItemRepository.saveAndFlush(foodItem);
        Long ownerId = owner.getId();

        // Get all the foodItemList where owner equals to ownerId
        defaultFoodItemShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the foodItemList where owner equals to ownerId + 1
        defaultFoodItemShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllFoodItemsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        foodItem.setCustomer(customer);
        foodItemRepository.saveAndFlush(foodItem);
        Long customerId = customer.getId();

        // Get all the foodItemList where customer equals to customerId
        defaultFoodItemShouldBeFound("customerId.equals=" + customerId);

        // Get all the foodItemList where customer equals to customerId + 1
        defaultFoodItemShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFoodItemShouldBeFound(String filter) throws Exception {
        restFoodItemMockMvc.perform(get("/api/food-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restFoodItemMockMvc.perform(get("/api/food-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFoodItemShouldNotBeFound(String filter) throws Exception {
        restFoodItemMockMvc.perform(get("/api/food-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFoodItemMockMvc.perform(get("/api/food-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFoodItem() throws Exception {
        // Get the foodItem
        restFoodItemMockMvc.perform(get("/api/food-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoodItem() throws Exception {
        // Initialize the database
        foodItemService.save(foodItem);

        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();

        // Update the foodItem
        FoodItem updatedFoodItem = foodItemRepository.findById(foodItem.getId()).get();
        // Disconnect from session so that the updates on updatedFoodItem are not directly saved in db
        em.detach(updatedFoodItem);
        updatedFoodItem
            .name(UPDATED_NAME);

        restFoodItemMockMvc.perform(put("/api/food-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFoodItem)))
            .andExpect(status().isOk());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);
        FoodItem testFoodItem = foodItemList.get(foodItemList.size() - 1);
        assertThat(testFoodItem.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFoodItem() throws Exception {
        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();

        // Create the FoodItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodItemMockMvc.perform(put("/api/food-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(foodItem)))
            .andExpect(status().isBadRequest());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoodItem() throws Exception {
        // Initialize the database
        foodItemService.save(foodItem);

        int databaseSizeBeforeDelete = foodItemRepository.findAll().size();

        // Delete the foodItem
        restFoodItemMockMvc.perform(delete("/api/food-items/{id}", foodItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
