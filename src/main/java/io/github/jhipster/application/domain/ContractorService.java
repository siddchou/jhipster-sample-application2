package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ContractorService.
 */
@Entity
@Table(name = "contractor_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contractorservice")
public class ContractorService implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Contractor contractor;

    @OneToOne
    @JoinColumn(unique = true)
    private Services services;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsVerified() {
        return isVerified;
    }

    public ContractorService isVerified(Boolean isVerified) {
        this.isVerified = isVerified;
        return this;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public ContractorService startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public ContractorService endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public ContractorService contractor(Contractor contractor) {
        this.contractor = contractor;
        return this;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public Services getServices() {
        return services;
    }

    public ContractorService services(Services services) {
        this.services = services;
        return this;
    }

    public void setServices(Services services) {
        this.services = services;
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
        ContractorService contractorService = (ContractorService) o;
        if (contractorService.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contractorService.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContractorService{" +
            "id=" + getId() +
            ", isVerified='" + isIsVerified() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
