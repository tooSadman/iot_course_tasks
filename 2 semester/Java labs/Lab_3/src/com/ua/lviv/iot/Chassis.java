package com.ua.lviv.iot;

public class Chassis extends Spares {
  public int numberof;

  public Chassis(int serialNumber, int price, CategoryEnum detailCategory, int numberof/*, String name*/) {
    super(serialNumber, price, detailCategory/*, name*/);
    this.numberof = numberof;
  }

  @Override
  public String toString(){
    return "Serial number: " + getSerialNumber() +
    "  price: " + getPrice() +
    "$  Category: " + getCategory() +
    "  number of detailsl: " + numberof+" pieces";
  }
}
