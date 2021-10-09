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
 * Criteria class for the {@link com.pickwinr.domain.Cycle} entity. This class is used
 * in {@link com.pickwinr.web.rest.CycleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cycles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CycleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter runDate;

    private StringFilter email;

    private StringFilter winners;

    private StringFilter alternatives;

    private LongFilter postId;

    private LongFilter opcodeId;

    public CycleCriteria() {}

    public CycleCriteria(CycleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.runDate = other.runDate == null ? null : other.runDate.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.winners = other.winners == null ? null : other.winners.copy();
        this.alternatives = other.alternatives == null ? null : other.alternatives.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
        this.opcodeId = other.opcodeId == null ? null : other.opcodeId.copy();
    }

    @Override
    public CycleCriteria copy() {
        return new CycleCriteria(this);
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

    public InstantFilter getRunDate() {
        return runDate;
    }

    public InstantFilter runDate() {
        if (runDate == null) {
            runDate = new InstantFilter();
        }
        return runDate;
    }

    public void setRunDate(InstantFilter runDate) {
        this.runDate = runDate;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getWinners() {
        return winners;
    }

    public StringFilter winners() {
        if (winners == null) {
            winners = new StringFilter();
        }
        return winners;
    }

    public void setWinners(StringFilter winners) {
        this.winners = winners;
    }

    public StringFilter getAlternatives() {
        return alternatives;
    }

    public StringFilter alternatives() {
        if (alternatives == null) {
            alternatives = new StringFilter();
        }
        return alternatives;
    }

    public void setAlternatives(StringFilter alternatives) {
        this.alternatives = alternatives;
    }

    public LongFilter getPostId() {
        return postId;
    }

    public LongFilter postId() {
        if (postId == null) {
            postId = new LongFilter();
        }
        return postId;
    }

    public void setPostId(LongFilter postId) {
        this.postId = postId;
    }

    public LongFilter getOpcodeId() {
        return opcodeId;
    }

    public LongFilter opcodeId() {
        if (opcodeId == null) {
            opcodeId = new LongFilter();
        }
        return opcodeId;
    }

    public void setOpcodeId(LongFilter opcodeId) {
        this.opcodeId = opcodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CycleCriteria that = (CycleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(runDate, that.runDate) &&
            Objects.equals(email, that.email) &&
            Objects.equals(winners, that.winners) &&
            Objects.equals(alternatives, that.alternatives) &&
            Objects.equals(postId, that.postId) &&
            Objects.equals(opcodeId, that.opcodeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, runDate, email, winners, alternatives, postId, opcodeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CycleCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (runDate != null ? "runDate=" + runDate + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (winners != null ? "winners=" + winners + ", " : "") +
            (alternatives != null ? "alternatives=" + alternatives + ", " : "") +
            (postId != null ? "postId=" + postId + ", " : "") +
            (opcodeId != null ? "opcodeId=" + opcodeId + ", " : "") +
            "}";
    }
}
