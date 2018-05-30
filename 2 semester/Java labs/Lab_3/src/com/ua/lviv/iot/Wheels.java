package com.ua.lviv.iot;

public class Wheels extends Chassis {
  private int size;

  public Wheels(int serialNumber, int price, CategoryEnum detailCategory,int numberof,/*, String name,*/ int size) {
    super(serialNumber, price, detailCategory, numberof/*, name*/);
    this.size = size;
  }

  @Override
  public String toString(){
    return "Serial number: " + getSerialNumber() +
    "  price: " + getPrice() +
    "$  Category: " + getCategory() +
    "  number of detailsl: " + numberof+" pieces" +
    "  wheel size: " + size + "cm";
  }

  //  getter/Setters

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
