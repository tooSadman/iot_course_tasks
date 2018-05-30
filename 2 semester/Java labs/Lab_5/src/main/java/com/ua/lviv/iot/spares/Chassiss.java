package com.ua.lviv.iot.spares;

import com.ua.lviv.iot.spares.CategoryEnum;
import com.ua.lviv.iot.spares.Spares;

public class Chassiss extends Spares {
    public int numberof;

    public Chassiss(final Integer id, final int serialNumber, final int price, final CategoryEnum detailCategory, final int numberof) {
        super(id, serialNumber, price, detailCategory);
        this.numberof = numberof;
    }

    @Override
    public String toString() {
        return "Serial number: " + getSerialNumber()
                + "  price: " + getPrice()
                + "$  Category: " + getCategory()
                + "  number of detailsl: " + numberof
                + " pieces";
    }

    public String getHeaders() {
        return super.getHeaders() + "numberof ";
    }

    public  String toCSV() {
        return super.toCSV() + "," + numberof;
    }

    public final int getNumberOf(){
        return numberof;
    }

    public final void setNumberOf(int numberof){
        this.numberof = numberof;
    }
}
