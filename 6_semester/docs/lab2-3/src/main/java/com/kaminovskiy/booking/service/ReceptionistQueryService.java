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

import com.kaminovskiy.booking.domain.Receptionist;
import com.kaminovskiy.booking.domain.*; // for static metamodels
import com.kaminovskiy.booking.repository.ReceptionistRepository;
import com.kaminovskiy.booking.service.dto.ReceptionistCriteria;

/**
 * Service for executing complex queries for {@link Receptionist} entities in the database.
 * The main input is a {@link ReceptionistCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Receptionist} or a {@link Page} of {@link Receptionist} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReceptionistQueryService extends QueryService<Receptionist> {

    private final Logger log = LoggerFactory.getLogger(ReceptionistQueryService.class);

    private final ReceptionistRepository receptionistRepository;

    public ReceptionistQueryService(ReceptionistRepository receptionistRepository) {
        this.receptionistRepository = receptionistRepository;
    }

    /**
     * Return a {@link List} of {@link Receptionist} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Receptionist> findByCriteria(ReceptionistCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Receptionist> specification = createSpecification(criteria);
        return receptionistRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Receptionist} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Receptionist> findByCriteria(ReceptionistCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Receptionist> specification = createSpecification(criteria);
        return receptionistRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReceptionistCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Receptionist> specification = createSpecification(criteria);
        return receptionistRepository.count(specification);
    }

    /**
     * Function to convert {@link ReceptionistCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Receptionist> createSpecification(ReceptionistCriteria criteria) {
        Specification<Receptionist> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Receptionist_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Receptionist_.name));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Receptionist_.phone));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Receptionist_.address));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildSpecification(criteria.getOwnerId(),
                    root -> root.join(Receptionist_.owner, JoinType.LEFT).get(Owner_.id)));
            }
            if (criteria.getManagedRoomsId() != null) {
                specification = specification.and(buildSpecification(criteria.getManagedRoomsId(),
                    root -> root.join(Receptionist_.managedRooms, JoinType.LEFT).get(Room_.id)));
            }
            if (criteria.getBillId() != null) {
                specification = specification.and(buildSpecification(criteria.getBillId(),
                    root -> root.join(Receptionist_.bill, JoinType.LEFT).get(Bill_.id)));
            }
        }
        return specification;
    }
}
