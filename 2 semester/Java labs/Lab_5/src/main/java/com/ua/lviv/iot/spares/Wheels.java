package com.ua.lviv.iot.spares;

public class Wheels extends Chassiss {
    private int size;
    private int id;

    public Wheels(final Integer id, final int serialNumber, final int price, final CategoryEnum detailCategory, final int numberof, final int size) {
        super(id, serialNumber, price, detailCategory, numberof);
        this.size = size;
    }

    @Override
    public String toString() {
        return "Serial number: " + getSerialNumber()
                + "  price: " + getPrice()
                + "$  Category: " + getCategory()
                + "  number of detailsl: " + numberof
                + " pieces" + "  wheel size: "
                + size + "cm";
    }

    public String getHeaders() {
        return super.getHeaders() + "size ";
    }

    public  String toCSV() {
        return super.toCSV() + "," + size;
    }

    //  getter/Setters

    public final int getSize() {
        return size;
    }

    public final void setSize(int size) {
        this.size = size;
    }
}

