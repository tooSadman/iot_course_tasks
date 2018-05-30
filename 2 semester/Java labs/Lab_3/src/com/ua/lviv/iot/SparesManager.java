package com.ua.lviv.iot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class SparesManager {
  private ArrayList<Spares> sparesname;

  public SparesManager(ArrayList<Spares> sparesname) {
    this.sparesname = sparesname;
  }

  public void sortBySerialNumber() {
    sparesname.sort(Comparator.comparing(Spares::getSerialNumber));
  }

  public void findSpares(CategoryEnum enum1) {//for each
    ArrayList<Spares> enumList = new ArrayList<>();
    for (int index = 0; index < sparesname.size(); index++) {
      if (sparesname.get(index).getCategory() == enum1) {
        enumList.add(sparesname.get(index));
      }
    }
    for (int index1 = 0; index1 < enumList.size(); index1++) {
      System.out.println((index1+1)+". " + enumList.get(index1).getSerialNumber()/* + "-" + enumList.get(index1).getName()*/);
    }
  }

  public void arrayPrint() {
    for (int index2 = 0; index2 < sparesname.size(); index2++) {
      System.out.println((index2+1)+sparesname.get(index2).toString());
    }
  }
}
