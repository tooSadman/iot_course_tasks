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

import com.kaminovskiy.booking.domain.Bill;
import com.kaminovskiy.booking.domain.*; // for static metamodels
import com.kaminovskiy.booking.repository.BillRepository;
import com.kaminovskiy.booking.service.dto.BillCriteria;

/**
 * Service for executing complex queries for {@link Bill} entities in the database.
 * The main input is a {@link BillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Bill} or a {@link Page} of {@link Bill} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BillQueryService extends QueryService<Bill> {

    private final Logger log = LoggerFactory.getLogger(BillQueryService.class);

    private final BillRepository billRepository;

    public BillQueryService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    /**
     * Return a {@link List} of {@link Bill} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Bill> findByCriteria(BillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bill> specification = createSpecification(criteria);
        return billRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Bill} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Bill> findByCriteria(BillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bill> specification = createSpecification(criteria);
        return billRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bill> specification = createSpecification(criteria);
        return billRepository.count(specification);
    }

    /**
     * Function to convert {@link BillCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Bill> createSpecification(BillCriteria criteria) {
        Specification<Bill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Bill_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumber(), Bill_.number));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildSpecification(criteria.getOwnerId(),
                    root -> root.join(Bill_.owner, JoinType.LEFT).get(Owner_.id)));
            }
            if (criteria.getReceptionistsId() != null) {
                specification = specification.and(buildSpecification(criteria.getReceptionistsId(),
                    root -> root.join(Bill_.receptionists, JoinType.LEFT).get(Receptionist_.id)));
            }
            if (criteria.getCustomersId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomersId(),
                    root -> root.join(Bill_.customers, JoinType.LEFT).get(Customer_.id)));
            }
        }
        return specification;
    }
}
