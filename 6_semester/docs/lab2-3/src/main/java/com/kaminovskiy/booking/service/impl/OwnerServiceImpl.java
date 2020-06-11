package com.kaminovskiy.booking.service.impl;

import com.kaminovskiy.booking.service.OwnerService;
import com.kaminovskiy.booking.domain.Owner;
import com.kaminovskiy.booking.repository.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Owner}.
 */
@Service
@Transactional
public class OwnerServiceImpl implements OwnerService {

    private final Logger log = LoggerFactory.getLogger(OwnerServiceImpl.class);

    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    /**
     * Save a owner.
     *
     * @param owner the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Owner save(Owner owner) {
        log.debug("Request to save Owner : {}", owner);
        return ownerRepository.save(owner);
    }

    /**
     * Get all the owners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Owner> findAll(Pageable pageable) {
        log.debug("Request to get all Owners");
        return ownerRepository.findAll(pageable);
    }


    /**
     *  Get all the owners where Manager is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Owner> findAllWhereManagerIsNull() {
        log.debug("Request to get all owners where Manager is null");
        return StreamSupport
            .stream(ownerRepository.findAll().spliterator(), false)
            .filter(owner -> owner.getManager() == null)
            .collect(Collectors.toList());
    }


    /**
     *  Get all the owners where Receptionist is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Owner> findAllWhereReceptionistIsNull() {
        log.debug("Request to get all owners where Receptionist is null");
        return StreamSupport
            .stream(ownerRepository.findAll().spliterator(), false)
            .filter(owner -> owner.getReceptionist() == null)
            .collect(Collectors.toList());
    }


    /**
     *  Get all the owners where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Owner> findAllWhereCustomerIsNull() {
        log.debug("Request to get all owners where Customer is null");
        return StreamSupport
            .stream(ownerRepository.findAll().spliterator(), false)
            .filter(owner -> owner.getCustomer() == null)
            .collect(Collectors.toList());
    }


    /**
     *  Get all the owners where FoodItem is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Owner> findAllWhereFoodItemIsNull() {
        log.debug("Request to get all owners where FoodItem is null");
        return StreamSupport
            .stream(ownerRepository.findAll().spliterator(), false)
            .filter(owner -> owner.getFoodItem() == null)
            .collect(Collectors.toList());
    }


    /**
     *  Get all the owners where Bill is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Owner> findAllWhereBillIsNull() {
        log.debug("Request to get all owners where Bill is null");
        return StreamSupport
            .stream(ownerRepository.findAll().spliterator(), false)
            .filter(owner -> owner.getBill() == null)
            .collect(Collectors.toList());
    }


    /**
     *  Get all the owners where Room is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Owner> findAllWhereRoomIsNull() {
        log.debug("Request to get all owners where Room is null");
        return StreamSupport
            .stream(ownerRepository.findAll().spliterator(), false)
            .filter(owner -> owner.getRoom() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one owner by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Owner> findOne(Long id) {
        log.debug("Request to get Owner : {}", id);
        return ownerRepository.findById(id);
    }

    /**
     * Delete the owner by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Owner : {}", id);
        ownerRepository.deleteById(id);
    }
}
