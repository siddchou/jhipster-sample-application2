package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the UserAddressMap entity. This class is used in UserAddressMapResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /user-address-maps?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserAddressMapCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private LongFilter appUserId;

    private LongFilter addressId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public LongFilter getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(LongFilter appUserId) {
        this.appUserId = appUserId;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserAddressMapCriteria that = (UserAddressMapCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(appUserId, that.appUserId) &&
            Objects.equals(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startDate,
        endDate,
        appUserId,
        addressId
        );
    }

    @Override
    public String toString() {
        return "UserAddressMapCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (appUserId != null ? "appUserId=" + appUserId + ", " : "") +
                (addressId != null ? "addressId=" + addressId + ", " : "") +
            "}";
    }

}
