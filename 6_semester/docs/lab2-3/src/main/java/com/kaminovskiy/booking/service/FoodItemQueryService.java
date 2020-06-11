package com.kaminovskiy.booking.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.kaminovskiy.booking.domain.FoodItem;
import com.kaminovskiy.booking.domain.*; // for static metamodels
import com.kaminovskiy.booking.repository.FoodItemRepository;
import com.kaminovskiy.booking.service.dto.FoodItemCriteria;

/**
 * Service for executing complex queries for {@link FoodItem} entities in the database.
 * The main input is a {@link FoodItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FoodItem} or a {@link Page} of {@link FoodItem} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FoodItemQueryService extends QueryService<FoodItem> {

    private final Logger log = LoggerFactory.getLogger(FoodItemQueryService.class);

    private final FoodItemRepository foodItemRepository;

    public FoodItemQueryService(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    /**
     * Return a {@link List} of {@link FoodItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FoodItem> findByCriteria(FoodItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FoodItem> specification = createSpecification(criteria);
        return foodItemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FoodItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FoodItem> findByCriteria(FoodItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FoodItem> specification = createSpecification(criteria);
        return foodItemRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FoodItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FoodItem> specification = createSpecification(criteria);
        return foodItemRepository.count(specification);
    }

    /**
     * Function to convert {@link FoodItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FoodItem> createSpecification(FoodItemCriteria criteria) {
        Specification<FoodItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FoodItem_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), FoodItem_.name));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildSpecification(criteria.getOwnerId(),
                    root -> root.join(FoodItem_.owner, JoinType.LEFT).get(Owner_.id)));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(FoodItem_.customer, JoinType.LEFT).get(Customer_.id)));
            }
        }
        return specification;
    }
}
