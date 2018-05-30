package com.ua.lviv.iot.spares;

import com.ua.lviv.iot.spares.CategoryEnum;
import javax.persistence.*;

@Entity
public class Spares {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "serial_number")
    private Integer serialNumber;

    @Column(name = "price")
    private Integer price;

    @Column(name = "detailCategory")
    private CategoryEnum detailCategory;

    public Spares(final Integer id, final int serialNumber, final int price, final CategoryEnum detailCategory) {
        setId(id);
        setSerialNumber(serialNumber);
        setPrice(price);
        setCategory(detailCategory);
    }

    public Spares(){

    }

    //  Setters and getters

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer id) { this.id= id;
    }

    public final int getSerialNumber() {
        return serialNumber;
    }

    public final void setSerialNumber(int serialNumber) { this.serialNumber = serialNumber;
    }


    public final int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public final CategoryEnum getCategory() {
        return detailCategory;
    }

    public void setCategory(CategoryEnum detailCategory) {
        this.detailCategory = detailCategory;
    }

    public String getHeaders() {
        return "serialNumber, price, CategoryEnum ";
    }

    public  String toCSV() {
        return serialNumber + "," + price + "," + detailCategory.name() +" ";
    }
    // end of getters/setters

}
