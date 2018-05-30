package com.ua.lviv.iot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    ArrayList<Spares> sparesname = new ArrayList<>();
    SparesManager sparesManager = new SparesManager(sparesname);
    String inputNumber;
    Scanner mainmenuScanner = new Scanner(System.in); // Reading from System.in

    do {

      System.out.println("\n"+"\n"+
          "Choose an option:" + "\n" + "1. Create 4 objcets and add them to array." + "\n" + "2. Output array." + "\n"
              + "3. Sort array by serial number." + "\n" + "4. Find details by running gear " + "\n" + "0. Close.");

      inputNumber = mainmenuScanner.next(); // Scans the next token of the input.

      switch (inputNumber) {
      case "1":{
        Chassis chassis1 = new Chassis(182348, 345, CategoryEnum.RunningGear, 4);
        Wheels wheel = new Wheels(185682, 487, CategoryEnum.RunningGear, 56 , 34);
        Electronics smallTv = new Electronics(128908, 400, CategoryEnum.Electronics, 35);
        Electronics sensors = new Electronics(125688, 200, CategoryEnum.Electronics, 48);
        sparesname.add(chassis1);
        sparesname.add(wheel);
        sparesname.add(smallTv);
        sparesname.add(sensors);
        break;
      }

      case "2": {
        System.out.println("Array output: ");
        sparesManager.arrayPrint();
        break;
      }

      case "3": {
        System.out.println("\n" + "Sorted by serial number array: ");
        sparesManager.sortBySerialNumber();
        sparesManager.arrayPrint();
        break;
      }

      case "4": {
        System.out.println("\n" + "Find running gear details:");
        sparesManager.findSpares(CategoryEnum.RunningGear);
        break;
      }

        case "5":{
        System.out.println("sadklasjd");
        break;
      }

      }
    } while (!inputNumber.equals("0"));

  }
}
