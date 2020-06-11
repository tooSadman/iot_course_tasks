package com.kaminovskiy.booking.service;

import com.kaminovskiy.booking.domain.FoodItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link FoodItem}.
 */
public interface FoodItemService {

    /**
     * Save a foodItem.
     *
     * @param foodItem the entity to save.
     * @return the persisted entity.
     */
    FoodItem save(FoodItem foodItem);

    /**
     * Get all the foodItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FoodItem> findAll(Pageable pageable);

    /**
     * Get the "id" foodItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FoodItem> findOne(Long id);

    /**
     * Delete the "id" foodItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
