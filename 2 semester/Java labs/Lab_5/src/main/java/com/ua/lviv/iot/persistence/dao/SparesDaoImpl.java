package com.ua.lviv.iot.persistence.dao;

import com.ua.lviv.iot.spares.Spares;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;


@Named
@Dependent
public class SparesDaoImpl extends AbstractDao<Spares> implements SparesDao, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    protected Class<Spares> getEntityClass() {
        return Spares.class;
    }


}
