package com.pickwinr.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.pickwinr.domain.Post} entity. This class is used
 * in {@link com.pickwinr.web.rest.PostResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /posts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PostCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter link;

    private IntegerFilter commentCount;

    private StringFilter content;

    private LongFilter cycleId;

    public PostCriteria() {}

    public PostCriteria(PostCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.commentCount = other.commentCount == null ? null : other.commentCount.copy();
        this.content = other.content == null ? null : other.content.copy();
        this.cycleId = other.cycleId == null ? null : other.cycleId.copy();
    }

    @Override
    public PostCriteria copy() {
        return new PostCriteria(this);
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

    public StringFilter getLink() {
        return link;
    }

    public StringFilter link() {
        if (link == null) {
            link = new StringFilter();
        }
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
    }

    public IntegerFilter getCommentCount() {
        return commentCount;
    }

    public IntegerFilter commentCount() {
        if (commentCount == null) {
            commentCount = new IntegerFilter();
        }
        return commentCount;
    }

    public void setCommentCount(IntegerFilter commentCount) {
        this.commentCount = commentCount;
    }

    public StringFilter getContent() {
        return content;
    }

    public StringFilter content() {
        if (content == null) {
            content = new StringFilter();
        }
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PostCriteria that = (PostCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(link, that.link) &&
            Objects.equals(commentCount, that.commentCount) &&
            Objects.equals(content, that.content) &&
            Objects.equals(cycleId, that.cycleId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, commentCount, content, cycleId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (link != null ? "link=" + link + ", " : "") +
            (commentCount != null ? "commentCount=" + commentCount + ", " : "") +
            (content != null ? "content=" + content + ", " : "") +
            (cycleId != null ? "cycleId=" + cycleId + ", " : "") +
            "}";
    }
}
