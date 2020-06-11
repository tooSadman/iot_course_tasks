package com.kaminovskiy.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Receptionist.
 */
@Entity
@Table(name = "receptionist")
public class Receptionist implements Serializable {

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

    @OneToOne
    @JoinColumn(unique = true)
    private Owner owner;

    @OneToMany(mappedBy = "receptionist")
    private Set<Room> managedRooms = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("receptionists")
    private Bill bill;

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

    public Receptionist name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public Receptionist phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public Receptionist address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Owner getOwner() {
        return owner;
    }

    public Receptionist owner(Owner owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Set<Room> getManagedRooms() {
        return managedRooms;
    }

    public Receptionist managedRooms(Set<Room> rooms) {
        this.managedRooms = rooms;
        return this;
    }

    public Receptionist addManagedRooms(Room room) {
        this.managedRooms.add(room);
        room.setReceptionist(this);
        return this;
    }

    public Receptionist removeManagedRooms(Room room) {
        this.managedRooms.remove(room);
        room.setReceptionist(null);
        return this;
    }

    public void setManagedRooms(Set<Room> rooms) {
        this.managedRooms = rooms;
    }

    public Bill getBill() {
        return bill;
    }

    public Receptionist bill(Bill bill) {
        this.bill = bill;
        return this;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Receptionist)) {
            return false;
        }
        return id != null && id.equals(((Receptionist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Receptionist{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
