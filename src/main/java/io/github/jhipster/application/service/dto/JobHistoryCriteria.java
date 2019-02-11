package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.JobStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the JobHistory entity. This class is used in JobHistoryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /job-histories?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JobHistoryCriteria implements Serializable {
    /**
     * Class for filtering JobStatus
     */
    public static class JobStatusFilter extends Filter<JobStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private JobStatusFilter jobStatus;

    private LongFilter paymentId;

    private LongFilter contractorServiceId;

    private LongFilter userAddressMapId;

    private LongFilter jobTimeLogId;

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

    public JobStatusFilter getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatusFilter jobStatus) {
        this.jobStatus = jobStatus;
    }

    public LongFilter getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
    }

    public LongFilter getContractorServiceId() {
        return contractorServiceId;
    }

    public void setContractorServiceId(LongFilter contractorServiceId) {
        this.contractorServiceId = contractorServiceId;
    }

    public LongFilter getUserAddressMapId() {
        return userAddressMapId;
    }

    public void setUserAddressMapId(LongFilter userAddressMapId) {
        this.userAddressMapId = userAddressMapId;
    }

    public LongFilter getJobTimeLogId() {
        return jobTimeLogId;
    }

    public void setJobTimeLogId(LongFilter jobTimeLogId) {
        this.jobTimeLogId = jobTimeLogId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JobHistoryCriteria that = (JobHistoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(jobStatus, that.jobStatus) &&
            Objects.equals(paymentId, that.paymentId) &&
            Objects.equals(contractorServiceId, that.contractorServiceId) &&
            Objects.equals(userAddressMapId, that.userAddressMapId) &&
            Objects.equals(jobTimeLogId, that.jobTimeLogId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startDate,
        endDate,
        jobStatus,
        paymentId,
        contractorServiceId,
        userAddressMapId,
        jobTimeLogId
        );
    }

    @Override
    public String toString() {
        return "JobHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (jobStatus != null ? "jobStatus=" + jobStatus + ", " : "") +
                (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
                (contractorServiceId != null ? "contractorServiceId=" + contractorServiceId + ", " : "") +
                (userAddressMapId != null ? "userAddressMapId=" + userAddressMapId + ", " : "") +
                (jobTimeLogId != null ? "jobTimeLogId=" + jobTimeLogId + ", " : "") +
            "}";
    }

}
