package com.ua.lviv.iot;

import com.ua.lviv.iot.spares.CategoryEnum;
import com.ua.lviv.iot.spares.Spares;
import com.ua.lviv.iot.manager.SparesManagerX;
import com.ua.lviv.iot.spares.Wheels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WheelsTest {

    private Wheels wheel;

    public static final int SERIALNUMBERWHEEL = 185682;
    public static final int PRICEWHEEL = 487;
    public static final int NUMBEROFWHEEL = 56;
    public static final int SIZEWHEEL = 34;

    @BeforeEach
    void setUp() {
        ArrayList<Spares> sparesname = new ArrayList<>();
        ArrayList<Spares> enumList = new ArrayList<>();
        SparesManagerX sparesManager = new SparesManagerX(sparesname);
        wheel = new Wheels(1, SERIALNUMBERWHEEL, PRICEWHEEL, CategoryEnum.RunningGear, NUMBEROFWHEEL, SIZEWHEEL);
    }

    @Test
    void getSize() {
        assertEquals(SIZEWHEEL, wheel.getSize());
    }

    /*@Test
    void toString(){
        assertEquals("Serial number: " + SERIALNUMBERWHEEL
                + "  price: " + PRICEWHEEL
                + "$  Category: " + CategoryEnum.RunningGear.toString()
                + "  number of detailsl: " + NUMBEROFWHEEL
                + " pieces" + "  wheel size: "
                + SIZEWHEEL + "cm", wheel.toString());
    }*/
}