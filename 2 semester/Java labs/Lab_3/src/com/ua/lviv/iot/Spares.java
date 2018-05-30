package com.ua.lviv.iot;

public abstract class Spares {
    private int serialNumber;
    private int price;
    private CategoryEnum detailCategory;
    private String name;

    public Spares(int serialNumber, int price, CategoryEnum detailCategory/*, String name*/) {
        this.serialNumber = serialNumber;
        this.price = price;
        this.detailCategory = detailCategory;
        //  this.name = name;
    }

    //  Setters and getters

    public String getType() {
        return "Spare: " + "/n"
                + "serialNumber" + serialNumber + "/n"
                + "price: " + price + "/n"
                + "name: " + name + "/n"
                + "Category: " + detailCategory;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public CategoryEnum getCategory() {
        return detailCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // end of getters/setters
    @Override
    public String toString() {
        return "Serial number: " + serialNumber +
                "  price: " + price +
                "$  Category: " + detailCategory;
    }
}
