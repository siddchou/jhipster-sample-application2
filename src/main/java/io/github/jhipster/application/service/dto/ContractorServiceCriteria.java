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
 * Criteria class for the ContractorService entity. This class is used in ContractorServiceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /contractor-services?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContractorServiceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isVerified;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private LongFilter contractorId;

    private LongFilter servicesId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(BooleanFilter isVerified) {
        this.isVerified = isVerified;
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

    public LongFilter getContractorId() {
        return contractorId;
    }

    public void setContractorId(LongFilter contractorId) {
        this.contractorId = contractorId;
    }

    public LongFilter getServicesId() {
        return servicesId;
    }

    public void setServicesId(LongFilter servicesId) {
        this.servicesId = servicesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContractorServiceCriteria that = (ContractorServiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(isVerified, that.isVerified) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(contractorId, that.contractorId) &&
            Objects.equals(servicesId, that.servicesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        isVerified,
        startDate,
        endDate,
        contractorId,
        servicesId
        );
    }

    @Override
    public String toString() {
        return "ContractorServiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (isVerified != null ? "isVerified=" + isVerified + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (contractorId != null ? "contractorId=" + contractorId + ", " : "") +
                (servicesId != null ? "servicesId=" + servicesId + ", " : "") +
            "}";
    }

}
