package com.kaminovskiy.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bill.
 */
@Entity
@Table(name = "bill")
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @OneToOne
    @JoinColumn(unique = true)
    private Owner owner;

    @OneToMany(mappedBy = "bill")
    private Set<Receptionist> receptionists = new HashSet<>();

    @ManyToMany(mappedBy = "bills")
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public Bill number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Owner getOwner() {
        return owner;
    }

    public Bill owner(Owner owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Set<Receptionist> getReceptionists() {
        return receptionists;
    }

    public Bill receptionists(Set<Receptionist> receptionists) {
        this.receptionists = receptionists;
        return this;
    }

    public Bill addReceptionists(Receptionist receptionist) {
        this.receptionists.add(receptionist);
        receptionist.setBill(this);
        return this;
    }

    public Bill removeReceptionists(Receptionist receptionist) {
        this.receptionists.remove(receptionist);
        receptionist.setBill(null);
        return this;
    }

    public void setReceptionists(Set<Receptionist> receptionists) {
        this.receptionists = receptionists;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public Bill customers(Set<Customer> customers) {
        this.customers = customers;
        return this;
    }

    public Bill addCustomers(Customer customer) {
        this.customers.add(customer);
        customer.getBills().add(this);
        return this;
    }

    public Bill removeCustomers(Customer customer) {
        this.customers.remove(customer);
        customer.getBills().remove(this);
        return this;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bill)) {
            return false;
        }
        return id != null && id.equals(((Bill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bill{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            "}";
    }
}
