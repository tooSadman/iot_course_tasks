package com.kaminovskiy.booking.service;

import com.kaminovskiy.booking.domain.Receptionist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Receptionist}.
 */
public interface ReceptionistService {

    /**
     * Save a receptionist.
     *
     * @param receptionist the entity to save.
     * @return the persisted entity.
     */
    Receptionist save(Receptionist receptionist);

    /**
     * Get all the receptionists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Receptionist> findAll(Pageable pageable);

    /**
     * Get the "id" receptionist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Receptionist> findOne(Long id);

    /**
     * Delete the "id" receptionist.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
