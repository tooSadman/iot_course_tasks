package com.ua.lviv.iot;

public class Electronics extends Spares {
  private int type;

  public Electronics(int serialNumber, int price, CategoryEnum detailCategory, int type/*, String name*/) {
    super(serialNumber, price, detailCategory/*, name*/);
    this.type = type;
  }



  @Override
  public String toString(){
    return "Serial number: " + getSerialNumber() +
    "  price: " + getPrice() +
    "$  Category: " + getCategory() +
    "  detail type: " + type + "cm";
  }
}
