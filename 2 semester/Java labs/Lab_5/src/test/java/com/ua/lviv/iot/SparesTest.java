package com.ua.lviv.iot;

import com.ua.lviv.iot.spares.CategoryEnum;
import com.ua.lviv.iot.spares.Chassiss;
import com.ua.lviv.iot.spares.Spares;
import com.ua.lviv.iot.manager.SparesManagerX;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SparesTest {

    private Chassiss chassis1;

    public static final int SERIALNUMBERCHASSIS1 = 182348;
    public static final int PRICECHASSIS1 = 345;
    public static final int NUMBEROFCHASSIS1 = 4;

    @BeforeEach
    void setUp() {
        ArrayList<Spares> sparesname = new ArrayList<>();
        ArrayList<Spares> enumList = new ArrayList<>();
        SparesManagerX sparesManager = new SparesManagerX(sparesname);
        chassis1 = new Chassiss(6, SERIALNUMBERCHASSIS1, PRICECHASSIS1, CategoryEnum.RunningGear, NUMBEROFCHASSIS1);
    }

    @Test
    void getSerialNumber() {
        assertEquals(SERIALNUMBERCHASSIS1, chassis1.getSerialNumber());

    }

    @Test
    void getPrice() {
        assertEquals(PRICECHASSIS1, chassis1.getPrice());
    }

    @Test
    void getCategory() {
        assertEquals(CategoryEnum.RunningGear, chassis1.getCategory());
    }
}