package com.ua.lviv.iot.manager;

import com.ua.lviv.iot.spares.CategoryEnum;
import com.ua.lviv.iot.spares.Spares;
import com.ua.lviv.iot.persistence.dao.SparesDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SparesManager implements Serializable {

    private static final long serialVersionUID = 1L;

    public SparesDao sparesDao;

    private static Map<Integer, Spares> sparesMap = new HashMap<>();

    public SparesManager(ArrayList<Spares> spares) {
    }

    public Map<Integer, Spares> getSparesMap() {
        return sparesMap;
    }

    public void setSparesMap(Map<Integer, Spares> sparesList) {
        SparesManager.sparesMap = sparesList;

    }

    public Spares getSpare(Integer id) {
        return sparesDao.findById(id);
    }

    public final void addSpare(final Spares newSpare) {
        sparesDao.persist(newSpare);
    }

    public  void updateSpare(Spares newSpare) {
        sparesDao.update(newSpare);
    }

    public  void deleteSpare(Integer id) {
        sparesDao.delete(id);
    }


    /*public Map<Integer, Spares> searchByCategory(CategoryEnum categoryEnum) {
        Map<Integer, Spares> listInput = new HashMap<>();
        for (Map.Entry<Integer, Spares> spares : sparesMap.entrySet()) {
            if (spares.getValue().categoryEnum == categoryEnum) {
                listInput.put(spares.getValue().getId(), spares.getValue());
            }
        }
        return listInput;
    }*/
}
