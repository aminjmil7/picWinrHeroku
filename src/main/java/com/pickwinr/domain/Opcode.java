package com.pickwinr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Opcode.
 */
@Entity
@Table(name = "opcode")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Opcode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "count", nullable = false)
    private Integer count;

    @NotNull
    @Column(name = "ceation_dated", nullable = false)
    private Instant ceationDated;

    @Column(name = "expiration_date")
    private Instant expirationDate;

    @Column(name = "opiration_code")
    private String opirationCode;

    @OneToMany(mappedBy = "opcode")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "post", "opcode" }, allowSetters = true)
    private Set<Cycle> cycles = new HashSet<>();

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Opcode id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getCount() {
        return this.count;
    }

    public Opcode count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Instant getCeationDated() {
        return this.ceationDated;
    }

    public Opcode ceationDated(Instant ceationDated) {
        this.ceationDated = ceationDated;
        return this;
    }

    public void setCeationDated(Instant ceationDated) {
        this.ceationDated = ceationDated;
    }

    public Instant getExpirationDate() {
        return this.expirationDate;
    }

    public Opcode expirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getOpirationCode() {
        return this.opirationCode;
    }

    public Opcode opirationCode(String opirationCode) {
        this.opirationCode = opirationCode;
        return this;
    }

    public void setOpirationCode(String opirationCode) {
        this.opirationCode = opirationCode;
    }

    public Set<Cycle> getCycles() {
        return this.cycles;
    }

    public Opcode cycles(Set<Cycle> cycles) {
        this.setCycles(cycles);
        return this;
    }

    public Opcode addCycle(Cycle cycle) {
        this.cycles.add(cycle);
        cycle.setOpcode(this);
        return this;
    }

    public Opcode removeCycle(Cycle cycle) {
        this.cycles.remove(cycle);
        cycle.setOpcode(null);
        return this;
    }

    public void setCycles(Set<Cycle> cycles) {
        if (this.cycles != null) {
            this.cycles.forEach(i -> i.setOpcode(null));
        }
        if (cycles != null) {
            cycles.forEach(i -> i.setOpcode(this));
        }
        this.cycles = cycles;
    }

    public User getUser() {
        return this.user;
    }

    public Opcode user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opcode)) {
            return false;
        }
        return id != null && id.equals(((Opcode) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Opcode{" +
            "id=" + getId() +
            ", count=" + getCount() +
            ", ceationDated='" + getCeationDated() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", opirationCode='" + getOpirationCode() + "'" +
            "}";
    }
}
