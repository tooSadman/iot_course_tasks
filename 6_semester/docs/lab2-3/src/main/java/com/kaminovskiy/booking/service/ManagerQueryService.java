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

import com.kaminovskiy.booking.domain.Manager;
import com.kaminovskiy.booking.domain.*; // for static metamodels
import com.kaminovskiy.booking.repository.ManagerRepository;
import com.kaminovskiy.booking.service.dto.ManagerCriteria;

/**
 * Service for executing complex queries for {@link Manager} entities in the database.
 * The main input is a {@link ManagerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Manager} or a {@link Page} of {@link Manager} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ManagerQueryService extends QueryService<Manager> {

    private final Logger log = LoggerFactory.getLogger(ManagerQueryService.class);

    private final ManagerRepository managerRepository;

    public ManagerQueryService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    /**
     * Return a {@link List} of {@link Manager} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Manager> findByCriteria(ManagerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Manager> specification = createSpecification(criteria);
        return managerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Manager} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Manager> findByCriteria(ManagerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Manager> specification = createSpecification(criteria);
        return managerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ManagerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Manager> specification = createSpecification(criteria);
        return managerRepository.count(specification);
    }

    /**
     * Function to convert {@link ManagerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Manager> createSpecification(ManagerCriteria criteria) {
        Specification<Manager> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Manager_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Manager_.name));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Manager_.phone));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildSpecification(criteria.getOwnerId(),
                    root -> root.join(Manager_.owner, JoinType.LEFT).get(Owner_.id)));
            }
            if (criteria.getCustomersId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomersId(),
                    root -> root.join(Manager_.customers, JoinType.LEFT).get(Customer_.id)));
            }
            if (criteria.getInventoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryId(),
                    root -> root.join(Manager_.inventory, JoinType.LEFT).get(Inventory_.id)));
            }
        }
        return specification;
    }
}
