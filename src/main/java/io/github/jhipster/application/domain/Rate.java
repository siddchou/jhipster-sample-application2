package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Rate.
 */
@Entity
@Table(name = "rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rate")
public class Rate implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate_name")
    private String rateName;

    @Column(name = "rate_desc")
    private String rateDesc;

    @Column(name = "full_rate")
    private Integer fullRate;

    @Column(name = "idle_rate")
    private Integer idleRate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRateName() {
        return rateName;
    }

    public Rate rateName(String rateName) {
        this.rateName = rateName;
        return this;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public String getRateDesc() {
        return rateDesc;
    }

    public Rate rateDesc(String rateDesc) {
        this.rateDesc = rateDesc;
        return this;
    }

    public void setRateDesc(String rateDesc) {
        this.rateDesc = rateDesc;
    }

    public Integer getFullRate() {
        return fullRate;
    }

    public Rate fullRate(Integer fullRate) {
        this.fullRate = fullRate;
        return this;
    }

    public void setFullRate(Integer fullRate) {
        this.fullRate = fullRate;
    }

    public Integer getIdleRate() {
        return idleRate;
    }

    public Rate idleRate(Integer idleRate) {
        this.idleRate = idleRate;
        return this;
    }

    public void setIdleRate(Integer idleRate) {
        this.idleRate = idleRate;
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
        Rate rate = (Rate) o;
        if (rate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rate{" +
            "id=" + getId() +
            ", rateName='" + getRateName() + "'" +
            ", rateDesc='" + getRateDesc() + "'" +
            ", fullRate=" + getFullRate() +
            ", idleRate=" + getIdleRate() +
            "}";
    }
}
