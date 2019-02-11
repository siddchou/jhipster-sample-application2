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

/**
 * Criteria class for the Services entity. This class is used in ServicesResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /services?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServicesCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter serviceName;

    private StringFilter serviceDescription;

    private LongFilter rateId;

    private LongFilter categoryId;

    private LongFilter locationId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getServiceName() {
        return serviceName;
    }

    public void setServiceName(StringFilter serviceName) {
        this.serviceName = serviceName;
    }

    public StringFilter getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(StringFilter serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public LongFilter getRateId() {
        return rateId;
    }

    public void setRateId(LongFilter rateId) {
        this.rateId = rateId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServicesCriteria that = (ServicesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serviceName, that.serviceName) &&
            Objects.equals(serviceDescription, that.serviceDescription) &&
            Objects.equals(rateId, that.rateId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(locationId, that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serviceName,
        serviceDescription,
        rateId,
        categoryId,
        locationId
        );
    }

    @Override
    public String toString() {
        return "ServicesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serviceName != null ? "serviceName=" + serviceName + ", " : "") +
                (serviceDescription != null ? "serviceDescription=" + serviceDescription + ", " : "") +
                (rateId != null ? "rateId=" + rateId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
            "}";
    }

}
