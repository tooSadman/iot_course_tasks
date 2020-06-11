package com.kaminovskiy.booking.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.kaminovskiy.booking.domain.Customer} entity. This class is used
 * in {@link com.kaminovskiy.booking.web.rest.CustomerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter phone;

    private StringFilter address;

    private IntegerFilter roomNumber;

    private LongFilter ownerId;

    private LongFilter foodItemsId;

    private LongFilter roomId;

    private LongFilter billsId;

    private LongFilter managerId;

    public CustomerCriteria() {
    }

    public CustomerCriteria(CustomerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.roomNumber = other.roomNumber == null ? null : other.roomNumber.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
        this.foodItemsId = other.foodItemsId == null ? null : other.foodItemsId.copy();
        this.roomId = other.roomId == null ? null : other.roomId.copy();
        this.billsId = other.billsId == null ? null : other.billsId.copy();
        this.managerId = other.managerId == null ? null : other.managerId.copy();
    }

    @Override
    public CustomerCriteria copy() {
        return new CustomerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public IntegerFilter getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(IntegerFilter roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getFoodItemsId() {
        return foodItemsId;
    }

    public void setFoodItemsId(LongFilter foodItemsId) {
        this.foodItemsId = foodItemsId;
    }

    public LongFilter getRoomId() {
        return roomId;
    }

    public void setRoomId(LongFilter roomId) {
        this.roomId = roomId;
    }

    public LongFilter getBillsId() {
        return billsId;
    }

    public void setBillsId(LongFilter billsId) {
        this.billsId = billsId;
    }

    public LongFilter getManagerId() {
        return managerId;
    }

    public void setManagerId(LongFilter managerId) {
        this.managerId = managerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerCriteria that = (CustomerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(address, that.address) &&
            Objects.equals(roomNumber, that.roomNumber) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(foodItemsId, that.foodItemsId) &&
            Objects.equals(roomId, that.roomId) &&
            Objects.equals(billsId, that.billsId) &&
            Objects.equals(managerId, that.managerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        phone,
        address,
        roomNumber,
        ownerId,
        foodItemsId,
        roomId,
        billsId,
        managerId
        );
    }

    @Override
    public String toString() {
        return "CustomerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (roomNumber != null ? "roomNumber=" + roomNumber + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (foodItemsId != null ? "foodItemsId=" + foodItemsId + ", " : "") +
                (roomId != null ? "roomId=" + roomId + ", " : "") +
                (billsId != null ? "billsId=" + billsId + ", " : "") +
                (managerId != null ? "managerId=" + managerId + ", " : "") +
            "}";
    }

}
