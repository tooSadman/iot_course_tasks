package com.kaminovskiy.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$")
    @Column(name = "phone", unique = true)
    private String phone;

    @Size(max = 100)
    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "room_number")
    private Integer roomNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private Owner owner;

    @OneToMany(mappedBy = "customer")
    private Set<FoodItem> foodItems = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Room> rooms = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "customer_bills",
               joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "bills_id", referencedColumnName = "id"))
    private Set<Bill> bills = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("customers")
    private Manager manager;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public Customer phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public Customer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public Customer roomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Owner getOwner() {
        return owner;
    }

    public Customer owner(Owner owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Set<FoodItem> getFoodItems() {
        return foodItems;
    }

    public Customer foodItems(Set<FoodItem> foodItems) {
        this.foodItems = foodItems;
        return this;
    }

    public Customer addFoodItems(FoodItem foodItem) {
        this.foodItems.add(foodItem);
        foodItem.setCustomer(this);
        return this;
    }

    public Customer removeFoodItems(FoodItem foodItem) {
        this.foodItems.remove(foodItem);
        foodItem.setCustomer(null);
        return this;
    }

    public void setFoodItems(Set<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Customer rooms(Set<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    public Customer addRoom(Room room) {
        this.rooms.add(room);
        room.setCustomer(this);
        return this;
    }

    public Customer removeRoom(Room room) {
        this.rooms.remove(room);
        room.setCustomer(null);
        return this;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<Bill> getBills() {
        return bills;
    }

    public Customer bills(Set<Bill> bills) {
        this.bills = bills;
        return this;
    }

    public Customer addBills(Bill bill) {
        this.bills.add(bill);
        bill.getCustomers().add(this);
        return this;
    }

    public Customer removeBills(Bill bill) {
        this.bills.remove(bill);
        bill.getCustomers().remove(this);
        return this;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    public Manager getManager() {
        return manager;
    }

    public Customer manager(Manager manager) {
        this.manager = manager;
        return this;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            ", roomNumber=" + getRoomNumber() +
            "}";
    }
}
