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
 * Criteria class for the {@link com.kaminovskiy.booking.domain.Room} entity. This class is used
 * in {@link com.kaminovskiy.booking.web.rest.RoomResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rooms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RoomCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter number;

    private StringFilter location;

    private LongFilter ownerId;

    private LongFilter customerId;

    private LongFilter receptionistId;

    public RoomCriteria() {
    }

    public RoomCriteria(RoomCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.receptionistId = other.receptionistId == null ? null : other.receptionistId.copy();
    }

    @Override
    public RoomCriteria copy() {
        return new RoomCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getNumber() {
        return number;
    }

    public void setNumber(IntegerFilter number) {
        this.number = number;
    }

    public StringFilter getLocation() {
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getReceptionistId() {
        return receptionistId;
    }

    public void setReceptionistId(LongFilter receptionistId) {
        this.receptionistId = receptionistId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RoomCriteria that = (RoomCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(number, that.number) &&
            Objects.equals(location, that.location) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(receptionistId, that.receptionistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        number,
        location,
        ownerId,
        customerId,
        receptionistId
        );
    }

    @Override
    public String toString() {
        return "RoomCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (number != null ? "number=" + number + ", " : "") +
                (location != null ? "location=" + location + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (receptionistId != null ? "receptionistId=" + receptionistId + ", " : "") +
            "}";
    }

}
