package com.ua.lviv.iot.spares;

import com.ua.lviv.iot.spares.CategoryEnum;
import com.ua.lviv.iot.spares.Spares;

public class Electronics extends Spares {
    private int type;

    public Electronics(final Integer id, final int serialNumber, final int price, final CategoryEnum detailCategory, final int type) {
        super(id, serialNumber, price, detailCategory);
        this.type = type;
    }


    @Override
    public final String toString() {
        return "Serial number: " + getSerialNumber()
                + "  price: " + getPrice()
                + "$  Category: " + getCategory()
                + "  detail type: " + type
                + "cm";
    }

    public String getHeaders() {
        return super.getHeaders() + "type ";
    }

    public String toCSV() {
        return super.toCSV() + "," + type;
    }

    public final int getType() {
        return type;
    }

    public void setType(int type){this.type = type;}

}
