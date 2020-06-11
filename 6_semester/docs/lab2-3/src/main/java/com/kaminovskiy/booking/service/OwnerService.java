package com.kaminovskiy.booking.service;

import com.kaminovskiy.booking.domain.Owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Owner}.
 */
public interface OwnerService {

    /**
     * Save a owner.
     *
     * @param owner the entity to save.
     * @return the persisted entity.
     */
    Owner save(Owner owner);

    /**
     * Get all the owners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Owner> findAll(Pageable pageable);
    /**
     * Get all the OwnerDTO where Manager is {@code null}.
     *
     * @return the list of entities.
     */
    List<Owner> findAllWhereManagerIsNull();
    /**
     * Get all the OwnerDTO where Receptionist is {@code null}.
     *
     * @return the list of entities.
     */
    List<Owner> findAllWhereReceptionistIsNull();
    /**
     * Get all the OwnerDTO where Customer is {@code null}.
     *
     * @return the list of entities.
     */
    List<Owner> findAllWhereCustomerIsNull();
    /**
     * Get all the OwnerDTO where FoodItem is {@code null}.
     *
     * @return the list of entities.
     */
    List<Owner> findAllWhereFoodItemIsNull();
    /**
     * Get all the OwnerDTO where Bill is {@code null}.
     *
     * @return the list of entities.
     */
    List<Owner> findAllWhereBillIsNull();
    /**
     * Get all the OwnerDTO where Room is {@code null}.
     *
     * @return the list of entities.
     */
    List<Owner> findAllWhereRoomIsNull();

    /**
     * Get the "id" owner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Owner> findOne(Long id);

    /**
     * Delete the "id" owner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
