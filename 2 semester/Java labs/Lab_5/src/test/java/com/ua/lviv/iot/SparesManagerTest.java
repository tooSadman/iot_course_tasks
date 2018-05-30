package com.ua.lviv.iot;

import com.ua.lviv.iot.manager.SparesManagerX;
import com.ua.lviv.iot.spares.*;
import com.ua.lviv.iot.writer.SparesWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SparesManagerTest {

    private Chassiss chassis1;
    private Wheels wheel;
    private Electronics smallTv;
    private Electronics sensors;
    private ArrayList<Spares> sparesname;
    private ArrayList<Spares> enumList;
    private SparesManagerX sparesManager;


    public static final int SERIALNUMBERCHASSIS1 = 182348;
    public static final int PRICECHASSIS1 = 345;
    public static final int NUMBEROFCHASSIS1 = 4;
    public static final int SERIALNUMBERWHEEL = 185682;
    public static final int PRICEWHEEL = 487;
    public static final int NUMBEROFWHEEL = 56;
    public static final int SIZEWHEEL = 34;
    public static final int SERIALNUMBERSMALLTV = 128908;
    public static final int PRICESMALLTV = 400;
    public static final int TYPESMALLTV = 35;
    public static final int SERIALNUMBERSENSORS = 125688;
    public static final int PRICESENSORS = 200;
    public static final int TYPESENSORS = 48;

    @BeforeEach
    void setUp() {
        sparesname = new ArrayList<>();
        enumList = new ArrayList<>();
        sparesManager = new SparesManagerX(sparesname);

        chassis1 = new Chassiss(1, SERIALNUMBERCHASSIS1, PRICECHASSIS1, CategoryEnum.RunningGear, NUMBEROFCHASSIS1);
        wheel = new Wheels(2, SERIALNUMBERWHEEL, PRICEWHEEL, CategoryEnum.RunningGear, NUMBEROFWHEEL, SIZEWHEEL);
        smallTv = new Electronics(3, SERIALNUMBERSMALLTV, PRICESMALLTV, CategoryEnum.Electronics, TYPESMALLTV);
        sensors = new Electronics(4, SERIALNUMBERSENSORS, PRICESENSORS, CategoryEnum.Electronics, TYPESENSORS);
        sparesname.add(chassis1);
        sparesname.add(wheel);
        sparesname.add(smallTv);
        sparesname.add(sensors);
    }

    @Test
    void sortBySerialNumber() {
        List<Spares> sparesnameTest = new ArrayList<>();
        sparesnameTest.add(sensors);
        sparesnameTest.add(smallTv);
        sparesnameTest.add(chassis1);
        sparesnameTest.add(wheel);
        sparesManager.sortBySerialNumber();
        assertEquals(sparesnameTest, sparesname);
    }

    @Test
    void findSpares() {
        List<Spares> enumListTest = new ArrayList<>();
        enumListTest.add(smallTv);
        enumListTest.add(sensors);
        sparesManager.findSpares(CategoryEnum.Electronics, enumList);
        assertEquals(enumListTest, enumList);
    }

    @Test
    void arrayPrint() {
        StringBuilder ss = new StringBuilder();
        ss.append("1. Serial number: 182348  price: 345$  Category: RunningGear  number of detailsl: 4 pieces" + "\n" +
                "2. Serial number: 185682  price: 487$  Category: RunningGear  number of detailsl: 56 pieces  wheel size: 34cm" + "\n" +
                "3. Serial number: 128908  price: 400$  Category: Electronics  detail type: 35cm" + "\n" +
                "4. Serial number: 125688  price: 200$  Category: Electronics  detail type: 48cm" + "\n");
        assertEquals(ss, sparesManager.arrayPrint(sparesname));
    }

    @Test
    void writeToFile(){
        SparesWriter sparesWriter = new SparesWriter();
        sparesWriter.writeToFile(enumList);
    }
}