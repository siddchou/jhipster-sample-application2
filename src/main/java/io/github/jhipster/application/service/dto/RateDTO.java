package io.github.jhipster.application.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Rate entity.
 */
public class RateDTO implements Serializable {

    private Long id;

    private String rateName;

    private String rateDesc;

    private Integer fullRate;

    private Integer idleRate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public String getRateDesc() {
        return rateDesc;
    }

    public void setRateDesc(String rateDesc) {
        this.rateDesc = rateDesc;
    }

    public Integer getFullRate() {
        return fullRate;
    }

    public void setFullRate(Integer fullRate) {
        this.fullRate = fullRate;
    }

    public Integer getIdleRate() {
        return idleRate;
    }

    public void setIdleRate(Integer idleRate) {
        this.idleRate = idleRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RateDTO rateDTO = (RateDTO) o;
        if (rateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RateDTO{" +
            "id=" + getId() +
            ", rateName='" + getRateName() + "'" +
            ", rateDesc='" + getRateDesc() + "'" +
            ", fullRate=" + getFullRate() +
            ", idleRate=" + getIdleRate() +
            "}";
    }
}
