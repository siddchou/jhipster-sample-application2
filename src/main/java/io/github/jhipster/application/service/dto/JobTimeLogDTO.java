package io.github.jhipster.application.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the JobTimeLog entity.
 */
public class JobTimeLogDTO implements Serializable {

    private Long id;

    private Instant startDate;

    private Instant endDate;

    private Boolean isValidated;


    private Long jobHistoryId;

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

    public Boolean isIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public Long getJobHistoryId() {
        return jobHistoryId;
    }

    public void setJobHistoryId(Long jobHistoryId) {
        this.jobHistoryId = jobHistoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobTimeLogDTO jobTimeLogDTO = (JobTimeLogDTO) o;
        if (jobTimeLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobTimeLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobTimeLogDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", isValidated='" + isIsValidated() + "'" +
            ", jobHistory=" + getJobHistoryId() +
            "}";
    }
}
