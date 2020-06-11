package com.kaminovskiy.booking.service.impl;

import com.kaminovskiy.booking.service.FoodItemService;
import com.kaminovskiy.booking.domain.FoodItem;
import com.kaminovskiy.booking.repository.FoodItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FoodItem}.
 */
@Service
@Transactional
public class FoodItemServiceImpl implements FoodItemService {

    private final Logger log = LoggerFactory.getLogger(FoodItemServiceImpl.class);

    private final FoodItemRepository foodItemRepository;

    public FoodItemServiceImpl(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    /**
     * Save a foodItem.
     *
     * @param foodItem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FoodItem save(FoodItem foodItem) {
        log.debug("Request to save FoodItem : {}", foodItem);
        return foodItemRepository.save(foodItem);
    }

    /**
     * Get all the foodItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FoodItem> findAll(Pageable pageable) {
        log.debug("Request to get all FoodItems");
        return foodItemRepository.findAll(pageable);
    }

    /**
     * Get one foodItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FoodItem> findOne(Long id) {
        log.debug("Request to get FoodItem : {}", id);
        return foodItemRepository.findById(id);
    }

    /**
     * Delete the foodItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FoodItem : {}", id);
        foodItemRepository.deleteById(id);
    }
}
