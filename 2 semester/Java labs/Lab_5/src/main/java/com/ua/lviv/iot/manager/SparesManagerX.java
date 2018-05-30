package com.ua.lviv.iot.manager;

import com.ua.lviv.iot.spares.CategoryEnum;
import com.ua.lviv.iot.spares.Spares;
import com.ua.lviv.iot.spares.Spares;
import com.ua.lviv.iot.persistence.dao.SparesDao;

import java.io.Serializable;
import java.util.*;

public class SparesManagerX implements Serializable {

    private static final long serialVersionUID = 1L;

    public SparesDao sparesDao;

    private ArrayList<Spares> sparesname;


    public SparesManagerX(final ArrayList<Spares> sparesname) {
        this.sparesname = sparesname;
    }








    public final void sortBySerialNumber() {
        sparesname.sort(Comparator.comparing(Spares::getSerialNumber));
    }

    public final void findSpares(final CategoryEnum enum1, ArrayList<Spares> enumList1) {//for each
        for (int index = 0; index < sparesname.size(); index++) {
            if (sparesname.get(index).getCategory() == enum1) {
                enumList1.add(sparesname.get(index));
            }
        }
    }

    public final StringBuilder arrayPrint(final ArrayList<Spares> sparesname) {
        StringBuilder s= new StringBuilder();
        for (int index2 = 0; index2 < sparesname.size(); index2++) {
            s.append(index2 + 1 + ". "
                    + sparesname.get(index2).toString()+"\n");}
            return s;
        }

}