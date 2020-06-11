package com.kaminovskiy.booking.web.rest;

import com.kaminovskiy.booking.BookingApp;
import com.kaminovskiy.booking.domain.Room;
import com.kaminovskiy.booking.domain.Owner;
import com.kaminovskiy.booking.domain.Customer;
import com.kaminovskiy.booking.domain.Receptionist;
import com.kaminovskiy.booking.repository.RoomRepository;
import com.kaminovskiy.booking.service.RoomService;
import com.kaminovskiy.booking.service.dto.RoomCriteria;
import com.kaminovskiy.booking.service.RoomQueryService;

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
 * Integration tests for the {@link RoomResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class RoomResourceIT {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;
    private static final Integer SMALLER_NUMBER = 1 - 1;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomQueryService roomQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomMockMvc;

    private Room room;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createEntity(EntityManager em) {
        Room room = new Room()
            .number(DEFAULT_NUMBER)
            .location(DEFAULT_LOCATION);
        return room;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createUpdatedEntity(EntityManager em) {
        Room room = new Room()
            .number(UPDATED_NUMBER)
            .location(UPDATED_LOCATION);
        return room;
    }

    @BeforeEach
    public void initTest() {
        room = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoom() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        // Create the Room
        restRoomMockMvc.perform(post("/api/rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isCreated());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate + 1);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testRoom.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void createRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        // Create the Room with an existing ID
        room.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomMockMvc.perform(post("/api/rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRooms() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList
        restRoomMockMvc.perform(get("/api/rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(room.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));
    }
    
    @Test
    @Transactional
    public void getRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get the room
        restRoomMockMvc.perform(get("/api/rooms/{id}", room.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(room.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION));
    }


    @Test
    @Transactional
    public void getRoomsByIdFiltering() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        Long id = room.getId();

        defaultRoomShouldBeFound("id.equals=" + id);
        defaultRoomShouldNotBeFound("id.notEquals=" + id);

        defaultRoomShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRoomShouldNotBeFound("id.greaterThan=" + id);

        defaultRoomShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRoomShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRoomsByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where number equals to DEFAULT_NUMBER
        defaultRoomShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the roomList where number equals to UPDATED_NUMBER
        defaultRoomShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRoomsByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where number not equals to DEFAULT_NUMBER
        defaultRoomShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the roomList where number not equals to UPDATED_NUMBER
        defaultRoomShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRoomsByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultRoomShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the roomList where number equals to UPDATED_NUMBER
        defaultRoomShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRoomsByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where number is not null
        defaultRoomShouldBeFound("number.specified=true");

        // Get all the roomList where number is null
        defaultRoomShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    public void getAllRoomsByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where number is greater than or equal to DEFAULT_NUMBER
        defaultRoomShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the roomList where number is greater than or equal to UPDATED_NUMBER
        defaultRoomShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRoomsByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where number is less than or equal to DEFAULT_NUMBER
        defaultRoomShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the roomList where number is less than or equal to SMALLER_NUMBER
        defaultRoomShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRoomsByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where number is less than DEFAULT_NUMBER
        defaultRoomShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the roomList where number is less than UPDATED_NUMBER
        defaultRoomShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRoomsByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where number is greater than DEFAULT_NUMBER
        defaultRoomShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the roomList where number is greater than SMALLER_NUMBER
        defaultRoomShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }


    @Test
    @Transactional
    public void getAllRoomsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where location equals to DEFAULT_LOCATION
        defaultRoomShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the roomList where location equals to UPDATED_LOCATION
        defaultRoomShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllRoomsByLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where location not equals to DEFAULT_LOCATION
        defaultRoomShouldNotBeFound("location.notEquals=" + DEFAULT_LOCATION);

        // Get all the roomList where location not equals to UPDATED_LOCATION
        defaultRoomShouldBeFound("location.notEquals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllRoomsByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultRoomShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the roomList where location equals to UPDATED_LOCATION
        defaultRoomShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllRoomsByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where location is not null
        defaultRoomShouldBeFound("location.specified=true");

        // Get all the roomList where location is null
        defaultRoomShouldNotBeFound("location.specified=false");
    }
                @Test
    @Transactional
    public void getAllRoomsByLocationContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where location contains DEFAULT_LOCATION
        defaultRoomShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the roomList where location contains UPDATED_LOCATION
        defaultRoomShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllRoomsByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where location does not contain DEFAULT_LOCATION
        defaultRoomShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the roomList where location does not contain UPDATED_LOCATION
        defaultRoomShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }


    @Test
    @Transactional
    public void getAllRoomsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);
        Owner owner = OwnerResourceIT.createEntity(em);
        em.persist(owner);
        em.flush();
        room.setOwner(owner);
        roomRepository.saveAndFlush(room);
        Long ownerId = owner.getId();

        // Get all the roomList where owner equals to ownerId
        defaultRoomShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the roomList where owner equals to ownerId + 1
        defaultRoomShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllRoomsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        room.setCustomer(customer);
        roomRepository.saveAndFlush(room);
        Long customerId = customer.getId();

        // Get all the roomList where customer equals to customerId
        defaultRoomShouldBeFound("customerId.equals=" + customerId);

        // Get all the roomList where customer equals to customerId + 1
        defaultRoomShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllRoomsByReceptionistIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);
        Receptionist receptionist = ReceptionistResourceIT.createEntity(em);
        em.persist(receptionist);
        em.flush();
        room.setReceptionist(receptionist);
        roomRepository.saveAndFlush(room);
        Long receptionistId = receptionist.getId();

        // Get all the roomList where receptionist equals to receptionistId
        defaultRoomShouldBeFound("receptionistId.equals=" + receptionistId);

        // Get all the roomList where receptionist equals to receptionistId + 1
        defaultRoomShouldNotBeFound("receptionistId.equals=" + (receptionistId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoomShouldBeFound(String filter) throws Exception {
        restRoomMockMvc.perform(get("/api/rooms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(room.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));

        // Check, that the count call also returns 1
        restRoomMockMvc.perform(get("/api/rooms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoomShouldNotBeFound(String filter) throws Exception {
        restRoomMockMvc.perform(get("/api/rooms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoomMockMvc.perform(get("/api/rooms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRoom() throws Exception {
        // Get the room
        restRoomMockMvc.perform(get("/api/rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoom() throws Exception {
        // Initialize the database
        roomService.save(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room
        Room updatedRoom = roomRepository.findById(room.getId()).get();
        // Disconnect from session so that the updates on updatedRoom are not directly saved in db
        em.detach(updatedRoom);
        updatedRoom
            .number(UPDATED_NUMBER)
            .location(UPDATED_LOCATION);

        restRoomMockMvc.perform(put("/api/rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoom)))
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testRoom.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Create the Room

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomMockMvc.perform(put("/api/rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(room)))
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoom() throws Exception {
        // Initialize the database
        roomService.save(room);

        int databaseSizeBeforeDelete = roomRepository.findAll().size();

        // Delete the room
        restRoomMockMvc.perform(delete("/api/rooms/{id}", room.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
