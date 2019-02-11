package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.JobStatus;

/**
 * A JobHistory.
 */
@Entity
@Table(name = "job_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jobhistory")
public class JobHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_status")
    private JobStatus jobStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private Payment payment;

    @OneToOne
    @JoinColumn(unique = true)
    private ContractorService contractorService;

    @OneToOne
    @JoinColumn(unique = true)
    private UserAddressMap userAddressMap;

    @OneToMany(mappedBy = "jobHistory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobTimeLog> jobTimeLogs = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public JobHistory startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public JobHistory endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public JobHistory jobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
        return this;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Payment getPayment() {
        return payment;
    }

    public JobHistory payment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public ContractorService getContractorService() {
        return contractorService;
    }

    public JobHistory contractorService(ContractorService contractorService) {
        this.contractorService = contractorService;
        return this;
    }

    public void setContractorService(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    public UserAddressMap getUserAddressMap() {
        return userAddressMap;
    }

    public JobHistory userAddressMap(UserAddressMap userAddressMap) {
        this.userAddressMap = userAddressMap;
        return this;
    }

    public void setUserAddressMap(UserAddressMap userAddressMap) {
        this.userAddressMap = userAddressMap;
    }

    public Set<JobTimeLog> getJobTimeLogs() {
        return jobTimeLogs;
    }

    public JobHistory jobTimeLogs(Set<JobTimeLog> jobTimeLogs) {
        this.jobTimeLogs = jobTimeLogs;
        return this;
    }

    public JobHistory addJobTimeLog(JobTimeLog jobTimeLog) {
        this.jobTimeLogs.add(jobTimeLog);
        jobTimeLog.setJobHistory(this);
        return this;
    }

    public JobHistory removeJobTimeLog(JobTimeLog jobTimeLog) {
        this.jobTimeLogs.remove(jobTimeLog);
        jobTimeLog.setJobHistory(null);
        return this;
    }

    public void setJobTimeLogs(Set<JobTimeLog> jobTimeLogs) {
        this.jobTimeLogs = jobTimeLogs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobHistory jobHistory = (JobHistory) o;
        if (jobHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobHistory{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", jobStatus='" + getJobStatus() + "'" +
            "}";
    }
}
