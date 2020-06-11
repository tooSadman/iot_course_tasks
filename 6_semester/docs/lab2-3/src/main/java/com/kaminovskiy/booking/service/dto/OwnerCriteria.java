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
 * Criteria class for the {@link com.kaminovskiy.booking.domain.Owner} entity. This class is used
 * in {@link com.kaminovskiy.booking.web.rest.OwnerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /owners?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OwnerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter phone;

    private LongFilter managerId;

    private LongFilter receptionistId;

    private LongFilter customerId;

    private LongFilter foodItemId;

    private LongFilter billId;

    private LongFilter roomId;

    public OwnerCriteria() {
    }

    public OwnerCriteria(OwnerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.managerId = other.managerId == null ? null : other.managerId.copy();
        this.receptionistId = other.receptionistId == null ? null : other.receptionistId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.foodItemId = other.foodItemId == null ? null : other.foodItemId.copy();
        this.billId = other.billId == null ? null : other.billId.copy();
        this.roomId = other.roomId == null ? null : other.roomId.copy();
    }

    @Override
    public OwnerCriteria copy() {
        return new OwnerCriteria(this);
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

    public LongFilter getManagerId() {
        return managerId;
    }

    public void setManagerId(LongFilter managerId) {
        this.managerId = managerId;
    }

    public LongFilter getReceptionistId() {
        return receptionistId;
    }

    public void setReceptionistId(LongFilter receptionistId) {
        this.receptionistId = receptionistId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(LongFilter foodItemId) {
        this.foodItemId = foodItemId;
    }

    public LongFilter getBillId() {
        return billId;
    }

    public void setBillId(LongFilter billId) {
        this.billId = billId;
    }

    public LongFilter getRoomId() {
        return roomId;
    }

    public void setRoomId(LongFilter roomId) {
        this.roomId = roomId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OwnerCriteria that = (OwnerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(managerId, that.managerId) &&
            Objects.equals(receptionistId, that.receptionistId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(foodItemId, that.foodItemId) &&
            Objects.equals(billId, that.billId) &&
            Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        phone,
        managerId,
        receptionistId,
        customerId,
        foodItemId,
        billId,
        roomId
        );
    }

    @Override
    public String toString() {
        return "OwnerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (managerId != null ? "managerId=" + managerId + ", " : "") +
                (receptionistId != null ? "receptionistId=" + receptionistId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (foodItemId != null ? "foodItemId=" + foodItemId + ", " : "") +
                (billId != null ? "billId=" + billId + ", " : "") +
                (roomId != null ? "roomId=" + roomId + ", " : "") +
            "}";
    }

}
