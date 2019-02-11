package io.github.jhipster.application.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.JobStatus;

/**
 * A DTO for the JobHistory entity.
 */
public class JobHistoryDTO implements Serializable {

    private Long id;

    private Instant startDate;

    private Instant endDate;

    private JobStatus jobStatus;


    private Long paymentId;

    private Long contractorServiceId;

    private Long userAddressMapId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getContractorServiceId() {
        return contractorServiceId;
    }

    public void setContractorServiceId(Long contractorServiceId) {
        this.contractorServiceId = contractorServiceId;
    }

    public Long getUserAddressMapId() {
        return userAddressMapId;
    }

    public void setUserAddressMapId(Long userAddressMapId) {
        this.userAddressMapId = userAddressMapId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobHistoryDTO jobHistoryDTO = (JobHistoryDTO) o;
        if (jobHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobHistoryDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", jobStatus='" + getJobStatus() + "'" +
            ", payment=" + getPaymentId() +
            ", contractorService=" + getContractorServiceId() +
            ", userAddressMap=" + getUserAddressMapId() +
            "}";
    }
}
