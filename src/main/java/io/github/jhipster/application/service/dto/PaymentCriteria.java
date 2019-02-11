package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.PaymentType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Payment entity. This class is used in PaymentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /payments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentCriteria implements Serializable {
    /**
     * Class for filtering PaymentType
     */
    public static class PaymentTypeFilter extends Filter<PaymentType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter totalPaymentAmount;

    private PaymentTypeFilter paymentType;

    private DoubleFilter contractorAmount;

    private DoubleFilter businessAmount;

    private InstantFilter startDate;

    private InstantFilter endDate;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(DoubleFilter totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public PaymentTypeFilter getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeFilter paymentType) {
        this.paymentType = paymentType;
    }

    public DoubleFilter getContractorAmount() {
        return contractorAmount;
    }

    public void setContractorAmount(DoubleFilter contractorAmount) {
        this.contractorAmount = contractorAmount;
    }

    public DoubleFilter getBusinessAmount() {
        return businessAmount;
    }

    public void setBusinessAmount(DoubleFilter businessAmount) {
        this.businessAmount = businessAmount;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentCriteria that = (PaymentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(totalPaymentAmount, that.totalPaymentAmount) &&
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(contractorAmount, that.contractorAmount) &&
            Objects.equals(businessAmount, that.businessAmount) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        totalPaymentAmount,
        paymentType,
        contractorAmount,
        businessAmount,
        startDate,
        endDate
        );
    }

    @Override
    public String toString() {
        return "PaymentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (totalPaymentAmount != null ? "totalPaymentAmount=" + totalPaymentAmount + ", " : "") +
                (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
                (contractorAmount != null ? "contractorAmount=" + contractorAmount + ", " : "") +
                (businessAmount != null ? "businessAmount=" + businessAmount + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
            "}";
    }

}
