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

/**
 * Criteria class for the Rate entity. This class is used in RateResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /rates?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RateCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter rateName;

    private StringFilter rateDesc;

    private IntegerFilter fullRate;

    private IntegerFilter idleRate;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRateName() {
        return rateName;
    }

    public void setRateName(StringFilter rateName) {
        this.rateName = rateName;
    }

    public StringFilter getRateDesc() {
        return rateDesc;
    }

    public void setRateDesc(StringFilter rateDesc) {
        this.rateDesc = rateDesc;
    }

    public IntegerFilter getFullRate() {
        return fullRate;
    }

    public void setFullRate(IntegerFilter fullRate) {
        this.fullRate = fullRate;
    }

    public IntegerFilter getIdleRate() {
        return idleRate;
    }

    public void setIdleRate(IntegerFilter idleRate) {
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
        final RateCriteria that = (RateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(rateName, that.rateName) &&
            Objects.equals(rateDesc, that.rateDesc) &&
            Objects.equals(fullRate, that.fullRate) &&
            Objects.equals(idleRate, that.idleRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        rateName,
        rateDesc,
        fullRate,
        idleRate
        );
    }

    @Override
    public String toString() {
        return "RateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (rateName != null ? "rateName=" + rateName + ", " : "") +
                (rateDesc != null ? "rateDesc=" + rateDesc + ", " : "") +
                (fullRate != null ? "fullRate=" + fullRate + ", " : "") +
                (idleRate != null ? "idleRate=" + idleRate + ", " : "") +
            "}";
    }

}
