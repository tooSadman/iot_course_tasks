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
 * Criteria class for the {@link com.kaminovskiy.booking.domain.Bill} entity. This class is used
 * in {@link com.kaminovskiy.booking.web.rest.BillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BillCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter number;

    private LongFilter ownerId;

    private LongFilter receptionistsId;

    private LongFilter customersId;

    public BillCriteria() {
    }

    public BillCriteria(BillCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
        this.receptionistsId = other.receptionistsId == null ? null : other.receptionistsId.copy();
        this.customersId = other.customersId == null ? null : other.customersId.copy();
    }

    @Override
    public BillCriteria copy() {
        return new BillCriteria(this);
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

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getReceptionistsId() {
        return receptionistsId;
    }

    public void setReceptionistsId(LongFilter receptionistsId) {
        this.receptionistsId = receptionistsId;
    }

    public LongFilter getCustomersId() {
        return customersId;
    }

    public void setCustomersId(LongFilter customersId) {
        this.customersId = customersId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BillCriteria that = (BillCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(number, that.number) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(receptionistsId, that.receptionistsId) &&
            Objects.equals(customersId, that.customersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        number,
        ownerId,
        receptionistsId,
        customersId
        );
    }

    @Override
    public String toString() {
        return "BillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (number != null ? "number=" + number + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (receptionistsId != null ? "receptionistsId=" + receptionistsId + ", " : "") +
                (customersId != null ? "customersId=" + customersId + ", " : "") +
            "}";
    }

}
