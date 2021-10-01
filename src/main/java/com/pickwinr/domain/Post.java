package com.pickwinr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "link", nullable = false)
    private String link;

    @NotNull
    @Column(name = "comment_count", nullable = false)
    private Integer commentCount;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @JsonIgnoreProperties(value = { "post", "opcode" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Cycle cycle;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post id(Long id) {
        this.id = id;
        return this;
    }

    public String getLink() {
        return this.link;
    }

    public Post link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getCommentCount() {
        return this.commentCount;
    }

    public Post commentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return this.content;
    }

    public Post content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Cycle getCycle() {
        return this.cycle;
    }

    public Post cycle(Cycle cycle) {
        this.setCycle(cycle);
        return this;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            ", commentCount=" + getCommentCount() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
