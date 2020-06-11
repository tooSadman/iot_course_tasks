package com.kaminovskiy.booking.web.rest;

import com.kaminovskiy.booking.domain.FoodItem;
import com.kaminovskiy.booking.service.FoodItemService;
import com.kaminovskiy.booking.web.rest.errors.BadRequestAlertException;
import com.kaminovskiy.booking.service.dto.FoodItemCriteria;
import com.kaminovskiy.booking.service.FoodItemQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kaminovskiy.booking.domain.FoodItem}.
 */
@RestController
@RequestMapping("/api")
public class FoodItemResource {

    private final Logger log = LoggerFactory.getLogger(FoodItemResource.class);

    private static final String ENTITY_NAME = "foodItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoodItemService foodItemService;

    private final FoodItemQueryService foodItemQueryService;

    public FoodItemResource(FoodItemService foodItemService, FoodItemQueryService foodItemQueryService) {
        this.foodItemService = foodItemService;
        this.foodItemQueryService = foodItemQueryService;
    }

    /**
     * {@code POST  /food-items} : Create a new foodItem.
     *
     * @param foodItem the foodItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foodItem, or with status {@code 400 (Bad Request)} if the foodItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/food-items")
    public ResponseEntity<FoodItem> createFoodItem(@RequestBody FoodItem foodItem) throws URISyntaxException {
        log.debug("REST request to save FoodItem : {}", foodItem);
        if (foodItem.getId() != null) {
            throw new BadRequestAlertException("A new foodItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodItem result = foodItemService.save(foodItem);
        return ResponseEntity.created(new URI("/api/food-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /food-items} : Updates an existing foodItem.
     *
     * @param foodItem the foodItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodItem,
     * or with status {@code 400 (Bad Request)} if the foodItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foodItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/food-items")
    public ResponseEntity<FoodItem> updateFoodItem(@RequestBody FoodItem foodItem) throws URISyntaxException {
        log.debug("REST request to update FoodItem : {}", foodItem);
        if (foodItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoodItem result = foodItemService.save(foodItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, foodItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /food-items} : get all the foodItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foodItems in body.
     */
    @GetMapping("/food-items")
    public ResponseEntity<List<FoodItem>> getAllFoodItems(FoodItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FoodItems by criteria: {}", criteria);
        Page<FoodItem> page = foodItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /food-items/count} : count all the foodItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/food-items/count")
    public ResponseEntity<Long> countFoodItems(FoodItemCriteria criteria) {
        log.debug("REST request to count FoodItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(foodItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /food-items/:id} : get the "id" foodItem.
     *
     * @param id the id of the foodItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foodItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/food-items/{id}")
    public ResponseEntity<FoodItem> getFoodItem(@PathVariable Long id) {
        log.debug("REST request to get FoodItem : {}", id);
        Optional<FoodItem> foodItem = foodItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foodItem);
    }

    /**
     * {@code DELETE  /food-items/:id} : delete the "id" foodItem.
     *
     * @param id the id of the foodItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/food-items/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        log.debug("REST request to delete FoodItem : {}", id);
        foodItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
