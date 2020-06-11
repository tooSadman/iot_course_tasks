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

import com.kaminovskiy.booking.domain.Owner;
import com.kaminovskiy.booking.domain.*; // for static metamodels
import com.kaminovskiy.booking.repository.OwnerRepository;
import com.kaminovskiy.booking.service.dto.OwnerCriteria;

/**
 * Service for executing complex queries for {@link Owner} entities in the database.
 * The main input is a {@link OwnerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Owner} or a {@link Page} of {@link Owner} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OwnerQueryService extends QueryService<Owner> {

    private final Logger log = LoggerFactory.getLogger(OwnerQueryService.class);

    private final OwnerRepository ownerRepository;

    public OwnerQueryService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    /**
     * Return a {@link List} of {@link Owner} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Owner> findByCriteria(OwnerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Owner> specification = createSpecification(criteria);
        return ownerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Owner} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Owner> findByCriteria(OwnerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Owner> specification = createSpecification(criteria);
        return ownerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OwnerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Owner> specification = createSpecification(criteria);
        return ownerRepository.count(specification);
    }

    /**
     * Function to convert {@link OwnerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Owner> createSpecification(OwnerCriteria criteria) {
        Specification<Owner> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Owner_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Owner_.name));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Owner_.phone));
            }
            if (criteria.getManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getManagerId(),
                    root -> root.join(Owner_.manager, JoinType.LEFT).get(Manager_.id)));
            }
            if (criteria.getReceptionistId() != null) {
                specification = specification.and(buildSpecification(criteria.getReceptionistId(),
                    root -> root.join(Owner_.receptionist, JoinType.LEFT).get(Receptionist_.id)));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(Owner_.customer, JoinType.LEFT).get(Customer_.id)));
            }
            if (criteria.getFoodItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getFoodItemId(),
                    root -> root.join(Owner_.foodItem, JoinType.LEFT).get(FoodItem_.id)));
            }
            if (criteria.getBillId() != null) {
                specification = specification.and(buildSpecification(criteria.getBillId(),
                    root -> root.join(Owner_.bill, JoinType.LEFT).get(Bill_.id)));
            }
            if (criteria.getRoomId() != null) {
                specification = specification.and(buildSpecification(criteria.getRoomId(),
                    root -> root.join(Owner_.room, JoinType.LEFT).get(Room_.id)));
            }
        }
        return specification;
    }
}
