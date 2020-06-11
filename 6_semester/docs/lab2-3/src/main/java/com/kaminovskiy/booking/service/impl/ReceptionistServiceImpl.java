package com.kaminovskiy.booking.service.impl;

import com.kaminovskiy.booking.service.ReceptionistService;
import com.kaminovskiy.booking.domain.Receptionist;
import com.kaminovskiy.booking.repository.ReceptionistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Receptionist}.
 */
@Service
@Transactional
public class ReceptionistServiceImpl implements ReceptionistService {

    private final Logger log = LoggerFactory.getLogger(ReceptionistServiceImpl.class);

    private final ReceptionistRepository receptionistRepository;

    public ReceptionistServiceImpl(ReceptionistRepository receptionistRepository) {
        this.receptionistRepository = receptionistRepository;
    }

    /**
     * Save a receptionist.
     *
     * @param receptionist the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Receptionist save(Receptionist receptionist) {
        log.debug("Request to save Receptionist : {}", receptionist);
        return receptionistRepository.save(receptionist);
    }

    /**
     * Get all the receptionists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Receptionist> findAll(Pageable pageable) {
        log.debug("Request to get all Receptionists");
        return receptionistRepository.findAll(pageable);
    }

    /**
     * Get one receptionist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Receptionist> findOne(Long id) {
        log.debug("Request to get Receptionist : {}", id);
        return receptionistRepository.findById(id);
    }

    /**
     * Delete the receptionist by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Receptionist : {}", id);
        receptionistRepository.deleteById(id);
    }
}
