package com.kaminovskiy.booking.web.rest;

import com.kaminovskiy.booking.BookingApp;
import com.kaminovskiy.booking.domain.Bill;
import com.kaminovskiy.booking.domain.Owner;
import com.kaminovskiy.booking.domain.Receptionist;
import com.kaminovskiy.booking.domain.Customer;
import com.kaminovskiy.booking.repository.BillRepository;
import com.kaminovskiy.booking.service.BillService;
import com.kaminovskiy.booking.service.dto.BillCriteria;
import com.kaminovskiy.booking.service.BillQueryService;

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
 * Integration tests for the {@link BillResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BillResourceIT {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;
    private static final Integer SMALLER_NUMBER = 1 - 1;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillService billService;

    @Autowired
    private BillQueryService billQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillMockMvc;

    private Bill bill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bill createEntity(EntityManager em) {
        Bill bill = new Bill()
            .number(DEFAULT_NUMBER);
        return bill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bill createUpdatedEntity(EntityManager em) {
        Bill bill = new Bill()
            .number(UPDATED_NUMBER);
        return bill;
    }

    @BeforeEach
    public void initTest() {
        bill = createEntity(em);
    }

    @Test
    @Transactional
    public void createBill() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // Create the Bill
        restBillMockMvc.perform(post("/api/bills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bill)))
            .andExpect(status().isCreated());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate + 1);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createBillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // Create the Bill with an existing ID
        bill.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillMockMvc.perform(post("/api/bills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bill)))
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBills() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList
        restBillMockMvc.perform(get("/api/bills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bill.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }


    @Test
    @Transactional
    public void getBillsByIdFiltering() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        Long id = bill.getId();

        defaultBillShouldBeFound("id.equals=" + id);
        defaultBillShouldNotBeFound("id.notEquals=" + id);

        defaultBillShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBillShouldNotBeFound("id.greaterThan=" + id);

        defaultBillShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBillShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBillsByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where number equals to DEFAULT_NUMBER
        defaultBillShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the billList where number equals to UPDATED_NUMBER
        defaultBillShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBillsByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where number not equals to DEFAULT_NUMBER
        defaultBillShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the billList where number not equals to UPDATED_NUMBER
        defaultBillShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBillsByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultBillShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the billList where number equals to UPDATED_NUMBER
        defaultBillShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBillsByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where number is not null
        defaultBillShouldBeFound("number.specified=true");

        // Get all the billList where number is null
        defaultBillShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillsByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where number is greater than or equal to DEFAULT_NUMBER
        defaultBillShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the billList where number is greater than or equal to UPDATED_NUMBER
        defaultBillShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBillsByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where number is less than or equal to DEFAULT_NUMBER
        defaultBillShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the billList where number is less than or equal to SMALLER_NUMBER
        defaultBillShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBillsByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where number is less than DEFAULT_NUMBER
        defaultBillShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the billList where number is less than UPDATED_NUMBER
        defaultBillShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBillsByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where number is greater than DEFAULT_NUMBER
        defaultBillShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the billList where number is greater than SMALLER_NUMBER
        defaultBillShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }


    @Test
    @Transactional
    public void getAllBillsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);
        Owner owner = OwnerResourceIT.createEntity(em);
        em.persist(owner);
        em.flush();
        bill.setOwner(owner);
        billRepository.saveAndFlush(bill);
        Long ownerId = owner.getId();

        // Get all the billList where owner equals to ownerId
        defaultBillShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the billList where owner equals to ownerId + 1
        defaultBillShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllBillsByReceptionistsIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);
        Receptionist receptionists = ReceptionistResourceIT.createEntity(em);
        em.persist(receptionists);
        em.flush();
        bill.addReceptionists(receptionists);
        billRepository.saveAndFlush(bill);
        Long receptionistsId = receptionists.getId();

        // Get all the billList where receptionists equals to receptionistsId
        defaultBillShouldBeFound("receptionistsId.equals=" + receptionistsId);

        // Get all the billList where receptionists equals to receptionistsId + 1
        defaultBillShouldNotBeFound("receptionistsId.equals=" + (receptionistsId + 1));
    }


    @Test
    @Transactional
    public void getAllBillsByCustomersIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);
        Customer customers = CustomerResourceIT.createEntity(em);
        em.persist(customers);
        em.flush();
        bill.addCustomers(customers);
        billRepository.saveAndFlush(bill);
        Long customersId = customers.getId();

        // Get all the billList where customers equals to customersId
        defaultBillShouldBeFound("customersId.equals=" + customersId);

        // Get all the billList where customers equals to customersId + 1
        defaultBillShouldNotBeFound("customersId.equals=" + (customersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBillShouldBeFound(String filter) throws Exception {
        restBillMockMvc.perform(get("/api/bills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));

        // Check, that the count call also returns 1
        restBillMockMvc.perform(get("/api/bills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBillShouldNotBeFound(String filter) throws Exception {
        restBillMockMvc.perform(get("/api/bills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBillMockMvc.perform(get("/api/bills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBill() throws Exception {
        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBill() throws Exception {
        // Initialize the database
        billService.save(bill);

        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Update the bill
        Bill updatedBill = billRepository.findById(bill.getId()).get();
        // Disconnect from session so that the updates on updatedBill are not directly saved in db
        em.detach(updatedBill);
        updatedBill
            .number(UPDATED_NUMBER);

        restBillMockMvc.perform(put("/api/bills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBill)))
            .andExpect(status().isOk());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingBill() throws Exception {
        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Create the Bill

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillMockMvc.perform(put("/api/bills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bill)))
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBill() throws Exception {
        // Initialize the database
        billService.save(bill);

        int databaseSizeBeforeDelete = billRepository.findAll().size();

        // Delete the bill
        restBillMockMvc.perform(delete("/api/bills/{id}", bill.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
