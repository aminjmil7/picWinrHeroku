package com.pickwinr.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.pickwinr.domain.Opcode} entity. This class is used
 * in {@link com.pickwinr.web.rest.OpcodeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /opcodes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OpcodeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter opirationCode;

    private IntegerFilter count;

    private InstantFilter ceationDated;

    private InstantFilter expirationDate;

    private LongFilter cycleId;

    private LongFilter userId;

    public OpcodeCriteria() {}

    public OpcodeCriteria(OpcodeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.opirationCode = other.opirationCode == null ? null : other.opirationCode.copy();
        this.count = other.count == null ? null : other.count.copy();
        this.ceationDated = other.ceationDated == null ? null : other.ceationDated.copy();
        this.expirationDate = other.expirationDate == null ? null : other.expirationDate.copy();
        this.cycleId = other.cycleId == null ? null : other.cycleId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public OpcodeCriteria copy() {
        return new OpcodeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOpirationCode() {
        return opirationCode;
    }

    public StringFilter opirationCode() {
        if (opirationCode == null) {
            opirationCode = new StringFilter();
        }
        return opirationCode;
    }

    public void setOpirationCode(StringFilter opirationCode) {
        this.opirationCode = opirationCode;
    }

    public IntegerFilter getCount() {
        return count;
    }

    public IntegerFilter count() {
        if (count == null) {
            count = new IntegerFilter();
        }
        return count;
    }

    public void setCount(IntegerFilter count) {
        this.count = count;
    }

    public InstantFilter getCeationDated() {
        return ceationDated;
    }

    public InstantFilter ceationDated() {
        if (ceationDated == null) {
            ceationDated = new InstantFilter();
        }
        return ceationDated;
    }

    public void setCeationDated(InstantFilter ceationDated) {
        this.ceationDated = ceationDated;
    }

    public InstantFilter getExpirationDate() {
        return expirationDate;
    }

    public InstantFilter expirationDate() {
        if (expirationDate == null) {
            expirationDate = new InstantFilter();
        }
        return expirationDate;
    }

    public void setExpirationDate(InstantFilter expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LongFilter getCycleId() {
        return cycleId;
    }

    public LongFilter cycleId() {
        if (cycleId == null) {
            cycleId = new LongFilter();
        }
        return cycleId;
    }

    public void setCycleId(LongFilter cycleId) {
        this.cycleId = cycleId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OpcodeCriteria that = (OpcodeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(opirationCode, that.opirationCode) &&
            Objects.equals(count, that.count) &&
            Objects.equals(ceationDated, that.ceationDated) &&
            Objects.equals(expirationDate, that.expirationDate) &&
            Objects.equals(cycleId, that.cycleId) &&
            Objects.equals(userId, that.userId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opirationCode, count, ceationDated, expirationDate, cycleId, userId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpcodeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (opirationCode != null ? "opirationCode=" + opirationCode + ", " : "") +
            (count != null ? "count=" + count + ", " : "") +
            (ceationDated != null ? "ceationDated=" + ceationDated + ", " : "") +
            (expirationDate != null ? "expirationDate=" + expirationDate + ", " : "") +
            (cycleId != null ? "cycleId=" + cycleId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }
}
