package io.github.jhipster.application.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.PaymentType;

/**
 * A DTO for the Payment entity.
 */
public class PaymentDTO implements Serializable {

    private Long id;

    private Double totalPaymentAmount;

    private PaymentType paymentType;

    private Double contractorAmount;

    private Double businessAmount;

    private Instant startDate;

    private Instant endDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(Double totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Double getContractorAmount() {
        return contractorAmount;
    }

    public void setContractorAmount(Double contractorAmount) {
        this.contractorAmount = contractorAmount;
    }

    public Double getBusinessAmount() {
        return businessAmount;
    }

    public void setBusinessAmount(Double businessAmount) {
        this.businessAmount = businessAmount;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentDTO paymentDTO = (PaymentDTO) o;
        if (paymentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", totalPaymentAmount=" + getTotalPaymentAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", contractorAmount=" + getContractorAmount() +
            ", businessAmount=" + getBusinessAmount() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
