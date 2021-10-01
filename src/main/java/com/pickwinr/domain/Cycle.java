package com.pickwinr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cycle.
 */
@Entity
@Table(name = "cycle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cycle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "run_date", nullable = false)
    private Instant runDate;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "winners")
    private String winners;

    @Column(name = "alternatives")
    private String alternatives;

    @JsonIgnoreProperties(value = { "cycle" }, allowSetters = true)
    @OneToOne(mappedBy = "cycle")
    private Post post;

    @ManyToOne
    @JsonIgnoreProperties(value = { "cycles", "user" }, allowSetters = true)
    private Opcode opcode;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cycle id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getRunDate() {
        return this.runDate;
    }

    public Cycle runDate(Instant runDate) {
        this.runDate = runDate;
        return this;
    }

    public void setRunDate(Instant runDate) {
        this.runDate = runDate;
    }

    public String getEmail() {
        return this.email;
    }

    public Cycle email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWinners() {
        return this.winners;
    }

    public Cycle winners(String winners) {
        this.winners = winners;
        return this;
    }

    public void setWinners(String winners) {
        this.winners = winners;
    }

    public String getAlternatives() {
        return this.alternatives;
    }

    public Cycle alternatives(String alternatives) {
        this.alternatives = alternatives;
        return this;
    }

    public void setAlternatives(String alternatives) {
        this.alternatives = alternatives;
    }

    public Post getPost() {
        return this.post;
    }

    public Cycle post(Post post) {
        this.setPost(post);
        return this;
    }

    public void setPost(Post post) {
        if (this.post != null) {
            this.post.setCycle(null);
        }
        if (post != null) {
            post.setCycle(this);
        }
        this.post = post;
    }

    public Opcode getOpcode() {
        return this.opcode;
    }

    public Cycle opcode(Opcode opcode) {
        this.setOpcode(opcode);
        return this;
    }

    public void setOpcode(Opcode opcode) {
        this.opcode = opcode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cycle)) {
            return false;
        }
        return id != null && id.equals(((Cycle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cycle{" +
            "id=" + getId() +
            ", runDate='" + getRunDate() + "'" +
            ", email='" + getEmail() + "'" +
            ", winners='" + getWinners() + "'" +
            ", alternatives='" + getAlternatives() + "'" +
            "}";
    }
}
