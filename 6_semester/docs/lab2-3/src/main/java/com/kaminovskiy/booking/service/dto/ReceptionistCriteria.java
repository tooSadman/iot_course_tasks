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
 * Criteria class for the {@link com.kaminovskiy.booking.domain.Receptionist} entity. This class is used
 * in {@link com.kaminovskiy.booking.web.rest.ReceptionistResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /receptionists?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReceptionistCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter phone;

    private StringFilter address;

    private LongFilter ownerId;

    private LongFilter managedRoomsId;

    private LongFilter billId;

    public ReceptionistCriteria() {
    }

    public ReceptionistCriteria(ReceptionistCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
        this.managedRoomsId = other.managedRoomsId == null ? null : other.managedRoomsId.copy();
        this.billId = other.billId == null ? null : other.billId.copy();
    }

    @Override
    public ReceptionistCriteria copy() {
        return new ReceptionistCriteria(this);
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

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getManagedRoomsId() {
        return managedRoomsId;
    }

    public void setManagedRoomsId(LongFilter managedRoomsId) {
        this.managedRoomsId = managedRoomsId;
    }

    public LongFilter getBillId() {
        return billId;
    }

    public void setBillId(LongFilter billId) {
        this.billId = billId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReceptionistCriteria that = (ReceptionistCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(address, that.address) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(managedRoomsId, that.managedRoomsId) &&
            Objects.equals(billId, that.billId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        phone,
        address,
        ownerId,
        managedRoomsId,
        billId
        );
    }

    @Override
    public String toString() {
        return "ReceptionistCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (managedRoomsId != null ? "managedRoomsId=" + managedRoomsId + ", " : "") +
                (billId != null ? "billId=" + billId + ", " : "") +
            "}";
    }

}
